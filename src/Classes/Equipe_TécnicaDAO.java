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

public class Equipe_T�cnicaDAO {
	
	//GETTERS E SETTERS
	private int id;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private int id_projeto;
	public int getId_projeto() {return id_projeto;}
	public void setId_projeto(int id_projeto) {this.id_projeto = id_projeto;}
	
	private int id_tecnico;
	public int getId_tecnico() {return id_tecnico;}
	public void setId_tecnico(int id_tecnico) {this.id_tecnico = id_tecnico;}
	
	private String nome_tecnico;
	public String getNome_tecnico() {return nome_tecnico;}
	public void setNome_tecnico(String nome_tecnico) {this.nome_tecnico = nome_tecnico;}
	
	private String cargo_tecnico;
	public String getCargo_tecnico() {return cargo_tecnico;}
	public void setCargo_tecnico(String cargo_tecnico) {this.cargo_tecnico = cargo_tecnico;}
	
	//M�TODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_EQUIPE_T�CNICA VALUES ("+getId_projeto()+","+getId_tecnico()+",'"+getNome_tecnico()+"','"+getCargo_tecnico()+"')");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ATEN��O! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
		}
	}
	
	//M�TODO EXCLUIR
	public void Excluir(){
		
		try{
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("DELETE FROM TB_EQUIPE_T�CNICA WHERE ID_T�CNICO = "+getId_tecnico()+" AND ID_PROJETO = "+getId_projeto()+"");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "ATEN��O! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
		}		
	}				
	
	//M�TODO LISTAR - T�CNICOS
	public TableModel Listar_T�cnicos(){
		
		try {
			//VALIDANDO SE TA ALI NAO TA AQUI UTILIZANDO O COMANDO LEFT JOIN
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = ("SELECT TB_T�CNICOS.ID,TB_T�CNICOS.NOME,TB_T�CNICOS.CARGO FROM TB_T�CNICOS LEFT JOIN TB_EQUIPE_T�CNICA ON (TB_T�CNICOS.ID = TB_EQUIPE_T�CNICA.ID_T�CNICO) WHERE (TB_EQUIPE_T�CNICA.ID_PROJETO <> "+getId_projeto()+" OR TB_EQUIPE_T�CNICA.ID_T�CNICO <> TB_T�CNICOS.ID) OR TB_EQUIPE_T�CNICA.ID_T�CNICO IS NULL"); 
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
	
	//M�TODO LISTAR - INTEGRANTES DA EQUIPE
	public TableModel Listar_Integrantes_Equipe(){	
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT ID = ID_T�CNICO, NOME, CARGO FROM TB_EQUIPE_T�CNICA WHERE ID_PROJETO = "+getId_projeto()+"";
			stmt = conexao.createStatement();
			rs = stmt.executeQuery(sql);
			return resutSetToTableModel(rs);
		} catch (Exception e) {
			return null;
		}		
	}
	public static TableModel resutSetToTableModel1(ResultSet rs) {
		
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