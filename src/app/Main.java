package app;

public class Main {
    public static void main(String[] args) {

        boolean executando = false;

        do {
            Utilitarios.exibirMenu();
            int escolha = Utilitarios.lerInteiro("Escolha uma opção: ");

            switch (escolha) {
                case 1:
                    Utilitarios.usuarioMenu();
                    break;

                case 2:
                    Utilitarios.postMenu();
                    break;

                case 3:
                    Utilitarios.comentarioMenu();
                    break;

                case 4:
                    Utilitarios.curtidasMenu();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opção inválida");
            }

        } while(executando);
    }
}
