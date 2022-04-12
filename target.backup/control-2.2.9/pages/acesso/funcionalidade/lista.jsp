<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Lista Funcionalidades</h2>
<html:form action="Funcionalidade.do" >
     <table>
        <tr>
            <td>Aplicação</td>
            <td>
                <html:select property="idAplicacao">
                    <html:option value="">Selecione</html:option>
                    <html:optionsCollection label="nome" value="id" name="listaDeAplicacoes"/>
                </html:select>
           </td>
        </tr>
        <tr>
            <td>
                <div align="left">
                    <html:submit property="metodo" styleClass="botao" value="filtrar"/> 
                </div>
            </td>
        </tr>
    </table>
    <div align="right"><html:submit value="excluir" property="metodo" onclick="if(confirm('Deseja realmente excluir o(s) registro(s) selecionado(s)?')) {return true;} return false;" styleClass="botao" /></div>
    <display:table id="funcionalidade" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./Funcionalidade.do" pagesize="30"  class="tabelaDisplay">
        <display:column title="Aplicacao" property="aplicacao.nomeCurto" sortable="true" group="1" />
        <display:column ><input type="checkbox" name="ids_exclusao" value="${funcionalidade.id}"/></display:column>
        <display:column title="Nome Curto" property="nomeCurto" sortable="true" />
        <display:column title="Nome" property="nome" sortable="true" />
        <display:column title="Descrição" property="descricao" sortable="true" />
        <display:column title="" href="./Funcionalidade.do?metodo=selecionar" paramId="id" paramProperty="id" ><img align="middle" src="img/edit.png" width="22" height="22" border="0" alt="edit"/></display:column>
        <display:column title="" href="./Funcionalidade.do?metodo=usuarios" paramId="id" paramProperty="id" ><img align="middle" src="img/usr.png" width="22" height="22" border="0" alt="edit"/></display:column>
    </display:table>
</html:form>