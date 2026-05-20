package service;

import model.Post;
import model.Usuario;
import java.util.List;

import java.time.LocalDate;

public class PostService {

    public boolean criarPost(long idAutor, String titulo, String conteudo) {

        Usuario autor = UsuarioRepository.buscarId(idAutor);
        if (autor == null) return false;

        Post post = new Post(autor, titulo, conteudo);
        post.setDtCriacao(LocalDate.now());

        repository.salvar(post);

        return true;
    }

    public boolean editarPost(long idPost, String titulo, String conteudo) {

        Post post = repository.buscarId(idPost);
        if (post == null) return false;

        post.setTitulo(titulo);
        post.setConteudo(conteudo);

        repository.salvar(post);
        return true;
    }

    public Post buscarPostPorId(long idPost) {

        Post post = repository.buscarId(idPost);
        if (post == null) return null;

        return post;
    }

    public List<Post> buscarPostPorTitulo(String titulo) {

        return repository.buscarTitulo(titulo);
    }

    public List<Post> listarPosts() {

        return repository.listarPosts();
    }

    public boolean deletarPost(long idPost) {

        Post post = repository.buscarId(idPost);
        if (post == null) return false;

        repository.remover(post);
        return true;
    }
}
