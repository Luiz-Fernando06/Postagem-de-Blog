package model;

/**
 * Representa uma curtida única de um usuário em um post ou comentário.
 */
public class Curtidas {

    private long id;
    private Usuario autor;
    private Curtivel curtivel;

    public Curtidas(Usuario autor, Curtivel curtivel) {
        this.autor = autor;
        this.curtivel = curtivel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Curtivel getCurtivel() {
        return curtivel;
    }

    public void setCurtivel(Curtivel curtivel) {
        this.curtivel = curtivel;
    }
}
