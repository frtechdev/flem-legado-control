<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>



<h2>Lista de Aplicações</h2>
<html:form action="Aplicacao.do">
    <html:hidden property="id" value="${aplicacao.id}"/>
    <div id="Aplicacao" style="display: block;">
        <fieldset>
            <legend>Informe o Nome a ser filtrado e clique no consultar. </legend>
            <html:text property="nome" size="130"/>
            <br/>
            <html:submit property="consultar" value="consultar" styleClass="botao" />
        </fieldset>
    </div>
    <br />

    <div align="right"><html:submit property="metodo" value="excluir" onclick="if(confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?')) {return true;} return false;" styleClass="botao" /></div>
    <display:table id="aplicacao" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./Aplicacao.do" pagesize="30"  class="tabelaDisplay">
        <display:column ><input type="checkbox" name="ids_exclusao" value="${aplicacao.id}"/></display:column>
        <display:column value="${aplicacao.nomeCurto}" title="Nome Curto" sortable="true" />
        <display:column value="${aplicacao.nome}" title="Nome" sortable="true"  />
        <display:column value="${aplicacao.descricao}" title="Descrição" sortable="true"    />
        <display:column title="" href="./Aplicacao.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="edit"/></display:column>
    </display:table>
</html:form>