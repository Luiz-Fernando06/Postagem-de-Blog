package repository;

import model.Post;
import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acesso ao banco de dados para a entidade Post.
 *
 * Métodos:
 *  salvar()         — INSERT ou UPDATE
 *  buscarId()       — SELECT por id
 *  buscarTitulo()   — SELECT por trecho do título (LIKE)
 *  listarPosts()    — SELECT todos os posts
 *  remover()        — DELETE por id
 */
public class PostRepository {

    public void salvar(Post post) {
        if (post.getId() == 0) {
            inserir(post);
        } else {
            atualizar(post);
        }
    }

    private void inserir(Post post) {
        String sql = "INSERT INTO post (titulo, conteudo, dt_criacao, qtd_curtidas, autor_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, post.getTitulo());
            ps.setString(2, post.getConteudo());
            ps.setDate(3, Date.valueOf(post.getDtCriacao()));
            ps.setLong(4, post.getQtdCurtidas());
            ps.setLong(5, post.getAutor().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                post.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir post: " + e.getMessage());
        }
    }

    private void atualizar(Post post) {
        // Atualiza título, conteúdo e curtidas (curtidas mudam ao curtir/descurtir)
        String sql = "UPDATE post SET titulo = ?, conteudo = ?, qtd_curtidas = ? WHERE id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, post.getTitulo());
            ps.setString(2, post.getConteudo());
            ps.setLong(3, post.getQtdCurtidas());
            ps.setLong(4, post.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar post: " + e.getMessage());
        }
    }

    public Post buscarId(long id) {
        // JOIN com usuario para já trazer o autor junto
        String sql = "SELECT p.*, u.id AS u_id, u.nome AS u_nome, u.email AS u_email, " +
                     "u.senha AS u_senha, u.data_criacao AS u_data_criacao " +
                     "FROM post p " +
                     "JOIN usuario u ON p.autor_id = u.id " +
                     "WHERE p.id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearPost(rs);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar post por id: " + e.getMessage());
        }

        return null;
    }

    // Busca por trecho no título (não precisa ser exato)
    public List<Post> buscarTitulo(String titulo) {
        String sql = "SELECT p.*, u.id AS u_id, u.nome AS u_nome, u.email AS u_email, " +
                     "u.senha AS u_senha, u.data_criacao AS u_data_criacao " +
                     "FROM post p " +
                     "JOIN usuario u ON p.autor_id = u.id " +
                     "WHERE p.titulo LIKE ?";

        List<Post> posts = new ArrayList<>();

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // % no início e no fim permite buscar em qualquer parte do título
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                posts.add(mapearPost(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar post por titulo: " + e.getMessage());
        }

        return posts;
    }

    public List<Post> listarPosts() {
        String sql = "SELECT p.*, u.id AS u_id, u.nome AS u_nome, u.email AS u_email, " +
                     "u.senha AS u_senha, u.data_criacao AS u_data_criacao " +
                     "FROM post p " +
                     "JOIN usuario u ON p.autor_id = u.id " +
                     "ORDER BY p.id DESC";   // posts mais recentes primeiro

        List<Post> posts = new ArrayList<>();

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                posts.add(mapearPost(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar posts: " + e.getMessage());
        }

        return posts;
    }

    public void remover(Post post) {
        String sql = "DELETE FROM post WHERE id = ?";

        try (Connection con = ConexaoBanco.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, post.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao remover post: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------
    // mapearPost: monta o objeto Post (com o objeto Usuario autor dentro)
    // a partir de uma linha do ResultSet
    // ------------------------------------------------------------------
    private Post mapearPost(ResultSet rs) throws SQLException {
        // Monta o autor primeiro
        Usuario autor = new Usuario();
        autor.setId(rs.getLong("u_id"));
        autor.setNome(rs.getString("u_nome"));
        autor.setEmail(rs.getString("u_email"));
        autor.setSenha(rs.getString("u_senha"));
        Date uDataCriacao = rs.getDate("u_data_criacao");
        if (uDataCriacao != null) autor.setDataCriacao(uDataCriacao.toLocalDate());

        // Monta o post
        Post post = new Post(autor, rs.getString("titulo"), rs.getString("conteudo"));
        post.setId(rs.getLong("id"));
        post.setQtdCurtidas(rs.getLong("qtd_curtidas"));
        Date dtCriacao = rs.getDate("dt_criacao");
        if (dtCriacao != null) post.setDtCriacao(dtCriacao.toLocalDate());

        return post;
    }
}
