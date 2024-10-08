package cat.udl.eps.softarch.demo.repository;

import cat.udl.eps.softarch.demo.domain.Apartment;
import cat.udl.eps.softarch.demo.domain.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner, Long>, PagingAndSortingRepository<Owner, Long> {
    Optional<Owner> findById(@Param("id") Long id);
    List<Owner> findByName(@Param("name") String name);
    List<Owner> findByPhone(@Param("phoneNumber") String phoneNumber);
}
