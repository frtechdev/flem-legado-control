<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Lista Usuario</h2>
<html:form action="Usuario.do" >
    <table>
        <tr>
            <td>Nome:</td>
            <td><html:text property="nome" size="50" value="${usuarioAction_filtro_nome}"/></td>
        </tr>
        <tr>
            <td>Login: </td>
            <td><html:text property="login" size="25" value="${usuarioAction_filtro_login}"/></td>
        </tr>
        <tr>
           <td>Lotação:</td>
           <td>
           <html:select property="lotacaoDominio" value="${usuarioAction_filtro_lotacaoDominio}">
             <html:option value="">Selecione</html:option>
            <html:optionsCollection label="nome" value="codigoDominio" name="listadepartamento"/>
           </html:select>
           </td>
        </tr>
        <tr>
           <td>Situação:</td>
           <td>
           <html:select property="situacao" value="${usuarioAction_filtro_situacao}">
             <html:option value="true">Ativos</html:option>
             <html:option value="false">Inativos</html:option>
           </html:select>
           </td>
        </tr>
        <tr>
            <td><div align="left"><html:submit property="metodo" styleClass="botao" value="filtrar"/>   <html:submit value="excluir" property="metodo"  onclick="if(confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?')) {return true;} return false;" styleClass="botao" /></div></td>
       </tr>
    </table>

    <display:table id="usuario" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./Usuario.do" pagesize="30"  class="tabelaDisplay">

        <display:column ><input type="checkbox" name="ids_exclusao" value="${usuario.id}"/></display:column>
        <display:column property="login" title="Login" sortable="true" />
        <display:column property="nome" title="Nome" sortable="true"  />
        <display:column title="Lotação Dominio" sortable="true">${mapdepartamento[usuario.lotacaoDominio.toString()].nome}</display:column>
        <display:column title="Autenticação" style="width:105px" >${usuario.tipoAutenticacao.nome}</display:column>
        <display:column title="Ativo">${usuario.ativo ? "Sim" : "Não"}</display:column>
        <display:column title="Perfil" href="./UsuarioPerfil.do" paramId="id" paramProperty="id" ><img align="middle" src="img/exec.png" width="22" height="22" border="0" alt="Perfil"/></display:column>
        <display:column title="App Intra" href="./UsuarioAplicativoIntranet.do" paramId="id" paramProperty="id" ><img align="middle" src="img/intra.png" width="22" height="22" border="0" alt="Perfil Intranet"/></display:column>
        <display:column title="Depto" href="./DepartamentoUsuario.do" paramId="id" paramProperty="id" ><img align="middle" src="img/kded.png" width="22" height="22" border="0" alt="Departamento"/></display:column>
        <display:column title="CC" href="./CentroCustoUsuario.do" paramId="id" paramProperty="id" ><img align="middle" src="img/polygon.png" width="22" height="22" border="0" alt="Centro de Custo"/></display:column>
        <display:column title="" href="./Usuario.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Editar Dados do Usuário"/></display:column>
        <display:column title="" href="./Usuario.do?metodo=selecionarAutenticacao" paramId="id" paramProperty="id" ><c:if test="${usuario.tipoAutenticacao.id == 1}"><img align="middle" src="img/secrecy-icon.png" width="22" height="22" border="0" alt="Mudar Autenticação"/></c:if></display:column>
    </display:table>
</html:form>

