<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html:html>
<span class="tituloInterno">Pedido de Despesa - Cotação</span>
<html:form action="/itensPedidoDespesa">
<table>
	<tr>
		<td>
			Pedido de Despesa
		</td>
		<td>
		<select name="idPedidoDespesa">
			<c:forEach var="pd" items="${listPedido}">
					<option value="${pd.id}">Pedido: ${pd.numeroPD} &nbsp;&nbsp;, Divisão: ${pd.nomeDivisao}</option>
			</c:forEach>
		</select>
		</td>
		<td><html:image src="./imagens/add.png" style="cursor: pointer" alt="Adicionar" border="0" title="Adicionar" /></td>
	</tr>
</table>
<input type="hidden" name="operacao" value="prepareCotarPedidoDespesa"/>
	
</html:form>


</html:html>