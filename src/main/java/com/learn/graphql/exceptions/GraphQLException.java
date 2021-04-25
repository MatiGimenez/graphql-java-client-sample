package com.learn.graphql.exceptions;

import org.springframework.http.HttpStatus;

import javax.xml.ws.WebServiceException;
import java.util.HashMap;

public class GraphQLException extends WebServiceException {

    private String message;
    private HttpStatus code;
    private HashMap<String, String> response;

    public GraphQLException(String message) {
        super(message);
        this.message = message;
        this.code = this.getCodeFromMessage(this.message);
        this.response = new HashMap<>();
        this.response.put("message", this.message);
        this.response.put("code", this.code.toString());
    }

    private HttpStatus getCodeFromMessage(String message) {
        if (message.contains("Authentication Error")) return HttpStatus.UNAUTHORIZED;
        if (message.contains("Bad Request")) return HttpStatus.BAD_REQUEST;
        return HttpStatus.CONFLICT;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getCode() {
        return this.code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public HashMap<String, String> getResponse(){
        return this.response;
    }
}
