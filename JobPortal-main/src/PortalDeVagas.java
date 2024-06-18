
package PortalDeVagas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PortalDeVagas {
    private ArrayList<Pessoa> pessoas;

    public PortalDeVagas(DbContext db) {
        this.pessoas = new ArrayList<>();
        this.database = db;
    }

    private DbContext database;

    public void cadastrarPessoa(Scanner scanner) {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        try {
            database.conectarBanco();
            String sql = "INSERT INTO public.pessoa(nome, email, cpf) VALUES(?, ?, ?)";
            PreparedStatement pstmt = database.getConnection().prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, cpf);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Perfil cadastrado com sucesso!");
                Pessoa pessoa = new Pessoa(nome, cpf, email);
                pessoas.add(pessoa);
            } else {
                System.out.println("Erro ao cadastrar pessoa. Consulte o log para mais informações.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.desconectarBanco();
        }
    }

    public void listarPessoas() {

        try {
            database.conectarBanco();
            String query = "SELECT * FROM public.pessoa";
            ResultSet resultado = database.executarQuerySql(query);
            System.out.println("Lista de pessoas cadastradas:");
            while (resultado.next()) {
                String nome = resultado.getString("nome");
                String cpf = resultado.getString("cpf");
                String email = resultado.getString("email");
                System.out.println("Nome: " + nome + ", CPF: " + cpf + ", Email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.desconectarBanco();
        }
    }

    public void buscarPessoa(Scanner scanner) {
        System.out.print("Digite o CPF da pessoa que deseja buscar: ");
        String cpf = scanner.nextLine();

        try {
            database.conectarBanco();
            String query = "SELECT * FROM public.pessoa WHERE cpf = ?";
            PreparedStatement pstmt = database.getConnection().prepareStatement(query);
            pstmt.setString(1, cpf);
            ResultSet resultado = pstmt.executeQuery();
            if (resultado.next()) {
                String nome = resultado.getString("nome");
                String email = resultado.getString("email");
                System.out.println("Pessoa encontrada:");
                System.out.println("Nome: " + nome + ", CPF: " + cpf + ", Email: " + email);
            } else {
                System.out.println("Pessoa não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.desconectarBanco();
        }
    }

    public void atualizarPessoa(Scanner scanner) {
        System.out.print("Digite o CPF da pessoa que deseja atualizar: ");
        String cpf = scanner.nextLine();

        try {
            database.conectarBanco();
            String query = "UPDATE public.pessoa SET nome = ?, email = ? WHERE cpf = ?";
            PreparedStatement pstmt = database.getConnection().prepareStatement(query);
            System.out.print("Digite o novo nome: ");
            String novoNome = scanner.nextLine();
            System.out.print("Digite o novo email: ");
            String novoEmail = scanner.nextLine();
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoEmail);
            pstmt.setString(3, cpf);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pessoa atualizada com sucesso!");
                for (Pessoa pessoa : pessoas) {
                    if (pessoa.getCpf().equals(cpf)) {
                        pessoa.setNome(novoNome);
                        pessoa.setEmail(novoEmail);
                    }
                }
            } else {
                System.out.println("Pessoa não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.desconectarBanco();
        }
    }

    public void excluirPessoa(Scanner scanner) {
        System.out.print("Digite o CPF da pessoa que deseja excluir: ");
        String cpf = scanner.nextLine();

        try {
            database.conectarBanco();
            String query = "DELETE FROM public.pessoa WHERE cpf = ?";
            PreparedStatement pstmt = database.getConnection().prepareStatement(query);
            pstmt.setString(1, cpf);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pessoa excluída com sucesso!");
                pessoas.removeIf(p -> p.getCpf().equals(cpf));
            } else {
                System.out.println("Pessoa não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.desconectarBanco();
        }
    }
}