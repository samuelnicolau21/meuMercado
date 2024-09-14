package br.ufc.poo.produtos.excecoes;
import java.lang.Exception;

public class PIException extends Exception{
	private static final long serialVersionUID = 3618486638512954931L;

	public PIException(String mensagem){
		super(mensagem);
	}
}