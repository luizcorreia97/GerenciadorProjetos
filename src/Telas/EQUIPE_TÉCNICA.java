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
import Classes.Equipe_TécnicaDAO;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class EQUIPE_TÉCNICA extends JFrame {
	
	Equipe_TécnicaDAO Equipe_Técnica = new Equipe_TécnicaDAO();

	private JPanel contentPane;
	private JScrollPane pane_lista_técnicos_cadastrados;
	private JTable table_tecnicos_cadastrados;
	
	private JScrollPane pane_lista_integrantes_da_equipe;
	private JTable table_integrantes_da_equipe;
	
	public EQUIPE_TÉCNICA(int id_projeto) {
		setType(Type.POPUP);
		
		//MÉTODO ASSIM QUE INICIALIZA A TELA EQUIPE TÉCNICA
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				Equipe_Técnica.setId_projeto(id_projeto);
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
		
		pane_lista_técnicos_cadastrados = new JScrollPane();
		pane_lista_técnicos_cadastrados.setBounds(490, 82, 338, 137);
		getContentPane().add(pane_lista_técnicos_cadastrados);
		
		pane_lista_integrantes_da_equipe = new JScrollPane();
		pane_lista_integrantes_da_equipe.setBounds(10, 82, 382, 137);
		getContentPane().add(pane_lista_integrantes_da_equipe);

		setTitle("GERENCIAR EQUIPE T\u00C9CNICA");
		//setLocation(450, 150);
		setLocationRelativeTo(null);
		setResizable(false);

		//BLOQUEIA AO CLICAR NA TABLE TÉCNICOS CADASTRADOS
		table_tecnicos_cadastrados = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		//PEGA O QUE FOI SELECIONADO NA TABLE TÉCNICOS CADASTRADOS E JOGA NAS VARIÁVEIS
		table_tecnicos_cadastrados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int posicao = table_tecnicos_cadastrados.getSelectedRow();
				Equipe_Técnica.setId_projeto(id_projeto);
				Equipe_Técnica.setId_tecnico(Integer.parseInt(table_tecnicos_cadastrados.getValueAt(posicao, 0).toString()));
				Equipe_Técnica.setNome_tecnico(table_tecnicos_cadastrados.getValueAt(posicao, 1).toString());
				Equipe_Técnica.setCargo_tecnico(table_tecnicos_cadastrados.getValueAt(posicao, 2).toString());
				
			}
		});
		table_tecnicos_cadastrados.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_lista_técnicos_cadastrados.setViewportView(table_tecnicos_cadastrados);
		
		//BLOQUEIA AO CLICAR NA TABLE TÉCNICOS CADASTRADOS
		table_integrantes_da_equipe = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};

		//PEGA O QUE FOI SELECIONADO NA TABLE INTEGRANTES DA EQUIPE E JOGA NAS VARIÁVEIS
		table_integrantes_da_equipe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int posicao = table_integrantes_da_equipe.getSelectedRow();
				Equipe_Técnica.setId_projeto(id_projeto);
				Equipe_Técnica.setId_tecnico(Integer.parseInt(table_integrantes_da_equipe.getValueAt(posicao, 0).toString()));
				
			}
		});
		table_integrantes_da_equipe.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_lista_integrantes_da_equipe.setViewportView(table_integrantes_da_equipe);
		
		//BOTÃO "SALVAR"
		JButton btn_salvar = new JButton("Salvar");
		btn_salvar.setToolTipText("Salvar Equipe T\u00E9cnica");
		btn_salvar.setForeground(Color.BLACK);
		btn_salvar.setBackground(UIManager.getColor("Button.background"));
		btn_salvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Equipe Técnica Salva Com Sucesso!");
				dispose();
			}
		});
		btn_salvar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_salvar.setBounds(372, 46, 134, 25);
		contentPane.add(btn_salvar);

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
		btn_voltar.setBounds(10, 11, 108, 25);
		contentPane.add(btn_voltar);
		
		JLabel lbl_integrantes_equipe = new JLabel("Integrantes da Equipe");
		lbl_integrantes_equipe.setFont(new Font("Andalus", Font.PLAIN, 18));
		lbl_integrantes_equipe.setBounds(67, 46, 171, 25);
		contentPane.add(lbl_integrantes_equipe);
		
		//BOTÃO ADICIONA
		JButton btn_adiciona = new JButton("");
		btn_adiciona.setToolTipText("Adicionar Integrante");
		btn_adiciona.setIcon(new ImageIcon(EQUIPE_TÉCNICA.class.getResource("/Imagens/Flexa Verde.jpg")));
		btn_adiciona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				int posicao_table = table_tecnicos_cadastrados.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Adicionar ''"
				+table_tecnicos_cadastrados.getValueAt(posicao_table, 1)+"'' A Equipe Técnica?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
										
				if(valida == JOptionPane.YES_OPTION){
					Equipe_Técnica.Cadastrar();
					((DefaultTableModel) table_tecnicos_cadastrados.getModel()).removeRow(table_tecnicos_cadastrados.getSelectedRow());				
					popular_tables();
				}
				else {
					JOptionPane.showMessageDialog(null, "''"+table_tecnicos_cadastrados.getValueAt(posicao_table, 1)+"'' Não Foi Adicionado A Equipe Técnica!");
				}
			}
		});
		btn_adiciona.setForeground(Color.BLACK);
		btn_adiciona.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_adiciona.setBackground(UIManager.getColor("Button.background"));
		btn_adiciona.setBounds(419, 111, 47, 35);
		contentPane.add(btn_adiciona);
		
		//BOTÃO REMOVE
		JButton btn_remove = new JButton("");
		btn_remove.setToolTipText("Remover Integrante");
		btn_remove.setIcon(new ImageIcon(EQUIPE_TÉCNICA.class.getResource("/Imagens/Flexa Vermelha.jpg")));
		btn_remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		
				int posicao_table = table_integrantes_da_equipe.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Remover ''"
				+table_integrantes_da_equipe.getValueAt(posicao_table, 1)+"'' Da Equipe Técnica?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
										
				if(valida == JOptionPane.YES_OPTION){
					Equipe_Técnica.Excluir();
					((DefaultTableModel) table_integrantes_da_equipe.getModel()).removeRow(table_integrantes_da_equipe.getSelectedRow());
					popular_tables();
				}
				else {
					JOptionPane.showMessageDialog(null, "''"+table_integrantes_da_equipe.getValueAt(posicao_table, 1)+"'' Não Foi Removido Da Equipe!");
				}
			}
		});
		btn_remove.setForeground(Color.BLACK);
		btn_remove.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_remove.setBackground(UIManager.getColor("Button.background"));
		btn_remove.setBounds(419, 156, 47, 35);
		contentPane.add(btn_remove);
		
		//BOTÃO CHAMA TELA DE TÉCNICOS
		JButton btn_técnicos = new JButton("T\u00E9cnicos");
		btn_técnicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TÉCNICOS().setVisible(true);
			}
		});
		btn_técnicos.setForeground(Color.BLACK);
		btn_técnicos.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_técnicos.setBackground(UIManager.getColor("Button.background"));
		btn_técnicos.setBounds(596, 46, 140, 25);
		contentPane.add(btn_técnicos);
		
		JPanel panel = new JPanel();
		panel.setBounds(67, 46, 161, 25);
		contentPane.add(panel);
		
		JLabel lbl_imagem = new JLabel("");
		lbl_imagem.setFont(new Font("Andalus", Font.PLAIN, 17));
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Lista.jpg"));
		lbl_imagem.setBounds(0, 0, 838, 234);
		contentPane.add(lbl_imagem);
	}		
	//MÉTODO POPULAR TABELAS
	public void popular_tables(){

		table_tecnicos_cadastrados.setModel(Equipe_Técnica.Listar_Técnicos());
		table_integrantes_da_equipe.setModel(Equipe_Técnica.Listar_Integrantes_Equipe());
		
	}
}