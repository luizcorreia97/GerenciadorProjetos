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
import Classes.StakeholdersDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class STAKEHOLDERS extends JFrame {
	
	StakeholdersDAO Stakeholders = new StakeholdersDAO();

	private JPanel contentPane;
	private JTextField txt_nome;
	private JScrollPane pane_lista;
	private JTable table;
	private JLabel lbl_imagem = new JLabel();
	
	public STAKEHOLDERS(int id_projeto) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				Stakeholders.setId_projeto(id_projeto);
				popular_table();
				txt_nome.requestFocus();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pane_lista = new JScrollPane();
		pane_lista.setBounds(20, 110, 568, 164);
		getContentPane().add(pane_lista);

		setTitle("GERENCIAR STAKEHOLDERS");
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

		txt_nome = new JTextField();
		txt_nome.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_nome.setHorizontalAlignment(SwingConstants.CENTER);
		txt_nome.setColumns(10);
		txt_nome.setBounds(103, 66, 473, 22);
		contentPane.add(txt_nome);

		//BOTÃO VOLTAR
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
		
		JLabel lbl_nome = new JLabel("Nome:");
		lbl_nome.setBounds(30, 66, 63, 22);
		contentPane.add(lbl_nome);
		lbl_nome.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(20, 58, 568, 41);
		contentPane.add(layeredPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 60, 19);
		layeredPane.add(panel);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(20, 285, 568, 46);
		contentPane.add(layeredPane_1);
		
		//BOTÃO CADASTRAR
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setBounds(26, 11, 162, 25);
		layeredPane_1.add(btn_cadastrar);
		btn_cadastrar.setForeground(Color.BLACK);
		btn_cadastrar.setBackground(UIManager.getColor("Button.background"));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar ''"
						+txt_nome.getText()+"''?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Stakeholders.setNome(txt_nome.getText());
				
						if(valida == JOptionPane.YES_OPTION){
							Stakeholders.Cadastrar();
							txt_nome.setText(null);
							txt_nome.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+Stakeholders.getNome()+"'' Não Foi Cadastrado(a)! =(");
							txt_nome.setText(null);
							txt_nome.requestFocus();
						}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO ALTERAR
		JButton btn_alterar = new JButton("Alterar");
		btn_alterar.setBounds(198, 11, 162, 25);
		layeredPane_1.add(btn_alterar);
		btn_alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Alterar ''"
						+table.getValueAt(posicao_table, 1)+"'' Para ''"+txt_nome.getText()+"''?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Stakeholders.setId_projeto(id_projeto);
						Stakeholders.setNome(txt_nome.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Stakeholders.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Stakeholders.Alterar();
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Alterado(a) Com Sucesso Para ''"+Stakeholders.getNome()+"'' =)");
							txt_nome.setText(null);
							txt_nome.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Não Foi Alterado(a)!");
							txt_nome.setText(null);
							txt_nome.requestFocus();
						}
			}
		});
		btn_alterar.setForeground(Color.BLACK);
		btn_alterar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_alterar.setBackground(UIManager.getColor("Button.background"));
		
		//BOTÃO EXCLUIR
		JButton btn_excluir = new JButton("Excluir");
		btn_excluir.setBounds(370, 11, 174, 25);
		layeredPane_1.add(btn_excluir);
		btn_excluir.setForeground(Color.BLACK);
		btn_excluir.setBackground(UIManager.getColor("Button.background"));
		btn_excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Excluir ''"
						+table.getValueAt(posicao_table, 1)+"''?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Stakeholders.setId_projeto(id_projeto);
						Stakeholders.setNome(txt_nome.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Stakeholders.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Stakeholders.Excluir();
							txt_nome.setText(null);
							txt_nome.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Não Foi Excluído(a)!");
							txt_nome.setText(null);
							txt_nome.requestFocus();
						}				
			}
		});
		btn_excluir.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Vários.jpg"));
		lbl_imagem.setBounds(0, 0, 609, 352);
		contentPane.add(lbl_imagem);
	}
	
	//MÉTODO POPULAR TABLE COM OS DADOS
	public void popular_table(){
		
		table.setModel(Stakeholders.Listar());
		
	}
	
	//MÉTODO PEGAR LINHA SELECIONADA E JOGA NO TEXT
	public void dados(){
		
		int posicao_table = table.getSelectedRow();
		txt_nome.setText(table.getValueAt(posicao_table, 1).toString());		
		txt_nome.requestFocus();
	}
}