package project.stealth;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private VBox emptyVbox;

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
    private TextArea txtAreaMessage;

    private String finalMessage;
    private Operation operation;

    //lista com os métodos de encriptação
    ObservableList<Cipher> listCiphers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //garante que nenhuma opção vai parecer antes do usuário selecionar o método
        emptyVbox.toFront();
        //garante que a mensagem vai estar vazia
        finalMessage = " ";
        labelFinalMessage.setText(finalMessage);
        //Desabilita os campos para que o usuário selecione a operação
        buttonGetMessage.setDisable(true);
        txtAreaMessage.setDisable(true);
        comboBoxOptions.setDisable(true);

        //adiciona as opções no combobox
        listCiphers.addAll(Cipher.CAESAR,Cipher.XOR,Cipher.ONETIMEPAD,Cipher.POLYBIUSSQUARE,Cipher.HILL);
        comboBoxOptions.setItems(listCiphers);

        /*
        Botão de fechar
         */
        buttonClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        /*
        Botão que dá acesso as funções de Encrypt
         */
        buttonEncrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                operation = Operation.ENCRYPT;
                //para ficar mais claro pro usuário a operação que está sendo realizada
                labelFinalMessage.setStyle("-fx-background-color: -ORANGE;");
                //Habilita os campos para o usuário
                buttonGetMessage.setDisable(false);
                txtAreaMessage.setDisable(false);
                comboBoxOptions.setDisable(false);
            }
        });

        /*
        Botão que dá acesso as funções de Decrypt
         */
        buttonDecrypt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                operation = Operation.DECRYPT;
                //para ficar mais claro pro usuário a operação que está sendo realizada
                labelFinalMessage.setStyle("-fx-background-color: -YELLOW;");
                //Habilita os campos para o usuário
                buttonGetMessage.setDisable(false);
                txtAreaMessage.setDisable(false);
                comboBoxOptions.setDisable(false);
            }
        });
        comboBoxOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cipher>() {
            @Override
            public void changed(ObservableValue<? extends Cipher> observableValue, Cipher oldValue, Cipher newValue) {
                if(newValue.equals(Cipher.CAESAR)){
                    shiftOptionVbox.toFront();
                }
                else if(newValue.equals(Cipher.XOR)){
                    keyOptionVbox.toFront();
                }
                else if(newValue.equals(Cipher.POLYBIUSSQUARE)){
                    emptyVbox.toFront();
                }
                else if(newValue.equals(Cipher.ONETIMEPAD) && operation.equals(Operation.DECRYPT)){
                    keyOptionVbox.toFront();
                }
                else if(newValue.equals(Cipher.HILL)){
                    matrixOptionVbox.toFront();
                }
                else{
                    emptyVbox.toFront();
                }
            }
        });
    }
}
