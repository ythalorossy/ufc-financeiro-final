<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3 style="color: red;"><html:errors /></h3>
<span class="tituloInterno">Pedido de Despesa - Cotação</span>

<fieldset>
<legend>Dados do Pedido de Despesa</legend>
<table width="99%"> 
	<tr>
		<th align="left" width="20%"><label>Numero do PD</label></th>
		<th align="left" width="20%"><label>Divisão</label></th>
		<th align="left" width="20%"><label>Laboratório</label></th>
		<th align="left" width="30%"><label>Serviço</label></th>
		<th align="left" width="20%"><label>Data</label></th>
		
	</tr>
	<tr>
		<td> 
			${requestScope.numeroPD}
		</td>
		<td> 
			${requestScope.divisao}
		</td>
		<td> 
			${requestScope.laboratorio}
		</td>
		<td>
			${requestScope.tipoServico}
		</td>
		
		<td>
			${requestScope.dataPD}
		</td>
	</tr>
	<tr>
		<th align="left" width="20%" colspan="4"><label>Justificativa</label></th>
	</tr>
	<tr>
		<td colspan="4">
			${requestScope.justificativa}
		</td>
	</tr>
</table>
</fieldset>

<fieldset>
<legend>Cotar Item</legend>
<html:form action="/itensPedidoDespesa">
	<input type="hidden" name="operacao" value="${requestScope.operacao}" />
	
	<table width="90%" border="0" cellpadding="2" cellspacing="2">
	<tr bgcolor="#666666">
		<th align="center" width="40%"><span style="color: white">Item</span></th>
		<th style="color: white" width="10%"><label>Unidade</label></th>
		<th style="color: white" width="10%"><label>Quantidade</label></th>
		<th style="color: white" width="20%">
			<table width="100%">
				<tr>
					<th colspan="2">
						<span style="color: white">Valor Estimado</span>
					</th>
				</tr>
				<tr bgcolor="#c1c1c1">
					<td width="49%" align="center">Unitário</td>
					<td width="49%" align="center">Total</td>
				</tr>
			</table>
		</th>
		<th style="color: white" width="20%">
			<table width="100%">
				<tr>
					<th colspan="2">
						<span style="color: white">Valor Cotado</span>
					</th>
				</tr>
				<tr bgcolor="#c1c1c1">
					<td width="49%" align="center" colspan="2">Unitário</td>
					
				</tr>
			</table>
		</th>
	</tr>
	<c:forEach var="item" items="${itensPedidoDespesaForm.items}" varStatus="status">
			<tr bgcolor='${(status.index % 2) == 0 ? "#F1F1F1" : "#FFFFFF"}'>
			
				<html:hidden property="item[${status.index}].id"/>
				<html:hidden property="item[${status.index}].idDivisao"/>
				<html:hidden property="item[${status.index}].nomeDivisao"/>
				<html:hidden property="item[${status.index}].idPedidoDespesa"/>
			
				<td align="left">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].item" /><span>${item.item}</span>
				</td>
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].unidade" /><span>${item.unidade }</span>
				</td>
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].quantidade" /><span>${item.quantidade }</span>
				</td>
				<td align="center">
					<table width="100%">
						<tr>
							<td width="49%" align="right">
								<label for="item[${status.index}].checked"></label>
								<html:hidden property="item[${status.index}].valorUnitario" /><span>${item.valorUnitario}</span>
							</td>
							<td width="49%" align="right">
								<label for="item[${status.index}].checked"></label>
								<html:hidden property="item[${status.index}].valorTotal" /><span>${item.valorTotal}</span>
							</td>
						</tr>
					</table>
				</td>
				<td align="center">
					<table width="100%">
						<tr>
							<td width="49%" align="right" colspan="2">
								<html:text property="item[${status.index}].valorUnitarioCotado"/>
							</td>


						</tr>
					</table>
				</td>
				
			</tr>
		</c:forEach>
	</table>
	<table width="99%">
		<tr>
			<td width="60%">
				<input type="submit" value="Finalizar">
			</td>
			<td width="40%" align="left">
				Total Previsto:<strong><c:out value="${requestScope.totalPedidoDespesa}"/></strong>
			</td>
		</tr>

	</table>
	<input type="hidden" name="idPedidoDespesa" value = "${idPedidoDespesa}" >
	<input type="hidden" name="operacao" value="${operacao}"/>
</html:form>