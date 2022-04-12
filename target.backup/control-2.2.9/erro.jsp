<%@page contentType="text/html" isErrorPage="true"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <title><fmt:message key="aplicacao.nome" /></title>
    </head>
    <body>
        <div id="wrap">
             <jsp:include flush="false" page="inc/header.jsp" />
             <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Página de Erro</h2>
                <p>Ocorreu um erro Entre em contato com o NTO.</p>
            </div>

            <jsp:include flush="false" page="inc/footer.jsp" />

        </div>
    </body>
</html>
