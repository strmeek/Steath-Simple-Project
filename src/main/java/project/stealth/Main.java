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
        System.out.println(controller.testingCaesarEncrypt());
        System.out.println(controller.testingCaesarDecrypt());
        System.out.println("--------------------------------------------");
        System.out.println(controller.testingXorEncrypt());
        System.out.println(controller.testingXorDecrypt());
        System.out.println("--------------------------------------------");
        System.out.println(controller.testingRailFenceEncrypt());
        System.out.println(controller.testingRailFenceDecrypt());
        System.out.println("--------------------------------------------");
        System.out.println(controller.testingPolybiusSquareEncrypt());
        System.out.println(controller.testingPolybiusSquareDecrypt());
        System.out.println("--------------------------------------------");
        System.out.println(controller.testingOneTimePadEncrypt());
        System.out.println(controller.testingOneTimePadDecrypt());
        System.out.println("--------------------------------------------");
        System.out.println(controller.testingHillEncrypt());
        System.out.println(controller.testingHillDecrypt());
    }
}