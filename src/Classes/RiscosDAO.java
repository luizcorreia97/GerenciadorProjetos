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

public class RiscosDAO {
	
	//GETTERS E SETTERS
	private int id;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private int id_projeto;
	public int getId_projeto() {return id_projeto;}
	public void setId_projeto(int id_projeto) {this.id_projeto = id_projeto;}
	
	private String descrição;
	public String getDescrição() {return descrição;}
	public void setDescrição(String descrição) {this.descrição = descrição;}
	
	private String plano_de_ação;
	public String getPlano_de_ação() {return plano_de_ação;}
	public void setPlano_de_ação(String plano_de_ação) {this.plano_de_ação = plano_de_ação;}
	
	//MÉTODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_RISCOS VALUES ("+getId_projeto()+",'"+getDescrição()+"','"+getPlano_de_ação()+"')");
			JOptionPane.showMessageDialog(null, "''"+getDescrição()+"'' Cadastrado Com Sucesso! =)");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, getDescrição()+"Não Foi Cadastrado(a) No Banco de Dados! =(");
		}
	}
	
	//MÉTODO ALTERAR
	public void Alterar(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_RISCOS SET DESCRIÇÃO = '"+getDescrição()+"', PLANO_DE_AÇÃO = '"+getPlano_de_ação()+"' WHERE ID = "+getId()+" AND ID_PROJETO = "+getId_projeto()+"");			
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getDescrição()+"Não Foi Alterado(a) No Banco de Dados! =(");
		}		
	}

	//MÉTODO EXCLUIR
	public void Excluir(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("DELETE FROM TB_RISCOS WHERE ID = '"+getId()+"'");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getDescrição()+"Não Foi Excluído(a) No Banco de Dados! =(");
		}		
	}
	
	//MÉTODO LISTAR
	public TableModel Listar(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT ID, DESCRIÇÃO, PLANO_DE_AÇÃO FROM TB_RISCOS WHERE ID_PROJETO = "+getId_projeto()+"";
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