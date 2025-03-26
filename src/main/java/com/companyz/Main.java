package com.companyz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the login view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/companyz/views/LoginView.fxml"));
        Scene loginScene = new Scene(loader.load());
        
        primaryStage.setTitle("Employee Management System - Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
