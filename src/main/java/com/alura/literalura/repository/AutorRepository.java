package com.alura.literalura.repository;

import com.alura.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);
    List<Autor> findByAnoFalecimentoIsNull();
    List<Autor> findByAnoNascimentoLessThanEqual(Integer ano);
}
