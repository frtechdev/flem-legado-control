<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Usuario: ${usuario.nome}</h2>
<html:form action="CentroCustoUsuario.do">
    <display:table id="centroUsuario" name="listaCentroCustoUsuario" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./CentroCustoUsuario.do" pagesize="10"  class="tabelaDisplay">
        <display:column property="codigoCentroCusto" title="Código" sortable="true" group="1"   />
        <display:column property="nomeCentroCusto" title="Centro de Custo" sortable="true" group="1"   />
        <display:column title="" href="./CentroCustoUsuario.do?id=${usuario.id}&metodo=removerCentroCusto" paramId="codigo" paramProperty="codigoCentroCusto" ><img align="middle" src="img/encrypted.png" width="22" height="22" border="0" alt="remove"/></display:column>
    </display:table>

    <div id="CentroCustoUsuario" style="display: block;">
        <fieldset>
            <legend>Informe os Centros de Custo separando-os por vírgula.</legend>
            <html:text property="nomeCentroCustoUsuario" size="130"/>
            <br />
            <html:submit property="consultar" value="Consultar" styleClass="botao"/>
        </fieldset>
    </div>
    <br />
    <display:table id="centro" name="listaCentroCustos" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./CentroCustoUsuario.do" pagesize="10" class="tabelaDisplay">
        <display:column property="id" title="Código" sortable="true" group="1"   />
        <display:column property="descricaoCompleta" title="Centro de Custo" sortable="true"  />
        <display:column title="" href="./CentroCustoUsuario.do?id=${usuario.id}&metodo=adicionarCentroCusto" paramId="codigo" paramProperty="id" ><img align="middle" src="img/decrypted.png" width="22" height="22" border="0" alt="adiciona"/></display:column>
    </display:table>
</html:form>