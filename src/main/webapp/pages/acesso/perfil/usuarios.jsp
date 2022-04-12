<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<h2>Lista  de usuários</h2>
<html:form action="Perfil.do?metodo=usuarios" >
    
    <h5><u>Perfil: ${perfil.nome}</u></h5>
   
    <display:table id="usuarioperfil" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./Perfil.do?metodo=usuarios" pagesize="30"  class="tabelaDisplay">
        
        <display:column title="Usuario" property="nome" sortable="true"  />
        <display:column title="Login" property="login" sortable="true" />
        <display:column title="Nome" property="lotacao" sortable="true" />

        
    </display:table>
</html:form>