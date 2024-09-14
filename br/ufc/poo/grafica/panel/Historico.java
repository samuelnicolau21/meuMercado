package br.ufc.poo.grafica.panel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import br.ufc.poo.clientes.Cliente;
import br.ufc.poo.clientes.ClienteAbstrato;
import br.ufc.poo.clientes.Empresa;
import br.ufc.poo.mercado.Mercado;
import br.ufc.poo.produtos.Produto;
import br.ufc.poo.produtos.ProdutoPerecivel;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Historico extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable tabela;
    private Mercado mercado;
	private JTextArea textArea;
    
    public Historico() {

        this.mercado = new Mercado();

        setBackground(new Color(143, 188, 143));
        setForeground(new Color(255, 255, 255));
        setLayout(null);

        /* Declaração dos JLabels */
        JLabel lblHistorico = new JLabel("HISTORICO");
        lblHistorico.setBounds(239, 24, 104, 24);
        lblHistorico.setFont(new Font("Dialog", Font.BOLD, 20));
        add(lblHistorico);

        JLabel lblCupomFiscal = new JLabel("CUPOM FISCAL");
        lblCupomFiscal.setBounds(288, 302, 142, 24);
        lblCupomFiscal.setFont(new Font("Dialog", Font.BOLD, 20));
        add(lblCupomFiscal);

        /* Declaração do ScrollPane da tabela de historico */
        JScrollPane scrollHistorico = new JScrollPane();
        scrollHistorico.setBounds(10, 54, 573, 219);
        add(scrollHistorico);

        JButton btnMostrar = new JButton("Mostrar");
        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizar();
            }
        });
        btnMostrar.setBounds(605, 69, 114, 48);
        add(btnMostrar);

        // Declarando tabela de vendas do mercado
        this.tabela = new JTable();
        this.tabela.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Nome", "CPF/CNPJ", "Cupom Fiscal"
            }
        ) {
            private static final long serialVersionUID = 1L;
            Class[] columnTypes = new Class[] {
                String.class, String.class, Boolean.class
            };

            public Class<?> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            public boolean isCellEditable(int row, int column) {
                    return column == 2; // Apenas a coluna de seleção é editável
            }
        });
        


        scrollHistorico.setViewportView(this.tabela);

        /* Declaração do ScrollPane do CupomFiscal */
        JScrollPane scrollCupom = new JScrollPane();
        scrollCupom.setBounds(10, 343, 704, 193);
        add(scrollCupom);

        this.textArea = new JTextArea();
        textArea.setFont(new Font("Dialog", Font.BOLD, 26));
        scrollCupom.setViewportView(this.textArea);
        
        JButton btnCupom = new JButton("Cupom");
        btnCupom.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		cupom();
        	}
        });
        btnCupom.setBounds(605, 140, 114, 48);
        add(btnCupom);
    }

    private void atualizar() {
        this.mercado.desserializar();
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0); // Limpa a tabela antes de preencher novamente
        int linhas = modelo.getRowCount();
        ClienteAbstrato[] clientes = this.mercado.listarClientes();
        for (ClienteAbstrato cliente : clientes) {
            String nome = cliente.getNome();
            String documento = null;
            if (cliente instanceof Cliente) {
                documento = ((Cliente) cliente).getDocumento();
            }
            if (cliente instanceof Empresa) {
                documento = ((Empresa) cliente).getDocumento();
            }
            Object[] row = { nome, documento, false };
            modelo.addRow(row);
        }
    }
    private void cupom() {
    	this.mercado.desserializar();
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        ClienteAbstrato cliente = null;
        String documento = null;
        int linhas = modelo.getRowCount();
    	for (int i = 0; i < linhas; i++) {
    		boolean selecionado = (boolean) modelo.getValueAt(i, 2); // Coluna de seleção é a coluna de índice 4
    		if (selecionado) {
    			cliente = getProdutoDaTabela(i); // Obtém o produto correspondente à linha na tabela
    			documento = (String) modelo.getValueAt(i, 1);
    		}
    	}
	    this.textArea.setText("********CUPOM FISCAL********\n"+ 
	    							"Documento:"+documento+"\n"+
	    							"Nome:"+cliente.getNome()+"\n"
	    							+"Nome do Produto---"+"Quantidade"+"---"+"Valor(R$)\n");
	    Produto[] produtos = cliente.setCupom().getCompras();
	    for(Produto produto:produtos) {
	    	this.textArea.setText(this.textArea.getText()
									+produto.getNome()+"---"+produto.getQuantidade()+"UN"+"---"+produto.getValor()+"R$\n");
	    }
	    this.textArea.setText(this.textArea.getText() + "R$"+cliente.setCupom().valorTotal());
    }
    
    private ClienteAbstrato getProdutoDaTabela(int rowIndex) {
	    DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
	    String nome = (String) modelo.getValueAt(rowIndex, 0);
	    String documento = (String) modelo.getValueAt(rowIndex, 1);
	     
	    // Encontre o produto com base no nome e validade (se for um produto perecível)
	    ClienteAbstrato[] clientes = this.mercado.listarClientes();
	    for (ClienteAbstrato cliente : clientes) {
	        if (cliente.getNome().equals(nome)) {
	            return cliente;
	            }
	        }
	    
	    return null; // Produto não encontrado
	}
}

