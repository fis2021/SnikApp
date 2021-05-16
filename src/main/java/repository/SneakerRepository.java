package repository;

import domain.Sneaker;
import domain.exception.CustomException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SneakerRepository extends AbstractRepository<Integer, Sneaker> {

    private static String url;
    private static String username;
    private static String password;

    public SneakerRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private void loadData() throws SQLException {

        super.elems.clear();
        String sql = "Select * from \"Sneaker\" ";

        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                int size = rs.getInt("size");
                String condition = rs.getString("conditie");
                double price = rs.getDouble("price");
                String username = rs.getString("proprietar");
                Boolean aproved=rs.getBoolean("aproved");
                Sneaker Sneaker = new Sneaker(name, size, condition, price, username,aproved);
                Sneaker.setId(id);
                super.add(Sneaker);
            }
        } catch (SQLException | CustomException e) {
            e.printStackTrace();
        }
    }

    public boolean addSneaker(Sneaker s) throws CustomException, SQLException {

        String sql;
        String name = s.getName();
        int size = s.getSize();
        String conditie = s.getCondition();
        double price = s.getPrice();
        String usernam = s.getUsername();
        boolean aproved =false;
        int new_id = super.elems.size() + 1;

        try (var connection = DriverManager.getConnection(url, username, password)) {
            sql = "insert into \"Sneaker\"(name, size, conditie, price, proprietar,aproved) VALUES (?,?,?,?,?,?) ";

            var ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, size);
            ps.setString(3, conditie);
            ps.setDouble(4, price);
            ps.setString(5,usernam);
            ps.setBoolean(6,aproved);

            var rs = ps.executeUpdate();
            super.add(s);


            return true;
        } catch (SQLException | CustomException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int findSneakerId(Sneaker p) throws SQLException {

        String sql = "Select * from \"Sneaker\" where " + "name = " + "'" + p.getName() + "' AND proprietar = " + "'" + p.getUsername() + "' AND size = " + p.getSize() + ";";

        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(sql);
             var rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                int id = -1;
                id = rs.getInt("id");
                if (id > 0)
                    return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean idExists(int ID){
        String sql = "Select * from \"Sneaker\" where id =" + ID + ";";

        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(sql);
             var rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                if(ID == rs.getInt("id")) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateIndexes(int id) {
        String sql = "update \"Sneaker\" SET id = id - 1" + " where id > " + id;

        try (var connection = DriverManager.getConnection(url, username, password)
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

    public boolean sneakerExists(String name, String conditie, int size) {

        String sql = "Select * from \"Sneaker\" ";

        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                String Name = rs.getString("name");
                String Conditie = rs.getString("conditie");
                int Size = rs.getInt("size");
                if(Name.equals(name) && Conditie.equals(conditie) && Size == size)
                    return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSneaker(Sneaker s) throws CustomException, SQLException {

        int idFromWhereToUpdate = s.getId();

        if (idFromWhereToUpdate > 0) {
            String sql = "delete from \"Sneaker\" " +
                    "where id = " + s.getId() + ";";

            try (var connection = DriverManager.getConnection(url, username, password)
            ) {
                var ps = connection.prepareStatement(sql);
                var rs = ps.executeUpdate();

                //deleting from the memory repository
                super.elems.remove(s.getId());
                return true;
//                if (updateIndexes(idFromWhereToUpdate)) {
//                    return true;
//                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new CustomException("You're trying to delete a non-existent Sneaker!");
        }
        return false;
    }

    public boolean updateSneaker(double priceUpdated,boolean aproved, Sneaker s) throws SQLException {

        String sql = "update \"Sneaker\"  SET "
                + "price = "  + priceUpdated+ ", "
                + "aproved = " + aproved + " "
                + "where id = "  + s.getId() + ";";

        try (var connection = DriverManager.getConnection(url, username, password)

        ) {
            var ps = connection.prepareStatement(sql);
            var rs = ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String createTemplate(String str) {
        StringBuilder tmp = new StringBuilder();
        tmp.append("'").append(str).append("'").append(", ");

        return tmp.toString();
    }

    public static Sneaker getSneaker(int SneakerId) {
        String SneakerSql = "select * from \"Sneaker\" where id=" + SneakerId;
        try (var connection = DriverManager.getConnection(url, username, password);
             var ps = connection.prepareStatement(SneakerSql);
             var rs = ps.executeQuery()) {

            rs.next();

            int id = rs.getInt("id");
            String name = rs.getString("name");
            int size = rs.getInt("size");
            String conditie = rs.getString("conditie");
            double price = rs.getDouble("price");
            String proprietar = rs.getString("proprietar");
            Boolean aproved=rs.getBoolean("aproved");

            Sneaker sneaker = new Sneaker(name, size, conditie, price, proprietar,aproved);
            sneaker.setId(id);
            return sneaker;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Sneaker> getAll() {
        try {
            loadData();
            return new ArrayList<>(super.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(super.getAll());
    }

}