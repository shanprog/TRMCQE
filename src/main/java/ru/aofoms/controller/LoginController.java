package ru.aofoms.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import ru.aofoms.entity.User;
import ru.aofoms.service.UserService;
import ru.aofoms.view.LoginView;
import ru.aofoms.view.MainAdminView;
import ru.aofoms.view.MainUserView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FXMLController
public class LoginController {

    private final UserService userService;

    private final MainUserView mainUserView;
    private final MainAdminView mainAdminView;
    private final LoginView loginView;

    @FXML
    private TextField name;
    @FXML
    private PasswordField password;

    public LoginController(UserService userService, MainUserView mainUserView, MainAdminView mainAdminView, LoginView loginView) {
        this.userService = userService;
        this.mainUserView = mainUserView;
        this.mainAdminView = mainAdminView;
        this.loginView = loginView;
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void login() {
        User user;

        try {
            if ((user = userService.findUserByNameAndPassword(name.getText().trim(), DigestUtils.md5Hex(password.getText().trim()))) != null) {
                Stage stage = new Stage();

                String roleName = user.getRole().getName();

                if (roleName.equals("User")) {
                    stage.setScene(new Scene(mainUserView.getView()));
                    stage.setTitle("Реестр ЭКМП");
                } else if (roleName.equals("Administrator")) {
                    stage.setScene(new Scene(mainAdminView.getView()));
                    stage.setTitle("Реестр ЭКМП: Администрирование");
                }

                stage.setOnCloseRequest(event -> Platform.exit());
                stage.show();

                ((Stage) loginView.getView().getScene().getWindow()).close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(loginView.getView().getScene().getWindow());
                alert.setTitle("Неверные значения!");
                alert.setContentText("Логин или пароль заданы неверно!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("d_M_y_H_m_s");
            String filename = "D://EKMP//errors//log_" + LocalDateTime.now().format(dtFormatter) + ".txt";

            File file = new File(filename);
            try {
                if (file.createNewFile()) {
                    PrintWriter writer = new PrintWriter(filename);

                    writer.println(e.toString());
                    writer.println();

                    StackTraceElement[] stackTrace = e.getStackTrace();
                    for (StackTraceElement ste : stackTrace) {
                        writer.println(ste.toString());
                    }

                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(loginView.getView().getScene().getWindow());
            alert.setTitle("Неверные значения!");
            alert.showAndWait();

        }

    }
}
