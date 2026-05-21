package service;

import model.Usuario;
import java.time.LocalDate;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public Boolean cadastro(String nome, String email, String senha) {

        Usuario usuarioExistente = usuarioRepository.buscarPorEmail(email);

        if (usuarioExistente != null) {
            return false;
        }

        Usuario user = new Usuario();

        user.setNome(nome);
        user.setEmail(email);
        user.setSenha(senha);
        user.setDataCriacao(LocalDate.now());

        usuarioRepository.salvar(user);
        return true;
    }

    public Boolean login(String email, String senha) {

         Usuario user = usuarioRepository.buscarPorEmail(email);

        if (user == null || !senha.equals(user.getSenha())) {
            return false;
        }

        return true;
    }

    public boolean editarConta(long id, String nome, String email, String senha) {
    	
    	Usuario user = usuarioRepository.buscarId(id);

        if (user == null) return false;
			
    	user.setNome(nome);
    	user.setEmail(email);
    	user.setSenha(senha);

        usuarioRepository.salvar(user);
    	
    	return true;
    }
}
