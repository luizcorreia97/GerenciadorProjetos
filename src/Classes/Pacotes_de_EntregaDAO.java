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

public class Pacotes_de_EntregaDAO {
	
	//GETTERS E SETTERS
	private int id_pacote;
	public int getId_pacote() {return id_pacote;}
	public void setId_pacote(int id_pacote) {this.id_pacote = id_pacote;}
	
	private int id_projeto;
	public int getId_projeto() {return id_projeto;}
	public void setId_projeto(int id_projeto) {this.id_projeto = id_projeto;}
	
	private int nº_pacote;
	public int getNº_pacote() {return nº_pacote;}
	public void setNº_pacote(int nº_pacote) {this.nº_pacote = nº_pacote;}		
	
	private String descrição;	
	public String getDescrição() {return descrição;}
	public void setDescrição(String descrição) {this.descrição = descrição;}
	
	private String data_cadastro;
	public String getData_cadastro() {return data_cadastro;}
	public void setData_cadastro(String data_cadastro) {this.data_cadastro = data_cadastro;}
	
	private String fase;
	public String getFase() {return fase;}
	public void setFase(String fase) {this.fase = fase;}
	
	private float custo;
	public float getCusto() {return custo;}
	public void setCusto(float custo) {this.custo = custo;}
	
	private String data_prevista_entrega;
	public String getData_prevista_entrega() {return data_prevista_entrega;}
	public void setData_prevista_entrega(String data_prevista_entrega) {this.data_prevista_entrega = data_prevista_entrega;}
	
	private String data_real_entrega;
	public String getData_real_entrega() {return data_real_entrega;}
	public void setData_real_entrega(String data_real_entrega) {this.data_real_entrega = data_real_entrega;}
	
	private String situação;
	public String getSituação() {return situação;}
	public void setSituação(String situação) {this.situação = situação;}
	
	//MÉTODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_PACOTES_ENTREGA (Nº_PACOTE, DESCRIÇÃO, DATA_CADASTRO, FASE, CUSTO, DATA_PREVISTA_ENTREGA, ID_PROJETO, SITUAÇÃO) VALUES ("+getNº_pacote()+",'"+getDescrição()+"','"+getData_cadastro()+"','"+getFase()+"',"+getCusto()+",'"+getData_prevista_entrega()+"',"+getId_projeto()+",'"+getSituação()+"')");
			JOptionPane.showMessageDialog(null, "Pacote ("+getNº_pacote()+") - ''"+getDescrição()+"'' Cadastrado Com Sucesso! =)");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ATENÇÃO! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
		}
	}
	
	//MÉTODO ATUALIZAR
		public void Atualizar() {

			try {
				Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
				Statement st = string_conexao.createStatement();
				st.execute("UPDATE TB_PACOTES_ENTREGA SET DESCRIÇÃO = '"+getDescrição()+"', DATA_CADASTRO = '"+getData_cadastro()+"', FASE = '"+getFase()+"', CUSTO = "+getCusto()+", DATA_PREVISTA_ENTREGA = '"+getData_prevista_entrega()+"' WHERE ID_PROJETO = "+getId_projeto()+" AND Nº_PACOTE = "+getNº_pacote()+"");
				JOptionPane.showMessageDialog(null, "Pacote ("+getNº_pacote()+") - ''"+getDescrição()+"'' Atualizado Com Sucesso! =)");
				st.close();
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "ATENÇÃO! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
			}
		}
	
	//MÉTODO CANCELAR
	public void Cancelar(){
		
		try{
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_PACOTES_ENTREGA SET SITUAÇÃO = '"+getSituação()+"' WHERE ID_PROJETO = "+getId_projeto()+" AND Nº_PACOTE = "+getNº_pacote()+"");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "ATENÇÃO! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
		}		
	}	
	//MÉTODO ENTREGAR
	public void Entregar(){
		
		try{
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_PACOTES_ENTREGA SET SITUAÇÃO = '"+getSituação()+"', DATA_REAL_ENTREGA = '"+getData_real_entrega()+"' WHERE ID_PROJETO = "+getId_projeto()+" AND Nº_PACOTE = "+getNº_pacote()+"");
			JOptionPane.showMessageDialog(null, "Pacote Entregue Com Sucesso! =)");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "ATENÇÃO! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
		}		
	}
	
	//MÉTODO LISTAR - CASOS DE USO JÁ CADASTRADOS NESTE PROJETO
	public TableModel ListaCasos_de_Uso(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = ("SELECT ID, CSU FROM TB_CASOS_DE_USO WHERE ID_PROJETO = "+getId_projeto()+" AND Nº_PACOTE = 0");									
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
	
	//MÉTODO LISTAR - CASOS DE USO DO PACOTE
	public TableModel ListaCasos_de_Uso_Entrega(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT ID, CSU FROM TB_CASOS_DE_USO WHERE ID_PROJETO = "+getId_projeto()+" AND Nº_PACOTE = "+getNº_pacote()+"";
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
	
	//MÉTODO LISTAR PACOTES SALVOS
	public TableModel Listar_Pacotes_Salvos(){
  		
  		try {
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
  			String sql = "SELECT Nº_PACOTE, DESCRIÇÃO, CADASTRO = DATA_CADASTRO, FASE, CUSTO_R$ = CUSTO, PREVISÃO = DATA_PREVISTA_ENTREGA, SITUAÇÃO, ENTREGUE = DATA_REAL_ENTREGA FROM TB_PACOTES_ENTREGA WHERE ID_PROJETO = "+getId_projeto()+"";
			stmt = conexao.createStatement();
  			rs = stmt.executeQuery(sql);
  			return resutSetToTableModel(rs);
  		} catch (Exception e) {
  			return null;
  		}		
  	}
  	public static TableModel resutSetToTableModel11(ResultSet rs) {
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