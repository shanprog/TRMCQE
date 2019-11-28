package ru.aofoms.util;

import java.util.Objects;

public class SaveInfo {

    private String id;
    private Action action;
    private Object object;

    public SaveInfo() {
    }

    public SaveInfo(String id, Action action, Object object) {
        this.id = id;
        this.action = action;
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ExpertDocType getExpertDocType() {
        return ExpertDocType.getExpertDocType(object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveInfo saveInfo = (SaveInfo) o;
        return id.equals(saveInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

//    public String toString() {
//        return "SaveInfo{" +
//                "id='" + id + '\'' +
//                ", action=" + action +
//                ", object=" + object +
//                '}';
//    }

    @Override
    public String toString() {
        return object.toString();
    }
}
