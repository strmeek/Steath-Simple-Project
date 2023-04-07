package project.stealth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main /*extends Application*/ {
    /*@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        String css = this.getClass().getResource("style.css").toExternalForm(); // get the css file.
        scene.getStylesheets().add(css); // add the css file to the scene.
        stage.setTitle("Stealth - Encriptador de Texto");
        stage.initStyle(StageStyle.UNIFIED);
        stage.setScene(scene);
        stage.show();
    }*/

    public static void main(String[] args) {
        //launch();
        var controller = new MainController();
        System.out.println(controller.testing());
    }
}