<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<h2>Usuario: ${usr.nome}</h2>
<html:form action="UsuarioPerfil.do?id=${id}" >
<display:table id="perfil" name="listaUsuario" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./UsuarioPerfil.do" pagesize="10"  class="tabelaDisplay">
    <display:column property="nomeCurto" title="Nome" sortable="true" group="1"   />
    <display:column property="descricao" title="Descrição" sortable="true"  />
    <display:column title="" href="./UsuarioPerfil.do?metodo=removerPerfil" paramId="prf" paramProperty="id" ><img align="middle" src="img/encrypted.png" width="22" height="22" border="0" alt="remove"/></display:column>
</display:table>
<br>

<div id="UsuarioPerfil" style="display: block;">
        <fieldset>
            <legend>Informe o Nome para filtrar e clique no consultar. </legend>
            <html:text property="nomePerfilForm" size="130"/>
            <br />
            <html:submit property="consultar" value="Consultar" styleClass="botao" />
        </fieldset>
</div>
            
<display:table id="perfil2" name="listaPerfis" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./UsuarioPerfil.do" pagesize="10" class="tabelaDisplay">
    <display:column property="nomeCurto" title="Nome" sortable="true" group="1"   />
    <display:column property="descricao" title="Descrição" sortable="true"  />
    <display:column title="" href="./UsuarioPerfil.do?metodo=adicionarPerfil" paramId="prf" paramProperty="id" ><img align="middle" src="img/decrypted.png" width="22" height="22" border="0" alt="adiciona"/></display:column>
</display:table>
</html:form>