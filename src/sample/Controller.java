package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;



public class Controller {
    public javafx.scene.control.TextArea textArea;
    @FXML
    public TextField textField;
    @FXML


    public void clickbtn(ActionEvent actionEvent) {

    textArea.appendText(textField.getText()+"\n");
    textField.clear();
    textField.getOnAction();

    }

    public void clickedmouse(MouseEvent mouseEvent) {
        textField.clear();
    }

    public void keypress(javafx.scene.input.KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
        textArea.appendText( textField.getText()+"\n");
        textField.clear();}
    }
}
