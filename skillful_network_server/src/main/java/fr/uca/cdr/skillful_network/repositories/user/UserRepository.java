package fr.uca.cdr.skillful_network.repositories.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.uca.cdr.skillful_network.entities.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findById(long id);

	Page<User> findByLastNameContainsOrFirstNameContainsAllIgnoreCase(Pageable pageable, String keyword1, String keyword2);

}
