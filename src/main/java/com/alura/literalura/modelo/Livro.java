package com.alura.literalura.modelo;

import com.alura.literalura.DTO.DadosLivro;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")


public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer downloads;

    @ManyToMany(fetch= FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    public Livro(){
    }

    public Livro(DadosLivro dadosLivro, List<Autor> autores){
        this.titulo = dadosLivro.titulo();
        this.idioma = dadosLivro.idiomas().get(0);
        this.downloads = dadosLivro.download();
        this.autores = autores;
    }




    public Livro(String titulo, String idioma, Integer downloads, List<Autor> autores) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.downloads = downloads;
        this.autores = autores;
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

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public List <Autor> getAutores() {
        return autores;
    }

    public void setAutores(List <Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", downloads=" + downloads +
                ", autores=" + autores +
                '}';
    }
}
