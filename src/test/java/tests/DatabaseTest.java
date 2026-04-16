package tests;

import utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {

        try {

            Connection conexao = DatabaseUtils.conectar();

            Statement stmt = conexao.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("---------------------");
            }

            conexao.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
