<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="tituloInterno">Acompanhamento de Pedido de Despesa</span>

<html:form action="acompanhamentoPD">

	<html:errors />
	<html:hidden property="theItem.pd" value="${acompanhamentoPDForm.theItem.pd}" />

	<table width="98%" cellpadding="2" cellspacing="2">
		<tr>
			<td><h3>Número:	${acompanhamentoPDForm.theItem.numeroPD}</h3></td>
		</tr>
		
		<tr bgcolor="#c1c1c1">
			<td>Data Recebimento</td>
			<td>Data Envio</td>
			<td>Divisão</td>
		</tr>
		<tr bgcolor="#f1f1f1" height="25">
			<td>
				<html:hidden property="theItem.dataRecebimento" value="${acompanhamentoPDForm.theItem.dataRecebimento}" />
				${acompanhamentoPDForm.theItem.dataRecebimento}
			</td>
			<td>
				<html:hidden property="theItem.dataEnvio" value="${acompanhamentoPDForm.theItem.dataEnvio}" />
				${acompanhamentoPDForm.theItem.dataEnvio}
			</td>
			<td>
				<html:select property="theItem.divisao">
					<c:forEach items="${listDivisao}" var="divisao">
						<html:option value="${divisao.id}">${divisao.nome}</html:option>
					</c:forEach>
				</html:select>
			</td>
		</tr>
		<tr bgcolor="#c1c1c1">
			<td colspan="3">Observação</td>
		</tr>
		<tr bgcolor="#f1f1f1">
			<td colspan="3">
			<html:textarea property="theItem.observacao" rows="5" cols="100"   /></td>
		</tr>
		<tr>
			<td colspan="3"><html:submit value="Adicionar"></html:submit></td>
		</tr>

	</table>
	<html:hidden property="operacao" value="save"/>
</html:form>

<hr>

<table width="98%">
<tr bgcolor="#c1c1c1">
	<th>Data Envio</th>
	<th>Data Recebimento</th>
	<th>Divisão</th>
	<th>Observação</th>
</tr>

<c:forEach items="${listAPD}" var="apd" varStatus="status"> 
<tr bgcolor="#f1f1f1">
	<td width="10%">${apd.dataEnvio}</td>
	<td width="10%">${apd.dataRecebimento}</td>
	<td width="10%">${apd.nomeDivisao}</td>
	<td width="70%">${apd.observacao}</td>
</tr>
</c:forEach>
</table>
