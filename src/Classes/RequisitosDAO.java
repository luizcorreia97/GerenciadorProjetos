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

public class RequisitosDAO {
	
	//GETTERS E SETTERS
	private int id;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private int id_projeto;
	public int getId_projeto() {return id_projeto;}
	public void setId_projeto(int id_projeto) {this.id_projeto = id_projeto;}
	
	private String RF;
	public String getRF() {return RF;}
	public void setRF(String rF) {RF = rF;}
	
	private String descri��o;	
	public String getDescri��o() {return descri��o;}
	public void setDescri��o(String descri��o) {this.descri��o = descri��o;}
	
	//M�TODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_REQUISITOS VALUES ("+getId_projeto()+",'"+getRF()+"','"+getDescri��o()+"')");
			JOptionPane.showMessageDialog(null, "''"+getRF()+"'' Cadastrado Com Sucesso! =)");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, getRF()+" N�o Foi Cadastrado(a) No Banco de Dados! =(");
		}
	}
	
	//M�TODO ALTERAR
	public void Alterar(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_REQUISITOS SET RF = '"+getRF()+"', DESCRI��O = '"+getDescri��o()+"' WHERE ID = "+getId()+" AND ID_PROJETO = "+getId_projeto()+"");			
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getRF()+" N�o Foi Alterado(a) No Banco de Dados! =(");
		}		
	}

	//M�TODO EXCLUIR
	public void Excluir(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("DELETE FROM TB_REQUISITOS WHERE ID = "+getId()+" AND ID_PROJETO = "+getId_projeto()+"");
			JOptionPane.showMessageDialog(null, "''"+getRF()+"'' Exclu�do Com Sucesso! =)");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getRF()+" N�o Foi Exclu�do(a) No Banco de Dados! =(");
		}
	}
	
	//M�TODO LISTAR
	public TableModel Listar(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT ID, RF, DESCRI��O FROM TB_REQUISITOS WHERE ID_PROJETO = "+getId_projeto()+"";
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