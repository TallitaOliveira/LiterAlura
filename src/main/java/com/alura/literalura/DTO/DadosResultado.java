package com.alura.literalura.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record DadosResultado(
        @JsonAlias("results")List<DadosLivro> resultados
        ) {
}
