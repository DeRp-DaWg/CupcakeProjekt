package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.*;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.BottomMapper;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.OrderMapper;
import dat.cupcake.model.persistence.ToppingMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "order", value = "/order")
public class OrderServlet extends HttpServlet {
    private ConnectionPool connectionPool;
    private OrderMapper orderMapper;
    
    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        orderMapper = new OrderMapper(connectionPool);
        ToppingMapper toppingMapper = new ToppingMapper(connectionPool);
        BottomMapper bottomMapper = new BottomMapper(connectionPool);
        Order[] orders = new Order[0];
        Topping[] toppings = new Topping[0];
        Bottom[] bottoms = new Bottom[0];
        try {
            orders = orderMapper.getActiveOrdersByUser((User) request.getSession().getAttribute("user"));
            toppings = toppingMapper.getToppings();
            bottoms = bottomMapper.getBottoms();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("orders", orders);
        request.setAttribute("toppings", toppings);
        request.setAttribute("bottoms", bottoms);
        request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        //int toppingId = Integer.parseInt(request.getParameter("topping"));
        //int bottomId = Integer.parseInt(request.getParameter("bottom"));
        String[] orderParameter = request.getParameterValues("order id");
        String[] toppingParameter = request.getParameterValues("topping");
        String[] bottomParameter = request.getParameterValues("bottom");
        Order[] newOrders = new Order[toppingParameter.length];
        for (int i = 0; i < toppingParameter.length; i++) {
            int orderId = Integer.parseInt(orderParameter[i]);
            int toppingId = Integer.parseInt(toppingParameter[i]);
            int bottomId = Integer.parseInt(bottomParameter[i]);
            newOrders[i] = new Order(orderId, user, Status.PREPARING, LocalDateTime.now(), new Topping(toppingId), new Bottom(bottomId));
        }
        String[] buttonParameter = request.getParameter("button").split("-");
        String button = buttonParameter[0];
        int value = 0;
        try {
            value = Integer.parseInt(buttonParameter[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        switch (button) {
            case "remove":
                remove(user, value);
                break;
            case "save":
                save(user, newOrders);
                break;
            case "submit":
                submit(user);
                break;
            default:
                break;
        }
        response.sendRedirect("order");
    }
    
    private void remove(User user, int value) {
        try {
            Order orderToDelete = orderMapper.getOrdersByUser(user)[value];
            orderMapper.updateOrderStatus(orderToDelete);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
    
    private void save(User user, Order[] orders) {
        try {
            for (Order order : orders) {
                orderMapper.updateOrder(order);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
    
    private void submit(User user) {
    
    }
}
