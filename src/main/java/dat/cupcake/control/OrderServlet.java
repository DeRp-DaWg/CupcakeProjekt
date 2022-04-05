package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.Bottom;
import dat.cupcake.model.entities.Order;
import dat.cupcake.model.entities.Topping;
import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.BottomMapper;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.OrderMapper;
import dat.cupcake.model.persistence.ToppingMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "order", value = "/order")
public class OrderServlet extends HttpServlet {
    private ConnectionPool connectionPool;
    
    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderMapper orderMapper = new OrderMapper(connectionPool);
        ToppingMapper toppingMapper = new ToppingMapper(connectionPool);
        BottomMapper bottomMapper = new BottomMapper(connectionPool);
        Order[] orders = new Order[0];
        Topping[] toppings = new Topping[0];
        Bottom[] bottoms = new Bottom[0];
        try {
            orders = orderMapper.getOrdersByUser((User) request.getSession().getAttribute("user"));
            toppings = toppingMapper.getToppings();
            bottoms = bottomMapper.getBottoms();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute("orders", orders);
        request.setAttribute("toppings", toppings);
        request.setAttribute("bottoms", bottoms);
        request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int toppingId = Integer.parseInt(request.getParameter("topping"));
        int bottomId = Integer.parseInt(request.getParameter("bottom"));
        
    }
}
