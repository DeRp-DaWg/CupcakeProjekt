package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.Bottom;
import dat.cupcake.model.entities.Topping;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BottomMapper {
    ConnectionPool connectionPool;
    
    public BottomMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
    
    public Bottom readBottom(int bottomId) throws DatabaseException {
        Bottom bottom = null;
        String sql =
                "SELECT * FROM bottom " +
                "WHERE bottom_id = ?";
        String bottomName = null;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, bottomId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bottomName = rs.getString("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    bottom = new Bottom(bottomId, bottomPrice, bottomName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, e.getMessage());
        }
        if (bottomName == null) {
            throw new DatabaseException("Bottom with id: " + bottomId + " was not found in the database.");
        }
        return null;
    }
    
    public Bottom readBottomByName(String bottomName) throws DatabaseException {
        Bottom bottom = null;
        String sql =
                "SELECT * FROM bottom " +
                "WHERE bottom_name = ?";
        int bottomId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, bottomName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    bottom = new Bottom(bottomId, bottomPrice, bottomName);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, e.getMessage());
        }
        if (bottomId == 0) {
            throw new DatabaseException("Bottom: " + bottomName + " was not found in the database.");
        }
        return bottom;
    }
    
    public void createBottom(Bottom bottom) throws DatabaseException {
        String findBottomSql =
                "SELECT * FROM bottom " +
                "WHERE bottom_id = ?";
        String sql =
                "INSERT INTO bottom (bottom_price, bottom_name) " +
                "VALUES (?, ?)";
        
        boolean bottomFound = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(findBottomSql)) {
                ps.setInt(1, bottom.getId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bottomFound = true;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("An error occurred while trying to find bottom: " + bottom);
        }
        
        if (!bottomFound) {
            try (Connection connection = connectionPool.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, bottom.getPrice());
                    ps.setString(2, bottom.getName());
                    ps.executeUpdate();
                }
            }
            catch (SQLException e) {
                throw new DatabaseException("An error occurred while trying to create bottom: " + bottom);
            }
        }
    }
    
    public void deleteBottom(Bottom bottom) throws DatabaseException {
        String sql =
                "DELETE FROM bottom " +
                "WHERE bottom_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, bottom.getId());
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }
}
