package br.ufc.poo.clientes;

import java.io.Serializable;

import br.ufc.poo.clientes.excecoes.DIException;
import br.ufc.poo.cupomFiscal.CupomFiscal;

public class Cliente extends ClienteAbstrato implements Serializable{

	private static final long serialVersionUID = 5616587296203472502L;
	private String cpf;
	
	public Cliente(String nome, String cpf){
		super(nome);
		this.cpf = cpf;
	}
	public String getDocumento() {
		return this.cpf;
	}

	@Override
	public void verificarDocumento(String cpf) throws DIException {
		verificarCPF(cpf);
	}
	
	public void verificarCPF(String cpf) throws DIException {
	    // Remove caracteres não numéricos
	    String documento = cpf.replaceAll("[^0-9]", "");

	    // Verifica se o CPF possui 11 dígitos
	    if (documento.length() != 11) {
	        throw new DIException("CPF inválido");
	    }

	    // Transforma a String em um vetor de inteiros
	    int[] cpfNumerico = new int[11];
	    for (int i = 0; i < 11; i++) {
	        cpfNumerico[i] = Character.getNumericValue(documento.charAt(i));
	    }

	    // Calcula o primeiro dígito verificador
	    int soma = 0;
	    for (int i = 0; i < 9; i++) {
	        soma += cpfNumerico[i] * (10 - i);
	    }
	    int primeiroDigito = 11 - (soma % 11);
	    if (primeiroDigito == 10 || primeiroDigito == 11) {
	        primeiroDigito = 0;
	    }

	    // Verifica o primeiro dígito verificador
	    if (primeiroDigito != cpfNumerico[9]) {
	        throw new DIException("CPF inválido");
	    }

	    // Calcula o segundo dígito verificador
	    soma = 0;
	    for (int i = 0; i < 10; i++) {
	        soma += cpfNumerico[i] * (11 - i);
	    }
	    int segundoDigito = 11 - (soma % 11);
	    if (segundoDigito == 10 || segundoDigito == 11) {
	        segundoDigito = 0;
	    }

	    // Verifica o segundo dígito verificador
	    if (segundoDigito != cpfNumerico[10]) {
	        throw new DIException("CPF inválido");
	    }
	}

}
