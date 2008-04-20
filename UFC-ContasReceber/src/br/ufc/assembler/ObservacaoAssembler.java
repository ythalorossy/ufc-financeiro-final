package br.ufc.assembler;

import java.util.ArrayList;
import java.util.List;

import br.com.Observacao;
import br.ufc.TO.ObservacaoTO;

import com.converte.ConverteData;

public class ObservacaoAssembler {
	
	public static ObservacaoAssembler getInstance(){
		return new ObservacaoAssembler();
	}
	
	public ObservacaoTO entity2EntityTO(Observacao entity) {
		final ObservacaoTO to = new ObservacaoTO();
		to.setId(String.valueOf(entity.getId()));
		to.setObservacao(entity.getObservacao());
		try {
			to.setData(ConverteData.retornaData(entity.getData()));
		} catch (Exception e) {
		}
		
		return to;
	}
	public List<ObservacaoTO> entity2EntityTO(List<Observacao> entity) {
		final List<ObservacaoTO> to = new ArrayList<ObservacaoTO>();
		for (Observacao observacao : entity) {
			to.add(entity2EntityTO(observacao));
		}
		return to;
	}
	
	
	public Observacao to2Entity(ObservacaoTO to) {
		final Observacao entity = new Observacao();
		entity.setId(Integer.parseInt(to.getId()));
		entity.setObservacao(to.getObservacao());
		try {
			entity.setData(ConverteData.retornaData(to.getData()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	public List<Observacao> to2Entity(List<ObservacaoTO> to) {
		final List<Observacao> entity = new ArrayList<Observacao>();
		for (ObservacaoTO obTO: to) {
			entity.add(to2Entity(obTO));
		}
		
		return entity;
	}
	
	

}
