package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.offer.OfferImportXMLDto;
import softuni.exam.models.dto.xml.offer.OfferRootImportXMLDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.Picture;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String OFFER_PATH = "src/main/resources/files/xml/offers.xml";

    @Autowired
    private final OfferRepository offerRepository;

    @Autowired
    private final XmlParser xmlParser;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final SellerRepository sellerRepository;

    @Autowired
    private final ValidationUtil validationUtil;

    @Autowired
    private final PictureRepository pictureRepository;

    public OfferServiceImpl(OfferRepository offerRepository, XmlParser xmlParser, ModelMapper modelMapper, CarRepository carRepository, SellerRepository sellerRepository, ValidationUtil validationUtil, PictureRepository pictureRepository) {
        this.offerRepository = offerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.validationUtil = validationUtil;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(OFFER_PATH)));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        OfferRootImportXMLDto offerRootImportXMLDto = xmlParser.parseXml(OfferRootImportXMLDto.class, OFFER_PATH);

        for (OfferImportXMLDto offerImportXMLDto : offerRootImportXMLDto.getOfferImportXMLDtoList()) {

            Offer offer = offerRepository.findOfferByDescriptionAndAddedOn(offerImportXMLDto.getDescription(), offerImportXMLDto.getAddedOn());
            Car car = carRepository.getOne(offerImportXMLDto.getCarImportXMLDto().getId());
            Seller seller = sellerRepository.getOne(offerImportXMLDto.getSellerImportXMLDto().getId());

            if (validationUtil.isValid(offerImportXMLDto) && offer == null && car.getId() != 0 && seller.getId() != 0){
                List<Picture> pictures =  pictureRepository.findPicturesByCar_Id(car.getId());

                Offer offer1 = modelMapper.map(offerImportXMLDto, Offer.class);
                offer1.setCar(car);
                offer1.setSeller(seller);
                offer1.setPictures(pictures);
                offerRepository.saveAndFlush(offer1);

                sb.append(String.format("Successfully import offer %s - %b", offer1.getAddedOn().toString(),offer1.isHasGoldStatus()))
                        .append(System.lineSeparator());
            }else {
                sb.append("Invalid offer").append(System.lineSeparator());
            }
        }


        return sb.toString();
    }
}
