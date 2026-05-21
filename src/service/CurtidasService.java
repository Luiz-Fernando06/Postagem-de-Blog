package service;

import model.Curtidas;
import model.Curtivel;
import model.Usuario;
import java.util.List;

public class CurtidasService {

    private UsuarioRepository usuarioRepository;
    private CurtidasRepository curtidasRepository;

    public boolean curtirOuDescurtir(long idAutor, Curtivel curtivel) {

        Usuario autor = usuarioRepository.buscarId(idAutor);

        if (autor == null || curtivel == null) return false;

        Curtidas curtidaExistente = curtidasRepository.buscarCurtidas(autor, curtivel);

        if (curtidaExistente != null) {

            curtidasRepository.remover(curtidaExistente);

            curtivel.setQtdCurtidas(curtivel.getQtdCurtidas() - 1);

            return true;
        }

        Curtidas curtida = new Curtidas(autor, curtivel);

        curtidasRepository.salvar(curtida);

        curtivel.setQtdCurtidas(curtivel.getQtdCurtidas() + 1);

        return true;
    }

    public void removerCurtidas(Curtivel curtivel) {

        List<Curtidas> curtidas = curtidasRepository.buscarCurtidas(curtivel);

        for (Curtidas curtida : curtidas) {
            curtidasRepository.remover(curtida);
        }

        curtivel.setQtdCurtidas(0);
    }
}
