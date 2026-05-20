package model;

import java.time.LocalDate;
import model.Usuario;

public class Post {

    private long id;
    private String titulo;
    private String conteudo;
    private LocalDate dtCriacao;
    private Usuario autor;

    public Post(Usuario autor, String titulo, String conteudo) {
        this.autor = autor;
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDate dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
}
