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
            ps.setInt(1, enti.getId());
            ps.setString(2, enti.getNome());
            ps.setObject(3, enti.getContato());

            ps.execute();
            ps.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
