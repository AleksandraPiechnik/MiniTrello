package com.ii_rest_api.project.security;

public class JwtProperties {
    public static final String SECRET = "ola"; //by which the token gets hashed
    public static final int EXPIRATION_TIME = 864000000; //10 days

    //after we retrive the token we need to send it back in authorization header
    //and we need to prefix it with word BEARER
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";
}
