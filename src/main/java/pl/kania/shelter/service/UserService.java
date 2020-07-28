package pl.kania.shelter.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kania.shelter.api.model.User;
import pl.kania.shelter.domain.user.UserEntity;
import pl.kania.shelter.domain.user.UserRepository;
import pl.kania.shelter.exceptions.ResourceAlreadyExistsException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResourceAlreadyExistsException("Użytkownik " +user.getUsername()+ " już istnieje");
        }
        UserEntity userEntity = new UserEntity(
                null,
                user.getUsername(),
                encoder.encode(user.getPassword()),
                "USER");

        userRepository.save(userEntity);
    }


}

