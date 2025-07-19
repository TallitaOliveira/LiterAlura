package com.alura.literalura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DadosAutor> authors,
        @JsonAlias("languagens") List <String> idiomas,
        @JsonAlias("dowloand_count") Integer dowloands) {

}

