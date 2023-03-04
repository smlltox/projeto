package br.edu.projeto.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// Define regra específica para CDI injetar objetos de classes onde o construtor comum não pode ser utilizado
// Também define o escopo do objeto criado
public class Resources {

	@Produces
    @ApplicationScoped    
    public DataSource produceDatasource() {
		try {
			Context ctx = new InitialContext();
			return (DataSource) ctx.lookup("java:/PostgresDS");
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
