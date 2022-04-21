package dat.cupcake.control;

import dat.cupcake.model.config.ApplicationStart;
import dat.cupcake.model.entities.User;
import dat.cupcake.model.exceptions.DatabaseException;
import dat.cupcake.model.persistence.ConnectionPool;
import dat.cupcake.model.persistence.OrderMapper;
import dat.cupcake.model.persistence.UserMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "viewaccounts", value = "/viewaccounts")
public class ViewAccountsServlet extends HttpServlet {
    private ConnectionPool connectionPool;
    
    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserMapper userMapper = new UserMapper(connectionPool);
        User[] users = new User[0];
        try {
            users = userMapper.getUsers();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        request.setAttribute("users", users);
        request.getRequestDispatcher("WEB-INF/viewaccounts.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }
}
