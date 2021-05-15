package controller.admin;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import config.DatabaseCredentials;
import domain.Sneaker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;
import repository.SneakerRepository;

import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginController extends DatabaseCredentials implements Initializable {

    @FXML
    private Button acceptButton, declineButton;

    @FXML
    private TextField idField;

    @FXML
    private TreeTableView<AdminLoginController.Sneak> sneakerTable = new TreeTableView<>();

    @FXML
    private TreeTableColumn<AdminLoginController.Sneak,String> idColumn  =  new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<AdminLoginController.Sneak,String> nameColumn  = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<AdminLoginController.Sneak, String > sizeColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<AdminLoginController.Sneak, String> conditionColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<AdminLoginController.Sneak, String > priceColumn = new TreeTableColumn<>();

    private SneakerRepository sneakerRepository = new SneakerRepository(super.url, super.username, super.password);

    public void initialize(URL location, ResourceBundle resources) {

    }

    public void acceptButtonClicked(ActionEvent actionEvent){

    }

    public void declineButtonClicked(ActionEvent actionEvent){

    }

    public void setObservableListForSneakerTable(){
        ObservableList<AdminLoginController.Sneak> products = FXCollections.observableArrayList();
        for(Sneaker s : sneakerRepository.getAll()){
            if(!s.isAproved()){
                AdminLoginController.Sneak sneak = new AdminLoginController.Sneak(Integer.toString(s.getId()), s.getName(), Integer.toString(s.getSize()),s.getCondition(), Double.toString(s.getPrice()));
                products.add(sneak);
            }
        }
        final TreeItem<AdminLoginController.Sneak> root = new RecursiveTreeItem<AdminLoginController.Sneak>(products, RecursiveTreeObject::getChildren);
        sneakerTable.setRoot(root);
        sneakerTable.setShowRoot(false);
    }

    public void initializeSneakTable(){
        idColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String> param) {
                return param.getValue().getValue().id;
            }
        });

        nameColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String> param) {
                return param.getValue().getValue().name;
            }
        });

        sizeColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String> param) {
                return param.getValue().getValue().size;
            }
        });

        conditionColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String> param) {
                return param.getValue().getValue().condition;
            }
        });

        priceColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AdminLoginController.Sneak, String> param) {
                return param.getValue().getValue().price;
            }
        });
    }

    class Sneak extends RecursiveTreeObject<AdminLoginController.Sneak> {
        StringProperty id;
        StringProperty name;
        StringProperty size;
        StringProperty condition;
        StringProperty price;

        public Sneak(String id, String name, String size, String condition, String price) {
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

