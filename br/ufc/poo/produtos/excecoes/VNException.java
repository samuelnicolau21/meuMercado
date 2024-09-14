package br.ufc.poo.produtos.excecoes;
import java.lang.Exception;

public class VNException extends Exception{
	private static final long serialVersionUID = -6713425996873886471L;

	public VNException(String mensagem){
		super(mensagem);
	}
} 