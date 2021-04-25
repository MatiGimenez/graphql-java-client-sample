package com.learn.graphql.resources;

import com.learn.graphql.GQLClient.GraphqlClient;
import com.learn.graphql.exceptions.GraphQLException;
import com.learn.graphql.models.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ThreadResource {

    @Autowired
    private GraphqlClient graphqlClient;

    @GetMapping("/")
    public Map<String, String> helloWorld() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "Hello World");
        return map;
    }

    @GetMapping("/thread")
    public List<Thread> getThreads(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
        return graphqlClient.getThreads(page, size);
    }

    @GetMapping("/thread/{id}")
    public ResponseEntity<?> getThreadById(@PathVariable("id") String id) {
        try {
            Thread thread = graphqlClient.getThread(id);
            if (thread == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(thread, HttpStatus.OK);
        } catch (GraphQLException gqlE) {
            return new ResponseEntity<>(gqlE.getResponse(), gqlE.getCode());
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
