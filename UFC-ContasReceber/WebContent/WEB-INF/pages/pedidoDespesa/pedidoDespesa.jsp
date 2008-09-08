<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<span class="tituloInterno">Pedido de Despesa</span>

<h3 style="color: red;"><html:errors property="failSave"/></h3>

<html:form action="pedidoDespesa">

	<input type="hidden" name="operacao" value="${operacao }"/>
	<html:hidden property="theItem.id"/>

<table width="99%" cellpadding="2" cellspacing="2">
	<tr bgcolor="#f1f1f1">
		<td width="1%">&nbsp;</td>
		<td width="15%"> <span class="labelForm">Divisão</span> </td>
		<td>
		<html:select property="theItem.idDivisao" 
		onchange="javascript:makeRequest('/ajax','idDivisao',this.options[this.selectedIndex].value, 'laboratorio','laboratorios')">
			<option value=""></option>
			<c:forEach items="${listAllDivisao}" var="divisao">
				<html:option value="${divisao.id}">${divisao.nome}</html:option>
			</c:forEach>
		</html:select>
		<html:errors property="divisaoVazio"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td width="1%">&nbsp;</td>
		<td width="15%"> <span class="labelForm">Laboratório</span> </td>
		<td>
			<html:select styleId="laboratorios" property="theItem.idLaboratorio">
				<html:option value=""></html:option>
			</html:select>
			<html:errors property="laboratorioVazio"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td width="1%">&nbsp;</td>
		<td><span class="labelForm">Número do Pedido</span></td>
		<td> 
			<html:text property="theItem.numeroPD"/>
			<html:errors property="numeroPD"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td width="1%">&nbsp;</td>
		<td><span class="labelForm">Projeto</span></td>
		<td> 
			<html:radio property="theItem.projeto" value="Sim"/>Sim
			<html:radio property="theItem.projeto" value="Não"/>Não
			<html:errors property="numeroPD"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td width="1%">&nbsp;</td>
		<td><span class="labelForm">Tipo de Serviço</span></td>
		<td>
			<html:radio property="theItem.tipoServico" value="CONSIGNACAO">Consignação</html:radio>
			<html:radio property="theItem.tipoServico" value="MATERIAL">Material</html:radio>
			<html:radio property="theItem.tipoServico" value="SERVICO">Serviço</html:radio>
			<html:radio property="theItem.tipoServico" value="DIARIA">Diária</html:radio>
			<html:radio property="theItem.tipoServico" value="OBRA">Obra</html:radio>
			<html:errors property="tipoServico"/>
		</td>
	</tr>
	
	<tr bgcolor="#f1f1f1">
		<td width="1%">&nbsp;</td>
		<td><span class="labelForm">Data</span></td>
		<td>
			<html:text property="theItem.dataPD" onkeyup="javascript:formatFieldData(this)"/>
			<html:errors property="dataInvalida"/><html:errors property="dataVazio"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td width="1%">&nbsp;</td>
		<td><span class="labelForm">Justificativa</span></td>
		<td>
		<html:textarea property="theItem.justificativa" cols="40" rows="5"/>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1">
		<td width="1%">&nbsp;</td>
		<td><span class="labelForm">Tem Anexos?</span></td>
		<td> 
			<html:radio property="theItem.anexo" value="Sim"/>Sim
			<html:radio property="theItem.anexo" value="Não"/>Não
			<html:errors property="numeroPD"/>
		</td>
	</tr>
	<tr>
		<td width="1%">&nbsp;</td>
		<td>&nbsp;</td>
		<td>
			<html:submit>Adicionar Itens</html:submit>
		</td>
	</tr>
</table>

</html:form>