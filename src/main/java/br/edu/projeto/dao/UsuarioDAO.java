package br.edu.projeto.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.sql.DataSource;

import br.edu.projeto.model.Usuario;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados
@Stateful
public class UsuarioDAO implements Serializable{

	@Inject
    private DataSource ds;
	
    public Usuario findById(Integer id) {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT usuario, email FROM usuario WHERE id_usuario = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Usuario u = new Usuario();
			if (rs.next()) {
				u.setId(id);
				u.setEmail(rs.getString("email"));
				u.setUsuario(rs.getString("usuario"));
			}
			return u;
		} catch (SQLException e) {e.printStackTrace();}
        return null;
    }
    
    public Usuario findByName(String name) {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT id_usuario, email FROM usuario WHERE usuario = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			Usuario u = new Usuario();
			if (rs.next()) {
				u.setId(rs.getInt("id_usuario"));
				u.setEmail(rs.getString("email"));
				u.setUsuario(name);
			}
			return u;
		} catch (SQLException e) {e.printStackTrace();}
        return null;
    }
    
    public List<Usuario> listAll() {
    	List<Usuario> usuarios = new ArrayList<Usuario>();
    	try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT id_usuario, usuario, email FROM usuario");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Usuario u = new Usuario();
				u.setEmail(rs.getString("email"));
				u.setId(rs.getInt("id_usuario"));
				u.setUsuario(rs.getString("usuario"));
				usuarios.add(u);
			}
		} catch (SQLException e) {e.printStackTrace();}
        return usuarios;
    }
    
    public Boolean save(Usuario u) {
    	Boolean resultado = false;
    	try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO usuario (usuario, email, senha) VALUES (?, ?, ?)");
			ps.setString(1, u.getUsuario());
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getSenha());
			ps.executeQuery();
			resultado = true;
		} catch (SQLException e) {e.printStackTrace();}
        return resultado;
    }
}
