package com.example.numberbook;

public class PersonData {

    private int contactId;
    private String fullName;
    private String mobileNumber;
    private String dataSource;
    private String createdDate;

    public PersonData() {
    }

    public PersonData(String fullName, String mobileNumber) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
    }

    public int getContactId() {
        return contactId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}