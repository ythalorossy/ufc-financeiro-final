<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="notaFiscal">
<fieldset>
<legend>Nota Fiscal</legend>
<table width="99%" border="0">
	<tr bgcolor="#c1c1c1">
		<th>
			Nota Fiscal
		</th>
		<th>
			Número do Processo
		</th>
		<th>
			Cliente
		</th>
		<th>
			Data de Saída
		</th>
		<th>
			Valor
		</th>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td> 
			<c:out value="${requestScope.numeroNotaFiscal}"></c:out>
		</td>
		<td > 
			<c:out value="${requestScope.numeroProcesso}"></c:out>
		</td>
		<td align="center">
			<c:out value="${requestScope.cliente}"></c:out>
		</td>
		<td align="center">
			<c:out value="${requestScope.dataSaida}"></c:out>
		</td>
		<td align="right" width="10%">
			<c:out value="${requestScope.valorTotal}"></c:out>
		</td>
	</tr>
</table>
</fieldset>
<fieldset>
	<legend>Itens da Nota Fiscal</legend>
	<table width="99%" border="0">
		<tr bgcolor="#c1c1c1">
			<th>
				Divisão
			</th>
			<th>
				Descrição
			</th>
			<th>
				Quantidade
			</th>
			<th>
				Valor Unitário
			</th>
			<th>
				Valor Total
			</th>
		</tr>
		
			<c:forEach var="item" items="${itensNotaFiscal}" varStatus="status">
			<tr bgcolor='${(status.index % 2) == 0 ? "#F1F1F1" : "#FFFFFF"}'>
			
				<td align="center"><span>${item.idDivisao}</span>
				</td>
				<td align="center">
					<span>${item.servico }</span>
				</td>
				<td align="center">
					<span>${item.quantidade }</span>
				</td>
				<td align="right" width="10%">
					<span>${item.valor}</span>
				</td>
				<td align="right" width="10%">
					<span>${item.valorTotal}</span>
				</td>
				
			</tr>
		</c:forEach>
		
	</table>
</fieldset>
<fieldset>
	<legend>Parcelas da Nota Fiscal</legend> 
	<table width="99%" border="0">
		<tr bgcolor="#c1c1c1">
			<th>
				Número
			</th>
			<th>
				Data de Pagamento
			</th>
			<th>
				Valor
			</th>
		</tr>
		
			<c:forEach var="item" items="${parcelas}" varStatus="status">
			<tr bgcolor='${(status.index % 2) == 0 ? "#F1F1F1" : "#FFFFFF"}'>
			
				<td align="center"><span>${item.numeroParcela}</span>
				</td>
				<td align="center" width="10%">
					<span>${item.dataPagamento }</span>
				</td>
				<td align="right" width="10%">
					<span>${item.valor}</span>
				</td>
			</tr>
		</c:forEach>
		
	</table>
</fieldset>
<html:hidden property="theItem.id" />
<html:hidden property="theItem.notaFiscal" />
<html:hidden property="theItem.dataSaida" />
<html:hidden property="theItem.numeroProcesso" />
<html:hidden property="theItem.id" />
<html:hidden property="theItem.idCliente" />
<html:hidden property="theItem.valorNota" />
<html:hidden property="theItem.tipoNota" />
<html:hidden property="theItem.status" />
<input type="hidden" name="operacao" value="reabrirNota"/>
<html:submit>Reabrir Nota Fiscal?</html:submit>
</html:form>