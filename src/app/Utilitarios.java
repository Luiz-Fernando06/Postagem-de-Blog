package app;

import java.util.Scanner;

/**
 * Metodos utilitarios pelo sistema
 */
public class Utilitarios {

    public static void limparTela() {
        System.out.println("\n\n\n\n\n\n\n\n");
    }

    public static void linha() {
        System.out.println("---------------------------------------");
    }

    public static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        Scanner ler = new Scanner(System.in);

        try {
            return Integer.parseInt(ler.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void pausar() {
        Scanner ler = new Scanner(System.in);
        System.out.println();
        System.out.print("Pressione ENTER para continuar...");
        ler.nextLine();
    }
}
