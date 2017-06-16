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
import Classes.RequisitosDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;

public class REQUISITOS extends JFrame {
	
	RequisitosDAO Requisitos = new RequisitosDAO();

	private JPanel contentPane;
	private JScrollPane pane_lista;
	private JTable table;
	private JTextField txt_rf;
	JTextArea txt_descri��o = new JTextArea();	
	private JLabel lbl_imagem = new JLabel();
	
	public REQUISITOS(int id_projeto) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Requisitos.setId_projeto(id_projeto);
				popular_table();
				txt_rf.requestFocus();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 716, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pane_lista = new JScrollPane();
		pane_lista.setBounds(20, 216, 666, 173);
		getContentPane().add(pane_lista);

		setTitle("GERENCIAR REQUISITOS");
		setLocation(450, 150);

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

		txt_rf = new JTextField();
		txt_rf.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_rf.setHorizontalAlignment(SwingConstants.CENTER);
		txt_rf.setColumns(10);
		txt_rf.setBounds(89, 66, 583, 22);
		contentPane.add(txt_rf);

		//BOT�O VOLTAR
		JButton btn_voltar = new JButton("Voltar");
		btn_voltar.setForeground(Color.BLACK);
		btn_voltar.setBackground(UIManager.getColor("Button.background"));
		btn_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		txt_descri��o.setFont(new Font("Andalus", Font.PLAIN, 15));
		txt_descri��o.setBounds(127, 99, 545, 92);
		contentPane.add(txt_descri��o);
		btn_voltar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_voltar.setBounds(20, 22, 104, 25);
		contentPane.add(btn_voltar);
		
		JLabel lbl_rf = new JLabel("RF:");
		lbl_rf.setBounds(40, 66, 39, 22);
		contentPane.add(lbl_rf);
		lbl_rf.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(20, 58, 666, 147);
		contentPane.add(layeredPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 47, 19);
		layeredPane.add(panel);
		
		JLabel label = new JLabel("Descri\u00E7\u00E3o:");
		label.setBounds(10, 41, 89, 22);
		layeredPane.add(label);
		label.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 41, 88, 21);
		layeredPane.add(panel_1);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(20, 411, 666, 46);
		contentPane.add(layeredPane_1);
		
		//BOT�O CADASTRAR
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setBounds(26, 11, 193, 25);
		layeredPane_1.add(btn_cadastrar);
		btn_cadastrar.setForeground(Color.BLACK);
		btn_cadastrar.setBackground(UIManager.getColor("Button.background"));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar ''"
						+txt_rf.getText()+"''?",
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Requisitos.setId_projeto(id_projeto);
						Requisitos.setRF(txt_rf.getText());
						Requisitos.setDescri��o(txt_descri��o.getText());
				
						if(valida == JOptionPane.YES_OPTION){
							Requisitos.Cadastrar();
							txt_rf.setText(null);
							txt_descri��o.setText(null);
							txt_rf.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+Requisitos.getRF()+"'' N�o Foi Cadastrado(a)! =(");
							txt_rf.setText(null);
							txt_descri��o.setText(null);
							txt_rf.requestFocus();
						}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOT�O ALTERAR
		JButton btn_alterar = new JButton("Alterar");
		btn_alterar.setBounds(229, 11, 199, 25);
		layeredPane_1.add(btn_alterar);
		btn_alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Alterar ''"
						+table.getValueAt(posicao_table, 1)+"'' Para ''"+txt_rf.getText()+"''?",
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Requisitos.setId_projeto(id_projeto);
						Requisitos.setRF(txt_rf.getText());
						Requisitos.setDescri��o(txt_descri��o.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Requisitos.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Requisitos.Alterar();
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Alterado(a) Com Sucesso Para ''"+Requisitos.getRF()+"'' =)");
							txt_rf.setText(null);
							txt_descri��o.setText(null);
							txt_rf.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' N�o Foi Alterado(a)!");
							txt_rf.setText(null);
							txt_descri��o.setText(null);
							txt_rf.requestFocus();
						}
			}
		});
		btn_alterar.setForeground(Color.BLACK);
		btn_alterar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_alterar.setBackground(UIManager.getColor("Button.background"));
		
		//BOT�O EXCLUIR
		JButton btn_excluir = new JButton("Excluir");
		btn_excluir.setBounds(438, 11, 202, 25);
		layeredPane_1.add(btn_excluir);
		btn_excluir.setForeground(Color.BLACK);
		btn_excluir.setBackground(UIManager.getColor("Button.background"));
		btn_excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Excluir ''"
						+table.getValueAt(posicao_table, 1)+"''?",
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Requisitos.setId_projeto(id_projeto);
						Requisitos.setRF(txt_rf.getText());
						Requisitos.setDescri��o(txt_descri��o.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Requisitos.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Requisitos.Excluir();
							txt_rf.setText(null);
							txt_descri��o.setText(null);
							txt_rf.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' N�o Foi Exclu�do(a)!");
							txt_rf.setText(null);
							txt_descri��o.setText(null);
							txt_rf.requestFocus();
						}				
			}
		});
		btn_excluir.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2� Periodo\\Programa��o Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\V�rios.jpg"));
		lbl_imagem.setBounds(0, 0, 710, 468);
		contentPane.add(lbl_imagem);
	}
	
	//M�TODO POPULAR TABLE COM OS DADOS
	public void popular_table(){
		
		table.setModel(Requisitos.Listar());
		
	}
	
	//M�TODO PEGAR LINHA SELECIONADA E JOGA NO TEXT
	public void dados(){
		
		int posicao_table = table.getSelectedRow();	
		txt_rf.setText(table.getValueAt(posicao_table, 1).toString());
		txt_descri��o.setText(table.getValueAt(posicao_table, 2).toString());		
		txt_rf.requestFocus();
	}
}