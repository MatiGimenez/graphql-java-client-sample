package com.learn.graphql.models;

import io.aexp.nodes.graphql.annotations.GraphQLArgument;
import io.aexp.nodes.graphql.annotations.GraphQLProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@GraphQLProperty(name = "getThreadById", arguments = @GraphQLArgument(name = "t_id", type = "Int"))
public class Thread {
    private String t_id;
    private String u_id;
    private Integer likes;
    private String subject;
    private Date dateline;
    private Integer views;
    private String message;

    public Thread() {

    }

    public Thread(String subject, String message, String u_id) {
        this.subject = subject;
        this.message = message;
        this.u_id = u_id;
    }
}
