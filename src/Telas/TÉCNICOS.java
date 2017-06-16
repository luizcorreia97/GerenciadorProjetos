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
import Classes.TécnicosDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;
import java.awt.SystemColor;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TÉCNICOS extends JFrame {

	TécnicosDAO Técnicos = new TécnicosDAO();

	private JPanel contentPane;
	private JTextField txt_nome;
	private JScrollPane pane_lista;
	private JTable table;
	JComboBox cmb_cargo = new JComboBox();
	private JLabel lbl_imagem = new JLabel();
	private JTextField txt_cargo;
	
	public TÉCNICOS() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				popular_table();
				try {
					txt_nome.requestFocus();
					ListarCargosNoCombo();
				} catch (Exception e) {
					System.out.println("Erro Ao Popular Combo de Cargo");
					e.printStackTrace();
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 497, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		pane_lista = new JScrollPane();
		pane_lista.setBounds(20, 223, 442, 170);
		getContentPane().add(pane_lista);

		setTitle("GERENCIAR T\u00C9CNICOS");
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
		txt_nome.setBounds(99, 66, 363, 22);
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
		
		JLabel lbl_nome_técnico = new JLabel("Nome:");
		lbl_nome_técnico.setBounds(30, 66, 59, 22);
		contentPane.add(lbl_nome_técnico);
		lbl_nome_técnico.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JButton btn_cargo = new JButton("Cargo:");
		btn_cargo.setToolTipText("Gerenciar Cargos");
		btn_cargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CARGO().setVisible(true);
			}
		});
		btn_cargo.setFont(new Font("Andalus", Font.PLAIN, 17));
		btn_cargo.setBounds(30, 99, 86, 20);
		contentPane.add(btn_cargo);
		
		JLayeredPane layered_nome_cargo = new JLayeredPane();
		layered_nome_cargo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layered_nome_cargo.setBounds(20, 58, 453, 69);
		contentPane.add(layered_nome_cargo);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 58, 19);
		layered_nome_cargo.add(panel);
		cmb_cargo.setToolTipText("Selecionar Cargo");
		cmb_cargo.setBounds(264, 38, 179, 23);
		layered_nome_cargo.add(cmb_cargo);
		
		cmb_cargo.setFont(new Font("Andalus", Font.PLAIN, 16));
		
		txt_cargo = new JTextField();
		txt_cargo.setEditable(false);
		txt_cargo.setHorizontalAlignment(SwingConstants.CENTER);
		txt_cargo.setFont(new Font("Andalus", Font.PLAIN, 18));
		txt_cargo.setColumns(10);
		txt_cargo.setBounds(104, 39, 150, 22);
		layered_nome_cargo.add(txt_cargo);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(20, 452, 453, 46);
		contentPane.add(layeredPane_1);
		
		//BOTÃO CADASTRAR
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setBounds(26, 11, 129, 25);
		layeredPane_1.add(btn_cadastrar);
		btn_cadastrar.setForeground(Color.BLACK);
		btn_cadastrar.setBackground(UIManager.getColor("Button.background"));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar "
				+txt_nome.getText()+"?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
				
				Técnicos.setNome(txt_nome.getText());
				Técnicos.setCargo(cmb_cargo.getSelectedItem().toString());
		
				if(valida == JOptionPane.YES_OPTION){
					Técnicos.Cadastrar();
					JOptionPane.showMessageDialog(null, Técnicos.getNome()+" Cadastrado(a) Com Sucesso! =)");
					txt_nome.setText(null);
					txt_cargo.setText(null);
					txt_nome.requestFocus();
					popular_table();
				}
				else {
					JOptionPane.showMessageDialog(null, Técnicos.getNome()+" Não Foi Cadastrado(a)! =(");
					txt_nome.setText(null);
					txt_cargo.setText(null);
					txt_nome.requestFocus();
				}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOTÃO ALTERAR
		JButton btn_alterar = new JButton("Alterar");
		btn_alterar.setBounds(165, 11, 117, 25);
		layeredPane_1.add(btn_alterar);
		btn_alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Alterar "
				+table.getValueAt(posicao_table, 1)+" ?",
				"ATENÇÃO",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
				
				Técnicos.setNome(txt_nome.getText());
				Técnicos.setCargo(cmb_cargo.getSelectedItem().toString());
				
				if(valida == JOptionPane.YES_OPTION){
					Técnicos.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
					Técnicos.Alterar();
					JOptionPane.showMessageDialog(null, table.getValueAt(posicao_table, 1).toString()+" Alterado(a) Com Sucesso Para "+Técnicos.getNome()+" =)");
					txt_nome.setText(null);
					txt_cargo.setText(null);
					txt_nome.requestFocus();
					popular_table();
				}
				else {
					JOptionPane.showMessageDialog(null, table.getValueAt(posicao_table, 1).toString()+" Não Foi Alterado(a)!");
					txt_nome.setText(null);
					txt_cargo.setText(null);
					txt_nome.requestFocus();
				}
			}
		});
		btn_alterar.setForeground(Color.BLACK);
		btn_alterar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_alterar.setBackground(UIManager.getColor("Button.background"));
		
		//BOTÃO EXCLUIR
		JButton btn_excluir = new JButton("Excluir");
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
				
				Técnicos.setNome(txt_nome.getText());
				Técnicos.setCargo(cmb_cargo.getSelectedItem().toString());
				
				if(valida == JOptionPane.YES_OPTION){
					Técnicos.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
					Técnicos.Excluir();
					JOptionPane.showMessageDialog(null, Técnicos.getNome()+" Excluído(a) Com Sucesso! =)");
					txt_nome.setText(null);
					txt_cargo.setText(null);
					txt_nome.requestFocus();
					popular_table();
				}
				else {
					JOptionPane.showMessageDialog(null, table.getValueAt(posicao_table, 1).toString()+" Não Foi Excluído(a)!");
					txt_nome.setText(null);
					txt_cargo.setText(null);
					txt_nome.requestFocus();
				}				
			}
		});
		btn_excluir.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JLayeredPane layered_ordena = new JLayeredPane();
		layered_ordena.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layered_ordena.setBounds(61, 138, 362, 74);
		contentPane.add(layered_ordena);
		
		JLabel label = new JLabel("ORDENAR POR");
		label.setFont(new Font("Andalus", Font.PLAIN, 17));
		label.setBounds(116, 11, 115, 17);
		layered_ordena.add(label);
		
		ButtonGroup botoes_ordena_tipo = new ButtonGroup();
		
		ButtonGroup botoes_ordem = new ButtonGroup();
		
		JRadioButton rd_id = new JRadioButton("Id");
		rd_id.setToolTipText("Ordenar Pelo Id");
		rd_id.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Técnicos.setOrdena_tipo("ID");
				popular_table();
				txt_nome.requestFocus();
			}
		});
		rd_id.setSelected(true);
		rd_id.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_id.setBounds(21, 35, 46, 23);
		layered_ordena.add(rd_id);
		rd_id.setSelected(true);
		botoes_ordena_tipo.add(rd_id);
		
		JRadioButton rd_nome = new JRadioButton("Nome");
		rd_nome.setToolTipText("Ordenar Pelo Nome");
		rd_nome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Técnicos.setOrdena_tipo("NOME");
				popular_table();
				txt_nome.requestFocus();
			}
		});
		rd_nome.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_nome.setBounds(136, 35, 66, 23);
		layered_ordena.add(rd_nome);
		botoes_ordena_tipo.add(rd_nome);
		
		JRadioButton rd_cargo = new JRadioButton("Cargo");
		rd_cargo.setToolTipText("Ordenar Pelo Cargo");
		rd_cargo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Técnicos.setOrdena_tipo("CARGO");
				popular_table();
				txt_nome.requestFocus();
			}
		});
		rd_cargo.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_cargo.setBounds(260, 35, 78, 23);
		layered_ordena.add(rd_cargo);
		botoes_ordena_tipo.add(rd_cargo);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(116, 11, 115, 17);
		layered_ordena.add(panel_2);
		
		JLayeredPane layered_ordem = new JLayeredPane();
		layered_ordem.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layered_ordem.setBounds(61, 404, 356, 37);
		contentPane.add(layered_ordem);
		
		JLabel label_1 = new JLabel("EM ORDEM");
		label_1.setFont(new Font("Andalus", Font.PLAIN, 17));
		label_1.setBounds(10, 10, 86, 17);
		layered_ordem.add(label_1);
		
		JRadioButton rd_crescente = new JRadioButton("Crescente");
		rd_crescente.setToolTipText("Ordenar Em Ordem Crescente");
		rd_crescente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Técnicos.setOrdena_ordem("ASC");
				popular_table();
				txt_nome.requestFocus();
			}
		});
		rd_crescente.setSelected(true);
		rd_crescente.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_crescente.setBounds(122, 7, 100, 23);
		layered_ordem.add(rd_crescente);
		botoes_ordem.add(rd_crescente);
		
		JRadioButton rd_decrescente = new JRadioButton("Descrescente");
		rd_decrescente.setToolTipText("Ordenar Em Ordem Decrescente");
		rd_decrescente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Técnicos.setOrdena_ordem("DESC");
				popular_table();
				txt_nome.requestFocus();
			}
		});
		rd_decrescente.setFont(new Font("Andalus", Font.PLAIN, 17));
		rd_decrescente.setBounds(227, 7, 115, 23);
		layered_ordem.add(rd_decrescente);
		botoes_ordem.add(rd_decrescente);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 91, 16);
		layered_ordem.add(panel_1);
		
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Vários.jpg"));
		lbl_imagem.setBounds(0, 0, 491, 509);
		contentPane.add(lbl_imagem);
	}
	
	//MÉTODO POPULAR TABLE COM OS DADOS
	public void popular_table(){
		table.setModel(Técnicos.Listar());
	}
	
	//MÉTODO PEGAR LINHA SELECIONADA E JOGA NOS TEXTS
	public void dados(){		
		
		int posicao_table = table.getSelectedRow();	
		txt_nome.setText(table.getValueAt(posicao_table, 1).toString());
		txt_cargo.setText(table.getValueAt(posicao_table, 2).toString());		
		txt_nome.requestFocus();
	}
	
	//MÉTODO QUE POPULA OS CARGOS NO COMBO
    public JComboBox<String> ListarCargosNoCombo() throws Exception {
    	try {
    		cmb_cargo.removeAllItems();
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Conecta().getConnection();
			String sql = "SELECT CARGO FROM TB_CARGOS";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cmb_cargo.addItem(rs.getString("CARGO"));
	            cmb_cargo.updateUI();
			}
		} catch (Exception e) {
			return null;
		}
		return cmb_cargo;
    }
}