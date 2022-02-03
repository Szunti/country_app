import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryApp {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/country_app?useSSL=false&serverTimezone=UTC";
    public static final String USER = "zoli";
    public static final String PASSWORD = "Test123!";

    public static final Path DATA_PATH = Path.of("src/main/resources/data.txt");

    public static void main(String[] args) {
//        createCountry();
//        fillCountries(DATA_PATH);
        System.out.println(giveInfoFromCountry(9));
        System.out.println(giveAllInfoFromCountry());
    }

    private static void updateCountry(int id, int population) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            String updatePopulation = "UPDATE country " +
                    "SET population = " + population + " WHERE id = " + id;
            statement.executeUpdate(updatePopulation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void fillCountries(Path dataPath) {
        try (BufferedReader reader = Files.newBufferedReader(dataPath)) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                String country = fields[0];
                String city = fields[1];
                int population = Integer.parseInt(fields[2]);
                insertCountryRow(country, city, population);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createCountry() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            String createCountry = "CREATE TABLE IF NOT EXISTS country (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "country VARCHAR(50)," +
                    "city VARCHAR(50)," +
                    "population INT" +
                    ");";
            statement.execute(createCountry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertCountryRow(String country, String city, int population) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            String insertCountry = "INSERT INTO country" +
                    "(country, city, population)" +
                    "VALUES" +
                    "('" + country + "','" + city + "'," + population + ")";
            int insertedRows = statement.executeUpdate(insertCountry);
        } catch (SQLException e) {
            e.printStackTrace();
        };
    }

    private static Country giveInfoFromCountry(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            String giveInfo = "SELECT country, city, population FROM country " +
                    "WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(giveInfo);
            resultSet.next();
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            int population = resultSet.getInt("population");
            return new Country(country, city, population);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Country> giveAllInfoFromCountry() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            String giveInfo = "SELECT country, city, population FROM country";
            ResultSet resultSet = statement.executeQuery(giveInfo);
            List<Country> countries = new ArrayList<>();
            while (resultSet.next()) {
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                int population = resultSet.getInt("population");
                countries.add(new Country(country, city, population));
            }
            return countries;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
