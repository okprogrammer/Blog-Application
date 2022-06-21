package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidTokenException  extends RuntimeException{
    
    private String token;
    
    public InvalidTokenException(String token) {
        super(String.format("%s is invalid token", token));
        this.token = token;
}

}
