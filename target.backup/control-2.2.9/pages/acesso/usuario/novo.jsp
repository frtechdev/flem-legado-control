<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/util.js"></script>
    <script>
        function pedeSenha(){
            if(document.getElementById("tipoAutenticacao").value == 1)
                document.getElementById("tableSenha").style.display = "block";
            else
                document.getElementById("tableSenha").style.display = "none";
        }

        function validarSenha(){
            if(document.getElementById("tipoAutenticacao").value == 1 && document.getElementById("senha").value == ''){
                alert("Digite a autenticação!");
                return false;
            }else {
                return true;
            }
        }
        function alterarTipoSolicitacao(){
            Funcoes.tipoSolicitacaoPorLotacao($('lotacao').value, lotacao );
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
        function lotacao(data){
            $('tipoSolicitacao').value = data;
        }
    </script>
    <html:javascript formName="usuarioForm" method="validar" />

    <h2>Novo Usuario</h2>
    <html:form action="Usuario.do?metodo=adicionar" onsubmit="return validar(this) && validarSenha();">
        <html:hidden property="id" />
        <table>
            <tr><td>Nome: </td>
            <td><html:text property="nome" size="50" maxlength="50" styleClass="requerido" /> </td></tr>
            <tr><td>Usuario: </td>
                <td><html:text property="login" size="30" maxlength="15" styleClass="requerido" styleId="login" />
                <html:button property="" value="Verificar" onclick="validarLogin();" styleClass="botao" /></td></tr>      
            <tr><td>Departamento:</td>
                <td><html:select styleId="lotacao" property="lotacaoDominio" onchange="alterarTipoSolicitacao();">
                        <html:option value="" />
                        <html:optionsCollection name="listadepartamento" value="codigoDominio" label="nome" />
                </html:select></td>
            </tr>
            <tr>
              <td>Tipo Autenticação:</td>
              <td>
                  <html:select property="tipoAutenticacao" onchange="pedeSenha();" styleId="tipoAutenticacao">
                  <html:option value="">Selecione:</html:option>
                  <html:optionsCollection label="nome" value="id" name="tipos"/>
              </html:select>
              </td>
            </tr>
            <tr>
              <td>Tipo Solicitação de Viagem</td>
              <td>
                  <html:select property="tipoSolicitacao" styleId="tipoSolicitacao">
                  <html:optionsCollection label="nome" value="id" name="tiposS"/>
              </html:select>
              </td>
            </tr>

            <table style="display:none;" id="tableSenha">
            <tr>
                  <td>Senha:</td>
                  <td><html:password property="senha" styleId="senha" styleClass="requerido"/></td>
            </tr>
            </table>
            <tr><td colspan="2" align="center"><html:submit value="adicionar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao"/></td></tr>
        </table>
    </html:form>
