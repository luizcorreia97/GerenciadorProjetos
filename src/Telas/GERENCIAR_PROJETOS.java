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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.UIManager;

import Banco_de_Dados.Conecta;
import Classes.Pacotes_de_EntregaDAO;
import Classes.ProjetosDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;

import javax.swing.JTextField;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GERENCIAR_PROJETOS extends JFrame {
	
	ProjetosDAO Projetos = new ProjetosDAO();
	Pacotes_de_EntregaDAO Pacotes_de_Entrega = new Pacotes_de_EntregaDAO();
	
	private JPanel contentPane;
	
	private JTable table_justifivativas;
	private JTable table_objetivos;
	private JTable table_stakeholders;
	private JTable table_equipe_tecnica;
	private JTable table_requisitos;
	private JTable table_casos_de_uso;
	private JTable table_riscos;
	private JTable table_premissas;
	private JTable table_restricoes;
	private JTable table_pacotes_de_entrega;

	private JLabel lbl_imagem = new JLabel();
	private JLabel lbl_projetos_cadastrados;
	private JLabel id;
	private JLabel lbl_id;
	private JLabel nome_projeto;
	private JLabel lbl_data_prevista_inicial;
	private JLabel data_prevista_inicio;

	private JScrollPane pane_justificativas;
	private JScrollPane pane_objetivos;
	private JScrollPane pane_stakeholders;
	private JScrollPane pane_equipe_tecnica;
	private JScrollPane pane_premissas;
	private JScrollPane pane_restricoes;
	private JScrollPane pane_entregas;
	private JScrollPane pane_casos_de_uso;
	private JScrollPane pane_riscos;
	private JScrollPane pane_requisitos;
	private JLabel data_real_inicio;
	private JLabel data_prevista_entrega;
	private JLabel lbl_data_prevista_entrega;
	private JLabel data_real_entrega;
	private JLabel custo_total_previsto;
	private JLabel lbl_custo_total_previsto;
	private JPanel panel_2;
	private JButton btn_requisitos;
	private JButton btn_riscos;
	private JButton btn_restricoes;
	private JButton btn_equipe_tecnica;
	private JButton btn_casos_de_uso;
	private JButton btn_premissas;
	private JButton btn_entregas;

	JButton btn_gerente = new JButton("Gerente:");
	JButton btn_stakeholders = new JButton("Stakeholders");
	JButton btn_justificativas = new JButton("Justificativas");
	JButton btn_objetivos = new JButton("Objetivos");

	private JLabel custo_total_real;
	private JPanel panel;
	private JButton btn_ciclo_de_vida;
	private JButton btn_situação;
	JButton btn_entregar_projeto = new JButton("Entregar Projeto");
	JLabel lbl_custo_total_real = new JLabel("0.00");
	
	JComboBox<String> cmb_gp = new JComboBox();
	JComboBox<String> cmb_ciclo = new JComboBox();
	JComboBox<String> cmb_situação = new JComboBox();
	
	private JLayeredPane layeredPane_4;
	private MaskFormatter mascara_data_real_inicio;
	private MaskFormatter mascara_data_real_entrega;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	
	JFormattedTextField txt_data_real_inicio = new JFormattedTextField(mascara_data_real_inicio);
	JFormattedTextField txt_data_real_entrega = new JFormattedTextField(mascara_data_real_entrega);
	private JTextField txt_nome_projeto;
	private JTextField txt_gerente;
	private JTextField txt_ciclo_de_vida;
	private JTextField txt_situação;
	
	int controle_entrega_projeto = 0;
	
	private JButton btn_atualiza_gerente;
	private JButton btn_atualiza_ciclo;
	private JButton btn_atualiza_situação;
	private JButton btn_atualiza_data_real_inicio;
	private JButton btn_atualiza_nome;
	
	public GERENCIAR_PROJETOS(int id_projeto) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					Projetos.setId_projeto(id_projeto);
					Verificar_Situação_Projeto();
					Popular_Campos();
					Popular_Tabelas();	
					Somar_Pacotes();
					ListarTecnicosNoCombo();
					ListarCiclosNoCombo();
					ListarSituacaoNoCombo();
					txt_nome_projeto.requestFocus();
				} catch (Exception e1) {
					System.out.println("Erro Ao Popular Combos");
					e1.printStackTrace();
				}
			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1215, 709);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("GERENCIAR PROJETOS");
		setLocation(80, 30);
		setResizable(false);
		
		try {
			mascara_data_real_inicio = new MaskFormatter("## / ## / ####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			mascara_data_real_entrega = new MaskFormatter("## / ## / ####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		layeredPane_4 = new JLayeredPane();
		layeredPane_4.setBounds(10, 14, 390, 657);
		contentPane.add(layeredPane_4);
		layeredPane_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txt_data_real_inicio.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_data_real_inicio.setBounds(204, 343, 94, 20);
		layeredPane_4.add(txt_data_real_inicio);
		txt_data_real_entrega.setEditable(false);
		txt_data_real_entrega.setBounds(204, 374, 103, 20);
		layeredPane_4.add(txt_data_real_entrega);
		txt_data_real_entrega.setHorizontalAlignment(SwingConstants.CENTER);
		txt_data_real_entrega.setFont(new Font("Andalus", Font.PLAIN, 17));
													
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(410, 14, 789, 657);
		contentPane.add(layeredPane_1);
		
		//TABLE JUSTIFICATIVAS
		pane_justificativas = new JScrollPane();
		pane_justificativas.setBounds(10, 39, 373, 95);
		layeredPane_1.add(pane_justificativas);
		table_justifivativas = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_justifivativas.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_justificativas.setViewportView(table_justifivativas);
		
		//TABLE OBJETIVOS
		pane_objetivos = new JScrollPane();
		pane_objetivos.setBounds(404, 39, 373, 95);
		layeredPane_1.add(pane_objetivos);
		table_objetivos = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_objetivos.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_objetivos.setViewportView(table_objetivos);
		
		//TABLE STAKEHOLDERS		
		pane_stakeholders = new JScrollPane();
		pane_stakeholders.setBounds(10, 167, 373, 89);
		layeredPane_1.add(pane_stakeholders);
		table_stakeholders = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};		
		table_stakeholders.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_stakeholders.setViewportView(table_stakeholders);
		
		//TABLE EQUIPE TÉCNICA		
		pane_equipe_tecnica = new JScrollPane();
		pane_equipe_tecnica.setBounds(404, 167, 375, 89);
		layeredPane_1.add(pane_equipe_tecnica);
		table_equipe_tecnica = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};		
		table_equipe_tecnica.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_equipe_tecnica.setViewportView(table_equipe_tecnica);
		
		//TABLE CASOS DE USO
		pane_casos_de_uso = new JScrollPane();
		pane_casos_de_uso.setBounds(404, 293, 375, 89);
		layeredPane_1.add(pane_casos_de_uso);
		table_casos_de_uso = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_casos_de_uso.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_casos_de_uso.setViewportView(table_casos_de_uso);
		
		//TABLE REQUISITOS
		pane_requisitos = new JScrollPane();
		pane_requisitos.setBounds(10, 293, 373, 89);
		layeredPane_1.add(pane_requisitos);
		table_requisitos = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_requisitos.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_requisitos.setViewportView(table_requisitos);
		
		//TABLE RISCOS
		pane_riscos = new JScrollPane();
		pane_riscos.setBounds(10, 420, 373, 95);
		layeredPane_1.add(pane_riscos);
		table_riscos = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_riscos.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_riscos.setViewportView(table_riscos);
		
		//TABLE RESTRIÇÕES
		pane_restricoes = new JScrollPane();
		pane_restricoes.setBounds(10, 557, 373, 86);
		layeredPane_1.add(pane_restricoes);
		table_restricoes = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_restricoes.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_restricoes.setViewportView(table_restricoes);
		
		//TABLE PACOTES_ENTREGA
		pane_entregas = new JScrollPane();
		pane_entregas.setBounds(404, 557, 373, 86);
		layeredPane_1.add(pane_entregas);
		table_pacotes_de_entrega = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_pacotes_de_entrega.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_entregas.setViewportView(table_pacotes_de_entrega);
		
		//TABLE PREMISSAS
		pane_premissas = new JScrollPane();
		pane_premissas.setBounds(404, 420, 373, 95);
		layeredPane_1.add(pane_premissas);
		table_premissas = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_premissas.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_premissas.setViewportView(table_premissas);
		
		//BOTÃO EQUIPE TÉCNICA
		btn_equipe_tecnica = new JButton("Equipe T\u00E9cnica");
		btn_equipe_tecnica.setToolTipText("Gerenciar Equipe T\u00E9cnica");
		btn_equipe_tecnica.setForeground(Color.BLACK);
		btn_equipe_tecnica.setBackground(UIManager.getColor("Button.background"));
		btn_equipe_tecnica.setBounds(523, 138, 157, 25);
		layeredPane_1.add(btn_equipe_tecnica);
		btn_equipe_tecnica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new EQUIPE_TÉCNICA(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_equipe_tecnica.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO CASOS DE USO
		btn_casos_de_uso = new JButton("Casos de Uso");
		btn_casos_de_uso.setToolTipText("Gerenciar Casos de Uso");
		btn_casos_de_uso.setForeground(Color.BLACK);
		btn_casos_de_uso.setBackground(UIManager.getColor("Button.background"));
		btn_casos_de_uso.setBounds(523, 267, 149, 25);
		layeredPane_1.add(btn_casos_de_uso);
		btn_casos_de_uso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CASOS_DE_USO(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_casos_de_uso.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_stakeholders.setToolTipText("Gerenciar Stakeholders");
		
		//BOTÃO STAKEHOLDERS
		btn_stakeholders.setForeground(Color.BLACK);
		btn_stakeholders.setBackground(UIManager.getColor("Button.background"));
		btn_stakeholders.setBounds(126, 138, 144, 25);
		layeredPane_1.add(btn_stakeholders);
		btn_stakeholders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new STAKEHOLDERS(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_stakeholders.setFont(new Font("Andalus", Font.PLAIN, 20));
							
		//BOTÃO REQUISITOS
		btn_requisitos = new JButton("Requisitos");
		btn_requisitos.setToolTipText("Gerenciar Requisitos");
		btn_requisitos.setForeground(Color.BLACK);
		btn_requisitos.setBackground(UIManager.getColor("Button.background"));
		btn_requisitos.setBounds(126, 267, 144, 25);
		layeredPane_1.add(btn_requisitos);
		btn_requisitos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new REQUISITOS(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_requisitos.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO RISCOS
		btn_riscos = new JButton("Riscos");
		btn_riscos.setToolTipText("Gerenciar Riscos");
		btn_riscos.setForeground(Color.BLACK);
		btn_riscos.setBackground(UIManager.getColor("Button.background"));
		btn_riscos.setBounds(126, 393, 144, 25);
		layeredPane_1.add(btn_riscos);
		btn_riscos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RISCOS(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_riscos.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO RESTRIÇÕES
		btn_restricoes = new JButton("Restri\u00E7\u00F5es");
		btn_restricoes.setToolTipText("Gerenciar Restri\u00E7\u00F5es");
		btn_restricoes.setForeground(Color.BLACK);
		btn_restricoes.setBackground(UIManager.getColor("Button.background"));
		btn_restricoes.setBounds(126, 530, 144, 25);
		layeredPane_1.add(btn_restricoes);
		btn_restricoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RESTRIÇÕES(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_restricoes.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO PACOTES DE ENTREGA
		btn_entregas = new JButton("Pacotes");
		btn_entregas.setToolTipText("Gerenciar Pacotes");
		btn_entregas.setForeground(Color.BLACK);
		btn_entregas.setBackground(UIManager.getColor("Button.background"));
		btn_entregas.setBounds(515, 530, 136, 25);
		layeredPane_1.add(btn_entregas);
		btn_entregas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int numeros_csu = table_casos_de_uso.getRowCount()+1;
				new PESQUISAR_PACOTES(Projetos.getId_projeto(), id_projeto, numeros_csu).setVisible(true);
			}
		});
		btn_entregas.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO PREMISSAS
		btn_premissas = new JButton("Premissas");
		btn_premissas.setToolTipText("Gerenciar Premissas");
		btn_premissas.setForeground(Color.BLACK);
		btn_premissas.setBackground(UIManager.getColor("Button.background"));
		btn_premissas.setBounds(515, 393, 149, 25);
		layeredPane_1.add(btn_premissas);
		btn_premissas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new PREMISSAS(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_premissas.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_justificativas.setToolTipText("Gerenciar Justificativas");
		
		//BOTÃO JUSTIFICATIVAS
		btn_justificativas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new JUSTIFICATIVAS(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_justificativas.setForeground(Color.BLACK);
		btn_justificativas.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_justificativas.setBackground(UIManager.getColor("Button.background"));
		btn_justificativas.setBounds(126, 11, 144, 25);
		layeredPane_1.add(btn_justificativas);
		btn_objetivos.setToolTipText("Gerenciar Objetivos");
		
		//BOTÃO OBJETIVOS
		btn_objetivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OBJETIVOS(Projetos.getId_projeto()).setVisible(true);
			}
		});
		btn_objetivos.setForeground(Color.BLACK);
		btn_objetivos.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_objetivos.setBackground(UIManager.getColor("Button.background"));
		btn_objetivos.setBounds(523, 11, 157, 25);
		layeredPane_1.add(btn_objetivos);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(74, 577, 250, 69);
		layeredPane_4.add(layeredPane);
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btn_entregar_projeto.setToolTipText("Entregar Projeto");
		btn_entregar_projeto.setIcon(new ImageIcon(GERENCIAR_PROJETOS.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/capslock-icon.png")));
		
		//BOTÃO ENTREGAR
		btn_entregar_projeto.setBounds(22, 11, 212, 47);
		layeredPane.add(btn_entregar_projeto);
		btn_entregar_projeto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(controle_entrega_projeto == 0){
					
					JOptionPane.showMessageDialog(null, "Verifique a Data de Entrega e Clique Novamente no Botão 'Entregar Projeto'! =)");
					txt_data_real_entrega.setEditable(true);
					txt_data_real_entrega.setText(sdf.format(new Date()));
					txt_data_real_entrega.requestFocus();
					controle_entrega_projeto = 1;
					//Bloquear_Projeto_Ao_Entregar();
				}
				else{
					int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Entregar o Projeto ''"+txt_nome_projeto.getText()+"'' ?",
					"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
					
					//Bloquear_Projeto_Ao_Entregar();
					
					if(valida == JOptionPane.YES_OPTION){
						Projetos.setSituação("Entregue");
						Projetos.setData_real_entrega(txt_data_real_entrega.getText());
						Projetos.Entregar();
						dispose();
						new PESQUISAR_PROJETOS().setVisible(true);								
					}
					else {							
						JOptionPane.showMessageDialog(null, "Projeto "+Projetos.getNome_projeto()+" Não Foi Entregue! =(");
						Popular_Campos();
					}	
				}			
			}
		});
		btn_entregar_projeto.setForeground(Color.BLACK);
		btn_entregar_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_entregar_projeto.setBackground(UIManager.getColor("Button.background"));
		
		//BOTÃO CICLO DE VIDA
		btn_ciclo_de_vida = new JButton("Ciclo de Vida:");
		btn_ciclo_de_vida.setToolTipText("Gerenciar Ciclos de Vida");
		btn_ciclo_de_vida.setBounds(10, 467, 127, 17);
		layeredPane_4.add(btn_ciclo_de_vida);
		btn_ciclo_de_vida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CICLOS_DE_VIDA().setVisible(true);
			}
		});
		btn_ciclo_de_vida.setForeground(Color.BLACK);
		btn_ciclo_de_vida.setFont(new Font("Andalus", Font.PLAIN, 16));
		btn_ciclo_de_vida.setBackground(UIManager.getColor("Button.background"));
		
		custo_total_previsto = new JLabel("Custo Total Previsto R$:");
		custo_total_previsto.setBounds(57, 294, 177, 14);
		layeredPane_4.add(custo_total_previsto);
		custo_total_previsto.setHorizontalAlignment(SwingConstants.RIGHT);
		custo_total_previsto.setForeground(Color.BLACK);
		custo_total_previsto.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		custo_total_real = new JLabel("Custo Total Real R$:");
		custo_total_real.setBounds(56, 405, 152, 14);
		layeredPane_4.add(custo_total_real);
		custo_total_real.setHorizontalAlignment(SwingConstants.RIGHT);
		custo_total_real.setForeground(Color.BLACK);
		custo_total_real.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		lbl_custo_total_previsto = new JLabel("");
		lbl_custo_total_previsto.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_custo_total_previsto.setBounds(244, 294, 111, 14);
		layeredPane_4.add(lbl_custo_total_previsto);
		lbl_custo_total_previsto.setForeground(Color.BLACK);
		lbl_custo_total_previsto.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		//BOTÃO SITUAÇÃO
		btn_situação = new JButton("Situa\u00E7\u00E3o:");
		btn_situação.setToolTipText("Gerenciar Situa\u00E7\u00F5es");
		btn_situação.setBounds(10, 522, 127, 17);
		layeredPane_4.add(btn_situação);
		btn_situação.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SITUAÇÕES().setVisible(true);
			}
		});
		btn_situação.setForeground(Color.BLACK);
		btn_situação.setFont(new Font("Andalus", Font.PLAIN, 17));
		btn_situação.setBackground(UIManager.getColor("Button.background"));
																
		data_prevista_inicio = new JLabel("Data Prevista Inicio:");
		data_prevista_inicio.setBounds(63, 240, 145, 14);
		layeredPane_4.add(data_prevista_inicio);
		data_prevista_inicio.setHorizontalAlignment(SwingConstants.RIGHT);
		data_prevista_inicio.setForeground(Color.BLACK);
		data_prevista_inicio.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		btn_atualiza_data_real_inicio = new JButton("");
		btn_atualiza_data_real_inicio.setToolTipText("Atualizar Data Real de Inicio do Projeto");
		btn_atualiza_data_real_inicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Atualizar a Data Real de Inicio do Projeto Para ''"+txt_data_real_inicio.getText()+"'' ?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
				
				if(valida == JOptionPane.YES_OPTION){							
					
					Projetos.setId_projeto(id_projeto);
					Projetos.setData_real_inicio(txt_data_real_inicio.getText());
					
					Projetos.Atualizar_Data_Real_Inicio_Projeto();
				}
				else {							
					JOptionPane.showMessageDialog(null, "Data Real de Inicio do Projeto Não Foi Atualizada! =(");
				}
			}
		});
		btn_atualiza_data_real_inicio.setIcon(new ImageIcon(GERENCIAR_PROJETOS.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		btn_atualiza_data_real_inicio.setForeground(Color.BLACK);
		btn_atualiza_data_real_inicio.setFont(new Font("Andalus", Font.PLAIN, 16));
		btn_atualiza_data_real_inicio.setBackground(UIManager.getColor("Button.background"));
		btn_atualiza_data_real_inicio.setBounds(317, 343, 39, 20);
		layeredPane_4.add(btn_atualiza_data_real_inicio);
		
		data_real_inicio = new JLabel("Data Real Inicio:");
		data_real_inicio.setBounds(57, 347, 137, 14);
		layeredPane_4.add(data_real_inicio);
		data_real_inicio.setHorizontalAlignment(SwingConstants.RIGHT);
		data_real_inicio.setForeground(Color.BLACK);
		data_real_inicio.setFont(new Font("Andalus", Font.PLAIN, 17));

		lbl_data_prevista_inicial = new JLabel("");
		lbl_data_prevista_inicial.setBounds(218, 240, 115, 14);
		layeredPane_4.add(lbl_data_prevista_inicial);
		lbl_data_prevista_inicial.setForeground(Color.BLACK);
		lbl_data_prevista_inicial.setFont(new Font("Andalus", Font.PLAIN, 17));
				
		data_prevista_entrega = new JLabel("Data Prevista Entrega:");
		data_prevista_entrega.setBounds(63, 266, 157, 19);
		layeredPane_4.add(data_prevista_entrega);
		data_prevista_entrega.setHorizontalAlignment(SwingConstants.RIGHT);
		data_prevista_entrega.setForeground(Color.BLACK);
		data_prevista_entrega.setFont(new Font("Andalus", Font.PLAIN, 17));
						
		lbl_data_prevista_entrega = new JLabel("");
		lbl_data_prevista_entrega.setBounds(228, 268, 117, 14);
		layeredPane_4.add(lbl_data_prevista_entrega);
		lbl_data_prevista_entrega.setForeground(Color.BLACK);
		lbl_data_prevista_entrega.setFont(new Font("Andalus", Font.PLAIN, 17));
								
		data_real_entrega = new JLabel("Data Real Entrega:");
		data_real_entrega.setBounds(57, 375, 137, 25);
		layeredPane_4.add(data_real_entrega);
		data_real_entrega.setHorizontalAlignment(SwingConstants.RIGHT);
		data_real_entrega.setForeground(Color.BLACK);
		data_real_entrega.setFont(new Font("Andalus", Font.PLAIN, 17));
																
		nome_projeto = new JLabel("Nome do Projeto");
		nome_projeto.setBounds(103, 120, 145, 25);
		layeredPane_4.add(nome_projeto);
		nome_projeto.setHorizontalAlignment(SwingConstants.CENTER);
		nome_projeto.setForeground(Color.BLACK);
		nome_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
								
		lbl_projetos_cadastrados = new JLabel("Detalhes do Projeto");
		lbl_projetos_cadastrados.setBounds(10, 74, 370, 25);
		layeredPane_4.add(lbl_projetos_cadastrados);
		lbl_projetos_cadastrados.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_projetos_cadastrados.setForeground(Color.BLACK);
		lbl_projetos_cadastrados.setBackground(Color.BLACK);
		lbl_projetos_cadastrados.setFont(new Font("Andalus", Font.PLAIN, 30));
										
		JButton btn_voltar = new JButton("Voltar");
		btn_voltar.setToolTipText("Voltar Para a Tela Pesquisar Projetos");
		btn_voltar.setBounds(10, 24, 88, 25);
		layeredPane_4.add(btn_voltar);
		btn_voltar.setForeground(Color.BLACK);
		btn_voltar.setBackground(UIManager.getColor("Button.background"));
		btn_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PESQUISAR_PROJETOS().setVisible(true);
				dispose();
			}
		});
		btn_voltar.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		panel_2 = new JPanel();
		panel_2.setBounds(43, 229, 318, 91);
		layeredPane_4.add(panel_2);
		
		panel = new JPanel();
		panel.setBounds(10, 72, 370, 27);
		layeredPane_4.add(panel);
		
		txt_nome_projeto = new JTextField();
		txt_nome_projeto.setHorizontalAlignment(SwingConstants.CENTER);
		txt_nome_projeto.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_nome_projeto.setBounds(10, 145, 324, 20);
		layeredPane_4.add(txt_nome_projeto);
		txt_nome_projeto.setColumns(10);
		
		txt_gerente = new JTextField();
		txt_gerente.setEditable(false);
		txt_gerente.setHorizontalAlignment(SwingConstants.CENTER);
		txt_gerente.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_gerente.setColumns(10);
		txt_gerente.setBounds(114, 169, 266, 20);
		layeredPane_4.add(txt_gerente);
		btn_gerente.setToolTipText("Gerenciar Gerentes");
		
		btn_gerente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TÉCNICOS().setVisible(true);
			}
		});
		btn_gerente.setForeground(Color.BLACK);
		btn_gerente.setFont(new Font("Andalus", Font.PLAIN, 17));
		btn_gerente.setBackground(UIManager.getColor("Button.background"));
		btn_gerente.setBounds(10, 171, 94, 17);
		layeredPane_4.add(btn_gerente);
		
		lbl_custo_total_real.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_custo_total_real.setForeground(Color.BLACK);
		lbl_custo_total_real.setFont(new Font("Andalus", Font.PLAIN, 17));
		lbl_custo_total_real.setBounds(214, 405, 111, 14);
		layeredPane_4.add(lbl_custo_total_real);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(43, 331, 318, 101);
		layeredPane_4.add(panel_1);
		
		txt_ciclo_de_vida = new JTextField();
		txt_ciclo_de_vida.setEditable(false);
		txt_ciclo_de_vida.setHorizontalAlignment(SwingConstants.CENTER);
		txt_ciclo_de_vida.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_ciclo_de_vida.setColumns(10);
		txt_ciclo_de_vida.setBounds(147, 465, 233, 20);
		layeredPane_4.add(txt_ciclo_de_vida);
		
		txt_situação = new JTextField();
		txt_situação.setEditable(false);
		txt_situação.setHorizontalAlignment(SwingConstants.CENTER);
		txt_situação.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_situação.setColumns(10);
		txt_situação.setBounds(147, 522, 233, 20);
		layeredPane_4.add(txt_situação);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(103, 120, 145, 25);
		layeredPane_4.add(panel_3);
	
		btn_atualiza_gerente = new JButton("");
		btn_atualiza_gerente.setToolTipText("Atualizar Gerente do Projeto");
		btn_atualiza_gerente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Atualizar o Gerente do Projeto De ''"+txt_gerente.getText()+"'' Para ''"+cmb_gp.getSelectedItem()+"'' ?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
				
				if(valida == JOptionPane.YES_OPTION){							
					
					Projetos.setId_projeto(id_projeto);
					Projetos.setGerente_projeto(cmb_gp.getSelectedItem().toString());
					
					Projetos.Atualizar_Nome_Gerente();
				}
				else {							
					JOptionPane.showMessageDialog(null, "Gerente do Projeto Não Foi Atualizado! =(");
				}
			}
		});
		btn_atualiza_gerente.setIcon(new ImageIcon(GERENCIAR_PROJETOS.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		btn_atualiza_gerente.setForeground(Color.BLACK);
		btn_atualiza_gerente.setFont(new Font("Andalus", Font.PLAIN, 16));
		btn_atualiza_gerente.setBackground(UIManager.getColor("Button.background"));
		btn_atualiza_gerente.setBounds(341, 192, 39, 19);
		layeredPane_4.add(btn_atualiza_gerente);
		
		btn_atualiza_ciclo = new JButton("");
		btn_atualiza_ciclo.setToolTipText("Atualizar Ciclo de Vida do Projeto");
		btn_atualiza_ciclo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Atualizar o Ciclo de Vida do Projeto De ''"+txt_ciclo_de_vida.getText()+"'' Para ''"+cmb_ciclo.getSelectedItem()+"'' ?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
				
				if(valida == JOptionPane.YES_OPTION){							
					
					Projetos.setId_projeto(id_projeto);
					Projetos.setCiclo_de_vida(cmb_ciclo.getSelectedItem().toString());
					
					Projetos.Atualizar_Ciclo_De_Vida_Projeto();
					
					dispose();
					new PESQUISAR_PROJETOS().setVisible(true);
				}
				else {							
					JOptionPane.showMessageDialog(null, "Ciclo de Vida do Projeto Não Foi Atualizado! =(");
				}
			}
		});
		btn_atualiza_ciclo.setIcon(new ImageIcon(GERENCIAR_PROJETOS.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		btn_atualiza_ciclo.setForeground(Color.BLACK);
		btn_atualiza_ciclo.setFont(new Font("Andalus", Font.PLAIN, 16));
		btn_atualiza_ciclo.setBackground(UIManager.getColor("Button.background"));
		btn_atualiza_ciclo.setBounds(341, 491, 39, 20);
		layeredPane_4.add(btn_atualiza_ciclo);
		
		btn_atualiza_situação = new JButton("");
		btn_atualiza_situação.setToolTipText("Atualizar Situa\u00E7\u00E3o do Projeto");
		btn_atualiza_situação.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Atualizar a Situação do Projeto De ''"+txt_situação.getText()+"'' Para ''"+cmb_situação.getSelectedItem()+"'' ?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
				
				if(valida == JOptionPane.YES_OPTION){							
					
					Projetos.setId_projeto(id_projeto);
					Projetos.setSituação(cmb_situação.getSelectedItem().toString());
					
					Projetos.Atualizar_Situação_Projeto();
					
					dispose();
					new PESQUISAR_PROJETOS().setVisible(true);
				}
				else {							
					JOptionPane.showMessageDialog(null, "Ciclo de Vida do Projeto Não Foi Atualizado! =(");
				}
			}
		});
		btn_atualiza_situação.setIcon(new ImageIcon(GERENCIAR_PROJETOS.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		btn_atualiza_situação.setForeground(Color.BLACK);
		btn_atualiza_situação.setFont(new Font("Andalus", Font.PLAIN, 16));
		btn_atualiza_situação.setBackground(UIManager.getColor("Button.background"));
		btn_atualiza_situação.setBounds(341, 546, 39, 20);
		layeredPane_4.add(btn_atualiza_situação);
		
		btn_atualiza_nome = new JButton("");
		btn_atualiza_nome.setToolTipText("Atualizar Nome do Projeto");
		btn_atualiza_nome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Atualizar o Nome do Projeto Para ''"+txt_nome_projeto.getText()+"'' ?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
				
				if(valida == JOptionPane.YES_OPTION){							
					
					Projetos.setId_projeto(id_projeto);
					Projetos.setNome_projeto(txt_nome_projeto.getText());
					
					Projetos.Atualizar_Nome_Projeto();
				}
				else {							
					JOptionPane.showMessageDialog(null, "Nome do Projeto Não Foi Atualizado! =(");
				}
			}
		});
		btn_atualiza_nome.setIcon(new ImageIcon(GERENCIAR_PROJETOS.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		btn_atualiza_nome.setForeground(Color.BLACK);
		btn_atualiza_nome.setFont(new Font("Andalus", Font.PLAIN, 16));
		btn_atualiza_nome.setBackground(UIManager.getColor("Button.background"));
		btn_atualiza_nome.setBounds(341, 146, 39, 20);
		layeredPane_4.add(btn_atualiza_nome);
		
		cmb_gp.setFont(new Font("Andalus", Font.PLAIN, 17));
		cmb_gp.setBounds(114, 192, 219, 20);
		layeredPane_4.add(cmb_gp);
		cmb_ciclo.setToolTipText("Selecionar Ciclo de Vida");
		
		cmb_ciclo.setFont(new Font("Andalus", Font.PLAIN, 17));
		cmb_ciclo.setBounds(147, 491, 186, 20);
		layeredPane_4.add(cmb_ciclo);
		cmb_situação.setToolTipText("Selecionar Situa\u00E7\u00E3o");
		
		cmb_situação.setFont(new Font("Andalus", Font.PLAIN, 17));
		cmb_situação.setBounds(147, 546, 186, 20);
		layeredPane_4.add(cmb_situação);
				
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Detalhes.jpg"));
		lbl_imagem.setFont(new Font("Andalus", Font.PLAIN, 17));
		lbl_imagem.setBounds(0, 0, 1234, 722);
		contentPane.add(lbl_imagem);
				
		id = new JLabel("Id:");
		id.setBounds(0, 0, 31, 20);
		contentPane.add(id);
		id.setHorizontalAlignment(SwingConstants.RIGHT);
		id.setForeground(Color.BLACK);
		id.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		lbl_id = new JLabel("");
		lbl_id.setBounds(0, 0, 31, 20);
		contentPane.add(lbl_id);
		lbl_id.setForeground(Color.BLACK);
		lbl_id.setFont(new Font("Andalus", Font.PLAIN, 17));
	}
	
	//MÉTODO QUE POPULA OS GERENTES NO COMBO DO GP
    public JComboBox<String> ListarTecnicosNoCombo() throws Exception {
    	try {
    		cmb_gp.removeAllItems();
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT NOME FROM TB_TÉCNICOS WHERE NOME <> (SELECT GERENTE_PROJETO FROM TB_PROJETOS WHERE ID_PROJETO = "+Projetos.getId_projeto()+")";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
	            cmb_gp.addItem(rs.getString("NOME"));
	            }
		} catch (Exception e) {
			return null;
		}
		return cmb_gp;
    }
    
	//MÉTODO QUE POPULA OS CICLOS DE VIDA NO COMBO
    public JComboBox<String> ListarCiclosNoCombo() throws Exception {
    	try {
    		cmb_ciclo.removeAllItems();
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT DESCRIÇÃO FROM TB_CICLOS_DE_VIDA WHERE DESCRIÇÃO <> (SELECT CICLO_DE_VIDA FROM TB_PROJETOS WHERE ID_PROJETO = "+Projetos.getId_projeto()+")";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cmb_ciclo.addItem(rs.getString("DESCRIÇÃO"));
			}
		} catch (Exception e) {
			return null;
		}
		return cmb_ciclo;
    }
    
    //MÉTODO QUE POPULA AS SITUAÇÕES NO COMBO
    public JComboBox<String> ListarSituacaoNoCombo() throws Exception {
    	try {
    		cmb_situação.removeAllItems();
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT DESCRIÇÃO FROM TB_SITUAÇÕES WHERE DESCRIÇÃO <> (SELECT SITUAÇÃO FROM TB_PROJETOS WHERE ID_PROJETO = "+Projetos.getId_projeto()+")";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
	            cmb_situação.addItem(rs.getString("DESCRIÇÃO"));
	        }
		} catch (Exception e) {
			return null;
		}
		return cmb_situação;
    }
    
    //MÉTODO BUSCAR OS DADOS NO BANCO	
  	public static TableModel resutSetToTableModel(ResultSet rs) {
  		try {
  			ResultSetMetaData metaData = rs.getMetaData();
  			int numerocolunas = metaData.getColumnCount();
  			Vector columname = new Vector();
  			for (int column = 0; column < numerocolunas; column++) {
  				columname.addElement(metaData.getColumnLabel(column + 1));
  			}
  			Vector linhas = new Vector();
  			while (rs.next()) {
  				Vector novalinha = new Vector();
  				for (int i = 1; i <= numerocolunas; i++) {
  					novalinha.addElement(rs.getObject(i));
  				}
  				linhas.addElement(novalinha);
  			}
  			return new DefaultTableModel(linhas, columname);
  		} catch (Exception e) {
  			e.printStackTrace();
  			return null;
  		}
  	}
  	public TableModel Lista_Justificativas(){
  		
  		try {
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
  			String sql = "SELECT DESCRIÇÃO FROM TB_JUSTIFICATIVAS WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			return resutSetToTableModel(rs);
  		} catch (Exception e) {
  			return null;
  		}		
  	}
	public TableModel Lista_Objetivos(){
	  		
  		try {
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
  			String sql = "SELECT DESCRIÇÃO FROM TB_OBJETIVOS WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			return resutSetToTableModel(rs);
  		} catch (Exception e) {
  			return null;
  		}		
  	}
	public TableModel Lista_Stakeholders(){
			
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT NOME FROM TB_STAKEHOLDERS WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	public TableModel Lista_Equipe_Técnica(){
			
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT NOME FROM TB_EQUIPE_TÉCNICA WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	public TableModel Lista_Requisitos(){
			
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT RF FROM TB_REQUISITOS WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	public TableModel Lista_Casos_de_Uso(){
			
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT CSU FROM TB_CASOS_DE_USO WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	public TableModel Lista_Riscos(){
			
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT DESCRIÇÃO FROM TB_RISCOS WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	public TableModel Lista_Premissas(){
			
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT DESCRIÇÃO FROM TB_PREMISSAS WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	public TableModel Lista_Restrições(){
			
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT DESCRIÇÃO FROM TB_RESTRIÇÕES WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	public TableModel Lista_Pacotes_de_Entregas(){
			
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT Nº_PACOTE, DESCRIÇÃO, SITUAÇÃO FROM TB_PACOTES_ENTREGA WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	// MÉTODO PARA LISTAR TOAS AS TABELAS
	public void Popular_Tabelas(){
		
		table_justifivativas.setModel(Lista_Justificativas());
		table_objetivos.setModel(Lista_Objetivos());
		table_stakeholders.setModel(Lista_Stakeholders());
		table_equipe_tecnica.setModel(Lista_Equipe_Técnica());
		table_requisitos.setModel(Lista_Requisitos());
		table_casos_de_uso.setModel(Lista_Casos_de_Uso());
		table_riscos.setModel(Lista_Riscos());
		table_premissas.setModel(Lista_Premissas());
		table_restricoes.setModel(Lista_Restrições());
		table_pacotes_de_entrega.setModel(Lista_Pacotes_de_Entregas());
	}
	
	//MÉTODO PRA SETAR NOS LABELS E TEXTS OQ ESTA NO BANCO NO ID DESTE PROJETO ESPECÍFICO
	public void Popular_Campos(){
		
		try {
			
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT *, DATA1 = CONVERT(CHAR,DATA_PREVISTA_INICIO,103), DATA2 = CONVERT(CHAR,DATA_PREVISTA_ENTREGA,103) FROM TB_PROJETOS WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			
			rs.next();
			
			//JOGANDO DIRETO DO BANCO NOS COMPONENTES
			lbl_id.setText(rs.getString("ID_PROJETO"));
			txt_nome_projeto.setText(rs.getString("NOME_PROJETO"));
			txt_gerente.setText(rs.getString("GERENTE_PROJETO"));
			lbl_data_prevista_inicial.setText(rs.getString("DATA1"));
			lbl_data_prevista_entrega.setText(rs.getString("DATA2"));
			lbl_custo_total_previsto.setText(rs.getString("CUSTO_TOTAL_PREVISTO"));
			txt_data_real_inicio.setText(rs.getString("DATA_REAL_INICIO"));
			//txt_data_real_entrega.setText(rs.getString("DATA_REAL_ENTREGA"));
			txt_ciclo_de_vida.setText(rs.getString("CICLO_DE_VIDA"));
			txt_situação.setText(rs.getString("SITUAÇÃO"));
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro Ao Jogar Dados Nos Labels e Texts!");
			e.printStackTrace();
		}
	}
	//MÉTODO PRA SETAR A SOMA DOS PACOTES NO TXT
	public void Somar_Pacotes(){
		
		try {
			
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT SOMA_PACOTES = SUM(CUSTO) FROM TB_PACOTES_ENTREGA WHERE SITUAÇÃO <> 'Cancelado' AND ID_PROJETO = "+Projetos.getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			
			rs.next();
			
			lbl_custo_total_real.setText(rs.getString("SOMA_PACOTES"));
			if(table_pacotes_de_entrega.getRowCount() == 0){
				lbl_custo_total_real.setText("0.00");
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro Ao Jogar Dados Nos Labels e Texts!");
			e.printStackTrace();
		}
	}
	public void Verificar_Situação_Projeto(){
  		
  		try {
			
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Conecta().getConnection();
  			String sql = "SELECT * FROM TB_PROJETOS WHERE ID_PROJETO = "+Projetos.getId_projeto()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			
  			rs.next();
  	  		if((rs.getString("SITUAÇÃO").equals("Entregue"))){									
  	  			Bloquear_Projeto_Entregue();
  	  			txt_data_real_entrega.setText(rs.getString("DATA_REAL_ENTREGA"));
  	  		}
  	  		else if((rs.getString("SITUAÇÃO").equals("Paralisado"))||(rs.getString("SITUAÇÃO").equals("Cancelado"))){
  	  			Bloquear_Projeto_Paralisado_Ou_Cancelado();
  	  		}
  	  		else if((rs.getString("SITUAÇÃO").equals("Viabilidade"))||(rs.getString("SITUAÇÃO").equals("Cadastrado"))){
  	  			Liberar_Componentes();
  	  		}

		} catch (Exception e) {
			// TODO: handle exception
		}  		
  	}
	// MÉTODO QUE MUDA TODOS OS COMPONENTES DA TELA DE TRUE PARA FALSE
	public void Bloquear_Projeto_Ao_Entregar(){
		btn_justificativas.setEnabled(false);
		btn_objetivos.setEnabled(false);
		//btn_atualizar.setEnabled(false);
		btn_casos_de_uso.setEnabled(false);
		btn_ciclo_de_vida.setEnabled(false);
		btn_entregas.setEnabled(false);
		btn_stakeholders.setEnabled(false);
		btn_equipe_tecnica.setEnabled(false);
		btn_premissas.setEnabled(false);
		btn_requisitos.setEnabled(false);
		btn_restricoes.setEnabled(false);
		btn_riscos.setEnabled(false);
		btn_situação.setEnabled(false);
		btn_gerente.setEnabled(false);
		btn_atualiza_ciclo.setEnabled(false);
		btn_atualiza_data_real_inicio.setEnabled(false);
		btn_atualiza_gerente.setEnabled(false);
		btn_atualiza_nome.setEnabled(false);
		btn_atualiza_situação.setEnabled(false);
		txt_nome_projeto.setEditable(false);
		txt_gerente.setEditable(false);
		txt_data_real_inicio.setEditable(false);
		txt_situação.setEditable(false);
		txt_ciclo_de_vida.setEditable(false);
		cmb_gp.setEnabled(false);
		cmb_ciclo.setEnabled(false);
		cmb_situação.setEnabled(false);
		table_casos_de_uso.setEnabled(false);
		table_equipe_tecnica.setEnabled(false);
		table_justifivativas.setEnabled(false);
		table_objetivos.setEnabled(false);
		table_pacotes_de_entrega.setEnabled(false);
		table_premissas.setEnabled(false);
		table_requisitos.setEnabled(false);
		table_restricoes.setEnabled(false);
		table_riscos.setEnabled(false);
		table_stakeholders.setEnabled(false);
	}
	public void Bloquear_Projeto_Entregue(){
		btn_justificativas.setEnabled(false);
		btn_objetivos.setEnabled(false);
		btn_casos_de_uso.setEnabled(false);
		btn_ciclo_de_vida.setEnabled(false);
		btn_entregas.setEnabled(false);
		btn_stakeholders.setEnabled(false);
		btn_equipe_tecnica.setEnabled(false);
		btn_premissas.setEnabled(false);
		btn_requisitos.setEnabled(false);
		btn_restricoes.setEnabled(false);
		btn_riscos.setEnabled(false);
		btn_situação.setEnabled(false);
		btn_gerente.setEnabled(false);
		btn_atualiza_ciclo.setEnabled(false);
		btn_atualiza_data_real_inicio.setEnabled(false);
		btn_atualiza_gerente.setEnabled(false);
		btn_atualiza_nome.setEnabled(false);
		btn_atualiza_situação.setEnabled(false);
		txt_nome_projeto.setEditable(false);
		txt_gerente.setEditable(false);
		txt_data_real_inicio.setEditable(false);
		txt_data_real_entrega.setEditable(false);
		txt_situação.setEditable(false);
		txt_ciclo_de_vida.setEditable(false);
		cmb_gp.setEnabled(false);
		cmb_ciclo.setEnabled(false);
		cmb_situação.setEnabled(false);
		btn_entregar_projeto.setEnabled(false);
		table_casos_de_uso.setEnabled(false);
		table_equipe_tecnica.setEnabled(false);
		table_justifivativas.setEnabled(false);
		table_objetivos.setEnabled(false);
		table_pacotes_de_entrega.setEnabled(false);
		table_premissas.setEnabled(false);
		table_requisitos.setEnabled(false);
		table_restricoes.setEnabled(false);
		table_riscos.setEnabled(false);
		table_stakeholders.setEnabled(false);
	}
	// MÉTODO QUE MUDA TODOS OS COMPONENTES DA TELA DE TRUE PARA FALSE EXCETO
	// O COMBOBOX E BOTÃO RELACIONADO A SITUAÇÃO DO PROJETO
	public void Bloquear_Projeto_Paralisado_Ou_Cancelado(){
		btn_justificativas.setEnabled(false);
		btn_objetivos.setEnabled(false);
		btn_casos_de_uso.setEnabled(false);
		btn_ciclo_de_vida.setEnabled(false);
		btn_entregas.setEnabled(false);
		btn_stakeholders.setEnabled(false);
		btn_equipe_tecnica.setEnabled(false);
		btn_premissas.setEnabled(false);
		btn_requisitos.setEnabled(false);
		btn_restricoes.setEnabled(false);
		btn_riscos.setEnabled(false);
		btn_gerente.setEnabled(false);
		btn_atualiza_ciclo.setEnabled(false);
		btn_atualiza_data_real_inicio.setEnabled(false);
		btn_atualiza_gerente.setEnabled(false);
		btn_atualiza_nome.setEnabled(false);
		txt_nome_projeto.setEditable(false);
		txt_gerente.setEditable(false);
		txt_data_real_inicio.setEditable(false);
		txt_data_real_entrega.setEditable(false);
		txt_situação.setEditable(false);
		txt_ciclo_de_vida.setEditable(false);
		cmb_gp.setEnabled(false);
		cmb_ciclo.setEnabled(false);
		btn_entregar_projeto.setEnabled(false);
		table_casos_de_uso.setEnabled(false);
		table_equipe_tecnica.setEnabled(false);
		table_justifivativas.setEnabled(false);
		table_objetivos.setEnabled(false);
		table_pacotes_de_entrega.setEnabled(false);
		table_premissas.setEnabled(false);
		table_requisitos.setEnabled(false);
		table_restricoes.setEnabled(false);
		table_riscos.setEnabled(false);
		table_stakeholders.setEnabled(false);
	}
	public void Liberar_Componentes(){	
		btn_justificativas.setEnabled(true);
		btn_objetivos.setEnabled(true);
		btn_casos_de_uso.setEnabled(true);
		btn_ciclo_de_vida.setEnabled(true);
		btn_entregas.setEnabled(true);
		btn_stakeholders.setEnabled(true);
		btn_equipe_tecnica.setEnabled(true);
		btn_premissas.setEnabled(true);
		btn_requisitos.setEnabled(true);
		btn_restricoes.setEnabled(true);
		btn_riscos.setEnabled(true);
		btn_situação.setEnabled(true);
		btn_gerente.setEnabled(true);
		btn_atualiza_ciclo.setEnabled(true);
		btn_atualiza_data_real_inicio.setEnabled(true);
		btn_atualiza_gerente.setEnabled(true);
		btn_atualiza_nome.setEnabled(true);
		btn_atualiza_situação.setEnabled(true);
		txt_nome_projeto.setEditable(true);
		txt_gerente.setEditable(true);
		txt_data_real_inicio.setEditable(true);
		//txt_data_real_entrega.setEditable(true);
		txt_situação.setEditable(true);
		txt_ciclo_de_vida.setEditable(true);
		cmb_gp.setEnabled(true);
		cmb_ciclo.setEnabled(true);
		cmb_situação.setEnabled(true);
		btn_entregar_projeto.setEnabled(true);
		table_casos_de_uso.setEnabled(true);
		table_equipe_tecnica.setEnabled(true);
		table_justifivativas.setEnabled(true);
		table_objetivos.setEnabled(true);
		table_pacotes_de_entrega.setEnabled(true);
		table_premissas.setEnabled(true);
		table_requisitos.setEnabled(true);
		table_restricoes.setEnabled(true);
		table_riscos.setEnabled(true);
		table_stakeholders.setEnabled(true);
	}
}