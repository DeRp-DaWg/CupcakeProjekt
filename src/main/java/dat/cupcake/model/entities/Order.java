package dat.cupcake.model.entities;

import java.util.Objects;

public class Order {
    private int price;
    private String topping;
    private String bottom;
    private User user;
    
    public Order(int price, String topping, String bottom) {
        this.price = price;
        this.topping = topping;
        this.bottom = bottom;
    }
    
    public int getPrice() {
        return price;
    }
    
    public String getTopping() {
        return topping;
    }
    
    public String getBottom() {
        return bottom;
    }
    
    public User getUser() {
        return user;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", topping='" + topping + '\'' +
                ", bottom='" + bottom + '\'' +
                ", user=" + user +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return price == order.price && topping.equals(order.topping) && bottom.equals(order.bottom) && user.equals(order.user);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(price, topping, bottom, user);
    }
}
