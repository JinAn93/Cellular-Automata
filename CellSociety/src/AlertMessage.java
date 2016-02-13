import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMessage {

    public void showAlertMessage (String alertType, String title, String header, String contents){
        Alert alert;
        if(alertType.equals("ERROR")){
            alert = new Alert(AlertType.ERROR);
        }
        else{ 
            alert = new Alert(AlertType.INFORMATION);
        }
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contents);
        alert.showAndWait();
    }

}
