package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.Order;
import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.OrderMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "vieworders", value = "/vieworders")
public class ViewOrdersServlet extends HttpServlet {
    private ConnectionPool connectionPool;
    
    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderMapper orderMapper = new OrderMapper(connectionPool);
        Order[] orders = new Order[0];
        try {
            orders = orderMapper.getOrders();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("WEB-INF/vieworders.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user id"));
        OrderMapper orderMapper = new OrderMapper(connectionPool);
        Order[] orders = new Order[0];
        try {
            orders = orderMapper.getOrdersByUser(new User(userId));
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("WEB-INF/vieworders.jsp").forward(request, response);
    }
}
