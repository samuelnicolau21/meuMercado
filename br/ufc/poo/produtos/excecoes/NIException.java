package br.ufc.poo.produtos.excecoes;
import java.lang.Exception;

public class NIException extends Exception{
	private static final long serialVersionUID = 1623153903155080801L;

	public NIException(String mensagem){
		super(mensagem);
	}
} 