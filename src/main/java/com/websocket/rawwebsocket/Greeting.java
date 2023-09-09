package com.websocket.rawwebsocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Greeting {
    private String message;

    public Greeting() {
        this("");
    }


    public Greeting(String message) {
        this.message = message;
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public byte[] toJsonBytes() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsBytes(this);
    }

    public void readValueFromJson(String json) throws JsonProcessingException  {
        ObjectMapper objectMapper = new ObjectMapper();
        Greeting greating = objectMapper.readValue(json, this.getClass());
        setMessage(greating.getMessage());
    }

    public void readValueFromJson(byte[] json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Greeting greating = objectMapper.readValue(json, this.getClass());
        setMessage(greating.getMessage());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
