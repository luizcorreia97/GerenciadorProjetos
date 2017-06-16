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
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import Banco_de_Dados.Conecta;
import Classes.Casos_de_UsoDAO;
import Classes.Pacotes_de_EntregaDAO;
import Classes.ProjetosDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFormattedTextField;

import java.awt.Toolkit;
import java.awt.SystemColor;

public class NOVO_PACOTE extends JFrame {
	
	ProjetosDAO Projetos = new ProjetosDAO();
	Pacotes_de_EntregaDAO Pacotes_de_Entrega = new Pacotes_de_EntregaDAO();
	Casos_de_UsoDAO Casos_de_Uso = new Casos_de_UsoDAO();
	
	int valida_cadastro = 0;
	
	private JPanel contentPane;
	private JScrollPane pane_lista_csu;
	private JTable table_csu;
	private DefaultTableModel modelo_csu;
	
	private JScrollPane pane_lista_csu_entrega;
	private JTable table_csu_entrega;
	private DefaultTableModel modelo_csu_entrega;
	
	private JTextField txt_descricao;
	private JLabel lbl_imagem = new JLabel();
	private JTextField txt_nome_projeto;
	private JTextField txt_gerente_projeto;
	JLabel lbl_num_pacote = new JLabel("");
	
	JComboBox cmb_fase = new JComboBox();
	private JTextField txt_custo;
	
	private MaskFormatter mascara_data_cadastro;
	private MaskFormatter mascara_data_previsao_entrega;
	private JTextField txt_ciclo;

	public NOVO_PACOTE(int id_projeto, int num_pacote) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					txt_descricao.requestFocus();
					Pacotes_de_Entrega.setId_projeto(id_projeto);
					Pacotes_de_Entrega.setNº_pacote(num_pacote);
					lbl_num_pacote.setText(Integer.toString(Pacotes_de_Entrega.getNº_pacote()));
					Popular_Campos();
					popular_tables();
					ListarFaseNoCombo();
					txt_descricao.requestFocus();
				} catch (Exception e1) {
					System.out.println("Erro Ao Popular Combo Fases");
					e1.printStackTrace();
				}
			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 823, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setTitle("NOVO PACOTE");
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
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(10, 144, 585, 97);
		contentPane.add(layeredPane_1);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_2.setBounds(605, 36, 201, 205);
		contentPane.add(layeredPane_2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		JFormattedTextField txt_data_cadastro = new JFormattedTextField(sdf.format(new Date()));
		txt_data_cadastro.setBounds(173, 67, 127, 20);
		layeredPane_1.add(txt_data_cadastro);
		txt_data_cadastro.setHorizontalAlignment(SwingConstants.CENTER);
		txt_data_cadastro.setFont(new Font("Andalus", Font.PLAIN, 18));
		
		JFormattedTextField txt_data_previsao_entrega = new JFormattedTextField(mascara_data_previsao_entrega);
		txt_data_previsao_entrega.setBounds(35, 116, 122, 20);
		layeredPane_2.add(txt_data_previsao_entrega);
		txt_data_previsao_entrega.setHorizontalAlignment(SwingConstants.CENTER);
		txt_data_previsao_entrega.setFont(new Font("Andalus", Font.PLAIN, 17));
		
		pane_lista_csu = new JScrollPane();
		pane_lista_csu.setBounds(431, 297, 375, 107);
		getContentPane().add(pane_lista_csu);
		
		pane_lista_csu_entrega = new JScrollPane();
		pane_lista_csu_entrega.setBounds(21, 297, 319, 107);
		getContentPane().add(pane_lista_csu_entrega);

		// Criar Modelo de Tabela CSU
		table_csu = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_csu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int posicao = table_csu.getSelectedRow();
				
				Casos_de_Uso.setId_projeto(Pacotes_de_Entrega.getId_projeto());
				Casos_de_Uso.setNº_pacote(Pacotes_de_Entrega.getNº_pacote());
				Casos_de_Uso.setId(Integer.parseInt(table_csu.getValueAt(posicao, 0).toString()));
				Casos_de_Uso.setCSU(table_csu.getValueAt(posicao, 1).toString());
			}
		});
		table_csu.setFont(new Font("Andalus", Font.PLAIN, 15));
		modelo_csu = new DefaultTableModel(null, new Object[] { "ID", "CSU", "DESCRIÇÃO"});
		table_csu.setModel(modelo_csu);
		pane_lista_csu.setViewportView(table_csu);
		
		// Criar Modelo de Tabela CSU Entregas
		table_csu_entrega = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_csu_entrega.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int posicao = table_csu_entrega.getSelectedRow();
				
				Casos_de_Uso.setId_projeto(Pacotes_de_Entrega.getId_projeto());
				Casos_de_Uso.setNº_pacote(Pacotes_de_Entrega.getNº_pacote());
				Casos_de_Uso.setId(Integer.parseInt(table_csu_entrega.getValueAt(posicao, 0).toString()));
				Casos_de_Uso.setCSU(table_csu_entrega.getValueAt(posicao, 1).toString());
			}
		});
		table_csu_entrega.setFont(new Font("Andalus", Font.PLAIN, 15));
		modelo_csu_entrega = new DefaultTableModel(null, new Object[] { "ID", "CSU", "DESCRIÇÃO"});
		table_csu_entrega.setModel(modelo_csu_entrega);
		pane_lista_csu_entrega.setViewportView(table_csu_entrega);

		txt_descricao = new JTextField();
		txt_descricao.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_descricao.setHorizontalAlignment(SwingConstants.CENTER);
		txt_descricao.setColumns(10);
		txt_descricao.setBounds(116, 183, 468, 22);
		contentPane.add(txt_descricao);

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
		
		JLabel label_2 = new JLabel("");
		label_2.setFont(new Font("Andalus", Font.PLAIN, 20));
		label_2.setBounds(165, 71, 196, 16);
		contentPane.add(label_2);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(10, 36, 585, 97);
		contentPane.add(layeredPane);
		
		JLabel lbl_nome_projeto = new JLabel("Nome do Projeto:");
		lbl_nome_projeto.setBounds(10, 17, 146, 16);
		layeredPane.add(lbl_nome_projeto);
		lbl_nome_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JLabel lblNomeDoGerente = new JLabel("Nome do Gerente do Projeto:");
		lblNomeDoGerente.setBounds(11, 44, 243, 16);
		layeredPane.add(lblNomeDoGerente);
		lblNomeDoGerente.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 17, 152, 16);
		layeredPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 44, 243, 16);
		layeredPane.add(panel_2);
		
		txt_nome_projeto = new JTextField();
		txt_nome_projeto.setEditable(false);
		txt_nome_projeto.setHorizontalAlignment(SwingConstants.CENTER);
		txt_nome_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_nome_projeto.setColumns(10);
		txt_nome_projeto.setBounds(167, 11, 408, 22);
		layeredPane.add(txt_nome_projeto);
		
		txt_gerente_projeto = new JTextField();
		txt_gerente_projeto.setEditable(false);
		txt_gerente_projeto.setHorizontalAlignment(SwingConstants.CENTER);
		txt_gerente_projeto.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_gerente_projeto.setColumns(10);
		txt_gerente_projeto.setBounds(259, 41, 316, 19);
		layeredPane.add(txt_gerente_projeto);
		
		JLabel lbl_ciclo = new JLabel("Ciclo de Vida:");
		lbl_ciclo.setFont(new Font("Andalus", Font.PLAIN, 20));
		lbl_ciclo.setBounds(11, 72, 121, 16);
		layeredPane.add(lbl_ciclo);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 72, 121, 16);
		layeredPane.add(panel);
		
		txt_ciclo = new JTextField();
		txt_ciclo.setHorizontalAlignment(SwingConstants.CENTER);
		txt_ciclo.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_ciclo.setEditable(false);
		txt_ciclo.setColumns(10);
		txt_ciclo.setBounds(142, 70, 249, 19);
		layeredPane.add(txt_ciclo);
		
		JButton btn_fase = new JButton("Fase:");
		btn_fase.setToolTipText("Gerenciar Fases");
		btn_fase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FASES().setVisible(true);
			}
		});
		cmb_fase.setBounds(410, 63, 165, 23);
		layeredPane_1.add(cmb_fase);
		
			cmb_fase.setFont(new Font("Andalus", Font.PLAIN, 16));
		btn_fase.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_fase.setBounds(310, 66, 90, 20);
		layeredPane_1.add(btn_fase);
		
		JLabel lblNDoPacote = new JLabel("N\u00BA do Pacote:");
		lblNDoPacote.setBounds(10, 11, 116, 16);
		layeredPane_1.add(lblNDoPacote);
		lblNDoPacote.setFont(new Font("Andalus", Font.PLAIN, 20));
		
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
	
		lbl_num_pacote.setBounds(132, 11, 25, 16);
		layeredPane_1.add(lbl_num_pacote);
		lbl_num_pacote.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 11, 150, 16);
		layeredPane_1.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 38, 90, 20);
		layeredPane_1.add(panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(10, 66, 150, 20);
		layeredPane_1.add(panel_6);
		
		JButton btn_csu = new JButton("Casos de Uso");
		btn_csu.setToolTipText("Gerenciar Casos de Uso");
		btn_csu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new CASOS_DE_USO(id_projeto).setVisible(true);
			}
		});
		btn_csu.setForeground(Color.BLACK);
		btn_csu.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_csu.setBackground(UIManager.getColor("Button.background"));
		btn_csu.setBounds(500, 264, 155, 22);
		contentPane.add(btn_csu);
		
		JLabel lbl_csu_entrega = new JLabel("CSU Relacionados Ao Pacote");
		lbl_csu_entrega.setForeground(Color.BLACK);
		lbl_csu_entrega.setFont(new Font("Andalus", Font.PLAIN, 20));
		lbl_csu_entrega.setBackground(Color.WHITE);
		lbl_csu_entrega.setBounds(53, 264, 247, 25);
		contentPane.add(lbl_csu_entrega);
				
		JLabel lblDataDePreviso = new JLabel("Previs\u00E3o Entrega");
		lblDataDePreviso.setBounds(25, 83, 155, 22);
		layeredPane_2.add(lblDataDePreviso);
		lblDataDePreviso.setForeground(Color.BLACK);
		lblDataDePreviso.setFont(new Font("Andalus", Font.PLAIN, 20));
		lblDataDePreviso.setBackground(Color.WHITE);
						
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setToolTipText("Cadastrar Pacote");
		btn_cadastrar.setBounds(25, 157, 146, 25);
		layeredPane_2.add(btn_cadastrar);
		btn_cadastrar.setForeground(Color.BLACK);
		btn_cadastrar.setBackground(UIManager.getColor("Button.background"));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Verificar_Ciclo_E_CSU_Selecionados();
				
				if(valida_cadastro == 0){
					
					int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar o Projeto "
							+txt_descricao.getText()+"?",
							"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
					
							if(valida == JOptionPane.YES_OPTION){							
								
								Pacotes_de_Entrega.setNº_pacote(Integer.parseInt(lbl_num_pacote.getText()));
								Pacotes_de_Entrega.setDescrição(txt_descricao.getText());
								Pacotes_de_Entrega.setData_cadastro(txt_data_cadastro.getText());
								Pacotes_de_Entrega.setFase(cmb_fase.getSelectedItem().toString());
								Pacotes_de_Entrega.setCusto(Float.parseFloat(txt_custo.getText()));
								Pacotes_de_Entrega.setData_prevista_entrega(txt_data_previsao_entrega.getText());
								Pacotes_de_Entrega.setSituação("Cadastrado");
								
								Pacotes_de_Entrega.Cadastrar();
						
					  			new PESQUISAR_PACOTES(id_projeto, id_projeto, num_pacote).setVisible(true);
					  			dispose();
							}
							else {
								JOptionPane.showMessageDialog(null, "Pacote "+Pacotes_de_Entrega.getNº_pacote()+" Não Foi Cadastrado! =(");
							}	
				}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 20));
								
		JLabel custo_do_pacote = new JLabel("Custo do Pacote R$");
		custo_do_pacote.setBounds(20, 29, 160, 16);
		layeredPane_2.add(custo_do_pacote);
		custo_do_pacote.setForeground(Color.BLACK);
		custo_do_pacote.setFont(new Font("Andalus", Font.PLAIN, 20));
		custo_do_pacote.setBackground(Color.WHITE);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(20, 29, 155, 16);
		layeredPane_2.add(panel_8);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBounds(25, 83, 140, 22);
		layeredPane_2.add(panel_9);
		
		txt_custo = new JTextField();
		txt_custo.setBounds(25, 51, 146, 21);
		layeredPane_2.add(txt_custo);
		txt_custo.setHorizontalAlignment(SwingConstants.CENTER);
		txt_custo.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_custo.setColumns(10);
				
		JPanel panel_10 = new JPanel();
		panel_10.setBounds(53, 265, 237, 21);
		contentPane.add(panel_10);
		
		JButton btn_adiciona = new JButton("");
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
		btn_adiciona.setIcon(new ImageIcon(NOVO_PACOTE.class.getResource("/Imagens/Flexa Verde.jpg")));
		btn_adiciona.setForeground(Color.BLACK);
		btn_adiciona.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_adiciona.setBackground(SystemColor.menu);
		btn_adiciona.setBounds(364, 307, 47, 35);
		contentPane.add(btn_adiciona);
		
		JButton btn_remove = new JButton("");
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
		btn_remove.setIcon(new ImageIcon(NOVO_PACOTE.class.getResource("/Imagens/Flexa Vermelha.jpg")));
		btn_remove.setForeground(Color.BLACK);
		btn_remove.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_remove.setBackground(SystemColor.menu);
		btn_remove.setBounds(364, 352, 47, 35);
		contentPane.add(btn_remove);
		lbl_imagem.setBounds(0, 0, 817, 429);
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
			String sql = "SELECT DESCRIÇÃO FROM TB_FASES";
			stmt = conexao.createStatement();
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
  	public void Popular_Campos(){
  		
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
  			JOptionPane.showMessageDialog(null, "Erro Ao Jogar Dados Nos Labels e Texts!");
  			e.printStackTrace();
  		}
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
  	  		if(rs.getString("CICLO_DE_VIDA").equals("Iterativo e Incremental")){
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