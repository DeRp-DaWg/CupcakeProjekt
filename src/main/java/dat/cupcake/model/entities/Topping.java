package dat.cupcake.model.entities;

import java.util.Objects;

public class Topping {
    private int id;
    private int price;
    private String name;
    
    public Topping(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }
    
    public Topping(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public int getPrice() {
        return price;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "Topping{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topping topping = (Topping) o;
        return id == topping.id && price == topping.price && name.equals(topping.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, price, name);
    }
}
