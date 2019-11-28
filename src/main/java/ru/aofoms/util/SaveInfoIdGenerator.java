package ru.aofoms.util;

import org.springframework.stereotype.Service;

@Service
public class SaveInfoIdGenerator {

    private long generateId = 0;

    private long getNextId() {
        return ++generateId;
    }

    public String getNextValue(Class c) {
        long nid = getNextId();
        String nextId = (nid < 10) ? "0" + nid : "" + nid;
        return c.getSimpleName() + "_" + nextId;
    }

    public void clearValue() {
        generateId = 0;
    }
}
