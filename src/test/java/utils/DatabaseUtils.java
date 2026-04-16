package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseUtils {

    public static Connection conectar() throws Exception {

        Properties properties = new Properties();

        InputStream input = DatabaseUtils.class
                .getClassLoader()
                .getResourceAsStream("config/database.properties");

        if (input == null) {
            throw new RuntimeException("Arquivo database.properties não encontrado.");
        }

        properties.load(input);

        String url = properties.getProperty("db.url");
        String usuario = properties.getProperty("db.user");
        String senha = properties.getProperty("db.password");

        return DriverManager.getConnection(url, usuario, senha);
    }
}