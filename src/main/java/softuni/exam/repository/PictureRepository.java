package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Picture;

import java.util.List;

//ToDo
public interface PictureRepository extends JpaRepository<Picture,Integer> {
        Picture findPictureByName(String name);
        List<Picture> findPicturesByCar_Id(Integer integer);
}
