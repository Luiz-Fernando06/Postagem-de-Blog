package controller;

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

    private final Scanner ler;
    private final CurtidasService curtidasService;
    private final PostService postService;
    private final ComentarioService comentarioService;

    public CurtidasController(CurtidasService curtidasService,
                              PostService postService,
                              ComentarioService comentarioService,
                              Scanner ler) {
        this.curtidasService = curtidasService;
        this.postService = postService;
        this.comentarioService = comentarioService;
        this.ler = ler;
    }

    public void curtirOuDescurtirPost() {
        Main.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        Post post = escolherPost("Escolha um post para curtir/descurtir: ");
        if (post == null) {
            return;
        }

        boolean sucesso = curtidasService.toggleCurtir(usuarioLogado.getId(), post);

        if (sucesso) {
            System.out.println("Acao realizada. Curtidas do post: " + post.getQtdCurtidas());
        } else {
            System.out.println("Nao foi possivel realizar a acao.");
        }
    }

    public void curtirOuDescurtirComentario() {
        Main.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        Post post = escolherPost("Escolha o post do comentario: ");
        if (post == null) {
            return;
        }

        List<Comentario> comentarios = comentarioService.listarComentariosDoPost(post.getId());

        if (comentarios.isEmpty()) {
            System.out.println("Nenhum comentario nesse post.");
            return;
        }

        for (int i = 0; i < comentarios.size(); i++) {
            Comentario comentario = comentarios.get(i);
            Main.linha();
            System.out.println("[" + (i + 1) + "] " + comentario.getConteudo());
            System.out.println("Autor: " + comentario.getAutor().getNome());
            System.out.println("Curtidas: " + comentario.getQtdCurtidas());
        }
        Main.linha();

        int escolha = lerInteiro("Escolha um comentario para curtir/descurtir: ");

        if (escolha < 1 || escolha > comentarios.size()) {
            System.out.println("Opcao invalida!");
            return;
        }

        Comentario comentario = comentarios.get(escolha - 1);
        boolean sucesso = curtidasService.toggleCurtir(usuarioLogado.getId(), comentario);

        if (sucesso) {
            System.out.println("Acao realizada. Curtidas do comentario: " + comentario.getQtdCurtidas());
        } else {
            System.out.println("Nao foi possivel realizar a acao.");
        }
    }

    private Post escolherPost(String mensagem) {
        List<Post> posts = postService.listarPosts();

        if (posts.isEmpty()) {
            System.out.println("Nenhum post.");
            return null;
        }

        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            System.out.println("[" + (i + 1) + "] " + post.getTitulo() + " - Curtidas: " + post.getQtdCurtidas());
        }

        int escolha = lerInteiro(mensagem);

        if (escolha < 1 || escolha > posts.size()) {
            System.out.println("Opcao invalida!");
            return null;
        }

        return posts.get(escolha - 1);
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);

        try {
            return Integer.parseInt(ler.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
