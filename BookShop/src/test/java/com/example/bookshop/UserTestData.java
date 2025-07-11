package com.example.bookshop;

import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;

import java.util.List;

public class UserTestData {
    public static final int ADMIN_ID = 1;
    public static final int USER_ID = 101;
    public static final User admin = new User(ADMIN_ID, new Role(null, "ADMIN"), "Admin", "admin@gmail.com", "123");
    public static final User user = new User(USER_ID, new Role(null, "USER"), "User1", "chel1@gmail.com", "123");
    public static final User invalidUser = new User(USER_ID, new Role(null, "USER"), "User", "user@gmail.com", "user");
}
