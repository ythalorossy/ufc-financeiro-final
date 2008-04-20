<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<ul id="menuContextoGeral">
	<li><a href="caixa.do?operacao=montarCaixa">Caixa</a></li>
	<li><a href="caixaContasReceber.do?operacao=montarCaixa">Contas	Receber</a></li>
	<li><a href="caixaContasPagar.do?operacao=montarCaixa">Contas Pagar</a></li>
	<li><a href="caixaEntradaSaida.do?operacao=prepareSave">Entrada/Saida</a></li>
	<li><a href="formasPagamento.do?operacao=prepareSave">Formas de Pagamento</a></li>
	<li><a href="notaFiscal.do?operacao=home">Nota Fiscal</a></li>
	<li><a href="pedidoDespesa.do?operacao=home">Pedido de Despesa</a></li>
	<li><a href="relatorio.do?operacao=prepareRelatorio">Relatórios</a></li>
	<li><a href="logon.do?operacao=sair">Sair</a></li>
</ul>
