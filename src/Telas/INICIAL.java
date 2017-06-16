package Telas;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;

import Banco_de_Dados.Conectar_Banco;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.UIManager;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.Toolkit;

public class INICIAL extends JFrame {

	private JPanel contentPane;
	JLabel lbl_imagem = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					INICIAL frame = new INICIAL();
					frame.setVisible(true);

					//DE INICIO O PROJETO JÁ CONECTA AO BANCO DE DADOS
					Conectar_Banco conec = new Conectar_Banco();
					conec.conectar();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public INICIAL() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Luiz Henrique\\Downloads\\Combo.jpg"));

		setTitle("SEJA BEM VINDO AO");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 367, 377);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//setLocation(525, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(UIManager.getColor("Button.background"));
		layeredPane.setBounds(45, 231, 277, 93);
		contentPane.add(layeredPane);
		layeredPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		layeredPane.setLayout(null);
				
		//BOTÃO GERENCIAR TÉCNICOS
		JButton btn_gerenciar_técnicos = new JButton("GERENCIAR T\u00C9CNICOS");
		btn_gerenciar_técnicos.setToolTipText("Gerenciar T\u00E9cnicos");
		btn_gerenciar_técnicos.setBounds(10, 51, 256, 32);
		layeredPane.add(btn_gerenciar_técnicos);
		btn_gerenciar_técnicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new TÉCNICOS().setVisible(true);

			}
		});
		btn_gerenciar_técnicos.setForeground(Color.BLACK);
		btn_gerenciar_técnicos.setFont(new Font("Snap ITC", Font.PLAIN, 15));
		btn_gerenciar_técnicos.setBackground(UIManager.getColor("Button.background"));
		
		//BOTÃO GERENCIAR PROJETOS
		JButton btn_gerenciar_projetos1 = new JButton("GERENCIAR PROJETOS");
		btn_gerenciar_projetos1.setToolTipText("Gerenciar Projetos");
		btn_gerenciar_projetos1.setBounds(10, 11, 256, 32);
		layeredPane.add(btn_gerenciar_projetos1);
		btn_gerenciar_projetos1.setBackground(UIManager.getColor("Button.background"));
		btn_gerenciar_projetos1.setForeground(Color.BLACK);
		btn_gerenciar_projetos1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new PESQUISAR_PROJETOS().setVisible(true);
				dispose();
			}
		});
		btn_gerenciar_projetos1.setFont(new Font("Snap ITC", Font.PLAIN, 15));

		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBackground(UIManager.getColor("Button.background"));
		layeredPane_2.setBounds(22, 23, 317, 191);
		contentPane.add(layeredPane_2);
		layeredPane_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				
		JLabel lbl_1 = new JLabel("SGPDS");
		lbl_1.setBounds(97, 11, 138, 32);
		layeredPane_2.add(lbl_1);
		lbl_1.setForeground(Color.BLACK);
		lbl_1.setFont(new Font("Snap ITC", Font.PLAIN, 30));
		
		JLabel lbl_2 = new JLabel("SISTEMA DE GEST\u00C3O");
		lbl_2.setBounds(22, 54, 276, 25);
		layeredPane_2.add(lbl_2);
		lbl_2.setForeground(Color.BLACK);
		lbl_2.setFont(new Font("Snap ITC", Font.PLAIN, 20));
		
		JLabel lbl_3 = new JLabel("DE SOFTWARE");
		lbl_3.setBounds(65, 157, 176, 25);
		layeredPane_2.add(lbl_3);
		lbl_3.setForeground(Color.BLACK);
		lbl_3.setFont(new Font("Snap ITC", Font.PLAIN, 20));
		
		JLabel lbl_4 = new JLabel("DE PROJETOS DE");
		lbl_4.setBounds(48, 90, 225, 20);
		layeredPane_2.add(lbl_4);
		lbl_4.setForeground(Color.BLACK);
		lbl_4.setFont(new Font("Snap ITC", Font.PLAIN, 20));
		
		JLabel lbl_5 = new JLabel("DE DESENVOLVIMENTO");
		lbl_5.setBounds(10, 121, 288, 25);
		layeredPane_2.add(lbl_5);
		lbl_5.setForeground(Color.BLACK);
		lbl_5.setFont(new Font("Snap ITC", Font.PLAIN, 20));
						
		JPanel panel = new JPanel();
		panel.setBounds(93, 11, 131, 32);
		layeredPane_2.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(22, 54, 266, 20);
		layeredPane_2.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(48, 90, 214, 20);
		layeredPane_2.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 121, 288, 25);
		layeredPane_2.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(65, 160, 176, 22);
		layeredPane_2.add(panel_4);

		lbl_imagem.setIcon(new ImageIcon("C:\\Users\\Luiz Henrique\\Desktop\\FACEAR\\2º Periodo\\Programação Orientada a Objetos\\Arquivos Eclipse\\INTEGRADOR_3_FINALIZADO\\src\\Imagens\\Inicial.jpg"));
		lbl_imagem.setBounds(0, 0, 368, 398);
		contentPane.add(lbl_imagem);
	}
}