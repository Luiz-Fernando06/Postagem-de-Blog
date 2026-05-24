package controller;

import app.Utilitarios;
import model.Post;
import service.PostService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static controller.UsuarioController.usuarioLogado;

/**
 * Controla as entradas do usuário relacionadas às postagens.
 */
public class PostController {

    private final Scanner LER;
    private final PostService POSTSERVICE;

    public PostController(PostService postService, Scanner ler) {
        this.POSTSERVICE = postService;
        this.LER = ler;
    }
    public void criarPost() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        System.out.print("Titulo do post: ");
        String titulo = LER.nextLine();

        System.out.print("Conteudo do post: ");
        String conteudo = LER.nextLine();

        boolean sucesso = POSTSERVICE.criarPost(usuarioLogado.getId(), titulo, conteudo);
        System.out.println(sucesso ? "Post criado!" : "Não foi possivel criar o post.");
    }

    public void listarPosts() {
        Utilitarios.limparTela();

        List<Post> posts = POSTSERVICE.listarPosts();

        if (posts.isEmpty()) {
            System.out.println("Nenhum post.");
            return;
        }

        for (Post post : posts) {
            exibirPost(post);
        }

        Utilitarios.linha();
    }

    public void editarPost() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faça login!");
            return;
        }

        List<Post> meusPosts = listarPostsDoUsuarioLogado();

        if (meusPosts.isEmpty()) {
            System.out.println("Nenhum post.");
            return;
        }

        Post postEscolhido = escolherPost(meusPosts, "Escolha um post: ");
        if (postEscolhido == null) return;

        boolean editando = true;

        do {
            System.out.println("O que deseja editar: ");
            System.out.println(" 1 - Titulo");
            System.out.println(" 2 - Conteudo");
            System.out.println(" 3 - Tudo");
            System.out.println(" 4 - Sair");

            int opcao = Utilitarios.lerInteiro("Opcao: ");

            switch (opcao) {
                case 1:
                    System.out.println("Novo Titulo: ");
                    String titulo = LER.nextLine();

                    boolean sucesso1 = POSTSERVICE.editarTitulo(postEscolhido.getId(), titulo);

                    if (sucesso1) {
                        System.out.println("Titulo atualizado!");
                    }

                    break;

                case 2:
                    System.out.println("Novo Conteudo: ");
                    String conteudo = LER.nextLine();

                    boolean sucesso2 = POSTSERVICE.editarConteudo(postEscolhido.getId(), conteudo);

                    if (sucesso2) {
                        System.out.println("Conteudo atualizado!");
                    }

                    break;

                case 3:
                    System.out.println("Novo Titulo: ");
                    String novoTitulo = LER.nextLine();

                    System.out.println("Novo Conteudo: ");
                    String novoConteudo = LER.nextLine();

                    boolean sucesso4 = POSTSERVICE.editarPost(
                            postEscolhido.getId(),
                            novoTitulo,
                            novoConteudo
                    );

                    if (sucesso4) {
                        System.out.println("Post atualizado!");
                    }

                    break;

                case 4:
                    editando = false;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (editando);
    }

    public void buscarPost() {
        System.out.println("Escolha uma forma de buscar o Post: ");
        System.out.println(" 1 - Buscar por ID");
        System.out.println(" 2 - Buscar por Titulo");
        System.out.println(" 3 - Voltar");
        int escolha = Utilitarios.lerInteiro("Opção: ");

        switch (escolha) {
            case 1:
                System.out.print("ID do post: ");
                long id = Long.parseLong(LER.nextLine());

                Post post1 = POSTSERVICE.buscarPostPorId(id);

                if (post1 == null) {
                    System.out.println("Post não encontrado!");
                    return;
                }

                System.out.println("[" +post1.getId()+ "] " + post1.getTitulo());
                System.out.println("Autor: " + post1.getAutor().getNome());
                System.out.println("Curtidas: " + post1.getQtdCurtidas());
                System.out.println(post1.getConteudo());

                break;

            case 2:
                System.out.println("Titulo do post: ");
                String titulo = LER.nextLine();

                List<Post> posts = POSTSERVICE.buscarPostPorTitulo(titulo);

                if (posts == null || posts.isEmpty()) {
                    System.out.println("Nenhum post encontrado com esse titulo!");
                    return;
                }

                for (int i = 0; i < posts.size(); i++) {
                    Utilitarios.linha();
                    Post post2 = posts.get(i);
                    System.out.println("[" + post2.getId() + "] " + post2.getTitulo());
                    System.out.println("Autor: " + post2.getAutor().getNome());
                    System.out.println("Curtidas: " + post2.getQtdCurtidas());
                    System.out.println(post2.getConteudo());
                }

                Utilitarios.linha();
                break;

            case 3:
                return;

            default:
                System.out.println("Opção inválida!");
        }

    }

    public void deletarPost() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Faca login!");
            return;
        }

        List<Post> meusPosts = listarPostsDoUsuarioLogado();

        if (meusPosts.isEmpty()) {
            System.out.println("Nenhum post.");
            return;
        }
        Post postEscolhido = escolherPost(meusPosts, "Escolha um Post para deletar: ");
        if (postEscolhido == null) return;

        if (postEscolhido.getAutor().getId() != usuarioLogado.getId()) {
            System.out.println("Voce nao pode deletar esse post!");
            return;
        }

        System.out.println("Tem certeza que deseja deletar esse post? [S/N]");
        String resposta = LER.nextLine();

        if (!resposta.equalsIgnoreCase("s")) {
            System.out.println("Operacao cancelada.");
            return;
        }

        boolean sucesso = POSTSERVICE.deletarPost(postEscolhido.getId());

        if (sucesso) {
            System.out.println("Post deletado!");
        } else {
            System.out.println("Erro ao deletar post.");
        }
    }

    private void exibirPost(Post post) {
        Utilitarios.linha();
        System.out.println("[ID " + post.getId() + "] " + post.getTitulo());
        System.out.println("Autor: " + post.getAutor().getNome());
        System.out.println("Criado em: " + post.getDtCriacao());
        System.out.println("Curtidas: " + post.getQtdCurtidas());
        System.out.println(post.getConteudo());
    }

    private List<Post> listarPostsDoUsuarioLogado() {
        List<Post> meusPosts = new ArrayList<>();
        List<Post> posts = POSTSERVICE.listarPosts();

        for (Post post : posts) {
            meusPosts.add(post);
        }

        for (int i = 0; i < meusPosts.size(); i++) {
            Post post = meusPosts.get(i);
            System.out.println("[" + (i + 1) + "] " + post.getTitulo());
        }

        return meusPosts;
    }

    private Post escolherPost(List<Post> posts, String mensagem) {
        int escolha = Utilitarios.lerInteiro(mensagem);

        if (escolha < 1 || escolha > posts.size()) {
            System.out.println("Opcao invalida!");
            return null;
        }
        return posts.get(escolha - 1);
    }
}