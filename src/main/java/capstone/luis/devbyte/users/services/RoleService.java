package capstone.luis.devbyte.users.services;

import capstone.luis.devbyte.users.models.Role;

public interface RoleService {
    Role getByName(String name);
    Role saveRole(Role role);
}
