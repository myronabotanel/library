package org.example.view;

import javafx.scene.Scene;
import java.util.Stack;

public class NavigationManager
{
    private static final Stack<Scene> sceneHistory = new Stack<>();

    public static void navigateTo(Scene scene, javafx.stage.Stage stage) {
        if (stage.getScene() != null) {
            sceneHistory.push(stage.getScene()); // Salvam scena curenta
        }
        stage.setScene(scene); // Seteam scena noua
    }

    public static void goBack(javafx.stage.Stage stage) {
        if (!sceneHistory.isEmpty()) {
            stage.setScene(sceneHistory.pop()); // Revinim la scena anterioara
        }
    }
}
