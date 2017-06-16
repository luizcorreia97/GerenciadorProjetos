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
	
	private int nº_pacote;
	public int getNº_pacote() {return nº_pacote;}
	public void setNº_pacote(int nº_pacote) {this.nº_pacote = nº_pacote;}	
	
	private String CSU;
	public String getCSU() {return CSU;}
	public void setCSU(String cSU) {CSU = cSU;}
	
	private String descrição;	
	public String getDescrição() {return descrição;}
	public void setDescrição(String descrição) {this.descrição = descrição;}
	
	//MÉTODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_CASOS_DE_USO VALUES ("+getId_projeto()+",'"+getCSU()+"','"+getDescrição()+"',"+0+")");
			JOptionPane.showMessageDialog(null, "''"+getCSU()+"'' Cadastrado Com Sucesso! =)");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, getCSU()+"Não Foi Cadastrado(a) No Banco de Dados! =(");
		}
	}
	
	//MÉTODO ALTERAR
	public void Alterar(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_CASOS_DE_USO SET CSU = '"+getCSU()+"', DESCRIÇÃO = '"+getDescrição()+"' WHERE ID = "+getId()+" AND ID_PROJETO = "+getId_projeto()+"");			
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getCSU()+"Não Foi Alterado(a) No Banco de Dados! =(");
		}		
	}
	//MÉTODO ALTERAR UMA CASO DE USO PARA UM PACOTE
	//VAI SETAR O Nº DO PACOTE NESSE CASO DE USO
	public void Alterar_CSU_Com_Pacote_Normal(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_CASOS_DE_USO SET Nº_PACOTE = "+getNº_pacote()+" WHERE ID = "+getId()+" AND Nº_PACOTE = 0 AND ID_PROJETO = "+getId_projeto()+"");																		
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Não Foi Alterado(a) No Banco de Dados! =(");
		}		
	}
	//MÉTODO ALTERAR UMA CASO DE USO PARA 0
	//VAI SETAR 0 AONDE TINHA O Nº DO PACOTE POIS AGORA NÃO TEM MAIS
	public void Alterar_CSU_Sem_Pacote_Normal(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_CASOS_DE_USO SET Nº_PACOTE = 0 WHERE ID = "+getId()+" AND Nº_PACOTE = "+getNº_pacote()+" AND ID_PROJETO = "+getId_projeto()+"");																		
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Não Foi Alterado(a) No Banco de Dados! =(");
		}		
	}
	//MÉTODO ALTERAR TODOS OS CASO DE USO DESSE PROJETO PARA UM PACOTE SÓ
	//VAI SETAR O Nº DO PACOTE NESSES CASOS DE USO
		public void Alterar_CSU_Com_Pacote_Ciclo_Cascata(){
			
			try{
				Connection string_conexao = new Conecta().getConnection();
				Statement st = string_conexao.createStatement();
				st.execute("UPDATE TB_CASOS_DE_USO SET Nº_PACOTE = "+getNº_pacote()+" WHERE ID_PROJETO = "+getId_projeto()+"");																		
				st.close();
			}
			catch (SQLException e){
				JOptionPane.showMessageDialog(null, "Não Foi Alterado(a) No Banco de Dados! =(");
			}		
	}
		//MÉTODO ALTERAR TODOS OS CASO DE USO DESSE PROJETO PARA 0
		//VAI SETAR 0 EM TODOS CASOS DE USO DESTE PROJETO
		//AONDE TINHA O Nº DO PACOTE POIS AGORA NÃO TEM MAIS PACOTE
	public void Alterar_CSU_Sem_Pacote_Ciclo_Cascata(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_CASOS_DE_USO SET Nº_PACOTE = 0 WHERE ID_PROJETO = "+getId_projeto()+"");																		
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "Não Foi Alterado(a) No Banco de Dados! =(");
		}		
	}

	//MÉTODO EXCLUIR
	public void Excluir(){
		
		try{
			Connection string_conexao = new Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("DELETE FROM TB_CASOS_DE_USO WHERE ID = "+getId()+" AND ID_PROJETO = "+getId_projeto()+"");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, getCSU()+"Não Foi Excluído(a) No Banco de Dados! =(");
		}		
	}
	
	//MÉTODO LISTAR	
	public TableModel Listar(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT ID, CSU, DESCRIÇÃO FROM TB_CASOS_DE_USO WHERE ID_PROJETO = "+getId_projeto()+"";
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