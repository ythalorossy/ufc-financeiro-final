<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<span class="tituloInterno">Contas - A Pagar</span>

<table width="99%" border="0">
	<tr>
		<td align="right">
			<html:form action="/caixaContasPagar">
				<html:hidden property="operacao" value="montarCaixa" />
				Data: <input type="text" name="dataToList" onkeyup="javascript:formatFieldData(this)" size="10" />
				<html:image src="./imagens/control_play_blue.png" style="cursor: pointer" alt="pesquisar" border="0" title="Pesquisar" />
			</html:form>
		</td>
	</tr>
</table>
<span style="color: red;"><html:errors property="caixaBatido"/><html:errors property="dataBaixaMaior"/></span>
<table width="99%" border="0">
	<tr bgcolor="#c1c1c1">
		<th>Documento</th>
		<th>Divisão</th>
		<th>Data Prevista</th>
		<th>Valor Previsto</th>
		<th></th>
	</tr>

	<c:forEach items="${listAllContasPagar}" var="contasPagar" varStatus="status">
		<tr>
			<td align="center">${contasPagar.observacao}</td>
			<td>${contasPagar.nomeDivisao}</td>
			<td align="center">${contasPagar.dataPrevista}</td>
			<td width="10%" align="right">${contasPagar.valorPrevista}</td>
			<td width="2%" align="center">
			<html:image src="./imagens/accept.png" onclick="javascript:showHide('div-${status.index}')" style="cursor: pointer" alt="Efetuar Baixa" border="0" title="Efetuar Baixa" />
			</td>
		</tr>
		<tr>
			<td colspan="5">
			<div id="div-${status.index}" class="hide">
			<div style="border: 2px solid #000000; padding: 4px">
			<html:form
				action="/caixaContasPagar">
				<html:hidden property="operacao" value="baixar" />
				<html:hidden property="theItem.id" value="${contasPagar.id}" />
				<html:hidden property="theItem.idDivisao" value="${contasPagar.idDivisao}" />
				<html:hidden property="theItem.idNotaFiscal" value="${contasPagar.idNotaFiscal}" />
				<html:hidden property="theItem.idParcela" value="${contasPagar.idParcela}" />
				<html:hidden property="theItem.valorPrevista" value="${contasPagar.valorPrevista}" />
				<html:hidden property="theItem.dataPrevista" value="${contasPagar.dataPrevista}" />
				<html:hidden property="theItem.valorEfetivo" value="${contasPagar.valorEfetivo}" />
				<html:hidden property="theItem.dataEfetiva" value="${contasPagar.dataEfetiva}" />
				<html:hidden property="theItem.observacao" value="${contasPagar.observacao}" />
				<html:hidden property="theItem.status" value="${contasPagar.status}" />
				<html:hidden property="theItem.idPedidoDespesa" value="${contasPagar.idPedidoDespesa}" />
				<html:hidden property="theItem.observacao" value="${contasPagar.observacao}" />

				<table width="98%" bgcolor="#c1c1c1">
					<tr>
						<td>Data Pagamento: <input type="text" name="dataPagamento" value="${contasPagar.dataPrevista}" onkeyup="javascript:formatFieldData(this)"></td>
						<td>
							<input type="radio" name="jurosDesconto" value="0" checked="checked">Nenhum
							<input type="radio" name="jurosDesconto" value="150">Juros 
							<input type="radio" name="jurosDesconto" value="151">Desconto
						</td>
						<td>Valor: <input type="text" name="theItem.valorMonetario" value="0"></td>
						<td>
							<html:select property="theItem.idFormasPagamento">
								<c:forEach items="${listAllFormasPagamentoTO}" var="formasPagamento" varStatus="status">
									<html:option value="${formasPagamento.id}">${formasPagamento.formaPagamento}</html:option>
								</c:forEach>
							</html:select>
						</td>
						<td>
						
						<html:image src="./imagens/tick.png" style="cursor: pointer; border: 1px solid #ffffff" alt="Baixar" border="0" title="Baixar" />
						
						</td>
					</tr>
				</table>
			</html:form>
			</div>
			</div>
			</td>
		</tr>
	</c:forEach>

</table>

<h2><span style="font-weight: bold; color: red;"><html:errors property="caixaBatido"/></span></h2>
