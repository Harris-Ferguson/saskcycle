package com.saskcycle.controller;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.DAO.UserDAOInterface;
import com.saskcycle.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    private static final String USER_ROLE = "USER";
    private static final String ORG_ROLE = "ORG";

    @Autowired
    private UserDAOInterface userDataAccess;

    @Autowired
    private CurrentUserDAOInterface currentUserDataAccess;

    @Autowired
    private PasswordEncoder encoder;


    /**
     * Register a new regular user account to the system. Will be given USER permissions
     *
     * @param username username for the user
     * @param email    email for the user
     * @param password password for the user
     */
    public void register(String username, String email, String password) {
        UserDetails newUser = buildUserDetails(username, password, USER_ROLE);
        register(newUser, email);
    }

    /**
     * Register a new organizational user to the system. Will be given ORG permissions
     *
     * @param username username for the user
     * @param email    email for the user
     * @param password password for the user
     */
    public void registerOrg(String username, String email, String password) {
        UserDetails newUser = buildUserDetails(username, password, USER_ROLE, ORG_ROLE);
        register(newUser, email);
    }

    private UserDetails buildUserDetails(String username, String password, String... roles) {
        String encodedPass = encoder.encode(password);
        return User.withUsername(username).password(encodedPass).roles(roles).build();
    }

    private void register(UserDetails user, String email) {
        Account account = Account.makeAccountFromUser(user, email);
        if (userDataAccess.accountExists(account)) {
            throw new IllegalArgumentException("Account already exists");
        }
        userDataAccess.addAccount(account);
    }

    public Account getCurrentAccount() {
        return currentUserDataAccess.getCurrentAccount();
    }

    public void updateWishlist(String id) {
        currentUserDataAccess.updateWishlist(id);
    }

    public void updateEvents(String id) {
        currentUserDataAccess.updateEvents(id);
    }
}
