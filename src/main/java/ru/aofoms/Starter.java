package ru.aofoms;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.aofoms.util.SplashWindow;
import ru.aofoms.view.LoginView;

@SpringBootApplication
public class Starter extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(Starter.class, LoginView.class, new SplashWindow(), args);
    }

}
