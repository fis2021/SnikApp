package controller.register;

import config.DatabaseCredentials;
import controller.AlertBox;
import controller.SceneManager;
import controller.user.MarketController;
import domain.Person;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import repository.PersonRepository;

import java.net.URL;
import java.util.ResourceBundle;


public class RegisterController extends DatabaseCredentials implements Initializable {

    @FXML
    private TextField usText, pwText, firstText, lastText, phoneText, addressText;

    @FXML
    private Button confirmButton;

    PersonRepository personRepository = new PersonRepository(super.url, super.username, super.password);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        personRepository.getAll();
    }

    public void confirmButtonClicked(ActionEvent actionEvent) throws  Exception{
        String user = usText.getText();
        String pass = pwText.getText();
        String first = firstText.getText();
        String last = lastText.getText();
        String phone = phoneText.getText();
        String address = addressText.getText();

        Person u = new Person(user,pass, first, last, address, phone);

        if(personRepository.addPerson(u)){
            AlertBox.display("Succes","User adaugat cu succes");
            FXMLLoader loader = SceneManager.getInstance().getFXML(SceneManager.States.MARKET);
            MarketController controller = loader.getController();
            controller.setUsernameLogged(user);
            controller.initializeSneakTable();
            controller.setObservableListForSneakerTable();
            SceneManager.getInstance().switchScene(SceneManager.States.MARKET);
        }else{
            AlertBox.display("Eroare","Username-il deja exista");
        }

    }

    public void backButtonClicked(ActionEvent actionEvent) {
        resetFields();
        SceneManager.getInstance().switchScene(SceneManager.States.LOGIN);
    }

    private void resetFields() {
        if(!usText.getText().isEmpty())
            usText.clear();

        if(!pwText.getText().isEmpty())
            pwText.clear();

        if(!firstText.getText().isEmpty())
            firstText.clear();

        if(!lastText.getText().isEmpty())
            lastText.clear();

        if(!addressText.getText().isEmpty())
            addressText.clear();

        if(!phoneText.getText().isEmpty() )
            phoneText.clear();

    }
}