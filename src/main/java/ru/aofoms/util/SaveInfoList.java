package ru.aofoms.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aofoms.entity.*;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaveInfoList {

    private List<SaveInfo> saveInfoList = new ArrayList<>();

    @Autowired
    private SaveInfoIdGenerator generator;

    public void addSaveInfo(SaveInfo saveInfo, Expert expert) {
        if (saveInfo.getExpertDocType() != ExpertDocType.CERTIFICATE && saveInfo.getExpertDocType() != ExpertDocType.EXPERTISE) {
            ((EntityWithExpert) saveInfo.getObject()).setExpert(expert);
        }

        for (SaveInfo si : saveInfoList) {
            if (si.equals(saveInfo)) {
                if (si.getAction() == Action.ADD) {
                    saveInfo.setAction(Action.ADD);
                }
            }
        }

        saveInfoList.remove(saveInfo);
        saveInfoList.add(saveInfo);

        // Надо написать сортировку такую, чтобы эксперт сохранялся правильно.
        saveInfoList.sort((o1, o2) -> {
            if (o1 == null && o2 == null) return 0;

            if (o1 == null) return -1;

            if (o2 == null) return 1;

            // Выведение экспертиз в конец списка, чтобы экспертизы сохранялись после специальностей. Костыль, так сказать
            if (o1.getObject() instanceof Expertise && !(o2.getObject() instanceof Expertise)) return 1;
            if (o2.getObject() instanceof Expertise && !(o1.getObject() instanceof Expertise)) return -1;

            if (o1 instanceof Comparable) return ((Comparable) o1.getId()).compareTo(o2.getId());

            return Collator.getInstance().compare(o1.getId().toString(), o2.getId().toString());
        });
    }

    public List<SaveInfo> getSaveInfoList() {
        return saveInfoList;
    }

    public ObservableList<SaveInfo> getElementsByDocTypeFromSaveInfoList(ExpertDocType expertDocType) {
        ObservableList<SaveInfo> result = FXCollections.observableArrayList();
        saveInfoList.stream().filter(si -> si.getExpertDocType() == expertDocType).forEach(result::add);
        return result;
    }

    public ObservableList<SaveInfo> getListToView(ExpertDocType expertDocType, ObservableList listItems) {
        ObservableList<SaveInfo> listToView = getElementsByDocTypeFromSaveInfoList(expertDocType);

        if (listToView.isEmpty()) {

            for (Object item : listItems) {
                SaveInfo saveInfo = new SaveInfo(generator.getNextValue(item.getClass()), Action.CONST, item);

                saveInfoList.add(saveInfo);
                listToView.add(saveInfo);
            }
        }

        // Выкидывание объектов с действием DELETE
        ObservableList<SaveInfo> deleteList = FXCollections.observableArrayList();
        listToView.stream().filter(saveInfo -> saveInfo.getAction() == Action.DELETE).forEach(deleteList::add);
        listToView.removeAll(deleteList);

        return listToView;
    }

    public ObservableList<SaveInfo> getListToViewInviteOrg(ObservableList listItems) {
        ObservableList<SaveInfo> listToView = getElementsInviteOrgFromSaveInfoList();

        if (listToView.isEmpty()) {

            for (Object item : listItems) {
                SaveInfo saveInfo = new SaveInfo(generator.getNextValue(item.getClass()), Action.CONST, item);

                saveInfoList.add(saveInfo);
                listToView.add(saveInfo);
            }
        }

        // Выкидывание объектов с действием DELETE
        ObservableList<SaveInfo> deleteList = FXCollections.observableArrayList();
        listToView.stream().filter(saveInfo -> saveInfo.getAction() == Action.DELETE).forEach(deleteList::add);
        listToView.removeAll(deleteList);

        return listToView;
    }

    private ObservableList<SaveInfo> getElementsInviteOrgFromSaveInfoList() {

        ObservableList<SaveInfo> result = FXCollections.observableArrayList();

        saveInfoList.stream().filter(si -> si.getObject() instanceof InviteOrg).forEach(result::add);

        return result;
    }

    public void setSaveInfoList(List<SaveInfo> saveInfoList) {
        this.saveInfoList = saveInfoList;
    }

    public void clearSaveInfoList() {
        saveInfoList.clear();
    }

    public void removeElement(SaveInfo element) {
        saveInfoList.remove(element);
    }

    public void addElement(SaveInfo element) {
        saveInfoList.add(element);
    }

    public ObservableList<ExpertSpecialty> getSpecialtiesList() {
        ObservableList<ExpertSpecialty> result = FXCollections.observableArrayList();

        ObservableList<SaveInfo> temp = getElementsByDocTypeFromSaveInfoList(ExpertDocType.SPECIALTY);
        temp.stream().map(si -> (ExpertSpecialty) si.getObject()).forEach(result::add);

        return result;
    }

    public ObservableList<Workplace> getWorkplacesList() {
        ObservableList<Workplace> result = FXCollections.observableArrayList();

        ObservableList<SaveInfo> temp = getElementsByDocTypeFromSaveInfoList(ExpertDocType.WORKPLACE);
        temp.stream().map(si -> (Workplace) si.getObject()).forEach(result::add);

        return result;
    }

    public List getListByType(ExpertDocType expertDocType) {
        List result;
        ObservableList<SaveInfo> list = getElementsByDocTypeFromSaveInfoList(expertDocType);
        result = list.stream().map(SaveInfo::getObject).collect(Collectors.toList());

        return result;
    }

    public void removeExpertiseElement(SaveInfo specialtyItem) {
        SaveInfo findElement = null;

        for (SaveInfo saveInfo : saveInfoList) {

            if (saveInfo.getExpertDocType() == ExpertDocType.EXPERTISE &&
                    ((Expertise) saveInfo.getObject()).getSpecialty().getSpecialty().equals(
                            ((ExpertSpecialty) specialtyItem.getObject()).getSpecialty())
            ) {
                findElement = saveInfo;
            }
        }

        saveInfoList.remove(findElement);
    }
}
