package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Responsável por fornecer a conexão com o banco de dados MySQL.
 *
 * Como funciona:
 *  - getConexao() abre e retorna uma Connection nova a cada chamada.
 *  - Cada Repository fecha a conexão no bloco finally após o uso.
 *  - Altere as constantes URL, USER e PASSWORD conforme seu ambiente.
 */
public class ConexaoBanco {

    // ----------------------------------------------------------------
    //  CONFIGURAÇÕES — ajuste se necessário
    // ----------------------------------------------------------------
    private static final String URL    = "jdbc:mysql://localhost:3306/bloguinho?useSSL=false&serverTimezone=America/Sao_Paulo";
    private static final String USER   = "root";       // seu usuário MySQL
    private static final String PASSWORD = "Tire2002@";     // sua senha MySQL
    // ----------------------------------------------------------------

    /**
     * Abre e retorna uma nova conexão com o banco de dados.
     * Lança RuntimeException se não conseguir conectar,
     * para que o erro apareça claro no console.
     */
    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage(), e);
        }
    }
}
