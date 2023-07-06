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

import br.edu.projeto.model.Pessoa;
import br.edu.projeto.util.DbUtil;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados e garante maior segurança nas transações com o banco
@Stateful
public class PessoaDAO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private DataSource ds;
    
    public List<Pessoa> listAll() {
    	List<Pessoa> pessoa = new ArrayList<Pessoa>();
    	Connection con = null;//Conexão com a base
    	PreparedStatement ps = null;//Instrução SQL
    	ResultSet rs = null;//Resposta do SGBD
    	try {
			con = this.ds.getConnection();//Pegar um conexão
			ps = con.prepareStatement("SELECT id, name, social_nm, cpf, height, peso, genero, age, email, tlfon, ender FROM clientes");
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Pessoa p = new Pessoa();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("name"));
				p.setNomesc(rs.getString("social_nm"));
				p.setCpf(rs.getString("cpf"));
				p.setHeight(rs.getInt("height"));
				p.setPeso(rs.getInt("peso"));
				p.setGenero(rs.getString("genero"));
				p.setIdade(rs.getInt("age"));
				p.setEmail(rs.getString("email"));
				p.setTelf(rs.getString("tlfon"));
				p.setEndereco(rs.getString("ender"));
				pessoa.add(p);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return pessoa;
    }
       
    public Boolean insert(Pessoa p) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("INSERT INTO clientes (name, social_nm, cpf, height, peso, genero, age, email, tlfon, ender, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, p.getNome());
				ps.setString(2, p.getNomesc());
				ps.setString(3, p.getCpf());	
				ps.setFloat(4, p.getHeight());
				ps.setFloat(5, p.getPeso());
				ps.setString(6, p.getGenero());
				ps.setInt(7, p.getIdade());
				ps.setString(8, p.getEmail());
				ps.setString(9, p.getTelf());
				ps.setString(10, p.getEndereco());
				ps.setInt(11, p.getId());
				ps.execute();
				resultado = true;
			} catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }
    
    public Boolean update(Pessoa p) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {
	            ps = con.prepareStatement("UPDATE clientes SET social_nm = ?, height = ?, peso = ?, genero = ?, age = ?, email = ?, tlfon = ?, ender = ? WHERE id = ?");
	            ps.setString(1, p.getNomesc());
	            ps.setFloat(2, p.getHeight());
	            ps.setFloat(3, p.getPeso());
	            ps.setString(4, p.getGenero());
	            ps.setInt(5, p.getIdade());
	            ps.setString(6, p.getEmail());
	            ps.setString(7, p.getTelf());
	            ps.setString(8, p.getEndereco());
	            ps.setInt(9, p.getId());
	            ps.execute();
	            resultado = true;
	        } catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }
    
    public Boolean delete(Pessoa p) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM clientes WHERE id = ?");
				ps.setInt(1, p.getId());
				ps.execute();
				resultado = true;
			} catch (SQLException e) {e.printStackTrace();}
    	} catch (SQLException e) {e.printStackTrace();
    	} finally {
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
    	return resultado;
    }
}