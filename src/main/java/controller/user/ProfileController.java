package controller.user;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import config.DatabaseCredentials;
import controller.AlertBox;
import controller.SceneManager;
import domain.Sneaker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

public class ProfileController extends DatabaseCredentials implements Initializable {

    @FXML
    private Button marketButton, editButton, deleteButton, addButton;

    @FXML
    private TextField idText, priceText;

    @FXML
    private TreeTableView<ProfileController.Sneak> sneakerTable= new TreeTableView<>();

    @FXML
    private TreeTableColumn<ProfileController.Sneak,String> idColumn  =  new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<ProfileController.Sneak,String> nameColumn  = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<ProfileController.Sneak, String > sizeColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<ProfileController.Sneak, String> conditionColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<ProfileController.Sneak, String > priceColumn = new TreeTableColumn<>();

    private SneakerRepository sneakerRepository = new SneakerRepository(super.url, super.username, super.password);

    private String usernameLogged;

    public String getUsernameLogged() {
        return usernameLogged;
    }

    public void setUsernameLogged(String usernameLogged) {
        this.usernameLogged = usernameLogged;
    }

    public TreeTableView<Sneak> getSneakerTable() {
        return sneakerTable;
    }

    public void initialize(URL location, ResourceBundle resources){

    }

    public void setObservableListForSneakerTable(){
        ObservableList<ProfileController.Sneak> products = FXCollections.observableArrayList();
        for(Sneaker s : sneakerRepository.getAll()){
            if(s.getUsername().equals(usernameLogged) && s.isAproved()){
                Sneak sneak = new Sneak(Integer.toString(s.getId()), s.getName(), Integer.toString(s.getSize()),s.getCondition(), Double.toString(s.getPrice()));
                products.add(sneak);
            }
        }
        final TreeItem<Sneak> root = new RecursiveTreeItem<ProfileController.Sneak>(products, RecursiveTreeObject::getChildren);
        sneakerTable.setRoot(root);
        sneakerTable.setShowRoot(false);
    }

    public void initializeSneakTable(){
        idColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String> param) {
                return param.getValue().getValue().id;
            }
        });

        nameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String> param) {
                return param.getValue().getValue().name;
            }
        });

        sizeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String> param) {
                return param.getValue().getValue().size;
            }
        });

        conditionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String> param) {
                return param.getValue().getValue().condition;
            }
        });

        priceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProfileController.Sneak, String> param) {
                return param.getValue().getValue().price;
            }
        });
    }

    public void marketButtonClicked(ActionEvent actionEvent) {
        SceneManager.getInstance().switchScene(SceneManager.States.MARKET);
    }

    public void editButtonClicked(ActionEvent actionEvent) throws Exception {
        int id = Integer.parseInt(idText.getText());
        int newPrice = Integer.parseInt(priceText.getText());
        Sneaker s = SneakerRepository.getSneaker(id);
        if(s != null){
            if(sneakerRepository.updateSneaker(newPrice,s.isAproved(), s)){
                AlertBox.display("UPDATED", "The price has been updated");
                setObservableListForSneakerTable();
            }else{
                AlertBox.display("ERROR", "The price hasn`t been updated");
            }
        }else{
            AlertBox.display("ERROR", "Invalid ID");
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) throws Exception {
        if(!idText.getText().isEmpty()){
            if(sneakerRepository.idExists(Integer.parseInt(idText.getText())) ){
                Sneaker s  = sneakerRepository.getSneaker(Integer.parseInt(idText.getText()));
                if(s.getUsername().equals(usernameLogged) && s.isAproved()){
                    sneakerRepository.deleteSneaker(s);
                    AlertBox.display("DELETE", "Delete successful");
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

    public void addButtonClicked(ActionEvent actionEvent) {
        FXMLLoader loader = SceneManager.getInstance().getFXML(SceneManager.States.NEWPAIR);
        AddSneakerController controller = loader.getController();
        controller.setUsernameLogged(usernameLogged);
        controller.intializeSneakerRepo();
        SceneManager.getInstance().switchScene(SceneManager.States.NEWPAIR);

    }

    class Sneak extends RecursiveTreeObject<ProfileController.Sneak> {
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