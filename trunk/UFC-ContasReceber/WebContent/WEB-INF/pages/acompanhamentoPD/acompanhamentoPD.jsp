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
			<td colspan="4"><h3>Número:	${acompanhamentoPDForm.theItem.numeroPD}</h3></td>
		</tr>
		<tr bgcolor="#c1c1c1">
			<th>Data Recebimento</th>
			<th>Data Envio</th>
			<th>Divisão</th>
			<th>UPD</th>
		</tr>
		<tr bgcolor="#f1f1f1">
			<td>
				<html:hidden property="theItem.dataRecebimento" value="${acompanhamentoPDForm.theItem.dataRecebimento}" />
				${acompanhamentoPDForm.theItem.dataRecebimento}
			</td>
			<td>
				<html:hidden property="theItem.dataEnvio" value="${acompanhamentoPDForm.theItem.dataEnvio}" />
				${acompanhamentoPDForm.theItem.dataEnvio}
			</td>
			
			<td>
				<html:select property="theItem.divisao"
					onchange="javascript:makeRequest('/ajax','idDivisao',this.options[this.selectedIndex].value, 'laboratorio','laboratorios')">
					<html:option value=""></html:option>
					<c:forEach var="divisao" items="${listDivisao}">
						<html:option value="${divisao.id}">${divisao.nome}</html:option>
					</c:forEach>
				</html:select>
			</td>
			
			<td>
				<html:select styleId="laboratorios" property="theItem.laboratorio">
					<html:option value=""></html:option>
				</html:select>
			</td>
		</tr>
		<tr bgcolor="#c1c1c1">
			<td colspan="4">Observação</td>
		</tr>
		<tr bgcolor="#f1f1f1">
			<td colspan="4">
				<html:textarea property="theItem.observacao" rows="5" cols="100"/>
			</td>
		</tr>
		<tr>
			<td colspan="4"><html:submit value="Adicionar"></html:submit></td>
		</tr>

	</table>
	<html:hidden property="operacao" value="save"/>
</html:form>

<hr>

<table width="98%" cellpadding="2" cellspacing="2">
<tr bgcolor="#c1c1c1">
	<th>Data Envio</th>
	<th>Data Recebimento</th>
	<th>Divisão</th>
	<th>UPD</th>
	<th>Observação</th>
</tr>
<c:forEach items="${listAPD}" var="apd" varStatus="status"> 
<tr bgcolor="#f1f1f1">
	<td width="10%">${apd.dataEnvio}</td>
	<td width="10%">${apd.dataRecebimento}</td>
	<td width="10%">${apd.nomeDivisao}</td>
	<td width="10%">${apd.nomeLaboratorio}</td>
	<td width="70%"><div style="font-size:10px">${apd.observacao}</div></td>
</tr>
</c:forEach>
</table>
