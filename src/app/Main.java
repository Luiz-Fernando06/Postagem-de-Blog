package app;

import controller.UsuarioController;
import controller.PostController;
import controller.ComentarioController;
import controller.CurtidasController;

import repository.UsuarioRepository;
import repository.PostRepository;
import repository.ComentarioRepository;
import repository.CurtidasRepository;

import service.CurtidasService;
import service.UsuarioService;
import service.PostService;
import service.ComentarioService;

import java.util.Scanner;
import static controller.UsuarioController.usuarioLogado;

/**
 * Classe principal da aplicação. Monta as dependências e controla o menu geral.
 */
public class Main {
    private static Scanner ler = new Scanner(System.in);

    private static UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static PostRepository postRepository = new PostRepository();
    private static ComentarioRepository comentarioRepository = new ComentarioRepository();
    private static CurtidasRepository curtidasRepository = new CurtidasRepository();

    private static UsuarioService usuarioService = new UsuarioService(usuarioRepository);
    private static PostService postService = new PostService(usuarioRepository, postRepository);
    private static ComentarioService comentarioService = new ComentarioService(comentarioRepository, usuarioRepository, postRepository);
    private static CurtidasService curtidasService = new CurtidasService(usuarioRepository, curtidasRepository);

    private static UsuarioController usuarioController = new UsuarioController(usuarioService, ler);
    private static PostController postController = new PostController(postService, ler);
    private static ComentarioController comentarioController = new ComentarioController(comentarioService, postService, ler);
    private static CurtidasController curtidasController = new CurtidasController(curtidasService, postService, comentarioService, ler);


    public static void main(String[] args) {
        boolean executando = true;

        do {
            Utilitarios.linha();

            if (usuarioLogado == null) {
                System.out.println("Usuário Logado: nenhum");
            } else {
                System.out.println("Usuário Logado: "+ usuarioLogado.getNome());
            }

            Utilitarios.linha();

            exibirMenu();
            int escolha = Utilitarios.lerInteiro("Escolha uma opção: ");

            switch (escolha) {
                case 1:
                    Utilitarios.limparTela();
                    usuarioMenu();
                    break;

                case 2:
                    Utilitarios.limparTela();
                    postMenu();
                    break;

                case 3:
                    Utilitarios.limparTela();
                    comentarioMenu();
                    break;

                case 4:
                    Utilitarios.limparTela();
                    curtidasMenu();
                    break;

                case 0:
                    executando = false;
                    Utilitarios.limparTela();
                    System.out.println("Saindo do app....");
                    break;

                default:
                    Utilitarios.limparTela();
                    System.out.println("Opção inválida");
                    Utilitarios.pausar();

            }

        } while(executando);
    }

    public static void exibirMenu() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        📝  B L O G U I N H O  📝       ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║             MENU PRINCIPAL           ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  [1]  Menu Usuario                   ║");
        System.out.println("║  [2]  Menu Post                      ║");
        System.out.println("║  [3]  Menu Comentario                ║");
        System.out.println("║  [4]  Menu Curtidas                  ║");
        System.out.println("║  [0]  Sair                           ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    public static void usuarioMenu() {
        boolean executando = true;

        do {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║              MENU USUARIO            ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  [1]  Cadastrar Usuario              ║");
            System.out.println("║  [2]  Login                          ║");
            System.out.println("║  [3]  Editar Usuario                 ║");
            System.out.println("║  [4]  Logout                         ║");
            System.out.println("║  [0]  Voltar                         ║");
            System.out.println("╚══════════════════════════════════════╝");
            int escolha = Utilitarios.lerInteiro("Escolha uma opção: ");

            switch (escolha) {
                case 1:
                    usuarioController.cadastrar();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 2:
                    usuarioController.login();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 3:
                    usuarioController.editarConta();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 4:
                    usuarioController.logout();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 0:
                    executando = false;
                    Utilitarios.limparTela();
                    break;

                default:
                    System.out.println("Opção invalida!");
                    Utilitarios.pausar();
                    Utilitarios.limparTela();

            }
        } while(executando);
    }

    public static void postMenu() {
        boolean executando = true;

        do {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║               Menu Post              ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  [1]  Criar Post                     ║");
            System.out.println("║  [2]  Editar Post                    ║");
            System.out.println("║  [3]  Remover Post                   ║");
            System.out.println("║  [4]  Listar Post                    ║");
            System.out.println("║  [5]  Buscar Posts                   ║");
            System.out.println("║  [0]  Voltar                         ║");
            System.out.println("╚══════════════════════════════════════╝");
            int escolha = Utilitarios.lerInteiro("Escolha uma opção: ");

            switch (escolha) {
                case 1:
                    postController.criarPost();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 2:
                    postController.editarPost();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 3:
                    postController.deletarPost();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 4:
                    postController.listarPosts();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 5:
                    postController.buscarPost();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 0:
                    executando = false;
                    Utilitarios.limparTela();
                    break;

                default:
                    System.out.println("Opção invalida!");
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
            }
        } while (executando);
    }

    public static void comentarioMenu() {
        boolean executando = true;

        do {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║            Menu Comentario           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  [1]  Comentar em um Post            ║");
            System.out.println("║  [2]  Editar Comentario              ║");
            System.out.println("║  [3]  Remover Comentario             ║");
            System.out.println("║  [4]  Listar Comentario              ║");
            System.out.println("║  [0]  Voltar                         ║");
            System.out.println("╚══════════════════════════════════════╝");
            int escolha = Utilitarios.lerInteiro("Escolha uma opção: ");

            switch (escolha) {
                case 1:
                    comentarioController.comentarPost();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 2:
                    comentarioController.editarComentario();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 3:
                    comentarioController.deletarComentario();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 4:
                    comentarioController.listarComentariosDoPost();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 0:
                    executando = false;
                    Utilitarios.limparTela();
                    break;

                default:
                    System.out.println("Opção invalida!");
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
            }
        } while (executando);
    }

    public static void curtidasMenu() {
        boolean executando = true;

        do {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║             MENU CURTIDAS            ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  [1]  Curtir/Descurtir Post          ║");
            System.out.println("║  [2]  Curtir/Descurtir Comentario    ║");
            System.out.println("║  [0]  Voltar                         ║");
            System.out.println("╚══════════════════════════════════════╝");
            int escolha = Utilitarios.lerInteiro("Escolha uma opção: ");

            switch (escolha) {
                case 1:
                    curtidasController.toggleCurtirPost();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 2:
                    curtidasController.toggleCurtirComentario();
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
                    break;

                case 0:
                    executando = false;
                    Utilitarios.limparTela();
                    break;

                default:
                    System.out.println("Opção invalida!");
                    Utilitarios.pausar();
                    Utilitarios.limparTela();
            }
        } while (executando);
    }

}
