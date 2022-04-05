package dat.cupcake.model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private int orderId;
    private User user;
    private Status status;
    private LocalDateTime date;
    private int price;
    private Topping topping;
    private Bottom bottom;
    
    public Order(int orderId, User user, Status status, LocalDateTime date, int price, Topping topping, Bottom bottom) {
        this.orderId = orderId;
        this.user = user;
        this.status = status;
        this.date = date;
        this.price = price;
        this.topping = topping;
        this.bottom = bottom;
    }
    
    public Order(int orderId, User user, Status status, LocalDateTime date, Topping topping, Bottom bottom) {
        this.orderId = orderId;
        this.user = user;
        this.status = status;
        this.date = date;
        this.topping = topping;
        this.bottom = bottom;
    }
    
    public Order(int orderId, Status status, LocalDateTime date, int price) {
        this.orderId = orderId;
        this.status = status;
        this.date = date;
        this.price = price;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
    
    public int getPrice() {
        return price;
    }
    
    public Topping getTopping() {
        return topping;
    }
    
    public void setTopping(Topping topping) {
        this.topping = topping;
    }
    
    public Bottom getBottom() {
        return bottom;
    }
    
    public void setBottom(Bottom bottom) {
        this.bottom = bottom;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", status=" + status +
                ", date=" + date +
                ", price=" + price +
                ", topping=" + topping +
                ", bottom=" + bottom +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && price == order.price && user.equals(order.user) && status == order.status && date.equals(order.date) && topping.equals(order.topping) && bottom.equals(order.bottom);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(orderId, user, status, date, price, topping, bottom);
    }
}
