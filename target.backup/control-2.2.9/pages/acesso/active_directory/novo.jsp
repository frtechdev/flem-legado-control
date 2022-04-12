<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://flem.org.br/tags-html" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html:javascript formName="adForm" method="validar" />

<h2>Agendamento de Bloqueio para ${usuario.nome}</h2>
<html:form action="ActiveDirectory.do" onsubmit="return validar(this);">
    <html:hidden property="bloqueio.id" value="${usuario.agendamento.id}"/>
    <html:hidden property="bloqueio.usuario.id" value="${usuario.id}"/>
    <html:hidden property="bloqueio.usuario.login" value="${usuario.login}"/>
    <table>
        <tr>
            <td>Data inicial do bloqueio </td>
            <td><html:data styleId="dataInicio" property="bloqueio.dataInicio" size="10" styleClass="requerido" value="${usuario.agendamento.dataInicio}" /> </td>
        </tr>
        <tr>
            <td>Data para desbloquear </td>
            <td><html:data styleId="dataFim" property="bloqueio.dataFim"  size="10" styleClass="requerido" value="${usuario.agendamento.dataFim}"/> </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <html:submit value="salvar" styleClass="botao" property="metodo"/> &nbsp; 
                <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /> &nbsp;
                <c:if test="${usuario.agendamento.id ne null}">
                    <html:submit value="excluir" styleClass="botao" property="metodo"/>
                </c:if>
            </td>
        </tr>
    </table>
</html:form>
