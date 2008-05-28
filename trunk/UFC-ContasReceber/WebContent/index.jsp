<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>


<tiles:insert page="/WEB-INF/tiles/template.jsp">

	<!-- String com titulo da Página -->
	<tiles:put name="titulo" value="Controle Financeiro NUTEC" />

	<!-- Pagina com Topo -->
	<tiles:put name="topo" value="/WEB-INF/tiles/topo.jsp" />

	<!-- Pagina com Menu -->
	<tiles:put name="menu" value="" />

	<tiles:put name="conteudo" value="/login.jsp" />
	
	<tiles:put name="rodape" value="" />

</tiles:insert>
