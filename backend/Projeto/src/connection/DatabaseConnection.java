package connection;

import java.sql.DriverManager;//biblioteca responsavel por trazer conexões com banco de dados pro java

public class DatabaseConnection {
    private static final String url = ""; //endereço do banco de dados
    private static final String user = "";
    private static final String password = "";

    public static java.sql.Connection conn;

    public static java.sql.Connection getConnect(){//metodo usado para conectar no banco de dados
        try {

            Class.forName("com.mysql.cj.jdbc.Driver"); //Carrega o driver JDBC do MySQL

            return DriverManager.getConnection( //aqui cria conexão com o banco de dados
                    url,
                    user,
                    password
            );

        } catch (Exception error){ //erros: senha errada, banco offline, driver não encontrado
            error.printStackTrace();
            return null;
        }
    }
}
