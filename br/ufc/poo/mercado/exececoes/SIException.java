package br.ufc.poo.mercado.exececoes;
import java.lang.Exception;

//Excecao para Saldo do mercado Negativo
public class SIException extends Exception{
	public SIException(String mensagem){
		super(mensagem);
	}
} 