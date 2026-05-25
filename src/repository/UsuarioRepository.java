package repository;

import model.Usuario;
import java.sql.*;
import java.time.LocalDate;

/**
 * Acesso ao banco de dados para a entidade Usuario.
 *
 * Métodos:
 *  salvar()        — INSERT ou UPDATE (decide pelo id: 0 = novo)
 *  buscarId()      — SELECT por id
 *  buscarPorEmail()— SELECT por email
 */
public class UsuarioRepository {

    // ------------------------------------------------------------------
    // salvar: se id == 0 faz INSERT e preenche o id gerado pelo banco;
    //         se id > 0 faz UPDATE nos dados do usuário.
    // ------------------------------------------------------------------
    public void salvar(Usuario usuario) {
        if (usuario.getId() == 0) {
            inserir(usuario);
        } else {
            atualizar(usuario);
        }
    }

    private void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha, data_criacao) VALUES (?, ?, ?, ?)";

        // Connection dentro do try-with-resources: fecha automaticamente ao sair do bloco
        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setDate(4, Date.valueOf(usuario.getDataCriacao()));

            ps.executeUpdate();

            // Pega o id que o MySQL gerou automaticamente e salva no objeto
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuario: " + e.getMessage());
        }
    }

    private void atualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setLong(4, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuario: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // buscarId: SELECT * FROM usuario WHERE id = ?
    // Retorna null se não encontrar.
    // ------------------------------------------------------------------
    public Usuario buscarId(long id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);   // converte a linha do banco em objeto
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuario por id: " + e.getMessage());
        }

        return null;
    }

    // ------------------------------------------------------------------
    // buscarPorEmail: usado no login e para checar duplicidade
    // ------------------------------------------------------------------
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuario por email: " + e.getMessage());
        }

        return null;
    }

    // ------------------------------------------------------------------
    // mapearUsuario: lê uma linha do ResultSet e monta o objeto Usuario
    // ------------------------------------------------------------------
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getString("senha"));

        Date dataCriacao = rs.getDate("data_criacao");
        if (dataCriacao != null) {
            u.setDataCriacao(dataCriacao.toLocalDate());
        }

        return u;
    }
}
