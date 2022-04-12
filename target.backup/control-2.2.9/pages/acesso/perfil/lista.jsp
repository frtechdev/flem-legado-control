<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Lista Perfil</h2>

<html:form action="Perfil.do">
      <html:hidden property="id" value="${perfil.id}"/>

<div id="Perfil" style="display: block;">
    <fieldset>
        <legend>Informe o Nome a ser filtrado e clique no consultar. </legend>
        <html:text property="nomePerfilForm" size="130"/>
        <br />
        <html:submit property="consultar" value="Consultar" styleClass="botao"/>
    </fieldset>
</div>
<br />

    <div align="right"><html:submit value="excluir" onclick="if(confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?')) {return true;} return false;" styleClass="botao" /></div>
    <display:table id="perfil" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./Perfil.do" pagesize="30"  class="tabelaDisplay">
        <display:column ><input type="checkbox" name="ids_exclusao" value="${perfil.id}"/></display:column>
        <display:column value="${perfil.nomeCurto}" title="Nome Curto" sortable="true" />
        <display:column value="${perfil.nome}" title="Nome" sortable="true"  />
        <display:column value="${perfil.descricao}" title="Descrição" sortable="true"    />
        <display:column title="" href="./PerfilFuncionalidade.do" paramId="id" paramProperty="id" ><img align="middle" src="img/exec.png" width="22" height="22" border="0" alt="funcionalidades"/></display:column>
        <display:column title="" href="./Perfil.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="edit"/></display:column>
          <display:column title="" href="./Perfil.do?metodo=usuarios" paramId="id" paramProperty="id" ><img align="middle" src="img/usr.png" width="22" height="22" border="0" alt="edit"/></display:column>
    </display:table>
</html:form>