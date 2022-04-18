package Domashka8;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseRepository {
    private String insertWeather = "insert into weather (city, localdate, temperature) values (?, ?, ?)";
    private static final String DB_PATH = "jdbc:sqlite:geekbrains.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean saveWeatherToDataBase(Weather weather) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);
            saveWeather.setString(1, weather.getCity());
            saveWeather.setString(2, weather.getLocalDate());
            saveWeather.setDouble(3, weather.getTemperature());
            return saveWeather.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new SQLException("Сохранение погоды в базу данных не выполнено!");
    }

    public void saveWeatherToDataBase(List<Weather> weatherList) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);
            for (Weather weather : weatherList) {
                saveWeather.setString(1, weather.getCity());
                saveWeather.setString(2, weather.getLocalDate());
                saveWeather.setDouble(3, weather.getTemperature());
                saveWeather.addBatch();
            }
            saveWeather.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //public List<Weather> getSavedToDBWeather() {
    //    try (Connection connection = DriverManager.getConnection(DB_PATH)) {
    //        //TODO: реализовать этот метод получения данных из таблицы погоды
    //    } catch (SQLException throwables) {
    //        throwables.printStackTrace();
    //    }
    //}

    public static void main(String[] args) throws SQLException {
        DataBaseRepository dataBaseRepository = new DataBaseRepository();
        dataBaseRepository.saveWeatherToDataBase(new Weather("Москва", "12.12.12", 12));

        dataBaseRepository.saveWeatherToDataBase(new ArrayList<>(Arrays.asList(
                new Weather("ttt1", "ttt1", 1),
                new Weather("ttt11", "ttt1", 1),
                new Weather("ttt111", "ttt1", 1),
                new Weather("ttt1111", "ttt1", 1))));
    }
}