package softuni.exam.models.dto.xml.offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferRootImportXMLDto {

    @XmlElement(name = "offer")
    private List<OfferImportXMLDto> offerImportXMLDtoList;

    public OfferRootImportXMLDto() {
    }

    public List<OfferImportXMLDto> getOfferImportXMLDtoList() {
        return offerImportXMLDtoList;
    }

    public void setOfferImportXMLDtoList(List<OfferImportXMLDto> offerImportXMLDtoList) {
        this.offerImportXMLDtoList = offerImportXMLDtoList;
    }
}
