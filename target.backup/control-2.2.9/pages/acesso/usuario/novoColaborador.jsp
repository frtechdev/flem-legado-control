<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/util.js"></script>
<script language="javascript">
    function pedeSenha(){
        if (document.getElementById("tipoAutenticacao").value == 1)
            document.getElementById("tableSenha").style.visibility = "visible";
        else
            document.getElementById("tableSenha").style.visibility = "hidden";
    }
    
    function validarLogin(){
            if(document.getElementById("login").value == ""){
                alert("Digite o login novamente!")
            }else{
                Funcoes.verificarLogin($('login').value, dados);   
            }
        } 
        function dados(variavel){
            if(variavel == true){
              alert("O login escolhido já está em uso , Informe um novo login!");  
            }else{
                alert("Login Disponível");
            }
        }

    function validarSenha(){
        if (document.getElementById("tipoAutenticacao").value == 1) {
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
    }

</script>
<h2>Novo Usuario</h2>
<html:form action="Colaborador.do?metodo=adicionar" onsubmit="return validarSenha();">
    <table>
        <tr><td>Nome: </td>
            <td><html:text property="nome" size="50" maxlength="50" /> </td></tr>
        <tr><td>Usuario: </td>
            <td><html:text property="login" size="30" maxlength="15" styleId="login" />
             <html:button property="" value="Verificar" onclick="validarLogin();" styleClass="botao" />
            </td></tr>
        <tr>
            <td>Tipo Autenticação:</td>
            <td>
                <html:select property="tipoAutenticacao" onchange="pedeSenha();" styleId="tipoAutenticacao">
                    <html:optionsCollection label="nome" value="id" name="tipos"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td>Tipo Solicitação</td>
            <td>
                <html:select property="tipoSolicitacao" styleId="tipoSolicitacao">
                    <html:optionsCollection label="nome" value="id" name="tiposS"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <table id="tableSenha" style="width: 100%; visibility: hidden;">
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
        <tr><td colspan="2" align="center"><html:submit value="adicionar" styleClass="botao" /></td></tr>
    </table>
</html:form>