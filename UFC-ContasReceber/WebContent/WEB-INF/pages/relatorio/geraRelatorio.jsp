<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="tituloInterno">Relatórios</span>

<script>
	window.onload = function() {
		makeRequest('/ajax','prefix','', 'cliente', 'clientes');
	}
</script>

<fieldset>
<legend>Nota Fiscal</legend>

<table width="99%" border="0" cellspacing="2" cellpadding="5">
	
	<!-- Por Numero -->
	<html:form action="relatorio" target="_blank">
		<tr bgcolor="#f1f1f1" height="40">
			<td width="1%">&nbsp;</td>
			<td width="11%"><span class="labelForm">Por Número</span></td>
			<td>
				<input type="hidden" name="operacao" value="relatorioNotaFiscalUnica">
				<select name="idNotaFiscal" style="width: 200px;">
					<c:forEach var="notaFiscal" items="${notaFiscalTO}" >
						<option value="${notaFiscal.id}">${notaFiscal.notaFiscal}</option>
					</c:forEach>
				</select>
				<html:errors prefix="image.error"/>
			</td>
			<td width="5%"><input type="submit" value="Gerar"></td>
		</tr>
	</html:form>
	
	<!-- Por Cliente -->
	<html:form action="relatorio" target="_blank">
		<tr bgcolor="#f1f1f1" height="40">
			<td width="1%">&nbsp;</td>
			<td width="11%"><span class="labelForm">CGCPF</span></td>
			<td>
				<input type="hidden" name="operacao" value="relatorioNotaFiscalAnaliticaCliente">
				<input id="cgcpf" type="text"value="${notaFiscalForm.theItem.idCliente}"	onkeyup="javascript:makeRequest('/ajax','prefix',this.value, 'cliente','clientes')" />		
				<select id="clientes" name="idCliente" style="width: 200px;"></select>
				<html:errors prefix="image.error"/>
			</td>
			<td width="5%"><input type="submit" value="Gerar"></td>
		</tr>
	</html:form>
	
	<!-- Por Sintetico -->
	<html:form action="relatorio" target="_blank">
		<tr bgcolor="#f1f1f1" height="40">
			<td width="1%">&nbsp;</td>
			<td width="11%"><span class="labelForm">Sintético</span></td>
			<td>
				<input type="hidden" name="operacao" value="relatorioNotaFiscalSinteticoCliente">
				<select name="idCliente" style="width: 200px;">
					<c:forEach var="clientes" items="${clientes}" >
						<option value="${clientes.id}">${clientes.nome}</option>
					</c:forEach>
				</select>
				<html:errors prefix="image.error"/>
			</td>
			<td width="5%"><input type="submit" value="Gerar"></td>
		</tr>
	</html:form>

	<html:form action="relatorio" target="_blank">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">UPD</span></td>
		<td>
			<table>
				<tr>
					<td><input type="hidden" name="operacao" value="relatorioLaboratorio"></td>
					<td>
						<select name="idLaboratorio" style="width: 200px;">
							<c:forEach var="laboratorio" items="${laboratorio}" >
								<option value="${laboratorio.id}">${laboratorio.nome}</option>
							</c:forEach>
						</select>
					</td>
					<td width="3%"></td>
					<td>
						Data Inicial<br/> 
						<input type="text" name="dataInicial" onkeyup="javascript:formatFieldData(this)">
					</td>
					<td>
						Data Final<br/>
						<input type="text" name="dataFinal" onkeyup="javascript:formatFieldData(this)">
					</td>
				</tr>
			</table>
		</td>
		<td width="5%">
			<input type="submit" value="Gerar"><html:errors prefix="image.error" />			
		</td>
	</tr>
	</html:form>
	
	<html:form action="relatorio" target="_blank">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Período</span></td>
		<td>
			<input type="hidden" name="operacao" value="relatorioNotaFiscalSinteticoPeriodo">
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
							<html:errors property="dataInvalida" prefix="image.error"/>
						</td>
					</tr>
				</table>
		</td>
		<td width="5%"><input type="submit" value="Gerar"></td>
	</tr>
	</html:form>
</table>
</fieldset>

<br/>

<fieldset>
<legend>Contas a Receber</legend>
<table width="99%" border="0" cellspacing="2" cellpadding="2">
	
	<html:form action="relatorio" target="_blank">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Inadimplentes</span></td>
		<td>
			<input type="hidden" name="operacao" value="relatorioContasReceberInadimplentes">
			Data Prevista de Pagamento<br/>
			<input type="text" name="dataPrevista" onkeyup="javascript:formatFieldData(this)">
			<html:errors property="dataContaInvalida" prefix="image.error"/>
		</td>
		<td width="5%"><input type="submit" value="Gerar"></td>
	</tr>
	</html:form>
	
	<html:form action="relatorio" target="_blank">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Intervalo de Datas Analítico</span></td>
		<td>
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
						<html:errors property="dataContaAnaliticoInvalida" prefix="image.error"/>
						<html:errors property="dataInvalida" prefix="image.error"/>
					</td>
				</tr>
			</table>
		</td>
		<td width="5%"><input type="submit" value="Gerar"></td>
	</tr>
	</html:form>
	
	<html:form action="relatorio" target="_blank">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Intervalo de Datas Sintético</span></td>
		<td>
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
						<html:errors property="dataContaAnaliticoInvalida" prefix="image.error"/>
						<html:errors property="dataInvalida" prefix="image.error"/>
					</td>
				</tr>
			</table>
		</td>
		<td width="5%"><input type="submit" value="Gerar"></td>
	</tr>
	</html:form>
		
	<html:form action="relatorio" target="_blank">	
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">CGCPF</span></td>
		<td>
			<input id="cgcpf" type="text" value="${notaFiscalForm.theItem.idCliente}" onkeyup="javascript:makeRequest('/ajax','prefix',this.value, 'cliente','clientes1')" />
				
			<select id="clientes1" name="idCliente" style="width: 200px;">
				<c:forEach var="clientes" items="${clientes}" >
					<option value="${clientes.id}">${clientes.nome}</option>
				</c:forEach>
			</select>
			<input type="hidden" name="operacao" value="relatorioContasReceberClienteSintetico">
		</td>
		<td><input type="submit" value="Gerar"><html:errors  prefix="image.error"/></td>
	</tr>
	</html:form>		
</table>

</fieldset>

<br/>

<fieldset>
<legend>Contas a Pagar</legend>

<table width="99%" border="0" cellspacing="2" cellpadding="5">

	<html:form action="relatorio" target="_blank">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="14%"><span class="labelForm">Intervalo de Datas Analítico</span></td>
		<td>
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
						<html:errors property="dataContaAnaliticoInvalida" prefix="image.error"/>
						<html:errors property="dataInvalida" prefix="image.error"/>
					</td>
				</tr>
			</table>
		</td><td width="5%"><input type="submit" value="Gerar"></td>
	</tr>
	</html:form>
	
	<html:form action="relatorio" target="_blank">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="14%"><span class="labelForm">Intervalo de Datas Sintético</span></td>
		<td>
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
						<html:errors property="dataContaAnaliticoInvalida" prefix="image.error"/>
						<html:errors property="dataInvalida" prefix="image.error"/>
					</td>
				</tr>
			</table>
		</td>
		<td><input type="submit" value="Gerar"></td>
	</tr>	
	</html:form>	
	
	<html:form action="relatorio" target="_blank">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Divisão</span></td>
		<td>
			<select name="idDivisao" style="width: 200px;">
				<c:forEach var="divisao" items="${divisao}" >
					<option value="${divisao.id}">${divisao.nome}</option>
				</c:forEach>
			</select>
			<html:errors  prefix="image.error"/>
			<input type="hidden" name="operacao" value="relatorioContasPagarDivisaoSintetico">
		</td>
		<td width="5%"><input type="submit" value="Gerar"></td>
	</tr>
	</html:form>	
</table>

<br/>

<fieldset>
<legend>Caixa</legend>

<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Intervalo de Data</span></td>
		<td>
			<html:form action="relatorio" target="_blank">
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
							<html:errors property="dataInvalida" prefix="image.error"/>
						</td>
					</tr>
				</table>
			</html:form>
		</td>
		<td width="5%"><input type="submit" value="Gerar"></td>
	</tr>
</table>
</fieldset>
<fieldset>
<legend>Pedido de Despesa</legend>
<table width="99%" border="0" cellspacing="2" cellpadding="5">
	<tr bgcolor="#f1f1f1" height="40">
		<td width="1%">&nbsp;</td>
		<td width="11%"><span class="labelForm">Por Número</span></td>
		<td>
		<html:form action="relatorio" target="_blank">
			<input type="hidden" name="operacao" value="relatorioPedidoDespesaAnalitico">
			<select name="idPD" style="width: 200px;">
				<c:forEach var="pd" items="${pd}" >
					<option value="${pd.id}">${pd.numeroPD}</option>
				</c:forEach>
			</select>
			<html:errors prefix="image.error"/>
		</html:form>
		</td>
		<td width="5%"><input type="submit" value="Gerar"></td>
	</tr>
</table>
</fieldset>

<br/>
