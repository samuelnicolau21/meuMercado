package br.ufc.poo.produtos.repositorio;

import br.ufc.poo.produtos.*;
import br.ufc.poo.produtos.excecoes.*;

public interface IRepositorioProduto {
	public void cadastrar(Produto p) throws QNException,VNException,NIException;
	public void remover(String nome, int quantidade) throws PIException, QNException;
	public Produto buscar(String nome);
	public boolean existeProduto(String nome);
	public int tamanho();
	public int quantidadeTotal();
	public Produto[] listar();
}