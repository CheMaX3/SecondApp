package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.dto.UserProfile;
import project.entity.Role;
import project.entity.User;
import project.repository.RoleRepository;
import project.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service

public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with name: " + username + " not found");
        }

        return user;
    }

    public User findUserById(Integer userId) {

        Optional<User> userFromDb = userRepository.findById(userId);

        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public List<Role> allRoles() {
        return roleRepository.findAll();
    }

    public void saveUser(User user) {

        buildUserForSave(user);
        userRepository.save(user);

    }

    public boolean deleteUser(Integer userId) {

        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public UserProfile getUserProfile(Integer id) {

        User user = findUserById(id);

        return convertUserToUserProfile(user);
    }

    public void updateUserProfile(UserProfile userProfile, Integer id) {

        User user = userRepository.getReferenceById(id);

        user.setUsername(Optional.ofNullable(userProfile.getUsername()).orElse(user.getUsername()));
        user.setPassword(Optional.ofNullable(bCryptPasswordEncoder.encode(userProfile.getPassword())).orElse(user.getPassword()));
        user.setRoles(Optional.ofNullable(userProfile.getRoles()).orElse(user.getRoles()));
        userRepository.save(user);
    }

    private User buildUserForSave(User userFromRegistrationPage) {
        userFromRegistrationPage.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        userFromRegistrationPage.setPassword(bCryptPasswordEncoder.encode(userFromRegistrationPage.getPassword()));

    }

    public String passwordConfirmation(User user) {

        String message = "";

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            message = "Пароли не совпадают";
        }

        return message;
    }

    public String userExists(User user) {

        String message = "";

        if (userRepository.findByUsername(user.getUsername()) != null) {
            message = "Пользователь с таким именем уже существует";
        }

        return message;
    }

    private UserProfile convertUserToUserProfile (User user) {

        UserProfile userProfile = new UserProfile();

        userProfile.setId(user.getId());
        userProfile.setUsername(user.getUsername());
        userProfile.setFirstName(user.getFirstName());
        userProfile.setLastName(user.getLastName());
        userProfile.setMiddleName(user.getMiddleName());
        userProfile.setEmail(user.getEmail());
        userProfile.setPassword(user.getPassword());
        userProfile.setBalance(user.getBalance());
        userProfile.setRoles(user.getRoles());

        return userProfile;
    }
}