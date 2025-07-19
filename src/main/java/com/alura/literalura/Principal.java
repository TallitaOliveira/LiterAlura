package com.alura.literalura;

import com.alura.literalura.DTO.DadosAutor;
import com.alura.literalura.DTO.DadosLivro;
import com.alura.literalura.DTO.DadosResultado;
import com.alura.literalura.modelo.Autor;
import com.alura.literalura.modelo.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConverteDados conversor = new ConverteDados();
    private final Scanner leitura = new Scanner(System.in);

    private static final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibirMenu() {
        int opcaoEscolhida = -1;

        while (opcaoEscolhida != 0) {
            System.out.println("""
                   \s
                    ===== MENU =====
                    1 - Buscar livros por título
                    2 - Listar livros buscados
                    3 - Listar autores vivos no ano escolhido
                    4 - Listar livros por idioma
                    0 - Sair
                    Escolha:\s
                   \s""");

            opcaoEscolhida = leitura.nextInt();
            leitura.nextLine();

            switch (opcaoEscolhida) {
                case 1 -> buscarLivro();
                case 2 -> listarLivros();
                case 3 -> listarAutoresVivos();
                case 4 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Até a próxima!");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void buscarLivro() {
        System.out.print("Digite o nome do livro: ");
        String titulo = leitura.nextLine();

        String url = URL_BASE + "?search=" + titulo.replace(" ", "%20");
        String json = consumoAPI.obterDados(url);

        DadosResultado resultado = conversor.obterDados(json, DadosResultado.class);

        if (resultado != null && !resultado.resultados().isEmpty()) {
            DadosLivro dadosLivro = resultado.resultados().get(0);

            DadosAutor dadosAutor = dadosLivro.authors().get(0);
            Autor autor = new Autor(dadosAutor.nome(), dadosAutor.anoDeNascimento(), dadosAutor.anoDeFalecimento());
            autorRepository.save(autor);

            Livro livro = new Livro();
            livro.setTitulo(dadosLivro.titulo());
            livro.setIdioma(dadosLivro.idiomas().get(0));
            livro.setNumeroDownloads(dadosLivro.dowloands());
            livro.setAutor(autor);

            livroRepository.save(livro);

            System.out.println("Livro salvo com sucesso!");
        } else {
            System.out.println(" Nenhum livro encontrado.");
        }
    }

    private void listarLivros() {
        var livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.print("Digite o ano desejado: ");
        int ano = leitura.nextInt();
        leitura.nextLine();

        var autores = autorRepository.findAll()
                .stream()
                .filter(a -> a.getAnoFalecimento() == null || a.getAnoFalecimento() > ano)
                .toList();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.print("Digite o idioma (ex: 'en', 'pt', 'es'): ");
        String idioma = leitura.nextLine();

        var livros = livroRepository.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma.");
        } else {
            livros.forEach(System.out::println);
        }
    }
}
