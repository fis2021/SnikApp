
import controller.SceneManager;
import domain.Sneaker;
import javafx.application.Application;
import javafx.stage.Stage;
import repository.SneakerRepository;

public class Main extends Application {

    private Stage stage;
    private SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws Exception {

        String url = "jdbc:postgresql://tai.db.elephantsql.com:5432/gyipvslv";
        String username = "gyipvslv";
        String password = "ST4aEQz6bWr46YEGkSRquEzFpCPvlQt9";

        SceneManager.setUp(stage);
        SceneManager.getInstance().switchScene(SceneManager.States.LOGIN);

    }

    public static void main(String[] args){
        launch(args);
    }




}