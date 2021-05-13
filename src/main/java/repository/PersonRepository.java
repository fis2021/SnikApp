package repository;

import domain.Admin;
import domain.Person;
import domain.exception.CustomException;
import domain.User;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PersonRepository extends AbstractRepository<Integer, Person> {

    private static String url;
    private static String username;
    private static String password;

    public PersonRepository(String url, String user, String password) {
        this.url = url;
        this.username = user;
        this.password = password;
    }

    private void loadData() throws SQLException {

        super.elems.clear();
        String sql = "select * from \"Persoane\" ";

        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                boolean isAdmin = rs.getBoolean("isadmin");

                if (isAdmin) {
                    Admin admin = new Admin(username, password, firstName, lastName, address, phone);
                    admin.setId(id);
                    super.add(admin);
                } else {
                    User user = new User(username, password, firstName, lastName, address, phone);
                    user.setId(id);
                    super.add(user);
                }

            }
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public boolean addPerson(Person person) throws Exception {


        String sql = "";
        String Username = person.getUsername();
        String Password = person.getPassword();
        String Firstname = person.getFirstName();
        String Lastname = person.getLastName();
        String Address = person.getAddress();
        String Phone = person.getPhoneNumber();

        boolean isAdmin = false;

        if(personExists(person.getUsername(), person.getPassword()) ) {
            return false;
        }



        if( (person instanceof User) ) {
            isAdmin = ((User) person).isAdmin();
        }

        int new_id = super.elems.size() + 1;

        try (var connection = DriverManager.getConnection(url, username, password)

        ) {

            sql = "insert into \"Persoane\" "
                    + "VALUES("  + "'" + new_id + "'"  + ", "
                    + createTemplate(Username)
                    + "'" + Password + "', "
                    + createTemplate(Firstname)
                    + createTemplate(Lastname)
                    + createTemplate(Phone)
                    + createTemplate(Address)
                    + "'" + isAdmin + "'" +
                    ");";

            var ps = connection.prepareStatement(sql);
            var rs = ps.executeUpdate();

            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean personExists(String Username, String Password) throws Exception {

        String sql = "select * from \"Persoane\" ";

        Person person = getPersonAfterUsername(Username);
        if(person == null) {
            return false;
        }

        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {

                String UserName = rs.getString("username");
                String PassWord = rs.getString("password");

                if( UserName.equals(Username) && PassWord.equals(Password) )
                    return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public  Person getPersonAfterUsername(String user_name) {
        String customerSql = "select * from \"Persoane\" where username=" + "'" + user_name + "'";
        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(customerSql);
             var rs = ps.executeQuery()) {

            while(rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone");
                boolean isAdmin = rs.getBoolean("isadmin");

                if (isAdmin) {
                    //TODO implement admin

                }
                else {
                    User user = new User(username, password, firstname, lastname, address, phoneNumber);
                    user.setId(id);
                    return user;
                }
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int findUserId(User user) {

        String sql = "Select * from \"user\" where " +
                "username =" + "'" + user.getUsername() + "'" +
                "AND password=" + "'" + user.getPassword() + "'" + " ;";

        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(sql);
             var rs = ps.executeQuery();
        ) {

            while( rs.next() ) {
                int id = -1;
                id = rs.getInt("id");
                if(id > 0)
                    return id;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean updateIndexes(int id) {

        String sql = "update \"user\" SET id = id - 1" +
                "where id > " + id;

        try (var connection = DriverManager.getConnection(url, username, password);
        ) {

            var ps = connection.prepareStatement(sql);
            var rs = ps.executeUpdate();
            return true;
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static String createTemplate(String str) {
        StringBuilder tmp = new StringBuilder();
        tmp.append("'").append(str).append("'").append(", ");

        return tmp.toString();
    }

    public List<Person> getAll() {
        try {
            loadData();
            return new ArrayList<>(super.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(super.getAll());
    }
}
