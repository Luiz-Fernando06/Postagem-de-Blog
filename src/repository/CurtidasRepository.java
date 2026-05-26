package repository;

import model.Comentario;
import model.Curtidas;
import model.Curtivel;
import model.Post;
import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acesso ao banco de dados para a entidade Curtidas.
 *
 * Métodos:
 *  salvar()           — INSERT de uma curtida
 *  remover()          — DELETE de uma curtida
 *  buscarCurtidas()   — busca uma curtida específica (para o toggle)
 *  buscarCurtidas()   — lista todas curtidas de um Curtivel (para limpar ao deletar)
 */
public class CurtidasRepository {

    public void salvar(Curtidas curtidas) {
        String sql = "INSERT INTO curtidas (autor_id, post_id, comentario_id) VALUES (?, ?, ?)";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, curtidas.getAutor().getId());

            if (curtidas.getCurtivel() instanceof Post) {
                ps.setLong(2, curtidas.getCurtivel().getId());
                ps.setNull(3, Types.BIGINT);

            } else if (curtidas.getCurtivel() instanceof Comentario) {
                ps.setNull(2, Types.BIGINT);
                ps.setLong(3, curtidas.getCurtivel().getId());

            } else {
                System.out.println("Tipo curtivel invalido: " + curtidas.getCurtivel().getClass().getName());
                return;
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                curtidas.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao salvar curtida: " + e.getMessage());
        }
    }

    public void remover(Curtidas curtidas) {
        String sql = "DELETE FROM curtidas WHERE id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, curtidas.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover curtida: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // buscarCurtidas(autor, curtivel):
    // Verifica se esse usuário já curtiu esse post/comentário específico.
    // Retorna null se não curtiu ainda (usado no toggle da CurtidasService).
    // ------------------------------------------------------------------
    public Curtidas buscarCurtidas(Usuario autor, Curtivel curtivel) {
        String sql;

        if (curtivel instanceof Post) {
            sql = "SELECT * FROM curtidas WHERE autor_id = ? AND post_id = ?";
        } else if (curtivel instanceof Comentario) {
            sql = "SELECT * FROM curtidas WHERE autor_id = ? AND comentario_id = ?";
        } else {
            System.out.println("Tipo curtivel invalido: " + curtivel.getClass().getName());
            return null;
        }

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, autor.getId());
            ps.setLong(2, curtivel.getId());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Curtidas c = new Curtidas(autor, curtivel);
                c.setId(rs.getLong("id"));
                return c;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar curtida: " + e.getMessage());
        }

        return null;
    }

    // ------------------------------------------------------------------
    // buscarCurtidas(curtivel):
    // Retorna TODAS as curtidas de um post ou comentário.
    // Usado para limpar as curtidas antes de deletar um post/comentário.
    // ------------------------------------------------------------------
    public List<Curtidas> buscarCurtidas(Curtivel curtivel) {
        boolean ehPost = curtivel instanceof Post;

        String sql = ehPost
                ? "SELECT c.*, u.id AS u_id, u.nome AS u_nome, u.email AS u_email, u.senha AS u_senha, u.data_criacao AS u_data_criacao FROM curtidas c JOIN usuario u ON c.autor_id = u.id WHERE c.post_id = ?"
                : "SELECT c.*, u.id AS u_id, u.nome AS u_nome, u.email AS u_email, u.senha AS u_senha, u.data_criacao AS u_data_criacao FROM curtidas c JOIN usuario u ON c.autor_id = u.id WHERE c.comentario_id = ?";

        List<Curtidas> lista = new ArrayList<>();

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, curtivel.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario autor = new Usuario();
                autor.setId(rs.getLong("u_id"));
                autor.setNome(rs.getString("u_nome"));
                autor.setEmail(rs.getString("u_email"));
                autor.setSenha(rs.getString("u_senha"));
                Date uData = rs.getDate("u_data_criacao");
                if (uData != null) autor.setDataCriacao(uData.toLocalDate());

                Curtidas c = new Curtidas(autor, curtivel);
                c.setId(rs.getLong("id"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar curtidas: " + e.getMessage());
        }

        return lista;
    }
}
