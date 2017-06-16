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
import Classes.JustificativasDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JUSTIFICATIVAS extends JFrame {
	
	JustificativasDAO Justificativas = new JustificativasDAO();

	private JPanel contentPane;
	private JTextField txt_descri��o;
	private JScrollPane pane_lista;
	private JTable table;
	private JLabel lbl_imagem = new JLabel();
	
	public JUSTIFICATIVAS(int id_projeto) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Justificativas.setId_projeto(id_projeto);
				popular_table();
				txt_descri��o.requestFocus();
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

		setTitle("GERENCIAR JUSTIFICATIVAS");
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

		txt_descri��o = new JTextField();
		txt_descri��o.setFont(new Font("Andalus", Font.PLAIN, 20));
		txt_descri��o.setHorizontalAlignment(SwingConstants.CENTER);
		txt_descri��o.setColumns(10);
		txt_descri��o.setBounds(133, 66, 443, 22);
		contentPane.add(txt_descri��o);

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
		btn_voltar.setBounds(20, 22, 104, 25);
		contentPane.add(btn_voltar);
		
		JLabel lbl_nome_t�cnico = new JLabel("Descri\u00E7\u00E3o:");
		lbl_nome_t�cnico.setBounds(30, 66, 93, 22);
		contentPane.add(lbl_nome_t�cnico);
		lbl_nome_t�cnico.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setBounds(20, 58, 568, 41);
		contentPane.add(layeredPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 92, 19);
		layeredPane.add(panel);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane_1.setBounds(20, 285, 568, 46);
		contentPane.add(layeredPane_1);
		
		//BOT�O CADASTRAR
		JButton btn_cadastrar = new JButton("Cadastrar");
		btn_cadastrar.setBounds(26, 11, 162, 25);
		layeredPane_1.add(btn_cadastrar);
		btn_cadastrar.setForeground(Color.BLACK);
		btn_cadastrar.setBackground(UIManager.getColor("Button.background"));
		btn_cadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Cadastrar ''"
						+txt_descri��o.getText()+"''?",
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Justificativas.setId_projeto(id_projeto);
						Justificativas.setDescricao(txt_descri��o.getText());
				
						if(valida == JOptionPane.YES_OPTION){
							Justificativas.Cadastrar();
							txt_descri��o.setText(null);
							txt_descri��o.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+Justificativas.getDescricao()+"'' N�o Foi Cadastrado(a)! =(");
							txt_descri��o.setText(null);
							txt_descri��o.requestFocus();
						}
			}
		});
		btn_cadastrar.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		//BOT�O ALTERAR
		JButton btn_alterar = new JButton("Alterar");
		btn_alterar.setBounds(198, 11, 162, 25);
		layeredPane_1.add(btn_alterar);
		btn_alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int posicao_table = table.getSelectedRow();
				int valida = JOptionPane.showConfirmDialog(null,"Deseja Realmente Alterar ''"
						+table.getValueAt(posicao_table, 1)+"'' Para ''"+txt_descri��o.getText()+"''?",
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Justificativas.setId_projeto(id_projeto);
						Justificativas.setDescricao(txt_descri��o.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Justificativas.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Justificativas.Alterar();
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' Alterado(a) Com Sucesso Para ''"+Justificativas.getDescricao()+"'' =)");
							txt_descri��o.setText(null);
							txt_descri��o.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' N�o Foi Alterado(a)!");
							txt_descri��o.setText(null);
							txt_descri��o.requestFocus();
						}
			}
		});
		btn_alterar.setForeground(Color.BLACK);
		btn_alterar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_alterar.setBackground(UIManager.getColor("Button.background"));
		
		//BOT�O EXCLUIR
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
						"ATEN��O",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null);
						
						Justificativas.setId_projeto(id_projeto);
						Justificativas.setDescricao(txt_descri��o.getText());
						
						if(valida == JOptionPane.YES_OPTION){
							Justificativas.setId(Integer.parseInt(table.getValueAt(posicao_table, 0).toString()));
							Justificativas.Excluir();
							txt_descri��o.setText(null);
							txt_descri��o.requestFocus();
							popular_table();
						}
						else {
							JOptionPane.showMessageDialog(null, "''"+table.getValueAt(posicao_table, 1).toString()+"'' N�o Foi Exclu�do(a)!");
							txt_descri��o.setText(null);
							txt_descri��o.requestFocus();
						}
			}
		});
		btn_excluir.setFont(new Font("Andalus", Font.PLAIN, 20));
		
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2� Periodo\\Programa��o Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\V�rios.jpg"));
		lbl_imagem.setBounds(0, 0, 609, 352);
		contentPane.add(lbl_imagem);
	}
	
	//M�TODO POPULAR TABLE COM OS DADOS
	public void popular_table(){
		
		table.setModel(Justificativas.Listar());
		
	}
	
	//M�TODO PEGAR LINHA SELECIONADA E JOGA NO TEXT
	public void dados(){
		
		int posicao_table = table.getSelectedRow();	
		txt_descri��o.setText(table.getValueAt(posicao_table, 1).toString());		
		txt_descri��o.requestFocus();
	}
}