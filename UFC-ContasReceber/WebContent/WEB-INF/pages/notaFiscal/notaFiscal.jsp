<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<span class="tituloInterno">Nota Fiscal</span>

<h3 style="color: red;"><html:errors property="failSave"/></h3>

<html:form action="notaFiscal">
<table width="99%" cellpadding="2" cellspacing="2">
	<tr bgcolor="#f1f1f1">
		<td> Cliente </td>
	</tr>
	<tr bgcolor="#f1f1f1" height="25">
		<td>
		<html:select property="theItem.idCliente">
			<c:forEach items="${clientes}" var="cliente">
				<option value="${cliente.id}">${cliente.nome}</option>
			</c:forEach>
		</html:select>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td>Nota Fiscal</td>
	</tr>
	<tr bgcolor="#f1f1f1" height="25">	
		<td> 
			<html:text property="theItem.notaFiscal"/>
			<html:errors property="notaFiscal"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td>N�mero do Processo</td>
	</tr>
	<tr bgcolor="#f1f1f1">	
		<td>
			<html:text property="theItem.numeroProcesso"/>
			<html:errors property="numeroProcesso"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td>N�mero do Contrato</td>
	</tr>
	<tr bgcolor="#f1f1f1">	
		<td>
			<html:text property="theItem.numeroContrato"/>
			<html:errors property="numeroContrato"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td>Data de Saida</td>
	</tr>
	<tr bgcolor="#f1f1f1">	
		<td>
			<html:text property="theItem.dataSaida" onkeyup="javascript:formatFieldData(this)"/>
			<html:errors property="dataInvalida"/><html:errors property="dataVazio"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td>Tipo nota Fiscal</td>
	<tr/>
	<tr bgcolor="#f1f1f1">	
		<td>
			<input type="radio" name="theItem.tipoNota" value="50" checked="checked">Contabilizada
			<input type="radio" name="theItem.tipoNota" value="51">N�o Contabilizada
		</td>
	</tr>
	<html:hidden property="theItem.id"/>
	<tr>
		<td colspan="2">
			<html:image src="./imagens/add.png" style="cursor: pointer; border: 1px solid #ffffff" alt="Criar Notas Fiscal" border="0" title="Criar Notas Fiscal" />
		</td>
	</tr>
</table>



<html:hidden property="theItem.id"/>
<html:hidden property="theItem.status"/>
<html:hidden property="theItem.valorNota"/>
<input type="hidden" name="operacao" value="${operacao }"/>

</html:form>