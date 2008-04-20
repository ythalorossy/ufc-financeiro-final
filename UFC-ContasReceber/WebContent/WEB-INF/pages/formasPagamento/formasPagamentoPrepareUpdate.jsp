<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<span class="tituloInterno">Formas De Pagamento - Atualizacão</span>

<html:form action="/formasPagamento">

	<html:hidden property="theItem.id" value="${formasPagamentoTO.id}"/>
	Forma de Pagamento: 
	<br/>
	<html:text property="theItem.formaPagamento" size="25" value="${formasPagamentoTO.formaPagamento}"/>
	<html:hidden property="operacao" value="update"/>
	<html:image src="./imagens/accept.png" style="cursor: pointer" alt="Editar" border="0" title="Editar" />
</html:form>