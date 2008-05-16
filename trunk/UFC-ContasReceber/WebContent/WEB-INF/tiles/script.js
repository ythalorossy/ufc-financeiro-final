<script type="text/javascript">

	/*
	* FORMATO DE DATA
	* Usado em campos que precisam ser modelados no formato de data
	* Modo de Usar: onkeyup='javascript:formatFieldData(this)'
	* Onde: this é o proprio campo
	*
	*/
	function formatFieldData(field) {
		field.maxLength = 10;
		
		var valorAtual = field.value
		
		
		if (valorAtual.length == 2) {
			valorAtual += "/";
		} 
		if (valorAtual.length == 5) {
			valorAtual += "/"; 
		}
		
		field.value = valorAtual;
		
	}
	
	/*
	* EXTORNAR CAIXA
	*/
	
	function extornarCaixa (idCaixa) {
	
		var form = document.forms[1];
		form.operacao.value = "extornarCaixa";
		form['theItem.id'].value = idCaixa;
		form.submit();
	
	}


	/*
	* SHOW / HIDE
	* Mostra ou Oculta um determinado elemento dentro do documento
	* Modo de usar: Passe o Id de um elemento, este mesmo elemento 
	* deve estar usando uma das classe (show ou hide) de estilos CSS
	*/	
	function showHide (element) {
		
		var elem = document.getElementById(element);
		
		if (elem.className == "show") {
			elem.className = "hide";
		} else if (elem.className == "hide") {
			elem.className = "show"; 
		} else {
			elem.className = "hide";
		}
		
	}
	
	/*
	* DOSUBMIT()
	* Responsavel por receber o id de um elemento form dentro do documento atual
	* setar o valor do campo operacao e submete-lo
	*
	*/
	function doSubmit(idForm, operation){
		var form = document.getElementById(idForm);
		
		form.operacao.value = operation;
		form.submit();
	}
	
	function doDelete(idForm, operation){
	
		var form = document.getElementById(idForm);
		form.operacao.value = operation;
		form.submit();
	}
	


	/*
	* DOSUBMIT()
	* Responsavel por receber o id de um elemento form dentro do documento atual
	* setar o valor do campo operacao, o campo theItem.id e submete-lo
	*
	*/
	function doSubmit1(idForm, operation, id){
		var form = document.getElementById(idForm);
		form['theItem.id'].value = id;
		form.operacao.value = operation;
		form.submit();
	}
	
	function doDeleteObservacao(idForm, operation, id, theItem){
		var form = document.getElementById(idForm);
		form['observacaoId'].value = id;
		form['theItem.id'].value = theItem;
		form.operacao.value = operation;
		form.submit();
	}
	
	/*
	* DOSUBMITMENU()
	* Responsavel por receber o id de um elemento form dentro do documento atual
	* setar o valor do campo operacao, o campo theItem.id e submete-lo
	*
	*/
	function doSubmitMenu(operation, action){
		var form = document.forms[0];
		form.action = action;
		form.operacao.value = operation;
		form.submit();
	}
	




</script>