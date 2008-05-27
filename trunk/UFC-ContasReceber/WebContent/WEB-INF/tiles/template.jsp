<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:getAsString name="titulo" /></title>

<jsp:include page="/WEB-INF/tiles/style.css" />

<script type="text/javascript" language="javascript">
    /*
    * Variavel usada para controle do Ajax, armazena do valor
    * absoluto da url do sistema.
    */
    var urlSistema = "<%=request.getContextPath()%>";
</script>

<jsp:include page="/WEB-INF/tiles/script.js" />

</head>
<body>

<div id="geral">

<div id="topo"><tiles:insert attribute="topo" /></div>
<div id="menu"><tiles:insert attribute="menu" /></div>
<div id="conteudo"><tiles:insert attribute="conteudo" /></div>
<div id="rodape"><tiles:insert attribute="rodape" /></div>

</div>

</body>
</html>