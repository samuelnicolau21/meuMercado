package br.ufc.poo.grafica.panel;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Date;
import java.util.Vector;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.toedter.calendar.JDateChooser;

import br.ufc.poo.mercado.Mercado;
import br.ufc.poo.mercado.exececoes.SIException;
import br.ufc.poo.produtos.Produto;
import br.ufc.poo.produtos.ProdutoPerecivel;
import br.ufc.poo.produtos.excecoes.NIException;
import br.ufc.poo.produtos.excecoes.PIException;
import br.ufc.poo.produtos.excecoes.QNException;
import br.ufc.poo.produtos.excecoes.VNException;

public class Estoque extends JPanel {

	//Variaveis da classe Estoque
	private static final long serialVersionUID = 1L;
	private JTextField textNome;
	private JTextField textValor;
	private JTextField textQuantidade;
	private JTextField textSaldo;
	private JTable tabela;
	private JDateChooser dateChooser;
	private Mercado mercado;
	
	//Metodo inicializador do JPanel
	public Estoque() {
		this.mercado = new Mercado();
		
		/*Cores e layout do JPanel*/
		setBackground(new Color(143, 188, 143));
		setForeground(new Color(255, 255, 255));
		setLayout(null);
		
		/*Declaração dos textFields na JPanel*/
		this.textNome = new JTextField();
		this.textNome.setBounds(47, 100, 114, 19);
		this.textNome.setColumns(10);
		add(this.textNome);
		
		this.textValor = new JTextField();
		this.textValor.setBounds(409, 100, 114, 19);
		this.textValor.setColumns(10);
		add(this.textValor);
		
		this.textQuantidade = new JTextField();
		this.textQuantidade.setBounds(226, 100, 114, 19);
		this.textQuantidade.setColumns(10);
		add(this.textQuantidade);
		
		this.textSaldo = new JTextField();
		this.textSaldo.setBounds(325, 162, 114, 35);
		this.textSaldo.setColumns(10);
		add(this.textSaldo);
		
		/*Declaração dos JLabels no JPanel*/
		JLabel lblNomeDoProduto = new JLabel("Nome do Produto");
		lblNomeDoProduto.setBounds(47, 69, 126, 19);
		lblNomeDoProduto.setFont(new Font("Dialog", Font.BOLD, 16));
		add(lblNomeDoProduto);
		
		JLabel lblValorDoProduto = new JLabel("Valor do Produto");
		lblValorDoProduto.setBounds(409, 69, 121, 19);
		lblValorDoProduto.setFont(new Font("Dialog", Font.BOLD, 16));
		add(lblValorDoProduto);
		
		JLabel lblEstoque = new JLabel("ESTOQUE");
		lblEstoque.setBounds(339, 10, 87, 24);
		lblEstoque.setFont(new Font("Dialog", Font.BOLD, 20));
		add(lblEstoque);
		
		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(226, 69, 83, 19);
		lblQuantidade.setFont(new Font("Dialog", Font.BOLD, 16));
		add(lblQuantidade);
		
		JLabel lblSaldo = new JLabel("Saldo:");
		lblSaldo.setBounds(355, 143, 46, 19);
		lblSaldo.setFont(new Font("Dialog", Font.BOLD, 16));
		add(lblSaldo);
		
		JLabel lblValidade = new JLabel("Validade");
		lblValidade.setFont(new Font("Dialog", Font.BOLD, 16));
		lblValidade.setBounds(585, 72, 121, 19);
		add(lblValidade);
		
		/*Declaração dos JButtons no JPanel*/
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}
		});
		btnAtualizar.setBounds(47, 231, 114, 48);
		add(btnAtualizar);
		
		JButton btnValidade = new JButton("Validade");
		btnValidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Validade();
			}
		});
		btnValidade.setBounds(601, 231, 105, 48);
		add(btnValidade);
		
		JButton btnVender = new JButton("Tirar");
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnVender.setBounds(462, 231, 114, 48);
		add(btnVender);
		
		JButton btnComprar = new JButton("Comprar");
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comprar();
			}
		});
		btnComprar.setBounds(188, 231, 114, 48);
		add(btnComprar);
		
		JButton btnValor = new JButton("Valor");
		btnValor.setBounds(325, 231, 114, 48);
		btnValor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarValor();
			}
		});
		add(btnValor);
		
		/*Declarção de uma dateChooser para escolher a data*/
		this.dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd/MM/yyyy");
		this.dateChooser.setBounds(590, 100, 116, 19);
		add(this.dateChooser);
		
		/*Declarção de uma JScrollPane para colocar a tabela no JPanel principal*/
		JScrollPane scrollTabela = new JScrollPane();
		scrollTabela.setBounds(10, 341, 745, 195);
		add(scrollTabela);
		
		/*Definindo caracteristicas da tabela*/
		this.tabela = new JTable();
		this.tabela.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "Quantidade", "Valor", "Validade", "Selecionar"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Double.class, Object.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			@Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Apenas a coluna de seleção é editável
            }
		});
		scrollTabela.setViewportView(this.tabela);
		
		atualizar();
	}

	//Metodo para comprar o produto e colocalo no estoque
	private void comprar() {
		try {
			this.mercado.desserializar();
			String nome = this.textNome.getText();
			String quantidade = this.textQuantidade.getText();
			String valor = this.textValor.getText();
			String validade = ((JTextField) this.dateChooser.getDateEditor().getUiComponent()).getText();
			int quantidadeInt = Integer.parseInt(quantidade); // Converte a quantidade e o valor para os tipos adequados
			double valorDouble = Double.parseDouble(valor);
			Produto produto;
			if (!validade.isEmpty()) {
				produto = new ProdutoPerecivel(nome, quantidadeInt, valorDouble, validade);
			} else {
				produto = new Produto(nome, quantidadeInt, valorDouble);
			}
			this.mercado.comprarProdutos(produto);
			this.mercado.serializar();
			this.textNome.setText("");
			this.textQuantidade.setText("");
			this.textValor.setText("");
			this.textSaldo.setText(Double.toString(mercado.getSaldo()));
			((JTextField) this.dateChooser.getDateEditor().getUiComponent()).setText("");
			JOptionPane.showMessageDialog(null, "Compra feita com sucesso");
		} catch (QNException qne) {
			JOptionPane.showMessageDialog(null, qne.getMessage());
		} catch (VNException vne) {
			JOptionPane.showMessageDialog(null, vne.getMessage());
		} catch (NIException nie) {
			JOptionPane.showMessageDialog(null, nie.getMessage());
		} catch (SIException sie) {
			JOptionPane.showMessageDialog(null, sie.getMessage());
		}
	}
	
	//Metodo que coloca na tabela somente os produtos que já passaram da validade
	private void Validade() {
		try {
		this.mercado.desserializar();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
		modelo.setRowCount(0); // Limpa a tabela antes de preencher novamente
            String dataFormatada = formato.format(new Date());
            Produto[] produtos = this.mercado.listarEstoque();
            for (Produto produto : produtos) {
                if (produto instanceof ProdutoPerecivel) {
                    Date dataProduto = transformarData(((ProdutoPerecivel) produto).getValidade());
                    Date dataAtual = transformarData(dataFormatada);
                    if (!compararDatas(dataProduto, dataAtual)) {
            			String nome = produto.getNome();
            			int quantidade = produto.getQuantidade();
                        double valor = produto.getValor();
                        String validade = validade = ((ProdutoPerecivel) produto).getValidade().toString();
                        Object[] row = { nome, quantidade, valor, validade, false };
                        modelo.addRow(row);
                    }
                }
            }
        } catch (ParseException e) {
        		e.printStackTrace();
        		JOptionPane.showInputDialog(this, "Erro na Data");
        }
	}
	
	//Metodo para atualizar a tabela de acordo com os objetos salvos no arquivo
	private void atualizar() {
		this.mercado.desserializar();
		this.textSaldo.setText(Double.toString(mercado.getSaldo()));
		DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
		modelo.setRowCount(0); // Limpa a tabela antes de preencher novamente
		Produto[] produtos = this.mercado.listarEstoque();
		for (Produto produto : produtos) {
			String nome = produto.getNome();
			int quantidade = produto.getQuantidade();
			double valor = produto.getValor();
			String validade = "";
			if(produto instanceof ProdutoPerecivel){
				validade = ((ProdutoPerecivel) produto).getValidade().toString();
			}
			Object[] row = { nome, quantidade, valor, validade, false };
            modelo.addRow(row);
        }
	}
	
	//Metodo que remove os itens marcados na tabela
	private void remover() {
		try {
	        this.mercado.desserializar();
	        DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
	        int linhas = modelo.getRowCount();
	        Vector<Produto> produtosExcluir = new Vector<>();
	        for (int i = 0; i < linhas; i++) {
	            boolean selecionado = (boolean) modelo.getValueAt(i, 4); // Coluna de seleção é a coluna de índice 4
	            if (selecionado) {
	                Produto produto = getProdutoDaTabela(i); // Obtém o produto correspondente à linha na tabela
	                produtosExcluir.add(produto);
	            }
	        }
	        for (Produto produto : produtosExcluir) {
	            this.mercado.removerProduto(produto.getNome(), produto.getQuantidade());
	        }
	        this.mercado.serializar();
	        JOptionPane.showMessageDialog(null, "Retirada feita com sucesso");
		} catch (PIException pie) {
		JOptionPane.showMessageDialog(null, pie.getMessage());
		} catch (QNException qne) {
			JOptionPane.showMessageDialog(null, qne.getMessage());
		}
	}

	private Produto getProdutoDaTabela(int rowIndex) {
	    DefaultTableModel modelo = (DefaultTableModel) this.tabela.getModel();
	    String nome = (String) modelo.getValueAt(rowIndex, 0);
	    int quantidade = (int) modelo.getValueAt(rowIndex, 1);
	    double valor = (double) modelo.getValueAt(rowIndex, 2);
	    String validade = (String) modelo.getValueAt(rowIndex, 3);
	    
	    // Encontre o produto com base no nome e validade (se for um produto perecível)
	    Produto[] produtos = this.mercado.listarEstoque();
	    for (Produto produto : produtos) {
	        if (produto.getNome().equals(nome)) {
	            if (produto instanceof ProdutoPerecivel && validade.equals(((ProdutoPerecivel) produto).getValidade().toString())) {
	                return produto;
	            } else if (!(produto instanceof ProdutoPerecivel)) {
	                return produto;
	            }
	        }
	    }
	    
	    return null; // Produto não encontrado
	}

	private void alterarValor() {
	    try {
	        this.mercado.desserializar();
	        DefaultTableModel model = (DefaultTableModel) this.tabela.getModel();
	        int linha = this.tabela.getSelectedRow(); // Obtém a linha selecionada na tabela
	        if (linha == -1) {
	            JOptionPane.showMessageDialog(null, "Selecione um produto da tabela");
	            return;
	        }
	        String novoValorStr = this.textValor.getText();
	        double novoValor = Double.parseDouble(novoValorStr);
	        Produto produto = getProdutoDaTabela(linha); // Obtém o produto correspondente à linha selecionada
			mercado.mudarValorProduto(produto.getNome(), novoValor);
	        this.mercado.serializar();
	        this.textValor.setText("");
	        JOptionPane.showMessageDialog(null, "Valor atualizado com sucesso");
	    } catch (VNException vne) {
	    	JOptionPane.showMessageDialog(null, vne.getMessage());
		} catch (PIException pie) {
			JOptionPane.showMessageDialog(null, pie.getMessage());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "Caixa de texto vázio");
		}
	}
	
	public Date transformarData(String data) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.parse(data);
    }

    public boolean compararDatas(Date dataProduto, Date dataAtual) {
        return dataProduto.compareTo(dataAtual) > 0;
    }
}


