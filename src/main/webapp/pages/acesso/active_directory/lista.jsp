<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://flem.org.br/acesso-tag" prefix="acesso"%>
<h2>Lista de funcionários (Autenticação Active Directory)</h2>
<html:form action="ActiveDirectory.do" >
    <table>
        <tr>
            <td>Nome:</td>
            <td><html:text property="nome" size="50" value="${adAction_filtro_nome}"/></td>
        </tr>
        <tr>
            <td><div align="left"><html:submit property="metodo" styleClass="botao" value="filtrar"/></div></td>
        </tr>
    </table>

    <display:table id="usuario" name="lista" defaultsort="1" sort="list" export="false" excludedParams="metodo" requestURI="./ActiveDirectory.do" pagesize="30"  class="tabelaDisplay">

        <display:column property="nome" title="Nome" sortable="true"  />
        <display:column property="login" title="Login" sortable="true" />
        <display:column property="lotacao" title="Lotação" sortable="true"  />
        <display:column property="dataInicioBloqueio" title="Inicio Agendamento" format="{0,date,dd/MM/yyyy}" />
        <display:column property="dataFimBloqueio" title="Fim Agendamento" format="{0,date,dd/MM/yyyy}" />
        <acesso:verificaPermissao funcionalidade="bloqueioAD">
            <display:column title="Status" sortable="true">

                <c:if test="${usuario.bloqueadoAd}">
                    <html:link  href="./ActiveDirectory.do?metodo=desbloquear&id=${usuario.id}" >
                        <img src="<%=request.getContextPath()%>/img/locked.png" onclick="if(confirm('Clique em OK para confirmar o desbloqueio')) {return true;} return false;"   />
                    </html:link>
                </c:if>
                <c:if test="${!usuario.bloqueadoAd}">
                    <html:link  href="./ActiveDirectory.do?id=${usuario.id}&metodo=bloquear"   >
                        <img src="<%=request.getContextPath()%>/img/unlocked.png"  border="0" onclick="if(confirm('Clique em OK para confirmar o bloqueio')) {return true;} return false;" />
                    </html:link>
                </c:if>
            </display:column>
            <display:column title="Agendamento">
                <html:link  href="./ActiveDirectory.do?id=${usuario.id}&metodo=agendar"  >
                    <img src="<%=request.getContextPath()%>/img/month.png"  border="0" />
                </html:link>
            </display:column>
        </acesso:verificaPermissao>

    </display:table>
</html:form>

