<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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
			<td colspan="2"><input type="submit" value="pesquisar"></td>
		</tr>
	</table>
</html:form>