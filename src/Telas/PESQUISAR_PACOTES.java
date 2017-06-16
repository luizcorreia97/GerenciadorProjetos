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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.UIManager;

import Banco_de_Dados.Conecta;
import Classes.Pacotes_de_EntregaDAO;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PESQUISAR_PACOTES extends JFrame {

	Pacotes_de_EntregaDAO Pacotes_de_Entrega = new Pacotes_de_EntregaDAO();
	
	private JPanel contentPane;

	private JTable table_pacotes;

	private JLabel lbl_imagem = new JLabel();
	private JLabel lbl_projetos_cadastrados;

	private JScrollPane pane_stakeholders;
	private JPanel panel;
	JButton btn_novo_pacote = new JButton("Novo");
	JButton btn_visualizar_pacote = new JButton("Visualizar");

	int i;
	
	public PESQUISAR_PACOTES(int id_projeto, int id_pacote, int num_pacote) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				Pacotes_de_Entrega.setId_projeto(id_projeto);
				i = 0;
				popular_table();
				Verificar_Ciclo();
				Verificar_CSU_Sem_Pacote();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 870, 259);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pane_stakeholders = new JScrollPane();
		pane_stakeholders.setBounds(20, 55, 834, 164);
		getContentPane().add(pane_stakeholders);

		setTitle("PESQUISAR PACOTES");
		//setLocation(250, 250);
		setLocationRelativeTo(null);
		setResizable(false);
		
		ButtonGroup botoes_ordena_tipo = new ButtonGroup();
		
		ButtonGroup botoes_ordem = new ButtonGroup();
		
		// Criar Modelo de Tabela Stakeholders
		table_pacotes = new JTable(){
			public boolean isCellEditable(int row, int column) {
		           return false;
		    }
		};
		table_pacotes.setFont(new Font("Andalus", Font.PLAIN, 15));
		pane_stakeholders.setViewportView(table_pacotes);

		JButton btn_voltar = new JButton("Voltar");
		btn_voltar.setToolTipText("Voltar Para Tela 'Gerenciar Projeto'");
		btn_voltar.setForeground(Color.BLACK);
		btn_voltar.setBackground(UIManager.getColor("Button.background"));
		btn_voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btn_voltar.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_voltar.setBounds(20, 19, 92, 25);
		contentPane.add(btn_voltar);

		lbl_projetos_cadastrados = new JLabel("Lista de Pacotes");
		lbl_projetos_cadastrados.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_projetos_cadastrados.setForeground(Color.BLACK);
		lbl_projetos_cadastrados.setBackground(Color.BLACK);
		lbl_projetos_cadastrados.setFont(new Font("Andalus", Font.PLAIN, 30));
		lbl_projetos_cadastrados.setBounds(244, 19, 188, 25);
		contentPane.add(lbl_projetos_cadastrados);
		btn_visualizar_pacote.setToolTipText("Visualizar Pacote");
		
		btn_visualizar_pacote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					int posicao = table_pacotes.getSelectedRow();
					int num_pacote = ((int)table_pacotes.getValueAt(posicao, 0));
					
					new GERENCIAR_PACOTES(id_projeto, num_pacote).setVisible(true);
					dispose();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Selecione Um Pacote Por Favor!","ATENÇÃO!!",2);
				}
				
			}
		});
		btn_visualizar_pacote.setForeground(Color.BLACK);
		btn_visualizar_pacote.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_visualizar_pacote.setBackground(UIManager.getColor("Button.background"));
		btn_visualizar_pacote.setBounds(675, 19, 132, 25);
		contentPane.add(btn_visualizar_pacote);
		btn_novo_pacote.setToolTipText("Novo Pacote");
				
		btn_novo_pacote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					int num_pacote = table_pacotes.getRowCount()+1;
					
					new NOVO_PACOTE(id_projeto, num_pacote).setVisible(true);
					dispose();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Selecione Um Pacote Por Favor!","ATENÇÃO!!",2);
				}
				
			}
		});
		btn_novo_pacote.setForeground(Color.BLACK);
		btn_novo_pacote.setFont(new Font("Andalus", Font.PLAIN, 20));
		btn_novo_pacote.setBackground(UIManager.getColor("Button.background"));
		btn_novo_pacote.setBounds(515, 19, 120, 25);
		contentPane.add(btn_novo_pacote);
						
		panel = new JPanel();
		panel.setBounds(244, 19, 188, 25);
		contentPane.add(panel);
		
		lbl_imagem.setFont(new Font("Andalus", Font.PLAIN, 17));
		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Lista.jpg"));
		lbl_imagem.setBounds(0, 0, 864, 230);
		contentPane.add(lbl_imagem);
	}
	
	//MÉTODO POPULAR TABLE COM OS DADOS
  	public void popular_table(){
  		
  		table_pacotes.setModel(Pacotes_de_Entrega.Listar_Pacotes_Salvos());
  		
  	}
  	public void Verificar_Ciclo(){
  		
  		try {
			
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Conecta().getConnection();
  			String sql = "SELECT CICLO_DE_VIDA FROM TB_PROJETOS WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+"";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			
  			rs.next();
  	  		if((rs.getString("CICLO_DE_VIDA").equals("Cascata"))&&(table_pacotes.getRowCount() == 1)){									
  	  			btn_novo_pacote.setEnabled(false);
  	  		}

		} catch (Exception e) {
			// TODO: handle exception
		}  		
  	}
  	public void Verificar_CSU_Sem_Pacote(){
  		
  		try {
  					
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Conecta().getConnection();
  			String sql = "SELECT QTDE = COUNT(*) FROM TB_CASOS_DE_USO WHERE ID_PROJETO = "+Pacotes_de_Entrega.getId_projeto()+" AND Nº_PACOTE = 0";
  			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			rs.next();
  			
  			/*if(table_pacotes.getRowCount() == 0){
				btn_novo_pacote.setEnabled(false);
				btn_visualizar_pacote.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Você Não Possui Nenhum Casos de Uso Cadastrado.\n"
						+ "Favor Voltar A Tela ''Gerenciar Casos de Uso'' e Cadastra-lô(s)!","ATENÇÃO!!",2);
			}*/
  			if((table_pacotes.getRowCount()>0)&&(rs.getString("QTDE").equals("0"))){
  	  			Verifica_Entrega_Do_Projeto();
  	  		}
  			/*else{
  				if(i==0){
  					JOptionPane.showMessageDialog(null, "Ainda Existem Casos de Uso Que Estão Forá De Um Pacote, Favor Verificar!");
  		  	  		i=1;
  				}
  			}*/

		} catch (Exception e) {
			// TODO: handle exceptionfalse
		}  		
  	}
  	public void Verifica_Entrega_Do_Projeto(){
  		int pacote_ok=0;
  		for(int i=0;i<table_pacotes.getRowCount();i++){
  			if((table_pacotes.getValueAt(i, 6).toString().equals("Entregue")||(table_pacotes.getValueAt(i, 6).toString().equals("Cancelado")))){
  			pacote_ok++;
  			}
  		}
  		if(pacote_ok == table_pacotes.getRowCount()){
  			btn_novo_pacote.setEnabled(false);
	  		btn_visualizar_pacote.setEnabled(false);
  			JOptionPane.showMessageDialog(null, "Bom Trabalho Todos os Pacotes Foram Entregue(s)."
  					+ "\n             Clique no Botão 'Entregar Projeto'! =)");
  			dispose();
  		}
  	}
}