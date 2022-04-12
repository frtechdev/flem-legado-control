<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Usuario: ${usuario.nome}</h2>
<html:form action="DepartamentoUsuario.do">
    <table>
    <tr>
        <td>
            <div align="left">
                <html:button property="voltar" value="Voltar" styleClass="botao" onclick="document.location.href='Usuario.do'"/>
            </div>
        </td>
    </tr>
</table>

<div id="DepartamentoUsuario" style="display: block;">
    <fieldset>
        <legend>Informe o nome do Departamento e clique no consultar. </legend>
        <html:text property="nomeDepartamento" size="130"/>
        <br />
        <html:submit property="consultar" value="Consultar" styleClass="botao"/>
    </fieldset>
</div>
<br />

<display:table id="departamentoUsuario" name="listaDepartamentoUsuario" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./DepartamentoUsuario.do" pagesize="10"  class="tabelaDisplay">
    <display:column property="codigoDepartamentoDominio" title="Código" sortable="true" group="1"   />
    <display:column title="Departamento" sortable="true" group="1">${mapdepartamento[departamentoUsuario.codigoDepartamentoDominio.toString()].nome}</display:column>
    <display:column title="" href="./DepartamentoUsuario.do?id=${usuario.id}&metodo=removerDepartamento" paramId="codigo" paramProperty="codigoDepartamentoDominio" ><img align="middle" src="img/encrypted.png" width="22" height="22" border="0" alt="remove"/></display:column>
</display:table>
<br>
<display:table id="departamento" name="listaDepartamentos" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./DepartamentoUsuario.do" pagesize="10" class="tabelaDisplay">
    <display:column property="codigoDominio" title="Código" sortable="true" group="1"   />
    <display:column title="Departamento" sortable="true" >${mapdepartamento[departamento.codigoDominio.toString()].nome}</display:column>
    <display:column title="" href="./DepartamentoUsuario.do?id=${usuario.id}&metodo=adicionarDepartamento" paramId="codigo" paramProperty="codigoDominio" ><img align="middle" src="img/decrypted.png" width="22" height="22" border="0" alt="adiciona"/></display:column>
</display:table>
    </html:form>
