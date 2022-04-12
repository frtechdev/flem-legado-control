<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Lista de Aplicativos Intranet</h2>

<html:form action="AplicativoIntranet.do">
    
<div id="AplicacaoIntranet" style="display: block;">
    <fieldset>
        <legend>Informe o Nome a ser filtrado e clique no consultar. </legend>
        <html:text property="nomeAplicacaoIntranet" size="130"/>
        <br />
        <html:submit property="consultar" value="Consultar" styleClass="botao"/>
    </fieldset>
</div>
<br />
    
    <display:table id="aplicativo" name="listaintranet" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./AplicativoIntranet.do" pagesize="30"  class="tabelaDisplay">
       
        <display:column property="nome" title="Nome" sortable="true" />
        <display:column property="titulo" title="Título" sortable="true"  />
        <display:column property="url" title="URL" sortable="true"    />
        <display:column title="" href="./AplicativoIntranet.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="edit"/></display:column>
    </display:table>
</html:form>