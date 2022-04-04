package dat.cupcake.model.persistence;

import dat.cupcake.model.entities.Order;
import dat.cupcake.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderMapper {
    ConnectionPool connectionPool;
    
    public OrderMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }
    
    public Order[] getOrders() throws DatabaseException {
        Order[] orders = null;
        
        String sql = "SELECT * FROM orders";
        
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
            
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e, "Something went wrong");
        }
        return orders;
    }
}
