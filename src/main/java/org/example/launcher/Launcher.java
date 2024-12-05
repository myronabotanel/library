package org.example.launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application
{
    public static void main(String[] args){
        launch(args); // apel met start
    } //launch in spate va apela Start ce va primi primaryStage si pe care vom putea pune noi diferite scenex

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginComponentFactory.getInstance(false, primaryStage);
    }
}