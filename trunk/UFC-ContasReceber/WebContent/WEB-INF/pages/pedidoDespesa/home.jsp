<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<span class="tituloInterno">Pedido de Despesa</span>

<table>
<tr>
	<td></td>
</tr>
</table>

<ul>
	<li><a href="pedidoDespesa.do?operacao=prepareSave">Inserir</a></li>
	<li><a href="pedidoDespesa.do?operacao=prepareListCotacao">Inserir Cotação do Pedido de Despesa</a></li>
	<li><a href="pedidoDespesa.do?operacao=listAll">Pesquisar</a></li>
	
</ul>
<br />
<br />
<br />
<h3 style="color: red;"><html:errors /></h3>