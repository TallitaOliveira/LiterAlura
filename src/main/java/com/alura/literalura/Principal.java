package com.alura.literalura;

import com.alura.literalura.service.LivroService;

import java.util.Scanner;

public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final LivroService livroService = new LivroService();

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Buscar livros por título");
            System.out.println("2 - Listar livros buscados");
            System.out.println("3 - Listar autores vivos no ano escolhido");
            System.out.println("4 - Listar livros por idioma");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o título do livro: ");
                    var titulo = scanner.nextLine();
                    livroService.buscarLivro(titulo);
                }
                case 2 -> livroService.listarLivros();
                case 3 -> {
                    System.out.print("Digite o ano: ");
                    var ano = scanner.nextInt();
                    scanner.nextLine();
                    livroService.listarAutoresVivos(ano);
                }
                case 4 -> {
                    System.out.print("Digite o idioma (ex: en, pt): ");
                    var idioma = scanner.nextLine();
                    livroService.listarLivrosPorIdioma(idioma);
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}
