package br.ufc.poo.clientes.repo;

import br.ufc.poo.clientes.ClienteAbstrato;

public interface IRepositorioClientes {
	public void cadastrar(ClienteAbstrato cliente);
	public boolean existeCliente(String nome);
	public ClienteAbstrato buscar(String nome);
	public void remover(String nome, int quantidade) throws CIException;
	public  int tamanho();
	public  ClienteAbstrato[] listar();
}
