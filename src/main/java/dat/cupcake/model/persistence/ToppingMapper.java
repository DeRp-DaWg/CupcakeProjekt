package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.Topping;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToppingMapper {
    ConnectionPool connectionPool;
    
    public ToppingMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
    
    public Topping[] getToppings() throws DatabaseException {
        Topping[] toppings;
        int rows = 0;
        String rowCountSql = "SELECT count(*) FROM topping";
        String sql = "SELECT * FROM topping";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(rowCountSql)) {
                ResultSet rs = ps.executeQuery();
                rs.next();
                rows = rs.getInt("count(*)");
                toppings = new Topping[rows];
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                int rowCount = 0;
                while (rs.next()) {
                    int id = rs.getInt("topping_id");
                    int price = rs.getInt("topping_price");
                    String name = rs.getString("topping_name");
                    Topping topping = new Topping(id, price, name);
                    toppings[rowCount] = topping;
                    rowCount++;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return toppings;
    }
    
    public Topping readTopping(int toppingId) throws DatabaseException {
        Topping topping = null;
        String sql =
                "SELECT * FROM topping " +
                "WHERE topping_id = ?";
        String toppingName = null;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, toppingId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    toppingName = rs.getString("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    topping = new Topping(toppingId, toppingPrice, toppingName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, e.getMessage());
        }
        if (toppingName == null) {
            throw new DatabaseException("Topping with id: " + toppingId + " was not found in the database.");
        }
        return topping;
    }
    
    public Topping readToppingByName(String toppingName) throws DatabaseException {
        Topping topping = null;
        String sql =
                "SELECT * FROM topping " +
                "WHERE topping_name = ?";
        int toppingId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, toppingName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    topping = new Topping(toppingId, toppingPrice, toppingName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, e.getMessage());
        }
        if (toppingId == 0) {
            throw new DatabaseException("Topping with name: " + toppingName + " was not found in the database.");
        }
        return topping;
    }
    
    public void createTopping(Topping topping) throws DatabaseException {
        String findToppingSql =
                "SELECT * FROM topping " +
                "WHERE topping_id = ?";
        String sql =
                "INSERT INTO topping (topping_price, topping_name) " +
                "VALUES (?, ?)";
        
        boolean toppingFound = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(findToppingSql)) {
                ps.setInt(1, topping.getId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    toppingFound = true;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("An error occurred while trying to find topping: " + topping);
        }
        
        if (!toppingFound) {
            try (Connection connection = connectionPool.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, topping.getPrice());
                    ps.setString(2, topping.getName());
                    ps.executeUpdate();
                }
            }
            catch (SQLException e) {
                throw new DatabaseException("An error occurred while trying to create topping: " + topping);
            }
        }
    }
    
    public void deleteTopping(Topping topping) throws DatabaseException {
        String sql =
                "DELETE FROM topping " +
                "WHERE topping_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, topping.getId());
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }
}
