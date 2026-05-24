package app;

import java.util.Scanner;

/**
 * Metodos utilitarios pelo sistema
 */
public class Utilitarios {

    public static void limparTela() {
        System.out.println("\n\n\n\n\n");
    }

    public static void linha() {
        System.out.println("---------------------------------------");
    }

    public static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        Scanner ler = new Scanner(System.in);

        try {
            return Integer.parseInt(ler.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
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
}
