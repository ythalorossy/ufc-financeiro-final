<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<span class="tituloInterno">Entrada / Saida</span>

<html:form action="caixaEntradaSaida">

<html:hidden property="operacao" value="${requestScope.operacao}"/>
<html:hidden property="theItem.id"/>

<table width="99%" border="0" cellspacing="2" cellpadding="5">
<tr bgcolor="#f1f1f1">
	<td width="1%">&nbsp;</td>
	<td width="10%"><span class="labelForm">Operacao</span></td>
	<td>
	<input type="radio" name="theItem.operacao" value="ENTRADA" checked="checked" />Entrada
	<input type="radio" name="theItem.operacao" value="SAIDA"/>Saida<br/>
	</td>
</tr>
<tr bgcolor="#f1f1f1">
	<td width="1%">&nbsp;</td>
	<td><span class="labelForm">Data</span></td>
	<td>
	<html:text property="theItem.dataTransacao" onkeyup="javascript:formatFieldData(this)" size="10"/>
	</td>
</tr>
<tr bgcolor="#f1f1f1">
	<td width="1%">&nbsp;</td>
	<td><span class="labelForm">Descricão</span></td>
	<td>
	<html:textarea property="theItem.descricao" cols="40" rows="5"/>
	</td>
</tr>
<tr bgcolor="#f1f1f1">
	<td width="1%">&nbsp;</td>
	<td><span class="labelForm">Valor</span></td>
	<td>
	<html:text property="theItem.valor" size="10"/>
	</td>
</tr>
<tr>
	<td width="1%">&nbsp;</td>
	<td></td>
	<td>	
	<html:submit value="Adicionar no caixa"/>
	</td>
</tr>
</table>
</html:form>