package br.edu.projeto.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "login.xhtml",
                errorPage = "login-error.xhtml"
        )
)

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:/PostgresDS",
        callerQuery = "SELECT senha FROM usuario WHERE usuario = ?",
        groupsQuery = "SELECT permissao FROM tipo_permissao JOIN permissao USING (id_tipo_permissao) JOIN usuario USING (id_usuario) WHERE usuario = ?"
)

@ApplicationScoped
public class AppConfig {
	
}
