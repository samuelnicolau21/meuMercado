package br.ufc.poo.grafica.panel;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;


import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.ufc.poo.clientes.Cliente;
import br.ufc.poo.clientes.ClienteAbstrato;
import br.ufc.poo.clientes.Empresa;
import br.ufc.poo.clientes.excecoes.DIException;
import br.ufc.poo.clientes.repo.ClientesVector;
import br.ufc.poo.mercado.Mercado;
import br.ufc.poo.mercado.exececoes.PNException;
import br.ufc.poo.mercado.exececoes.SIException;
import br.ufc.poo.produtos.Produto;
import br.ufc.poo.produtos.excecoes.NIException;
import br.ufc.poo.produtos.excecoes.PIException;
import br.ufc.poo.produtos.excecoes.QNException;
import br.ufc.poo.produtos.excecoes.VNException;
import br.ufc.poo.produtos.repositorio.*;

public class Vendas extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textProduto;
	private JTextField textQuantidade;
	private JTextField textTotal;
	private JTextField textCliente;
	private JTextField textCPF;
	private JTextArea cupomFiscal;
	private Mercado mercado;
	private ProdutosVector listaVendas;
	private ClienteAbstrato cliente;
	private JRadioButton radioCPF;
    private JRadioButton radioCNPJ;
    private ButtonGroup grupoRadio;
    private boolean CPFselecionador;

	
	public Vendas() {
		this.mercado = new Mercado();
		this.listaVendas = new ProdutosVector();
		setForeground(new Color(255, 255, 255));
		setBackground(new Color(143, 188, 143));
		setLayout(null);
		
		/*Definindo as caracteristicas dos JTexts*/
		textProduto = new JTextField();
		textProduto.setBounds(48, 115, 138, 19);
		add(textProduto);
		textProduto.setColumns(10);
		
		textQuantidade = new JTextField();
		textQuantidade.setBounds(48, 177, 77, 19);
		add(textQuantidade);
		textQuantidade.setColumns(10);
		
		textTotal = new JTextField();
		textTotal.setBounds(379, 159, 114, 37);
		textTotal.setText("0.0");
		add(textTotal);
		textTotal.setColumns(10);
		
		textCliente = new JTextField();
		textCliente.setBounds(214, 115, 114, 19);
		add(textCliente);
		textCliente.setColumns(10);
		
		textCPF = new JTextField();
		textCPF.setBounds(214, 177, 114, 19);
		add(textCPF);
		textCPF.setColumns(10);
		
		/*Declarando os JLabels*/
		JLabel lblProduto = new JLabel("Produto:");
		lblProduto.setBounds(48, 85, 77, 24);
		lblProduto.setFont(new Font("Dialog", Font.BOLD, 20));
		add(lblProduto);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(48, 146, 109, 24);
		lblQuantidade.setFont(new Font("Dialog", Font.BOLD, 20));
		add(lblQuantidade);
		
		JLabel lblTotalDaVenda = new JLabel("Total da venda:");
		lblTotalDaVenda.setBounds(366, 110, 138, 24);
		lblTotalDaVenda.setFont(new Font("Dialog", Font.BOLD, 20));
		add(lblTotalDaVenda);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(214, 85, 69, 24);
		lblCliente.setFont(new Font("Dialog", Font.BOLD, 20));
		add(lblCliente);
		
		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		lblCpfcnpj.setBounds(214, 141, 101, 24);
		lblCpfcnpj.setFont(new Font("Dialog", Font.BOLD, 20));
		add(lblCpfcnpj);
		
		JLabel lblVendas = new JLabel("Vendas");
		lblVendas.setBounds(310, 34, 109, 27);
		lblVendas.setFont(new Font("Dialog", Font.BOLD, 30));
		add(lblVendas);
		
		/*Declarando os JButtons do JPanel*/
		JButton btnVender = new JButton("Vender");
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vender();
			}
		});
		btnVender.setBounds(606, 66, 98, 67);
		add(btnVender);
		
		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setBounds(606, 153, 98, 67);
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalizar();
			}
		});
		add(btnFinalizar);

		/*Declarando um espaço para mostrar o Cupom Fiscal*/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 289, 745, 247);
		add(scrollPane);
		
		this.cupomFiscal = new JTextArea();
		cupomFiscal.setFont(new Font("Dialog", Font.BOLD, 26));
		this.cupomFiscal.setForeground(new Color(0, 0, 0));
		scrollPane.setViewportView(cupomFiscal);
		
		JLabel lblNewLabel = new JLabel("Cupom Fiscal");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		lblNewLabel.setBounds(310, 239, 144, 38);
		add(lblNewLabel);
		
		this.radioCPF = new JRadioButton("CPF");
	    this.radioCPF.setSelected(true);
	    this.radioCPF.setBounds(214, 210, 59, 23);
	    add(this.radioCPF);

	    this.radioCNPJ = new JRadioButton("CNPJ");
	    this.radioCNPJ.setBounds(279, 210, 69, 23);
	    add(this.radioCNPJ);
	     
	    this.grupoRadio = new ButtonGroup();
	    this.grupoRadio.add(this.radioCPF);
	    this.grupoRadio.add(this.radioCNPJ);
	}
	
	private void vender() {
		try {
			this.mercado.desserializar();
			//Pegando as informações para realizar a lógica da venda
			String nomeProduto = this.textProduto.getText();
			String quantidade = this.textQuantidade.getText();
			String documento = this.textCPF.getText();
			String nomeCliente = this.textCliente.getText();
			int quantidadeInt = Integer.parseInt(quantidade);
			double valorProduto = this.mercado.precoProduto(nomeProduto);
			Produto produto = new Produto(nomeProduto, quantidadeInt,(valorProduto));	
	    	 if (radioCPF.isSelected()) {
	    		 CPFselecionador = true;
	         } else if (radioCNPJ.isSelected()) {
	        	 CPFselecionador = false;
	         }
	    	 if(this.mercado.vendaSegura(produto)) {
	    		 if(this.cupomFiscal.getText().equals("")){
	    			 if (this.CPFselecionador) {
	    				 this.cliente = new Cliente(nomeCliente,documento);
	    				 this.cliente.verificarDocumento(documento);
	    			 } else {
	    				 this.cliente = new Empresa(nomeCliente,documento);
	    			 }
	    			 this.cupomFiscal.setText("********CUPOM FISCAL********\n"+ 
	    							"Documento:"+documento+"\n"+
	    							"Nome:"+nomeCliente+"\n"
	    							+"Nome do Produto---"+"Quantidade"+"---"+"Valor(R$)\n"
	    							+nomeProduto+"---"+quantidade+"UN"+"---"+valorProduto+"R$\n");
	    		 } else {
	    			 this.cupomFiscal.setText(this.cupomFiscal.getText()
									+nomeProduto+"---"+quantidade+"UN"+"---"+valorProduto+"R$\n");
	    		 }
	    		 this.listaVendas.cadastrar(produto);
	    		 this.cliente.adicionarCompras(produto);
	    		 //Ajeitando o valor da compra total no TextField
	    		 String valorTotalString = this.textTotal.getText();
	    		 double valorTotal = Double.parseDouble(valorTotalString);
	    		 valorTotal = valorTotal+(valorProduto * quantidadeInt);
	    		 valorTotalString = Double.toString(valorTotal);
	    		 this.textTotal.setText(valorTotalString);
	    	 }
	    	 this.textProduto.setText("");
    		 this.textQuantidade.setText("");
    		 this.mercado.serializar();
		} catch (DIException die) {
			JOptionPane.showMessageDialog(null, die.getMessage());
		} catch (QNException qne) {
			JOptionPane.showMessageDialog(null, qne.getMessage());
		} catch (VNException vne) {
			JOptionPane.showMessageDialog(null, vne.getMessage());
		} catch (NIException nie) {
			JOptionPane.showMessageDialog(null, nie.getMessage());
		} catch (PIException pie) {
			JOptionPane.showMessageDialog(null, pie.getMessage());
		} catch (PNException pne) {
			JOptionPane.showMessageDialog(null, pne.getMessage());
		}
	}
	
	public void finalizar() {
		try {
			this.mercado.desserializar();
			Produto[] listaVendasCopia = this.listaVendas.listar();
			int Confirm = JOptionPane.showConfirmDialog(null,"Encerrar a comprar?","sim ou nao", JOptionPane.YES_NO_OPTION);
			double valorVenda=0.0;
			for(Produto produto:listaVendasCopia) {
				valorVenda = valorVenda + (produto.getValor()*produto.getQuantidade());
			}
			if(Confirm == JOptionPane.YES_OPTION) {
				this.cupomFiscal.setText(this.cupomFiscal.getText()+"Saldo total:"+valorVenda);
				LocalDate dataAtual = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String data = dataAtual.format(formatter);
				for(Produto produto:listaVendasCopia) {
					this.mercado.vender(produto, this.textCliente.getText(), data);
				}
				this.mercado.cadastrarCompra(cliente);
				this.textCPF.setText("");
				this.textCliente.setText("");
				this.cupomFiscal.setText("");
				double saldoAcres = this.mercado.getSaldo()+valorVenda;
				this.mercado.setSaldo(saldoAcres);
				this.textTotal.setText("");
				this.mercado.serializar();
				JOptionPane.showMessageDialog(null, "Venda feita com sucesso");
			} else {
				JOptionPane.showMessageDialog(null, "Continuando Compra");
			}
		} catch (PIException pie) {
			JOptionPane.showMessageDialog(null, pie.getMessage());
		} catch (QNException qne) {
			JOptionPane.showMessageDialog(null, qne.getMessage());
		} catch (SIException sie) {
			JOptionPane.showMessageDialog(null, sie.getMessage());
		}
	}
	
}
