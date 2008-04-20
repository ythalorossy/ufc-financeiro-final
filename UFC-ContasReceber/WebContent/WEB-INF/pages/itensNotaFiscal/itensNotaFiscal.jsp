<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="tituloInterno">Nota Fiscal - Itens</span>

<fieldset>
<legend>Dados da Nota Fiscal</legend>
<table width="99%"> 
	<tr>
		<th align="left" width="20%"><label>Nota Fiscal</label></th>
		<th align="left" width="20%"><label>Número do Processo</label></th>
		<th align="left" width="20%"><label>Número do Contrato</label></th>
		<th align="left" width="30%"><label>Cliente</label></th>
		<th align="left" width="20%"><label>Data da Saida</label></th>
	</tr>
	<tr>
		<td> 
			${requestScope.nf}
		</td>
		<td> 
			${requestScope.numeroProcesso}
		</td>
		<td> 
			${requestScope.numeroContrato}
		</td>
		<td>
			${requestScope.cliente}
		</td>
		
		<td>
			${requestScope.data}
		</td>
	</tr>
</table>
</fieldset>

<fieldset>
<legend>Adicionar Item</legend>
<html:form action="/itensNotaFiscal">
	<html:hidden property="theItem.id" />
	<html:hidden property="theItem.idCliente" value="${idCliente}" />
	
	<input type="hidden" name="operacao" value="${requestScope.operacao}" />
	<table border=0>
		<tr>
			<td width="10%">Divisao</td>
			<td width="50%">Laboratório <html:errors property="servico"/></td>
			<td width="50%">Serviço <html:errors property="servico"/></td>
			<td width="10%">Quantidade <html:errors property="quantidade"/></td>
			<td width="25%">Valor <html:errors property="valorVazio"/></td>
			<td></td>
		</tr>
		<tr>
			<td bgcolor="#f1f1f1">
				<html:select property="theItem.idDivisao">
					<c:forEach var="divisao" items="${divisao}">
						<html:option value="${divisao.id}">${divisao.nome}</html:option>
					</c:forEach>
				</html:select>
			</td>
			<td bgcolor="#f1f1f1">
				<html:select property="theItem.idLaboratorio">
					<html:option value=""></html:option>
					<c:forEach var="laboratorio" items="${laboratorios}">
						<html:option value="${laboratorio.id}">${laboratorio.nome}</html:option>
					</c:forEach>
				</html:select>
			</td>
			<td>
				<html:text property="theItem.servico" value="" size="35" />
			</td>
			<td>
				<html:text property="theItem.quantidade" value="" size="10"/>
			</td>

			<td>
				<html:text property="theItem.valor" value="" size="10"/>
			</td>

			<td align="right">
				<html:image src="./imagens/add.png" style="cursor: pointer;" alt="Adicionar Item" border="0" title="Adicionar Item" />
			</td>
		</tr>
	</table>
			<html:hidden property="theItem.idNotaFiscal" value="${requestScope.idNotaFiscal}" />
		<input type="hidden" name="operacao" value="${operacao}"/>
	
</html:form>
</fieldset>

<fieldset>
<legend>Itens</legend>
<html:form action="/itensNotaFiscal" styleId="formInsertItemNotaFiscal">
<div>
	<table width="99%">
		<tr bgcolor="#c1c1c1">
			<th width="15%">Divisão</th>
			<th width="15%">Laboratório</th>
			<th width="38%">Serviço</th>
			<th width="10%">Quantidade</th>
			<th width="10%">Valor Unitário</th>
			<th width="10%">Valor Total</th>
			<th width="2%" valign="bottom">
			<html:image src="./imagens/delete.png" onclick="doSubmit('formInsertItemNotaFiscal', 'delete')" style="cursor: pointer;" alt="Deletar" border="0" title="Deletar" />
		</tr>
		<c:forEach var="item" items="${itensNotaFiscalForm.items}" varStatus="status">
			<tr bgcolor='${(status.index % 2) == 0 ? "#F1F1F1" : "#FFFFFF"}'>
			
				<html:hidden property="item[${status.index}].idNotaFiscal" />
				<html:hidden property="item[${status.index}].id" />
				<html:hidden property="item[${status.index}].idCliente" />
			
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].idDivisao" /><span>${item.nomeDivisao}</span>
				</td>
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].idLaboratorio" /><span>${item.nomeLaboratorio}</span>
				</td>
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].servico" /><span>${item.servico }</span>
				</td>
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].quantidade" /><span>${item.quantidade }</span>
				</td>
				<td align="right">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].valor" /><span>${item.valor}</span>
				</td>
				<td align="right">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].valorTotal" /><span>${item.valorTotal}</span>
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

<html:form action="itensNotaFiscal">
	<table width="80%">
		<tr>
			<td><input type="submit" value="Gerar Parcelas"></td>
			
			<td></td>
			<td></td>
			<td><c:out value="${requestScope.totalNotaFiscal}"></c:out></td>
			<td width="35%"></td>
			<td align="right" style="font-size: 9px">
		</tr>

	</table>
	<input type="hidden" name="operacao" value="finalizarNotaFiscal">
	<input type="hidden" name="idNotaFiscal" value="${idNotaFiscal}">
	<input type="hidden" name="idNotaFiscal" value="${idCliente}">
</html:form>
