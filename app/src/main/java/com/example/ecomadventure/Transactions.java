package com.example.ecomadventure;

public class Transactions {
    private String username;
    private String title;
    private String image;
    private String noOfTicket;
    private String billAmount;
    private String transactionId;
    private String status;

    public Transactions(String username, String title, String imageId, String noOfTicket, String billAmount, String transactionId, String status) {
        this.username = username;
        this.title = title;
        this.image = imageId;
        this.noOfTicket = noOfTicket;
        this.billAmount = billAmount;
        this.transactionId = transactionId;
        this.status = status;
    }

    public Transactions() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageId() {
        return image;
    }

    public void setImageId(String imageId) {
        this.image = imageId;
    }

    public String getNoOfTicket() {
        return noOfTicket;
    }

    public void setNoOfTicket(String noOfTicket) {
        this.noOfTicket = noOfTicket;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
