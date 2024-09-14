package br.ufc.poo.produtos.excecoes;

public class QNException extends Exception{
	private static final long serialVersionUID = 6018154877526511276L;

	public QNException(String mensagem){
		//Exceção para quantidade negativa.
	  super(mensagem);
	}
} 