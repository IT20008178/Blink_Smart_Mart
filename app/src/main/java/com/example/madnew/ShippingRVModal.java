package com.example.madnew;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class ShippingRVModal implements Serializable {
    // creating variables for our different fields.
    private String shippingName;
    private String shippingContact;
    private String shippingNIC;
    private String shippingEmail;
    private String shippingProvince;
    private String shippingAddress;

    @Exclude
    private String shippingID;

    public ShippingRVModal() {
    }

    public ShippingRVModal(String shippingName, String shippingContact, String shippingNIC, String shippingEmail, String shippingProvince, String shippingAddress) {
        this.shippingName = shippingName;
        this.shippingContact = shippingContact;
        this.shippingNIC = shippingNIC;
        this.shippingEmail = shippingEmail;
        this.shippingProvince = shippingProvince;
        this.shippingAddress = shippingAddress;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingContact() {
        return shippingContact;
    }

    public void setShippingContact(String shippingContact) {
        this.shippingContact = shippingContact;
    }

    public String getShippingNIC() {
        return shippingNIC;
    }

    public void setShippingNIC(String shippingNIC) {
        this.shippingNIC = shippingNIC;
    }

    public String getShippingEmail() {
        return shippingEmail;
    }

    public void setShippingEmail(String shippingEmail) {
        this.shippingEmail = shippingEmail;
    }

    public String getShippingProvince() {
        return shippingProvince;
    }

    public void setShippingProvince(String shippingProvince) {
        this.shippingProvince = shippingProvince;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingID() {
        return shippingID;
    }

    public void setShippingID(String shippingID) {
        this.shippingID = shippingID;
    }
}

