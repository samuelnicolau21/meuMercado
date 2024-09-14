package br.ufc.poo.clientes;

import java.io.Serializable;

import br.ufc.poo.clientes.excecoes.DIException;
import br.ufc.poo.cupomFiscal.CupomFiscal;

public class Empresa extends ClienteAbstrato implements Serializable{

	private static final long serialVersionUID = 5616587296203472502L;
	private String cnpj;
	
	public Empresa(String nome, String cnpj) {
		super(nome);
		this.cnpj = cnpj;
	}
	
	public String getDocumento() {
		return this.cnpj;
	}

	@Override
	public void verificarDocumento(String cnpj) throws DIException {
		verificarCNPJ(cnpj);
		
	}
	
	public void verificarCNPJ(String cnpj)throws DIException{
        // Remove caracteres não numéricos
        String documento = cnpj.replaceAll("[^0-9]", "");

        // Verifica se o CNPJ possui 14 dígitos
        if (documento.length() != 14) {
            throw new DIException("CNPJ inválido");
        }

        // Transforma a String em um vetor de inteiros
        int[] cnpjNumerico = new int[14];
        for (int i = 0; i < 14; i++) {
            cnpjNumerico[i] = Character.getNumericValue(documento.charAt(i));
      
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        int peso = 5;
        for (int i = 0; i < 12; i++) {
            soma += cnpjNumerico[i] * peso;
            peso = (peso == 2) ? 9 : peso - 1;
        }
        int primeiroDigito = (11 - soma % 11) % 10;

        // Calcula o segundo dígito verificador
        soma = 0;
        peso = 6;
        for (int i = 0; i < 13; i++) {
            soma += cnpjNumerico[i] * peso;
            peso = (peso == 2) ? 9 : peso - 1;
        }
        int segundoDigito = (11 - soma % 11) % 10;

        // Verifica se os dígitos verificadores são iguais aos fornecidos no CNPJ
        if (primeiroDigito == cnpjNumerico[12] && segundoDigito == cnpjNumerico[13]) {
            return;
        } else {
            throw new DIException("CNPJ inválido");
        }
    }

}