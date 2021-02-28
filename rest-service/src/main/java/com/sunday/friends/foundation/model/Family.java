package com.sunday.friends.foundation.model;
import javax.persistence.*;
/**
 * Family Model
 * @author  Mahapatra Manas
 * @version 1.0
 * @since   11-20-2020
 */

@Entity
@Table(name="Family")
public class Family {


    private Integer familyId;
    private String address;

    public Family(Integer familyId, String address) {
        this.familyId = familyId;
        this.address = address;
    }

    public Family() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
