<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:errors />
<span class="tituloInterno">Pedido de Despesa - Itens</span>

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
<legend>Adicionar Item</legend>
<html:form action="/itensPedidoDespesa">
	<html:hidden property="theItem.id" />
	<html:hidden property="theItem.idDivisao" value="${idDivisao}" />
	<html:hidden property="theItem.nomeDivisao" value="${divisao}" />
	<html:hidden property="theItem.idPedidoDespesa" value="${idPedidoDespesa}" />
	
	<input type="hidden" name="operacao" value="${requestScope.operacao}" />
	
	<table width="90%" border="0" cellpadding="2" cellspacing="2">
	  <tr>
	    <td width="75%" rowspan="2" align="center" bgcolor="#c1c1c1">Especifica&ccedil;&atilde;o</td>
	    <td width="5%" rowspan="2" align="center" bgcolor="#c1c1c1">UND</td>
	    <td width="5%" rowspan="2" align="center" bgcolor="#c1c1c1">Qtde</td>
	    <td width="5%" align="center" bgcolor="#c1c1c1">Valor Estimado </td>
	    <td rowspan="3" valign="bottom"><html:image src="./imagens/add.png" style="cursor: pointer" alt="Adicionar" border="0" title="Adicionar" /></td>
	  </tr>
	  <tr>
	    <td width="10%" align="center" bgcolor="#c1c1c1">Unit&aacute;rio</td>
	  </tr>
	  <tr>
	    <td align="left"><html:text property="theItem.item" size="60"/></td>
	    <td align="center">
	    	<html:select property="theItem.unidade">
	    		<html:option value="UND">UNIDADE</html:option>
	    		<html:option value="CX">CAIXA</html:option>
	    		<html:option value="MT">METRO</html:option>
	    		<html:option value="M2">METRO2</html:option>
	    		<html:option value="MT3">METRO³</html:option>
	    		<html:option value="LT">LITRO</html:option>
	    		<html:option value="ML">MILILITRO</html:option>
	    		<html:option value="PCT">PACOTE</html:option>
	    		<html:option value="FRD">FARDO</html:option>
	    		<html:option value="RSN">RESMA</html:option>
	    		<html:option value="RL">ROLO</html:option>
	    		<html:option value="KG">QUILO</html:option>
	    		<html:option value="GR">GRAMO</html:option>
	    		<html:option value="MG">MILIGRAMO</html:option>
	    		<html:option value="OT">OUTROS</html:option>
	    	</html:select>
	    	
	    </td>
	    <td align="center"><html:text property="theItem.quantidade" size="5"/></td>
	    <td align="center"><html:text property="theItem.valorUnitario"/></td>
	  </tr>
	</table>
	
		<input type="hidden" name="operacao" value="${operacao}"/>
	
</html:form>
</fieldset>

<fieldset>
<legend>Itens</legend>
<html:form action="/itensPedidoDespesa" styleId="formInsertItemPedidoDespesa">
<div>
	<table bgcolor="#ffffff" width="99%">
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
						<td width="49%" align="center">Unitário</td>
						<td width="49%" align="center">Total</td>
					</tr>
				</table>
			</th>
			<th width="10%" valign="bottom">
				<img src="./imagens/delete.png" onclick="doSubmit('formInsertItemPedidoDespesa', 'delete')" style="cursor: pointer" alt="Excluir" border="0" title="Excluir" />
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
							<td width="49%" align="right">
								<label for="item[${status.index}].checked"></label>
								<html:hidden property="item[${status.index}].valorUnitarioCotado" /><span>${item.valorUnitarioCotado}</span>
							</td>
							<td width="49%" align="right">
								<label for="item[${status.index}].checked"></label>
								<html:hidden property="item[${status.index}].valorTotalCotado" /><span>${item.valorTotalCotado}</span>
							</td>
						</tr>
					</table>
				</td>
				<td align="center">
					<input type="checkbox" id="item[${status.index}].checked"
					name="item[${status.index}].checked" ${item.checked ? "checked='checked' " : ""} />
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<input type="hidden" name="operacao" value="" />
</html:form>
</fieldset>

<html:form action="itensPedidoDespesa">
	<table width="99%">
		<tr>
			<td width="80%">
				<input type="submit" value="Finalizar">
			</td>
			<td width="20%" align="right">
				Total:<strong><c:out value="${requestScope.totalPedidoDespesa}"/></strong>
			</td>
		</tr>

	</table>
	<input type="hidden" name="idPedidoDespesa" value = "${idPedidoDespesa}" >
	<input type="hidden" name="operacao" value="finalizarPD">
</html:form>
