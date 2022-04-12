/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.control.acesso.bo;

import br.org.flem.control.acesso.dao.UsuarioPerfilDAO;
import br.org.flem.control.acesso.negocio.UsuarioPerfil;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;

/**
 *
 * @author AJLima
 */
public class UsuarioPerfilBO {

public Collection<UsuarioPerfil> obterUsuariosPorFuncionalidade( int idfuncionalidade)throws SQLException, ParseException{
    
    Collection<UsuarioPerfil> usr = new UsuarioPerfilDAO().obterUsuariosPorFuncionalidade(idfuncionalidade); 
    return usr;
}

public Collection<UsuarioPerfil> obterUsuarioPorPerfil(int idperfil) throws SQLException,ParseException{

Collection<UsuarioPerfil> usr = new UsuarioPerfilDAO().obterUsuariosPorPerfil(idperfil);
return usr;
}
    
}