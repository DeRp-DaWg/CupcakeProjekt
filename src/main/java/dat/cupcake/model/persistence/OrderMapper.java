package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.*;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderMapper {
    ConnectionPool connectionPool;
    
    public OrderMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
    
    public Order[] getOrders() throws DatabaseException {
        UserMapper userMapper = new UserMapper(connectionPool);
        
        ArrayList<Order> orders = new ArrayList<>();
        String sql =
                "SELECT * FROM orders " +
                "INNER JOIN bottom " +
                "using(bottom_id) " +
                "INNER JOIN topping " +
                "using(topping_id)";
        
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    int userId = rs.getInt("user_id");
                    Status status = Status.valueOf(rs.getString("status"));
                    LocalDateTime date = rs.getObject("date", LocalDateTime.class);
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    Order order = new Order(orderId, new User(userId), status, date,
                            new Topping(toppingId, toppingPrice, toppingName),
                            new Bottom(bottomId, bottomPrice, bottomName));
                    orders.add(order);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        for (Order order : orders) {
            order.setUser(userMapper.getUser(order.getUser().getUserId()));
        }
        return orders.toArray(new Order[0]);
    }
    
    public Order[] getOrdersByUser(User user) throws DatabaseException {
        ArrayList<Order> orders = new ArrayList<>();
        String sql =
                "SELECT * FROM orders " +
                "INNER JOIN bottom " +
                "using(bottom_id) " +
                "INNER JOIN topping " +
                "using(topping_id)";
        
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    Status status = Status.valueOf(rs.getString("status"));
                    LocalDateTime date = rs.getObject("date", LocalDateTime.class);
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    Order order = new Order(orderId, user, status, date,
                            new Topping(toppingId, toppingPrice, toppingName),
                            new Bottom(bottomId, bottomPrice, bottomName));
                    orders.add(order);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return orders.toArray(new Order[0]);
    }
    
    public Order[] getActiveOrdersByUser(User user) throws DatabaseException {
        ArrayList<Order> orders = new ArrayList<>();
        String sql =
                "SELECT * FROM orders " +
                "INNER JOIN bottom " +
                "using(bottom_id) " +
                "INNER JOIN topping " +
                "using(topping_id) " +
                "WHERE user_id = ? AND (status = 'PREPARING' OR status = 'AWAITING_PICKUP')";
        
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, user.getUserId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    Status status = Status.valueOf(rs.getString("status"));
                    LocalDateTime date = rs.getObject("date", LocalDateTime.class);
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    Order order = new Order(orderId, user, status, date,
                            new Topping(toppingId, toppingPrice, toppingName),
                            new Bottom(bottomId, bottomPrice, bottomName));
                    orders.add(order);
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return orders.toArray(new Order[0]);
    }
    
    public void createOrder(Order order) throws DatabaseException {
        String sql =
                "INSERT INTO orders (user_id, topping_id, bottom_id, status, date) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, order.getUser().getUserId());
                ps.setInt(2, order.getTopping().getId());
                ps.setInt(3, order.getBottom().getId());
                ps.setString(4, order.getStatus().toString());
                ps.setString(5, order.getDate().toString());
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }
    
    public Order readOrder(int orderId) throws DatabaseException {
        Order order = null;
        String sql =
                "SELECT * FROM orders " +
                "INNER JOIN bottom " +
                "using(bottom_id) " +
                "INNER JOIN topping " +
                "using(topping_id) " +
                "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    Status status = Status.valueOf(rs.getString("status"));
                    LocalDateTime date = rs.getObject("date", LocalDateTime.class);
                    int toppingId = rs.getInt("topping_id");
                    int toppingPrice = rs.getInt("topping_price");
                    String toppingName = rs.getString("topping_name");
                    int bottomId = rs.getInt("bottom_id");
                    int bottomPrice = rs.getInt("bottom_price");
                    String bottomName = rs.getString("bottom_name");
                    order = new Order(orderId, new User(userId), status, date,
                            new Topping(toppingId, toppingPrice, toppingName),
                            new Bottom(bottomId, bottomPrice, bottomName));
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return order;
    }
    
    public void updateOrder(Order order) throws DatabaseException {
        String sql =
                "UPDATE orders " +
                "SET topping_id = ?, bottom_id = ?, status = ?, date = ? " +
                "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, order.getTopping().getId());
                ps.setInt(2, order.getBottom().getId());
                ps.setString(3, order.getStatus().toString());
                ps.setString(4, order.getDate().toString());
                ps.setInt(5, order.getOrderId());
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }
    
    public void deleteOrder(Order order) throws DatabaseException {
        String sql =
                "DELETE FROM orders " +
                "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, order.getOrderId());
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }
    
    public void updateOrderStatus(Order order) throws DatabaseException {
        String sql =
                "UPDATE orders " +
                "SET status = ? " +
                "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, order.getStatus().toString());
                ps.setInt(2, order.getOrderId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
    }
}
