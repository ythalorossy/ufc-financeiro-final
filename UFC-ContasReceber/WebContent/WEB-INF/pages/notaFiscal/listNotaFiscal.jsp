<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
</head>

<body>
<span class="tituloInterno">Listagem de Notas Fiscais</span>
<fieldset>
	<legend>
			Pesquisar Notas Fiscais por intervalo de datas
	</legend>
	<html:form action="notaFiscal">
		<table>
			<tr>
				<td>
					Data Inicial
				</td>
				<td>
					Data Final
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td>
					<input type="text" name="dataInicial" onkeyup="javascript:formatFieldData(this)">
				</td>
				<td>
					<input type="text" name="dataFinal" onkeyup="javascript:formatFieldData(this)">
				</td>
				<td>
					<input type="submit" value="Pesquisar">
				</td>
			</tr>
		</table>
	<input type="hidden" name="operacao" value = "findByDay">
	</html:form>

</fieldset>


<html:form action="notaFiscal" styleId="formListNotaFiscal">
	<table width="100%">
		<tr>
			<td>Nota Fiscal</td>
			<td>Número do Processo</td>
			<td>Número do Contrato</td>
			<td>Cliente</td>
			<td>Data de Saída</td>
			<td>Valor da Nota</td>
			<td>Status da Nota Fiscal</td>
			<td align="center">
			
			</td>

		</tr>
		<c:forEach var="item" items="${notaFiscalForm.items}" varStatus="status">
			<tr bgcolor='${(status.index % 2) == 0 ? "#F1F1F1" : "#FFFFFF"}'>
				<html:hidden property="item[${status.index}].id" />

				<td align="left"><a href="#"
					onClick="javascript:doSubmit1('formListNotaFiscal', 'prepareUpdate', ${item.id})"> <html:hidden
					property="item[${status.index}].notaFiscal" />${item.notaFiscal}</a>
				</td>
				<td align="left"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].numeroProcesso" /><span>${item.numeroProcesso}</span>
				</td>
				<td align="left"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].numeroContrato" /><span>${item.numeroContrato}</span>
				</td>
				<td align="left"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].idCliente" /><span>${item.nomeCliente}</span>
				</td>
				<td align="left"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].dataSaida" /><span>${item.dataSaida}</span>
				</td>
				<td align="left"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].valorNota" /><span>${item.valorNota}</span>
				</td>
				<td align="left"><label for="item[${status.index}].checked"></label>
				<html:hidden property="item[${status.index}].status" /><span>${item.status}</span>
				</td>
				<td align="center">
				<a href="#"	onClick="javascript:doDeletePergunta('formListNotaFiscal', 'delete', ${item.id})">
					<img src="./imagens/delete.png" border="0" /></a>
				</td>

			</tr>
			<html:hidden property="item[${status.index}].tipoNota" />
		</c:forEach>

	</table>
	<html:hidden property="theItem.id" />
	<html:hidden property="theItem.cancelamento" />

	<input type="hidden" name="operacao" />
</html:form>
<html:errors />
</body>
</html:html>