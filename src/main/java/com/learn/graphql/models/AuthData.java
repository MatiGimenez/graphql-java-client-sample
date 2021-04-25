package com.learn.graphql.models;

import io.aexp.nodes.graphql.annotations.GraphQLArgument;
import io.aexp.nodes.graphql.annotations.GraphQLProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@GraphQLProperty(name = "login", arguments = {@GraphQLArgument(name = "input")})
public class AuthData {

    private String id;
    private String username;
    private String jwt;

    public AuthData() {}
}
