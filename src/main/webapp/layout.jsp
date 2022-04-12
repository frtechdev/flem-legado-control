<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="autentica"%>
<%@taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/tags-html" prefix="html" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title><fmt:message key="aplicacao.nome" /></title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/800px.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
        <html:recursos/>
    </head>

    <body>
        <autentica:autentica sistema="control" />
        <div id="wrap">
            <tiles:insert attribute="header" />
            <div id="menu">
                <tiles:insert attribute="menu"/>
            </div>
            <div id="content">
                <tiles:insert attribute="body" />
            </div>
            <tiles:insert attribute="footer"/>
        </div>
    </body>
    <msg:alert escopo="session"/>
</html>