package Telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.UIManager;

import Banco_de_Dados.Conecta;
import Classes.RiscosDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;

import javax.swing.JTextArea;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RISCOS extends JFrame {

	RiscosDAO Riscos = new RiscosDAO();

	private JPanel contentPane;
	private JTextField txt_descri��o;
	private JTextArea txt_plano_de_a��o = new JTextArea();
	private JScrollPane pane_lista;
	private JTable table;
	private JLabel lbl_imagem = new JLabel();
	
	public RISCOS(int id_projeto) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Riscos.setId_projeto(id_projeto);
				popular_table();
				txt_descri��o.requestFocus();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 584, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pane_lista = new JScrollPane();
		pane_lista.setBounds(20, 291, 535, 127);
		getContentPane().add(pane_lista);

		setTitle("GERENCIAR RISCOS");
		setLocation(450, 100);

		//BLOQUEIA AO CLICAR NA TABLE
		table = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		//JOGA DADOS SELECIONADOS NOS TXTS
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dados();
			}
		});
		table.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_lista.setViewportView(table);

		txt_descri��o = new JTextField();
		txt_descri��o.setFont(new Font("Andalus", Font.PLAIN, 16));
		txt_descri��o.setHorizontalAlignment(SwingConstants.CENTER);
		txt_descri��o.setColumns(10);
		txt_descri��o.setBounds(133, 66, 411, 22);
		contentPane.add(txt_descri��o);

		//BOT�O VOLTAR
		JButton btn_voltar = new JButton("Voltar");
		btn_voltar.setForeground(Color.BLACK);
		btn_voltar.setBackground(UIManager.getColor("Button.background"));
		btn_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btn_voltar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_voltar.setBounds(20, 22, 104, 25);
		contentPane.add(btn_voltar);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(20, 58, 535, 222);
		contentPane.add(layeredPane);
		
		JLabel lbl_plano_de_a��o = new JLabel("Plano de A\u00E7\u00E3o:");
		lbl_plano_de_a��o.setBounds(10, 41, 125, 22);
		layeredPane.add(lbl_plano_de_a��o);
		lbl_plano_de_a��o.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 41, 125, 21);
		layeredPane.add(panel_1);
		
		JLabel lbl_descri��o = new JLabel("Descri\u00E7\u00E3o:");
		lbl_descri��o.setBounds(10, 11, 89, 22);
		layeredPane.add(lbl_descri��o);
		lbl_descri��o.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 14, 89, 19);
		layeredPane.add(panel);
		txt_plano_de_a��o.setFont(new Font("Andalus", Font.PLAIN, 15));
	
		txt_plano_de_a��o.setBounds(145, 43, 380, 168);
		layeredPane.add(txt_plano_de_a��o);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(20, 429, 535, 46);
		contentPane.add(layeredPane_1);
		
		//BOT�O CADASTRAR
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setBounds(26, 11, 155, 25);
		layeredPane_1.add(btn_cadastrar);
		btn_cadastrar.setForeground(Color.BLACK);
		btn_cadastrar.setBackground(UIManager.getColor("Button.background"));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar ''"
						+txt_descri��o.getText()+"''?",
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Riscos.setId_projeto(id_projeto);
						Riscos.setDescri��o(txt_descri��o.getText());
						Riscos.setPlano_de_a��o(txt_plano_de_a��o.getText());
				
						if(valida == JOptionPane.YES_OPTION){
							Riscos.Cadastrar();
							txt_descri��o.setText(null);
							txt_plano_de_a��o.setText(null);
							txt_descri��o.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+Riscos.getDescri��o()+"'' N�o Foi Cadastrado(a)! =(");
							txt_descri��o.setText(null);
							txt_plano_de_a��o.setText(null);
							txt_descri��o.requestFocus();
						}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOT�O ALTERAR
		JButton btn_alterar = new JButton("Alterar");
		btn_alterar.setBounds(191, 11, 157, 25);
		layeredPane_1.add(btn_alterar);
		btn_alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Alterar ''"
						+table.getValueAt(posicao_table, 1)+"'' ?",
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Riscos.setId_projeto(id_projeto);
						Riscos.setDescri��o(txt_descri��o.getText());
						Riscos.setPlano_de_a��o(txt_plano_de_a��o.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Riscos.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Riscos.Alterar();
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Alterado(a) Com Sucesso =)");
							txt_descri��o.setText(null);
							txt_plano_de_a��o.setText(null);
							txt_descri��o.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' N�o Foi Alterado(a)!");
							txt_descri��o.setText(null);
							txt_plano_de_a��o.setText(null);
							txt_descri��o.requestFocus();
						}
			}
		});
		btn_alterar.setForeground(Color.BLACK);
		btn_alterar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_alterar.setBackground(UIManager.getColor("Button.background"));
		
		//BOT�O EXCLUIR
		JButton btn_excluir = new JButton("Excluir");
		btn_excluir.setBounds(358, 11, 148, 25);
		layeredPane_1.add(btn_excluir);
		btn_excluir.setForeground(Color.BLACK);
		btn_excluir.setBackground(UIManager.getColor("Button.background"));
		btn_excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Excluir ''"
						+table.getValueAt(posicao_table, 1)+"''?",
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
		
						Riscos.setId_projeto(id_projeto);
						Riscos.setDescri��o(txt_descri��o.getText());
						Riscos.setPlano_de_a��o(txt_plano_de_a��o.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Riscos.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Riscos.Excluir();
							txt_descri��o.setText(null);
							txt_plano_de_a��o.setText(null);
							txt_descri��o.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' N�o Foi Exclu�do(a)!");
							txt_descri��o.setText(null);
							txt_plano_de_a��o.setText(null);
							txt_descri��o.requestFocus();
						}				
			}
		});
		btn_excluir.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2� Periodo\\Programa��o Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\V�rios.jpg"));
		lbl_imagem.setBounds(0, 0, 578, 486);
		contentPane.add(lbl_imagem);
	}
	
	//M�TODO POPULAR TABLE COM OS DADOS
	public void popular_table(){
		
		table.setModel(Riscos.Listar());
		
	}
	
	//M�TODO PEGAR LINHA SELECIONADA E JOGA NO TEXT
	public void dados(){
		
		int posicao_table = table.getSelectedRow();	
		txt_descri��o.setText(table.getValueAt(posicao_table, 1).toString());
		txt_plano_de_a��o.setText(table.getValueAt(posicao_table, 2).toString());		
		txt_descri��o.requestFocus();
	}
}