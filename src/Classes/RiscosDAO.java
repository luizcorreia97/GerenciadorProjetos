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
	
	private String descri��o;
	public String getDescri��o() {return descri��o;}
	public void setDescri��o(String descri��o) {this.descri��o = descri��o;}
	
	private String plano_de_a��o;
	public String getPlano_de_a��o() {return plano_de_a��o;}
	public void setPlano_de_a��o(String plano_de_a��o) {this.plano_de_a��o = plano_de_a��o;}
	
	//M�TODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_RISCOS VALUES ("+getId_projeto()+",'"+getDescri��o()+"','"+getPlano_de_a��o()+"')");
			JOptionPane.showMessageDialog(null, "''"+getDescri��o()+"'' Cadastrado Com Sucesso! =)");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, getDescri��o()+"N�o Foi Cadastrado(a) No Banco de Dados! =(");
		}
	}
	
	//M�TODO ALTERAR
	public void Alterar(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_RISCOS SET DESCRI��O = '"+getDescri��o()+"', PLANO_DE_A��O = '"+getPlano_de_a��o()+"' WHERE ID = "+getId()+" AND ID_PROJETO = "+getId_projeto()+"");			
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getDescri��o()+"N�o Foi Alterado(a) No Banco de Dados! =(");
		}		
	}

	//M�TODO EXCLUIR
	public void Excluir(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("DELETE FROM TB_RISCOS WHERE ID = '"+getId()+"'");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getDescri��o()+"N�o Foi Exclu�do(a) No Banco de Dados! =(");
		}		
	}
	
	//M�TODO LISTAR
	public TableModel Listar(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT ID, DESCRI��O, PLANO_DE_A��O FROM TB_RISCOS WHERE ID_PROJETO = "+getId_projeto()+"";
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