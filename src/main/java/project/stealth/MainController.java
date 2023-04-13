package project.stealth;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox backgroundVbox;

    @FXML
    private Button buttonClose;

    @FXML
    private Button buttonDarkmode;

    @FXML
    private Button buttonDecrypt;

    @FXML
    private Button buttonEncrypt;

    @FXML
    private Button buttonGetMessage;

    @FXML
    private ComboBox<Cipher> comboBoxOptions;

    @FXML
    private VBox keyOptionVbox;

    @FXML
    private Label labelFinalMessage;

    @FXML
    private VBox mainVbox;

    @FXML
    private VBox matrixOptionVbox;

    @FXML
    private TextField matrixP1;

    @FXML
    private TextField matrixP2;

    @FXML
    private TextField matrixP3;

    @FXML
    private TextField matrixP4;

    @FXML
    private HBox menuHbox;

    @FXML
    private VBox shiftOptionVbox;

    @FXML
    private TextField txtFieldMessage;

    ObservableList<Cipher> listCiphers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listCiphers.addAll(Cipher.CAESAR,Cipher.XOR,Cipher.ONETIMEPAD,Cipher.POLYBIUSSQUARE,Cipher.HILL);
        comboBoxOptions.setItems(listCiphers);
    }
}
