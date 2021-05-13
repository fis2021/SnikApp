package domain;

import java.util.Objects;

public class Sneaker extends BaseEntity<Integer> {
    private String name;
    private int size;
    private String condition;
    private double price;
    private String username;

    public Sneaker(String name, int size, String condition, double price, String username) {
        this.name = name;
        this.size = size;
        this.condition = condition;
        this.price = price;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sneaker sneaker = (Sneaker) o;
        return size == sneaker.size && Double.compare(sneaker.price, price) == 0 && Objects.equals(name, sneaker.name) && Objects.equals(condition, sneaker.condition) && Objects.equals(username, sneaker.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, condition, price, username);
    }

    @Override
    public String toString() {
        return "Sneaker{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", condition='" + condition + '\'' +
                ", price=" + price +
                ", username='" + username + '\'' +
                '}';
    }
}