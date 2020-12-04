package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.PictureImportDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURE_PATH = "src/main/resources/files/json/pictures.json";

    @Autowired
    private final PictureRepository pictureRepository;

    @Autowired
    private final Gson gson;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ValidationUtil validationUtil;

    @Autowired
    private final CarRepository carRepository;

    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, CarRepository carRepository) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.carRepository = carRepository;
    }


    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PICTURE_PATH)));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        PictureImportDto[] pictureImportDtos = gson.fromJson(readPicturesFromFile(), PictureImportDto[].class);

        for (PictureImportDto pictureImportDto : pictureImportDtos) {

            Picture picture = pictureRepository.findPictureByName(pictureImportDto.getName());
            Car car = carRepository.getOne(pictureImportDto.getCar());

            if (validationUtil.isValid(pictureImportDto) && picture == null && car.getId() != 0){
                Picture picture1 = modelMapper.map(pictureImportDto, Picture.class);
                picture1.setCar(car);
                pictureRepository.saveAndFlush(picture1);
                sb.append(String.format("Successfully import picture - %s", picture1.getName()))
                        .append(System.lineSeparator());
            }else {
                sb.append("Invalid picture").append(System.lineSeparator());
            }

        }


        return sb.toString();
    }
}
