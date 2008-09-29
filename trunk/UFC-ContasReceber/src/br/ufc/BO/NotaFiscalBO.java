package br.ufc.BO;

import java.util.GregorianCalendar;
import java.util.List;

import br.com.ContasReceber;
import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;
import br.com.Parcela;
import br.ufc.DAO.ContasReceberDAO;
import br.ufc.DAO.DAO;
import br.ufc.DAO.NotaFiscalDAO;
import br.ufc.uteis.Status;

public class NotaFiscalBO implements BO<NotaFiscal> {
	
	//objeto que realiza a interface DAO de comunicação com o banco de dados
	private DAO<NotaFiscal> notaFiscalDao = new NotaFiscalDAO();
	private boolean retorno = false;
	

	public boolean delete(NotaFiscal notaFiscal) {
		return notaFiscalDao.delete(notaFiscal);
	}

	public boolean save(NotaFiscal notaFiscal) {
		// Efetuado uma busca no banco para saber se o numero de nota fiscal já foi cadastrado
		final List<NotaFiscal> nf = ((NotaFiscalDAO)notaFiscalDao).findByNf(notaFiscal.getNotaFiscal());
		
		if(nf.isEmpty()){
			notaFiscal.setStatus(Status.ABERTO);
			//caso a listagem de nota fiscal esteja vazia é realizado a tentativa de inserção da nota fiscal
			if (notaFiscalDao.save(notaFiscal)){
				//caso a nota fiscal tenha sido inserida com sucesso é setado o valor de retorno para true
				retorno = true;
			}
		} 
		return retorno;
	}

	public boolean update(NotaFiscal notaFiscal) {
		// retorno de todas as parcelas pagas de uma dada Nota Fiscal
		final List<Parcela> parcela = ((ParcelaBO)ParcelaBO.getInstance()).verificaParcelaPaga(notaFiscal);

		//Se a lista estiver vazia
		if (parcela.isEmpty()){
			if (notaFiscalDao.update(notaFiscal)){
				retorno = true;
			}
		}
		return retorno;
	}

	public List<NotaFiscal> findAll() {
		return notaFiscalDao.findAll();
	}
	
	public List<NotaFiscal> findNotas30() {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, -30);
		System.out.println(calendar.getTime());
		return ((NotaFiscalDAO)notaFiscalDao).findNotas30(calendar);
	}

	public NotaFiscal findById(int id) {
		return notaFiscalDao.findById(id);
	}

	public static BO<NotaFiscal> getInstance(){
		// retorno uma instancia do Objeto NotaFiscalBO
		return new NotaFiscalBO();
	}

	public DAO<NotaFiscal> getDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(List<NotaFiscal> notaFiscal) {

		final ContasReceberDAO contasDao = new ContasReceberDAO();
		
		for (int i = 0; i < notaFiscal.size(); i++){

			// retorno de todas as parcelas pagas de uma dada Nota Fiscal
			final List<Parcela> parcelasPaga = ((ParcelaBO)ParcelaBO.getInstance()).verificaParcelaPaga(notaFiscal.get(i));
			

			//Se a lista estiver vazia
			if (parcelasPaga.isEmpty()){
				final List<ItensNotaFiscal> itens = ((ItensNotaFiscalBO)ItensNotaFiscalBO.getInstance()).
													findAllItensByNF(notaFiscal.get(i).getId());
				final List<Parcela> parcelas = ((ParcelaBO)ParcelaBO.getInstance()).findAllByNf(notaFiscal.get(i));
				final List<ContasReceber> contasReceber = contasDao.findAllContasByNf(notaFiscal.get(i).getId());
				
				for (int j = 0; j < parcelas.size(); j++){
					parcelas.get(j).setStatus(Status.CANCELADO);
				}
				for (int j = 0; j < itens.size(); j++){
					itens.get(j).setStatus(Status.CANCELADO);
				}
				notaFiscal.get(i).setStatus(Status.CANCELADO);
				if (((NotaFiscalDAO)notaFiscalDao).update(notaFiscal.get(i),parcelas,itens, contasReceber)){
						retorno = true;
				}
			}
		}
		return retorno;
	}

	public NotaFiscal findByNF(String notaFiscal) {
		return ((NotaFiscalDAO)notaFiscalDao).findByNF(notaFiscal);
	}
	
	public List<NotaFiscal> findByNF(NotaFiscal notaFiscal) {
		return ((NotaFiscalDAO)notaFiscalDao).findByNF(notaFiscal);
	}
	
	public boolean reabrirNota(NotaFiscal notaFiscal,
			List<ItensNotaFiscal> itensNotaFiscal, List<Parcela> parcelas) {
		if(parcelas.isEmpty()){
			notaFiscal.setStatus(Status.ABERTO);
		} else {
			notaFiscal.setStatus(Status.FATURADO);
		}
		for (int i = 0; i < itensNotaFiscal.size(); i++){
			itensNotaFiscal.get(i).setStatus(Status.ABERTO);
		}
		for (int i = 0; i < parcelas.size(); i++){
			parcelas.get(i).setStatus(Status.ABERTO);
		}
		return ((NotaFiscalDAO)notaFiscalDao).reabrirNotaFiscal(notaFiscal, parcelas, itensNotaFiscal);
	}

	public List<NotaFiscal> findByDay(GregorianCalendar dataInicial, GregorianCalendar dataFinal) {
		return ((NotaFiscalDAO)notaFiscalDao).findByDay(dataInicial, dataFinal);
	}

	public List<NotaFiscal> findByNumeroProcesso(String numeroProcesso) {
		return ((NotaFiscalDAO)notaFiscalDao).findByNumeroProcesso(numeroProcesso);
	}

}
