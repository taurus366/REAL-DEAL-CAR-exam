package softuni.exam.models.dto.xml.offer;

import org.hibernate.validator.constraints.Length;
import softuni.exam.config.LocalDateTImeAdaptor;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportXMLDto {

    @XmlElement(name = "description")
    @Length(min = 5)
    private String description;

    @XmlElement(name = "price")
    @Min(0)
    private BigDecimal price;

    @XmlElement(name = "added-on")
    @XmlJavaTypeAdapter(LocalDateTImeAdaptor.class)
    private LocalDateTime addedOn;

    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;

    @XmlElement(name = "car")
    private CarImportXMLDto carImportXMLDto;

    @XmlElement(name = "seller")
    private SellerImportXMLDto sellerImportXMLDto;

    public OfferImportXMLDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public CarImportXMLDto getCarImportXMLDto() {
        return carImportXMLDto;
    }

    public void setCarImportXMLDto(CarImportXMLDto carImportXMLDto) {
        this.carImportXMLDto = carImportXMLDto;
    }

    public SellerImportXMLDto getSellerImportXMLDto() {
        return sellerImportXMLDto;
    }

    public void setSellerImportXMLDto(SellerImportXMLDto sellerImportXMLDto) {
        this.sellerImportXMLDto = sellerImportXMLDto;
    }
}
