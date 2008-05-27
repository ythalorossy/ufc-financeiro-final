<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="tituloInterno">Nota Fiscal - Itens</span>

<fieldset><legend>Dados da Nota Fiscal</legend>
<table width="99%">
	<tr>
		<th align="left" width="20%"><label>Nota Fiscal</label></th>
		<th align="left" width="20%"><label>Número do Processo</label></th>
		<th align="left" width="20%"><label>Número do Contrato</label></th>
	</tr>
	<tr>
		<td>${requestScope.nf}</td>
		<td>${requestScope.numeroProcesso}</td>
		<td>${requestScope.numeroContrato}</td>
	</tr>
	<tr>
		<th align="left" width="30%"><label>Cliente</label></th>
		<th align="left" width=""><label>Desconto</label></th>
		<th align="left" width="20%"><label>Data da Saida</label></th>
	</tr>
	<tr>
		<td>${requestScope.cliente}</td>
		<td><c:if test="${requestScope.desconto != 0}">
			${requestScope.desconto}%
		</c:if></td>
		<td>${requestScope.data}</td>
	</tr>
</table>
</fieldset>

<fieldset><legend>Adicionar Item</legend> <html:form
	action="/itensNotaFiscal">
	<html:hidden property="theItem.id" />
	<html:hidden property="theItem.idCliente" value="${idCliente}" />

	<input type="hidden" name="operacao" value="${requestScope.operacao}" />
	<table border=0 width="98%">
		<tr bgcolor="#f1f1f1">
			<td width="30%">Divisao</td>
			<td width="30%" colspan="2">UPD <html:errors property="servico" /></td>
		</tr>
		<tr bgcolor="#f1f1f1">
			<td>
			<html:select property="theItem.idDivisao"
				onchange="javascript:makeRequest('/ajax','idDivisao',this.options[this.selectedIndex].value, 'laboratorio','laboratorios')">
				<html:option value=""></html:option>
				<c:forEach var="divisao" items="${divisao}">
					<html:option value="${divisao.id}">${divisao.nome}</html:option>
				</c:forEach>
			</html:select>
			</td>
			<td bgcolor="#f1f1f1" colspan="2">
			<html:select styleId="laboratorios" property="theItem.idLaboratorio">
				<html:option value=""></html:option>
			</html:select></td>
		</tr>
		<tr bgcolor="#f1f1f1">
			<td>Serviço <html:errors property="servico" /></td>
			<td>Quantidade <html:errors property="quantidade" /></td>
			<td>Valor <html:errors property="valorVazio" /></td>
		</tr>
		<tr bgcolor="#f1f1f1">
			<td><html:text property="theItem.servico" value="" size="35" />
			</td>
			<td><html:text property="theItem.quantidade" value="" size="10" />
			</td>

			<td><html:text property="theItem.valor" value="" size="10" /> <html:image
				src="./imagens/add.png" style="cursor: pointer;"
				alt="Adicionar Item" border="0" title="Adicionar Item" /></td>
		</tr>
	</table>
	<html:hidden property="theItem.idNotaFiscal"
		value="${requestScope.idNotaFiscal}" />
	<input type="hidden" name="operacao" value="${operacao}" />

</html:form></fieldset>

<fieldset><legend>Itens</legend> <html:form
	action="/itensNotaFiscal" styleId="formInsertItemNotaFiscal">
	<div>
	<table width="99%">
		<tr bgcolor="#c1c1c1">
			<th width="15%">Divisão</th>
			<th width="15%">Laboratório</th>
			<th width="38%">Serviço</th>
			<th width="10%">Quantidade</th>
			<th width="10%">Valor Unitário</th>
			<th width="10%">Valor Total</th>
			<th width="2%" valign="bottom"><a href="#"
				onclick="doDelete('formInsertItemNotaFiscal', 'delete')"><img
				src="./imagens/delete.png" border="0" style="vertical-align: middle"></a>
		</tr>
		<c:forEach var="item" items="${itensNotaFiscalForm.items}"
			varStatus="status">
			<tr bgcolor='${(status.index % 2) == 0 ? "#F1F1F1" : "#FFFFFF"}'>

				<html:hidden property="item[${status.index}].idNotaFiscal" />
				<html:hidden property="item[${status.index}].id" />
				<html:hidden property="item[${status.index}].idCliente" />

				<td align="center"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].idDivisao" /><span>${item.nomeDivisao}</span>
				</td>
				<td align="center"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].idLaboratorio" /><span>${item.nomeLaboratorio}</span>
				</td>
				<td align="center"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].servico" /><span>${item.servico
				}</span></td>
				<td align="center"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].quantidade" /><span>${item.quantidade
				}</span></td>
				<td align="right"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].valor" /><span>${item.valor}</span>
				</td>
				<td align="right"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].valorTotal" /><span>${item.valorTotal}</span>
				</td>
				<td align="center"><input type="checkbox"
					id="item[${status.index}].checked"
					name="item[${status.index}].checked"
					${item.checked ? "checked='checked' " : ""} /></td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<input type="hidden" name="operacao" value="" />
</html:form></fieldset>

<html:form action="itensNotaFiscal">
	<table width="80%">
		<tr>
			<td><input type="submit"
				value="${tipoNota eq 'Não Contabilizada' ? 'Finalizar' : 'Gerar Parcelas'}"></td>
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
