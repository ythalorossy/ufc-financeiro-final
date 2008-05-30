package br.ufc.BO;

import java.util.List;

import br.com.ItensNotaFiscal;
import br.com.NotaFiscal;
import br.com.Parcela;
import br.ufc.DAO.DAO;
import br.ufc.DAO.ItensNotaFiscalDAO;
import br.ufc.DAO.NotaFiscalDAO;
import br.ufc.DAO.ParcelaDAO;
import br.ufc.uteis.Status;

public class ItensNotaFiscalBO implements BO<ItensNotaFiscal> {
	
	private DAO<ItensNotaFiscal> dao = new ItensNotaFiscalDAO();
	private boolean retorno = false;

	public boolean delete(ItensNotaFiscal e) {
		return false;
	}

	public boolean save(ItensNotaFiscal e) {
		final NotaFiscal nf = e.getIdNotaFiscal();
		
		// setando o valor total do item
		final double valor = e.getValor();
		final int quantidade = e.getQuantidade();
		 double valorTotalItem = valor * quantidade;
		
		// calculado se existe desconto
		if(nf.getDesconto()!=0){
			double valorDesconto = (valorTotalItem * nf.getDesconto())/100;
			valorTotalItem -= valorDesconto;
		}
		
		e.setValorTotal(valorTotalItem);
		e.setStatus(Status.ABERTO);
		return dao.save(e);
	}

	public boolean update(ItensNotaFiscal e) {
		return retorno;
	}

	public List<ItensNotaFiscal> findAll() {
		return null;
	}

	public ItensNotaFiscal findById(int id) {
		return null;
	}

	public DAO<ItensNotaFiscal> getDAO() {
		return null;
	}

	public static BO<ItensNotaFiscal> getInstance() {
		return new ItensNotaFiscalBO();
	}

	public List<ItensNotaFiscal> findAllItensByNF(int idNotaFiscal) {
		
		return ((ItensNotaFiscalDAO)dao).findAllItensByNf(idNotaFiscal);
	}

	public double findSum(NotaFiscal notaFiscal) {
		return ((ItensNotaFiscalDAO)dao).findSum(notaFiscal);
	}

	public boolean delete(List<ItensNotaFiscal> list) {
		final NotaFiscal notaFiscal = list.get(0).getIdNotaFiscal();
		final ParcelaDAO parcelaDao = new ParcelaDAO();
		final List<Parcela> parcela = parcelaDao.findByIdNf(notaFiscal);
		if (!parcela.isEmpty()){
			retorno = ParcelaBO.getInstance().delete(parcela);
		} 
		retorno = true;
		if (retorno){
			retorno = ((ItensNotaFiscalDAO)dao).delete(list);
		}
		if(retorno){
			try {
				final double valor = ((ItensNotaFiscalDAO)dao).findSum(notaFiscal);
				notaFiscal.setValorNota(valor);
			} catch (Exception e) {
				notaFiscal.setValorNota(0);
			}
			retorno = new NotaFiscalDAO().update(notaFiscal);
		}
		return retorno;
	}

}
