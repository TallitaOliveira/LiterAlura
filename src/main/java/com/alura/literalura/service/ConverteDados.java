package com.alura.literalura.service;

import com.google.gson.Gson;

public class ConverteDados {
    private final Gson gson = new Gson();

    public <T> T obterDados(String json, Class<T> classe) {
        return gson.fromJson(json, classe);
    }
}
