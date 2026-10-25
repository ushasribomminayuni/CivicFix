
package com.civicfix;

import jakarta.persistence.*;

@Entity
@Table(name = "COMPLAINTS")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // Resident details
    @Column(nullable = false)
    private String residentName;

    @Column(nullable = false)
    private String mobileNumber;

    // Address details
    private String houseNumber;

    private String street;

    private String locality;

    private String ward;

    private String pinCode;

    // Complaint details
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String location;

    // Complaint status
    private String status = "Pending";

    public Complaint() {
    }

    // ID
    public Long getId() {
        return id;
    }

    // Resident name
    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    // Mobile number
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    // House number
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    // Street
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    // Locality
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    // Ward
    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    // PIN code
    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    // Complaint title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Complaint category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Exact problem location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
