package com.emazon.stock.infraestructura.util;

public class InfrastructureConstants {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORITIES = "authorities";
    public static final String JWT_KEY_GENERATOR = "${security.jwt.key.private}";
    public static final String JWT_USER_GENERATOR = "${security.jwt.user.generator}";
    public static final int JWT_TIME_EXPIRATION = 1000 * 60 * 24;

    public static final String TEMPLATE_RESPONSE_ERROR = "{\"message\": \"%s\"}";

    private InfrastructureConstants(){}
}
