//package di.web.user;
//
//import di.model.dto.user.ResponseUser;
//import di.model.entity.telephone.Telephone;
//import di.model.entity.user.RegularUser;
//import di.model.entity.user.User;
//import di.repository.telephone.TelephoneRepository;
//import di.repository.user.UserRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class WebUserService {
//    private final UserRepository userRepository;
//    private final TelephoneRepository telephoneRepository;
//
//    @Autowired
//    public WebUserService(UserRepository userRepository,TelephoneRepository telephoneRepository) {
//        this.userRepository = userRepository;
//        this.telephoneRepository = telephoneRepository;
//    }
//
//
//
//
//
//
//
//    public User convertResponseUserToUser(ResponseUser responseUser) {
//      User user = new User();
//        user.setEmail(responseUser.getEmail());
//        user.setPassword(responseUser.getPassword());
//        user.setTelephone(responseUser.getTelephone());
//        user.setName(responseUser.getName());
//        return user;
//    }
//
//    public  User findByPhone(String phone) {
//     return    userRepository.getByTelephone(phone);
//    }
//}
