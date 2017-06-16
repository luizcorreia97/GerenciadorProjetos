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

import Telas.PESQUISAR_PROJETOS;

public class ProjetosDAO {
	
	//GETTERS E SETTERS
	private int id_projeto;
	public int getId_projeto() {return id_projeto;}
	public void setId_projeto(int id_projeto) {this.id_projeto = id_projeto;}
	
	private String nome_projeto;
	public String getNome_projeto() {return nome_projeto;}
	public void setNome_projeto(String nome_projeto) {this.nome_projeto = nome_projeto;}
	
	private String gerente_projeto;
	public String getGerente_projeto() {return gerente_projeto;}
	public void setGerente_projeto(String gerente_projeto) {this.gerente_projeto = gerente_projeto;}
	
	private String data_prevista_inicio;
	public String getData_prevista_inicio() {return data_prevista_inicio;}
	public void setData_prevista_inicio(String data_prevista_inicio) {this.data_prevista_inicio = data_prevista_inicio;}
	
	private String data_real_inicio;
	public String getData_real_inicio() {return data_real_inicio;}
	public void setData_real_inicio(String data_real_inicio) {this.data_real_inicio = data_real_inicio;}
	
	private String data_prevista_entrega;
	public String getData_prevista_entrega() {return data_prevista_entrega;}
	public void setData_prevista_entrega(String data_prevista_entrega) {this.data_prevista_entrega = data_prevista_entrega;}
	
	private String data_real_entrega;
	public String getData_real_entrega() {return data_real_entrega;}
	public void setData_real_entrega(String data_real_entrega) {this.data_real_entrega = data_real_entrega;}
	
	private Float custo_total_previsto;
	public Float getCusto_total_previsto() {return custo_total_previsto;}
	public void setCusto_total_previsto(Float custo_total_previsto) {this.custo_total_previsto = custo_total_previsto;}
	
	private Float custo_total_real;
	public Float getCusto_total_real() {return custo_total_real;}
	public void setCusto_total_real(Float custo_total_real) {this.custo_total_real = custo_total_real;}
	
	private String ciclo_de_vida;
	public String getCiclo_de_vida() {return ciclo_de_vida;}
	public void setCiclo_de_vida(String ciclo_de_vida) {this.ciclo_de_vida = ciclo_de_vida;}
	
	private String situação;
	public String getSituação() {return situação;}
	public void setSituação(String situação) {this.situação = situação;}
	
	private String ordena_tipo = "Id";
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
  			st.execute("INSERT INTO TB_PROJETOS (NOME_PROJETO, GERENTE_PROJETO, DATA_PREVISTA_INICIO, DATA_PREVISTA_ENTREGA, CUSTO_TOTAL_PREVISTO, CICLO_DE_VIDA, SITUAÇÃO) VALUES ('"+getNome_projeto()+"','"+getGerente_projeto()+"','"+getData_prevista_inicio()+"','"+getData_prevista_entrega()+"',"+getCusto_total_previsto()+",'"+getCiclo_de_vida()+"','"+getSituação()+"')");
  			JOptionPane.showMessageDialog(null, "Projeto "+getNome_projeto()+" Cadastrado Com Sucesso! =)");
  			st.close();
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, " ATENÇÃO!! /nPROBLEMA NO BANCO AO CADASTRAR ESSE PROJETO. =(");
  		}
  	}
  	
  	//METÓDOS PARA ATUALIZAR
  	
  	//NOME DO PROJETO
  	public void Atualizar_Nome_Projeto() {

  		try {
  			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
  			Statement st = string_conexao.createStatement();
  			st.execute("UPDATE TB_PROJETOS SET NOME_PROJETO = '"+getNome_projeto()+"' WHERE ID_PROJETO = "+getId_projeto()+"");
  			JOptionPane.showMessageDialog(null, "Nome do Projeto Atualizado Com Sucesso! =)");
  			st.close();
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, " ATENÇÃO!! /nPROBLEMA NO BANCO AO CADASTRAR ESSE PROJETO. =(");
  		}
  	}
  //NOME DO GERENTE
  	public void Atualizar_Nome_Gerente() {

  		try {
  			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
  			Statement st = string_conexao.createStatement();
  			st.execute("UPDATE TB_PROJETOS SET GERENTE_PROJETO = '"+getGerente_projeto()+"' WHERE ID_PROJETO = "+getId_projeto()+"");
  			JOptionPane.showMessageDialog(null, "Gerente do Projeto Atualizado(a) Com Sucesso! =)");
  			st.close();
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, " ATENÇÃO!! /nPROBLEMA NO BANCO AO CADASTRAR ESSE PROJETO. =(");
  		}
  	}
  //DATA REAL DE INICIO DO PROJETO
  	public void Atualizar_Data_Real_Inicio_Projeto() {

  		try {
  			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
  			Statement st = string_conexao.createStatement();
  			st.execute("UPDATE TB_PROJETOS SET DATA_REAL_INICIO = '"+getData_real_inicio()+"' WHERE ID_PROJETO = "+getId_projeto()+"");
  			JOptionPane.showMessageDialog(null, "Data Real de Inicio do Projeto Atualizada Com Sucesso! =)");
  			st.close();
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, " ATENÇÃO!! /nPROBLEMA NO BANCO AO CADASTRAR ESSE PROJETO. =(");
  		}
  	}
    //CICLO DE VIDA DO PROJETO
  	public void Atualizar_Ciclo_De_Vida_Projeto() {

  		try {
  			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
  			Statement st = string_conexao.createStatement();
  			st.execute("UPDATE TB_PROJETOS SET CICLO_DE_VIDA = '"+getCiclo_de_vida()+"' WHERE ID_PROJETO = "+getId_projeto()+"");
  			JOptionPane.showMessageDialog(null, "Ciclo de Vida do Projeto Atualizado Com Sucesso! =)");
  			st.close();
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, " ATENÇÃO!! /nPROBLEMA NO BANCO AO CADASTRAR ESSE PROJETO. =(");
  		}
  	}
    //SITUAÇÃO DO PROJETO
  	public void Atualizar_Situação_Projeto() {

  		try {
  			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
  			Statement st = string_conexao.createStatement();
  			st.execute("UPDATE TB_PROJETOS SET SITUAÇÃO = '"+getSituação()+"' WHERE ID_PROJETO = "+getId_projeto()+"");
  			JOptionPane.showMessageDialog(null, "Situação do Projeto Atualizada Com Sucesso! =)");
  			st.close();
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, " ATENÇÃO!! /nPROBLEMA NO BANCO AO CADASTRAR ESSE PROJETO. =(");
  		}
  	}

    //METÓDO ENTREGAR O PROJETO
  	//ATUALIZA A DATA REAL DE ENTREGA E SITUAÇÃO RECEBE 'ENTREGUE'.
  	public void Entregar() {

  		try {
  			Connection string_conexao = new Banco_de_Dados.Conecta().getConnection();
  			Statement st = string_conexao.createStatement();
  			st.execute("UPDATE TB_PROJETOS SET DATA_REAL_ENTREGA = '"+getData_real_entrega()+"', SITUAÇÃO = '"+getSituação()+"' WHERE ID_PROJETO = "+getId_projeto()+"");
  			JOptionPane.showMessageDialog(null, "Projeto Entregue Com Sucesso! =)");
  			st.close();
  			
  		} catch (SQLException e) {
  			JOptionPane.showMessageDialog(null, " ATENÇÃO!! /nPROBLEMA NO BANCO AO CADASTRAR ESSE PROJETO. =(");
  		}
  	}

	//LISTAR PROJETOS SALVOS NO BANCO DE DADOS	
  	public TableModel Listar(){
  		
  		try {
  			Statement stmt = null;
  			ResultSet rs = null;
  			Connection conexao = new Banco_de_Dados.Conecta().getConnection();
  			String sql = "SELECT ID = ID_PROJETO, NOME = NOME_PROJETO, GERENTE = GERENTE_PROJETO, CICLO = CICLO_DE_VIDA, SITUAÇÃO FROM TB_PROJETOS ORDER BY "+getOrdena_tipo()+" "+getOrdena_ordem()+"";
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