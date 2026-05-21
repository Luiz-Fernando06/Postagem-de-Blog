package service;

import model.Post;
import model.Usuario;
import java.util.List;

import java.time.LocalDate;

public class PostService {

    private UsuarioRepository usuarioRepository;
    private PostRepository postRepository;

    public boolean criarPost(long idAutor, String titulo, String conteudo) {

        Usuario autor = usuarioRepository.buscarId(idAutor);
        if (autor == null) return false;

        Post post = new Post(autor, titulo, conteudo);
        post.setDtCriacao(LocalDate.now());

        postRepository.salvar(post);

        return true;
    }

    public boolean editarPost(long idPost, String titulo, String conteudo) {

        Post post = postRepository.buscarId(idPost);
        if (post == null) return false;

        post.setTitulo(titulo);
        post.setConteudo(conteudo);

        postRepository.salvar(post);
        return true;
    }

    public Post buscarPostPorId(long idPost) {

        Post post = postRepository.buscarId(idPost);
        if (post == null) return null;

        return post;
    }

    public List<Post> buscarPostPorTitulo(String titulo) {

        return postRepository.buscarTitulo(titulo);
    }

    public List<Post> listarPosts() {

        return postRepository.listarPosts();
    }

    public boolean deletarPost(long idPost) {

        Post post = postRepository.buscarId(idPost);
        if (post == null) return false;

        postRepository.remover(post);
        return true;
    }
}
