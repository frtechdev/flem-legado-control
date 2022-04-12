<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="autentica"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />

        <title><fmt:message key="aplicacao.nome" /></title>
    </head>
    <body>
        <autentica:autentica sistema="control" />
        <div id="wrap">
                       <jsp:include flush="false" page="/inc/header.jsp" />
                       <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
               <!-- conteudo -->
            </div>

            <jsp:include flush="false" page="/inc/footer.jsp" />

        </div>
    </body>
</html>
