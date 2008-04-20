<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="tituloInterno">Formas De Pagamento</span>

<font color=red><html:errors /></font>

<table width="99%" border="0" >
	<tr>
		<td>
		<html:form action="/formasPagamento">
			<html:text property="theItem.formaPagamento" size="20" />
			<html:hidden property="operacao" value="save" />
			<html:image src="./imagens/add.png" style="cursor: pointer" alt="Adicionar" border="0" title="Adicionar" />
		</html:form></td>
	</tr>
</table>

	<html:form action="/formasPagamento" styleId="formaPagamento">
	
	<html:hidden property="theItem.id"/>
	
	<table>
	<tr bgcolor="#c1c1c1">
		<th width="98%">Forma de Pagamento</th>
		<th width="2%"><html:image src="./imagens/delete.png" border="0"/></th>
	</tr>
	
		<c:forEach items="${listAllFormasPagamentoTO}" var="formasPagamento" varStatus="status">
				<html:hidden property="item[${status.index}].id" value="${formasPagamento.id}" /> 
				<html:hidden property="item[${status.index}].formaPagamento" value="${formasPagamento.formaPagamento}" />

			<tr bgcolor="#f1f1f1">
				<td>
					<a href="javascript:doSubmit1('formaPagamento','prepareUpdate', '${formasPagamento.id}')">${formasPagamento.formaPagamento}</a>
				</td>
				<td align="center"> 
					<input type="checkbox" id="item[${status.index}].checked" name="item[${status.index}].checked" ${item.checked ? "checked='checked' " : ""} />
				</td>
			</tr>

		</c:forEach>
		<tr>
			<td colspan="2" align="right" style="padding-right: 8px;">
				<html:hidden property="operacao" value="delete" /> 
			</td>
			<td align="left">
			</td>
		</tr>
	</table>
			
	</html:form>


