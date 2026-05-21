package service;

import model.Comentario;
import model.Post;
import model.Usuario;
import java.time.LocalDate;
import java.util.List;

public class ComentarioService {

    private ComentarioRepository comentarioRepository;
    private UsuarioRepository usuarioRepository;
    private PostRepository postRepository;

    public boolean criarComentario(long idAutor, long idPost, String conteudo ) {

        Usuario autor = usuarioRepository.buscarId(idAutor);
        if (autor == null) return false;

        Post post = postRepository.buscarId(idPost);
        if (post == null) return false;

        Comentario comentario = new Comentario(autor, post, conteudo);

        comentario.setDataCriacao(LocalDate.now());
        comentarioRepository.salvar(comentario);

        return true;
    }

    public boolean editarComentario(long idComentario, String conteudo) {

        Comentario comentario = comentarioRepository.buscarId(idComentario);
        if (comentario == null) return false;


        comentario.setConteudo(conteudo);
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
        if (post == null) return null;

        return comentarioRepository.listarComentariosDoPost(post);
    }

    public boolean deletarComentario(long id) {
        Comentario comentario = comentarioRepository.buscarId(id);
        if (comentario == null) return false;

        comentarioRepository.remover(comentario);
        return true;
    }
}
