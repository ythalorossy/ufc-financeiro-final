package br.ufc.action;

import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import br.com.AcompanhamentoPD;
import br.com.PedidoDespesa;
import br.ufc.BO.AcompanhamentoPDBO;
import br.ufc.BO.PedidoDespesaBO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.DAO.LaboratorioDAO;
import br.ufc.TO.AcompanhamentoPDTO;
import br.ufc.assembler.AcompanhamentoPDAssembler;
import br.ufc.form.AcompanhamentoPDForm;

import com.Auxiliar.Divisao;
import com.Auxiliar.Laboratorio;

public class AcompanhamentoPDAction extends DispatchAction{
	
	private final String LOAD_PAGE = "loadPage";
	private final String PREPARE_SAVE = "/WEB-INF/pages/acompanhamentoPD/acompanhamentoPD.jsp";
	
	public ActionForward prepareSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		final String idPD = request.getParameter("idPD");
		
		final List<Laboratorio> upd = new LaboratorioDAO().findAll();
		final List<Divisao> divisao = new DivisaoDAO().findAll();
		final PedidoDespesa pedidoDespesa = PedidoDespesaBO.getInstance().findById(Integer.parseInt(idPD));
		final List<AcompanhamentoPD> listAPD = AcompanhamentoPDBO.getInstance().findByPD(pedidoDespesa);
		final List<AcompanhamentoPDTO> listAPDTO = AcompanhamentoPDAssembler.getInstance().entity2EntityTO(listAPD);
		
		final AcompanhamentoPD aPD = fillAcompanhamentoPD(pedidoDespesa);
		final AcompanhamentoPDTO apdTO = AcompanhamentoPDAssembler.getInstance().entity2EntityTO(aPD);
		final AcompanhamentoPDForm acompanhamentoPDForm = (AcompanhamentoPDForm) form;
		
		acompanhamentoPDForm.setTheItem(apdTO);
		request.setAttribute("listUPD", upd);
		request.setAttribute("listDivisao", divisao);
		request.setAttribute("listAPD", listAPDTO);
		request.setAttribute(LOAD_PAGE, PREPARE_SAVE);
		
		return mapping.findForward("index");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		final AcompanhamentoPDForm apdForm = (AcompanhamentoPDForm) form;
		final ActionMessages errors = validate(apdForm, request);
		if(errors.isEmpty()){
			final AcompanhamentoPD apd = AcompanhamentoPDAssembler.getInstance().entityTO2Entity(apdForm.getTheItem());
			if (!AcompanhamentoPDBO.getInstance().save(apd)){
				errors.add("",null);
			}
		}
		
		return mapping.findForward("listPD");
	}
	
	private ActionMessages validate(AcompanhamentoPDForm apdForm,
			HttpServletRequest request) {
		final ActionMessages errors = new ActionMessages();
		final String divisao = apdForm.getTheItem().getLaboratorio();
		final String observacao = apdForm.getTheItem().getObservacao();
		
		if (GenericValidator.isBlankOrNull(divisao)){
			errors.add("",null);
		}
		if (GenericValidator.isBlankOrNull(observacao)){
			errors.add("",null);
		}
		return errors;
	}

	private AcompanhamentoPD fillAcompanhamentoPD(PedidoDespesa pedidoDespesa) {
		final List<AcompanhamentoPD> listAPD = AcompanhamentoPDBO.getInstance().findByPD(pedidoDespesa);
		final AcompanhamentoPD aPD = new AcompanhamentoPD();
		if(listAPD.isEmpty() || listAPD==null){
			aPD.setDataEnvio(new GregorianCalendar());
			aPD.setDataRecebimento(pedidoDespesa.getDataPD());
			aPD.setPd(pedidoDespesa);
		} else {
			aPD.setDataEnvio(new GregorianCalendar());
			aPD.setPd(pedidoDespesa);
			aPD.setDataRecebimento(listAPD.get(0).getDataEnvio());
		}
		return aPD;
	}

}
