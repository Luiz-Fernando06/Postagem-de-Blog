package model;

import java.time.LocalDate;

public class Comentario implements Curtivel{
    private long id;
    private String conteudo;
    private Usuario autor;
    private Post postagem;
    private LocalDate dataCriacao;
    private long qtdCurtidas;

    public Comentario(Usuario autor, Post postagem, String conteudo) {
        this.autor = autor;
        this.postagem = postagem;
        this.conteudo = conteudo;
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Post getPostagem() {
        return postagem;
    }

    public void setPostagem(Post postagem) {
        this.postagem = postagem;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public long getQtdCurtidas() {
        return qtdCurtidas;
    }

    public void setQtdCurtidas(long qtdCurtidas) {
        this.qtdCurtidas = qtdCurtidas;
    }
}
