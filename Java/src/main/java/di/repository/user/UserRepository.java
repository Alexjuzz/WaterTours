package di.repository.user;

import di.model.entity.user.GuestUser;
import di.model.entity.user.RegisterUser;
import di.model.entity.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // User
    @Query("SELECT u FROM User u WHERE u.telephone.number = :number")
    Optional<User> getByTelephone(@Param("number") String telephone);

//    Optional<User> findByUserName(@Param("name") String name);

    Optional<User> getUserByEmail(@Param("email") String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    // Guest User
    @Query("SELECT u FROM GuestUser u WHERE u.email = :email")
    Optional<GuestUser> getGuestUserByEmail(@Param("email") String email);

    // RegisterUser
    @Query("SELECT u FROM RegisterUser u WHERE u.telephone.number = :number")
    Optional<RegisterUser> getRegisterUserByPhone(@Param("number") String number);

    Optional<RegisterUser> findByName(@Param("name") String name);
}
