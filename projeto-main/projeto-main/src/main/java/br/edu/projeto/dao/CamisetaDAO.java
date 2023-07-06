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

import br.edu.projeto.model.Camiseta;
import br.edu.projeto.util.DbUtil;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados e garante maior segurança nas transações com o banco
@Stateful
public class CamisetaDAO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private DataSource ds;
    
    public List<Camiseta> listAll() {
    	List<Camiseta> camisetas = new ArrayList<Camiseta>();
    	Connection con = null;//Conexão com a base
    	PreparedStatement ps = null;//Instrução SQL
    	ResultSet rs = null;//Resposta do SGBD
    	try {
			con = this.ds.getConnection();//Pegar um conexão
			ps = con.prepareStatement("SELECT id_camiseta, tamanho, descricao FROM camiseta");
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Camiseta c = new Camiseta();
				c.setDescricao(rs.getString("descricao"));
				c.setIdCamiseta(rs.getLong("id_camiseta"));
				c.setTamanho(rs.getString("tamanho"));
				camisetas.add(c);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return camisetas;
    }
    
    public List<Camiseta> listByTamanho(String tamanho) {
    	List<Camiseta> camisetas = new ArrayList<Camiseta>();
    	Connection con = null;//Conexão com a base
    	PreparedStatement ps = null;//Instrução SQL
    	ResultSet rs = null;//Resposta do SGBD
    	try {
			con = this.ds.getConnection();//Pegar um conexão
			ps = con.prepareStatement("SELECT id_camiseta, tamanho, descricao FROM camiseta WHERE tamanho = ?");
			ps.setString(1, tamanho);
			rs = ps.executeQuery();
			while (rs.next()) {//Pega próxima linha do retorno
				Camiseta c = new Camiseta();
				c.setDescricao(rs.getString("descricao"));
				c.setIdCamiseta(rs.getLong("id_camiseta"));
				c.setTamanho(rs.getString("tamanho"));
				camisetas.add(c);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return camisetas;
    }
       
    public Boolean insert(Camiseta c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("INSERT INTO camiseta (id_camiseta, tamanho, descricao) VALUES (?, ?, ?)");
				ps.setLong(1, c.getIdCamiseta());
				ps.setString(2, c.getTamanho());
				ps.setString(3, c.getDescricao());	
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
    
    public Boolean update(Camiseta c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("UPDATE camiseta SET tamanho = ?, descricao = ? WHERE id_camiseta = ?");
				ps.setString(1, c.getTamanho());
				ps.setString(2, c.getDescricao());
				ps.setLong(3, c.getIdCamiseta());
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
    
    public Boolean delete(Camiseta c) {
    	Boolean resultado = false;
    	Connection con = null;
    	PreparedStatement ps = null;
    	try {
	    	con = this.ds.getConnection();
	    	try {				
				ps = con.prepareStatement("DELETE FROM camiseta WHERE id_camiseta = ?");
				ps.setLong(1, c.getIdCamiseta());
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
