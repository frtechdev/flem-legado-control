<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<script language="javascript">
    function pedeSenha(){
        if(document.getElementById("tipoAutenticacao").value == 1)
            document.getElementById("tableSenha").style.visibility = "visible";
        else
            document.getElementById("tableSenha").style.visibility = "hidden";
    }

    function validarSenha(){
        var senha = document.getElementById("senha").value;
        var confirmaSenha = document.getElementById("confirmaSenha").value;
        if(senha == ''){
            alert("Digite a senha!");
            return false;
        }else if (senha != confirmaSenha) {
            alert("A senha não confere com a confirmação de senha");
            return false;
        }
        else {
            return true;
        }
    }

</script>

<html:javascript formName="usuarioForm" method="validar" />

<h2>Cadastrar/Alterar Senha</h2>
<html:form action="Usuario.do?metodo=alterarAutenticacao" onsubmit="return validarSenha();">
    <html:hidden property="id" />
    <table>
        <tr>
            <td>Tipo Autenticação:</td>
            <td>
                <html:select property="tipoAutenticacao" onchange="pedeSenha();" styleId="tipoAutenticacao" disabled="true">
                    <html:option value="">Selecione:</html:option>
                    <html:optionsCollection label="nome" value="id" name="tipos"/>
                </html:select>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <table id="tableSenha" style="width: 100%;">
                    <tr>
                        <td>Senha:</td>
                        <td><html:password property="senha" styleClass="requerido" styleId="senha"/></td>
                    </tr>
                    <tr>
                        <td>Confirmar Senha:</td>
                        <td><html:password property="confirmaSenha" styleClass="requerido" styleId="confirmaSenha"/></td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr><td colspan="2" align="center"><html:submit value="alterar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao"/></td></tr>
    </table>
</html:form>