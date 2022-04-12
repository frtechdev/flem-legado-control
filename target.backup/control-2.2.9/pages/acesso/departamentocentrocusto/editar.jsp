<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Departamento: ${depCcNome}</h2>
<html:form action="DepartamentoCentroCusto.do?metodo=editar" onsubmit="return validar(this);">
    <html:hidden property="id"/>
<table>
    <tr>
        <td>
            <div align="left">
                <html:button property="voltar" value="Voltar" styleClass="botao" onclick="document.location.href='DepartamentoCentroCusto.do'"/>
            </div>
        </td>
    </tr>
</table>

<display:table id="departamentoCentro" name="listaCentroCustosDepartamento" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./DepartamentoCentroCusto.do?metodo=editar" pagesize="10"  class="tabelaDisplay">
    <display:column property="codigoCentroCusto" title="Código" sortable="true" group="1"   />
    <display:column property="nomeCentroCusto" title="Centro de Custo" sortable="true" group="1"   />
    <display:column title="" href="./DepartamentoCentroCusto.do?id=${depCcCodigo}&metodo=removerCentroCusto" paramId="codigo" paramProperty="codigoCentroCusto" ><img align="middle" src="img/encrypted.png" width="22" height="22" border="0" alt="remove"/></display:column>
</display:table>



<div id="CentroDeCusto" style="display: block;">
    <fieldset>
        <legend>Informe os Centros de Custo separando-os por vírgula.</legend>
        <html:text property="centroCusto" size="130"/>
        <br />
         <html:submit property="consultar" value="consultar" styleClass="botao" />
    </fieldset>
</div>
<br />
<div id="centroCustosConsultados" style="display: block;">
    <display:table id="centroCustos" name="listaCentroCustos" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./DepartamentoCentroCusto.do?metodo=editar" pagesize="10" class="tabelaDisplay">
        <display:column property="id" title="Código" sortable="true" group="1"   />
        <display:column property="descricaoCompleta" title="Centro de Custo" sortable="true" />
        <display:column title="" href="./DepartamentoCentroCusto.do?id=${sessionScope.depId}&metodo=adicionarCentroCusto" paramId="codigo" paramProperty="id" ><img align="middle" src="img/decrypted.png" width="22" height="22" border="0" alt="adiciona"/></display:column>
    </display:table>
</div>
</html:form>