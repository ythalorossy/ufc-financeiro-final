<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div style="display: table-column; float:left; clear: right; border: 1px solid #c1c1c1; width: 49%">
<fieldset>
<legend>Vencimentos do dia</legend>

<table width="100%" border="0" cellspacing="1">
	<tr bgcolor="#c1c1c1">
		<th>Documento</th>
		<th>Valor</th>
	</tr>
	<c:forEach items="${vencimentosDia}" var="vencimentos">
		<tr bgcolor="#f1f1f1">
			<td>${vencimentos.numeroNotaFiscal}</td>
			<td align="right">${vencimentos.valorPrevista}</td>
		</tr>
	</c:forEach>
</table>
</fieldset>
</div>

<div style="display: table-column; float:right; clear: right; border: 1px solid #c1c1c1; width: 49%">
<fieldset>
<legend>Inadimplentes</legend>
<table width="100%"  border="0" cellspacing="1">
	<tr bgcolor="#c1c1c1">
		<th width="20%">Documento</th>
		<th width="20%" align="center">Valor</th>
		<th align="center" width="40%">Data de Pagamento</th>
		<th align="center" width="20%">Dias em Atraso</th>
	</tr>
	<c:forEach items="${inadimplentes}" var="inadimplentes">
		<tr bgcolor="#f1f1f1">
			<td>${inadimplentes.numeroNotaFiscal}</td>
			<td align="right">${inadimplentes.valorPrevista}</td>
			<td align="center">${inadimplentes.dataPrevista}</td>
			<td align="center">${inadimplentes.diasAtraso}</td>
		</tr>
	</c:forEach>
</table>
</fieldset>
</div>

