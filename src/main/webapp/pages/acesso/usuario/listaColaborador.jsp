<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<html:form action="Colaborador.do" >
    <h2>Lista de colaboradores ativos e sem usuario de rede: </h2>
    <div id="listaCCs" style="display: block;">
        <fieldset>
            <legend>Informe o Nome do Colaborador e clique no consultar. </legend>
            <html:text property="nome" size="130"/>
            <br />
            <html:submit property="consultar" value="Consultar" styleClass="botao" />
        </fieldset>
    </div>
    <br />
    <display:table id="colaborador" name="lista" defaultsort="2" sort="list" export="false" excludedParams="metodo" requestURI="./Colaborador.do" pagesize="30"  class="tabelaDisplay">
        <display:column property="codigoDominio" title="Codigo Dominio" sortable="true" />
        <display:column property="nome" title="Nome" sortable="true"  />
        <display:column property="departamento.nome" title="Lotação" sortable="true"  />
        <display:column title="" href="./Colaborador.do?metodo=criar" paramId="mat" paramProperty="codigoDominio" ><img align="middle" src="img/exec.png" width="22" height="22" border="0" alt="Criar"/></display:column>
    </display:table>
</html:form>