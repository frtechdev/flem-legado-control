<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Departamentos</h2>

<html:form action="DepartamentoCentroCusto.do" >
    <html:hidden property="id" value="${departamento.id}"/>
        <div id="DepartamentoCentroCusto" style="display: block;">
        <fieldset>
            <legend>Informe o Nome do Departamento a ser filtrado e clique no consultar. </legend>
            <html:text property="nomeDepartamentoCentroCusto" size="130"/>
            <br/>
            <html:submit property="consultar" value="consultar" styleClass="botao" />
        </fieldset>
    </div>
    <br />
    <display:table id="dep" name="listadepartamento" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./DepartamentoCentroCusto.do" pagesize="30"  class="tabelaDisplay">
        <display:column ><input type="checkbox" name="ids_exclusao" value="${dep.codigoDominio}"/></display:column>
        <display:column property="codigoDominio" title="Código" sortable="true" />
        <display:column property="nome" title="Departamento" sortable="true" />
        <display:column title="" href="./DepartamentoCentroCusto.do?metodo=editar" paramId="id" paramProperty="codigoDominio" ><img align="middle" src="img/exec.png" width="22" height="22" border="0" alt="Centro de Custo"/></display:column>
        <display:column title="" href="./DepartamentoCentroCusto.do?metodo=tipo_solicitacao" paramId="codigo" paramProperty="codigoDominio" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="Tipo de Solicitacao Padrão"/></display:column>
    </display:table>
</html:form>

