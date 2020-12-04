package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    @Column
    private String make;
    @Column
    private String model;
    @Column
    private int kilometers;
    @Column(name = "registered_on")
    private LocalDate registeredOn;

    @OneToMany(mappedBy = "car",fetch = FetchType.EAGER)
    private List<Picture> pictures;

    @OneToMany(mappedBy = "car")
    private List<Offer> offers;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public Car() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }
}
