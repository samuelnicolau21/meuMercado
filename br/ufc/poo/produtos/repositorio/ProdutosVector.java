package br.ufc.poo.produtos.repositorio;
import java.util.Vector;
import br.ufc.poo.produtos.*;
import br.ufc.poo.produtos.excecoes.*;
import java.io.*;

public class ProdutosVector implements IRepositorioProduto, Serializable{
	private static final long serialVersionUID = -4335854164635268754L;
	Vector<Produto> listaDeProdutos;

	public ProdutosVector(){
		this.listaDeProdutos = new Vector<>();
	}
	public void cadastrar(Produto produto) throws QNException,VNException,NIException{
		if(produto.getNome().isEmpty()) {
			throw new NIException("Nome Invalido");
		} else if(produto.getQuantidade() < 0){
			throw new QNException("Quantiade Negativa");
		} else if(produto.getValor() < 0) {
			throw new VNException("Valor Negativo");
		} else {
			if(this.existeProduto(produto.getNome())) {
				Produto jaNaLista = this.buscar(produto.getNome());
				jaNaLista.setQuantidade(jaNaLista.getQuantidade() + produto.getQuantidade());
			}else{
				this.listaDeProdutos.add(produto);
			}
		}
	}
 
	public boolean existeProduto(String nome){
		return nome!= null && this.buscar(nome) != null;
	}
  
	public Produto buscar(String nome){
		for (Produto produto : listaDeProdutos) {
			if(produto.getNome().equals(nome)){
				return produto;
            }
        }
		return null;
	}
	
	public Produto recuperar(String nome) throws PIException {
	    if (!this.existeProduto(nome)) {
	      throw new PIException(nome);
	    }
	    return this.buscar(nome);
	  }
  
	public void remover(String nome, int quantidade) throws PIException,QNException {
	    if (!this.existeProduto(nome)) {
	    	throw new PIException("Produto Inexistente");
	    } else {
	    	Produto produtoRemover = this.buscar(nome);
	        int quantidadeAtual = produtoRemover.getQuantidade();
	        if (quantidade <= quantidadeAtual) {
	            if (quantidade == quantidadeAtual) {
	                listaDeProdutos.remove(produtoRemover);
	            } else {
	                produtoRemover.setQuantidade(quantidadeAtual - quantidade);
	            }
	        }
	    }
	}
 
	public  int tamanho(){
		return this.listaDeProdutos.size();
	}

	public int quantidadeTotal(){
		int soma=0;
		for(Produto produto : this.listaDeProdutos){
			soma += produto.getQuantidade();
		}
		return soma;
	}
  
	public  Produto[] listar(){
		Produto[] produtos = new Produto[listaDeProdutos.size()];
        	this.listaDeProdutos.toArray(produtos);
        	return produtos;
    }
	
}