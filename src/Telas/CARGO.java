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
import Classes.CargoDAO;
import Classes.CargoDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CARGO extends JFrame {

	CargoDAO Cargo = new CargoDAO();

	private JPanel contentPane;
	private JScrollPane pane_lista;
	private JTable table;
	private JTextField txt_cargo;
	private JLabel lbl_imagem = new JLabel();
	
	public CARGO() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				popular_table();
				txt_cargo.requestFocus();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 497, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pane_lista = new JScrollPane();
		pane_lista.setBounds(20, 110, 453, 164);
		getContentPane().add(pane_lista);

		setTitle("GERENCIAR CARGOS");
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

		txt_cargo = new JTextField();
		txt_cargo.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_cargo.setHorizontalAlignment(SwingConstants.CENTER);
		txt_cargo.setColumns(10);
		txt_cargo.setBounds(105, 66, 357, 22);
		contentPane.add(txt_cargo);

		//BOTÃO VOLTAR
		JButton btn_voltar = new JButton("Voltar");
		btn_voltar.setToolTipText("Voltar Para a Tela T\u00E9cnicos");
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
		
		JLabel lbl_cargo = new JLabel("Cargo:");
		lbl_cargo.setBounds(30, 66, 65, 22);
		contentPane.add(lbl_cargo);
		lbl_cargo.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(20, 58, 453, 41);
		contentPane.add(layeredPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 65, 19);
		layeredPane.add(panel);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(20, 285, 453, 46);
		contentPane.add(layeredPane_1);
		
		//BOTÃO CADASTRAR
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setToolTipText("Cadastrar Cargo");
		btn_cadastrar.setBounds(26, 11, 129, 25);
		layeredPane_1.add(btn_cadastrar);
		btn_cadastrar.setForeground(Color.BLACK);
		btn_cadastrar.setBackground(UIManager.getColor("Button.background"));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar "
						+txt_cargo.getText()+"?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Cargo.setCargo(txt_cargo.getText());
				
						if(valida == JOptionPane.YES_OPTION){
							Cargo.Cadastrar();
							JOptionPane.showMessageDialog(null, Cargo.getCargo()+" Cadastrado(a) Com Sucesso! =)");
							txt_cargo.setText(null);
							txt_cargo.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, Cargo.getCargo()+" Não Foi Cadastrado(a)! =(");
							txt_cargo.setText(null);
							txt_cargo.requestFocus();
						}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO ALTERAR
		JButton btn_alterar = new JButton("Alterar");
		btn_alterar.setToolTipText("Alterar Cargo");
		btn_alterar.setBounds(165, 11, 117, 25);
		layeredPane_1.add(btn_alterar);
		btn_alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Alterar "
						+table.getValueAt(posicao_table, 1)+" Para "+txt_cargo.getText()+"?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Cargo.setCargo(txt_cargo.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Cargo.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Cargo.Alterar();
							JOptionPane.showMessageDialog(null, table.getValueAt(posicao_table, 1).toString()+" Alterado(a) Com Sucesso Para "+Cargo.getCargo()+" =)");
							txt_cargo.setText(null);
							txt_cargo.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, table.getValueAt(posicao_table, 1).toString()+" Não Foi Alterado(a)!");
							txt_cargo.setText(null);
							txt_cargo.requestFocus();
						}
			}
		});
		btn_alterar.setForeground(Color.BLACK);
		btn_alterar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_alterar.setBackground(UIManager.getColor("Button.background"));
		
		//BOTÃO EXCLUIR
		JButton btn_excluir = new JButton("Excluir");
		btn_excluir.setToolTipText("Excluir Cargo");
		btn_excluir.setBounds(292, 11, 133, 25);
		layeredPane_1.add(btn_excluir);
		btn_excluir.setForeground(Color.BLACK);
		btn_excluir.setBackground(UIManager.getColor("Button.background"));
		btn_excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Excluir "
						+table.getValueAt(posicao_table, 1)+"?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Cargo.setCargo(txt_cargo.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Cargo.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Cargo.Excluir();
							JOptionPane.showMessageDialog(null, Cargo.getCargo()+" Excluído(a) Com Sucesso! =)");
							txt_cargo.setText(null);
							txt_cargo.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, table.getValueAt(posicao_table, 1).toString()+" Não Foi Excluído(a)!");
							txt_cargo.setText(null);
							txt_cargo.requestFocus();
						}
			}
		});
		btn_excluir.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Vários.jpg"));
		lbl_imagem.setBounds(0, 0, 491, 352);
		contentPane.add(lbl_imagem);
	}
	
	//MÉTODO POPULAR TABLE COM OS DADOS
	public void popular_table(){
		
		table.setModel(Cargo.Listar());
		
	}
	
	//MÉTODO PEGAR LINHA SELECIONADA E JOGA NO TEXT
	public void dados(){
		
		int posicao_table = table.getSelectedRow();	
		txt_cargo.setText(table.getValueAt(posicao_table, 1).toString());		
		txt_cargo.requestFocus();
	}
}