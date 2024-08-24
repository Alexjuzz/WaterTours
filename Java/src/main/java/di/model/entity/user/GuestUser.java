package di.model.entity.user;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("guest_users")
public class GuestUser extends User {

}
