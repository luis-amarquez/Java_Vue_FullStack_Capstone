package capstone.luis.devbyte.users.services;

import capstone.luis.devbyte.users.models.AppUser;

public interface AppUserService {
    AppUser getUserById(long userId);
    AppUser getUserByUsername(String username);
    AppUser registerAppUser(AppUser user);
    AppUser updateAppUser(AppUser user);
    // delete account but only allow the user of the account
    void deleteAppUser(AppUser user);
    void addRoleToUser(String username, String roleName);
}
