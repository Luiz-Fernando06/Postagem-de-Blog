package controller;

import app.Utilitarios;
import model.Comentario;
import model.Post;
import service.ComentarioService;
import service.CurtidasService;
import service.PostService;
import java.util.List;
import java.util.Scanner;
import static controller.UsuarioController.usuarioLogado;

/**
 * Controla as entradas do usuário para curtir ou descurtir posts e comentários.
 */
public class CurtidasController {

    private final Scanner LER;
    private final CurtidasService CURTIDASSERVICE;
    private final PostService POSTSERVICE;
    private final ComentarioService COMENTARIOSERVICE;

    public CurtidasController(CurtidasService curtidasService, PostService postService, ComentarioService comentarioService, Scanner ler) {
        this.CURTIDASSERVICE = curtidasService;
        this.POSTSERVICE = postService;
        this.COMENTARIOSERVICE = comentarioService;
        this.LER = ler;
    }

    public void toggleCurtirPost() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        Post post = escolherPost("Escolha um post para curtir/descurtir: ");
        if (post == null) return;

        boolean sucesso = CURTIDASSERVICE.toggleCurtir(usuarioLogado.getId(), post);

        if (sucesso) {
            System.out.println("Acao realizada. Curtidas do post: " + post.getQtdCurtidas());
        } else {
            System.out.println("Nao foi possivel realizar a acao.");
        }
    }

    public void toggleCurtirComentario() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        Comentario comentarios = escolherComentarioDoPost("Escolha o comentario: ");
        if (comentarios == null) return;

        boolean sucesso = CURTIDASSERVICE.toggleCurtir(usuarioLogado.getId(), comentarios);

        if (sucesso) {
            System.out.println("Acao realizada. Curtidas do comentario: " + comentarios.getQtdCurtidas());
        } else {
            System.out.println("Nao foi possivel realizar a acao.");
        }
    }

    private Post escolherPost(String mensagem) {
        List<Post> posts = POSTSERVICE.listarPosts();

        if (posts.isEmpty()) {
            System.out.println("Nenhum post.");
            return null;
        }

        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            Utilitarios.linha();
            System.out.println("[" + (i + 1) + "] " + post.getTitulo() + "\n - Curtidas: " + post.getQtdCurtidas());
        }

        Utilitarios.linha();

        int escolha = Utilitarios.lerInteiro(mensagem);

        if (escolha < 1 || escolha > posts.size()) {
            System.out.println("Opcao invalida!");
            return null;
        }

        return posts.get(escolha - 1);
    }

    private Comentario escolherComentarioDoPost(String mensagem) {
        Post posts = escolherPost("Escolha um Post: ");

        List<Comentario> comentarios = COMENTARIOSERVICE.listarComentariosDoPost(posts.getId());

        if (comentarios.isEmpty()) {
            System.out.println("Nenhum comentario nesse post.");
            return null;
        }

        for (int i = 0; i < comentarios.size(); i++) {
            Comentario comentario = comentarios.get(i);
            Utilitarios.linha();
            System.out.println("[" + (i + 1) + "] " + comentario.getConteudo());
            System.out.println("Autor: " + comentario.getAutor().getNome());
            System.out.println("Curtidas: " + comentario.getQtdCurtidas());
        }

        Utilitarios.linha();

        int escolha = Utilitarios.lerInteiro(mensagem);

        if (escolha < 1 || escolha > comentarios.size()) {
            System.out.println("Opcao invalida!");
            return null;
        }

        return comentarios.get(escolha - 1);
    }
}
