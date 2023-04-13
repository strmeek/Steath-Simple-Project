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

    @FXML
    private TextField txtFieldKey;

    @FXML
    private TextField txtFieldShift;
    @FXML
    private TextField txtFieldFinalMessage;

    private Encrypt encrypt = new Encrypt();
    private Decrypt decrypt = new Decrypt();
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
        txtFieldFinalMessage.setText(finalMessage);
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
                txtFieldFinalMessage.setStyle("-fx-background-color: -ORANGE;");
                //Habilita os campos para o usuário
                buttonGetMessage.setDisable(false);
                txtAreaMessage.setDisable(false);
                comboBoxOptions.setDisable(false);
                //garante que as opções que aparecerão serão as corretas
                methodSelected();
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
                txtFieldFinalMessage.setStyle("-fx-background-color: -YELLOW;");
                //Habilita os campos para o usuário
                buttonGetMessage.setDisable(false);
                txtAreaMessage.setDisable(false);
                comboBoxOptions.setDisable(false);
                //garante que as opções que aparecerão serão as corretas
                methodSelected();
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
        buttonGetMessage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Identifica qual foi o método escolhido
                Cipher currentCipher = comboBoxOptions.getSelectionModel().getSelectedItem();
                //verifica se a operação é de encriptação
                if(operation.equals(Operation.ENCRYPT)){
                    //Salva o texto do usuário
                    String encryptText = txtAreaMessage.getText();
                    if(currentCipher.equals(Cipher.CAESAR)){
                        int shift = Integer.parseInt(txtFieldShift.getText());
                        finalMessage = encrypt.caesarCipher(encryptText,shift);
                    }
                    else if(currentCipher.equals(Cipher.XOR)){
                        String key = txtFieldKey.getText();
                        finalMessage = encrypt.xorCipher(encryptText, key);
                    }
                    else if(currentCipher.equals(Cipher.POLYBIUSSQUARE)){
                        finalMessage = encrypt.polybiusSquareCipher(encryptText);
                    }
                    else if(currentCipher.equals(Cipher.ONETIMEPAD)){
                        finalMessage = encrypt.oneTimePadCipher(encryptText);
                    }
                    else if(currentCipher.equals(Cipher.HILL)){
                        int [][] matrix = new int[2][2];
                        matrix[0][0] = Integer.parseInt(matrixP1.getText());
                        matrix[0][1] = Integer.parseInt(matrixP2.getText());
                        matrix[1][0] = Integer.parseInt(matrixP3.getText());
                        matrix[1][1] = Integer.parseInt(matrixP4.getText());

                        finalMessage = encrypt.hillCipher(encryptText, matrix);
                    }
                    //Mostra o Resultado final
                    txtFieldFinalMessage.setText(finalMessage);
                    //Excessão para OneTimePad cipher
                    if(currentCipher.equals(Cipher.ONETIMEPAD)){
                        txtFieldFinalMessage.setText(finalMessage + "[" + "\nGuarde a Senha: " + encrypt.oneTimePadKey + "]");
                        System.out.println(encrypt.oneTimePadKey);
                    }
                    System.out.println(finalMessage);
                }
                //Verifica se a operação é Decriptação
                if (operation.equals(Operation.DECRYPT)){
                    //Salva o texto do Usuário
                    String encryptedText = txtAreaMessage.getText();
                    if(currentCipher.equals(Cipher.CAESAR)){
                        int shift = Integer.parseInt(txtFieldShift.getText());
                        finalMessage = decrypt.caesarDecipher(encryptedText,shift);
                    }
                    else if(currentCipher.equals(Cipher.XOR)){
                        String key = txtFieldKey.getText();
                        finalMessage = decrypt.xorDecipher(encryptedText, key);
                    }
                    else if(currentCipher.equals(Cipher.POLYBIUSSQUARE)){
                        finalMessage = decrypt.polybiusSquareDecipher(encryptedText);
                    }
                    else if(currentCipher.equals(Cipher.ONETIMEPAD)){
                        String key = txtFieldKey.getText();
                        finalMessage = decrypt.oneTimePadDecipher(encryptedText, key);
                    }
                    else if(currentCipher.equals(Cipher.HILL)){
                        int [][] matrix = new int[2][2];
                        matrix[0][0] = Integer.parseInt(matrixP1.getText());
                        matrix[0][1] = Integer.parseInt(matrixP2.getText());
                        matrix[1][0] = Integer.parseInt(matrixP3.getText());
                        matrix[1][1] = Integer.parseInt(matrixP4.getText());

                        finalMessage = decrypt.hillDecipher(encryptedText, matrix);
                    }
                    txtFieldFinalMessage.setText(finalMessage);
                    System.out.println(finalMessage);
                }
            }
        });
    }
    public void methodSelected(){
        Cipher newValue = comboBoxOptions.getSelectionModel().getSelectedItem();

        if(newValue == null){
            emptyVbox.toFront();
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
        else if(newValue.equals(Cipher.CAESAR)){
            shiftOptionVbox.toFront();
        }
        else{
            emptyVbox.toFront();
        }
    }
}
