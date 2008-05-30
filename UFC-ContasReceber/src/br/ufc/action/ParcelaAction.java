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

import br.com.NotaFiscal;
import br.com.Parcela;
import br.ufc.BO.BO;
import br.ufc.BO.NotaFiscalBO;
import br.ufc.BO.ParcelaBO;
import br.ufc.DAO.ClientesDAO;
import br.ufc.TO.ParcelaTO;
import br.ufc.assembler.ParcelaAssembler;
import br.ufc.form.ParcelaForm;
import br.ufc.uteis.Status;

import com.Auxiliar.Clientes;
import com.converte.ConverteData;
import com.fdr.ConverteNumero.ConverteNumero;

@SuppressWarnings("serial")
public class ParcelaAction extends DispatchAction implements Serializable {
	
	private final String LOAD_PAGE = "loadPage";
	private final String PREPARE_SAVE = "/WEB-INF/pages/parcela/parcelas.jsp";
	
	public ActionForward prepareSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		final ParcelaForm parcelaForm = (ParcelaForm) form;
		
		final ClientesDAO clientesDao = new ClientesDAO();
		final NotaFiscal notaFiscal = (NotaFiscal) request.getAttribute("notaFiscal");
		final Clientes cliente = clientesDao.findById(notaFiscal.getIdCliente());
		final String valorTotal = ConverteNumero.converteNumero(notaFiscal.getValorNota());
		String valorUtilizado;
		double valorParcelas;
		try {
			valorParcelas = ((ParcelaBO)ParcelaBO.getInstance()).findSum(notaFiscal);
			valorUtilizado = ConverteNumero.converteNumero(valorParcelas);
		} catch (Exception e) {
						// Caso a conversão dispare um erro significa que o retorno do metodo findSum foi nulo e não foi possivel
						// converter nulo em um numero Double
			valorUtilizado = "0,00";
			valorParcelas = 0;
		} 
		final String valorRestante = ConverteNumero.converteNumero(notaFiscal.getValorNota()- valorParcelas);
		
		
		
		final List<Parcela> listParcela = ((ParcelaBO)ParcelaBO.getInstance()).findAllByNf(notaFiscal);
		final List<ParcelaTO> listTO = ParcelaAssembler.getInstance().entity2EntityTO(listParcela);
		parcelaForm.setItems(listTO);
		
		
		request.setAttribute("notaFiscal", notaFiscal.getNotaFiscal());
		request.setAttribute("numeroProcesso", notaFiscal.getNumeroProcesso());
		request.setAttribute("numeroContrato", notaFiscal.getNumeroContrato());
		request.setAttribute("dataSaida", ConverteData.retornaData(notaFiscal.getDataSaida()));
		request.setAttribute("cliente", cliente.getNome());
		request.setAttribute("valorTotal", valorTotal);
		request.setAttribute("valorUtilizado", valorUtilizado);
		request.setAttribute("valorRestante", valorRestante);
		request.setAttribute("idNotaFiscal", notaFiscal.getId());
		request.setAttribute("idCliente", cliente.getId());
		
		request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
		return mapping.findForward("index");
	}

	public ActionForward saveAutomatico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		
		final Parcela parcela = new Parcela();
		// recuperando o id da nota fiscal
		final int idNotaFiscal = Integer.parseInt(request.getParameter("idNotaFiscal"));
		// recuperando o id do cliente
		final String idCliente = request.getParameter("idCliente");
		// recuperando o valor restante 
		final String valorSobra = request.getParameter("valorRestante");
		// recupereando a Nota fiscal referente ao id da nota fiscal
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(idNotaFiscal);

		
		request.setAttribute("metodo", "saveAutomatico");
		final ActionMessages errors = validate(form, request);
		// validação dos campos do formulário
		if (errors.isEmpty()){
			// recuperando a data de pagamento
			final String dataPagamento = request.getParameter("dataPagamento");
			// recuperando a quantidade de parcelas que se deseja gerar automaticamente
			final int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			
			if(quantidade >1 && notaFiscal.getNumeroContrato().equals("")){
				errors.add("numeroContrato", new ActionMessage("contrato.required"));
			}
			if(errors.isEmpty()){
				// Conversão do valorRestante para um double
				final double valorRestante = ConverteNumero.converteNumero(valorSobra);
				// Divisao do valorRestante pelo saldo convertendo o resultado para uma String
				// Isto é necessario para que possa ser salvo corretamente no banco
				final String saldo = ConverteNumero.converteNumero(valorRestante/quantidade); 
				// Conversão da String em um double
				final double valorParcela = ConverteNumero.converteNumero(saldo);

				if(notaFiscal.getDataSaida().before(ConverteData.retornaData(dataPagamento))){
					// setando os atributos no objeto parcela
					parcela.setIdCliente(idCliente);
					parcela.setIdNotaFiscal(notaFiscal);
					parcela.setValor(valorParcela);
					parcela.setDataPagamento(ConverteData.retornaData(dataPagamento));
					parcela.setStatus(Status.ABERTO);

					if (!((ParcelaBO)ParcelaBO.getInstance()).saveAutomatico(parcela, quantidade)){
						errors.add("valorMaior",new ActionMessage("valorMaior.parcela.error"));
					}
				} else {
					errors.add("dataMenor",new ActionMessage("dataMenor.parcela.error"));
				}
			}
		}
		// setado no request o objeto Nota Fiscal para que possa ser utilizado no metodo prepareSave
		request.setAttribute("notaFiscal", notaFiscal);
		saveErrors(request, errors);
		return prepareSave(mapping, form, request, response);
	}
	
	public ActionForward saveManual(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		
		final ParcelaForm parcelaForm = (ParcelaForm) form;
		final ParcelaTO to = parcelaForm.getTheItem();
		to.setStatus(String.valueOf(Status.ABERTO));
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(Integer.parseInt(to.getIdNotaFiscal()));
		request.setAttribute("metodo", "saveManual");
		final ActionMessages errors = validate(form, request);

		if (errors.isEmpty()){
		
			final Parcela parcela = ParcelaAssembler.getInstance().entityTO2Entity(to);
			final double valorDiferenca = notaFiscal.getValorNota() - parcela.getValor(); 
			
			if(notaFiscal.getNumeroContrato().equals("")&& valorDiferenca != 0){
				errors.add("numeroContrato", new ActionMessage("contrato.required"));
			}
			if(errors.isEmpty()){
				if(notaFiscal.getDataSaida().before(ConverteData.retornaData(to.getDataPagamento()))){
					// validação dos campos do formulário

					// setando os atributos no objeto parcela

					if (!ParcelaBO.getInstance().save(parcela)){
						errors.add("valorMaior",new ActionMessage("valorMaior.parcela.error"));
					} 


				}else {
					errors.add("dataMenor",new ActionMessage("dataMenor.parcela.error"));
				}
			}
		} 
		saveErrors(request, errors);
		request.setAttribute("notaFiscal", notaFiscal);
		return prepareSave(mapping, form, request, response);
	}

	public ActionForward prepareUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		// Método não utilizado
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		// Método não utilizado
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		final ParcelaForm parcelaForm = (ParcelaForm) form;
		final BO<Parcela> bo = ParcelaBO.getInstance();
		final ActionMessages errors = new ActionMessages();
		
		final List<Parcela> parcela2Delete = new ArrayList<Parcela>();
		final List<ParcelaTO> parcelaTO = parcelaForm.getItems();
		
		// Iteração para localizar os objetos checked's
		for(int i = 0; i < parcelaTO.size(); i++){
			if (parcelaTO.get(i).isChecked()){
				final Parcela parcela = new ParcelaAssembler().entityTO2Entity(parcelaTO.get(i)); 
				parcela2Delete.add(parcela);
			}
		}
		// Caso  lista não esteja vazia
		if (!parcela2Delete.isEmpty()){
			if(!((ParcelaBO)bo).delete(parcela2Delete)){
				errors.add("",null);
			} else {
				final NotaFiscal notaFiscal = parcela2Delete.get(0).getIdNotaFiscal();
				request.setAttribute("notaFiscal", notaFiscal);
			}
		}
		
		saveErrors(request, errors);
		return prepareSave(mapping, form, request, response);
	}
	
	public ActionForward finalizarNota(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * Método que finaliza a nota fiscal. Aqui é verificado se o valor total da nota fiscal esta sendo utilizado
		 * se estiver com sobra é lançado um erro na tela solicitando que tal valor seja completamente utilizado.
		 * Caso esteja totalmente utilizado é chamado uma ação para atualizar o status da nota fiscal para Faturado
		 */
		final ActionMessages errors = new ActionMessages();
		final int idNotaFiscal = Integer.parseInt(request.getParameter("idNotaFiscal"));
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(idNotaFiscal);
		final double somaParcela = ((ParcelaBO)ParcelaBO.getInstance()).findSum(notaFiscal);
		if((notaFiscal.getValorNota()-somaParcela) !=0){
			errors.add("valorNaoUtilizado", new ActionMessage("valor.somatorio.diferente"));
		}else {
			notaFiscal.setStatus(Status.FATURADO);
			NotaFiscalBO.getInstance().update(notaFiscal);
			
			return mapping.findForward("listNotaFiscal");
		}
		saveErrors(request, errors);
		request.setAttribute("notaFiscal", notaFiscal);
		return prepareSave(mapping, form, request, response);
	}
	
	private ActionMessages validate(ActionForm form, HttpServletRequest request) {
		/*
		 * Método que valida os campos do formulário. Testando se estão em branco e se campos de data e valor são realmente
		 * datas e valores
		 */
		final String metodo = (String) request.getAttribute("metodo");
		final ParcelaForm parcelaForm = (ParcelaForm) form;
		final String valor = request.getParameter("valor") == null ? parcelaForm.getTheItem().getValor() : request.getParameter("valor");
		final String dataPagamento = request.getParameter("dataPagamento") == null ? parcelaForm.getTheItem().getDataPagamento() : request.getParameter("dataPagamento");
		final String quantidade = request.getParameter("quantidade"); 
		final ActionMessages errors = new ActionMessages();
		
		if (!metodo.equals("saveAutomatico")){
			if (!GenericValidator.isBlankOrNull(valor)){
				try {
					ConverteNumero.converteNumero(valor);
				} catch (Exception e) {
					errors.add("valorInvalido",new ActionMessage("valor.invalido.error"));
				}
			} else {
				errors.add("valorVazio",new ActionMessage("valor.vazio.error"));
			}
			
		} else {
			if (!GenericValidator.isBlankOrNull(quantidade)){
				try {
					Integer.parseInt(quantidade);
					if (Integer.parseInt(quantidade)== 0 ){
						errors.add("quantidadeNulo",new ActionMessage("quantidade.nulo.error"));
					}
				} catch (Exception e) {
					errors.add("quantidadeInvalido",new ActionMessage("quantidadeInvalido.vazio.error"));
				}
			} else {
				errors.add("quantidadeVazio", new ActionMessage("quantidade.vazio.error"));
			}
		}
		
		if (!GenericValidator.isBlankOrNull(dataPagamento)){
			try {
				ConverteData.retornaData(dataPagamento);
			} catch (Exception e) {
				errors.add("dataInvalido",new ActionMessage("data.invalida.erro"));
			}
		}else{
			errors.add("dataVazio",new ActionMessage("data.vazio.erro"));
		}
		saveErrors(request, errors);
		return errors;
	}

}
