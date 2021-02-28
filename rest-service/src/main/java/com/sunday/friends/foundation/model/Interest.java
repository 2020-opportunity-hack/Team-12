package com.sunday.friends.foundation.model;

import javax.persistence.*;
import java.util.Date;
/**
 * Interest Model
 * @author  Mahapatra Manas
 * @version 1.0
 * @since   11-20-2020
 */
@Entity
@Table(name="Interest")
public class Interest {
    private Integer index;
    private Float interest;
    @Column(name = "time", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Interest() {
    }
    public Interest(Integer index, Float interest, Date timestamp) {
        this.index = index;
        this.interest = interest;
        this.timestamp = timestamp;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Float getInterest() {
        return interest;
    }

    public void setInterest(Float interest) {
        this.interest = interest;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
