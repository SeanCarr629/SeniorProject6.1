package com.example.andre.seniorproject;

import java.util.ArrayList;

public class Customer {

    private String firstName;
    private String lastName;
    private String companyName;
    private String phoneNumber;
    private String emailAddress;
    private String companyAddress;
    private String state;
    private String zipCode;
    private String fullName;
    private String customerID;
    private Order order;
    private ArrayList<Inventory> inventory;



    public Customer () {};

    public Customer(String firstName,String lastName,String companyName,String phoneNumber,String emailAddress,
                    String companyAddress,String state, String zipCode, String fullName)
    {

        this.firstName=firstName;
        this.lastName=lastName;
        this.companyName=companyName;
        this.phoneNumber=phoneNumber;
        this.emailAddress=emailAddress;
        this.companyAddress=companyAddress;
        this.state=state;
        this.zipCode= zipCode;
        this.fullName= firstName+ " "+ lastName;
        this.inventory = new ArrayList<Inventory>();
    }

    public ArrayList<Inventory> getInventory() {
        return this.inventory;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCompanyAddress() { return companyAddress; }

    public void setCompanyAddress(String companyAddress) { this.companyAddress = companyAddress; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getCustomerID() { return customerID; }

    public void setCustomerID(String customerID) { this.customerID = customerID; }



}
