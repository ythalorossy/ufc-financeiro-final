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

import br.com.ItensPedidoDespesa;
import br.com.PedidoDespesa;
import br.ufc.BO.BO;
import br.ufc.BO.ItensPedidoDespesaBO;
import br.ufc.BO.PedidoDespesaBO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.DAO.LaboratorioDAO;
import br.ufc.TO.PedidoDespesaTO;
import br.ufc.assembler.PedidoDespesaAssembler;
import br.ufc.form.PedidoDespesaForm;
import br.ufc.uteis.Status;

import com.Auxiliar.Divisao;
import com.Auxiliar.Laboratorio;
import com.converte.ConverteData;

@SuppressWarnings("serial")
public class PedidoDespesaAction extends DispatchAction implements Serializable {
	
	private final String LOAD_PAGE = "loadPage";
	private final String PREPARE_SAVE = "/WEB-INF/pages/pedidoDespesa/pedidoDespesa.jsp";
	private final String LIST_ALL = "/WEB-INF/pages/pedidoDespesa/listPedidoDespesa.jsp";
	private final String HOME = "/WEB-INF/pages/pedidoDespesa/home.jsp";
	private final String LIST_COTAR = "/WEB-INF/pages/pedidoDespesa/listCotarPedidoDespesa.jsp";
	
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
	 * Preparando o formulario para a inserção. Aqui é criado a listagem de divisoes e é encaminhado para a pagina de inserção
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepareSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		final List<Divisao> listAllDivisao = new DivisaoDAO().findAll();
		final List<Laboratorio> listAllLaboratorios = new LaboratorioDAO().findAll();
		final PedidoDespesaForm pdForm = (PedidoDespesaForm) form;
		pdForm.getTheItem().setTipoServico("MATERIAL");
		pdForm.getTheItem().setProjeto("Não");
		pdForm.getTheItem().setAnexo("Não");
		request.setAttribute("listAllDivisao", listAllDivisao);
		request.setAttribute("listAllLaboratorio", listAllLaboratorios);
		request.setAttribute("operacao", "save");
		request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
		return mapping.findForward("index");
	}

	/**
	 * Método que salva as informações da nota fiscal no banco, é dado um forward para o metodo prepareSave da classe ItensNotaFiscal
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		final PedidoDespesaForm pedidoDespesaForm = (PedidoDespesaForm) form;
		final ActionMessages errors = validate(pedidoDespesaForm, request);
		
		if(errors.isEmpty()){
			final PedidoDespesaTO pedidoDespesaTO = pedidoDespesaForm.getTheItem();
			final PedidoDespesa pedidoDespesa = PedidoDespesaAssembler.getInstance().entityTO2Entity(pedidoDespesaTO);
			if (PedidoDespesaBO.getInstance().save(pedidoDespesa)) {
				request.setAttribute("pedidoDespesa", pedidoDespesa);
				return mapping.findForward("itensPedidoDespesa");
			}
		}
		saveErrors(request, errors);
		return prepareSave(mapping, form, request, response);
	}

	
	/**
	 * Método que prepara o formulario para atualização. Feito testes de validação para verificar se há alguma parcela da nota fiscal
	 * paga, se houver é lançado um erro. Verificado também se a nota fiscal está com status de "cancelado", caso seja é dado um forward
	 * para o metodo "prepareReabrirNota". Este método, caso seja preciso mais na frente, pode envolver alguns tipos de validação para
	 * garantir que tal nota possa ser reaberta, exemplos de validação seria a utlização de uma senha para permitir reabrir a nota.
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
		final DivisaoDAO divisaoDao = new DivisaoDAO();
		final PedidoDespesaAssembler pedidoDespesaAssembler = new PedidoDespesaAssembler();
		final PedidoDespesaBO bo = new PedidoDespesaBO();
		final PedidoDespesaForm pedidoDespesaForm = (PedidoDespesaForm) form;
		final PedidoDespesa pd = bo.findById(Integer.parseInt(pedidoDespesaForm.getTheItem().getId()));

		if(pd.getStatus()!= Status.CONFIRMADO){
				final List<Divisao> listAllDivisao = divisaoDao.findAll();
				final List<Laboratorio> listAllLaboratorios = new LaboratorioDAO().findAll();
				final PedidoDespesaTO pdTO =  pedidoDespesaAssembler.entity2EntityTO(pd);
				pedidoDespesaForm.setTheItem(pdTO);
				request.setAttribute("listAllDivisao", listAllDivisao);
				request.setAttribute("listAllLaboratorio", listAllLaboratorios);
				request.setAttribute("operacao", "update");
				request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
				return mapping.findForward("index");

		}else{
			errors.add("statusConfirmado",new ActionMessage("status.confirmado.error"));
		}
		saveErrors(request, errors);
		return listAll(mapping, form, request, response);
	}

	/**
	 * Método que atualiza a nota fiscal. Forward para prepareSave da classe ItensNotaFiscal
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		final PedidoDespesaAssembler pedidoDespesaAssembler = new PedidoDespesaAssembler();
		final ActionMessages errors = new ActionMessages();
		final PedidoDespesaBO bo = new PedidoDespesaBO();
		final PedidoDespesaForm pedidoDespesaForm = (PedidoDespesaForm) form;
		
		final PedidoDespesaTO pedidoDespesaTO = pedidoDespesaForm.getTheItem();
		final PedidoDespesa pedidoDespesa = PedidoDespesaBO.getInstance().findById(Integer.parseInt(pedidoDespesaTO.getId()));
						
		pedidoDespesaForm.setTheItem(pedidoDespesaAssembler.entity2EntityTO(pedidoDespesa));
			
		
		if(validate(pedidoDespesaForm, request).isEmpty()){
			if(bo.update(pedidoDespesa)){
				request.setAttribute("pedidoDespesa", pedidoDespesa);
				return mapping.findForward("itensPedidoDespesa");
			} else {
				errors.add("failSave", new ActionMessage("erro.salvar"));
			}
		}
		saveErrors(request, errors);
		return prepareUpdate(mapping, form, request, response);
	}

	/**
	 * Método que altera o status da NotaFiscal para "cancelado", assim como seus itens e suas parcelas. Este método sensibiliza
	 * a tabela "ContasReceber" buscando todas as contas de uma dada nota fiscal e excluindo-as do banco de dados.
	 * É garantido que a exclusão seja feita por completo ou a mesma não será feita. Ou seja, a exclusão e atualização de todas as
	 * listagens (lista de itens, lista de parcelas, lista de contas a receber e o item nota fiscal) é feito dentro de uma unica
	 * transação.
	 * É garantido tambem que uma nota fiscal só pode ser excluida caso não exista nenhuma parcela paga para tal nota fiscal
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		final BO<PedidoDespesa> pedidoDespesaBO = PedidoDespesaBO.getInstance();

		final ActionMessages errors = new ActionMessages();

		final PedidoDespesaForm pedidoDespesaForm = (PedidoDespesaForm) form;

		final List<PedidoDespesa> pedidoDespesaToDelete = new ArrayList<PedidoDespesa>();
		
		final List<PedidoDespesaTO> pedidoDespesaTO = pedidoDespesaForm.getItems();
		
		for(int i = 0; i < pedidoDespesaTO.size(); i++){
			
			if (pedidoDespesaTO.get(i).isChecked()){
				
				pedidoDespesaToDelete.add(new PedidoDespesaAssembler().entityTO2Entity(pedidoDespesaTO.get(i)));
			}
		}
		if (!pedidoDespesaToDelete.isEmpty()){
			if(!pedidoDespesaBO.delete(pedidoDespesaToDelete)){
				errors.add("contaPaga", new ActionMessage("contaPaga.error.paga"));
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

		final PedidoDespesaForm pedidoDespesaForm = (PedidoDespesaForm) form;
		
		final List<PedidoDespesa> pedidoDespesa = ((PedidoDespesaBO)PedidoDespesaBO.getInstance()).findPedido30();
		final List<PedidoDespesaTO> pedidoDespesaTO = new PedidoDespesaAssembler().entity2EntityTO(pedidoDespesa);
		
		pedidoDespesaForm.setItems(pedidoDespesaTO);
		request.setAttribute(LOAD_PAGE, LIST_ALL);
		return mapping.findForward("index");
	}
	
	/**
	 * Metodo de validação do formulario
	 * @param pedidoDespesaForm
	 * @param request
	 * @return ActionMessages
	 */
	private ActionMessages validate(PedidoDespesaForm pedidoDespesaForm,
			HttpServletRequest request) {

		final ActionMessages errors = new ActionMessages();
		final String dataSaida = pedidoDespesaForm.getTheItem().getDataPD();
		final String numeroPD = pedidoDespesaForm.getTheItem().getNumeroPD();
		final String tipoServico = pedidoDespesaForm.getTheItem().getTipoServico();

		if (!GenericValidator.isBlankOrNull(dataSaida)){
				
			try {
				ConverteData.retornaData(dataSaida);

			} catch (Exception e) {
				errors.add("dataInvalida",new ActionMessage("data.invalida.erro"));
			}
			
		} else {
			errors.add("dataVazio",new ActionMessage("image.error"));
		}
		if (GenericValidator.isBlankOrNull(numeroPD)){
			errors.add("numeroPD", new ActionMessage("image.error"));
		}
		if (GenericValidator.isBlankOrNull(tipoServico)){
			errors.add("tipoServico",new ActionMessage("image.error"));
		}
		saveErrors(request, errors);
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
			errors.add("dataVazio", new ActionMessage("data.vazio.error"));
		}
		if (errors.isEmpty()){
			
			final PedidoDespesaForm pedidoDespesaForm = (PedidoDespesaForm) form;
			final List<PedidoDespesa> pedidoDespesa = ((PedidoDespesaBO)PedidoDespesaBO.getInstance()).findByDay(dataInicial, dataFinal);
			final List<PedidoDespesaTO> pedidoDespesaTO = new PedidoDespesaAssembler().entity2EntityTO(pedidoDespesa);
			pedidoDespesaForm.setItems(pedidoDespesaTO);
		}
		saveErrors(request, errors);
		request.setAttribute(LOAD_PAGE, LIST_ALL);
		return mapping.findForward("index");
	}
	
	
	public ActionForward confirmaPD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
		final ActionMessages errors = new ActionMessages();
		final PedidoDespesaForm pedidoDespesaForm = (PedidoDespesaForm) form;
		final String idPD = pedidoDespesaForm.getTheItem().getId();
		final int idPedidoDespesa = Integer.parseInt(idPD);
		
		final PedidoDespesa pedidoDespesa = PedidoDespesaBO.getInstance().findById(idPedidoDespesa);
		if (pedidoDespesa.getStatus() !=Status.COTADO){
			errors.add("valorNaoCotado", new ActionMessage("valor.nao.cotado"));
		}
		if (errors.isEmpty()){
			pedidoDespesa.setStatus(Status.CONFIRMADO);

			final List<ItensPedidoDespesa> itensPedidoDespesa = ItensPedidoDespesaBO.getInstance().findAllItensByNumeroPD(pedidoDespesa);

			for (ItensPedidoDespesa itensPD : itensPedidoDespesa) {
				itensPD.setStatus(Status.CONFIRMADO);
			}

			((PedidoDespesaBO)PedidoDespesaBO.getInstance()).aprovaPD(pedidoDespesa, itensPedidoDespesa);
		}
		return listAll(mapping, form, request, response);
	}
	
	public ActionForward prepareListCotacao(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final ActionMessages errors = new ActionMessages();
		final List<PedidoDespesa> listPD = ((PedidoDespesaBO)PedidoDespesaBO.getInstance()).findAllAguardando();
		if (!listPD.isEmpty()){
			final List<PedidoDespesaTO> listPDTo = new PedidoDespesaAssembler().entity2EntityTO(listPD);
			request.setAttribute("listPedido", listPDTo);
			request.setAttribute(LOAD_PAGE, LIST_COTAR);
			return mapping.findForward("index");
		} 
		errors.add("listaVazia", new ActionMessage("lista.pd.vazia"));
		saveErrors(request, errors);
		request.setAttribute(LOAD_PAGE, HOME);
		return mapping.findForward("index");
	}	
	
}
