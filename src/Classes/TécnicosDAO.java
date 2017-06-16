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

public class TécnicosDAO {
	
	//GETTERS E SETTERS
	private int id;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private String nome;
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	
	private String cargo;
	public String getCargo() {return cargo;}
	public void setCargo(String cargo) {this.cargo = cargo;}
	
	private String ordena_tipo = "ID";
	public String getOrdena_tipo() {return ordena_tipo;}
	public void setOrdena_tipo(String ordena_tipo) {this.ordena_tipo = ordena_tipo;}

	private String ordena_ordem = "ASC";
	public String getOrdena_ordem() {return ordena_ordem;}
	public void setOrdena_ordem(String ordena_ordem) {this.ordena_ordem = ordena_ordem;}
	
	//MÉTODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_TÉCNICOS VALUES ('"+getNome()+"','"+getCargo()+"')");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, getNome()+" Não Foi Cadastrado(a) No Banco de Dados! =(");
		}
	}
	
	//MÉTODO ALTERAR
	public void Alterar(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_TÉCNICOS SET NOME = '"+getNome()+"', CARGO = '"+getCargo()+"' WHERE ID = "+getId()+"");			
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getNome()+" Não Foi Alterado(a) No Banco de Dados! =(");
		}		
	}

	//MÉTODO EXCLUIR
	public void Excluir(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("DELETE FROM TB_TÉCNICOS WHERE ID = '"+getId()+"'");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getNome()+" Não Foi Excluído(a) No Banco de Dados! =(");
		}		
	}
	
	//MÉTODO LISTAR	
	public TableModel Listar(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT * FROM TB_TÉCNICOS ORDER BY "+getOrdena_tipo()+" "+getOrdena_ordem()+"";
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