package controller.user;

import config.DatabaseCredentials;
import controller.AlertBox;
import controller.SceneManager;
import domain.Sneaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import repository.SneakerRepository;


import java.net.URL;
import java.util.ResourceBundle;

public class AddSneakerController extends DatabaseCredentials implements Initializable {

    @FXML
    private TextField nameText, sizeText, conditionText, priceText;

    @FXML
    private Button requestButton;

    private String usernameLogged;

    SneakerRepository sneakerRepository;

    public void setUsernameLogged(String usernameLogged) {
        this.usernameLogged = usernameLogged;
    }

    public String getUsernameLogged() {
        return usernameLogged;
    }

    public void initialize(URL location, ResourceBundle resources){

    }

    public void intializeSneakerRepo(){
        sneakerRepository = new SneakerRepository(super.url, super.username, super.password);
    }

    public void requestButtonClicked(ActionEvent actionEvent) throws Exception{
        String name = nameText.getText();
        int size = Integer.parseInt(sizeText.getText());
        String condition = conditionText.getText();
        double price = Double.parseDouble(priceText.getText());

        Sneaker s = new Sneaker(name, size, condition, price, getUsernameLogged(), false);

        if(sneakerRepository.addSneaker(s)){
            AlertBox.display("GOOD JOB","An admin will review the pair as soon as possible");
        }else{
            AlertBox.display("ERROR","Something went wrong!");
        }
        SceneManager.getInstance().switchScene(SceneManager.States.PROFILE);
    }
}