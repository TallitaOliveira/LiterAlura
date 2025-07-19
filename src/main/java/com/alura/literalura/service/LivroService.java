package com.alura.literalura.service;

import com.alura.literalura.modelo.Autor;
import com.alura.literalura.modelo.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        // Aqui você pode mapear manualmente o JSON para o DTO (ou usar uma classe de resposta personalizada)
        // Suponha que você já extraiu os campos relevantes:
        // LivroDTO livroDTO = converteDados.obterDados(json, LivroDTO.class);
        // ...

        System.out.println(">> JSON da API (exibição simulada):");
        System.out.println(json); // apenas exibindo
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
