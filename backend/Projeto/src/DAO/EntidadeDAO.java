package DAO;

import connection.DatabaseConnection;
import entities.Entidade;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntidadeDAO {

    public void inserirEntidade(Entidade enti){
        String sqlScript = "INSERT INTO ENTIDADE (IDENTIDADE, NOME, CONTATO_ID)  VALUES (?, ?, ?)";

        PreparedStatement ps = null;

        try {
            ps = DatabaseConnection.getConnect().prepareStatement(sqlScript);
            ps.setInt(1, 1);
            ps.setString(2, "nome");
            ps.setObject(3, "contato");

            ps.execute();
            ps.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
