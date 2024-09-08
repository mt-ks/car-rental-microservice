package com.mtks.vehicle.security;

import com.mtks.vehicle.exceptions.ResponseErrorTypes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println("{\n" +
                "  \"status\": \"fail\",\n" +
                "  \"message\": \"Unauthorized.\",\n" +
                "  \"error_type\": \""+ ResponseErrorTypes.unauthorized +"\"\n" +
                "}");

    }
}
