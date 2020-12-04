package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.SellerImportXMLDto;
import softuni.exam.models.dto.xml.SellerRootImportXMLDto;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerServiceImpl implements SellerService {

    private static final String SELLER_PATH = "src/main/resources/files/xml/sellers.xml";

    @Autowired
    private final SellerRepository sellerRepository;

    @Autowired
    private final XmlParser xmlParser;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(SELLER_PATH)));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
            StringBuilder sb = new StringBuilder();

        SellerRootImportXMLDto sellerRootImportXMLDto = xmlParser.parseXml(SellerRootImportXMLDto.class, SELLER_PATH);

        for (SellerImportXMLDto sellerImportXMLDto : sellerRootImportXMLDto.getSellerImportXMLDtos()) {

            Seller email = sellerRepository.findSellerByEmail(sellerImportXMLDto.getEmail());

            if (validationUtil.isValid(sellerImportXMLDto) && email == null){

                Seller seller = modelMapper.map(sellerImportXMLDto, Seller.class);

                sellerRepository.saveAndFlush(seller);

                sb.append(String.format("Successfully import seller %s - %s", seller.getLastName(),seller.getEmail()))
                        .append(System.lineSeparator());
            }else {
                sb.append("Invalid seller").append(System.lineSeparator());
            }

        }


        return sb.toString();
    }
}
