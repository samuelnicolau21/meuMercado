package br.ufc.poo.mercado;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import java.util.Vector;

import br.ufc.poo.clientes.ClienteAbstrato;
import br.ufc.poo.clientes.repo.ClientesVector;
import br.ufc.poo.clientes.repo.IRepositorioClientes;
import br.ufc.poo.cupomFiscal.*;
import br.ufc.poo.mercado.exececoes.PNException;
import br.ufc.poo.mercado.exececoes.SIException;
import br.ufc.poo.produtos.Produto;
import br.ufc.poo.produtos.excecoes.NIException;
import br.ufc.poo.produtos.excecoes.PIException;
import br.ufc.poo.produtos.excecoes.QNException;
import br.ufc.poo.produtos.excecoes.VNException;
import br.ufc.poo.produtos.repositorio.IRepositorioProduto;
import br.ufc.poo.produtos.repositorio.ProdutosVector;

public class Mercado implements Serializable {
  private static final long serialVersionUID = 2863858886010792955L;
  private IRepositorioProduto estoque;
  private double saldo;
  private IRepositorioClientes vendas;

  public Mercado() {
    this.estoque = new ProdutosVector();
    this.vendas = new ClientesVector();
  }

  public void comprarProdutos(Produto produto) throws QNException, VNException, NIException, SIException {
    if (produto.getValor() * produto.getQuantidade() > this.saldo) {
      throw new SIException("A compra vai deixar o saldo negativo");
    } else {
      double saldoDesc = this.getSaldo();
      this.estoque.cadastrar(produto);
      saldoDesc -= produto.getValor() * produto.getQuantidade();
      this.saldo = saldoDesc;
    }
  }

  public void cadastrarCompra(ClienteAbstrato cliente) {
    vendas.cadastrar(cliente);
    this.saldo += cliente.valorCompra();
  }

  public void vender(Produto produto, String cliente, String data) throws PIException, QNException {
    this.estoque.remover(produto.getNome(), produto.getQuantidade());
  }

  public void removerProduto(String nome, int quantidade) throws PIException, QNException {
    this.estoque.remover(nome, quantidade);
  }

  public Produto buscarProduto(String nome) throws PIException {
    if (!this.estoque.existeProduto(nome)) {
      throw new PIException("Produto Inexistente");
    }
    return this.estoque.buscar(nome);
  }

  public boolean vendaSegura(Produto produto) throws PNException {
    boolean verificador = true;
    if (!this.estoque.existeProduto(produto.getNome())) {
      verificador = false;
      throw new PNException("Produto não cadastrado no mercado");
    }
    return verificador;
  }

  public double getSaldo() {
    return this.saldo;
  }

  public Produto[] listarEstoque() {
    return this.estoque.listar();
  }

  public void serializar() {
    String projectDir = System.getProperty("user.dir");
    String pathDir = "br/ufc/poo/arquivo";
    try {
      File diretorio = new File(pathDir);
      if (!diretorio.exists()) {
        diretorio.mkdirs();
      }
      String pathArquivo = pathDir + "/estoque2.bin";

      FileOutputStream fileOutputStream = new FileOutputStream(pathArquivo);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(estoque);
      objectOutputStream.writeDouble(saldo);
      objectOutputStream.writeObject(vendas);
      objectOutputStream.close();
      fileOutputStream.close();

      System.out.println("Dados do mercado foram serializados com sucesso.");
    } catch (IOException e) {
      System.out.println("Erro ao serializar os dados do mercado: " + e.getMessage());
    }
  }

  public void desserializar() {
    String projectDir = System.getProperty("user.dir");
    String pathDir = "br/ufc/poo/arquivo";
    try {
      String pathArquivo = pathDir + "/estoque2.bin";

      File arquivo = new File(pathArquivo);
      if (!arquivo.exists()) {
        System.out.println("O arquivo de serialização não existe.");
        return;
      }

      FileInputStream fileInputStream = new FileInputStream(pathArquivo);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

      this.estoque = (ProdutosVector) objectInputStream.readObject();
      this.saldo = objectInputStream.readDouble();
      this.vendas = (ClientesVector) objectInputStream.readObject();
      objectInputStream.close();
      fileInputStream.close();

      System.out.println("Dados do mercado foram desserializados com sucesso.");
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Erro ao desserializar os dados do mercado: " + e.getMessage());
    }
  }

  public void sincronizar() {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture<?> tarefa = executorService.scheduleAtFixedRate(() -> {
      desserializar();
      serializar();
    }, 0, 10, TimeUnit.SECONDS);
  }

  public void setSaldo(double saldo) throws SIException {
    if (saldo <= 0) {
      throw new SIException("Saldo Negativo");
    } else {
      this.saldo = saldo;
    }
  }

  public double precoProduto(String nomeProduto) {
    double valor = 0.0;
    if (this.estoque.existeProduto(nomeProduto)) {
      valor = this.estoque.buscar(nomeProduto).getValor();
    }
    return valor;
  }

  public ClienteAbstrato[] listarClientes() {
    return this.vendas.listar();
  }

  public int tamanhoVendas() {
    return vendas.tamanho();
  }

  public void mudarValorProduto(String nome, double novoValor) throws VNException, PIException {
    Produto produtoAlterar = this.estoque.buscar(nome);
    produtoAlterar.setValor(novoValor);
  }
}
