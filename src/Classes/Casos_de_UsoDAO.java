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

public class Casos_de_UsoDAO {
	
	//GETTERS E SETTERS
 	private int id;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private int id_projeto;
	public int getId_projeto() {return id_projeto;}
	public void setId_projeto(int id_projeto) {this.id_projeto = id_projeto;}
	
	private int n�_pacote;
	public int getN�_pacote() {return n�_pacote;}
	public void setN�_pacote(int n�_pacote) {this.n�_pacote = n�_pacote;}	
	
	private String CSU;
	public String getCSU() {return CSU;}
	public void setCSU(String cSU) {CSU = cSU;}
	
	private String descri��o;	
	public String getDescri��o() {return descri��o;}
	public void setDescri��o(String descri��o) {this.descri��o = descri��o;}
	
	//M�TODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_CASOS_DE_USO VALUES ("+getId_projeto()+",'"+getCSU()+"','"+getDescri��o()+"',"+0+")");
			JOptionPane.showMessageDialog(null, "''"+getCSU()+"'' Cadastrado Com Sucesso! =)");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, getCSU()+"N�o Foi Cadastrado(a) No Banco de Dados! =(");
		}
	}
	
	//M�TODO ALTERAR
	public void Alterar(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_CASOS_DE_USO SET CSU = '"+getCSU()+"', DESCRI��O = '"+getDescri��o()+"' WHERE ID = "+getId()+" AND ID_PROJETO = "+getId_projeto()+"");			
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getCSU()+"N�o Foi Alterado(a) No Banco de Dados! =(");
		}		
	}
	//M�TODO ALTERAR UMA CASO DE USO PARA UM PACOTE
	//VAI SETAR O N� DO PACOTE NESSE CASO DE USO
	public void Alterar_CSU_Com_Pacote_Normal(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_CASOS_DE_USO SET N�_PACOTE = "+getN�_pacote()+" WHERE ID = "+getId()+" AND N�_PACOTE = 0 AND ID_PROJETO = "+getId_projeto()+"");																		
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "N�o Foi Alterado(a) No Banco de Dados! =(");
		}		
	}
	//M�TODO ALTERAR UMA CASO DE USO PARA 0
	//VAI SETAR 0 AONDE TINHA O N� DO PACOTE POIS AGORA N�O TEM MAIS
	public void Alterar_CSU_Sem_Pacote_Normal(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_CASOS_DE_USO SET N�_PACOTE = 0 WHERE ID = "+getId()+" AND N�_PACOTE = "+getN�_pacote()+" AND ID_PROJETO = "+getId_projeto()+"");																		
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "N�o Foi Alterado(a) No Banco de Dados! =(");
		}		
	}
	//M�TODO ALTERAR TODOS OS CASO DE USO DESSE PROJETO PARA UM PACOTE S�
	//VAI SETAR O N� DO PACOTE NESSES CASOS DE USO
		public void Alterar_CSU_Com_Pacote_Ciclo_Cascata(){
			
			try{
				Connection string_conexao = new Conecta().getConnection();
				Statement st = string_conexao.createStatement();
				st.execute("UPDATE TB_CASOS_DE_USO SET N�_PACOTE = "+getN�_pacote()+" WHERE ID_PROJETO = "+getId_projeto()+"");																		
				st.close();
			}
			catch (SQLException e){
				JOptionPane.showMessageDialog(null, "N�o Foi Alterado(a) No Banco de Dados! =(");
			}		
	}
		//M�TODO ALTERAR TODOS OS CASO DE USO DESSE PROJETO PARA 0
		//VAI SETAR 0 EM TODOS CASOS DE USO DESTE PROJETO
		//AONDE TINHA O N� DO PACOTE POIS AGORA N�O TEM MAIS PACOTE
	public void Alterar_CSU_Sem_Pacote_Ciclo_Cascata(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_CASOS_DE_USO SET N�_PACOTE = 0 WHERE ID_PROJETO = "+getId_projeto()+"");																		
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "N�o Foi Alterado(a) No Banco de Dados! =(");
		}		
	}

	//M�TODO EXCLUIR
	public void Excluir(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("DELETE FROM TB_CASOS_DE_USO WHERE ID = "+getId()+" AND ID_PROJETO = "+getId_projeto()+"");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getCSU()+"N�o Foi Exclu�do(a) No Banco de Dados! =(");
		}		
	}
	
	//M�TODO LISTAR	
	public TableModel Listar(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT ID, CSU, DESCRI��O FROM TB_CASOS_DE_USO WHERE ID_PROJETO = "+getId_projeto()+"";
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