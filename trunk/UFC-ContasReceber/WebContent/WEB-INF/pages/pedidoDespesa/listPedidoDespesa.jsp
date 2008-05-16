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
<span class="tituloInterno">Listagem de Pedidos de Despesa</span>

<fieldset>
	<legend>
			Pesquisar Pedidos de Despesas por intervalo de datas
	</legend>
	<html:form action="pedidoDespesa">
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


<html:form action="pedidoDespesa" styleId="formListPedidoDespesa">
	<table width="100%">
		
		<tr bgcolor="#c1c1c1">
			<th>Pedido</th>
			<th>Divisão</th>
			<th>Tipo de Serviço</th>
			<th>Data</th>
			<th>Valor Total</th>
			<th>Justificativa</th>
			<th>Status</th>
			<th>Anexos</th>
			<th>Acompanhamento</th>
			<th width="2%">
			<a href="#" onclick="javascrit:doSubmit('formListPedidoDespesa', 'delete')"><html:image src="./imagens/delete.png" border="0"/></a>
			</th>
			<th>
				Aprovar 			
			</th>
		</tr>
		
		<c:forEach var="item" items="${pedidoDespesaForm.items}" varStatus="status">
		
			<tr bgcolor='${(status.index % 2) == 0 ? "#F1F1F1" : "#FFFFFF"}'>
				<html:hidden property="item[${status.index}].id" />
				<html:hidden property="item[${status.index}].idDivisao" />
				<html:hidden property="item[${status.index}].orcamento" />
				<html:hidden property="item[${status.index}].fonteRecurso" />
				
				<td align="center" width="5%">
					<a href="#"	onClick="javascript:doSubmit1('formListPedidoDespesa', 'prepareUpdate', ${item.id})">
					<html:hidden property="item[${status.index}].numeroPD" />${item.numeroPD}</a>
				</td>
				
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].nomeDivisao" />
					<span>${item.nomeDivisao}</span>
				</td>
				
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].tipoServico" />
					<span>${item.tipoServico}</span>
				</td>
				
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].dataPD" />
					<span>${item.dataPD}</span>
				</td>
				
				
				<td align="right">
				<c:if test="${item.valorCotado !='0,00'}">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].valorCotado" />
					<span>${item.valorCotado}</span>
				</c:if>
				<c:if test="${item.valorCotado =='0,00'}">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].valorPrevisto" />
					<span>${item.valorPrevisto}</span>
				</c:if>
				</td>
				
				
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].justificativa" />
					<span>${item.justificativa}</span>
				</td>
				
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].status" />
					<span>${item.status}</span>
				</td>
				
				<td align="center">
					<label for="item[${status.index}].checked"></label>
					<html:hidden property="item[${status.index}].anexo" />
					<span>${item.anexo}</span>
				</td>
				
				<td align="center"><html:link href="acompanhamentoPD.do?operacao=prepareSave&idPD=${item.id}">adicionar | ver</html:link> </td>
				
				
				<td align="center">
				<c:if test="${item.status !='Cancelado'}">
					<input type="checkbox"	id="item[${status.index}].checked" name="item[${status.index}].checked"
					${item.checked ? "checked='checked' " : ""} />
				</c:if>
				</td>
				<td align="center">
				<c:if test="${item.status =='Cotado'}">
					<a href="#"	onClick="javascript:doSubmit1('formListPedidoDespesa', 'confirmaPD', ${item.id})">
						<html:image src="./imagens/accept.png" style="cursor: pointer" alt="Aprovar" border="0" title="Aprovar PD" />
					</a>
				</c:if>
				</td>
				
			</tr>
		</c:forEach>

	</table>
	
	<html:hidden property="theItem.id" />

	<input type="hidden" name="operacao" />
</html:form>
<html:errors />
</body>
</html:html>