<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<h2>Nova Aplicação</h2>
<html:form action="/Aplicacao.do?metodo=adicionar" onsubmit="return validar(this);" >
    <html:hidden property="id" />
    <table>
        <tr><td>Nome: </td>
        <td><html:text property="nome" size="50" maxlength="50" styleClass="requerido" /> </td></tr>
        <tr><td>Nome Curto: </td>
        <td><html:text property="nomeCurto" size="30" maxlength="10" styleClass="requerido" /> </td></tr>
        <tr><td>Descrição: </td>
        <td><html:textarea property="descricao" cols="50" rows="3" styleClass="requerido" /> </td></tr>
        <tr><td>Versão: </td>
        <td><html:text property="versao" size="30" maxlength="10" styleClass="requerido" /> </td></tr>
        <tr><td>Linguagem: </td>
        <td><html:text property="linguagem" size="30" maxlength="30" styleClass="requerido" /> </td></tr>
        <tr><td>Servidor: </td>
        <td><html:text property="servidor" size="30" maxlength="30" styleClass="requerido" /> </td></tr>
        <tr><td>Observação: </td>
        <td><html:textarea property="observacao" cols="50" rows="3" /></td></tr>
        <tr><td colspan="2" align="center"><html:submit value="adicionar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td></tr>
    </table>
</html:form>
