package by.gurinovich.bamper.repositories.user;

import by.gurinovich.bamper.models.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    Optional<Address> findByAddress(String address);
    Optional<Address> findByCoordinates(String coordinates);
}
