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
import Classes.Casos_de_UsoDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;

public class CASOS_DE_USO extends JFrame {
	
	Casos_de_UsoDAO Casos_de_Uso = new Casos_de_UsoDAO();

	private JPanel contentPane;
	private JScrollPane pane_lista;
	private JTable table;
	private JTextField txt_csu;
	JTextArea txt_descrição = new JTextArea();
	private JLabel lbl_imagem = new JLabel();
	
	public CASOS_DE_USO(int id_projeto) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Casos_de_Uso.setId_projeto(id_projeto);
				popular_table();
				txt_csu.requestFocus();
			}
		});
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 704, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pane_lista = new JScrollPane();
		pane_lista.setBounds(20, 221, 656, 173);
		getContentPane().add(pane_lista);

		setTitle("GERENCIAR CASOS DE USO");
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

		txt_csu = new JTextField();
		txt_csu.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_csu.setHorizontalAlignment(SwingConstants.CENTER);
		txt_csu.setColumns(10);
		txt_csu.setBounds(89, 66, 574, 22);
		contentPane.add(txt_csu);

		//BOTÃO VOLTAR
		JButton btn_voltar = new JButton("Voltar");
		btn_voltar.setForeground(Color.BLACK);
		btn_voltar.setBackground(UIManager.getColor("Button.background"));
		btn_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		txt_descrição.setFont(new Font("Andalus", Font.PLAIN, 15));
		txt_descrição.setBounds(138, 99, 525, 97);
		contentPane.add(txt_descrição);
		btn_voltar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_voltar.setBounds(20, 22, 104, 25);
		contentPane.add(btn_voltar);
		
		JLabel lbl_csu = new JLabel("CSU:");
		lbl_csu.setBounds(30, 66, 49, 22);
		contentPane.add(lbl_csu);
		lbl_csu.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(20, 58, 656, 152);
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
		layeredPane_1.setBounds(20, 405, 656, 46);
		contentPane.add(layeredPane_1);
		
		//BOTÃO CADASTRAR
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setBounds(26, 11, 200, 25);
		layeredPane_1.add(btn_cadastrar);
		btn_cadastrar.setForeground(Color.BLACK);
		btn_cadastrar.setBackground(UIManager.getColor("Button.background"));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar ''"
						+txt_csu.getText()+"''?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Casos_de_Uso.setId_projeto(id_projeto);
						Casos_de_Uso.setCSU(txt_csu.getText());
						Casos_de_Uso.setDescrição(txt_descrição.getText());
				
						if(valida == JOptionPane.YES_OPTION){
							Casos_de_Uso.Cadastrar();
							txt_csu.setText(null);
							txt_descrição.setText(null);
							txt_csu.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+Casos_de_Uso.getCSU()+"'' Não Foi Cadastrado(a)! =(");
							txt_csu.setText(null);
							txt_descrição.setText(null);
							txt_csu.requestFocus();
						}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO ALTERAR
		JButton btn_alterar = new JButton("Alterar");
		btn_alterar.setBounds(236, 11, 179, 25);
		layeredPane_1.add(btn_alterar);
		btn_alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Alterar ''"
						+table.getValueAt(posicao_table, 1)+"'' Para ''"+txt_csu.getText()+"''?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Casos_de_Uso.setId_projeto(id_projeto);
						Casos_de_Uso.setCSU(txt_csu.getText());
						Casos_de_Uso.setDescrição(txt_descrição.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Casos_de_Uso.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Casos_de_Uso.Alterar();
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Alterado(a) Com Sucesso Para ''"+Casos_de_Uso.getCSU()+"'' =)");
							txt_csu.setText(null);
							txt_descrição.setText(null);
							txt_csu.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Não Foi Alterado(a)!");
							txt_csu.setText(null);
							txt_descrição.setText(null);
							txt_csu.requestFocus();
						}
			}
		});
		btn_alterar.setForeground(Color.BLACK);
		btn_alterar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_alterar.setBackground(UIManager.getColor("Button.background"));
		
		//BOTÃO EXCLUIR
		JButton btn_excluir = new JButton("Excluir");
		btn_excluir.setBounds(425, 11, 208, 25);
		layeredPane_1.add(btn_excluir);
		btn_excluir.setForeground(Color.BLACK);
		btn_excluir.setBackground(UIManager.getColor("Button.background"));
		btn_excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Excluir ''"
						+table.getValueAt(posicao_table, 1)+"''?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Casos_de_Uso.setId_projeto(id_projeto);
						Casos_de_Uso.setCSU(txt_csu.getText());
						Casos_de_Uso.setDescrição(txt_descrição.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Casos_de_Uso.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Casos_de_Uso.Excluir();
							JOptionPane.showMessageDialog(null, "''"+Casos_de_Uso.getCSU()+"'' Excluído Com Sucesso! =)");
							txt_csu.setText(null);
							txt_descrição.setText(null);
							txt_csu.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Não Foi Excluído(a)!");
							txt_csu.setText(null);
							txt_descrição.setText(null);
							txt_csu.requestFocus();
						}				
			}
		});
		btn_excluir.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Vários.jpg"));
		lbl_imagem.setBounds(0, 0, 698, 462);
		contentPane.add(lbl_imagem);
	}
	//MÉTODO POPULAR TABLE COM OS DADOS
	public void popular_table(){
		
		table.setModel(Casos_de_Uso.Listar());
		
	}
	
	//MÉTODO PEGAR LINHA SELECIONADA E JOGA NO TEXT
	public void dados(){
		
		int posicao_table = table.getSelectedRow();	
		txt_csu.setText(table.getValueAt(posicao_table, 1).toString());
		txt_descrição.setText(table.getValueAt(posicao_table, 2).toString());		
		txt_csu.requestFocus();
	}
}