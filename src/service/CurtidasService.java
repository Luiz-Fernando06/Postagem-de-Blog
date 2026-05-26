package service;

import model.Curtidas;
import model.Curtivel;
import model.Usuario;
import model.Comentario;
import model.Post;
import repository.ComentarioRepository;
import repository.PostRepository;
import repository.CurtidasRepository;
import repository.UsuarioRepository;
import java.util.List;

/**
 * Camada de regra de negócio para alternar curtidas em posts e comentários.
 */
public class CurtidasService {

    private UsuarioRepository usuarioRepository;
    private CurtidasRepository curtidasRepository;
    private PostRepository postRepository;
    private ComentarioRepository comentarioRepository;

    public CurtidasService(UsuarioRepository usuarioRepository, CurtidasRepository curtidasRepository,
                           PostRepository postRepository, ComentarioRepository comentarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.curtidasRepository = curtidasRepository;
        this.postRepository = postRepository;
        this.comentarioRepository = comentarioRepository;

    }

    public boolean toggleCurtir(long idAutor, Curtivel curtivel) {
        Usuario autor = usuarioRepository.buscarId(idAutor);

        if (autor == null || curtivel == null) return false;

        Curtidas curtidaExistente = curtidasRepository.buscarCurtidas(autor, curtivel);

        if (curtidaExistente != null) {
            curtidasRepository.remover(curtidaExistente);
            curtivel.setQtdCurtidas(curtivel.getQtdCurtidas() - 1);
            atualizarQtdCurtidas(curtivel);
            return true;
        }

        Curtidas curtida = new Curtidas(autor, curtivel);
        curtidasRepository.salvar(curtida);
        curtivel.setQtdCurtidas(curtivel.getQtdCurtidas() + 1);
        atualizarQtdCurtidas(curtivel);

        return true;
    }

    public void removerCurtidas(Curtivel curtivel) {
        if (curtivel == null) {
            return;
        }

        List<Curtidas> curtidas = curtidasRepository.buscarCurtidas(curtivel);

        for (Curtidas curtida : curtidas) {
            curtidasRepository.remover(curtida);
        }

        curtivel.setQtdCurtidas(0);
        atualizarQtdCurtidas(curtivel);
    }

    private void atualizarQtdCurtidas(Curtivel curtivel) {
        if (curtivel instanceof Post && postRepository != null) {
            postRepository.salvar((Post) curtivel);
        }

        if (curtivel instanceof Comentario && comentarioRepository != null) {
            comentarioRepository.salvar((Comentario) curtivel);
        }
    }
}
