package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Offer;

import java.time.LocalDateTime;

//ToDo
public interface OfferRepository extends JpaRepository<Offer,Integer> {

    Offer findOfferByDescriptionAndAddedOn(String description, LocalDateTime addedOn);
}
