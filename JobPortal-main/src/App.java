
package PortalDeVagas;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        DbContext db = new DbContext();
        PortalDeVagas portal = new PortalDeVagas(db);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Opções:");
            System.out.println("1. Cadastrar pessoa");
            System.out.println("2. Listar pessoas");
            System.out.println("3. Buscar pessoa");
            System.out.println("4. Atualizar pessoa");
            System.out.println("5. Excluir pessoa");
            System.out.println("6. Sair");

            System.out.print("Digite a opção desejada: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    portal.cadastrarPessoa(scanner);
                    break;
                case 2:
                    portal.listarPessoas();
                    break;
                case 3:
                    portal.buscarPessoa(scanner);
                    break;
                case 4:
                    portal.atualizarPessoa(scanner);
                    break;
                case 5:
                    portal.excluirPessoa(scanner);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
