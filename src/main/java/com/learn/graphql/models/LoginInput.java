package com.learn.graphql.models;

import io.aexp.nodes.graphql.annotations.GraphQLArgument;
import io.aexp.nodes.graphql.annotations.GraphQLProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInput {

    private String username;
    private String password;

    public LoginInput() { }

    public LoginInput(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
