<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="tituloInterno">Contas - A Receber</span>

<table width="99%" border="0">
	<tr>
		<td align="right">
			<html:form action="/caixaContasReceber">
				<html:hidden property="operacao" value="montarCaixa" />
				Data: <input type="text" name="dataToList" onkeyup="javascript:formatFieldData(this)" size="10" />
				<html:image src="./imagens/control_play_blue.png" style="cursor: pointer" alt="pesquisar" border="0" title="Pesquisar" />
			</html:form>
		</td>
	</tr>
</table>
<span style="color: red;">
			<html:errors property="caixaBatido"/>
			<html:errors property="dataBaixaMaior"/>
			<html:errors property="erroSalvar"/>
</span>
<table width="99%" border="0">
	<tr bgcolor="#c1c1c1">
		<th>Nota Fiscal</th>
		<th>Cliente</th>
		<th>Parcela</th>
		<th>Vencimento</th>
		<th>Valor</th>
		<th></th>
	</tr>

	<c:forEach items="${caixaContasReceberForm.items}" var="contasReceber" varStatus="status">
		
		<c:set var="index" value="${status.index}"/>
	
		<tr>
			<td width="10%" align="center" bgcolor="#f1f1f1">${contasReceber.numeroNotaFiscal}</td>
			<td bgcolor="#f1f1f1">&nbsp;${contasReceber.nomeCliente}</td>
			<td width="12%" bgcolor="#f1f1f1" align="center">${contasReceber.numeroParcela}</td>
			<td width="12%" bgcolor="#f1f1f1" align="center">${contasReceber.dataPrevista}</td>
			<td width="12%" bgcolor="#f1f1f1" align="right">${contasReceber.valorPrevista}</td>
			<td align="center" width="5%">
				<img src="./imagens/page_edit.png" style="cursor: pointer" alt="Observação" border="0" onclick="javascript:showHide('div-obs-${status.index}')" title="Observacoes" />
				<img src="./imagens/accept.png" style="cursor: pointer" alt="Efetuar Baixa" border="0" onclick="javascript:showHide('div-${status.index}')" />
			</td>
		</tr>
		<tr>
			<td colspan="6">
			
			<!-- Efetuar Baixa -->
			<div id="div-${index}" class="hide">
			<div style="border: 2px solid #000000; padding: 4px">
			<html:form action="/caixaContasReceber">
				<html:hidden property="operacao" value="baixar" />
				<html:hidden property="theItem.id" value="${contasReceber.id}" />
				<html:hidden property="theItem.idCliente" value="${contasReceber.idCliente}" />
				<html:hidden property="theItem.idNotaFiscal" value="${contasReceber.idNotaFiscal}" />
				<html:hidden property="theItem.idParcela" value="${contasReceber.idParcela}" />
				<html:hidden property="theItem.valorPrevista" value="${contasReceber.valorPrevista}" />
				<html:hidden property="theItem.dataPrevista" value="${contasReceber.dataPrevista}" />
				<html:hidden property="theItem.valorEfetivo" value="${contasReceber.valorEfetivo}" />
				<html:hidden property="theItem.dataEfetiva" value="${contasReceber.dataEfetiva}" />
				<html:hidden property="theItem.status" value="${contasReceber.status}" />

				<table width="98%" bgcolor="#c1c1c1" border="0">
					<tr>
						<td>Data Pagamento: <br/> 
						<input type="text" name="dataPagamento" value="${contasReceber.dataPrevista}" onkeyup="javascript:formatFieldData(this)" size="10"></td>
						<td>
							<input type="radio" name="jurosDesconto" value="0" checked="checked">Nenhum
							<input type="radio" name="jurosDesconto" value="150">Juros 
							<input type="radio" name="jurosDesconto" value="151">Desconto
						</td>
						<td>Valor: <br/> 
						<input type="text" name="theItem.valorMonetario" value="0" size="10"></td>
						<td>
						Formas Pagamento: <br/>
							<html:select property="theItem.idFormasPagamento">
								<c:forEach items="${listAllFormasPagamentoTO}" var="formasPagamento" varStatus="status">
									<html:option value="${formasPagamento.id}">${formasPagamento.formaPagamento}</html:option>
								</c:forEach>
							</html:select>
						</td>
						<td valign="bottom">
						<html:image src="./imagens/tick.png" style="cursor: pointer; border:1px solid #ffffff;" alt="Baixar" title="Baixar" />
						</td>
					</tr>
				</table>
				
			</html:form>
			
			</div>
			</div>
			
			
			<div id="div-obs-${index}" class="hide">
			<div style="border: 2px solid #000000; padding: 4px">
			
			<!-- Parte referente a Observação -->	
			<html:form action="/caixaContasReceber" styleId="formObservacao">
				<input type="hidden" name="theItem.id" value="${contasReceber.id}">
				<input type="hidden" name="operacao" value="updateObservacao">
				
				<table width="98%" border=0 bgcolor="#f1f1f1" cellpadding="0" cellspacing="0">
					<tr>
						<td>Nova Observação</td>
						<td></td>
					</tr>
					<tr>
						<td width="10%">
							<textarea name="observacao" cols="80" rows="4"></textarea>
						</td>
						<td width="90%" align="left" valign="baseline">
						<html:image src="./imagens/add.png" style="cursor: pointer" alt="Adicionar" border="0" title="Adicionar" />
						</td>
					</tr>
				</table>
				
				<br/>
				
				<!-- Listagem de observações -->
				<table width="98%" border="0">
					<tr bgcolor="#c1c1c1">
						<th width="88%">Lista de Observações</th>
						<th width="10%">Data</th>
						<th width="2%"></th>
					</tr>
					<c:forEach var="observacao" items="${contasReceber.observacao}" varStatus="s">
					
						<tr bgcolor="#f1f1f1">
							<td>${observacao.observacao}</td>
							<td>${observacao.data}</td>
							<td align="center">
								<a href="#" onclick="javascript:doDeleteObservacao('formObservacao', 'deleteObservacao',${observacao.id}, ${contasReceber.id})">		
								<img src="./imagens/delete.png" border="0">
								</a>
							</td>	
						</tr>
						
						</c:forEach>
				</table>
				<input type="hidden" name="observacaoId" value="">	
			</html:form>
			</div>
			</div>
			</td>
		</tr>
	</c:forEach>
	 
</table>

