<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Perfil: ${prf.nome}</h2>
<display:table id="funcionalidade" name="listaPerfil" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./PerfilFuncionalidade.do" pagesize="10"  class="tabelaDisplay">
    <display:column property="aplicacao.nomeCurto" title="Aplicacao" sortable="true" group="1"   />
    <display:column property="nome" title="Nome" sortable="true"  />
    <display:column title="" href="./PerfilFuncionalidade.do?metodo=removerAcesso" paramId="fun" paramProperty="id" ><img align="middle" src="img/encrypted.png" width="22" height="22" border="0" alt="remove"/></display:column>
</display:table>
<html:form action="PerfilFuncionalidade.do" styleId="aplicacaoForm">
        <table>
        <tr>
            <td>Aplicação</td>
            <td>
                <html:select property="idAplicacao" onchange="jQuery('#aplicacaoForm').submit()">
                    <html:option value="">Selecione</html:option>
                    <html:optionsCollection label="nome" value="id" name="listaDeAplicacoes"/>
                </html:select>
           </td>
        </tr>
        </table>
</html:form>
<display:table id="funcionalidade2" name="listaFuncionalidades" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./PerfilFuncionalidade.do" pagesize="10" class="tabelaDisplay">
    <display:column property="aplicacao.nomeCurto" title="Aplicacao" sortable="true" group="1"   />
    <display:column property="nome" title="Nome" sortable="true"/>
    <display:column title="" href="./PerfilFuncionalidade.do?metodo=adicionarAcesso" paramId="fun" paramProperty="id" ><img align="middle" src="img/decrypted.png" width="22" height="22" border="0" alt="adiciona"/></display:column>
</display:table>
<div align="left"><html:button property="" value="voltar" onclick="location.href='Perfil.do'" styleClass="botao" /></div>
<br />