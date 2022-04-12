<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Lista  de usuários</h2>
<html:form action="Funcionalidade.do?metodo=usuarios" >
    
    <h5><u>Funcionalidade: ${funcionalidade.aplicacao.nome} - ${funcionalidade.nome}</u></h5>
   
    <display:table id="usuariofuncionalidade" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./Funcionalidade.do?metodo=usuarios" pagesize="30"  class="tabelaDisplay">
        
        <display:column title="Usuario" property="nome" sortable="true"  />
        <display:column title="Login" property="login" sortable="true" />
        <display:column title="Lotação" property="lotacao" sortable="true" />
        <display:column title="Perfil" property="perfil" sortable="true" />
        
    </display:table>
</html:form>