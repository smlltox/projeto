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

import br.edu.projeto.model.Nacionalidades;
import br.edu.projeto.util.DbUtil;
import br.edu.projeto.util.Nacionalidade;

@Stateful
public class NacionalidadeDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
    private DataSource ds;
	
	public Nacionalidades findById(Integer id) {
		Nacionalidades tp = new Nacionalidades();
		Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
		try {
			con = this.ds.getConnection();
			ps = con.prepareStatement("SELECT tipo FROM nacionalidades WHERE id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				tp.setId(id);
				tp.setNacionalidade(Nacionalidade.valueOf(rs.getString("tipo")));
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return tp;
    }
	
    public List<Nacionalidades> listAll() {
    	List<Nacionalidades> nacs = new ArrayList<Nacionalidades>();
    	Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
			con = this.ds.getConnection();
			ps = con.prepareStatement("SELECT id, tipo FROM nacionalidades");
			rs = ps.executeQuery();
			while (rs.next()) {
				Nacionalidades tp = new Nacionalidades();
	            tp.setId(rs.getInt("id"));
	            String descricao = rs.getString("tipo");
	            Nacionalidade nacionalidade = Nacionalidade.valueOf(descricao);
	            tp.setNacionalidade(nacionalidade);
	            nacs.add(tp);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return nacs;
    }
}
