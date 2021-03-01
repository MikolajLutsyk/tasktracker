package com.devohost.tasktracker.security;

public class SecurityTokenConstants {
    public static final String SECRET = "supersecret_word";
    public static final String PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    public static final long EXPIRATION = 864000000; // 10 days
}