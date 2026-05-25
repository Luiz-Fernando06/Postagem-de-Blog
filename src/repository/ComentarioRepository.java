package repository;

import model.Comentario;
import model.Post;
import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acesso ao banco de dados para a entidade Comentario.
 *
 * Métodos:
 *  salvar()                    — INSERT ou UPDATE
 *  buscarId()                  — SELECT por id
 *  listarComentariosDoPost()   — SELECT todos de um post
 *  remover()                   — DELETE de um comentário
 *  removerComentariosDoPost()  — DELETE de todos comentários de um post
 *                                (chamado antes de deletar o post)
 */
public class ComentarioRepository {

    public void salvar(Comentario comentario) {
        if (comentario.getId() == 0) {
            inserir(comentario);
        } else {
            atualizar(comentario);
        }
    }

    private void inserir(Comentario comentario) {
        String sql = "INSERT INTO comentario (conteudo, data_criacao, qtd_curtidas, autor_id, post_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, comentario.getConteudo());
            ps.setDate(2, Date.valueOf(comentario.getDataCriacao()));
            ps.setLong(3, comentario.getQtdCurtidas());
            ps.setLong(4, comentario.getAutor().getId());
            ps.setLong(5, comentario.getPostagem().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                comentario.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir comentario: " + e.getMessage());
        }
    }

    private void atualizar(Comentario comentario) {
        // Atualiza conteúdo e curtidas
        String sql = "UPDATE comentario SET conteudo = ?, qtd_curtidas = ? WHERE id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, comentario.getConteudo());
            ps.setLong(2, comentario.getQtdCurtidas());
            ps.setLong(3, comentario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar comentario: " + e.getMessage());
        }
    }

    public Comentario buscarId(long id) {
        // JOIN duplo: traz o autor e o post do comentário de uma vez
        String sql = "SELECT c.*, " +
                     "u.id AS u_id, u.nome AS u_nome, u.email AS u_email, u.senha AS u_senha, u.data_criacao AS u_data_criacao, " +
                     "p.id AS p_id, p.titulo AS p_titulo, p.conteudo AS p_conteudo, p.dt_criacao AS p_dt_criacao, p.qtd_curtidas AS p_qtd_curtidas, " +
                     "pu.id AS pu_id, pu.nome AS pu_nome, pu.email AS pu_email, pu.senha AS pu_senha, pu.data_criacao AS pu_data_criacao " +
                     "FROM comentario c " +
                     "JOIN usuario u  ON c.autor_id = u.id " +
                     "JOIN post p     ON c.post_id  = p.id " +
                     "JOIN usuario pu ON p.autor_id  = pu.id " +
                     "WHERE c.id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearComentario(rs);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar comentario por id: " + e.getMessage());
        }

        return null;
    }

    public List<Comentario> listarComentariosDoPost(Post post) {
        String sql = "SELECT c.*, " +
                     "u.id AS u_id, u.nome AS u_nome, u.email AS u_email, u.senha AS u_senha, u.data_criacao AS u_data_criacao, " +
                     "p.id AS p_id, p.titulo AS p_titulo, p.conteudo AS p_conteudo, p.dt_criacao AS p_dt_criacao, p.qtd_curtidas AS p_qtd_curtidas, " +
                     "pu.id AS pu_id, pu.nome AS pu_nome, pu.email AS pu_email, pu.senha AS pu_senha, pu.data_criacao AS pu_data_criacao " +
                     "FROM comentario c " +
                     "JOIN usuario u  ON c.autor_id = u.id " +
                     "JOIN post p     ON c.post_id  = p.id " +
                     "JOIN usuario pu ON p.autor_id  = pu.id " +
                     "WHERE c.post_id = ? " +
                     "ORDER BY c.id ASC";

        List<Comentario> comentarios = new ArrayList<>();

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, post.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comentarios.add(mapearComentario(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar comentarios do post: " + e.getMessage());
        }

        return comentarios;
    }

    public void remover(Comentario comentario) {
        String sql = "DELETE FROM comentario WHERE id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, comentario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover comentario: " + e.getMessage());
        }
    }

    // Deleta todos os comentários de um post antes de deletar o post
    // (necessário por causa da FOREIGN KEY comentario.post_id → post.id)
    public void removerComentariosDoPost(Post post) {
        String sql = "DELETE FROM comentario WHERE post_id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, post.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover comentarios do post: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // mapearComentario: monta Comentario com Usuario autor e Post postagem
    // ------------------------------------------------------------------
    private Comentario mapearComentario(ResultSet rs) throws SQLException {
        // Autor do comentário
        Usuario autor = new Usuario();
        autor.setId(rs.getLong("u_id"));
        autor.setNome(rs.getString("u_nome"));
        autor.setEmail(rs.getString("u_email"));
        autor.setSenha(rs.getString("u_senha"));
        Date uData = rs.getDate("u_data_criacao");
        if (uData != null) autor.setDataCriacao(uData.toLocalDate());

        // Autor do post
        Usuario autorPost = new Usuario();
        autorPost.setId(rs.getLong("pu_id"));
        autorPost.setNome(rs.getString("pu_nome"));
        autorPost.setEmail(rs.getString("pu_email"));
        autorPost.setSenha(rs.getString("pu_senha"));
        Date puData = rs.getDate("pu_data_criacao");
        if (puData != null) autorPost.setDataCriacao(puData.toLocalDate());

        // Post ao qual o comentário pertence
        Post post = new Post(autorPost, rs.getString("p_titulo"), rs.getString("p_conteudo"));
        post.setId(rs.getLong("p_id"));
        post.setQtdCurtidas(rs.getLong("p_qtd_curtidas"));
        Date pData = rs.getDate("p_dt_criacao");
        if (pData != null) post.setDtCriacao(pData.toLocalDate());

        // Comentário
        Comentario c = new Comentario(autor, post, rs.getString("conteudo"));
        c.setId(rs.getLong("id"));
        c.setQtdCurtidas(rs.getLong("qtd_curtidas"));
        Date cData = rs.getDate("data_criacao");
        if (cData != null) c.setDataCriacao(cData.toLocalDate());

        return c;
    }
}
