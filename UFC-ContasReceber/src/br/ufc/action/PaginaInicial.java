package br.ufc.action;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import br.com.ContasReceber;
import br.ufc.BO.ContasReceberBO;
import br.ufc.TO.ContasReceberTO;
import br.ufc.assembler.ContasReceberAssembler;

import com.converte.ConverteData;

public class PaginaInicial extends DispatchAction{

	/*
	 * Classe utilizada para listar os inadimplentes e  vencimentos do dia
	 */
	
	private GregorianCalendar calendar = new GregorianCalendar();
	
	public ActionForward paginaInicial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ContasReceber> vencimentosDia = new ArrayList<ContasReceber>();
		List<ContasReceber> inadimplentes = new ArrayList<ContasReceber>(); 
		
		try{
			vencimentosDia = ((ContasReceberBO)ContasReceberBO.getInstance()).findAll(calendar);
			inadimplentes = ((ContasReceberBO)ContasReceberBO.getInstance()).findAllInadimplentes(calendar);

			final List<ContasReceberTO> vencimentosDiaTO = ContasReceberAssembler.getInstance().entity2EntityTO(vencimentosDia);
			final List<ContasReceberTO> inadimplentesTO = ContasReceberAssembler.getInstance().entity2EntityTO(inadimplentes);

			for (int i = 0; i < inadimplentesTO.size(); i++) {
				final GregorianCalendar dataInadimplente = ConverteData.retornaData(inadimplentesTO.get(i).getDataPrevista());
				final long diasAtraso = ((calendar.getTimeInMillis()/(24*60*60*1000)) - (dataInadimplente.getTimeInMillis()/(24*60*60*1000)));

				inadimplentesTO.get(i).setDiasAtraso(String.valueOf(diasAtraso));
			}

			request.setAttribute("vencimentosDia", vencimentosDiaTO);
			request.setAttribute("inadimplentes", inadimplentesTO);
		} catch (Exception e) {
			System.out.println("Nenhum registro encontrado!!");
		}
		request.setAttribute("loadPage", "/WEB-INF/pages/home.jsp");
		
		return mapping.findForward("paginaInicial");
	}

}
