package Telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import Classes.ProjetosDAO;
import Telas.INICIAL;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.UIManager;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.JRadioButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PESQUISAR_PROJETOS extends JFrame {
	
	ProjetosDAO Projetos = new ProjetosDAO();

	private JPanel contentPane;

	private JTable table;
	private DefaultTableModel modelo;

	private JLabel lbl_imagem = new JLabel();
	private JLabel lbl_projetos_cadastrados;

	private JScrollPane pane_projetos;
	private JLayeredPane layeredPane;
	private JLabel label;
	private JRadioButton rd_id;
	private JRadioButton rd_nome;
	private JRadioButton rd_gerente;
	private JLayeredPane layeredPane_1;
	private JLabel label_1;
	private JRadioButton rd_crescente;
	private JRadioButton rd_decrescente;

	public PESQUISAR_PROJETOS() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				popular_table();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 820, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ButtonGroup botoes_ordena_tipo = new ButtonGroup();		
		ButtonGroup botoes_ordem = new ButtonGroup();

		setTitle("PESQUISAR PROJETOS");
		//setLocation(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		modelo = new DefaultTableModel(null, new Object[] {"ID","NOME DO PROJETO","SITUAÇÃO"});

		JButton btn_voltar = new JButton("Voltar");
		btn_voltar.setToolTipText("Voltar Para Tela Inicial");
		btn_voltar.setForeground(Color.BLACK);
		btn_voltar.setBackground(UIManager.getColor("Button.background"));
		btn_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new INICIAL().setVisible(true);			
				dispose();
			}
		});
		btn_voltar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_voltar.setBounds(10, 24, 92, 20);
		contentPane.add(btn_voltar);

		lbl_projetos_cadastrados = new JLabel("Lista de Projetos Salvos");
		lbl_projetos_cadastrados.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_projetos_cadastrados.setForeground(Color.WHITE);
		lbl_projetos_cadastrados.setBackground(Color.BLACK);
		lbl_projetos_cadastrados.setFont(new Font("Andalus", Font.PLAIN, 30));
		lbl_projetos_cadastrados.setBounds(230, 9, 304, 43);
		contentPane.add(lbl_projetos_cadastrados);

		JButton btn_ver = new JButton("Visualizar");
		btn_ver.setToolTipText("Visualizar Projeto");
		btn_ver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				try {
					
					int posicao = table.getSelectedRow();
					Projetos.setId_projeto((int) table.getValueAt(posicao, 0));
					
					new GERENCIAR_PROJETOS(Projetos.getId_projeto()).setVisible(true);
					dispose();
				
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Selecione Um Projeto Por Favor!","ATENÇÃO!!",2);
				}
				
			}
		});
		btn_ver.setForeground(Color.BLACK);
		btn_ver.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_ver.setBackground(UIManager.getColor("Button.background"));
		btn_ver.setBounds(623, 327, 137, 25);
		contentPane.add(btn_ver);
		
		JButton btn_novo_projeto = new JButton("Novo");
		btn_novo_projeto.setToolTipText("Novo Projeto");
		btn_novo_projeto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int id_projeto = table.getRowCount()+1;
				
				new NOVO_PROJETO(id_projeto).setVisible(true);
				dispose();
			}
		});
		btn_novo_projeto.setForeground(Color.BLACK);
		btn_novo_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_novo_projeto.setBackground(UIManager.getColor("Button.background"));
		btn_novo_projeto.setBounds(53, 327, 137, 25);
		contentPane.add(btn_novo_projeto);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(10, 55, 794, 78);
		contentPane.add(layeredPane);
		
		rd_id = new JRadioButton("Id");
		rd_id.setToolTipText("Ordenar Pelo Id");
		rd_id.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Projetos.setOrdena_tipo("ID");
				popular_table();
			}
		});
		rd_id.setSelected(true);
		rd_id.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_id.setBounds(48, 48, 46, 23);
		layeredPane.add(rd_id);
		botoes_ordena_tipo.add(rd_id);
		
		rd_nome = new JRadioButton("Nome");
		rd_nome.setToolTipText("Ordenar Pelo Nome");
		rd_nome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Projetos.setOrdena_tipo("NOME");
				popular_table();
			}
		});
		rd_nome.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_nome.setBounds(187, 48, 66, 23);
		layeredPane.add(rd_nome);
		botoes_ordena_tipo.add(rd_nome);
		
		rd_gerente = new JRadioButton("Gerente");
		rd_gerente.setToolTipText("Ordenar Pelo Gerente");
		rd_gerente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Projetos.setOrdena_tipo("GERENTE");
				popular_table();
			}
		});
		rd_gerente.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_gerente.setBounds(344, 48, 78, 23);
		layeredPane.add(rd_gerente);
		botoes_ordena_tipo.add(rd_gerente);
		
		JRadioButton rd_ciclo = new JRadioButton("Ciclo");
		rd_ciclo.setToolTipText("Ordenar Pelo Ciclo");
		rd_ciclo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Projetos.setOrdena_tipo("CICLO");
				popular_table();
			}
		});
		rd_ciclo.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_ciclo.setBounds(517, 48, 66, 23);
		layeredPane.add(rd_ciclo);
		botoes_ordena_tipo.add(rd_ciclo);
		
		JRadioButton rd_situação = new JRadioButton("Situa\u00E7\u00E3o");
		rd_situação.setToolTipText("Ordenar Pela Situa\u00E7\u00E3o");
		rd_situação.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Projetos.setOrdena_tipo("SITUAÇÃO");
				popular_table();
			}
		});
		rd_situação.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_situação.setBounds(652, 48, 94, 23);
		layeredPane.add(rd_situação);
		botoes_ordena_tipo.add(rd_situação);
		
		label = new JLabel("ORDENAR POR");
		label.setBounds(315, 11, 128, 20);
		layeredPane.add(label);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(229, 315, 356, 37);
		contentPane.add(layeredPane_1);
		
		label_1 = new JLabel("EM ORDEM");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Andalus", Font.PLAIN, 17));
		label_1.setBounds(10, 10, 86, 17);
		layeredPane_1.add(label_1);
		
		rd_crescente = new JRadioButton("Crescente");
		rd_crescente.setToolTipText("Ordenar Em Ordem Crescente");
		rd_crescente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Projetos.setOrdena_ordem("ASC");
				popular_table();
			}
		});
		rd_crescente.setSelected(true);
		rd_crescente.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_crescente.setBounds(122, 7, 100, 23);
		layeredPane_1.add(rd_crescente);
		botoes_ordem.add(rd_crescente);
		
		rd_decrescente = new JRadioButton("Descrescente");
		rd_decrescente.setToolTipText("Ordenar Em Ordem Decrescente");
		rd_decrescente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Projetos.setOrdena_ordem("DESC");
				popular_table();
			}
		});
		rd_decrescente.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_decrescente.setBounds(227, 7, 115, 23);
		layeredPane_1.add(rd_decrescente);
		botoes_ordem.add(rd_decrescente);
		
		pane_projetos = new JScrollPane();
		pane_projetos.setBounds(10, 144, 794, 163);
		contentPane.add(pane_projetos);
		
		// Criar Modelo de Tabela
		table = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table.setFont(new Font("Andalus", Font.PLAIN, 15));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NOME DO PROJETO", "SITUA\u00C7\u00C3O"
			}
		));
		pane_projetos.setViewportView(table);
		
		lbl_imagem.setFont(new Font("Andalus", Font.PLAIN, 17));
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Lista.jpg"));
		lbl_imagem.setBounds(0, 0, 814, 375);
		contentPane.add(lbl_imagem);
		
		//DEFINIR A LARGURA DESEJADA DE CADA COLUNA DA TABLE
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(300); 
		table.getColumnModel().getColumn(2).setPreferredWidth(100); 
	}
  //MÉTODO POPULAR TABLE COM OS DADOS
  	public void popular_table(){
  		
  		table.setModel(Projetos.Listar());
  	}
}