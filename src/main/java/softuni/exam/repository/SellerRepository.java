package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Seller;

//ToDo
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Seller findSellerByEmail(String email);
}
