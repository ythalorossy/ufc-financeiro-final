package br.ufc.BO;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.ContasReceber;
import br.com.NotaFiscal;
import br.com.Observacao;
import br.com.Parcela;
import br.ufc.DAO.DAO;
import br.ufc.DAO.ParcelaDAO;
import br.ufc.uteis.Status;

public class ParcelaBO implements BO<Parcela> {
	
	private DAO<Parcela> dao = new ParcelaDAO();
	private boolean retorno = false;
	
	public boolean delete(Parcela e) {
		return false;
	}

	public boolean save(Parcela e) {
		// Recuperando a nota fiscal
		final NotaFiscal notaFiscal = NotaFiscalBO.getInstance().findById(e.getIdNotaFiscal().getId());
		// recuperando o valor total de parcelas cadastrados no banco para aquela parcela
		final List<Parcela> listParcela = ((ParcelaBO)ParcelaBO.getInstance()).findAllByNf(notaFiscal.getId());
		int numeroParcela = listParcela.size()+1;
		if (listParcela.size() >0){
			for (int i = 0; i < listParcela.size(); i++) {
				if (numeroParcela == listParcela.get(i).getNumeroParcela()){
					numeroParcela += 1;
				}
			}
			e.setNumeroParcela(numeroParcela);
			
		}else{
			e.setNumeroParcela(listParcela.size()+1);
		}
		double soma = 0;
		try{
			soma = findSum(notaFiscal.getId());
		} catch (Exception ex) {
			soma = 0;
		}
		final double valorParcela = e.getValor();
		
		// Testando se o valor total + o valor da parcela é maior que o valor total da nota fiscal 
		if((notaFiscal.getValorNota()-(soma+valorParcela))>=0 && (notaFiscal.getValorNota()-(soma))>0){
			final ContasReceber contasReceber = retornaContasReceber(e);
			((ParcelaDAO)dao).save(e, contasReceber);
			retorno = true;
		}		
		return retorno;
	}

	public boolean update(Parcela e) {
		return false;
	}

	@Override
	public List<Parcela> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Parcela findById(int id) {
		return dao.findById(id);
	}

	@Override
	public DAO<Parcela> getDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	public static BO<Parcela> getInstance() {
		return new ParcelaBO();
	}

	public double findSum(int idNotaFiscal) {
		// recuperado o valor do somatorio de uma dada parcela a partir de um id de nota fiscal
		final double soma = ((ParcelaDAO)dao).findSumParcelasByNF(idNotaFiscal);
		return soma;
	}

	public boolean saveAutomatico(Parcela parcela, int quantidade) {
		// Iteração para gerar o parcelamento automatico
		for (int i = 0; i < quantidade; i++) { 
			// Verificação de Dizimas periódicas e correção do valor da ultima parcela
			if ((quantidade - i) == 1){
				// Resgatando o valor do somatório das parcelas até este momento
				double somatorio = 0;
				try {
					somatorio = findSum(parcela.getIdNotaFiscal().getId());
				} catch (Exception e) {
					// somatorio = a nulo
				}
				// Resgatando o valor total da nota fiscal
				final double valorTotalNF = parcela.getIdNotaFiscal().getValorNota();
				// Calculando a diferença entre o valor da nota fiscal e o valor do somatorio
				final double diferenca = valorTotalNF - somatorio;
				// verificando se a diferença é maior que o valor da parcela
				if (diferenca != parcela.getValor()){
					// Inserindo no valor da parcela o valor da diferença
					parcela.setValor(diferenca);
				}
			}
			parcela.setNumeroParcela(i+1);
			// parcela é salva com a data inicial digitada
			if(ParcelaBO.getInstance().save(parcela)){
				// recuperado um calendario a partir da data de pagamento da parcela
				GregorianCalendar calendario = parcela.getDataPagamento();
				// adicionado 1 ao mês atual
				calendario.add(GregorianCalendar.MONTH, 1);
				// setado a nova data de pagamento a parcela
				parcela.setDataPagamento(calendario);
				
				retorno = true;
			} else {
				retorno = false;
				break;
			}
		}
		return retorno;
	}

	
	public List<Parcela> findAllByNf(int id) {
		return ((ParcelaDAO)dao).findByIdNf(id);
	}
	
	/**
	 * Método que retorna um objeto ContasReceber
	 * @param parcela
	 * @return
	 */
	public ContasReceber retornaContasReceber (Parcela parcela){
		
		final ContasReceber contasReceber = new ContasReceber();
		contasReceber.setDataPrevista(parcela.getDataPagamento());
		contasReceber.setDataEfetiva(parcela.getDataPagamento());
		contasReceber.setIdCliente(parcela.getIdCliente());
		contasReceber.setIdNotaFiscal(parcela.getIdNotaFiscal());
		contasReceber.setIdParcela(parcela.getId());
		contasReceber.setValorPrevista(parcela.getValor());
		contasReceber.setStatus(Status.ABERTO);
		contasReceber.setObservacao(new ArrayList<Observacao>());
		
		return contasReceber;
		
	}

	public boolean delete(List<Parcela> parcela2Delete) {
		final List<Parcela> parcelasPaga = new ArrayList<Parcela>();
		for (int i = 0; i < parcela2Delete.size(); i++){
			 parcelasPaga.addAll(verificaParcelaPaga(parcela2Delete.get(i).getIdNotaFiscal().getId()));
		}
		if (parcelasPaga.isEmpty()){
			final List<ContasReceber> contasReceber2Delete = new ArrayList<ContasReceber>();
			for (int i = 0; i < parcela2Delete.size(); i++){
				final ContasReceber contasReceber = ((ContasReceberBO)ContasReceberBO.getInstance()).findAllByIdParcela(parcela2Delete.get(i).getId());
				if (contasReceber != null){
					contasReceber2Delete.add(contasReceber);
				}
			}
			retorno = ((ParcelaDAO)dao).delete(parcela2Delete, contasReceber2Delete);
		}
		
		return retorno;
	}
	
	public List<Parcela> verificaParcelaPaga(int nf){
		return new ParcelaDAO().findByIdNfPaga(nf,Status.PAGO); 
	}


}
