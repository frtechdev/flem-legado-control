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

        function lembrete(){
            if(document.getElementById("tipoAutenticacao").value == 1){
                alert("Você ainda não possui senha. Favor cadastrá-la clicando no ícone cadeado/chave na tela anterior!");
                return true;
            }
        }
        function alterarTipoSolicitacao(){
            Funcoes.tipoSolicitacaoPorLotacao($('lotacao').value, lotacao );
        }
        function lotacao(data){
            $('tipoSolicitacao').value = data;
        }
    </script>
    <html:javascript formName="usuarioForm" method="validar" />

    <h2>Alterar Usuario</h2>
    <html:form action="Usuario.do?metodo=alterar" onsubmit="return validar(this) && lembrete();">
        <html:hidden property="id" />
        <table>
            <tr><td>Nome: </td>
            <td><html:text property="nome" size="50" maxlength="50" styleClass="requerido" /> </td></tr>
            <tr><td>Usuário: </td>
            <td><html:text property="login" size="30" maxlength="15" styleClass="requerido" /> </td></tr>
            <tr><td>Departamento:</td>
                <td><html:select styleId="lotacao" property="lotacaoDominio" onchange="alterarTipoSolicitacao();">
                        <html:optionsCollection name="listadepartamento" value="codigoDominio" label="nome" />
                </html:select></td>
            </tr>
            <tr>
              <td>Tipo Autenticação:</td>
              <td>
              <html:select property="tipoAutenticacao" onchange="pedeSenha();" styleId="tipoAutenticacao">
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
          <tr><td colspan="2" align="center"><html:submit value="alterar" styleClass="botao" /> &nbsp; <html:button property="" value="voltar" onclick="javascript: history.go(-1);" styleClass="botao"/></td></tr>
        </table>
    </html:form>