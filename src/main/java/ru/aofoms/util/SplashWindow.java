package ru.aofoms.util;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class SplashWindow extends SplashScreen {

    @Override
    public Parent getParent() {

        try {
            return FXMLLoader.load(getClass().getResource("/fxml/splash.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

