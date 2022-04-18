package Domashka8;

import java.sql.*;

public class SqliteExamples {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:geekbrains.db");
            Statement statement = connection.createStatement();

            statement.executeUpdate("update faculties set name = 'Тест1' where id = 1");

            ResultSet resultSet = statement.executeQuery("select * from students");

            while (resultSet.next()) {
                System.out.print(resultSet.getInt("id"));
                System.out.print(" ");
                System.out.print(resultSet.getString("name"));
                System.out.println();
            }

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into students (name, score, fac_id) values (?, ?, ?)");
            connection.setAutoCommit(false);
            preparedStatement.setString(1, "Олег");
            preparedStatement.setInt(2, 10);
            preparedStatement.setInt(3, 12);
            preparedStatement.addBatch();
            preparedStatement.setString(1, "Anton");
            preparedStatement.setInt(2, 10);
            preparedStatement.setInt(3, 12);
            preparedStatement.addBatch();
            preparedStatement.setString(1, "Anton");
            preparedStatement.setInt(2, 10);
            preparedStatement.setInt(3, 12);
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}