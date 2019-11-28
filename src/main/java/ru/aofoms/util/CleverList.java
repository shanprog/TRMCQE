package ru.aofoms.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Слишком похоже на SaveInfoList. Требуется рефакторинг в этом месте
@Component
public class CleverList {

    @Autowired
    private SaveInfoIdGenerator idGenerator;

    private List<SaveInfo> list = new ArrayList<>();

    public ObservableList getObservableList(List all) {

        list.clear();
        all.forEach(element -> list.add(new SaveInfo(idGenerator.getNextValue(element.getClass()), Action.CONST, element)));

        return FXCollections.observableArrayList(list);
    }

    public ObservableList<SaveInfo> getObservableList() {
        return FXCollections.observableArrayList(list);
    }

    public void removeElement(SaveInfo element) {
        list.remove(element);
    }

    public void addElement(SaveInfo element) {
        list.add(element);
    }

    public void setDeleteMarker(SaveInfo selectedItem) {
        SaveInfo item = list.get(list.indexOf(selectedItem));
        item.setAction(Action.DELETE);
    }

    public void clear() {
        list.clear();
        idGenerator.clearValue();
    }

    public void replaceElement(SaveInfo element) {
        list.remove(element);
        list.add(element);
    }
}
