package controller.login;

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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import repository.PersonRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends DatabaseCredentials implements Initializable {
    @FXML
    private TextField usText;

    @FXML
    private PasswordField pwText;

    @FXML
    private Button loginButton, registerButton;

    private PersonRepository personRepository = new PersonRepository(super.url, super.username, super.password);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void loginButtonClicked(ActionEvent actionEvent) throws Exception {

        String user = usText.getText();
        String pw = pwText.getText();

        boolean check = personRepository.personExists(user, pw);

        if (!check) {
            AlertBox.display("Eroare","User or password incorect");
        }

        if (check){

            Person personLogged = personRepository.getPersonAfterUsername(user);

            if(personLogged instanceof User) {
                FXMLLoader loader = SceneManager.getInstance().getFXML(SceneManager.States.MARKET);
                MarketController controller = loader.getController();
                controller.setUsernameLogged(user);
                controller.initializeSneakTable();
                controller.setObservableListForSneakerTable();
                SceneManager.getInstance().switchScene(SceneManager.States.MARKET);
            }

            if(userLogged instanceof Admin) {

            }

        }

    }
    public void registerButtonClicked(ActionEvent actionEvent) throws Exception {
        SceneManager.getInstance().switchScene(SceneManager.States.REGISTER);
    }

}