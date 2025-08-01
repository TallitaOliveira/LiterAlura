package com.alura.literalura.modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer anoNascimento;

    private Integer anoFalecimento;

    @ManyToMany (mappedBy = "autores", cascade = CascadeType.ALL)
    private List<Livro> livros;

    public Autor(){

    }
    public Autor(String nome, String anoDeNascimento, String anoDeFalecimento) {
        this.nome = nome;
        this.anoNascimento = anoDeNascimento != null ? Integer.valueOf(anoDeNascimento) : null;
        this.anoFalecimento = anoDeFalecimento != null ? Integer.valueOf(anoDeFalecimento) : null;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<com.alura.literalura.modelo.Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<com.alura.literalura.modelo.Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nome='" + nome + '\'' +
                ", nascimento=" + anoNascimento +
                ", falecimento=" + anoFalecimento +
                '}';
    }
}
