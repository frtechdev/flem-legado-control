<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<h2>Novo Aplicativo Intranet</h2>
<html:form action="/AplicativoIntranet.do?metodo=adicionar" onsubmit="return validar(this);" >
    <html:hidden property="id" />
    <table>
        <tr><td>Nome: </td>
        <td><html:text property="nome" size="50" maxlength="50" styleClass="requerido" /> </td></tr>
        <tr><td>Titulo: </td>
        <td><html:text property="titulo" size="30" maxlength="50" styleClass="requerido" /> </td></tr>
        <tr><td>URL: </td>
        <td><html:text property="url" size="30" maxlength="50" styleClass="requerido" /> </td></tr>
        <tr><td>Target: </td>
        <td><html:text property="target" size="30" maxlength="50" styleClass="requerido" /> </td></tr>
        <tr><td>Imagem: </td>
        <td><html:text property="imagem" size="30" maxlength="50" styleClass="requerido" /> </td></tr>
        <tr><td>Ordem: </td>
        <td><html:text property="ordem" size="30" maxlength="2" styleClass="requerido" /> </td></tr>
        <tr><td>Ativo: </td>
         <td><html:text property="ativo" size="1" maxlength="1" styleClass="requerido" /> (Exemplo: 0 ou 1)</td></tr>
        <tr><td>Segurança: </td>
        <td><html:text property="seguranca" size="1" maxlength="1" styleClass="requerido" /> (Exemplo: 0 ou 1)</td></tr>

        <tr><td colspan="2" align="center"><html:submit value="adicionar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td></tr>
    </table>
</html:form>