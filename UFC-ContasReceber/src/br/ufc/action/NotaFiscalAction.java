package br.ufc.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import br.com.ContasReceber;
import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;
import br.com.Parcela;
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.BO.BO;
import br.ufc.BO.ContasReceberBO;
import br.ufc.BO.ItensNotaFiscalBO;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.BO.ParcelaBO;
import br.ufc.DAO.ClientesDAO;
import br.ufc.TO.ItensNotaFiscalTO;
import br.ufc.TO.NotaFiscalTO;
import br.ufc.TO.ParcelaTO;
import br.ufc.assembler.ItensNotaFiscalAssembler;
import br.ufc.assembler.NotaFiscalAssembler;
import br.ufc.assembler.ParcelaAssembler;
import br.ufc.form.NotaFiscalForm;
import br.ufc.uteis.Status;

import com.Auxiliar.Clientes;
import com.converte.ConverteData;


public class NotaFiscalAction extends DispatchAction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4670183122255492630L;
	private final String LOAD_PAGE = "loadPage";
	private final String PREPARE_SAVE = "/WEB-INF/pages/notaFiscal/notaFiscal.jsp";
	private final String LIST_ALL = "/WEB-INF/pages/notaFiscal/listNotaFiscal.jsp";
	private final String REABRIR_NOTA = "/WEB-INF/pages/notaFiscal/reabrirNota.jsp";
	private final String HOME = "/WEB-INF/pages/notaFiscal/home.jsp";
	
	/**
	 * HOME
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward home(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		request.setAttribute(LOAD_PAGE, HOME);
		
		return mapping.findForward("index");
	}
	
	
	/**
	 * Preparando o formulario para a inser��o. Aqui � criado a listagem de clientes e � encaminhado para a pagina inicial
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//final ClientesDAO clientesDao = new ClientesDAO();
		
		// Recuperado todos os clientes
		//final List<Clientes> listClientes = clientesDao.findAll();
		
		//request.setAttribute("clientes", listClientes);
		request.setAttribute("operacao", "save");

		request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
		return mapping.findForward("index");
	}

	/**
	 * M�todo que salva as informa��es da nota fiscal no banco, � dado um forward para o metodo prepareSave da classe ItensNotaFiscal
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Cria��o do objeto de convers�o
		final NotaFiscalAssembler notaFiscalAssembler = new NotaFiscalAssembler();
		// Cria��o do objeto de negocio
		final NotaFiscalBO bo = new NotaFiscalBO();
		// Cria��o do Formul�rio
		final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;

		// Listagem de erros
		final ActionMessages errors = validate(notaFiscalForm, request);
		
		// Valida��o dos campos do formul�rio
		if (errors.isEmpty()){

			// objeto TO referente ao formulario
			final NotaFiscalTO notaFiscalTO = notaFiscalForm.getTheItem();
			// convers�o de objeto TO para um objeto persistente
			final NotaFiscal notaFiscal = notaFiscalAssembler.entityTO2Entity(notaFiscalTO);
			
			// tentativa de salvar o objeto
			if(bo.save(notaFiscal)){
				// Setando objeto notaFiscal no request para ser utilizado pelo metodo prepareSave do ItensNotaFiscal
				// ou para ser utilizado pelo metodo prepareSave do NotaFiscal
				request.setAttribute("notaFiscal", notaFiscal);
				return mapping.findForward("itensNotaFiscal");
			}  else{
						// caso o retorno seja falso � criado uma mensagem de erro, salvo no request e � dado um forward para a tela destino
				errors.add("failSave", new ActionMessage("SAVE.ERROR"));
			}
		}
						// caso n�o de erro no save � criado um forward para o metodo que ir� inserir os itens da nota fiscal
		saveErrors(request, errors);
		return prepareSave(mapping, form, request, response);
	}

	
	/**
	 * M�todo que prepara o formulario para atualiza��o. Feito testes de valida��o para verificar se h� alguma parcela da nota fiscal
	 * paga, se houver � lan�ado um erro. Verificado tamb�m se a nota fiscal est� com status de "cancelado", caso seja � dado um forward
	 * para o metodo "prepareReabrirNota". Este m�todo, caso seja preciso mais na frente, pode envolver alguns tipos de valida��o para
	 * garantir que tal nota possa ser reaberta, exemplos de valida��o seria a utliza��o de uma senha para permitir reabrir a nota.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		final ActionMessages errors = new ActionMessages();
						// Cria��o do objeto Clientes
		final ClientesDAO clientesDao = new ClientesDAO();
						// Cria��o do objeto de convers�o
		final NotaFiscalAssembler notaFiscalAssembler = new NotaFiscalAssembler();
						// cria��o do objeto de negocio
		final NotaFiscalBO bo = new NotaFiscalBO();
						// Cria��o do formul�rio
		final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
						// Buscando no banco pela nota fiscal referente ao ID
		final NotaFiscal nf = bo.findById(Integer.parseInt(notaFiscalForm.getTheItem().getId()));
		
						// Testando se existe algum pagamento efetuado de alguma parcela para o id da nota fiscal
						// Listagem de erros que ir� comportar tal teste caso aja algum erro
		final List<Parcela> parcelasPagas = ((ParcelaBO)ParcelaBO.getInstance()).verificaParcelaPaga(nf);
						// Caso a listagem de erros seja vazia
		if(parcelasPagas.isEmpty()){

			if (nf.getStatus()==Status.CANCELADO){
				notaFiscalForm.setTheItem(notaFiscalAssembler.entity2EntityTO(nf));
				return prepareReabrirNota(mapping, form, request, response);
			} else {
						// recuperado a lista de todos os clientes
				final List<Clientes> listClientes = clientesDao.findAll();
						// Pega o objeto persistente e converte em um objeto TO
				NotaFiscalTO nfTO =  notaFiscalAssembler.entity2EntityTO(nf);
						// Seta o objeto TO no formulario
				notaFiscalForm.setTheItem(nfTO);
				request.setAttribute("clientes", listClientes);
						// Forward para a pagina de destino
				request.setAttribute("operacao", "update");
						// Forward para a pagina de inser��o de nota fiscal levando no request o objeto NotaFiscal preenchido
						// para ser alterado
				request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
				return mapping.findForward("index");
			}
			
		}else{
			errors.add("parcelaPaga",new ActionMessage("parcelaPaga.error"));
		}
		saveErrors(request, errors);
		return listAll(mapping, form, request, response);
	}

	/**
	 * M�todo que atualiza a nota fiscal. Forward para prepareSave da classe ItensNotaFiscal
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		final NotaFiscalAssembler notaFiscalAssembler = new NotaFiscalAssembler();
		final ActionMessages errors = new ActionMessages();
		final NotaFiscalBO bo = new NotaFiscalBO();
		NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
		
						// cria��o do Objeto TO
		final NotaFiscalTO notaFiscalTO = notaFiscalForm.getTheItem();
						// cria��o do Objeto Persistente
		final NotaFiscal notaFiscal = notaFiscalAssembler.entityTO2Entity(notaFiscalTO);
						
						// inserindo o objeto TO no formulario
		notaFiscalForm.setTheItem(notaFiscalAssembler.entity2EntityTO(notaFiscal));
			
		
						// Validando os campos do formulario
		if(validate(notaFiscalForm, request).isEmpty()){
			if(bo.update(notaFiscal)){
						// Setando no request Objeto notaFiscal para ser utilizado pelo metodo prepareSave do ItensNotaFiscal ou
						// para ser utilizado pelo metodo prepareUpdate do NotaFiscal 
				request.setAttribute("notaFiscal", notaFiscal);
						// retorno do m�todo
				return mapping.findForward("itensNotaFiscal");
			} else {
						// caso o retorno seja falso � criado uma mensagem de erro, salvo no request e � dado um forward para a tela destino
				errors.add("failSave", new ActionMessage("erro.salvar"));
			}
		}
		saveErrors(request, errors);
		return prepareUpdate(mapping, form, request, response);
	}

	/**
	 * M�todo que altera o status da NotaFiscal para "cancelado", assim como seus itens e suas parcelas. Este m�todo sensibiliza
	 * a tabela "ContasReceber" buscando todas as contas de uma dada nota fiscal e excluindo-as do banco de dados.
	 * � garantido que a exclus�o seja feita por completo ou a mesma n�o ser� feita. Ou seja, a exclus�o e atualiza��o de todas as
	 * listagens (lista de itens, lista de parcelas, lista de contas a receber e o item nota fiscal) � feito dentro de uma unica
	 * transa��o.
	 * � garantido tambem que uma nota fiscal s� pode ser excluida caso n�o exista nenhuma parcela paga para tal nota fiscal
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
						// Cria��o do objeto de negocio
		final BO<NotaFiscal> bo = NotaFiscalBO.getInstance();
						// Listagem de erros
		final ActionMessages errors = new ActionMessages();
						// Recuperando o formulario
		final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
						// Cria��o da lista de dele��o
		final List<NotaFiscal> notaFiscal2Delete = new ArrayList<NotaFiscal>();
						// Cria��o do objeto transiente
		final NotaFiscalTO notaFiscalTO = notaFiscalForm.getTheItem();
		
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(Integer.parseInt(notaFiscalTO.getId()));
						// Itera��o sobre a lista de objetos
		notaFiscal.setCancelamento(notaFiscalTO.getCancelamento());
		notaFiscal2Delete.add(notaFiscal);
						// Verifica��o se a lista de objetos para dele��o est� preenchida
		if (!notaFiscal2Delete.isEmpty()){
						// Chamada do metodo delete do objeto de negocio, passando a listagem para dele��o
						// No metodo � feito uma verifica��o se existe alguma parcela paga para uma dada notaFiscal
						// Caso exista � dado retorno como "false"
			if(!bo.delete(notaFiscal2Delete)){
						// Capturado retorno para inserir na lista de erros
				errors.add("parcelaPaga",new ActionMessage("parcelaPaga.error"));
			}
		}
		saveErrors(request, errors);
		return listAll(mapping, form, request, response);
	}
	
	/**
	 * Listagem geral de notas fiscais
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
						// Recuperando o formulario
		final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
						// Cria��o da listagem de notas fiscais
		final List<NotaFiscal> notaFiscal = ((NotaFiscalBO)NotaFiscalBO.getInstance()).findNotas30();
						// Cria��o da listagem de objetos transientes
		final List<NotaFiscalTO> notaFiscalTO = new NotaFiscalAssembler().entity2EntityTO(notaFiscal);
						// Setado estes objetos TO no formulario
		notaFiscalForm.setItems(notaFiscalTO);
						// forward para a pagina de listagem
		request.setAttribute(LOAD_PAGE, LIST_ALL);
		return mapping.findForward("index");
	}
	
	/**
	 * M�todo que prepara o sistema para reabrir uma parcela. Aqui ser� listado a nota fiscal, ser� exibido todos os itens da
	 * nota fiscal e ser� exibido todas as parcelas da nota fiscal. � chamado a a��o "reabrirNota".
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareReabrirNota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
		 * M�todo precisa ser testado
		 */
		
						// Cria��o do objeto de convers�o
		final NotaFiscalAssembler notaFiscalAssembler = new NotaFiscalAssembler();
						// Recuperando o formul�rio
		final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
						// Convers�o do objeto transiente para uma NotaFiscal
		final NotaFiscal notaFiscal = notaFiscalAssembler.entityTO2Entity(notaFiscalForm.getTheItem());
						// Recuperando todos os itens da nota fiscal
		final List<ItensNotaFiscal> itensNotaFiscal = ((ItensNotaFiscalBO)ItensNotaFiscalBO.getInstance()).
														findAllItensByNF(notaFiscal.getId());
						// Recuperando todas as parcelas da nota fiscal
		final List<Parcela> parcelas = ((ParcelaBO)ParcelaBO.getInstance()).findAllByNf(notaFiscal);
		
						// Criando a listagem de objetos transientes: ItensNotaFiscalTO, ParcelaTO, NotaFiscalTO
		final List<ItensNotaFiscalTO> itensNotaFiscalTO = new ItensNotaFiscalAssembler().entity2EntityTO(itensNotaFiscal);
		final List<ParcelaTO> parcelasTO = new ParcelaAssembler().entity2EntityTO(parcelas);
		final NotaFiscalTO notaFiscalTO = notaFiscalAssembler.entity2EntityTO(notaFiscal);
		
		
						// Inserindo no request
		notaFiscalForm.setTheItem(notaFiscalTO);
		request.setAttribute("dataSaida", notaFiscalTO.getDataSaida());
		request.setAttribute("cliente", notaFiscalTO.getIdCliente());
		request.setAttribute("valorTotal", notaFiscalTO.getValorNota());
		request.setAttribute("numeroProcesso", notaFiscalTO.getNumeroProcesso());
		request.setAttribute("numeroNotaFiscal", notaFiscalTO.getNotaFiscal());
		request.setAttribute("itensNotaFiscal", itensNotaFiscalTO);
		request.setAttribute("parcelas", parcelasTO);
						// Forward para a tela de reabertura de nota fiscal
		request.setAttribute(LOAD_PAGE, REABRIR_NOTA);
		return mapping.findForward("index");
	}
	
	/**
	 * M�todo que reabri a nota Fiscal, assim como seus itens e suas parcelas. Ser� re-inserido os valores na tabela "contas a receber"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reabrirNota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final ActionMessages errors = new ActionMessages();
		final NotaFiscalAssembler notaFiscalAssembler = new NotaFiscalAssembler();
						// Recuperando o formul�rio
		final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
						// Convers�o do objeto transiente para uma NotaFiscal
		final NotaFiscal notaFiscal = notaFiscalAssembler.entityTO2Entity(notaFiscalForm.getTheItem());
						// Recuperando todos os itens da nota fiscal
		final List<ItensNotaFiscal> itensNotaFiscal = ((ItensNotaFiscalBO)ItensNotaFiscalBO.getInstance()).
														findAllItensByNF(notaFiscal.getId());
						// Recuperando todas as parcelas da nota fiscal
		final List<Parcela> parcelas = ((ParcelaBO)ParcelaBO.getInstance()).findAllByNf(notaFiscal);
		
						// Tentativa de atualiza��o dos dados
		if (((NotaFiscalBO)NotaFiscalBO.getInstance()).reabrirNota(notaFiscal, itensNotaFiscal, parcelas)){
						// Testa se a parcela � vazia
			if (!parcelas.isEmpty()){
						// Se a parcela n�o for fazia � realizado uma itera��o para a cria��o do ContasReceber
				final List<ContasReceber> contasReceberToSave = new ArrayList<ContasReceber>();
				for (int i = 0; i < parcelas.size(); i++){
						// cria��o do objeto ContasReceber para dar entrada novamente da notaFiscal no sistema
						// Este objeto utiliza o metodo retornaContasReceber especifico da classe ParcelaBO
					contasReceberToSave.add(((ParcelaBO)ParcelaBO.getInstance()).retornaContasReceber(parcelas.get(i)));
				}
						// Tentativa de inser��o no contas a receber
				if(!((ContasReceberBO)ContasReceberBO.getInstance()).save(contasReceberToSave)){
					errors.add("",null);
				}
			}
		}
		saveErrors(request, errors);
		// Caso o status da nota fiscal seja Aberto o retorno � dado para "prepareUpdate" da nota Fiscal
		if (NotaFiscalBO.getInstance().findById(notaFiscal.getId()).getStatus()== Status.ABERTO){
			// setando o novo objeto notaFiscal no form
			notaFiscal.setStatus(Status.ABERTO);
			notaFiscalForm.setTheItem(notaFiscalAssembler.entity2EntityTO(notaFiscal));
			return prepareUpdate(mapping, form, request, response);
		}
		return listAll(mapping, form, request, response);
	}
	
	

	/**
	 * Metodo de valida��o do formulario
	 * @param notaFiscalForm
	 * @param request
	 * @return ActionMessages
	 */
	private ActionMessages validate(NotaFiscalForm notaFiscalForm,
			HttpServletRequest request) {
			// Cria��o da listagem de erros
		final ActionMessages errors = new ActionMessages();
			// Recuperando a data digitada
		final String dataSaida = notaFiscalForm.getTheItem().getDataSaida();
			// Recuperando o numero da nota fiscal digitado
		final String notaFiscal = notaFiscalForm.getTheItem().getNotaFiscal();
			// Recupereando o tipo de nota (contabilizao, n�o contabilizado)
		final String tipoNota = notaFiscalForm.getTheItem().getTipoNota();
			// Recuperando o numero do processo
		final String processo = notaFiscalForm.getTheItem().getNumeroProcesso();
		
		final String desconto = notaFiscalForm.getTheItem().getDesconto();
		
		final String cliente = notaFiscalForm.getTheItem().getIdCliente();
		
		if(GenericValidator.isBlankOrNull(cliente)){
			errors.add("clienteNull", new ActionMessage("image.error"));
		}
		
			// testando se a data n�o veio em branco
		if (!GenericValidator.isBlankOrNull(dataSaida)){
				
				// Tentando converter o valor digitado em uma data valida
			try {
				ConverteData.retornaData(dataSaida);

			} catch (Exception e) {
				errors.add("dataInvalida",new ActionMessage("data.invalida.erro"));
			}
			
		} else {
			errors.add("dataVazio",new ActionMessage("image.error"));
		}
			// Verificando se a nota fiscal veio em branco
		if (GenericValidator.isBlankOrNull(notaFiscal)){
			errors.add("notaFiscal", new ActionMessage("image.error"));
		} else {
			try {
				Integer numero = Integer.parseInt(notaFiscal);
				if(numero > 1){
					numero = numero -1;
					final NotaFiscal nf = new NotaFiscalBO().findByNF(String.valueOf(numero));
					if(nf == null){
						errors.add("notaFiscal", new ActionMessage("notaFiscal.numeroSequencial.erro"));
					}
				}
				
			} catch (Exception e) {
				errors.add("notaFiscal", new ActionMessage("notaFiscal.numero.erro"));
			}
		}
			// Verificando se o tipo de nota fiscal veio em branco
		if (GenericValidator.isBlankOrNull(tipoNota)){
			errors.add("tipoNota",new ActionMessage("image.error"));
		}
		if (GenericValidator.isBlankOrNull(processo)){
			errors.add("numeroProcesso",new ActionMessage("image.error"));
		}
		
		try {
			
			if(!GenericValidator.isBlankOrNull(desconto)){
				ConverteNumero.converteNumero(desconto);
			}
		} catch (Exception e) {
			errors.add("desconto",new ActionMessage("desconto.invalido"));
		}
		
			// Salvando no a lista de erros no request
		saveErrors(request, errors);
			// Retornando a lista de erros
		return errors;
	}
	
	public ActionForward findByDay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final ActionMessages errors = new ActionMessages();
		final String dInicial = request.getParameter("dataInicial");
		final String dFinal = request.getParameter("dataFinal");
		GregorianCalendar dataInicial = null;
		GregorianCalendar dataFinal = null;
		
		if (!GenericValidator.isBlankOrNull(dInicial) && !GenericValidator.isBlankOrNull(dFinal)){
			try{
				dataInicial = ConverteData.retornaData(dInicial);
				dataFinal = ConverteData.retornaData(dFinal);
			}catch (Exception e) {
				errors.add("dataInvalida", new ActionMessage("data.invalida.erro"));
			}
		} else {
			errors.add("dataInvalida", new ActionMessage("data.vazio.erro"));
		}
		if (errors.isEmpty()){
			
			// Recuperando o formulario
			final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
			// Cria��o da listagem de notas fiscais
			final List<NotaFiscal> notaFiscal = ((NotaFiscalBO)NotaFiscalBO.getInstance()).findByDay(dataInicial, dataFinal);
			// Cria��o da listagem de objetos transientes
			final List<NotaFiscalTO> notaFiscalTO = new NotaFiscalAssembler().entity2EntityTO(notaFiscal);
			// Setado estes objetos TO no formulario
			notaFiscalForm.setItems(notaFiscalTO);
		} else {
			// Recuperando o formulario
			final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
			// Cria��o da listagem de notas fiscais
			final List<NotaFiscal> notaFiscal = ((NotaFiscalBO)NotaFiscalBO.getInstance()).findNotas30();
			// Cria��o da listagem de objetos transientes
			final List<NotaFiscalTO> notaFiscalTO = new NotaFiscalAssembler().entity2EntityTO(notaFiscal);
			// Setado estes objetos TO no formulario
			notaFiscalForm.setItems(notaFiscalTO);
		}
		
		saveErrors(request, errors);
						// forward para a pagina de listagem
		request.setAttribute(LOAD_PAGE, LIST_ALL);
		return mapping.findForward("index");
	}
	
	public ActionForward findByNF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final ActionMessages errors = new ActionMessages();
		final String nf = request.getParameter("nf");
		
		if (!GenericValidator.isBlankOrNull(nf)){
			if (errors.isEmpty()){
				
				// Recuperando o formulario
				final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
				
				final NotaFiscal notaFiscal = ((NotaFiscalBO)NotaFiscalBO.getInstance()).findByNF(nf);
				// Cria��o da listagem de notas fiscais
				final List<NotaFiscal> notaFiscalList = ((NotaFiscalBO)NotaFiscalBO.getInstance()).findByNF(notaFiscal);
				// Cria��o da listagem de objetos transientes
				final List<NotaFiscalTO> notaFiscalTO = new NotaFiscalAssembler().entity2EntityTO(notaFiscalList);
				// Setado estes objetos TO no formulario
				notaFiscalForm.setItems(notaFiscalTO);
			}
		}else {
			// Recuperando o formulario
			final NotaFiscalForm notaFiscalForm = (NotaFiscalForm) form;
			// Cria��o da listagem de notas fiscais
			final List<NotaFiscal> notaFiscal = ((NotaFiscalBO)NotaFiscalBO.getInstance()).findAll();
			// Cria��o da listagem de objetos transientes
			final List<NotaFiscalTO> notaFiscalTO = new NotaFiscalAssembler().entity2EntityTO(notaFiscal);
			// Setado estes objetos TO no formulario
			notaFiscalForm.setItems(notaFiscalTO);
		}
		
		saveErrors(request, errors);
						// forward para a pagina de listagem
		request.setAttribute(LOAD_PAGE, LIST_ALL);
		return mapping.findForward("index");
	}
}
