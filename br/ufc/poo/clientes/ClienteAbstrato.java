package br.ufc.poo.clientes;

import java.io.Serializable;

import br.ufc.poo.clientes.excecoes.DIException;
import br.ufc.poo.cupomFiscal.*;
import br.ufc.poo.produtos.*;
import br.ufc.poo.produtos.excecoes.*;

public abstract class ClienteAbstrato implements Serializable {
	private static final long serialVersionUID = -4117052509108890533L;
	protected String nome;
	protected CupomFiscal compras;
	
	public ClienteAbstrato(String nome) {
		this.nome = nome;
		compras = new CupomFiscal();
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public CupomFiscal setCupom() {
		return compras;
	}
	
	public CupomFiscal getCompras() {
		return this.compras;
	}
	
	public void adicionarCompras(Produto produto) throws PIException {
		compras.adicionarCompras(produto);
	}
	
	public abstract void verificarDocumento(String documento) throws DIException;
	
	public double valorCompra() {
		return this.compras.valorTotal();
	}
}
