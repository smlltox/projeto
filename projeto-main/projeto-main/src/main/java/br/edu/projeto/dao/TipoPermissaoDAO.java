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
import br.edu.projeto.util.DbUtil;
import br.edu.projeto.util.Permissao;

@Stateful
public class TipoPermissaoDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
    private DataSource ds;
	
	public TipoPermissao findById(Integer id) {
		TipoPermissao tp = new TipoPermissao();
		Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
		try {
			con = this.ds.getConnection();
			ps = con.prepareStatement("SELECT permissao FROM tipo_permissao WHERE id_tipo_permissao = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				tp.setId(id);
				tp.setPermissao(Permissao.valueOf(rs.getString("permissao")));
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return tp;
    }
	
    public List<TipoPermissao> listAll() {
    	List<TipoPermissao> permissoes = new ArrayList<TipoPermissao>();
    	Connection con = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
			con = this.ds.getConnection();
			ps = con.prepareStatement("SELECT id_tipo_permissao, permissao FROM tipo_permissao");
			rs = ps.executeQuery();
			while (rs.next()) {
				TipoPermissao tp = new TipoPermissao();
				tp.setId(rs.getInt("id_tipo_permissao"));
				tp.setPermissao(Permissao.valueOf(rs.getString("permissao")));
				permissoes.add(tp);
			}
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			DbUtil.closeResultSet(rs);
			DbUtil.closePreparedStatement(ps);
			DbUtil.closeConnection(con);
		}
        return permissoes;
    }
}
