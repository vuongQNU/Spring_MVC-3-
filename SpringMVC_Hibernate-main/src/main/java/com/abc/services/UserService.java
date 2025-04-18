package com.abc.services;

import com.abc.entities.User;
import java.util.List;

public interface UserService {
    User getUserByUserName(String username);
    boolean registerUser(User user) throws Exception;
    void updateUser(User user) throws Exception;
    List<User> searchUsersByFollowCounts(int minFollowing, int minFollower);
    boolean isEmailTaken(String email);
}