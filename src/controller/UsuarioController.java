package controller;

import app.Utilitarios;
import model.Usuario;
import service.UsuarioService;
import java.util.Scanner;

/**
 * Controla as entradas do usuário relacionadas à conta e sessão.
 */
public class UsuarioController {

    private Scanner LER = new Scanner(System.in);
    private UsuarioService USUARIOSERVICE;
    protected static Usuario usuarioLogado;

    public UsuarioController(UsuarioService usuarioService, Scanner ler) {
        this.USUARIOSERVICE = usuarioService;
        this.LER = ler;
    }

    public void cadastrar() {
        Utilitarios.limparTela();

        System.out.print("Nome: ");
        String nome = LER.nextLine();

        System.out.print("Email: ");
        String email = LER.nextLine();

        System.out.println("Senha: ");
        String senha = LER.nextLine();

        boolean sucesso = USUARIOSERVICE.cadastro(nome, email, senha);

        if (sucesso) {
            System.out.println("Usuario cadastrado!");
        } else {
            System.out.println("Email já cadastrado");
        }
    }

    public void login() {
        Utilitarios.limparTela();

        if (usuarioLogado != null) {
            System.out.println("Usuario já logado");
            return;
        }

        System.out.print("Email: ");
        String email = LER.nextLine();

        System.out.println("Senha: ");
        String senha = LER.nextLine();

        Usuario user = USUARIOSERVICE.login(email, senha);

        if (user != null) {
            usuarioLogado = user;
            System.out.println("Login realizado!");
            System.out.println("Bem-vindo " + usuarioLogado.getNome());
        } else {
            System.out.println("Email ou senha invalidos.");
        }
    }

    public void logout() {
        Utilitarios.limparTela();

        if (usuarioLogado == null) {
            System.out.println("Nenhum usuario logado!");
            return;
        }

        usuarioLogado = null;
        System.out.println("Logout realizado!");
    }

    public void editarConta() {
        Utilitarios.limparTela();

        if(usuarioLogado == null) {
            System.out.println("Faça login para editar");
            return;
        }

        boolean editando = true;

        do {

            System.out.println("O que deseja editar: ");
            System.out.println("1 - Nome");
            System.out.println("2 - Email");
            System.out.println("3 - Senha");
            System.out.println("4 - Tudo");
            System.out.println("5 - Sair");
            System.out.print("> ");
            int opcao = Integer.parseInt(LER.nextLine());

            switch (opcao) {
                case 1:
                    editarNome();
                    break;

                case 2:
                    editarEmail();
                    break;

                case 3:
                    editarSenha();
                    break;

                case 4:
                   editarTudo();
                    break;

                case 5:
                    editando = false;
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (editando);
    }

    private void editarNome() {
        System.out.println("Novo Nome: ");
        String nome = LER.nextLine();
        boolean sucesso = USUARIOSERVICE.editarNome(usuarioLogado.getId(), nome);
        if (sucesso) {
            System.out.println("Nome atualizado!");
        } else {
            System.out.println("Não foi possivel atualizar o nome");
        }
    }

    private void editarEmail() {
        System.out.println("Novo Email: ");
        String email = LER.nextLine();
        boolean sucesso = USUARIOSERVICE.editarEmail(usuarioLogado.getId(), email);
        if (sucesso) {
            System.out.println("Email atualizado!");
        } else {
            System.out.println("Email invalido ou já utilizado!");
        }
    }

    private void editarSenha() {
        System.out.println("Nova Senha: ");
        String senha = LER.nextLine();
        boolean sucesso = USUARIOSERVICE.editarSenha(usuarioLogado.getId(), senha);
        if (sucesso) {
            System.out.println("Senha atualizada!");
        } else {
            System.out.println("Não foi possivel atualizar a senha");
        }
    }

    private void editarTudo() {
        System.out.println("Novo Nome: ");
        String novoNome = LER.nextLine();

        System.out.println("Novo Email: ");
        String novoEmail = LER.nextLine();

        System.out.println("Nova Senha: ");
        String novaSenha = LER.nextLine();

        boolean sucesso = USUARIOSERVICE.editarConta(usuarioLogado.getId(), novoNome, novoEmail, novaSenha);
        if (sucesso) {
            System.out.println("Conta atualizada!");
        } else {
            System.out.println("Não foi possivel atualizar a conta");
        }
    }
}
