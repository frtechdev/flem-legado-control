<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<h2>Nova Funcionalidade</h2>
<html:form action="Funcionalidade.do?metodo=adicionar" onsubmit="return validar(this);">
    <html:hidden property="id" />
      <table>
        <tr><td>Nome: </td>
        <td><html:text property="nome" size="50" maxlength="50" styleClass="requerido" /> </td></tr>
         <tr><td>Nome Curto: </td>
        <td><html:text property="nomeCurto" size="50" maxlength="40" styleClass="requerido" /> </td></tr>
         <tr><td>Aplicação: </td>
        <td><html:select property="idAplicacao" value="${aplicacao.id}" styleClass="requerido" >
            <html:optionsCollection property="listaAplicacao" value="id" label="nomeCurto" />
        </html:select>  </td></tr>
        <tr><td>Descrição: </td>
        <td><html:textarea property="descricao" cols="50" rows="3" styleClass="requerido" /> </td></tr>
        <tr><td colspan="2" align="center"><html:submit value="adicionar" styleClass="botao"/> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td></tr>
    </table>
</html:form>