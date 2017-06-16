package Banco_de_Dados;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conectar_Banco {
	
	public void conectar(){
		try{
			Connection string_conexao = new Conecta().getConnection();
			//JOptionPane.showMessageDialog(null, "Você Está Conectado Ao Banco De Dados"
			//		+ "\n          *** SQLServer 2014 ***","SEJA BEM VINDO",2);
		}
		catch(SQLException e){
			//JOptionPane.showMessageDialog(null, "Falha Ao Conectar Ao Banco de Dados!");
		}
	}
}