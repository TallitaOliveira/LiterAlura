package com.alura.literalura.service;

import com.alura.literalura.DTO.DadosAutor;
import com.alura.literalura.DTO.DadosLivro;
import com.alura.literalura.DTO.DadosResultado;
import com.alura.literalura.modelo.Autor;
import com.alura.literalura.modelo.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private final ConsumoAPI consomeAPI = new ConsumoAPI();
    private final ConverteDados converteDados = new ConverteDados();

    public void buscarLivro(String titulo) {
        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");
        String json = consomeAPI.obterDados(url);

        DadosResultado dadosResultado = converteDados.obterDados(json, DadosResultado.class);


        if (dadosResultado != null && !dadosResultado.resultados().isEmpty()) {
            DadosLivro dadosLivro = dadosResultado.resultados().get(0);
            Livro livro = converterDadosParaLivro(dadosLivro);
            livroRepository.save(livro);

            System.out.println("Livro salvo: " + livro.getTitulo());
        } else {
            System.out.println("Nenhum livro encontrado com o título: " + titulo);
        }
    }
    private Livro converterDadosParaLivro(DadosLivro dadosLivro) {
        List<Autor> autores = dadosLivro.autores().stream()
                .map(this::processarAutor)
                .toList();

        return new Livro(
                dadosLivro.titulo(),
                dadosLivro.idiomas().isEmpty() ? "en" : dadosLivro.idiomas().get(0),
                dadosLivro.download(),
                autores
        );
    }

    private Autor processarAutor(DadosAutor dadosAutor) {
        Optional<Autor> autorExistente = autorRepository.findByNome(dadosAutor.nome());

        return autorExistente.orElseGet(() -> {
            Autor novoAutor = new Autor(
                    dadosAutor.nome(),
                    dadosAutor.anoDeNascimento(),
                    dadosAutor.anoDeFalecimento()
            );
            return autorRepository.save(novoAutor);
        });
    }


    public void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        livros.forEach(l -> System.out.println("📖 " + l.getTitulo()));
    }

    public void listarAutoresVivos(int ano) {
        List<Autor> autores = autorRepository.findAll();
        autores.stream()
                .filter(a -> a.getAnoFalecimento() == null || a.getAnoFalecimento() > ano)
                .forEach(a -> System.out.println("👤 " + a.getNome()));
    }

    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findByIdioma(idioma);
        livros.forEach(l -> System.out.println("📘 " + l.getTitulo()));
    }
}
