import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class extends Application and calls initStage method in UserInterface class.
 * @author Joseph Lilien
 * @author Jin An
 * @author Huijia Yu
 *
 */
public class Main extends Application {
    
    @Override
    public void start (Stage s) throws Exception {
        UserInterface userInt = new UserInterface();
        userInt.initStage(s);
        s.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

}
