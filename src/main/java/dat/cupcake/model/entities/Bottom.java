package dat.cupcake.model.entities;

import java.util.Objects;

public class Bottom {
    private int id;
    private int price;
    private String name;
    
    public Bottom(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }
    
    public Bottom(int id) {
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
        return "Bottom{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bottom bottom = (Bottom) o;
        return id == bottom.id && price == bottom.price && name.equals(bottom.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, price, name);
    }
}
