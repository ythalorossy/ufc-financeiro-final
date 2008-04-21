<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<span class="tituloInterno">Pedido de Despesa</span>

<table>
<tr>
	<td></td>
</tr>
</table>

<ul id="menuContextoGeral" style="width: 20%;">
	<li><a href="pedidoDespesa.do?operacao=prepareSave">Novo</a></li>
	<li><a href="pedidoDespesa.do?operacao=prepareListCotacao">Cotar</a></li>
	<li><a href="pedidoDespesa.do?operacao=listAll">Pesquisar</a></li>
</ul>
<br />
<br />
<br />
<h3 style="color: red;"><html:errors /></h3>