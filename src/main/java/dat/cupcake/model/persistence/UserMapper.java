package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMapper implements IUserMapper {
    ConnectionPool connectionPool;
    
    public UserMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    
    @Override
    public User login(String email, String password) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        
        User user = null;
        
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String role = rs.getString("role");
                    user = new User(userId, email, password, role);
                } else {
                    throw new DatabaseException("Wrong email or password");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }
    
    public User[] getUsers() throws DatabaseException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT user_id, email, role, balance FROM user";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("user_id");
                    String email = rs.getString("email");
                    String role = rs.getString("role");
                    int balance = rs.getInt("balance");
                    User user = new User(id, email, role, balance);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error getting all users");
        }
        return users.toArray(new User[0]);
    }
    
    public User getUser(User user) throws DatabaseException {
        return null;
    }
    
    public User getUser(int userId) throws DatabaseException {
        return getUser(new User(userId, null, null, null));
    }
    
    @Override
    public void createUser(User user) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        String sql = "INSERT INTO user (email, password, role, balance) values (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole());
                ps.setInt(4, user.getBalance());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected < 1) {
                    throw new DatabaseException("The user with email = " + user.getEmail() + " could not be inserted into the database");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert email into database");
        }
    }
    
    
}
