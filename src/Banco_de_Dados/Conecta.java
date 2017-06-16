
package Banco_de_Dados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conecta {

	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection
				("jdbc:sqlserver://localhost:1433;databaseName=INTEGRADOR_3;user=sa;password=thaliatreuk");
	}
}