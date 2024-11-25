package org.example.launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application
{
    public static void main(String[] args){
        launch(args); // apel met start
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginComponentFactory.getInstance(false, primaryStage);
    }
}