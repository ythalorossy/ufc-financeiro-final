package br.ufc.action;

import java.io.Serializable;
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

import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;
import br.ufc.BO.BO;
import br.ufc.BO.ItensNotaFiscalBO;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.DAO.ClientesDAO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.DAO.LaboratorioDAO;
import br.ufc.TO.ItensNotaFiscalTO;
import br.ufc.assembler.ItensNotaFiscalAssembler;
import br.ufc.form.ItensNotaFiscalForm;

import com.Auxiliar.Clientes;
import com.Auxiliar.Divisao;
import com.Auxiliar.Laboratorio;
import com.converte.ConverteData;
import com.fdr.ConverteNumero.ConverteNumero;

@SuppressWarnings("serial")
public class ItensNotaFiscalAction extends DispatchAction implements Serializable {
	
	private final String LOAD_PAGE = "loadPage";
	private final String PREPARE_SAVE = "/WEB-INF/pages/itensNotaFiscal/itensNotaFiscal.jsp";
	
	
	public ActionForward prepareSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	
		final ItensNotaFiscalBO itensNotaFiscalBO = new ItensNotaFiscalBO();
		final NotaFiscalBO notaFiscalBO = new NotaFiscalBO();

						// Objeto do formulario ItensNotaFiscal
		final ItensNotaFiscalForm itensNotaFiscalForm= (ItensNotaFiscalForm) form;
						// Objeto NotaFiscal recuperado do request
		final NotaFiscal notaFiscal = (NotaFiscal) request.getAttribute("notaFiscal");
						// Id da Nota Fiscal
		int idNotaFiscal = notaFiscal.getId();
		
		
		final Clientes cliente = new ClientesDAO().findById(notaFiscal.getIdCliente());

		String totalNotaFiscal;
						// Bloco que recupera o valor total do somatorio de itens de uma dada nota fiscal
						// No caso de n�o existir item ir� ser lan�ado uma exception que ser� tratada no bloco catch
		try {
			totalNotaFiscal = ConverteNumero.converteNumero(itensNotaFiscalBO.findSum(notaFiscal));
			notaFiscal.setValorNota(ConverteNumero.converteNumero(totalNotaFiscal));
						// Atualizando o valor total na tabela nota fiscal
			notaFiscalBO.update(notaFiscal);
		} catch (Exception e) {
						// Caso a convers�o dispare um erro significa que o retorno do metodo findSum foi nulo e n�o foi possivel
						// converter nulo em um numero Double
			totalNotaFiscal = "0,00";
		} 

						// Aqui � formatado a data para o Locale Brasil
		final String dataNotaFiscal = ConverteData.retornaData(notaFiscal.getDataSaida());
						// Lista de itens de uma dada nota fiscal
		final List<ItensNotaFiscal> itensNF = itensNotaFiscalBO.findAllItensByNF(idNotaFiscal);
						// Convers�o da entidade persistente na entidade View
		final List<ItensNotaFiscalTO> itensTO = new ItensNotaFiscalAssembler().entity2EntityTO(itensNF);
		
		final List<Divisao> divisao = new DivisaoDAO().findAll();
		final List<Laboratorio> listLaboratorio = new LaboratorioDAO().findAll();
		itensNotaFiscalForm.setItems(itensTO);
		
		request.setAttribute("idNotaFiscal", idNotaFiscal);
		request.setAttribute("numeroProcesso", notaFiscal.getNumeroProcesso());
		request.setAttribute("numeroContrato", notaFiscal.getNumeroContrato());
		request.setAttribute("cliente", cliente.getNome());
		request.setAttribute("idCliente", cliente.getId());
		request.setAttribute("nf", notaFiscal.getNotaFiscal());
		request.setAttribute("divisao", divisao);
		request.setAttribute("laboratorios", listLaboratorio);
		
						// � setado o objeto notaFiscal no request para poder ser recuperado no metodo Save para ser reutilizado
		request.setAttribute("notaFiscal", notaFiscal);
		request.setAttribute("data", dataNotaFiscal);
		request.setAttribute("totalNotaFiscal", totalNotaFiscal);
						// request.setAttribute("pagina", "/WEB-INF/pages/itensNotaFiscal/itensNotaFiscal.jsp");
		request.setAttribute("operacao", "save");
		request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
		return mapping.findForward("index");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		final ItensNotaFiscalAssembler itensAssembler = new ItensNotaFiscalAssembler();		
										//Criado o formulario de ItensNotaFiscal
		final ItensNotaFiscalForm itensForm = (ItensNotaFiscalForm) form;
						// recuperado o ID da notaFiscal
		final String idNotaFiscal = itensForm.getTheItem().getIdNotaFiscal();
						// recuperado o Objeto notaFiscal a partir do id da NotaFiscal
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(Integer.parseInt(idNotaFiscal));
						// Setado no formulario o objeto notaFiscalTO
		if (validate(itensForm, request).isEmpty()){
						// convertido o objeto itensNotaFiscalForm em um objeto ItensNotaFiscal
			final ItensNotaFiscal itens = itensAssembler.entityTO2Entity(itensForm.getTheItem());
			itens.setData(notaFiscal.getDataSaida());
			ItensNotaFiscalBO.getInstance().save(itens);
		}
						// setado no request o objeto NotaFiscal para ser reutilizado no metodo prepareSave
		request.setAttribute("notaFiscal", notaFiscal);
		request.setAttribute("itensForm", itensForm);
		return prepareSave(mapping, form, request, response); 
	}

	public ActionForward finalizarNotaFiscal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		final NotaFiscalBO notaFiscalBO = new NotaFiscalBO();
						// recuperado o ID da notaFiscal
		final String idNotaFiscal = request.getParameter("idNotaFiscal");
						// recuperado o Objeto notaFiscal a partir do id da NotaFiscal
		final NotaFiscal notaFiscal = notaFiscalBO.findById(Integer.parseInt(idNotaFiscal));
		request.setAttribute("notaFiscal", notaFiscal);
		if(notaFiscal.getValorNota()==0){
			final ActionMessages errors = new ActionMessages();
			errors.add("valorVazio", new ActionMessage("itens.vazio.valor"));
			saveErrors(request, errors);
			return prepareSave(mapping, form, request, response);
		}
		
		
		return mapping.findForward("parcelaNotaFiscal");
		
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		// Cria��o do objeto de negocio
		final BO<ItensNotaFiscal> bo = ItensNotaFiscalBO.getInstance();
		// Cria��o da Listagem de erros
		final ActionMessages errors = new ActionMessages();
		// Cria��o do formul�rio
		final ItensNotaFiscalForm itensNotaFiscalForm = (ItensNotaFiscalForm) form;
		// Cria��o da lista de itens para dele��o
		final List<ItensNotaFiscal> itensNotaFiscal2Delete = new ArrayList<ItensNotaFiscal>();
		// Recuperando o Objeto TO
		final List<ItensNotaFiscalTO> itensNotaFiscalTO = itensNotaFiscalForm.getItems();
		
		// Itera��o sobre a lista de to buscando por objetos "checked"
		for (ItensNotaFiscalTO itensNFTO : itensNotaFiscalTO) {
			if (itensNFTO.isChecked()){
				// se o objeto estiver checked ele � inserindo na listagem para dele��o
				final ItensNotaFiscal itensNotaFiscal = new ItensNotaFiscalAssembler().entityTO2Entity(itensNFTO); 
				itensNotaFiscal2Delete.add(itensNotaFiscal);
			}
		}
		// Caso a listagem n�o esteja vazia � chamado o metodo de dele��o
		if (!itensNotaFiscal2Delete.isEmpty()){
			if(!bo.delete(itensNotaFiscal2Delete)){
				errors.add("",null);
			} else {
				final NotaFiscal notaFiscal = itensNotaFiscal2Delete.get(0).getIdNotaFiscal();
				request.setAttribute("notaFiscal", notaFiscal);
			}
		}
		
		saveErrors(request, errors);
		return prepareSave(mapping, form, request, response);
		
	}
	
	private ActionMessages validate(ItensNotaFiscalForm itensForm,
			HttpServletRequest request) {
		// M�todo que valida os campos do formul�rio
		final ActionMessages errors = new ActionMessages();
		final String quantidade = itensForm.getTheItem().getQuantidade();
		final String valor = itensForm.getTheItem().getValor();
		final String servico = itensForm.getTheItem().getServico();
		if (!GenericValidator.isBlankOrNull(quantidade)){
			// tentativa de convers�o da quantidade, validando um n�mero
			try {
				Integer.parseInt(quantidade);
			} catch (Exception e) {
				errors.add("quantidadeInvalido", new ActionMessage("quantidadeInvalido.vazio.error"));
			}
		} else {
			errors.add("quantidade", new ActionMessage("image.error.noText"));
		}
		if(quantidade.equals("0")){
			errors.add("quantidade", new ActionMessage("image.error.noText"));
		}
		if (!GenericValidator.isBlankOrNull(valor)){
			// tentativa de convers�o do valor, validando um valor
			try {
				ConverteNumero.converteNumero(valor);
			} catch (Exception e) {
				errors.add("valorInvalido", new ActionMessage("valor.invalido.error"));
			}
		} else{
			errors.add("valorVazio", new ActionMessage("image.error.noText"));
		}
		if (GenericValidator.isBlankOrNull(servico)){
			errors.add("servico", new ActionMessage("image.error.noText"));
		}
		saveErrors(request, errors);
		return errors;
	}

}
