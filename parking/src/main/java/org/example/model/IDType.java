package org.example.model;

public enum IDType {
    PASSPORT("Passport"),
    DRIVERS_LICENSE("Driver's Licence"),
    STUDENT_ID("Student ID"),
    NATIONAL_ID("National ID"),
    FOREIGN_ID("Foreign ID");

    private final String text;

    private IDType(String text){
        this.text = text;
    }

    public static IDType returnIDType(String text){
        for(IDType idType : IDType.values()){
            if(idType.text.equals(text)) {
                return idType;
            }
        }
        return null;
    }

    public static String[] getIDTypes(){
        String[] idTypes = new String[IDType.values().length];
        for(int n = 0; n < idTypes.length; n++){
            idTypes[n] = IDType.values()[n].getText();
        }
        return idTypes;
    }

    public String getText(){
        return this.text;
    }
}