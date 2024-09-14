package br.ufc.poo.produtos;

import java.io.Serializable;

import br.ufc.poo.produtos.excecoes.QNException;
import br.ufc.poo.produtos.excecoes.VNException;

public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Double valor;
	private int quantidade;

	public Produto(String nome, int quantidade, double valor){
			this.nome = nome;
			this.quantidade = quantidade;
			this.valor = valor;
	}

	public String getNome(){
		return this.nome;
	}

	public Double getValor(){
		return this.valor;
	}

	public int getQuantidade(){
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) throws QNException{
		if(quantidade < 0) {
			throw new QNException("Quantidade negativa");
		}else {
			this.quantidade = quantidade;
		}
	}
	
	public void setValor(double valor) throws VNException {
		if(valor <= 0) {
			throw new VNException("Valor Invalido");
		} else {
			this.valor = valor;
		}
	}
}
