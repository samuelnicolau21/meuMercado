package br.ufc.poo.mercado.exececoes;
import java.lang.Exception;

//Excecao para Saldo do mercado Negativo
public class PNException extends Exception{
	public PNException(String mensagem){
		super(mensagem);
	}
} 