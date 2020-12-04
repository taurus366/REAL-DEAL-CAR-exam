package softuni.exam.models.dto.xml;

import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entity.Rating;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerImportXMLDto {


    @XmlElement(name = "first-name")
    @Length(min = 2,max = 20)
    private String firstName;

    @XmlElement(name = "last-name")
    @Length(min = 2,max = 20)
    private String lastName;

    @XmlElement(name = "email")
    @Email(regexp = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$")
    private String email;

    @XmlElement(name = "rating")
    @NotNull
    private Rating rating;

    @XmlElement(name = "town")
    @NotNull
    private String town;

    public SellerImportXMLDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
