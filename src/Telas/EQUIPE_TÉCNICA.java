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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;


import java.awt.Color;

import javax.swing.UIManager;

import Banco_de_Dados.Conecta;
import Classes.Equipe_T�cnicaDAO;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class EQUIPE_T�CNICA extends JFrame {
	
	Equipe_T�cnicaDAO Equipe_T�cnica = new Equipe_T�cnicaDAO();

	private JPanel contentPane;
	private JScrollPane pane_lista_t�cnicos_cadastrados;
	private JTable table_tecnicos_cadastrados;
	
	private JScrollPane pane_lista_integrantes_da_equipe;
	private JTable table_integrantes_da_equipe;
	
	public EQUIPE_T�CNICA(int id_projeto) {
		setType(Type.POPUP);
		
		//M�TODO ASSIM QUE INICIALIZA A TELA EQUIPE T�CNICA
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				Equipe_T�cnica.setId_projeto(id_projeto);
				popular_tables();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 844, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pane_lista_t�cnicos_cadastrados = new JScrollPane();
		pane_lista_t�cnicos_cadastrados.setBounds(490, 82, 338, 137);
		getContentPane().add(pane_lista_t�cnicos_cadastrados);
		
		pane_lista_integrantes_da_equipe = new JScrollPane();
		pane_lista_integrantes_da_equipe.setBounds(10, 82, 382, 137);
		getContentPane().add(pane_lista_integrantes_da_equipe);

		setTitle("GERENCIAR EQUIPE T\u00C9CNICA");
		//setLocation(450, 150);
		setLocationRelativeTo(null);
		setResizable(false);

		//BLOQUEIA AO CLICAR NA TABLE T�CNICOS CADASTRADOS
		table_tecnicos_cadastrados = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		//PEGA O QUE FOI SELECIONADO NA TABLE T�CNICOS CADASTRADOS E JOGA NAS VARI�VEIS
		table_tecnicos_cadastrados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int posicao = table_tecnicos_cadastrados.getSelectedRow();
				Equipe_T�cnica.setId_projeto(id_projeto);
				Equipe_T�cnica.setId_tecnico(Integer.parseInt(table_tecnicos_cadastrados.getValueAt(posicao, 0).toString()));
				Equipe_T�cnica.setNome_tecnico(table_tecnicos_cadastrados.getValueAt(posicao, 1).toString());
				Equipe_T�cnica.setCargo_tecnico(table_tecnicos_cadastrados.getValueAt(posicao, 2).toString());
				
			}
		});
		table_tecnicos_cadastrados.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_lista_t�cnicos_cadastrados.setViewportView(table_tecnicos_cadastrados);
		
		//BLOQUEIA AO CLICAR NA TABLE T�CNICOS CADASTRADOS
		table_integrantes_da_equipe = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};

		//PEGA O QUE FOI SELECIONADO NA TABLE INTEGRANTES DA EQUIPE E JOGA NAS VARI�VEIS
		table_integrantes_da_equipe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int posicao = table_integrantes_da_equipe.getSelectedRow();
				Equipe_T�cnica.setId_projeto(id_projeto);
				Equipe_T�cnica.setId_tecnico(Integer.parseInt(table_integrantes_da_equipe.getValueAt(posicao, 0).toString()));
				
			}
		});
		table_integrantes_da_equipe.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_lista_integrantes_da_equipe.setViewportView(table_integrantes_da_equipe);
		
		//BOT�O "SALVAR"
		JButton btn_salvar = new JButton("Salvar");
		btn_salvar.setToolTipText("Salvar Equipe T\u00E9cnica");
		btn_salvar.setForeground(Color.BLACK);
		btn_salvar.setBackground(UIManager.getColor("Button.background"));
		btn_salvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Equipe T�cnica Salva Com Sucesso!");
				dispose();
			}
		});
		btn_salvar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_salvar.setBounds(372, 46, 134, 25);
		contentPane.add(btn_salvar);

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
		btn_voltar.setBounds(10, 11, 108, 25);
		contentPane.add(btn_voltar);
		
		JLabel lbl_integrantes_equipe = new JLabel("Integrantes da Equipe");
		lbl_integrantes_equipe.setFont(new Font("Andalus", Font.PLAIN, 18));
		lbl_integrantes_equipe.setBounds(67, 46, 171, 25);
		contentPane.add(lbl_integrantes_equipe);
		
		//BOT�O ADICIONA
		JButton btn_adiciona = new JButton("");
		btn_adiciona.setToolTipText("Adicionar Integrante");
		btn_adiciona.setIcon(new ImageIcon(EQUIPE_T�CNICA.class.getResource("/Imagens/Flexa Verde.jpg")));
		btn_adiciona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				int posicao_table = table_tecnicos_cadastrados.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Adicionar ''"
				+table_tecnicos_cadastrados.getValueAt(posicao_table, 1)+"'' A Equipe T�cnica?",
				"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
										
				if(valida == JOptionPane.YES_OPTION){
					Equipe_T�cnica.Cadastrar();
					((DefaultTableModel) table_tecnicos_cadastrados.getModel()).removeRow(table_tecnicos_cadastrados.getSelectedRow());				
					popular_tables();
				}
				else {
					JOptionPane.showMessageDialog(null, "''"+table_tecnicos_cadastrados.getValueAt(posicao_table, 1)+"'' N�o Foi Adicionado A Equipe T�cnica!");
				}
			}
		});
		btn_adiciona.setForeground(Color.BLACK);
		btn_adiciona.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_adiciona.setBackground(UIManager.getColor("Button.background"));
		btn_adiciona.setBounds(419, 111, 47, 35);
		contentPane.add(btn_adiciona);
		
		//BOT�O REMOVE
		JButton btn_remove = new JButton("");
		btn_remove.setToolTipText("Remover Integrante");
		btn_remove.setIcon(new ImageIcon(EQUIPE_T�CNICA.class.getResource("/Imagens/Flexa Vermelha.jpg")));
		btn_remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		
				int posicao_table = table_integrantes_da_equipe.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Remover ''"
				+table_integrantes_da_equipe.getValueAt(posicao_table, 1)+"'' Da Equipe T�cnica?",
				"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
										
				if(valida == JOptionPane.YES_OPTION){
					Equipe_T�cnica.Excluir();
					((DefaultTableModel) table_integrantes_da_equipe.getModel()).removeRow(table_integrantes_da_equipe.getSelectedRow());
					popular_tables();
				}
				else {
					JOptionPane.showMessageDialog(null, "''"+table_integrantes_da_equipe.getValueAt(posicao_table, 1)+"'' N�o Foi Removido Da Equipe!");
				}
			}
		});
		btn_remove.setForeground(Color.BLACK);
		btn_remove.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_remove.setBackground(UIManager.getColor("Button.background"));
		btn_remove.setBounds(419, 156, 47, 35);
		contentPane.add(btn_remove);
		
		//BOT�O CHAMA TELA DE T�CNICOS
		JButton btn_t�cnicos = new JButton("T\u00E9cnicos");
		btn_t�cnicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new T�CNICOS().setVisible(true);
			}
		});
		btn_t�cnicos.setForeground(Color.BLACK);
		btn_t�cnicos.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_t�cnicos.setBackground(UIManager.getColor("Button.background"));
		btn_t�cnicos.setBounds(596, 46, 140, 25);
		contentPane.add(btn_t�cnicos);
		
		JPanel panel = new JPanel();
		panel.setBounds(67, 46, 161, 25);
		contentPane.add(panel);
		
		JLabel lbl_imagem = new JLabel("");
		lbl_imagem.setFont(new Font("Andalus", Font.PLAIN, 17));
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2� Periodo\\Programa��o Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Lista.jpg"));
		lbl_imagem.setBounds(0, 0, 838, 234);
		contentPane.add(lbl_imagem);
	}		
	//M�TODO POPULAR TABELAS
	public void popular_tables(){

		table_tecnicos_cadastrados.setModel(Equipe_T�cnica.Listar_T�cnicos());
		table_integrantes_da_equipe.setModel(Equipe_T�cnica.Listar_Integrantes_Equipe());
		
	}
}