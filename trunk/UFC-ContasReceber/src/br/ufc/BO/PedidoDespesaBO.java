package br.ufc.BO;

import java.util.GregorianCalendar;
import java.util.List;

import br.com.ContasPagar;
import br.com.ItensPedidoDespesa;
import br.com.PedidoDespesa;
import br.ufc.DAO.DAO;
import br.ufc.DAO.PedidoDespesaDAO;
import br.ufc.uteis.Status;

public class PedidoDespesaBO implements BO<PedidoDespesa> {

	// objeto que realiza a interface DAO de comunicação com o banco de dados
	private DAO<PedidoDespesa> pedidoDespesaDao = new PedidoDespesaDAO();
	private boolean retorno = false;

	public boolean delete(PedidoDespesa e) {
		if(e.getStatus()!=Status.PAGO){
			retorno = pedidoDespesaDao.delete(e); 
		}
		return retorno;
	}

	public boolean save(PedidoDespesa e) {
	final List<PedidoDespesa> pd = ((PedidoDespesaDAO)pedidoDespesaDao).findByNumeroPD(e.getNumeroPD());
		
		if(pd.isEmpty()){
			e.setStatus(Status.ABERTO);
			if (pedidoDespesaDao.save(e)){
				retorno = true;
			}
		} 
		return retorno;
	}

	public boolean update(PedidoDespesa pedidoDespesa) {

		if (pedidoDespesa.getStatus()!= Status.CONFIRMADO){
			if (pedidoDespesaDao.update(pedidoDespesa)){
				retorno = true;
			}
		}
		return retorno;
	}

	public List<PedidoDespesa> findAll() {
		return pedidoDespesaDao.findAll();
	}

	public PedidoDespesa findById(int id) {
		return pedidoDespesaDao.findById(id);
	}

	public static BO<PedidoDespesa> getInstance() {
		return new PedidoDespesaBO();
	}

	public DAO<PedidoDespesa> getDAO() {
		return null;
	}

	public boolean delete(List<PedidoDespesa> pedidoDespesa) {
		
		for (int i = 0; i < pedidoDespesa.size(); i++){
			if(pedidoDespesa.get(i).getStatus()!= Status.PAGO){
			
				final List<ContasPagar> list = ((ContasPagarBO)ContasPagarBO.getInstance()).findByIdPD(pedidoDespesa.get(i).getId());
				//Se a lista estiver vazia
				for (ContasPagar contasPagar : list) {
					if (contasPagar.getStatus() != Status.PAGO){
						retorno = true;
					}
				}
				if(retorno){
					ContasPagarBO.getInstance().delete(list);
				}


				final List<ItensPedidoDespesa> itens = ((ItensPedidoDespesaBO)ItensPedidoDespesaBO.getInstance()).findAllItensByNumeroPD(pedidoDespesa.get(i));
				for (int j = 0; j < itens.size(); j++){
					itens.get(j).setStatus(Status.CANCELADO);
				}
				pedidoDespesa.get(i).setStatus(Status.CANCELADO);
				if (((PedidoDespesaDAO)pedidoDespesaDao).update(pedidoDespesa.get(i),itens)){
					retorno = true;
				} else {
					retorno = false;
				}
			}
		}
		return retorno;
	}

	public List<PedidoDespesa> findByDay(GregorianCalendar dataInicial,	GregorianCalendar dataFinal) {
		return ((PedidoDespesaDAO)pedidoDespesaDao).findByDay(dataInicial, dataFinal);
	}

	public List<PedidoDespesa> findPedido30() {
		final GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, -30);
		return ((PedidoDespesaDAO)pedidoDespesaDao).findPedido30(calendar);
	}
	
	public boolean update(PedidoDespesa pedidoDespesa, List<ItensPedidoDespesa> itens) {
		return ((PedidoDespesaDAO)pedidoDespesaDao).update(pedidoDespesa,itens);
	}
	
	public boolean aprovaPD(PedidoDespesa pedidoDespesa, List<ItensPedidoDespesa> itens){
		boolean retorno = false;
		if (update(pedidoDespesa, itens)){
			final ContasPagar contasPagar = retornaContasPagar(pedidoDespesa);
			retorno = ContasPagarBO.getInstance().save(contasPagar);
		}
		
		return retorno;
		 
	}
	private ContasPagar retornaContasPagar(PedidoDespesa pedidoDespesa){
		final ContasPagar contasPagar = new ContasPagar();
		contasPagar.setDataPrevista(pedidoDespesa.getDataPD());
		contasPagar.setValorPrevista(pedidoDespesa.getValorCotado());
		contasPagar.setIdDivisao(pedidoDespesa.getIdDivisao());
		contasPagar.setObservacao("PD "+pedidoDespesa.getNumeroPD());
		contasPagar.setStatus(Status.ABERTO);
		contasPagar.setIdPedidoDespesa(pedidoDespesa.getId());
		return contasPagar;
	}

	public List<PedidoDespesa> findAllAguardando() {
		return ((PedidoDespesaDAO)pedidoDespesaDao).findAllAguardando();
	}

}
