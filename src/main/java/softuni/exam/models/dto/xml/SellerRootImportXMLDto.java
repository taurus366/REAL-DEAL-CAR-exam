package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerRootImportXMLDto {

    @XmlElement(name = "seller")
    private List<SellerImportXMLDto> sellerImportXMLDtos;

    public SellerRootImportXMLDto() {
    }

    public List<SellerImportXMLDto> getSellerImportXMLDtos() {
        return sellerImportXMLDtos;
    }

    public void setSellerImportXMLDtos(List<SellerImportXMLDto> sellerImportXMLDtos) {
        this.sellerImportXMLDtos = sellerImportXMLDtos;
    }
}
