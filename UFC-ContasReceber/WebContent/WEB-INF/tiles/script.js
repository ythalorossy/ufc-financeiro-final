<script type="text/javascript">

	/*
	* FORMATO DE DATA
	* Usado em campos que precisam ser modelados no formato de data
	* Modo de Usar: onkeyup='javascript:formatFieldData(this)'
	* Onde: this � o proprio campo
	*
	*/
	function formatFieldData(field) {
		field.maxLength = 10;
		
		var valorAtual = field.value;
		
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
	
	/*
	* DOSUBMIT()
	* Questiona o usuario por um motivo de cancelamento, deixando em branco nada acontece.
	*/
	function doDeletePergunta(idForm, operation, id) {
		
		var form = document.getElementById(idForm);
		var motivoCancelamento = prompt("Digite o motivo do Cancelamento","");
		
		if (motivoCancelamento) {
			form['theItem.cancelamento'].value = motivoCancelamento;
			form['theItem.id'].value = id;
			form.operacao.value = operation;
			form.submit();
		}
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
	

	/*
	* Inicia com false a variavel que receber�
	* o valor do XMLHttpRequest nas solicita�oes AJAX
	*/
    var http_request = false;
	
	/*
	* MAKEREQUEST()
	* Responsavel por requisi�oes AJAX
	* Observa��o: A p�gina WEB-INF/tiles/template.jsp cont�m um script
	* que mantem a variavel urlSistema atualizada com a URL Absoluta do Sistema,
	* esta urlSistema ser� usada para as requisi�oes AJAX
	*
	* @param url Url da requisi�ao
	* @param paramentro Nome do parametro que ser� usado para efetuar a busca
	* @param valor Valor que ser� setado na variavel parametro
	* @param tipoRetorno Controle da fun��o que tratar� o retorno
	* @param alvo Elemento que receber� o resultado da requisi�ao
	*/    
    function makeRequest(url, parametro, valor, tipoRetorno, alvo) {
    
    	/*
    		Delimitador de quantidade de caracteres aceitavel para efetuar um consulta
    		iguais a Cliente
    	*/
    	var qtdeMaxCaracterForSearch = 8;
    	if (parametro == "prefix") {
    		if (qtdeMaxCaracterForSearch > valor.length) {
    			return false;
    		}
    	}
    
        http_request = false;
		
		/*
			Como o Ajax trabalha por requisic�es locais foi
			necess�rio criar um variavel global que guarda a URL(IP/DNS)
			padrao do sistema. Abaixo a urlPadrao � concatenada com a url
			passada na requisi�ao, criando uma url absoluta.
		*/
		url = urlSistema + url;
		
		// Adiciona valor do indice selecionado
        url+="?"+parametro+"="+valor
        
        url+="&tipoRetorno="+tipoRetorno;
        
        // Numero Randomico para quebrar o cache do navegador
        var meuRandom=parseInt(Math.random()*99999999);
        url+="&r="+meuRandom;
        
		//alert(url);
		
        if (window.XMLHttpRequest) { // Mozilla, Safari,...
            http_request = new XMLHttpRequest();
            if (http_request.overrideMimeType) {
                http_request.overrideMimeType('text/xml');
                
            }
        } else if (window.ActiveXObject) { // IE
            try {
                http_request = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    http_request = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {
                	try {
                	http_request = new ActiveXObject("MSXML2.XMLHTTP.3.0")
                	} catch(e) {}
                }
            }
        } 
        
        if (!http_request) {
            alert('Error :( N�o foi poss�vel criar uma instancia de XMLHTTP');
            return false;
        }
        
        /**
        	Func�o responsavel por tratar o retorno das requisi�oes
        */
        http_request.onreadystatechange = function () {
        
			if (http_request.readyState == 4) {
	           	if (http_request.status == 200) {
	           		
	           		/*
	           		* Remove todos os elementos filho do elemento Alvo
	           		*/
					var al = document.getElementById(alvo);
					var qtde = al.childNodes.length;
					for (i=0; i <= qtde; i++) {
						al.remove(i);
						al.remove(i);					
					}
	           		/* 
	           		* O Resultado da requisi�ao � uma String que cont�m
	           		* todos elementos encontrados concatenados por uma separador(---).
	           		* A fun��o split usa o separador(---) quebrar a String em um array,
	           		* a cada (---) um indice � criado dentro do array
	           		*/
	           		var arrayAuxiliar = http_request.responseText.split("---");
					
					/*
					* Navega dentro do array resultado do split e cria uma elemento
					* <option> para cara indice. O elemento option � inserido dentro
					* do alvo definido quando a fun�ao makeRequest � disparada.
					*/				
					for (i=0; i < arrayAuxiliar.length-1; i++) {
						var arrayAuxilarInterno = arrayAuxiliar[i].split(":");
						
						/*
						* Cria elemento <option>
						*/
						var option = document.createElement('option');
						
						/*
						* Seta valor para o <option>
						*/
						option.value = arrayAuxilarInterno[0];
						
						/*
						* Seta texto para o <option>
						*/
						option.text = arrayAuxilarInterno[1];
						
						/*
						* Seleciona o elemento alvo ao qual o <option> ser� adicionado
						*/
						var x = document.getElementById(alvo);
						
						/*
						* Adiciona elemento <option> ao alvo(x), trantando
						* os casos IE
						*/
						try {
							x.add(option,null); // compila��o padr�o
						} catch(ex){
							x.add(option); // apenas para IE ��
						}
						
						//alert("Valor:" + option.value + " - Texto: " + option.text)
						
					}	
						           		
	           	} else {
	               	alert('Problemas com a requisi��o.');
	           	}
	        }
	};
         
        http_request.open('GET', url, false);
        http_request.send(null);
    }
    

</script>