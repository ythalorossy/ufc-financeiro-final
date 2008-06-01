package br.ufc.action;

import java.util.ArrayList;
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
import br.com.ConverteNumero.ConverteNumero;
import br.ufc.BO.ItensPedidoDespesaBO;
import br.ufc.BO.PedidoDespesaBO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.DAO.LaboratorioDAO;
import br.ufc.TO.ItensPedidoDespesaTO;
import br.ufc.TO.PedidoDespesaTO;
import br.ufc.assembler.ItensPedidoDespesaAssembler;
import br.ufc.assembler.PedidoDespesaAssembler;
import br.ufc.form.ItensPedidoDespesaForm;
import br.ufc.uteis.Status;

import com.Auxiliar.Divisao;
import com.Auxiliar.Laboratorio;
import com.converte.ConverteData;

public class ItensPedidoDespesaAction extends DispatchAction{
	
	private final String LOAD_PAGE = "loadPage";
	private final String PREPARE_SAVE = "/WEB-INF/pages/itensPedidoDespesa/itensPedidoDespesa.jsp";
	private final String COTAR = "/WEB-INF/pages/itensPedidoDespesa/cotarPedidoDespesa.jsp";
	
	
	
	public ActionForward prepareSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		final String operacao = (String) request.getAttribute("operacao");
		
		try {
			if (operacao.equals("save")){
				form.reset(mapping, request);
			}
		} catch (Exception e) {
			
		}
		final ItensPedidoDespesaBO itensPedidoDespesaBO = new ItensPedidoDespesaBO();
		final PedidoDespesaBO pedidoDespesaBO = new PedidoDespesaBO();

						// Objeto do formulario 
		final ItensPedidoDespesaForm itensPedidoDespesaForm= (ItensPedidoDespesaForm) form;
						// Objeto recuperado do request
		final PedidoDespesa pedidoDespesa = (PedidoDespesa) request.getAttribute("pedidoDespesa");
		final PedidoDespesaTO pedidoDespesaTO = PedidoDespesaAssembler.getInstance().entity2EntityTO(pedidoDespesa);

		// Id da Nota Fiscal
		int idPedidoDespesa = pedidoDespesa.getId();
		
		
		final Divisao divisao = new DivisaoDAO().findById(pedidoDespesa.getIdDivisao());
		final Laboratorio lab = new LaboratorioDAO().findById(pedidoDespesa.getIdLaboratorio());

		String totalPedidoDespesa;
						// Bloco que recupera o valor total do somatorio de itens 
						// No caso de não existir item irá ser lançado uma exception que será tratada no bloco catch
		try {
			totalPedidoDespesa = ConverteNumero.converteNumero(itensPedidoDespesaBO.findSumPrevisto(pedidoDespesa));
			pedidoDespesa.setValorPrevisto(ConverteNumero.converteNumero(totalPedidoDespesa));
						// Atualizando o valor total na tabela pedido desepsa
			pedidoDespesaBO.update(pedidoDespesa);
		} catch (Exception e) {
						// Caso a conversão dispare um erro significa que o retorno do metodo findSum foi nulo e não foi possivel
						// converter nulo em um numero Double
			totalPedidoDespesa = "0,00";
		} 

						// Aqui é formatado a data para o Locale Brasil
		final String dataPD = ConverteData.retornaData(pedidoDespesa.getDataPD());
						// Lista de itens de uma dada nota fiscal
		
		final List<ItensPedidoDespesa> itensPD = itensPedidoDespesaBO.findAllItensByNumeroPD(pedidoDespesa);
						// Conversão da entidade persistente na entidade View
		final List<ItensPedidoDespesaTO> itensPedidoDespesaTO = new ItensPedidoDespesaAssembler().entity2EntityTO(itensPD);
		
		itensPedidoDespesaForm.setItems(itensPedidoDespesaTO);
		
		request.setAttribute("idPedidoDespesa", idPedidoDespesa);
		request.setAttribute("numeroPD", pedidoDespesa.getNumeroPD());
		request.setAttribute("projeto", pedidoDespesaTO.getProjeto());
		request.setAttribute("divisao", divisao.getNome());
		request.setAttribute("idDivisao", divisao.getId());
		request.setAttribute("laboratorio", lab.getNome());
		request.setAttribute("idLaboratorio", lab.getId());
		request.setAttribute("tipoServico", pedidoDespesa.getTipoServico());
		request.setAttribute("justificativa", pedidoDespesa.getJustificativa());
						// É setado o objeto no request para poder ser recuperado no metodo Save para ser reutilizado
		request.setAttribute("pedidoDespesa", pedidoDespesa);
		request.setAttribute("dataPD", dataPD);
		request.setAttribute("totalPedidoDespesa", totalPedidoDespesa);
						
		request.setAttribute("operacao", "save");
		request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
		return mapping.findForward("index");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		final ItensPedidoDespesaAssembler itensAssembler = new ItensPedidoDespesaAssembler();		
		final ItensPedidoDespesaForm itensForm = (ItensPedidoDespesaForm) form;
		final String idPedidoDepesa = itensForm.getTheItem().getIdPedidoDespesa();
		final PedidoDespesa pedidoDespesa = PedidoDespesaBO.getInstance().findById(Integer.parseInt(idPedidoDepesa));
		if (validate(itensForm, request).isEmpty()){
			final ItensPedidoDespesa itens = itensAssembler.entityTO2Entity(itensForm.getTheItem());
			ItensPedidoDespesaBO.getInstance().save(itens);
		}
		request.setAttribute("pedidoDespesa", pedidoDespesa);
		request.setAttribute("itensForm", itensForm);
		request.setAttribute("operacao", "save");
		return prepareSave(mapping, form, request, response); 
	}

	public ActionForward finalizarPD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		final PedidoDespesaBO pedidoDespesaBO = new PedidoDespesaBO();
		final String idPedidoDespesa = request.getParameter("idPedidoDespesa");
		final PedidoDespesa pedidoDespesa = pedidoDespesaBO.findById(Integer.parseInt(idPedidoDespesa));
		final List<ItensPedidoDespesa> itensPedidoDespesa = ItensPedidoDespesaBO.getInstance().findAllItensByNumeroPD(pedidoDespesa);
		
		request.setAttribute("pedidoDespesa", pedidoDespesa);
		pedidoDespesa.setStatus(Status.AGUARDANDO);
		pedidoDespesaBO.update(pedidoDespesa, itensPedidoDespesa);	
		
		return mapping.findForward("pedidoDespesa");
		
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
			
		final ActionMessages errors = new ActionMessages();
		final ItensPedidoDespesaForm itensPedidoDespesaForm = (ItensPedidoDespesaForm) form;
		final List<ItensPedidoDespesa> itensPedidoDespesa2Delete = new ArrayList<ItensPedidoDespesa>();
		final List<ItensPedidoDespesaTO> itensPedidoDespesaTO = itensPedidoDespesaForm.getItems();
		for (ItensPedidoDespesaTO itensPdTO : itensPedidoDespesaTO) {
			if (itensPdTO.isChecked()){
				final ItensPedidoDespesa itensPedidoDespesa = new ItensPedidoDespesaAssembler().entityTO2Entity(itensPdTO); 
				itensPedidoDespesa2Delete.add(itensPedidoDespesa);
			}
		}
		if (!itensPedidoDespesa2Delete.isEmpty()){
			if(!ItensPedidoDespesaBO.getInstance().delete(itensPedidoDespesa2Delete)){
				errors.add("",null);
			} else {
				final PedidoDespesa pedidoDespesa = itensPedidoDespesa2Delete.get(0).getIdPedidoDespesa();
				request.setAttribute("pedidoDespesa", pedidoDespesa);
			}
		}
		
		saveErrors(request, errors);
		return prepareSave(mapping, form, request, response);
		
	}
	
	private ActionMessages validate(ItensPedidoDespesaForm itensPedidoDespesaForm,
			HttpServletRequest request) {

		final ActionMessages errors = new ActionMessages();
		String quantidade = itensPedidoDespesaForm.getTheItem().getQuantidade();
		String valorUnitario = itensPedidoDespesaForm.getTheItem().getValorUnitario();
		String valorUnitarioCotado = itensPedidoDespesaForm.getTheItem().getValorUnitarioCotado();
		String item = itensPedidoDespesaForm.getTheItem().getItem();
		
		if (!GenericValidator.isBlankOrNull(quantidade)){
			try {
				Integer.parseInt(quantidade);
			} catch (Exception e) {
				errors.add("quantidadeInvalido", new ActionMessage("quantidadeInvalido.vazio.error"));
			}
		} else {
			errors.add("quantidadeVazio", new ActionMessage("quantidade.vazio.error"));
		}
		
		if (!GenericValidator.isBlankOrNull(valorUnitario)){
			try {
				ConverteNumero.converteNumero(valorUnitario);
			} catch (Exception e) {
				errors.add("valorInvalido", new ActionMessage("valor.invalido.error"));
			}
		} else{
			valorUnitario = "0";
		}
		
		if (!GenericValidator.isBlankOrNull(valorUnitarioCotado)){
			try {
				ConverteNumero.converteNumero(valorUnitarioCotado);
			} catch (Exception e) {
				errors.add("valorInvalido", new ActionMessage("valor.invalido.error"));
			}
		} else{
			valorUnitarioCotado = "0";
		}
		
		if (GenericValidator.isBlankOrNull(item)){
			errors.add("servico", new ActionMessage("servico.vazio.error"));
		}
		saveErrors(request, errors);
		return errors;
	}
	
	

	public ActionForward prepareCotarPedidoDespesa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String idPedidoDespesa = (String) request.getParameter("idPedidoDespesa");
		final PedidoDespesa pedidoDespesa = PedidoDespesaBO.getInstance().findById(Integer.parseInt(idPedidoDespesa));
		final PedidoDespesaTO pdTO = new PedidoDespesaAssembler().entity2EntityTO(pedidoDespesa);
		final Divisao divisao = new DivisaoDAO().findById(pedidoDespesa.getIdDivisao());
		final Laboratorio lab = new LaboratorioDAO().findById(pedidoDespesa.getIdLaboratorio());
		
		final List<ItensPedidoDespesa> listIPD = ItensPedidoDespesaBO.getInstance().findAllItensByNumeroPD(pedidoDespesa);
		final List<ItensPedidoDespesaTO> listIPDTo = new ItensPedidoDespesaAssembler().entity2EntityTO(listIPD);

		ItensPedidoDespesaForm itensForm = (ItensPedidoDespesaForm) form;
		itensForm.setItems(listIPDTo);
		
		request.setAttribute("idPedidoDespesa", idPedidoDespesa);
		request.setAttribute("numeroPD", pedidoDespesa.getNumeroPD());
		request.setAttribute("projeto", pdTO.getProjeto());
		request.setAttribute("divisao", divisao.getNome());
		request.setAttribute("idDivisao", divisao.getId());
		request.setAttribute("laboratorio", lab.getNome());
		request.setAttribute("idLaboratorio", lab.getId());
		request.setAttribute("tipoServico", pedidoDespesa.getTipoServico());
		request.setAttribute("justificativa", pedidoDespesa.getJustificativa());
						// É setado o objeto no request para poder ser recuperado no metodo Save para ser reutilizado
		request.setAttribute("pedidoDespesa", pedidoDespesa);
		request.setAttribute("dataPD", ConverteData.retornaData(pedidoDespesa.getDataPD()));
		request.setAttribute("totalPedidoDespesa", ConverteNumero.converteNumero(pedidoDespesa.getValorPrevisto()));
		request.setAttribute("operacao", "cotarPedidoDespesa");
		
		request.setAttribute(LOAD_PAGE, COTAR);
		return mapping.findForward("index");
	}
	
	public ActionForward cotarPedidoDespesa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		final ItensPedidoDespesaForm itensForm = (ItensPedidoDespesaForm) form;
		final ActionMessages errors = validaCotacao(itensForm, request);
		
		if (errors.isEmpty()){
			final List<ItensPedidoDespesa> itensPD = new ItensPedidoDespesaAssembler().entityTO2Entity(itensForm.getItems());
			final PedidoDespesa pedidoDespesa = itensPD.get(0).getIdPedidoDespesa();
			if (((ItensPedidoDespesaBO)ItensPedidoDespesaBO.getInstance()).cotarPD(itensPD, pedidoDespesa)){
				return mapping.findForward("pedidoDespesa");
			}
		}
		saveErrors(request, errors);
		return prepareCotarPedidoDespesa(mapping, form, request, response);
	}
	
	public ActionMessages validaCotacao(ItensPedidoDespesaForm form, HttpServletRequest request){
		final ActionMessages errors = new ActionMessages();
		final List<ItensPedidoDespesaTO> listTO = form.getItems();
		
		for (ItensPedidoDespesaTO ipd : listTO){
			if (GenericValidator.isBlankOrNull(ipd.getValorUnitarioCotado())){
				errors.add("valorUnitarioCotado", new ActionMessage("valor.unitario.branco"));
				break;
			}
			
			if (errors.isEmpty()){
				try {
					ConverteNumero.converteNumero(ipd.getValorUnitarioCotado());
				} catch (Exception e) {
					errors.add("valorUnitarioInvalido", new ActionMessage("valor.unitario.invalido"));
				}
				
			}
			
		}
		saveErrors(request, errors);
		return errors;
		
	}
	
	
	
	

}
