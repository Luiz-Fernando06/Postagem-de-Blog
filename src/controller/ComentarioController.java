package controller;

import app.Utilitarios;
import model.Comentario;
import model.Post;
import service.ComentarioService;
import service.PostService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static controller.UsuarioController.usuarioLogado;

/**
 * Controla as entradas do usuário relacionadas aos comentários dos posts.
 */
public class ComentarioController {

    private final Scanner LER;
    private final ComentarioService COMENTARIOSERVICE;
    private final PostService POSTSERVICE;

    public ComentarioController(ComentarioService comentarioService, PostService postService, Scanner ler) {
        this.COMENTARIOSERVICE = comentarioService;
        this.POSTSERVICE = postService;
        this.LER = ler;
    }

    public void comentarPost() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        Post postEscolhido = escolherPost("Escolha um post para comentar: ");
        if (postEscolhido == null) {
            return;
        }

        System.out.print("Comentario: ");
        String comentario = LER.nextLine();

        boolean sucesso = COMENTARIOSERVICE.criarComentario(usuarioLogado.getId(), postEscolhido.getId(), comentario);
        System.out.println(sucesso ? "Comentario criado!" : "Nao foi possivel criar o comentario.");
    }

    public void listarComentariosDoPost() {
        Utilitarios.limparTela();

        Post postEscolhido = escolherPost("Escolha um post para ver os comentarios: ");
        if (postEscolhido == null) {
            return;
        }

        List<Comentario> comentarios = COMENTARIOSERVICE.listarComentariosDoPost(postEscolhido.getId());

        if (comentarios.isEmpty()) {
            System.out.println("Nenhum comentario nesse post.");
            return;
        }

        exibirComentarios(comentarios);
    }

    public void editarComentario() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        Post postEscolhido = escolherPost("Escolha o post do comentario: ");
        if (postEscolhido == null) {
            return;
        }

        List<Comentario> meusComentarios = filtrarComentariosDoUsuario(postEscolhido.getId());

        if (meusComentarios.isEmpty()) {
            System.out.println("Voce nao possui comentarios nesse post.");
            return;
        }

        Comentario comentarioEscolhido = escolherComentario(meusComentarios, "Escolha um comentario: ");
        if (comentarioEscolhido == null) {
            return;
        }

        System.out.print("Novo comentario: ");
        String conteudo = LER.nextLine();

        boolean sucesso = COMENTARIOSERVICE.editarComentario(
                comentarioEscolhido.getId(),
                usuarioLogado.getId(),
                conteudo
        );

        System.out.println(sucesso ? "Comentario atualizado!" : "Nao foi possivel atualizar o comentario.");
    }

    public void deletarComentario() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        Post postEscolhido = escolherPost("Escolha o post do comentario: ");
        if (postEscolhido == null) {
            return;
        }

        List<Comentario> meusComentarios = filtrarComentariosDoUsuario(postEscolhido.getId());

        if (meusComentarios.isEmpty()) {
            System.out.println("Voce nao possui comentarios nesse post.");
            return;
        }

        Comentario comentarioEscolhido = escolherComentario(meusComentarios, "Escolha um comentario para deletar: ");
        if (comentarioEscolhido == null) {
            return;
        }

        System.out.print("Tem certeza que deseja deletar esse comentario? [S/N]: ");
        String resposta = LER.nextLine();

        if (!resposta.equalsIgnoreCase("s")) {
            System.out.println("Operacao cancelada.");
            return;
        }

        boolean sucesso = COMENTARIOSERVICE.deletarComentario(comentarioEscolhido.getId(), usuarioLogado.getId());
        System.out.println(sucesso ? "Comentario deletado!" : "Erro ao deletar comentario.");
    }

    private Post escolherPost(String mensagem) {
        List<Post> posts = POSTSERVICE.listarPosts();

        if (posts.isEmpty()) {
            System.out.println("Nenhum post.");
            return null;
        }

        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            System.out.println("[" + (i + 1) + "] " + post.getTitulo() + " - Autor: " + post.getAutor().getNome());
        }

        int escolha = Utilitarios.lerInteiro(mensagem);

        if (escolha < 1 || escolha > posts.size()) {
            System.out.println("Opcao invalida!");
            return null;
        }

        return posts.get(escolha - 1);
    }

    private List<Comentario> filtrarComentariosDoUsuario(long idPost) {
        List<Comentario> comentarios = COMENTARIOSERVICE.listarComentariosDoPost(idPost);
        List<Comentario> meusComentarios = new ArrayList<>();

        for (Comentario comentario : comentarios) {
            if (comentario.getAutor() != null && comentario.getAutor().getId() == usuarioLogado.getId()) {
                meusComentarios.add(comentario);
            }
        }

        exibirComentarios(meusComentarios);
        return meusComentarios;
    }

    private Comentario escolherComentario(List<Comentario> comentarios, String mensagem) {
        int escolha = Utilitarios.lerInteiro(mensagem);

        if (escolha < 1 || escolha > comentarios.size()) {
            System.out.println("Opcao invalida!");
            return null;
        }

        return comentarios.get(escolha - 1);
    }

    private void exibirComentarios(List<Comentario> comentarios) {
        for (int i = 0; i < comentarios.size(); i++) {
            Comentario comentario = comentarios.get(i);
            Utilitarios.linha();
            System.out.println("[" + (i + 1) + "] Comentario ID " + comentario.getId());
            System.out.println("Autor: " + comentario.getAutor().getNome());
            System.out.println("Criado em: " + comentario.getDataCriacao());
            System.out.println("Curtidas: " + comentario.getQtdCurtidas());
            System.out.println(comentario.getConteudo());
        }
        Utilitarios.linha();
    }
}
