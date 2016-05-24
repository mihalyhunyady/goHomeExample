package main;

import com.epam.hujj.GoHome;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloWorld extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        final Parent root = loader.load();
        final GoHome controller = loader.<GoHome>getController();

        primaryStage.setTitle("Go Home");
        primaryStage.setScene(new Scene(root, 300, 150));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            controller.stop();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
