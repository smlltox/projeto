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

import br.edu.projeto.model.TipoPermissao;
import br.edu.projeto.model.Usuario;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados e garante maior segurança nas transações com o banco
@Stateful
public class UsuarioDAO implements Serializable{

	@Inject
	private DataSource ds;
	
    public Usuario findById(Integer id) {
    	Usuario u = new Usuario();
    	try {
			Connection con = this.ds.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT usuario, email FROM usuario WHERE id_usuario = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				u.setId(id);
				u.setEmail(rs.getString("email"));
				u.setUsuario(rs.getString("usuario"));
			}
		} catch (SQLException e) {e.printStackTrace();}
        return u;
    }
    
    public Usuario findByName(String name) {
    	Usuario u = new Usuario();
    	try {
			Connection con = this.ds.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT id_usuario, email FROM usuario WHERE usuario = ?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				u.setId(rs.getInt("id_usuario"));
				u.setEmail(rs.getString("email"));
				u.setUsuario(name);
			}
		} catch (SQLException e) {e.printStackTrace();}
        return u;
    }
    
    public List<Usuario> listAll() {
    	List<Usuario> usuarios = new ArrayList<Usuario>();
    	try {
			Connection con = this.ds.getConnection();
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
    
    public Boolean insert(Usuario u) {
    	Boolean resultado = false;
    	try {
	    	Connection con = this.ds.getConnection();
	    	con.setAutoCommit(false);
	    	try {				
				PreparedStatement ps = con.prepareStatement("INSERT INTO usuario (usuario, email, senha) VALUES (?, ?, ?) RETURNING id_usuario");
				ps.setString(1, u.getUsuario());
				ps.setString(2, u.getEmail());
				ps.setString(3, u.getSenha());
				
				ResultSet rs = ps.executeQuery();
				rs.next();
				u.setId(rs.getInt("id_usuario"));
				
				PreparedStatement ps2 = con.prepareStatement("INSERT INTO permissao (id_usuario, id_tipo_permissao) VALUES (?, ?)");
				ps2.setInt(1, u.getId());
				for (TipoPermissao tp: u.getPermissoes()) {
					ps2.setInt(2, tp.getId());
					ps2.execute();
				}
				con.commit();
				resultado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
    	} catch (SQLException e) {e.printStackTrace();}
    	return resultado;
    }
    
    public Boolean update(Usuario u) {
    	Boolean resultado = false;
    	try {
	    	Connection con = this.ds.getConnection();
	    	con.setAutoCommit(false);
	    	try {				
				PreparedStatement ps = con.prepareStatement("UPDATE usuario SET usuario = ?,  email = ?, senha = ? WHERE id_usuario = ?");
				ps.setString(1, u.getUsuario());
				ps.setString(2, u.getEmail());
				ps.setString(3, u.getSenha());
				ps.setInt(4, u.getId());
				ps.execute();	
				
				PreparedStatement ps2 = con.prepareStatement("DELETE FROM permissao WHERE id_usuario = ?");
				ps2.setInt(1, u.getId());
				ps2.execute();			
				
				PreparedStatement ps3 = con.prepareStatement("INSERT INTO permissao (id_usuario, id_tipo_permissao) VALUES (?, ?)");
				ps3.setInt(1, u.getId());
				for (TipoPermissao tp: u.getPermissoes()) {
					ps3.setInt(2, tp.getId());
					ps3.execute();
				}
				con.commit();
				resultado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
    	} catch (SQLException e) {e.printStackTrace();}
    	return resultado;
    }
    
    public Boolean delete(Usuario u) {
    	Boolean resultado = false;
    	try {
	    	Connection con = this.ds.getConnection();
	    	con.setAutoCommit(false);
	    	try {				
				PreparedStatement ps = con.prepareStatement("DELETE FROM permissao WHERE id_usuario = ?");
				ps.setInt(1, u.getId());
				ps.execute();
				
				PreparedStatement ps2 = con.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
				ps2.setInt(1, u.getId());
				ps2.execute();
				
				con.commit();
				resultado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
    	} catch (SQLException e) {e.printStackTrace();}
    	return resultado;
    }
}
