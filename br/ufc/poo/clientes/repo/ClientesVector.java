package br.ufc.poo.clientes.repo;

import java.util.Vector;
import br.ufc.poo.clientes.*;
import java.io.*;

public class ClientesVector implements IRepositorioClientes,Serializable{
		private static final long serialVersionUID = -4335854164635268754L;
		Vector<ClienteAbstrato> clientes;

		public ClientesVector(){
			this.clientes = new Vector<>();
		}
		public void cadastrar(ClienteAbstrato cliente) {
			clientes.addElement(cliente);
		}
	 
		public boolean existeCliente(String nome){
			for (ClienteAbstrato cliente : clientes) {
				if(cliente.getNome().equals(nome)){
	              return true;
	            }
	        }
			return false;  
		}
	  
		public ClienteAbstrato buscar(String nome){
			for (ClienteAbstrato cliente : clientes) {
				if(cliente.getNome().equals(nome)){
					return cliente;
	            }
	        }
			return null;
		}
	  
		public void remover(String nome, int quantidade) throws CIException {
		    if (!this.existeCliente(nome)) {
		    	throw new CIException("Cliente Inexistente");
		    } else {
		    	ClienteAbstrato clienteRemover = this.buscar(nome);
		        clientes.remove(clienteRemover);
		    }
		}

	 
		public  int tamanho(){
			return this.clientes.size();
		}
	  
		public  ClienteAbstrato[] listar(){
			ClienteAbstrato[] cliente = new ClienteAbstrato[tamanho()];
	        	this.clientes.toArray(cliente);
	        	return cliente;
	    }
	}
