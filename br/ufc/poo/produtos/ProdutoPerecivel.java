package br.ufc.poo.produtos;

import java.io.Serializable;

public class ProdutoPerecivel extends Produto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String validade; 
  
	public ProdutoPerecivel(String nome, int quantidade, double valor, String validade){
		super(nome,quantidade,valor);  
		this.validade = validade;
	}
  
	public String getValidade(){
		return this.validade;
	}
}