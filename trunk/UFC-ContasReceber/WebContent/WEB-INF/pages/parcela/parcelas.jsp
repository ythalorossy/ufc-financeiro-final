<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<span class="tituloInterno">Parcelas</span>

<span style="color:#FF0000;"><html:errors property="valorNaoUtilizado"/></span>
<span style="color:#FF0000;"><html:errors property="numeroContrato"/></span>

<table width="100%" border="0">
<tr>
<td>
	<fieldset>
	<legend>Dados da Nota Fiscal</legend>
		<table width="99%" border="0" bordercolor="#FFFFFF" cellpadding="0" cellspacing="2">
			<tr>
				<td>Nota Fiscal</td>
				<td>${notaFiscal}</td>
			</tr>
			<tr>
				<td>Número do Processo</td>
				<td>${numeroProcesso}</td>
			</tr>
			<tr>
				<td>Número do Contrato</td>
				<td>${numeroContrato}</td>
			</tr>
			<tr>
				<td>Cliente</td>
				<td>${cliente}</td>
			</tr>
			<tr>
				<td>Data de Saída</td>
				<td>${dataSaida} &nbsp;&nbsp;&nbsp;&nbsp; <html:errors property="dataMenor"/></td>
			</tr>
		</table>
	</fieldset>

</td>
<td valign="top">
	<fieldset>
		<legend>Dados do Valor</legend>
		<table width="99%" border="0">
			<tr>
				<td>Valor Total da Nota Fiscal</td>
				<td>${valorTotal}</td>
			</tr>
			<tr>
				<td>Valor Utilizado no Parcelamento</td>
				<td>${valorUtilizado}</td>
				
			</tr>
			<tr>
				<td>Saldo para Parcelamento</td>
				<td>${valorRestante} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:errors property="valorMaior"/></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</fieldset>
</td>
</tr>
</table>

<hr/>

<fieldset>
	<legend>Parcelas Automática</legend>
	<span style="color:#ff0000">
	<html:errors property="quantidadeInvalido"/><html:errors property="quantidadeVazio"/><html:errors property="quantidadeNulo"/>
	<html:errors property="dataInvalido"/>
	<html:errors property="dataVazio"/>
	<html:errors property="valorInvalido"/>
	<html:errors property="valorVazio"/>
	<html:form action="/parcela">
	</span>	
		<table width="99%">
			<tr>
				<td width="14%">Quantidade</td>
				<td width="10%" align="left"><input type="text" name="quantidade" size="10"></td>
				<td width="5%">&nbsp;</td>
				<td width="14%">Data Inicial</td>
				<td width="10%"><input type="text" name="dataPagamento" onkeyup="javascript:formatFieldData(this)"></td>
				<td>
				<html:image src="./imagens/add.png" style="cursor: pointer;" alt="Gerar Parcelas" border="0" title="Gerar Parcelas" />
				</td>
			</tr>
		</table>
		<input type="hidden" name="idNotaFiscal" value="${idNotaFiscal}">
		<input type="hidden" name="valorRestante" value="${valorRestante}">
		<input type="hidden" name="idCliente" value="${idCliente}">
		<input type="hidden" name="operacao" value="saveAutomatico">
	</html:form>
	
	</fieldset>
	
	<fieldset>
	<legend>Parcelas Manual</legend>
	
	<html:form action="/parcela">
		<table width="99%">
			<tr>
				<td width="5%">Data:</td>
				<td width="20%"><html:text property="theItem.dataPagamento" onkeyup="javascript:formatFieldData(this)"/></td>
				<td width="5%">Valor</td>
				<td width="5%"><html:text property="theItem.valor"/></td>					
				<td>
				<html:image src="./imagens/add.png" style="cursor: pointer;" alt="Gerar Parcelas" border="0" title="Gerar Parcelas" />
				</td>
			</tr>
		</table>
		<html:hidden property="theItem.idNotaFiscal" value="${idNotaFiscal}"></html:hidden>
		<html:hidden property="theItem.idCliente" value="${idCliente}"></html:hidden>
		<input type="hidden" name="operacao" value="saveManual">
	</html:form>
	
</fieldset>

<hr/>

<fieldset>
<legend>Parcelas</legend>

<html:form action="/parcela" styleId="formListarParcelas">
<table bgcolor="#ffffff" width="99%">
		<tr bgcolor="#666666">
			<th style="color: white" width="10%"><label>Nº da Parcela</label></th> 
			<th style="color: white" width="30%"><label>Vencimento</label></th>
			<th style="color: white" width="20%"><label>Valor da Parcela</label></th>
			<th width="2%">
			<img src="./imagens/delete.png" onClick="javascript:doSubmit('formListarParcelas','delete')" style="cursor: pointer;" alt="Deletar" border="0" title="Deletar" />
			</th>
		</tr>
		<c:forEach var="item" items="${parcelaForm.items}" varStatus="status">
			<tr bgcolor='${(status.index % 2) == 0 ? "#F1F1F1" : "#FFFFFF"}'>
			
				<html:hidden property="item[${status.index}].idNotaFiscal" />
				<html:hidden property="item[${status.index}].id" />
				<html:hidden property="item[${status.index}].idCliente" />
				<html:hidden property="item[${status.index}].status" />
			
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].numeroParcela" /><span>${item.numeroParcela}</span>
				</td>
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].dataPagamento" /><span>${item.dataPagamento}</span>
				</td>
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].valor" /><span>${item.valor}</span>
				</td>
				<td align="center">
					<input type="checkbox" id="item[${status.index}].checked"
					name="item[${status.index}].checked" ${item.checked ? "checked='checked' " : ""} />
				</td>
			</tr>
		</c:forEach>
	</table>
	<input type="hidden" name="operacao" value="" />
</html:form>
</fieldset>
<html:form action="/parcela">
<table>
	<tr>
		<td colspan="4"><html:submit>Finalizar Nota Fiscal</html:submit></td>
	</tr>
</table>
	<input type="hidden" name="idNotaFiscal" value = "${idNotaFiscal}"/>
	<input type="hidden" name="operacao" value="finalizarNota" />
</html:form>

