package br.ufc.uteis;

public class Status {
	// Status nota fiscal, parcela, contas receber, caixa, itens da nota fiscal, pedido de desespesa
	public static final int ABERTO = 0;
	public static final int PAGO = 1;
	public static final int CANCELADO = 2;
	public static final int BATIDO = 3;
	public static final int AGUARDANDO = 4;
	public static final int FATURADO = 5;
	public static final int CONFIRMADO = 6;
	public static final int COTADO = 7;
	

	// Tipos de nota fiscal
	public static final int NOTA_CONTABILIZADA = 50;
	public static final int NOTA_NAO_CONTABILIZADA = 51;

	// Tipos de caixa entrada saida
	public static final int ENTRADA = 100;
	public static final int SAIDA = 101;

	// Tipos de Contas a receber
	public static final int JUROS = 150;
	public static final int DESCONTO = 151;
	
	
	// Projeto
	public static final int SIM = 200;
	public static final int NAO = 201;

	public static String retornaTipo(int status) {
		String retorno = "";
		switch (status) {
		case 0:
			retorno = "Aberto";
			break;
		case 1:
			retorno = "Pago";
			break;
		case 2:
			retorno = "Cancelado";
			break;
		case 3:
			retorno = "Batido";
			break;
		case 4:
			retorno = "Aguardando";
			break;
		case 5:
			retorno = "Faturado";
			break;
		case 6:
			retorno = "Confirmado";
			break;
		case 7:
			retorno = "Cotado";
			break;
		case 50:
			retorno = "Contabilizada";
			break;
		case 51:
			retorno = "Não Contabilizada";
			break;
		case 100:
			retorno = "Entrada";
			break;
		case 101:
			retorno = "Saída";
			break;

		case 150:
			retorno = "Juros";
			break;
		case 151:
			retorno = "Desconto";
			break;
		case 200:
			retorno = "Sim";
			break;
		case 201:
			retorno = "Não";
			break;
		}

		return retorno;
	}

	public static int retornaTipo(String status) {
		int retorno = 0;
		if (status.equals("Aberto")) {
			retorno = ABERTO;
		}
		if (status.equals("Pago")) {
			retorno = PAGO;
		}
		if (status.equals("Cancelado")) {
			retorno = CANCELADO;
		}
		if (status.equals("Batido")) {
			retorno = BATIDO;
		}
		if (status.equals("Aguardando")) {
			retorno = AGUARDANDO;
		}
		if (status.equals("Faturado")) {
			retorno = FATURADO;
		}
		if (status.equals("Confirmado")) {
			retorno = CONFIRMADO;
		}
		if (status.equals("Cotado")) {
			retorno = COTADO;
		}
		if (status.equals("Contabilizada")) {
			retorno = NOTA_CONTABILIZADA;
		}
		if (status.equals("Não Contabilizada")) {
			retorno = NOTA_NAO_CONTABILIZADA;
		}
		if (status.equals("Entrada")) {
			retorno = ENTRADA;
		}
		if (status.equals("Saida")) {
			retorno = SAIDA;
		}
		if (status.equals("Juros")) {
			retorno = JUROS;
		}
		if (status.equals("Desconto")) {
			retorno = DESCONTO;
		}
		if (status.equals("Sim")) {
			retorno = SIM;
		}
		if (status.equals("Não")) {
			retorno = NAO;
		}
		
		return retorno;
	}

}
