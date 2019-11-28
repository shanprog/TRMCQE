package ru.aofoms.controller;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputControl;
import ru.aofoms.util.MaskField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public interface StyleController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    default void enableErrorStyle(Node node) {
        node.getStyleClass().addAll("error_text_field");
    }

    default void enableErrorStyle(Node... nodes) {
        for (Node node : nodes)
            enableErrorStyle(node);
    }

    default void disableErrorStyle(Node node) {
        node.getStyleClass().removeAll("error_text_field");
    }

    default void disableErrorStyle(Node... nodes) {
        for (Node node : nodes)
            disableErrorStyle(node);
    }

    default boolean errorsInDateField(MaskField dateField) {

        if (dateField.getText().isEmpty()) {
            enableErrorStyle(dateField);
            return true;
        }

        if (errorsInMaskField(dateField)) {
            return true;
        }

        try {
            LocalDate.parse(dateField.getText(), formatter);
        } catch (DateTimeParseException dtpException) {
            enableErrorStyle(dateField);
            return true;
        }

        return false;
    }

    default boolean errorsInMaskField(MaskField... maskFields) {

        boolean check = false;

        for (MaskField field : maskFields) {
            for (char ch : field.getText().trim().toCharArray())
                if (ch == '_') {
                    enableErrorStyle(field);
                    check = true;
                }
        }

        return check;
    }

    default boolean errorsInTextField(TextInputControl... fields) {
        boolean check = false;

        for (TextInputControl tic : fields) {
            if (tic.getText().trim().isEmpty()) {
                enableErrorStyle(tic);
                check = true;
            }
        }

        return check;
    }

    default boolean errorsInListView(ListView... views) {
        boolean check = false;

        for (ListView listView : views) {
            if (listView.getItems().isEmpty()) {
                enableErrorStyle(listView);
                check = true;
            }
        }

        return check;
    }
}
