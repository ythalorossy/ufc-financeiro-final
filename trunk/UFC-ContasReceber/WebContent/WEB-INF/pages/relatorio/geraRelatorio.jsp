<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="tituloInterno">Relatórios</span>

<fieldset>
<legend>Nota Fiscal</legend>
<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Por Número</span></td>
		<td>
		<html:form action="relatorio">
			<input type="hidden" name="operacao" value="relatorioNotaFiscalUnica">
			<select name="idNotaFiscal" style="width: 200px;">
				<c:forEach var="notaFiscal" items="${notaFiscalTO}" >
					<option value="${notaFiscal.id}">${notaFiscal.notaFiscal}</option>
				</c:forEach>
			</select>
			<input type="submit" value="Gerar">
			<html:errors prefix="image.error"/>
		</html:form>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Por Cliente</span></td>
		<td>
			<html:form action="relatorio">
				<input type="hidden" name="operacao" value="relatorioNotaFiscalAnaliticaCliente">
				<select name="idCliente" style="width: 200px;">
					<c:forEach var="clientes" items="${clientes}" >
						<option value="${clientes.id}">${clientes.nome}</option>
					</c:forEach>
				</select>
				<input type="submit" value="Gerar">
				<html:errors prefix="image.error"/>
			</html:form>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Sintético</span></td>
		<td>
			<html:form action="relatorio">
				<input type="hidden" name="operacao" value="relatorioNotaFiscalSinteticoCliente">
				<select name="idCliente" style="width: 200px;">
					<c:forEach var="clientes" items="${clientes}" >
						<option value="${clientes.id}">${clientes.nome}</option>
					</c:forEach>
				</select>
				<input type="submit" value="Gerar">
				<html:errors prefix="image.error"/>
			</html:form>
		</td>
	</tr>
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Por Unidade de Produção</span></td>
			<html:form action="relatorio">
			<td>
				<input type="hidden" name="operacao" value="relatorioLaboratorio">
				<select name="idLaboratorio" style="width: 200px;">
					<c:forEach var="laboratorio" items="${laboratorio}" >
						<option value="${laboratorio.id}">${laboratorio.nome}</option>
					</c:forEach>
				</select>
			</td>
			<td>Data Inicial <br />
				<input type="text" name="dataInicial" onkeyup="javascript:formatFieldData(this)">
			</td>
			<td>Data Final <br />
				<input type="text" name="dataFinal" onkeyup="javascript:formatFieldData(this)">
			</td>
			<td>
				<input type="submit" value="Gerar"><html:errors prefix="image.error" />
			</td>
		</html:form>
	</tr>
</table>
</fieldset>

<br/>

<fieldset>
<legend>Contas a Receber</legend>
<div >
<table width="99%" border="0" cellspacing="2" cellpadding="2">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Inadimplentes</span></td>
		<td>
		<html:form action="relatorio">
			<input type="hidden" name="operacao" value="relatorioContasReceberInadimplentes">
			Data Prevista de Pagamento<br/>
			<input type="text" name="dataPrevista" onkeyup="javascript:formatFieldData(this)">
			<input type="submit" value="Gerar">
			<html:errors property="dataContaInvalida" prefix="image.error"/>
		</html:form>
		</td>
	</tr>
</table>


<br/>
<div style="padding-left: 20px">
<fieldset>
<legend>Intervalo de Datas Analítico</legend>

<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Contas a Receber</span></td>
		<td>
			<html:form action="relatorio">
				<input type="hidden" name="operacao" value="relatorioContasReceberAnalitico">
				<table>
					<tr>
						<td>
							Data Inicial <br/>
							<input type="text" name="dataInicial" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							Data Final <br/>
							<input type="text" name="dataFinal" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							<input type="submit" value="Gerar">
							<html:errors property="dataContaAnaliticoInvalida" prefix="image.error"/>
							<html:errors property="dataInvalida" prefix="image.error"/>
						</td>
					</tr>
				</table>
			</html:form>
		</td>
	</tr>
</table>
</fieldset>

<br/>

<fieldset>
<legend>Intervalo de Datas Sintético</legend>

<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Contas a Receber</span></td>
		<td>
			<html:form action="relatorio">
				<input type="hidden" name="operacao" value="relatorioContasReceberSintetico">
				<table>
					<tr>
						<td>
							Data Inicial <br/>
							<input type="text" name="dataInicial" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							Data Final <br/>
							<input type="text" name="dataFinal" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							<input type="submit" value="Gerar">
							<html:errors property="dataContaAnaliticoInvalida" prefix="image.error"/>
							<html:errors property="dataInvalida" prefix="image.error"/>
						</td>
					</tr>
				</table>
			</html:form>
		</td>
	</tr>
</table>
</fieldset>

<br/>

<fieldset>
<legend>Cliente</legend>
<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Cliente</span></td>
		<td>
		<html:form action="relatorio">
			<select name="idCliente" style="width: 200px;">
				<c:forEach var="clientes" items="${clientes}" >
					<option value="${clientes.id}">${clientes.nome}</option>
				</c:forEach>
			</select>
			<input type="submit" value="Gerar">
			<html:errors  prefix="image.error"/>
		<input type="hidden" name="operacao" value="relatorioContasReceberClienteSintetico">
		</html:form>
		</td>
	</tr>
</table>
</fieldset>

</div>

</fieldset>
<br/>


<fieldset>
<legend>Contas a Pagar</legend>

<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="14%"><span class="labelForm">Intervalo de Datas Analítico</span></td>
		<td>
			<html:form action="relatorio">
				<input type="hidden" name="operacao" value="relatorioContasPagarAnalitico">
				<table>
					<tr>
						<td>
							Data Inicial <br/>
							<input type="text" name="dataInicial" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							Data Final <br/>
							<input type="text" name="dataFinal" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							<input type="submit" value="Gerar">
							<html:errors property="dataContaAnaliticoInvalida" prefix="image.error"/>
							<html:errors property="dataInvalida" prefix="image.error"/>
						</td>
					</tr>
				</table>
			</html:form>
		</td>
	</tr>
</table>
<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="14%"><span class="labelForm">Intervalo de Datas Sintético</span></td>
		<td>
			<html:form action="relatorio">
				<input type="hidden" name="operacao" value="relatorioContasPagarSintetico">
				<table>
					<tr>
						<td>
							Data Inicial <br/>
							<input type="text" name="dataInicial" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							Data Final <br/>
							<input type="text" name="dataFinal" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							<input type="submit" value="Gerar">
							<html:errors property="dataContaAnaliticoInvalida" prefix="image.error"/>
							<html:errors property="dataInvalida" prefix="image.error"/>
						</td>
					</tr>
				</table>
			</html:form>
		</td>
	</tr>
</table>
<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Divisão</span></td>
		<td>
		<html:form action="relatorio">
			<select name="idDivisao" style="width: 200px;">
				<c:forEach var="divisao" items="${divisao}" >
					<option value="${divisao.id}">${divisao.nome}</option>
				</c:forEach>
			</select>
			<input type="submit" value="Gerar">
			<html:errors  prefix="image.error"/>
		<input type="hidden" name="operacao" value="relatorioContasPagarDivisaoSintetico">
		</html:form>
		</td>
	</tr>
</table>
</fieldset>

<br/>

<fieldset>
<legend>Caixa</legend>

<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Intervalo de Data</span></td>
		<td>
			<html:form action="relatorio">
				<input type="hidden" name="operacao" value="relatorioCaixa">
				<table>
					<tr>
						<td>
							Data Inicial <br/>
							<input type="text" name="dataInicial" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							Data Final <br/>
							<input type="text" name="dataFinal" onkeyup="javascript:formatFieldData(this)">
						</td>
						<td>
							<input type="submit" value="Gerar">
							<html:errors property="dataInvalida" prefix="image.error"/>
						</td>
					</tr>
				</table>
			</html:form>
		</td>
	</tr>
</table>
</fieldset>

<br/>
