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
    @Query("SELECT u FROM  User u WHERE u.telephone.number = :number")
    Optional<User> getByTelephone(@Param("number") String telephone);

    @Query("SELECT u FROM  User u WHERE u.name = :name")
    Optional<User> getByUserName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> getUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM  User u WHERE u.telephone.number = :number")
    Optional<User> getUserByTelephone(@Param("number") String telephone);

    boolean existsByUserName(String userName);

    boolean existByEmail(String email);

    // Guest User
    @Query("SELECT u FROM GuestUser  u WHERE u.email = :email")
    Optional<GuestUser> getGuestUserByEmail(@Param("email") String email);


    //RegisterUser
    @Query("SELECT u FROM RegisterUser u WHERE u.telephone.number = :number")
    Optional<RegisterUser> getRegistryUserByPhone(@Param("number") String number);
}
