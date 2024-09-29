package com.example.isyncpos.objects;

public class FieldTypeObject {

    private String name;

    private int isRequired;

    public FieldTypeObject(String name, int isRequired) {
        this.name = name;
        this.isRequired = isRequired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(int isRequired) {
        this.isRequired = isRequired;
    }

}
