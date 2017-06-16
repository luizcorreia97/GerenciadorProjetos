package Classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Banco_de_Dados.Conecta;

public class Ciclo_de_VidaDAO {
	
	//GETTERS E SETTERS
	private int id;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private String descricao;	
	public String getDescricao() {return descricao;}
	public void setDescricao(String descricao) {this.descricao = descricao;}
	
	public String toString(){
		return this.descricao;
	}
	
	//MÉTODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			// Colocar o Comando SQL
			st.execute("INSERT INTO TB_CICLOS_DE_VIDA VALUES ('"+getDescricao()+"')");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, getDescricao()+" Não Foi Cadastrado(a) No Banco de Dados! =(");
		}
	}
	
	//MÉTODO ALTERAR
	public void Alterar(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			//Colocar o Comando SQL
			st.execute("UPDATE TB_CICLOS_DE_VIDA SET DESCRIÇÃO = '"+getDescricao()+"' WHERE ID = "+getId()+"");			
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getDescricao()+" Não Foi Alterado(a) No Banco de Dados! =(");
		}		
	}

	//MÉTODO EXCLUIR
	public void Excluir(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			//Colocar o Comando SQL
			st.execute("DELETE FROM TB_CICLOS_DE_VIDA WHERE ID = '"+getId()+"'");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getDescricao()+" Não Foi Excluído(a) No Banco de Dados! =(");
		}
		
	}
	
	//MÉTODO LISTAR	
	public TableModel Listar(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT * FROM TB_CICLOS_DE_VIDA";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
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
}