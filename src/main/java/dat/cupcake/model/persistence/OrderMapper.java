package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.*;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderMapper {
    ConnectionPool connectionPool;
    
    public OrderMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
    
    public Order[] getOrders() throws DatabaseException {
        UserMapper userMapper = new UserMapper(connectionPool);
        ToppingMapper toppingMapper = new ToppingMapper(connectionPool);
        BottomMapper bottomMapper = new BottomMapper(connectionPool);
        int rows = 0;
        String rowCountSql = "SELECT count(*) FROM orders";
    
        Order[] orders = null;
        String sql =
                "SELECT order_id, user_id, status, date, SUM(topping_price + bottom_price) total_price, topping_id, bottom_id FROM orders " +
                "INNER JOIN bottom " +
                "using(bottom_id) " +
                "INNER JOIN topping " +
                "using(topping_id)";
        
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(rowCountSql)) {
                ResultSet rs = ps.executeQuery();
                rs.next();
                rows = rs.getInt("count(*)");
                orders = new Order[rows];
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
                    int orderId = rs.getInt("order_id");
                    int userId = rs.getInt("user_id");
                    Status status = Status.valueOf(rs.getString("status"));
                    LocalDateTime date = LocalDateTime.parse(rs.getString("date"));
                    int price = rs.getInt("price");
                    int toppingId = rs.getInt("topping_id");
                    int bottomId = rs.getInt("bottom_id");
                    //Create the individual order. The foreign keys gets stored in the corresponding object temporarily.
                    Order order = new Order(orderId, new User(userId), status, date, price, new Topping(toppingId), new Bottom(bottomId));
                    orders[rowCount] = order;
                    rowCount++;
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        for (Order order : orders) {
            order.setUser(userMapper.getUser(order.getUser().getUserId()));
            order.setTopping(toppingMapper.readTopping(order.getTopping().getId()));
            order.setBottom(bottomMapper.readBottom(order.getBottom().getId()));
        }
        return orders;
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
                "WHERE order_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int toppingId = rs.getInt("topping_id");
                    int bottomId = rs.getInt("bottom_id");
                    Status status = Status.valueOf(rs.getString("status"));
                    LocalDateTime date = LocalDateTime.parse(rs.getString("date"));
                    order = new Order(orderId, new User(userId), status, date, new Topping(toppingId), new Bottom(bottomId));
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        UserMapper userMapper = new UserMapper(connectionPool);
        ToppingMapper toppingMapper = new ToppingMapper(connectionPool);
        BottomMapper bottomMapper = new BottomMapper(connectionPool);
        order.setUser(userMapper.getUser(order.getUser().getUserId()));
        order.setTopping(toppingMapper.readTopping(order.getTopping().getId()));
        order.setBottom(bottomMapper.readBottom(order.getBottom().getId()));
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
}
