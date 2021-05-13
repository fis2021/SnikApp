package domain;

import java.util.Objects;

public class User extends Person {

    private final boolean isAdmin = false;

    public User(String username, String password, String firstName, String lastName, String address, String phoneNumber) {
        super(username, password, firstName, lastName, address, phoneNumber);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isAdmin);
    }

    @Override
    public String toString() {
        return super.toString() + "User{" +
                "isAdmin=" + isAdmin +
                '}';
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}