package capstone.luis.devbyte.users.services;

import capstone.luis.devbyte.errors.exceptions.ModelEntityNotFoundException;
import capstone.luis.devbyte.errors.exceptions.RegistrationException;
import capstone.luis.devbyte.users.models.AppUser;
import capstone.luis.devbyte.users.models.Role;
import capstone.luis.devbyte.users.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = appUserRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser getUserById(long userId) {
        try {
            return userRepository.findById(userId).get();
        } catch (NoSuchElementException e) {
            throw new ModelEntityNotFoundException(String.format("User does not exist for passed id: %d", userId), e);
        }
    }

    @Override
    public AppUser getUserByUsername(String username) {
        try {
            AppUser appUser = userRepository.findAppUserByUsername(username);
            if (appUser == null) {
                throw new ModelEntityNotFoundException(String.format("User does not exist for username: %s", username));
            }
            return appUser;
        } catch (NoSuchElementException e) {
            throw new ModelEntityNotFoundException(String.format("User does not exist for passed username: %s", username), e);
        }
    }

    @Override
    public AppUser registerAppUser(AppUser user) {
        //perform some checks to ensure unique
        AppUser usernameExists = userRepository.findAppUserByUsername(user.getUsername());

        if (usernameExists != null){
            throw new RegistrationException("Account with username already exists");
        }

        AppUser emailExists = userRepository.findAppUserByEmail(user.getEmail());

        if (emailExists != null) {
            throw new RegistrationException("Account with email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return updateAppUser(user);
    }

    @Override
    public AppUser updateAppUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteAppUser(AppUser user) {
        userRepository.delete(user);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = getUserByUsername(username);
        Role role = roleService.getByName(roleName);
        user.getRoles().add(role);
        updateAppUser(user);
    }
}
