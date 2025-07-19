package com.alura.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

public class ConverteDados {

    private final ObjectMapper mapper;

    public ConverteDados() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON: " + e.getMessage());
        }
    }
}
