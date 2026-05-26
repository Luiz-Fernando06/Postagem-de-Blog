package service;

import model.Usuario;
import repository.UsuarioRepository;
import java.time.LocalDate;

/**
 * Camada de regra de negócio para cadastro, login e edição de usuários.
 */
public class UsuarioService {

    private final UsuarioRepository USUARIOREPOSITORY;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.USUARIOREPOSITORY = usuarioRepository;
    }

    public Boolean cadastro(String nome, String email, String senha) {
        if (campoInvalido(nome) || campoInvalido(email) || campoInvalido(senha)) {
            return false;
        }

        Usuario usuarioExistente = USUARIOREPOSITORY.buscarPorEmail(email.trim());
        if (usuarioExistente != null) {
            return false;
        }

        Usuario user = new Usuario();
        user.setNome(nome.trim());
        user.setEmail(email.trim());
        user.setSenha(senha);
        user.setDataCriacao(LocalDate.now());

        USUARIOREPOSITORY.salvar(user);
        return true;
    }

    public Usuario login(String email, String senha) {
        if (campoInvalido(email) || campoInvalido(senha)) {
            return null;
        }

        Usuario user = USUARIOREPOSITORY.buscarPorEmail(email.trim());
        if (user == null || !senha.equals(user.getSenha())) return null;

        return user;
    }

    public boolean editarConta(long id, String nome, String email, String senha) {
        if (campoInvalido(nome) || campoInvalido(email) || campoInvalido(senha)) {
            return false;
        }

    	Usuario user = USUARIOREPOSITORY.buscarId(id);
        if (user == null) return false;

        Usuario usuarioComMesmoEmail = USUARIOREPOSITORY.buscarPorEmail(email.trim());
        if (usuarioComMesmoEmail != null && usuarioComMesmoEmail.getId() != id) return false;

    	user.setNome(nome.trim());
    	user.setEmail(email.trim());
    	user.setSenha(senha);

        USUARIOREPOSITORY.salvar(user);
    	return true;
    }

    public boolean editarNome(long id, String nome) {
        if (campoInvalido(nome)) return false;

        Usuario user = USUARIOREPOSITORY.buscarId(id);
        if (user == null) return false;

        user.setNome(nome.trim());
        USUARIOREPOSITORY.salvar(user);
        return true;
    }

    public boolean editarEmail(long id, String email) {
        if (campoInvalido(email)) return false;

        Usuario user = USUARIOREPOSITORY.buscarId(id);
        if (user == null) return false;

        Usuario usuarioComMesmoEmail = USUARIOREPOSITORY.buscarPorEmail(email.trim());
        if (usuarioComMesmoEmail != null && usuarioComMesmoEmail.getId() != id) {
            return false;
        }

        user.setEmail(email.trim());
        USUARIOREPOSITORY.salvar(user);
        return true;
    }

    public boolean editarSenha(long id, String senha) {
        if (campoInvalido(senha)) return false;

        Usuario user = USUARIOREPOSITORY.buscarId(id);
        if (user == null) return false;

        user.setSenha(senha);
        USUARIOREPOSITORY.salvar(user);
        return true;
    }

    private boolean campoInvalido(String valor) {
        return valor == null || valor.isBlank();
    }
}
