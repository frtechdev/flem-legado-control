<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<html:javascript formName="usuarioForm" method="validar" />

<h2>Departamento: ${mapDep[codigo].nome}</h2>
<html:form action="/DepartamentoCentroCusto.do?metodo=salvar_tipo_solicitacao&codigo=${codigo}">
    <table>
        <tr>
            <td>Tipo Solicitação de Viagem</td>
            <td>
                <html:select property="tipoSolicitacao" styleId="tipoSolicitacao">
                    <html:optionsCollection label="nome" value="id" name="tiposSolicitacao"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td>Liberação de Viagem Retroativa</td>
            <td>
                <label for="liberacaoViagemRetroativoSim">Sim</label> <html:radio property="liberacaoViagemRetroativo" styleId="liberacaoViagemRetroativoSim" value="true"/>
                <label for="liberacaoViagemRetroativoNao">Não</label> <html:radio property="liberacaoViagemRetroativo" styleId="liberacaoViagemRetroativoNao" value="false"/>
            </td>
        </tr>
        <tr><td colspan="2" align="center"><html:submit value="salvar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao" /></td></tr>
    </table>
</html:form>
