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

import java.util.List;
import java.util.Optional;
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
            exibirOpcoesMenu();

            try {
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
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida. Tente novamente.");
                leitura.nextLine(); // Limpa o buffer
            }
        }
    }

    private void exibirOpcoesMenu() {
        System.out.println("""
               \n===== MENU =====
               1 - Buscar livros por título
               2 - Listar livros buscados
               3 - Listar autores vivos no ano escolhido
               4 - Listar livros por idioma
               0 - Sair
               Escolha:""");
    }

    private void buscarLivro() {
        System.out.print("Digite o nome do livro: ");
        String titulo = leitura.nextLine();

        try {
            String url = URL_BASE + "?search=" + titulo.replace(" ", "%20");
            String json = consumoAPI.obterDados(url);
            DadosResultado resultado = conversor.obterDados(json, DadosResultado.class);

            if (resultado == null || resultado.resultados() == null || resultado.resultados().isEmpty()) {
                System.out.println("Nenhum livro encontrado.");
                return;
            }

            DadosLivro dadosLivro = resultado.resultados().get(0);
            processarDadosLivro(dadosLivro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
    }

    private void processarDadosLivro(DadosLivro dadosLivro) {

        Autor autor = processarAutor(dadosLivro);

        // Criar e salvar livro
        Livro livro = new Livro();
        livro.setTitulo(dadosLivro.titulo() != null ? dadosLivro.titulo() : "Título desconhecido");


        String idioma = "en"; // padrão
        if (dadosLivro.idiomas() != null && !dadosLivro.idiomas().isEmpty()) {
            idioma = dadosLivro.idiomas().get(0);
        }
        livro.setIdioma(idioma);

        livro.setDownloads(dadosLivro.download() != null ? dadosLivro.download() : 0);


        if (autor != null) {
            livro.setAutores(List.of(autor));
        }

        livroRepository.save(livro);
        System.out.println("Livro salvo com sucesso: " + livro.getTitulo());
    }

    private Autor processarAutor(DadosLivro dadosLivro) {
        if (dadosLivro.autores() == null || dadosLivro.autores().isEmpty()) {
            return null;
        }

        DadosAutor dadosAutor = dadosLivro.autores().get(0);
        Optional<Autor> autorExistente = autorRepository.findByNome(dadosAutor.nome());

        if (autorExistente.isPresent()) {
            return autorExistente.get();
        }

        Autor autor = new Autor();
        autor.setNome(dadosAutor.nome() != null ? dadosAutor.nome() : "Autor desconhecido");


        autor.setAnoNascimento(dadosAutor.anoDeNascimento() != null ?
                Integer.valueOf(dadosAutor.anoDeNascimento()) : null);
        autor.setAnoFalecimento(dadosAutor.anoDeFalecimento() != null ?
                Integer.valueOf(dadosAutor.anoDeFalecimento()) : null);

        autorRepository.save(autor);
        return autor;
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado ainda.");
            return;
        }

        System.out.println("\n=== LIVROS CADASTRADOS ===");
        livros.forEach(livro -> {
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Downloads: " + livro.getDownloads());
            if (!livro.getAutores().isEmpty()) {
                System.out.println("Autor(es): " +
                        livro.getAutores().stream()
                                .map(Autor::getNome)
                                .reduce((a, b) -> a + ", " + b)
                                .orElse("N/A"));
            }
            System.out.println("----------------------");
        });
    }

    private void listarAutoresVivos() {
        System.out.print("Digite o ano desejado: ");
        try {
            int ano = leitura.nextInt();
            leitura.nextLine();

            List<Autor> autores = autorRepository.findAll()
                    .stream()
                    .filter(a -> a.getAnoFalecimento() == null || a.getAnoFalecimento() > ano)
                    .toList();

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo no ano " + ano);
            } else {
                System.out.println("\n=== AUTORES VIVOS EM " + ano + " ===");
                autores.forEach(autor -> {
                    System.out.println("Nome: " + autor.getNome());
                    System.out.println("Nascimento: " + autor.getAnoNascimento());
                    System.out.println("Falecimento: " +
                            (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Ainda vivo"));
                    System.out.println("----------------------");
                });
            }
        } catch (Exception e) {
            System.out.println("Ano inválido. Digite um número válido.");
            leitura.nextLine();
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.print("Digite o idioma (ex: 'en', 'pt', 'es'): ");
        String idioma = leitura.nextLine().toLowerCase();

        List<Livro> livros = livroRepository.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma '" + idioma + "'");
        } else {
            System.out.println("\n=== LIVROS NO IDIOMA " + idioma.toUpperCase() + " ===");
            livros.forEach(livro -> {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Downloads: " + livro.getDownloads());
                System.out.println("----------------------");
            });
        }
    }
}