package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.CarImportDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private static final String CAR_PATH = "src/main/resources/files/json/cars.json";

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final Gson gson;

    @Autowired
    private final ValidationUtil validationUtil;

    @Autowired
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(CAR_PATH)));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();

        CarImportDto[] carImportDtos = gson.fromJson(readCarsFileContent(), CarImportDto[].class);

        for (CarImportDto carImportDto : carImportDtos) {

            Car car = carRepository.findCarByMakeAndModelAndKilometers(carImportDto.getMake(), carImportDto.getModel(), carImportDto.getKilometers());

            if (validationUtil.isValid(carImportDto) && car == null){
                car = modelMapper.map(carImportDto, Car.class);

                carRepository.saveAndFlush(car);

                sb.append(String.format("Successfully imported car - %s - %s", car.getMake(),car.getModel()))
                        .append(System.lineSeparator());

            }else {
                sb.append("Invalid car").append(System.lineSeparator());
            }

        }



        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();

        List<Car> cars = carRepository.findbyCAR();

        cars.forEach(c -> {
            sb.append(String.format("Car make - %s,model - %s", c.getMake(),c.getModel()))
                    .append(System.lineSeparator());
            sb.append(String.format("\tKilometers - %d", c.getKilometers()))
                    .append(System.lineSeparator());
            sb.append(String.format("\tRegistered on - %s", c.getRegisteredOn().toString()))
                    .append(System.lineSeparator());
            sb.append(String.format("\tNumber of pictures - %d", c.getPictures().size()))
                    .append(System.lineSeparator());

        });

        return sb.toString();
    }
}
