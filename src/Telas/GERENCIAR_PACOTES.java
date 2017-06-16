package Telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultComboBoxModel;

import Banco_de_Dados.Conecta;
import Classes.Casos_de_UsoDAO;
import Classes.Pacotes_de_EntregaDAO;
import Classes.ProjetosDAO;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GERENCIAR_PACOTES extends JFrame {
	
	ProjetosDAO Projetos = new ProjetosDAO();
	Pacotes_de_EntregaDAO Pacotes_de_Entrega = new Pacotes_de_EntregaDAO();
	Casos_de_UsoDAO Casos_de_Uso = new Casos_de_UsoDAO();

	private JPanel contentPane;
	private JScrollPane pane_lista_csu;
	private JTable table_csu;
	
	private JScrollPane pane_lista_csu_entrega;
	private JTable table_csu_entrega;
	
	private JTextField txt_descricao;
	private JLabel lbl_imagem = new JLabel();
	private JTextField txt_custo;
	private JTextField txt_nome_projeto;
	private JTextField txt_gerente_projeto;
	JLabel lbl_num_pacote = new JLabel("");
	JComboBox<String> cmb_fase = new JComboBox();
	
	int controle_entrega_pacote = 0;
	int valida_cadastro = 0;

	private int id;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private int id_projeto;
	public int getId_projeto() {return id_projeto;}
	public void setId_projeto(int id_projeto) {this.id_projeto = id_projeto;}
	
	private int nº_pacote;
	public int getNº_pacote() {return nº_pacote;}
	public void setNº_pacote(int nº_pacote) {this.nº_pacote = nº_pacote;}		
	
	private int id_csu;
	public int getId_csu() {return id_csu;}
	public void setId_csu(int id_csu) {this.id_csu = id_csu;}
	
	private String csu;
	public String getCsu() {return csu;}
	public void setCsu(String csu) {this.csu = csu;}
	
	JButton btn_atualizar = new JButton("Atualizar");
	JButton btn_entregar = new JButton("Entregar");
	JButton btn_fase = new JButton("Fase:");
	JButton btn_csu = new JButton("Casos de Uso");
	JButton btn_adiciona = new JButton("");
	JButton btn_remove = new JButton("");
	
	private MaskFormatter mascara_data_cadastro;
	private MaskFormatter mascara_data_previsao_entrega;
	private MaskFormatter mascara_data_entrega;
	
	JFormattedTextField txt_data_cadastro = new JFormattedTextField(mascara_data_cadastro);
	JFormattedTextField txt_data_previsao_entrega = new JFormattedTextField(mascara_data_previsao_entrega);
	JFormattedTextField txt_data_entrega = new JFormattedTextField(mascara_data_entrega);
	private JTextField txt_fase;
	private JTextField txt_ciclo;

	public GERENCIAR_PACOTES(int id_projeto, int num_pacote) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					txt_descricao.requestFocus();
					Pacotes_de_Entrega.setId_projeto(id_projeto);
					Pacotes_de_Entrega.setNº_pacote(num_pacote);
					lbl_num_pacote.setText(Integer.toString(Pacotes_de_Entrega.getNº_pacote()));
					Verificar_Situação_Pacote();
					Popular_Campos_Projeto();
					Popular_Campos_Pacote();
					popular_tables();
					ListarFaseNoCombo();
				} catch (Exception e1) {
					System.out.println("Erro Ao Popular Combo Fases");
					e1.printStackTrace();
				}
			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 823, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("GERENCIAR PACOTES");
		//setLocation(450, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		
		try {
			mascara_data_cadastro = new MaskFormatter("## / ## / ####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			mascara_data_previsao_entrega = new MaskFormatter("## / ## / ####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			mascara_data_entrega = new MaskFormatter("## / ## / ####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(10, 129, 585, 117);
		contentPane.add(layeredPane_1);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_2.setBounds(620, 33, 186, 203);
		contentPane.add(layeredPane_2);
		
		txt_data_cadastro.setEditable(false);
		txt_data_cadastro.setBounds(170, 67, 127, 20);
		layeredPane_1.add(txt_data_cadastro);
		txt_data_cadastro.setHorizontalAlignment(SwingConstants.CENTER);
		txt_data_cadastro.setFont(new Font("Andalus", Font.PLAIN, 18));
		
		pane_lista_csu = new JScrollPane();
		pane_lista_csu.setBounds(423, 283, 383, 107);
		getContentPane().add(pane_lista_csu);
		
		pane_lista_csu_entrega = new JScrollPane();
		pane_lista_csu_entrega.setBounds(10, 283, 346, 107);
		getContentPane().add(pane_lista_csu_entrega);

		// Criar Modelo de Tabela CSU
		table_csu = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_csu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int posicao = table_csu.getSelectedRow();
				
				Casos_de_Uso.setId_projeto(Pacotes_de_Entrega.getId_projeto());
				Casos_de_Uso.setNº_pacote(Pacotes_de_Entrega.getNº_pacote());
				Casos_de_Uso.setId(Integer.parseInt(table_csu.getValueAt(posicao, 0).toString()));
				Casos_de_Uso.setCSU(table_csu.getValueAt(posicao, 1).toString());
			}
		});
		table_csu.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_lista_csu.setViewportView(table_csu);
		
		// Criar Modelo de Tabela CSU Entregas
		table_csu_entrega = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_csu_entrega.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int posicao = table_csu_entrega.getSelectedRow();
				
				Casos_de_Uso.setId_projeto(Pacotes_de_Entrega.getId_projeto());
				Casos_de_Uso.setNº_pacote(Pacotes_de_Entrega.getNº_pacote());
				Casos_de_Uso.setId(Integer.parseInt(table_csu_entrega.getValueAt(posicao, 0).toString()));
				Casos_de_Uso.setCSU(table_csu_entrega.getValueAt(posicao, 1).toString());
			}
		});
		table_csu_entrega.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_lista_csu_entrega.setViewportView(table_csu_entrega);

		JButton btn_voltar = new JButton("Voltar");
		btn_voltar.setForeground(Color.BLACK);
		btn_voltar.setBackground(UIManager.getColor("Button.background"));
		btn_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PESQUISAR_PACOTES(id_projeto, id_projeto, num_pacote).setVisible(true);
				dispose();
			}
		});
		btn_voltar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_voltar.setBounds(10, 11, 106, 22);
		contentPane.add(btn_voltar);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(10, 38, 585, 82);
		contentPane.add(layeredPane);
		
		JLabel lbl_nome_projeto = new JLabel("Nome do Projeto:");
		lbl_nome_projeto.setBounds(10, 6, 146, 16);
		layeredPane.add(lbl_nome_projeto);
		lbl_nome_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JLabel lblNomeDoGerente = new JLabel("Nome do Gerente do Projeto:");
		lblNomeDoGerente.setBounds(11, 33, 243, 16);
		layeredPane.add(lblNomeDoGerente);
		lblNomeDoGerente.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 6, 152, 16);
		layeredPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 33, 243, 16);
		layeredPane.add(panel_2);
		
		txt_nome_projeto = new JTextField();
		txt_nome_projeto.setEditable(false);
		txt_nome_projeto.setHorizontalAlignment(SwingConstants.CENTER);
		txt_nome_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_nome_projeto.setColumns(10);
		txt_nome_projeto.setBounds(167, 0, 408, 22);
		layeredPane.add(txt_nome_projeto);
		
		txt_gerente_projeto = new JTextField();
		txt_gerente_projeto.setEditable(false);
		txt_gerente_projeto.setHorizontalAlignment(SwingConstants.CENTER);
		txt_gerente_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_gerente_projeto.setColumns(10);
		txt_gerente_projeto.setBounds(259, 30, 316, 19);
		layeredPane.add(txt_gerente_projeto);
		
		JLabel lbl_ciclo = new JLabel("Ciclo de Vida:");
		lbl_ciclo.setFont(new Font("Andalus", Font.PLAIN, 20));
		lbl_ciclo.setBounds(11, 56, 121, 16);
		layeredPane.add(lbl_ciclo);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 56, 121, 16);
		layeredPane.add(panel);
		
		txt_ciclo = new JTextField();
		txt_ciclo.setHorizontalAlignment(SwingConstants.CENTER);
		txt_ciclo.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_ciclo.setEditable(false);
		txt_ciclo.setColumns(10);
		txt_ciclo.setBounds(142, 54, 249, 19);
		layeredPane.add(txt_ciclo);
		
		btn_fase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FASES().setVisible(true);
			}
		});
		btn_fase.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_fase.setBounds(10, 90, 78, 20);
		layeredPane_1.add(btn_fase);
		
		JLabel lblNDoPacote = new JLabel("N\u00BA do Pacote:");
		lblNDoPacote.setBounds(10, 11, 150, 20);
		layeredPane_1.add(lblNDoPacote);
		lblNDoPacote.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		lbl_num_pacote.setFont(new Font("Andalus", Font.PLAIN, 20));
		lbl_num_pacote.setBounds(125, 11, 25, 27);
		layeredPane_1.add(lbl_num_pacote);
		
		JLabel lbl_descricao = new JLabel("Descri\u00E7\u00E3o:");
		lbl_descricao.setBounds(10, 38, 90, 20);
		layeredPane_1.add(lbl_descricao);
		lbl_descricao.setFont(new Font("Andalus", Font.PLAIN, 20));

		JLabel lbl_data_cadastro_entrega = new JLabel(
				"Data de Cadastro:");
		lbl_data_cadastro_entrega.setBounds(10, 66, 150, 20);
		layeredPane_1.add(lbl_data_cadastro_entrega);
		lbl_data_cadastro_entrega.setForeground(Color.BLACK);
		lbl_data_cadastro_entrega.setBackground(Color.WHITE);
		lbl_data_cadastro_entrega.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 11, 150, 20);
		layeredPane_1.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 38, 90, 20);
		layeredPane_1.add(panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(10, 66, 150, 20);
		layeredPane_1.add(panel_6);
		
		txt_descricao = new JTextField();
		txt_descricao.setBounds(111, 38, 464, 22);
		layeredPane_1.add(txt_descricao);
		txt_descricao.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_descricao.setHorizontalAlignment(SwingConstants.CENTER);
		txt_descricao.setColumns(10);
		
		cmb_fase.setFont(new Font("Andalus", Font.PLAIN, 16));
		cmb_fase.setBounds(374, 90, 188, 22);
		layeredPane_1.add(cmb_fase);
		
		txt_fase = new JTextField();
		txt_fase.setEditable(false);
		txt_fase.setHorizontalAlignment(SwingConstants.CENTER);
		txt_fase.setFont(new Font("Andalus", Font.PLAIN, 18));
		txt_fase.setColumns(10);
		txt_fase.setBounds(95, 91, 269, 19);
		layeredPane_1.add(txt_fase);
		
		btn_csu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new CASOS_DE_USO(id_projeto).setVisible(true);
			}
		});
		btn_csu.setForeground(Color.BLACK);
		btn_csu.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_csu.setBackground(UIManager.getColor("Button.background"));
		btn_csu.setBounds(533, 257, 155, 22);
		contentPane.add(btn_csu);
		
		JLabel lbl_csu_entrega = new JLabel("CSU Relacionados Ao Pacote");
		lbl_csu_entrega.setForeground(Color.BLACK);
		lbl_csu_entrega.setFont(new Font("Andalus", Font.PLAIN, 20));
		lbl_csu_entrega.setBackground(Color.WHITE);
		lbl_csu_entrega.setBounds(59, 247, 247, 25);
		contentPane.add(lbl_csu_entrega);
		btn_remove.setToolTipText("Remover CSU");
		
		btn_remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int posicao_table = table_csu_entrega.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Remover ''"
				+table_csu_entrega.getValueAt(posicao_table, 1)+"'' Do Pacote ''"+Casos_de_Uso.getNº_pacote()+"''?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
										
				if(valida == JOptionPane.YES_OPTION){
					Verificar_Ciclo_Pra_Remover();
					((DefaultTableModel) table_csu_entrega.getModel()).removeRow(table_csu_entrega.getSelectedRow());
					popular_tables();
				}
				else {
					JOptionPane.showMessageDialog(null, "''"+table_csu_entrega.getValueAt(posicao_table, 2)+"'' Não Foi Removido Do Pacote ''"+Casos_de_Uso.getNº_pacote()+"''!");
				}
			}
		});
		btn_remove.setIcon(new ImageIcon(GERENCIAR_PACOTES.class.getResource("/Imagens/Flexa Vermelha.jpg")));
		
		btn_remove.setForeground(Color.BLACK);
		btn_remove.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_remove.setBackground(UIManager.getColor("Button.background"));
		btn_remove.setBounds(366, 338, 47, 35);
		contentPane.add(btn_remove);
		btn_adiciona.setToolTipText("Adicionar CSU");
		
		btn_adiciona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int posicao_table = table_csu.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Adicionar ''"
				+table_csu.getValueAt(posicao_table, 1)+"'' Ao Pacote ''"+Pacotes_de_Entrega.getNº_pacote()+"''?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
										
				if(valida == JOptionPane.YES_OPTION){
					Verificar_Ciclo_Pra_Adicionar();
					((DefaultTableModel) table_csu.getModel()).removeRow(table_csu.getSelectedRow());				
					popular_tables();
				}
				else {
					JOptionPane.showMessageDialog(null, "''"+table_csu.getValueAt(posicao_table, 2)+"'' Não Foi Adicionado Ao Pacote ''"+Casos_de_Uso.getNº_pacote()+"''!");
				}
			}
		});
		btn_adiciona.setIcon(new ImageIcon(GERENCIAR_PACOTES.class.getResource("/Imagens/Flexa Verde.jpg")));
		
		btn_adiciona.setForeground(Color.BLACK);
		btn_adiciona.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_adiciona.setBackground(UIManager.getColor("Button.background"));
		btn_adiciona.setBounds(366, 296, 47, 35);
		contentPane.add(btn_adiciona);
				
		JLabel lblDataDePreviso = new JLabel("Previs\u00E3o Entrega");
		lblDataDePreviso.setBounds(15, 88, 155, 22);
		layeredPane_2.add(lblDataDePreviso);
		lblDataDePreviso.setForeground(Color.BLACK);
		lblDataDePreviso.setFont(new Font("Andalus", Font.PLAIN, 20));
		lblDataDePreviso.setBackground(Color.WHITE);
		btn_atualizar.setToolTipText("Atualizar Pacote");

		btn_atualizar.setBounds(15, 163, 146, 25);
		layeredPane_2.add(btn_atualizar);
		btn_atualizar.setForeground(Color.BLACK);
		btn_atualizar.setBackground(UIManager.getColor("Button.background"));
		btn_atualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Verificar_Ciclo_E_CSU_Selecionados();
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Atualizar o Pacote ''"
						+Pacotes_de_Entrega.getNº_pacote()+"'' ?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Pacotes_de_Entrega.setNº_pacote(Integer.parseInt(lbl_num_pacote.getText()));
						Pacotes_de_Entrega.setDescrição(txt_descricao.getText());
						Pacotes_de_Entrega.setData_cadastro(txt_data_cadastro.getText());
						Pacotes_de_Entrega.setFase(cmb_fase.getSelectedItem().toString());
						Pacotes_de_Entrega.setCusto(Float.parseFloat(txt_custo.getText()));
						Pacotes_de_Entrega.setData_prevista_entrega(txt_data_previsao_entrega.getText());
				
						if(valida == JOptionPane.YES_OPTION){							
							
							Pacotes_de_Entrega.Atualizar();
							new PESQUISAR_PACOTES(id_projeto, id_projeto, num_pacote).setVisible(true);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "Pacote "+Pacotes_de_Entrega.getNº_pacote()+" Não Foi Atualizado! =(");
						}				
				}
			});
			btn_atualizar.setFont(new Font("Andalus", Font.PLAIN, 20));
								
		JLabel custo_do_pacote = new JLabel("Custo do Pacote R$");
		custo_do_pacote.setBounds(10, 24, 160, 16);
		layeredPane_2.add(custo_do_pacote);
		custo_do_pacote.setForeground(Color.BLACK);
		custo_do_pacote.setFont(new Font("Andalus", Font.PLAIN, 20));
		custo_do_pacote.setBackground(Color.WHITE);
		
		txt_custo = new JTextField();
		txt_custo.setBounds(20, 51, 141, 22);
		layeredPane_2.add(txt_custo);
		txt_custo.setHorizontalAlignment(SwingConstants.CENTER);
		txt_custo.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_custo.setColumns(10);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(10, 24, 160, 16);
		layeredPane_2.add(panel_8);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBounds(15, 88, 140, 22);
		layeredPane_2.add(panel_9);
		
		txt_data_previsao_entrega.setHorizontalAlignment(SwingConstants.CENTER);
		txt_data_previsao_entrega.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_data_previsao_entrega.setBounds(25, 121, 122, 20);
		layeredPane_2.add(txt_data_previsao_entrega);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBounds(59, 251, 237, 21);
		contentPane.add(panel_10);
		
		JLayeredPane layeredPane_3 = new JLayeredPane();
		layeredPane_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_3.setBounds(59, 401, 531, 45);
		contentPane.add(layeredPane_3);
		btn_entregar.setToolTipText("Entregar Pacote");
		
		btn_entregar.setBounds(362, 11, 146, 25);
		layeredPane_3.add(btn_entregar);
		btn_entregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(controle_entrega_pacote == 0){
					
					int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Entregar o Pacote ''"
							+Pacotes_de_Entrega.getNº_pacote()+"'' ?",
							"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
												
							if(valida == JOptionPane.YES_OPTION){						
								
								JOptionPane.showMessageDialog(null, "Por Favor Verifique A Data de Entrega!");
								Bloquear_Pacote_Ao_Entregar();
								txt_data_entrega.setEditable(true);
								SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
								txt_data_entrega.setText(sdf.format(new Date()));
								txt_data_entrega.requestFocus();
								
							}
							else {							
								JOptionPane.showMessageDialog(null, "Projeto "+Projetos.getNome_projeto()+" Não Foi Entregue! =(");
								//Popular_Campos();
							}
							controle_entrega_pacote = 1;
				}
				else{

					Pacotes_de_Entrega.setData_real_entrega(txt_data_entrega.getText());
					Pacotes_de_Entrega.setSituação("Entregue");
					Pacotes_de_Entrega.Entregar();
					
					dispose();
					new PESQUISAR_PACOTES(id_projeto, id_projeto, num_pacote).setVisible(true);
				}
			}
		});
		btn_entregar.setForeground(Color.BLACK);
		btn_entregar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_entregar.setBackground(UIManager.getColor("Button.background"));
		txt_data_entrega.setEditable(false);
		
		txt_data_entrega.setBounds(193, 14, 124, 20);
		layeredPane_3.add(txt_data_entrega);
		txt_data_entrega.setHorizontalAlignment(SwingConstants.CENTER);
		txt_data_entrega.setFont(new Font("Andalus", Font.PLAIN, 18));
			
		JLabel lblDataDeEntrega = new JLabel("Data de Entrega:");
		lblDataDeEntrega.setBounds(37, 13, 146, 19);
		layeredPane_3.add(lblDataDeEntrega);
		lblDataDeEntrega.setForeground(Color.BLACK);
		lblDataDeEntrega.setFont(new Font("Andalus", Font.PLAIN, 20));
		lblDataDeEntrega.setBackground(Color.WHITE);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(37, 11, 138, 21);
		layeredPane_3.add(panel_7);
		
		lbl_imagem.setBounds(0, 0, 817, 457);
		contentPane.add(lbl_imagem);
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Lista.jpg"));
	}
	
	//MÉTODO QUE POPULA AS FASES NO COMBO
    public JComboBox<String> ListarFaseNoCombo() throws Exception {
    	try {
    		cmb_fase.removeAllItems();
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();

			String sql = "SELECT DESCRIÇÃO FROM TB_FASES WHERE DESCRIÇÃO <> (SELECT FASE FROM TB_PACOTES_ENTREGA WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+" AND Nº_PACOTE = "+Pacotes_de_Entrega.getNº_pacote()+")";stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
	            cmb_fase.addItem(rs.getString("DESCRIÇÃO"));
	            cmb_fase.updateUI(); // ISTO ATUALIZA O JCOMBOBOX
	            }
		} catch (Exception e) {
			return null;
		}
		return cmb_fase;
    }
    
	//MÉTODO POPULAR TABLE COM OS DADOS
	public void popular_tables(){
		table_csu.setModel(Pacotes_de_Entrega.ListaCasos_de_Uso());
		table_csu_entrega.setModel(Pacotes_de_Entrega.ListaCasos_de_Uso_Entrega());
	}
	
	//MÉTODO PRA SETAR NOS LABELS E TEXTS OQ ESTA NO BANCO NO ID DESTE PROJETO ESPECÍFICO
  	public void Popular_Campos_Projeto(){
  		
  		try {
  			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT ID_PROJETO, NOME_PROJETO, GERENTE_PROJETO, CICLO_DE_VIDA FROM TB_PROJETOS WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			
  			rs.next();
  			
  			//JOGANDO DIRETO DO BANCO NOS COMPONENTES
  			txt_nome_projeto.setText(rs.getString("NOME_PROJETO"));
  			txt_gerente_projeto.setText(rs.getString("GERENTE_PROJETO"));
  			txt_ciclo.setText(rs.getString("CICLO_DE_VIDA"));
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, "Erro Ao Jogar Dados Nos Labels e Texts PROJETO!");
  			e.printStackTrace();
  		}
  	}
  	
  	//MÉTODO PRA SETAR NOS LABELS E TEXTS OQ ESTA NO BANCO NO ID DESTE PROJETO ESPECÍFICO
  	public void Popular_Campos_Pacote(){
  		
  		try {
  			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT * FROM TB_PACOTES_ENTREGA WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+" AND Nº_PACOTE = "+Pacotes_de_Entrega.getNº_pacote()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			
  			rs.next();
  			
  			//JOGANDO DIRETO DO BANCO NOS COMPONENTES
  			txt_descricao.setText(rs.getString("DESCRIÇÃO"));
  			txt_data_cadastro.setText(rs.getString("DATA_CADASTRO"));
  			txt_fase.setText(rs.getString("FASE"));
  			txt_custo.setText(rs.getString("CUSTO"));
  			txt_data_previsao_entrega.setText(rs.getString("DATA_PREVISTA_ENTREGA"));
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, "Erro Ao Jogar Dados Nos Labels e Texts PACOTE!");
  			e.printStackTrace();
  		}
  	}
  	public void Verificar_Situação_Pacote(){
  		
  		try {
			
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Conecta().getConnection();
  			String sql = "SELECT SITUAÇÃO FROM TB_PACOTES_ENTREGA WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+" AND Nº_PACOTE = "+Pacotes_de_Entrega.getNº_pacote()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			
  			rs.next();

  	  		if((rs.getString("SITUAÇÃO").equals("Entregue"))){
  	  			Bloquear_Pacote_Entregue();
  	  		}

		} catch (Exception e) {
			// TODO: handle exception
		}  		
  	}
  	
  	//BLOQUEAR BOTÕES
  	
  	public void Bloquear_Pacote_Entregue(){
		btn_atualizar.setEnabled(false);
		btn_entregar.setEnabled(false);
		btn_fase.setEnabled(false);
		btn_csu.setEnabled(false);
		btn_adiciona.setEnabled(false);
		btn_remove.setEnabled(false);
		txt_descricao.setEditable(false);
		txt_custo.setEditable(false);
		txt_data_previsao_entrega.setEditable(false);
		txt_data_entrega.setEditable(false);
		cmb_fase.setEnabled(false);
		table_csu.setEnabled(false);
		table_csu_entrega.setEnabled(false);
	}
  	public void Bloquear_Pacote_Ao_Entregar(){
  		btn_atualizar.setEnabled(false);
		//btn_entregar.setEnabled(false);
		btn_fase.setEnabled(false);
		btn_csu.setEnabled(false);
		btn_adiciona.setEnabled(false);
		btn_remove.setEnabled(false);
		txt_descricao.setEditable(false);
		txt_custo.setEditable(false);
		txt_data_previsao_entrega.setEditable(false);
		//txt_data_entrega.setEditable(false);
		cmb_fase.setEnabled(false);
		table_csu.setEnabled(false);
		table_csu_entrega.setEnabled(false);
  	}
public void Verificar_Ciclo_Pra_Adicionar(){
  		
  		try {
			
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Conecta().getConnection();
  			String sql = "SELECT CICLO_DE_VIDA FROM TB_PROJETOS WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			
  			rs.next();
  	  		if(rs.getString("CICLO_DE_VIDA").equals("Cascata")){									
  	  			Casos_de_Uso.Alterar_CSU_Com_Pacote_Ciclo_Cascata();
  	  		}
  	  		else{
  	  			Casos_de_Uso.Alterar_CSU_Com_Pacote_Normal();
  	  		}

		} catch (Exception e) {
			// TODO: handle exception
		}  		
  	}
  	public void Verificar_Ciclo_Pra_Remover(){
  		
  		try {
			
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Conecta().getConnection();
  			String sql = "SELECT CICLO_DE_VIDA FROM TB_PROJETOS WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			
  			rs.next();
  	  		if(rs.getString("CICLO_DE_VIDA").equals("Cascata")){	
  	  			Casos_de_Uso.Alterar_CSU_Sem_Pacote_Ciclo_Cascata();
  	  		}
  	  		else{
  	  			Casos_de_Uso.Alterar_CSU_Sem_Pacote_Normal();
  	  		}

		} catch (Exception e) {
			// TODO: handle exception
		}  		
  	}	
  	public void Verificar_Ciclo_E_CSU_Selecionados(){
  		valida_cadastro = 0;
  		try {
			
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Conecta().getConnection();
  			String sql = "SELECT CICLO_DE_VIDA FROM TB_PROJETOS WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			
  			rs.next();
  	  		if(rs.getString("CICLO_DE_VIDA").equals("Cascata")){	
  	  		if(table_csu.getRowCount()>0){
  	  			JOptionPane.showMessageDialog(null, "Por Favor Adicione Todos Os CSU Ao Seu Pacote! =)");
  	    		valida_cadastro = 1;
  	  		}
  	  		}
  	  		else{
  	  		if(table_csu_entrega.getRowCount()==0){
  	  		JOptionPane.showMessageDialog(null, "Por Favor Adicione Ao Menos Um CSU Ao Seu Pacote! =)");
  	  			valida_cadastro = 1;
  	  		}
  	  	}

		} catch (Exception e) {
			// TODO: handle exception
		}  		
  	}
}