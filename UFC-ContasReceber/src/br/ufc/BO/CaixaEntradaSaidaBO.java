package br.ufc.BO;

import java.util.List;

import br.com.Caixa;
import br.com.CaixaEntradaSaida;
import br.ufc.DAO.CaixaDAO;
import br.ufc.DAO.CaixaEntradaSaidaDAO;
import br.ufc.DAO.DAO;
import br.ufc.uteis.Status;

public class CaixaEntradaSaidaBO implements BO<CaixaEntradaSaida> {

	private DAO<CaixaEntradaSaida> dao = new CaixaEntradaSaidaDAO();
	private CaixaDAO caixaDAO = new CaixaDAO();
	private Caixa caixa = new Caixa();
	private boolean result = false;

	public boolean delete(CaixaEntradaSaida e) {
		return dao.delete(e);
	}

	/**
	 * Salva um caixaEntradaSaida e abre um Caixa
	 */
	public boolean save(CaixaEntradaSaida e) {

		/*
		 * Modifica o valor(R$) para negativo em caso de SAIDA
		 */
		if (e.getOperacao() == Status.SAIDA)
			e.setValor((-1) * e.getValor());
		

		/*
		 * Tentar salvar, ao salvar retorna o mesmo objeto (e) com o id setado,
		 * para ser usado na insercao de um Caixa
		 */
		CaixaEntradaSaida ces = ((CaixaEntradaSaidaDAO) dao).saveReturn(e);

		/*
		 * Inserindo um caixa
		 */
		if (ces != null) {
			/*
			 * Usa as configuracoes do CaixaEntradaSaida para abrir um caixa
			 */
			caixa.setDataPagamento(ces.getDataTransacao());
			caixa.setDescricaoServico(ces.getDescricao());
			caixa.setDoc("CES-" + ces.getId());
			caixa.setIdCaixaEntradaSaida(ces);
			caixa.setStatus(Status.ABERTO);
			caixa.setValor(ces.getValor());

			/*
			 * Salva um Caixa do tipo CaixaEntradaSaida
			 */
			if (caixaDAO.save(caixa)) {
				result = true;
			}
		}

		return result;
	}

	public boolean update(CaixaEntradaSaida e) {
		return dao.update(e);
	}

	public List<CaixaEntradaSaida> findAll() {
		return dao.findAll();
	}

	public CaixaEntradaSaida findById(int id) {
		return dao.findById(id);
	}

	public CaixaEntradaSaida findByObject(CaixaEntradaSaida e) {
		return dao.findByObject(e);
	}

	public DAO<CaixaEntradaSaida> getDAO() {
		return null;
	}

	public static BO<CaixaEntradaSaida> getInstance() {
		return new CaixaEntradaSaidaBO();
	}

	@Override
	public boolean delete(List<CaixaEntradaSaida> list) {
		// TODO Auto-generated method stub
		return false;
	}

}
