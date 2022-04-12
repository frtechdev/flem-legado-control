<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<h2>Alterar Perfil</h2>
<html:form action="Perfil.do?metodo=alterar" onsubmit="return validar(this);">
    <html:hidden property="id" />
    <table>
        <tr><td>Nome: </td>
        <td><html:text property="nome" size="50" maxlength="50" styleClass="requerido" /> </td></tr>
        <tr><td>Nome Curto: </td>
        <td><html:text property="nomeCurto" size="30" maxlength="10" styleClass="requerido" /> </td></tr>
        <tr><td>Descri��o: </td>
        <td><html:textarea property="descricao" cols="50" rows="3" styleClass="requerido" /> </td></tr>
        <tr><td align="center" colspan="2"><html:submit value="alterar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td></tr>
    </table>
</html:form>