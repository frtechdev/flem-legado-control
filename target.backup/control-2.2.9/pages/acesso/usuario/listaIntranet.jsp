<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<h2>Usuario: ${usr.nome}</h2>
<html:form action="UsuarioAplicativoIntranet.do?id=${id}" >
<display:table id="perfilIntranet1" name="listaComAplicativos"  defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./UsuarioAplicativoIntranet.do" pagesize="15"  class="tabelaDisplay">
    <display:column  property="nome" title="Nome" sortable="true" group="1"   />
    <display:column property="titulo" title="Descrição" sortable="true"  />
    <display:column title="" href="./UsuarioAplicativoIntranet.do?metodo=removerPerfilIntranet" paramId="idapp" paramProperty="id" ><img align="middle" src="img/encrypted.png" width="22" height="22" border="0" alt="remove"/></display:column>
</display:table>
<br>

<div id="UsuarioAplicativoIntranet" style="display: block;">
        <fieldset>
            <legend>Informe o Nome para filtrar e clique no consultar. </legend>
            <html:text property="nomeAplicacaoIntranet" size="130"/>
            <br />
            <html:submit property="consultar" value="Consultar" styleClass="botao" />
        </fieldset>
</div>
            
<display:table id="perfilIntranet2" name="listaSemAplicativos" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./UsuarioAplicativoIntranet.do" pagesize="15" class="tabelaDisplay">
    <display:column property="nome" title="Nome" sortable="true" group="1"   />
    <display:column property="titulo" title="Descrição" sortable="true"  />
    <display:column title="" href="./UsuarioAplicativoIntranet.do?metodo=adicionarPerfilIntranet" paramId="idapp" paramProperty="id" ><img align="middle" src="img/decrypted.png" width="22" height="22" border="0" alt="adiciona"/></display:column>
</display:table>
</html:form>
    


