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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
        LinkedHashMap<Order, ArrayList<Order>> orderAndAmount = new LinkedHashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
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
        for (Order order : orders) {
            boolean alreadyExists = false;
            for (Order order2 : orderAndAmount.keySet()) {
                alreadyExists = order.getTopping().equals(order2.getTopping()) && order.getBottom().equals(order2.getBottom());
                if (alreadyExists) {
                    orderAndAmount.get(order2).add(order);
                    break;
                }
            }
            if (!alreadyExists) {
                orderAndAmount.put(order, new ArrayList<>());
            }
        }
        request.getSession().setAttribute("ordersMap", orderAndAmount);
        request.setAttribute("toppings", toppings);
        request.setAttribute("bottoms", bottoms);
        request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String[] orderParameters = request.getParameterValues("order id");
        String[] toppingParameters = request.getParameterValues("topping");
        String[] bottomParameters = request.getParameterValues("bottom");
        Order[] orders = new Order[0];
        int amountOfOrders = 0;
        if (orderParameters != null) {
            for (String orderParameter : orderParameters) {
                String[] orderIds = orderParameter.split("-");
                amountOfOrders += orderIds.length;
            }
        }
        try {
            int loopCounter = 0;
            orders = new Order[amountOfOrders];
            for (int i = 0; i < orderParameters.length; i++) {
                int toppingId = Integer.parseInt(toppingParameters[i]);
                int bottomId = Integer.parseInt(bottomParameters[i]);
                for (String orderParameter : orderParameters[i].split("-")) {
                    int orderId = Integer.parseInt(orderParameter);
                    orders[loopCounter] = new Order(orderId, user, Status.NOT_SUBMITTED, LocalDateTime.now(), new Topping(toppingId), new Bottom(bottomId));
                    loopCounter++;
                }
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        /*try {
            orders = new Order[amountOfOrders];
            for (int i = 0; i < amountOfOrders; i++) {
                int orderId = Integer.parseInt(orderParameters[i]);
                int toppingId = Integer.parseInt(toppingParameters[i]);
                int bottomId = Integer.parseInt(bottomParameters[i]);
                orders[i] = new Order(orderId, user, Status.NOT_SUBMITTED, LocalDateTime.now(), new Topping(toppingId), new Bottom(bottomId));
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }*/
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
                save(user, orders);
                break;
            case "submit":
                submit(user, orders);
                break;
            case "new order":
                save(user, orders);
                newOrder(user);
                break;
            case "add to order":
                addToOrder(user, orders, value);
                break;
            case "remove from order":
                removeFromOrder(user, orders, value);
                break;
            default:
                break;
        }
        response.sendRedirect("order");
    }
    
    private void remove(User user, int value) {
        try {
            Order order = null;
            Order[] orders = orderMapper.getOrdersByUser(user);
            for (Order tempOrder : orders) {
                if (tempOrder.getOrderId() == value) {
                    order = tempOrder;
                }
            }
            if (order != null) {
                order.setStatus(Status.CANCELLED);
                orderMapper.updateOrderStatus(order);
            }
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
    
    private void save(User user, Order[] orders) {
        try {
            for (Order order : orders) {
                orderMapper.updateOrder(order);
            }
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
    
    private void submit(User user, Order[] orders) {
        try {
            for (Order order : orders) {
                order.setStatus(Status.SUBMITTED);
                orderMapper.updateOrderStatus(order);
            }
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
        save(user, orders);
    }
    
    private void newOrder(User user) {
        Order order = new Order(0, user, Status.NOT_SUBMITTED, LocalDateTime.now(), new Topping(1), new Bottom(1));
        try {
            orderMapper.createOrder(order);
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
    
    private void addToOrder(User user, Order[] orders, int orderId) {
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println();
        System.out.println(orderId);
    }
    
    private void removeFromOrder(User user, Order[] orders, int orderId) {
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println();
        System.out.println(orderId);
    }
}
