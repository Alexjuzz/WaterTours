package di.service.user;


import di.customexceptions.email.EmailAlreadyUsedException;
import di.customexceptions.telephone.TelephoneAlreadyExistException;
import di.model.dto.user.ResponseRegistryUser;
import di.model.dto.user.ResponseUser;
import di.model.entity.user.RegularUser;
import di.model.entity.user.User;
import di.model.entity.telephone.Telephone;
import di.repository.telephone.TelephoneRepository;
import di.repository.user.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository repository;
    private final TelephoneRepository telephoneRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    @Autowired
    public UserService(UserRepository repository, TelephoneRepository telephoneRepository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.telephoneRepository = telephoneRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public ResponseUser createUser(RegularUser user) {
        //TODO доделать validator
        Set<ConstraintViolation<Telephone>> validation = validator.validate(user.getTelephone());
        if (!validation.isEmpty()) {
            throw new ConstraintViolationException(validation);
        }

        if (telephoneRepository.existsByNumber(user.getTelephone().getNumber())) {
            throw new TelephoneAlreadyExistException("Telephone number already exist");
        }
       RegularUser requestUser = new RegularUser();
        requestUser.setName(user.getName());
        requestUser.setEmail(user.getEmail());
        requestUser.setPassword(user.getPassword());

        Telephone telephone = new Telephone(user.getTelephone().getNumber());
        telephone.setUser(requestUser);
        requestUser.setTelephone(telephone);

        return convertUserToResponseUser(repository.save(requestUser));
    }

    @Transactional
    public List<ResponseUser> getAllUsers() {
        return repository.findAll().stream().map(this::convertUserToResponseUser).toList();
    }

    @Transactional
    public ResponseUser getUserByPhone(String number) {
        RegularUser user = repository.getByTelephone(number);
        return convertUserToResponseUser(user);
    }


    //region методы конвертации обьектов.
    private ResponseUser convertUserToResponseUser(RegularUser user) {
        ResponseUser response = new ResponseUser();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setTelephone(user.getTelephone());
        return response;
    }

    private ResponseRegistryUser convertUserToResponseRegistryUser(RegularUser user) {
        ResponseRegistryUser responseUser = new ResponseRegistryUser();
        responseUser.setEmail(user.getEmail());
        responseUser.setName(user.getName());
        responseUser.setId(user.getId());
        responseUser.setTelephone(user.getTelephone().getNumber());
        return responseUser;
    }
    //endregion
//region 26/04/2024

    /**
     * Регистрирует нового пользователя, проверяя, что электронная почта и телефонный номер еще не использовались.
     *
     * @param user Данные пользователя для регистрации.
     * @return ResponseRegistryUser Данные зарегистрированного пользователя.
     * @throws EmailAlreadyUsedException      если указанный email уже используется.
     * @throws TelephoneAlreadyExistException если указанный телефонный номер уже зарегистрирован.
     */

    @Transactional
    public ResponseRegistryUser registerUser(ResponseUser user) {
        checkValidData(user);
        RegularUser registerUser = new RegularUser();
        registerUser.setEmail(user.getEmail());
        registerUser.setName(user.getName());
        registerUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Telephone telephone = new Telephone(user.getTelephone().getNumber());
        telephone.setUser(registerUser);

        registerUser.setTelephone(telephone);
        return convertUserToResponseRegistryUser(repository.save(registerUser));
    }



public boolean checkValidData(ResponseUser user) {
    if (repository.getUserByEmail(user.getEmail()).isPresent()) {
        throw new EmailAlreadyUsedException("Email already used");
    }
    if (telephoneRepository.existsByNumber(user.getTelephone().getNumber())) {
        throw new TelephoneAlreadyExistException("Telephone number already exist");
    }
    return true;
}
// endregion
}
