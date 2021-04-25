package com.learn.graphql.models;

import io.aexp.nodes.graphql.annotations.GraphQLArgument;
import io.aexp.nodes.graphql.annotations.GraphQLArguments;
import io.aexp.nodes.graphql.annotations.GraphQLProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@GraphQLProperty(name="getThreads", arguments = @GraphQLArgument(name = "input"))
public class ThreadResult {

    private Integer found;
    private List<Thread> threads;
}
