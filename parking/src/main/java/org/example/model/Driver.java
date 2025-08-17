package org.example.model;

public class Driver {
    String uid;
    String name;
    String occupation;
    IDType idType;
    String identificationNumber;
    boolean active;

    public Driver() {
        //default constructor
    }
    public Driver(String uid, String name, String occupation, String idType, String identificationNumber) {
        this.uid = uid;
        this.name = name;
        this.occupation = occupation;
        this.idType = IDType.returnIDType(idType);
        this.identificationNumber = identificationNumber;
        this.active = true;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public IDType getIdType() {
        return idType;
    }

    public void setIdType(IDType idType) {
        this.idType = idType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}