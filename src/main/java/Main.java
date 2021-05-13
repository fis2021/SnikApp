
import controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;
    private SceneManager sceneManager;

    @Override
    public void start(Stage stage) throws Exception {

        String url = "jdbc:postgresql://tai.db.elephantsql.com:5432/gyipvslv";
        String username = "gyipvslv";
        String password = "ST4aEQz6bWr46YEGkSRquEzFpCPvlQt9";




        /* TESTING
        SceneManager.setUp(stage);
        SceneManager.getInstance().switchScene(SceneManager.States.LOGIN);
        */
        //PersonRepository r1 = new PersonRepository(url, username, password);




    }

    public static void main(String[] args){
        launch(args);
    }




}