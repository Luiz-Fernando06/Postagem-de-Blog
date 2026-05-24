package service;

import model.Post;
import model.Usuario;
import java.util.List;
import repository.ComentarioRepository;
import repository.PostRepository;
import repository.UsuarioRepository;
import java.time.LocalDate;

/**
 * Camada de regra de negócio para operações de posts.
 */
public class PostService {

    private final UsuarioRepository USUARIOREPOSITORY;
    private final PostRepository POSTREPOSITORY;
    private ComentarioRepository comentarioRepository;
    private CurtidasService curtidasService;

    public PostService(UsuarioRepository usuarioRepository, PostRepository postRepository) {
        this.USUARIOREPOSITORY = usuarioRepository;
        this.POSTREPOSITORY = postRepository;
    }

    public PostService(UsuarioRepository usuarioRepository,
                       PostRepository postRepository,
                       ComentarioRepository comentarioRepository,
                       CurtidasService curtidasService) {
        this(usuarioRepository, postRepository);
        this.comentarioRepository = comentarioRepository;
        this.curtidasService = curtidasService;
    }

    public boolean criarPost(long idAutor, String titulo, String conteudo) {
        if (campoInvalido(titulo) || campoInvalido(conteudo)) {
            return false;
        }

        Usuario autor = USUARIOREPOSITORY.buscarId(idAutor);
        if (autor == null) return false;

        Post post = new Post(autor, titulo.trim(), conteudo.trim());
        post.setDtCriacao(LocalDate.now());

        POSTREPOSITORY.salvar(post);
        return true;
    }

    public boolean editarPost(long idPost, String titulo, String conteudo) {
        if (campoInvalido(titulo) || campoInvalido(conteudo)) {
            return false;
        }

        Post post = POSTREPOSITORY.buscarId(idPost);
        if (post == null) return false;

        post.setTitulo(titulo.trim());
        post.setConteudo(conteudo.trim());

        POSTREPOSITORY.salvar(post);
        return true;
    }

    public boolean editarTitulo(long id, String titulo) {
        if (campoInvalido(titulo)) {
            return false;
        }
        Post post = POSTREPOSITORY.buscarId(id);
        if (post == null) return false;

        post.setTitulo(titulo.trim());
        POSTREPOSITORY.salvar(post);
        return true;
    }

    public boolean editarConteudo(long id, String conteudo) {
        if (campoInvalido(conteudo)) {
            return false;
        }
        Post post = POSTREPOSITORY.buscarId(id);
        if (post == null) return false;

        post.setConteudo(conteudo);
        POSTREPOSITORY.salvar(post);
        return true;
    }

    public Post buscarPostPorId(long idPost) {
        Post post = POSTREPOSITORY.buscarId(idPost);
        if (post == null) return null;
        return post;
    }

    public List<Post> buscarPostPorTitulo(String titulo) {
        return POSTREPOSITORY.buscarTitulo(titulo);
    }

    public List<Post> listarPosts() {
        return POSTREPOSITORY.listarPosts();
    }

    public boolean deletarPost(long idPost) {
        Post post = POSTREPOSITORY.buscarId(idPost);
        if (post == null) return false;

        if (comentarioRepository != null) {
            comentarioRepository.removerComentariosDoPost(post);
        }

        if (curtidasService != null) {
            curtidasService.removerCurtidas(post);
        }

        POSTREPOSITORY.remover(post);
        return true;
    }

    private boolean campoInvalido(String valor) {
        return valor == null || valor.isBlank();
    }
}
