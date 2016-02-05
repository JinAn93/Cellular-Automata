import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    private UserInterface userInt = new UserInterface();

    @Override
    public void start (Stage s) throws Exception {
        userInt.initStage(s);
        s.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

}
