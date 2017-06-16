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
	
	private int n�_pacote;
	public int getN�_pacote() {return n�_pacote;}
	public void setN�_pacote(int n�_pacote) {this.n�_pacote = n�_pacote;}		
	
	private String descri��o;	
	public String getDescri��o() {return descri��o;}
	public void setDescri��o(String descri��o) {this.descri��o = descri��o;}
	
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
	
	private String situa��o;
	public String getSitua��o() {return situa��o;}
	public void setSitua��o(String situa��o) {this.situa��o = situa��o;}
	
	//M�TODO CADASTRAR
	public void Cadastrar() {

		try {
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("INSERT INTO TB_PACOTES_ENTREGA (N�_PACOTE, DESCRI��O, DATA_CADASTRO, FASE, CUSTO, DATA_PREVISTA_ENTREGA, ID_PROJETO, SITUA��O) VALUES ("+getN�_pacote()+",'"+getDescri��o()+"','"+getData_cadastro()+"','"+getFase()+"',"+getCusto()+",'"+getData_prevista_entrega()+"',"+getId_projeto()+",'"+getSitua��o()+"')");
			JOptionPane.showMessageDialog(null, "Pacote ("+getN�_pacote()+") - ''"+getDescri��o()+"'' Cadastrado Com Sucesso! =)");
			st.close();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ATEN��O! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
		}
	}
	
	//M�TODO ATUALIZAR
		public void Atualizar() {

			try {
				Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
				Statement st = string_conexao.createStatement();
				st.execute("UPDATE TB_PACOTES_ENTREGA SET DESCRI��O = '"+getDescri��o()+"', DATA_CADASTRO = '"+getData_cadastro()+"', FASE = '"+getFase()+"', CUSTO = "+getCusto()+", DATA_PREVISTA_ENTREGA = '"+getData_prevista_entrega()+"' WHERE ID_PROJETO = "+getId_projeto()+" AND N�_PACOTE = "+getN�_pacote()+"");
				JOptionPane.showMessageDialog(null, "Pacote ("+getN�_pacote()+") - ''"+getDescri��o()+"'' Atualizado Com Sucesso! =)");
				st.close();
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "ATEN��O! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
			}
		}
	
	//M�TODO CANCELAR
	public void Cancelar(){
		
		try{
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_PACOTES_ENTREGA SET SITUA��O = '"+getSitua��o()+"' WHERE ID_PROJETO = "+getId_projeto()+" AND N�_PACOTE = "+getN�_pacote()+"");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "ATEN��O! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
		}		
	}	
	//M�TODO ENTREGAR
	public void Entregar(){
		
		try{
			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
			Statement st = string_conexao.createStatement();
			st.execute("UPDATE TB_PACOTES_ENTREGA SET SITUA��O = '"+getSitua��o()+"', DATA_REAL_ENTREGA = '"+getData_real_entrega()+"' WHERE ID_PROJETO = "+getId_projeto()+" AND N�_PACOTE = "+getN�_pacote()+"");
			JOptionPane.showMessageDialog(null, "Pacote Entregue Com Sucesso! =)");
			st.close();
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "ATEN��O! PROBLEMA AO CADASTRAR NO BANCO DE DADOS. =(");
		}		
	}
	
	//M�TODO LISTAR - CASOS DE USO J� CADASTRADOS NESTE PROJETO
	public TableModel ListaCasos_de_Uso(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = ("SELECT ID, CSU FROM TB_CASOS_DE_USO WHERE ID_PROJETO = "+getId_projeto()+" AND N�_PACOTE = 0");									
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
	
	//M�TODO LISTAR - CASOS DE USO DO PACOTE
	public TableModel ListaCasos_de_Uso_Entrega(){
		
		try {
			Statement stmt = null;
			ResultSet rs = null;
			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
			String sql = "SELECT ID, CSU FROM TB_CASOS_DE_USO WHERE ID_PROJETO = "+getId_projeto()+" AND N�_PACOTE = "+getN�_pacote()+"";
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
	
	//M�TODO LISTAR PACOTES SALVOS
	public TableModel Listar_Pacotes_Salvos(){
  		
  		try {
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
  			String sql = "SELECT N�_PACOTE, DESCRI��O, CADASTRO = DATA_CADASTRO, FASE, CUSTO_R$ = CUSTO, PREVIS�O = DATA_PREVISTA_ENTREGA, SITUA��O, ENTREGUE = DATA_REAL_ENTREGA FROM TB_PACOTES_ENTREGA WHERE ID_PROJETO = "+getId_projeto()+"";
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