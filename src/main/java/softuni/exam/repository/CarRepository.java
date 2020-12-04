package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.exam.models.entity.Car;

import java.util.List;

//ToDo
public interface CarRepository extends JpaRepository<Car,Integer> {

    Car findCarByMakeAndModelAndKilometers(String make, String model, int kilometers);

    @Query("SELECT c FROM Car c ORDER BY c.pictures.size DESC , c.make")
    List<Car> findbyCAR();

}
