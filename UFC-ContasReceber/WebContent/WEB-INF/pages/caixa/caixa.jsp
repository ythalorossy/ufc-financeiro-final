<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="tituloInterno">Caixa</span>

<fieldset>
<html:form action="/caixa">
	<table width="99%">
		<tr>
		<td>
			<html:hidden property="operacao" value="montarCaixa" />
			<input type="text" name="dataToList" value="" onkeyup="javascript:formatFieldData(this)" />
			<html:submit value="listar"/>
		</td>
		<td align="right"><span style="font-size: 18px; font-weight: b">${dataAtual}</span></td>
		</tr>
	</table>
</html:form>


<span style="color: red"><html:errors /></span>

<table width="99%" border="0" >
	<tr bgcolor="#c1c1c1">
		<th>Documento</th>
		<th>Descricao</th>
		<th>Data</th>
		<th>Valor</th>
		<th>Status</th>
		<th></th>
	</tr>

	<c:forEach items="${listCaixaDiaTO}" var="caixa" varStatus="status">
		<tr bgcolor="#f1f1f1">
			<td align="center">${caixa.doc}</td>
			<td>${caixa.descricaoServico}</td>
			<td>${caixa.dataPagamento}</td>
			<td align="right">${caixa.valor}</td>
			<td align="center">${caixa.status}</td>
			<td align="center"><html:form action="caixa">
				<html:hidden property="operacao" value="extornarCaixa" />
				<html:hidden property="theItem.id" value="${caixa.id}" />
				<html:hidden property="theItem.idContasReceber" value="${caixa.idContasReceber}" />
				<html:hidden property="theItem.idContasPagar" value="${caixa.idContasPagar}" />
				<html:hidden property="theItem.idCaixaEntradaSaida" value="${caixa.idCaixaEntradaSaida}" />
				<html:hidden property="theItem.id" value="${caixa.id}" />
				<html:hidden property="theItem.id" value="${caixa.status}" />
				<c:if test="${caixa.status!='Batido'}">
					<html:image src="./imagens/delete_X.png" style="cursor: pointer" alt="Extornar" border="0" title="Extornar" />
				</c:if>
				<input type="hidden" name="dataAtual" value="${dataAtual}">
			</html:form></td>

		</tr>
	</c:forEach>

</table>

<h3>Resumo do Caixa</h3>

<table width="99%" cellpadding="4" cellspacing="4">
	<tr bgcolor="#f1f1f1">
		<td width="20%"><span class="labelForm">Saldo Anterior</span></td>
		<td width="15%" align="right">${saldoAnterior }</td>
		<td width="65%">&nbsp;</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td><span class="labelForm">Saldo Dia</span></td>
		<td align="right">${saldoDia}</td>
		<td>&nbsp;</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td><span class="labelForm">Saldo Atual</span></td>
		<td align="right">${saldoAtual}</td>
		<td>&nbsp;</td>
	</tr>

</table>

<table width="99%">
	<tr>
		<td align="right" width="90%">
		<html:form action="/caixa">
			<html:hidden property="operacao" value="reabrirCaixa" />
			<html:hidden property="theItem.dataPagamento" value="${dataAtual}" />
			<html:submit value="Reabrir Caixar"></html:submit>
		</html:form>
		</td>
		<td align="right">
		<html:form action="/caixa">
			<html:hidden property="operacao" value="baterCaixa" />
			<html:hidden property="theItem.dataPagamento" value="${dataAtual}" />
			<html:submit value="Fechar Caixar"></html:submit>
		</html:form>
		</td>
	</tr>
</table>
