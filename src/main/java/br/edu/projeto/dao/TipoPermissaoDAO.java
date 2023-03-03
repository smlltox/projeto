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
import br.edu.projeto.util.Permissao;

@Stateful
public class TipoPermissaoDAO implements Serializable {

	@Inject
    private DataSource ds;
	
	public TipoPermissao findById(Integer id) {
		try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT permissao FROM tipo_permissao WHERE id_tipo_permissao = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			TipoPermissao tp = new TipoPermissao();
			if (rs.next()) {
				tp.setId(id);
				tp.setPermissao(Permissao.valueOf(rs.getString("permissao")));
			}
			return tp;
		} catch (SQLException e) {e.printStackTrace();}
        return null;
    }
	
    public List<TipoPermissao> listAll() {
    	List<TipoPermissao> permissoes = new ArrayList<TipoPermissao>();
    	try {
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT id_tipo_permissao, permissao FROM tipo_permissao");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TipoPermissao tp = new TipoPermissao();
				tp.setId(rs.getInt("id_tipo_permissao"));
				tp.setPermissao(Permissao.valueOf(rs.getString("permissao")));
				permissoes.add(tp);
			}
		} catch (SQLException e) {e.printStackTrace();}
        return permissoes;
    }
}
