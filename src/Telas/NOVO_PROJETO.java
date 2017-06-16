package Telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;

import Banco_de_Dados.Conecta;
import Banco_de_Dados.Conectar_Banco;
import Classes.ProjetosDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFormattedTextField;

import java.awt.Toolkit;

public class NOVO_PROJETO extends JFrame {

	ProjetosDAO Projetos = new ProjetosDAO();
	
	private JPanel contentPane;
	private JLabel lbl_imagem = new JLabel("");
	private JTextField txt_nome_projeto;
	private JTextField txt_custo_total_previsto;
	JComboBox cmb_gp = new JComboBox();
	JComboBox cmb_ciclo = new JComboBox();
	JComboBox cmb_situação = new JComboBox();
	private MaskFormatter mascara_data_prevista_inicio;
	private MaskFormatter mascara_data_prevista_entrega;
	
	JLabel lbl_id = new JLabel("");

	public NOVO_PROJETO(int id_projeto) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				try {				
					Projetos.setId_projeto(id_projeto);
					lbl_id.setText(Integer.toString(id_projeto));
					ListarTecnicosNoCombo();
					ListarCiclosNoCombo();
					ListarSituacaoNoCombo();
					txt_nome_projeto.requestFocus();
				} catch (Exception e) {
					System.out.println("Erro Ao Popular Combos");
					e.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setTitle("NOVO PROJETO");
		setBounds(100, 100, 536, 487);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setLocation(450, 100);
		setResizable(false);
		
		try {
			mascara_data_prevista_inicio = new MaskFormatter("## / ## / ####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			mascara_data_prevista_entrega = new MaskFormatter("## / ## / ####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(10, 11, 510, 437);
		contentPane.add(layeredPane);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(26, 69, 459, 81);
		layeredPane.add(layeredPane_1);
		
		JButton btn_tecnicos = new JButton("Gerente do Projeto:");
		btn_tecnicos.setToolTipText("Gerenciar T\u00E9cnicos");
		btn_tecnicos.setBounds(10, 50, 163, 20);
		layeredPane_1.add(btn_tecnicos);
		btn_tecnicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TÉCNICOS().setVisible(true);
			}
		});
		btn_tecnicos.setFont(new Font("Andalus", Font.PLAIN, 17));
		cmb_gp.setToolTipText("Selecionar Gerente do Projeto");
		cmb_gp.setBounds(181, 50, 189, 20);
		layeredPane_1.add(cmb_gp);
		cmb_gp.setFont(new Font("Andalus", Font.PLAIN, 17));
		
				JLabel lbl_nome_projeto = new JLabel("Nome do Projeto:");
				lbl_nome_projeto.setBounds(10, 22, 127, 14);
				layeredPane_1.add(lbl_nome_projeto);
				lbl_nome_projeto.setForeground(Color.BLACK);
				lbl_nome_projeto.setHorizontalAlignment(SwingConstants.LEFT);
				lbl_nome_projeto.setFont(new Font("Andalus", Font.PLAIN, 17));
				
						txt_nome_projeto = new JTextField();
						txt_nome_projeto.setBounds(134, 19, 315, 20);
						layeredPane_1.add(txt_nome_projeto);
						txt_nome_projeto.setHorizontalAlignment(SwingConstants.CENTER);
						txt_nome_projeto.setFont(new Font("Andalus", Font.PLAIN, 17));
						txt_nome_projeto.setColumns(10);
						
						JPanel panel = new JPanel();
						panel.setBounds(10, 22, 114, 14);
						layeredPane_1.add(panel);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_2.setBounds(111, 161, 287, 64);
		layeredPane.add(layeredPane_2);
		
		JFormattedTextField txt_data_prevista_inicio = new JFormattedTextField(sdf.format(new Date()));
		txt_data_prevista_inicio.setBounds(170, 8, 103, 20);
		layeredPane_2.add(txt_data_prevista_inicio);
		txt_data_prevista_inicio.setHorizontalAlignment(SwingConstants.CENTER);
		txt_data_prevista_inicio.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		JFormattedTextField txt_data_prevista_entrega = new JFormattedTextField(mascara_data_prevista_entrega);
		txt_data_prevista_entrega.setBounds(170, 36, 103, 20);
		layeredPane_2.add(txt_data_prevista_entrega);
		txt_data_prevista_entrega.setHorizontalAlignment(SwingConstants.CENTER);
		txt_data_prevista_entrega.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		JLabel lbl_data_prevista_inicio = new JLabel("Data Prevista Inicio:");
		lbl_data_prevista_inicio.setBounds(10, 11, 150, 14);
		layeredPane_2.add(lbl_data_prevista_inicio);
		lbl_data_prevista_inicio.setForeground(Color.BLACK);
		lbl_data_prevista_inicio.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_data_prevista_inicio.setFont(new Font("Andalus", Font.PLAIN, 17));
		
				JLabel lbl_data_prevista_entrega = new JLabel("Data Prevista Entrega:");
				lbl_data_prevista_entrega.setBounds(10, 39, 151, 17);
				layeredPane_2.add(lbl_data_prevista_entrega);
				lbl_data_prevista_entrega.setForeground(Color.BLACK);
				lbl_data_prevista_entrega.setHorizontalAlignment(SwingConstants.RIGHT);
				lbl_data_prevista_entrega.setFont(new Font("Andalus", Font.PLAIN, 17));
				
				JPanel panel_2 = new JPanel();
				panel_2.setBounds(10, 8, 142, 20);
				layeredPane_2.add(panel_2);
				
				JPanel panel_3 = new JPanel();
				panel_3.setBounds(10, 36, 150, 20);
				layeredPane_2.add(panel_3);
		
		JLayeredPane layeredPane_3 = new JLayeredPane();
		layeredPane_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_3.setBounds(71, 236, 353, 36);
		layeredPane.add(layeredPane_3);
		
		txt_custo_total_previsto = new JTextField();
		txt_custo_total_previsto.setBounds(185, 9, 147, 20);
		layeredPane_3.add(txt_custo_total_previsto);
		txt_custo_total_previsto.setHorizontalAlignment(SwingConstants.CENTER);
		txt_custo_total_previsto.setFont(new Font("Andalus", Font.PLAIN, 17));
		txt_custo_total_previsto.setColumns(10);
		
		JLabel lbl_custo_total_previsto = new JLabel("Custo Total Previsto R$:");
		lbl_custo_total_previsto.setBounds(10, 11, 165, 14);
		layeredPane_3.add(lbl_custo_total_previsto);
		lbl_custo_total_previsto.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_custo_total_previsto.setForeground(Color.BLACK);
		lbl_custo_total_previsto.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 9, 165, 16);
		layeredPane_3.add(panel_4);
		
		JLayeredPane layeredPane_4 = new JLayeredPane();
		layeredPane_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_4.setBounds(64, 283, 365, 64);
		layeredPane.add(layeredPane_4);
		cmb_ciclo.setToolTipText("Selecionar Ciclo de Vida");
		cmb_ciclo.setBounds(165, 11, 190, 20);
		layeredPane_4.add(cmb_ciclo);
		
		cmb_ciclo.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		JButton btn_situação = new JButton("Situa\u00E7\u00E3o:");
		btn_situação.setToolTipText("Gerenciar Situa\u00E7\u00F5es");
		btn_situação.setBounds(10, 37, 145, 20);
		layeredPane_4.add(btn_situação);
		btn_situação.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SITUAÇÕES().setVisible(true);
			}
		});
		btn_situação.setFont(new Font("Andalus", Font.PLAIN, 17));
		cmb_situação.setToolTipText("Selecionar Situa\u00E7\u00E3o");
		cmb_situação.setBounds(165, 37, 190, 20);
		layeredPane_4.add(cmb_situação);
		
		cmb_situação.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		JButton btn_ciclo = new JButton("C\u00EDclo de Vida:");
		btn_ciclo.setToolTipText("Gerenciar Ciclos de Vida");
		btn_ciclo.setBounds(10, 11, 145, 20);
		layeredPane_4.add(btn_ciclo);
		btn_ciclo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CICLOS_DE_VIDA().setVisible(true);
			}
		});
		btn_ciclo.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		JLayeredPane layeredPane_5 = new JLayeredPane();
		layeredPane_5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_5.setBounds(128, 365, 218, 52);
		layeredPane.add(layeredPane_5);
		
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setToolTipText("Cadastrar Projeto");
		btn_cadastrar.setBounds(31, 11, 163, 26);
		layeredPane_5.add(btn_cadastrar);
		btn_cadastrar.setIcon(new ImageIcon(NOVO_PROJETO.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar o Projeto "
						+txt_nome_projeto.getText()+"?",
						"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Projetos.setNome_projeto(txt_nome_projeto.getText());
						Projetos.setGerente_projeto(cmb_gp.getSelectedItem().toString());
						Projetos.setData_prevista_inicio(txt_data_prevista_inicio.getText());
						Projetos.setData_prevista_entrega(txt_data_prevista_entrega.getText());
						Projetos.setCusto_total_previsto(Float.parseFloat(txt_custo_total_previsto.getText()));
						Projetos.setCiclo_de_vida(cmb_ciclo.getSelectedItem().toString());
						Projetos.setSituação(cmb_situação.getSelectedItem().toString());
				
						if(valida == JOptionPane.YES_OPTION){							
							
							Projetos.Cadastrar();

				  			dispose();
							new PESQUISAR_PROJETOS().setVisible(true);
						}
						else {
							JOptionPane.showMessageDialog(null, "Projeto "+Projetos.getNome_projeto()+" Não Foi Cadastrado! =(");
							txt_nome_projeto.setText(null);
							txt_data_prevista_inicio.setText(null);
							txt_data_prevista_entrega.setText(null);
							txt_custo_total_previsto.setText(null);
							txt_nome_projeto.requestFocus();
						}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 17));
						
						JLayeredPane layeredPane_6 = new JLayeredPane();
						layeredPane_6.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
						layeredPane_6.setBounds(50, 21, 379, 37);
						layeredPane.add(layeredPane_6);
						
								JButton btn_voltar = new JButton("Voltar");
								btn_voltar.setToolTipText("Voltar Para Tela 'Lista de Projetos'");
								btn_voltar.setBounds(10, 11, 92, 20);
								layeredPane_6.add(btn_voltar);
								btn_voltar.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										new PESQUISAR_PROJETOS().setVisible(true);
										dispose();
									}
								});
								btn_voltar.setFont(new Font("Andalus", Font.PLAIN, 17));
								
										JLabel lblCadastroDeProjetos = new JLabel("Cadastro de Projetos");
										lblCadastroDeProjetos.setBounds(140, 6, 221, 25);
										layeredPane_6.add(lblCadastroDeProjetos);
										lblCadastroDeProjetos.setForeground(Color.BLACK);
										lblCadastroDeProjetos.setFont(new Font("Andalus", Font.PLAIN, 26));
										lblCadastroDeProjetos.setBackground(Color.BLACK);
										
										JPanel panel_1 = new JPanel();
										panel_1.setBounds(140, 6, 221, 25);
										layeredPane_6.add(panel_1);
				
				lbl_imagem.setBounds(0, 0, 532, 460);
				contentPane.add(lbl_imagem);
				lbl_imagem.setForeground(Color.WHITE);
				lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Cadastro.jpg"));
				
				JLabel id = new JLabel("Id:");
				id.setForeground(Color.BLACK);
				id.setHorizontalAlignment(SwingConstants.RIGHT);
				id.setFont(new Font("Andalus", Font.PLAIN, 17));
				id.setBounds(137, 53, 31, 14);
				contentPane.add(id);
				
						lbl_id.setHorizontalAlignment(SwingConstants.CENTER);
						lbl_id.setForeground(Color.BLACK);
						lbl_id.setFont(new Font("Andalus", Font.PLAIN, 17));
						lbl_id.setBounds(178, 53, 31, 14);
						contentPane.add(lbl_id);
						
						JPanel panel_7 = new JPanel();
						panel_7.setBounds(151, 47, 58, 20);
						contentPane.add(panel_7);
	}
	//MÉTODO QUE POPULA OS TÉNICOS NO COMBO DO GP
    public JComboBox<String> ListarTecnicosNoCombo() throws Exception {
    	try {
    		cmb_gp.removeAllItems();
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT NOME FROM TB_TÉCNICOS";
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
  //MÉTODO QUE POPULA OS CICLOS NO COMBO
    public JComboBox<String> ListarCiclosNoCombo() throws Exception {
    	try {
    		cmb_ciclo.removeAllItems();
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT DESCRIÇÃO FROM TB_CICLOS_DE_VIDA";
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
			String sql = "SELECT DESCRIÇÃO FROM TB_SITUAÇÕES";
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
}