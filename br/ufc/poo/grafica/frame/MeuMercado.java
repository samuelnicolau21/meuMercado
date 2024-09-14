package br.ufc.poo.grafica.frame;

import br.ufc.poo.grafica.panel.*;
  
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import java.awt.Color;

public class MeuMercado extends JFrame implements ItemListener {

	private static final long serialVersionUID = -1406384901096162695L;
	private JPanel panelPrincipal;
	final static String VENDAS = "Vendas";
    final static String HISTORICO = "Historico";
    final static String ESTOQUE = "Estoque";
    final static String INICIO = "Inicio";
    private JPanel cardPanel;
    private JComboBox<?> seletor;

	public MeuMercado() {
		/*Delcarando as JFrame e do contentePane do JFrame*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 617);
		this.panelPrincipal = new JPanel();

		setContentPane(this.panelPrincipal);
		this.panelPrincipal.setLayout(new BorderLayout(0, 0));
		
		/*Delcarando o seletor comboBox e o panel onde ele vai estar*/
		JPanel panelSeletor = new JPanel();
		panelSeletor.setForeground(new Color(255, 255, 255));
		panelSeletor.setBackground(new Color(46, 139, 87));
		String comboBoxItems[] = {INICIO, ESTOQUE, VENDAS, HISTORICO };
        this.seletor = new JComboBox(comboBoxItems);
        this.seletor.setEditable(false);
        this.seletor.addItemListener(this);
        panelSeletor.add(this.seletor);
		this.panelPrincipal.add(panelSeletor, BorderLayout.NORTH);
		
		/*Definindo o cardPanel com um CardLayout e adicionandos os cards no cardPanel*/
		this.cardPanel = new JPanel(new CardLayout());
		this.panelPrincipal.add(this.cardPanel, BorderLayout.CENTER);
		
		JPanel card1 = new Inicio();
		JPanel card2 = new Vendas();
		JPanel card3 = new Historico();
		JPanel card4 = new Estoque();
		
		this.cardPanel.add(card1, INICIO);
		this.cardPanel.add(card2, VENDAS);
		this.cardPanel.add(card3, HISTORICO);
		this.cardPanel.add(card4, ESTOQUE);
	}

	/*Função do CardLayout que é responsável por mudar os cards*/
	@Override
	public void itemStateChanged(ItemEvent e) {
		        CardLayout cl = (CardLayout)(this.cardPanel.getLayout());
		        cl.show(this.cardPanel, (String)e.getItem());
	}
}
