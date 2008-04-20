<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<tiles:insert page="/WEB-INF/tiles/template.jsp">

	<!-- String com titulo da Página -->
	<tiles:put name="titulo" value="Controle Financeiro NUTEC" />

	<!-- Pagina com Topo -->
	<tiles:put name="topo" value="/WEB-INF/tiles/topo.jsp" />

	<!-- Pagina com Menu -->
	<tiles:put name="menu" value="/WEB-INF/tiles/menu.jsp" />

	<!-- Verifica se a página a ser carregada e vazia -->
	<c:choose>
		<c:when test="${!empty requestScope.loadPage}">
			<c:set var="loadPage" value="${requestScope.loadPage}" />
		</c:when>
		<c:when test="${empty requestScope.loadPage}">
			<c:set var="loadPage" value="/principal.do?operacao=paginaInicial" />
		</c:when>
	</c:choose>

	<!-- Pagina a ser carregada -->
	<tiles:put name="conteudo" value="${loadPage}" />
	
	<tiles:put name="rodape" value="/WEB-INF/tiles/rodape.jsp" />

</tiles:insert>
