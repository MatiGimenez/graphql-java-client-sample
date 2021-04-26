package com.learn.graphql.GQLClient;

import com.learn.graphql.exceptions.GraphQLException;
import com.learn.graphql.models.AuthData;
import com.learn.graphql.models.LoginInput;
import com.learn.graphql.models.Thread;
import com.learn.graphql.models.ThreadResult;
import io.aexp.nodes.graphql.*;
import io.aexp.nodes.graphql.internal.Error;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.*;

@Service
public class GraphqlClient {

    private static final String GQL_URL = "DUMMY_URL";

    public List<Thread> getThreads(Integer page, Integer size) {
        if (page == null || page == 0) {
            page = 1;
        }
        if (size == null || size == 0) {
            size = 12;
        }

        List<Thread> result = new ArrayList<>();
        GraphQLTemplate graphQLTemplate = new GraphQLTemplate();

        InputObject<Object> searchData = new InputObject.Builder<>()
                .put("page", page)
                .put("size", size)
                .build();

        try {
            GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                    .url(GQL_URL)
                    .scalars(Date.class)
                    .arguments(new Arguments("getThreads", new Argument<>("input", searchData)))
                    .request(ThreadResult.class)
                    //.request("query { getThreads(input: { page: 30, size: 12 }) { found threads { t_id } } }")
                    .build();

            System.out.println(requestEntity.getRequest());
            GraphQLResponseEntity<ThreadResult> responseEntity = graphQLTemplate.query(requestEntity, ThreadResult.class);
            result = responseEntity.getResponse().getThreads();
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public Thread getThread(String id) throws MalformedURLException {
        Thread result = new Thread();
        AuthData loginData = this.login(new LoginInput("mati_test", "contraseña"));
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "Bearer " + loginData.getJwt());
        GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
        GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(GQL_URL)
                .scalars(Date.class)
                .headers(headers)
                //.arguments(new Arguments("getThreadById", new Argument<>("t_id", id)))
                //.request(Thread.class)
                .request("query { getThreadById(t_id: " + id + " ) { message } }")
                .build();

        GraphQLResponseEntity<Thread> responseEntity = graphQLTemplate.query(requestEntity, Thread.class);
        if (responseEntity.getErrors() != null && responseEntity.getErrors().length > 0) {
            Error error = responseEntity.getErrors()[0];
            throw new GraphQLException(error.getMessage());
        }
        result = responseEntity.getResponse();
        return result;
    }

    public AuthData login(LoginInput data) throws MalformedURLException {
        InputObject<Object> inputObject = new InputObject.Builder<>()
                .put("username", data.getUsername())
                .put("password", data.getPassword())
                .build();

        GraphQLTemplate graphQLTemplate = new GraphQLTemplate();
        GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(GQL_URL)
                .arguments(new Arguments("login", new Argument("input", inputObject)))
                .request(AuthData.class)
                .build();

        GraphQLResponseEntity<AuthData> responseEntity = graphQLTemplate.mutate(requestEntity, AuthData.class);
        if (responseEntity.getErrors() != null && responseEntity.getErrors().length > 0) {
            Error error = responseEntity.getErrors()[0];
            throw new GraphQLException(error.getMessage());
        }
        return responseEntity.getResponse();
    }
}
