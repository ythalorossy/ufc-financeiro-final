package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import com.converte.ConverteData;

import br.com.AcompanhamentoPD;
import br.ufc.BO.PedidoDespesaBO;
import br.ufc.DAO.DivisaoDAO;
import br.ufc.TO.AcompanhamentoPDTO;

public class AcompanhamentoPDAssembler implements ASSEMBLER<AcompanhamentoPD, AcompanhamentoPDTO> {
	
	public AcompanhamentoPDTO entity2EntityTO(AcompanhamentoPD entity) {
		final AcompanhamentoPDTO to = new AcompanhamentoPDTO();
		to.setDataEnvio(ConverteData.retornaData(entity.getDataEnvio()));
		to.setDataRecebimento(ConverteData.retornaData(entity.getDataRecebimento()));
		to.setDivisao(String.valueOf(entity.getDivisao()));
		to.setId(String.valueOf(entity.getId()));
		try {
			to.setNomeDivisao(new DivisaoDAO().findById(entity.getDivisao()).getNome());
		} catch (Exception e) {
			to.setNomeDivisao("");
		}
		to.setObservacao(entity.getObservacao());
		to.setPd(String.valueOf(entity.getPd().getId()));
		to.setNumeroPD(entity.getPd().getNumeroPD());
		return to;
	}

	public List<AcompanhamentoPDTO> entity2EntityTO(List<AcompanhamentoPD> entity) {
		final List<AcompanhamentoPDTO> listTO = new ArrayList<AcompanhamentoPDTO>();
		for (AcompanhamentoPD pd : entity) {
			listTO.add(entity2EntityTO(pd));
		}
		return listTO;
	}

	public AcompanhamentoPD entityTO2Entity(AcompanhamentoPDTO to) {
		final AcompanhamentoPD entity = new AcompanhamentoPD();
		try {
			entity.setDataEnvio(ConverteData.retornaData(to.getDataEnvio()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			entity.setDataRecebimento(ConverteData.retornaData(to.getDataRecebimento()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			entity.setDivisao(Integer.parseInt(to.getDivisao()));
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			entity.setId(Integer.parseInt(to.getId()));
		} catch (Exception e) {
			entity.setId(0);
		}
		
		entity.setObservacao(to.getObservacao());
		
		try {
			entity.setPd(PedidoDespesaBO.getInstance().findById(Integer.parseInt(to.getPd())));
		} catch (Exception e) {
			e.getMessage();
		}
		
		return entity;

	}

	public List<AcompanhamentoPD> entityTO2Entity(List<AcompanhamentoPDTO> to) {
		final List<AcompanhamentoPD> listEntity = new ArrayList<AcompanhamentoPD>();
		for (AcompanhamentoPDTO pdTO : to) {
			listEntity.add(entityTO2Entity(pdTO));
		}
		return listEntity;
	}

	public static ASSEMBLER<AcompanhamentoPD, AcompanhamentoPDTO> getInstance() {
		return new AcompanhamentoPDAssembler();
	}

}