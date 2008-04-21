<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/tiles/style.css" />
<jsp:include page="/WEB-INF/tiles/script.js" />


<br />
<br />
<br />
<br />
<br />
<br />

<br>
<table width="20%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td>
		<fieldset>
		<legend><span class="tituloInterno">Sistema Financeiro</span></legend>
		<html:form action="/logon">
			<input type="hidden" name="operacao" value="login">
			<table>
				<tr>
					<td>Usuario:</td>
					<td><input type="text" name="username"></td>
				<tr>
				<tr>
					<td>Senha:</td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit"
						value="Acessar"></td>
				</tr>
				<tr>
				<td>
					<c:out value="${errorLogin}"></c:out>
				</td>
				</tr>
			</table>
		</html:form></fieldset>

		</td>
	</tr>


</table>

