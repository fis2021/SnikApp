package controller.user;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import config.DatabaseCredentials;
import controller.AlertBox;
import controller.SceneManager;
import domain.Sneaker;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import repository.SneakerRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class MarketController extends DatabaseCredentials implements Initializable {

    @FXML
    private Button profileButton, buyButton;

    @FXML
    private TextField idToBuy;

    @FXML
    private TreeTableView<Sneak> sneakerTable= new TreeTableView<>();

    @FXML
    private TreeTableColumn<Sneak,String> idColumn  =  new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<Sneak,String> nameColumn  = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<Sneak, String > sizeColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<Sneak, String> conditionColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<Sneak, String > priceColumn = new TreeTableColumn<>();

    private SneakerRepository sneakerRepository = new SneakerRepository(super.url, super.username, super.password);

    private String usernameLogged;

    public SneakerRepository getSneakerRepository() {
        return sneakerRepository;
    }

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setObservableListForSneakerTable(){
        ObservableList<Sneak> products = FXCollections.observableArrayList();
        for(Sneaker s : sneakerRepository.getAll()){
            System.out.println(s);
            if(!s.getUsername().equals(usernameLogged)&& s.isAproved()){
                Sneak sneak = new Sneak(Integer.toString(s.getId()), s.getName(), Integer.toString(s.getSize()),s.getCondition(), Double.toString(s.getPrice()));
                products.add(sneak);
            }
            //System.out.println("DATABASE" + s.toString());
            //System.out.println("Sneak" + sneak.toString());
        }
        final TreeItem<Sneak> root = new RecursiveTreeItem<Sneak>(products, RecursiveTreeObject::getChildren);
        sneakerTable.setRoot(root);
        sneakerTable.setShowRoot(false);
    }

    public void initializeSneakTable(){
        idColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sneak, String> param) {
                return param.getValue().getValue().id;
            }
        });

        nameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sneak, String> param) {
                return param.getValue().getValue().name;
            }
        });

        sizeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sneak, String> param) {
                return param.getValue().getValue().size;
            }
        });

        conditionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sneak, String> param) {
                return param.getValue().getValue().condition;
            }
        });

        priceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sneak, String> param) {
                return param.getValue().getValue().price;
            }
        });
    }

    public void setUsernameLogged(String usernameLogged){
        this.usernameLogged = usernameLogged;
    }

    public String getUsernameLogged(){
        return usernameLogged;
    }

    public void buyButtonClicked(ActionEvent actionEvent) throws Exception{
        sneakerRepository.getAll();
        if(!idToBuy.getText().isEmpty()){
            if(sneakerRepository.idExists(Integer.parseInt(idToBuy.getText())) ){
                Sneaker s  = sneakerRepository.getSneaker(Integer.parseInt(idToBuy.getText()));
                if(!s.getUsername().equals(usernameLogged) && s.isAproved()){
                    sneakerRepository.deleteSneaker(s);
                    AlertBox.display("GOT` EM", "GOT` EM");
                    setObservableListForSneakerTable();
                }else{
                    AlertBox.display("INVALID ID","Invalid ID");
                }

            }else{
                AlertBox.display("INVALID ID", "Invalid ID");
            }
        }else{
            AlertBox.display("ERROR","Insert an ID");
        }

    }

    public void profileButtonClicked(ActionEvent actionEvent) throws Exception{
        FXMLLoader loader = SceneManager.getInstance().getFXML(SceneManager.States.PROFILE);
        ProfileController controller = loader.getController();
        controller.setUsernameLogged(usernameLogged);
        controller.initializeSneakTable();
        controller.setObservableListForSneakerTable();
        SceneManager.getInstance().switchScene(SceneManager.States.PROFILE);
        SceneManager.getInstance().switchScene(SceneManager.States.PROFILE);
    }


    class Sneak extends RecursiveTreeObject<Sneak> {
        StringProperty id;
        StringProperty name;
        StringProperty size;
        StringProperty condition;
        StringProperty price;

        public Sneak(String  id, String name, String size, String condition, String price) {
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.size = new SimpleStringProperty(size);
            this.condition = new SimpleStringProperty(condition);
            this.price = new SimpleStringProperty(price);
        }

        public String getId() {
            return id.get();
        }

        public StringProperty idProperty() {
            return id;
        }

        public String getName() {
            return name.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public String getSize() {
            return size.get();
        }

        public StringProperty sizeProperty() {
            return size;
        }

        public String getCondition() {
            return condition.get();
        }

        public StringProperty conditionProperty() {
            return condition;
        }

        public String getPrice() {
            return price.get();
        }

        public StringProperty priceProperty() {
            return price;
        }

        @Override
        public String toString() {
            return "Sneak{" +
                    "id=" + id +
                    ", name=" + name +
                    ", size=" + size +
                    ", condition=" + condition +
                    ", price=" + price +
                    '}';
        }
    }

}