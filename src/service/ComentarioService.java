package service;

import model.Comentario;
import model.Post;
import model.Usuario;
import repository.ComentarioRepository;
import repository.PostRepository;
import repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Camada de regra de negócio para criação, edição, listagem e remoção de comentários.
 */
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PostRepository postRepository;
    private CurtidasService curtidasService;

    public ComentarioService(ComentarioRepository comentarioRepository,
                             UsuarioRepository usuarioRepository,
                             PostRepository postRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.postRepository = postRepository;
    }

    public ComentarioService(ComentarioRepository comentarioRepository,
                             UsuarioRepository usuarioRepository,
                             PostRepository postRepository,
                             CurtidasService curtidasService) {
        this(comentarioRepository, usuarioRepository, postRepository);
        this.curtidasService = curtidasService;
    }

    public boolean criarComentario(long idAutor, long idPost, String conteudo ) {
        if (campoInvalido(conteudo)) {
            return false;
        }

        Usuario autor = usuarioRepository.buscarId(idAutor);
        if (autor == null) return false;

        Post post = postRepository.buscarId(idPost);
        if (post == null) return false;

        Comentario comentario = new Comentario(autor, post, conteudo.trim());
        comentario.setDataCriacao(LocalDate.now());

        comentarioRepository.salvar(comentario);
        return true;
    }

    public boolean editarComentario(long idComentario, long idAutor, String conteudo) {
        if (campoInvalido(conteudo)) {
            return false;
        }

        Comentario comentario = comentarioRepository.buscarId(idComentario);
        if (comentario == null || comentario.getAutor().getId() != idAutor) {
            return false;
        }

        comentario.setConteudo(conteudo.trim());
        comentarioRepository.salvar(comentario);
        return true;
    }

    public Comentario buscarComentario(long id) {
        Comentario comentario = comentarioRepository.buscarId(id);
        if (comentario == null) return null;
        return comentario;
    }

    public List<Comentario> listarComentariosDoPost(long idPost) {
        Post post = postRepository.buscarId(idPost);
        if (post == null) return new ArrayList<>();
        return comentarioRepository.listarComentariosDoPost(post);
    }

    public boolean deletarComentario(long id, long idAutor) {
        Comentario comentario = comentarioRepository.buscarId(id);
        if (comentario == null || comentario.getAutor().getId() != idAutor) return false;

        if (curtidasService != null) {
            curtidasService.removerCurtidas(comentario);
        }

        comentarioRepository.remover(comentario);
        return true;
    }

    private boolean campoInvalido(String valor) {
        return valor == null || valor.isBlank();
    }
}
