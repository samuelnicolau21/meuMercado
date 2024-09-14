package br.ufc.poo.cupomFiscal;

import java.util.Vector;
import java.io.Serializable;
import br.ufc.poo.produtos.*;
import br.ufc.poo.produtos.excecoes.PIException;

public class CupomFiscal implements Serializable {
	private static final long serialVersionUID = 1L;
    private Vector<Produto> produtos;

    public CupomFiscal() {
        this.produtos = new Vector<>();
    }

  public Produto[] getCompras() {
    Produto[] arrayDeProdutos = produtos.toArray(new Produto[produtos.size()]);
    return arrayDeProdutos;
  }

  public void adicionarCompras(Produto produto) throws PIException {
	  if(!(produto == null)) {
		  this.produtos.addElement(produto);
	  } else {
		  throw new PIException("Produto Inexistente");
	  }
  }
  
  public double valorTotal(){
	  double valorTotal = 0.0;
	  for(Produto produto:produtos) {
		  valorTotal += produto.getQuantidade()*produto.getValor();
	  }
	  return valorTotal;
  }
  
  

}