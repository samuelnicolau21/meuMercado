package br.ufc.poo.grafica.panel;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.UIManager;

import br.ufc.poo.mercado.Mercado;
import br.ufc.poo.mercado.exececoes.SIException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Inicio extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField textInicio;
	private Mercado mercado;

	public Inicio() {
		this.mercado = new Mercado();
		
		setBackground(new Color(143, 188, 143));
		setForeground(UIManager.getColor("CheckBox.focus"));
		setLayout(null);
	
		/*Definindo os JLabels do JPanel*/
		JLabel meuMercado = new JLabel("MEU MERCADO");
		meuMercado.setBounds(235, 26, 295, 73);
		meuMercado.setHorizontalAlignment(SwingConstants.CENTER);
		meuMercado.setFont(new Font("Dialog", Font.BOLD, 28));
		add(meuMercado);
		
		JLabel guilherme = new JLabel("Guilherme Sales de Andrade");
		guilherme.setBounds(50, 379, 340, 24);
		guilherme.setFont(new Font("Dialog", Font.BOLD, 20));
		add(guilherme);
		
		JLabel joao = new JLabel("João Pedro Aragão");
		joao.setBounds(50, 414, 340, 24);
		joao.setFont(new Font("Dialog", Font.BOLD, 20));
		add(joao);
		
		JLabel samuel = new JLabel("Samuel Nicolau");
		samuel.setBounds(50, 453, 190, 24);
		samuel.setFont(new Font("Dialog", Font.BOLD, 20));
		add(samuel);
		
		JLabel digiteSaldo = new JLabel("Saldo do Banco");
		digiteSaldo.setBounds(296, 131, 220, 19);
		digiteSaldo.setFont(new Font("Dialog", Font.BOLD, 16));
		add(digiteSaldo);
		
		JLabel Creditos = new JLabel("Créditos:");
		Creditos.setBounds(50, 340, 110, 24);
		Creditos.setFont(new Font("Dialog", Font.BOLD, 20));
		add(Creditos);
		
		/*Declarando propiedades da variavel JTextField*/
		this.textInicio = new JTextField();
		this.textInicio.setBounds(308, 157, 144, 65);
		this.textInicio.setFont(new Font("Dialog", Font.PLAIN, 16));
		this.textInicio.setText(Double.toString(mercado.getSaldo()));
		add(this.textInicio);
		this.textInicio.setColumns(10);
		
		mudarSaldo();
		
		/*Declarando os JButtons do JPanel*/
		JButton saldo = new JButton("Mudar");
		saldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarSaldo();
			}
		});
		saldo.setBounds(308, 237, 104, 49);
		add(saldo);
		
		/*JLabels que tem uma imagem*/
		JLabel carrinho1 = new JLabel();
		carrinho1.setIcon(new ImageIcon(Inicio.class.getResource("/br/ufc/poo/grafica/imagem/Cart.png")));
		carrinho1.setBounds(200, 34, 71, 65);
		add(carrinho1);
		
		JLabel carrinho2 = new JLabel();
		carrinho2.setIcon(new ImageIcon(Inicio.class.getResource("/br/ufc/poo/grafica/imagem/Cart.png")));
		carrinho2.setBounds(492, 34, 71, 65);
		add(carrinho2);
	}
	
	private void adicionarSaldo() {
		try {
			this.mercado.desserializar();
			String saldo = this.textInicio.getText();
			double saldoDouble = Double.parseDouble(saldo);
			this.mercado.setSaldo(saldoDouble);
			this.textInicio.setText(Double.toString(mercado.getSaldo()));
			this.mercado.serializar();
		} catch (SIException pie) {
			JOptionPane.showMessageDialog(null, pie.getMessage());
		}
	}
	
	 public void mudarSaldo() {
	    	this.mercado.desserializar();
	    	this.textInicio.setText(Double.toString(mercado.getSaldo()));
	    }
}

