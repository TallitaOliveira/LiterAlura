package com.alura.literalura.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<com.alura.literalura.dto.DadosAutor> authors,
        @JsonAlias("languagens")   List <String> idiomas) {

}

