package com.example.dome.application.handler;

import com.example.dome.application.common.ResponseParameter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomRestHandler {

    private final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public void responseJson(HttpServletResponse response, ResponseParameter responseParameter) throws IOException {
        response.addHeader("Content-Type", "application/json");
        objectMapper.writeValue(response.getOutputStream(), responseParameter);
    }
}
