package com.alura.literalura.modelo;

import com.alura.literalura.DTO.DadosLivro;
import jakarta.persistence.*;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer numeroDownloads;

    @ManyToOne
    private Autor autor;

    public Livro(){

    }

    public Livro(DadosLivro dadosLivro, Autor autor){
        this.titulo = dadosLivro.titulo();
        this.autor = autor;
        this.idioma = dadosLivro.idiomas().get(0);
        this.numeroDownloads = dadosLivro.dowloands();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", downloads=" + numeroDownloads +
                ", autor=" + (autor != null ? autor.getNome() : "Desconhecido") +
                '}';
    }
}
