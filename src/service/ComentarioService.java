package service;

import model.Comentario;
import model.Post;
import model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class ComentarioService {

    public boolean criarComentario(long idAutor, long idPost, String conteudo ) {

        Usuario autor = UsuarioRepository.buscarId(idAutor);
        if (autor == null) return false;

        Post post = PostRepository.buscarId(idPost);
        if (post == null) return false;

        Comentario comentario = new Comentario(autor, post, conteudo);

        comentario.setDataCriacao(LocalDate.now());
        repository.salvar(comentario);

        return true;
    }

    public boolean editarComentario(long idComentario, String conteudo) {

        Comentario comentario = repository.buscarId(idComentario);
        if (comentario == null) return false;


        comentario.setConteudo(conteudo);
        repository.salvar(comentario);

        return true;
    }

    public Comentario buscarComentario(long id) {

        Comentario comentario = repository.buscarId(id);
        if (comentario == null) return null;

        return comentario;
    }

    public List<Comentario> listarComentariosDoPost(long idPost) {

        Post post = PostRepository.buscarId(idPost);
        if (post == null) return null;

        return repository.listarTodosComentarios();
    }

    public boolean deletarComentario(long id) {
        Comentario comentario = repository.buscarId(id);
        if (comentario == null) return false;

        repository.remover(comentario);
        return true;
    }
}
