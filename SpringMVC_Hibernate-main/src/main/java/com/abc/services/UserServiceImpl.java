package com.abc.services;

import com.abc.dao.UserDAO;
import com.abc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Autowired
    private UserDAO userDAO;

    @Override
    public User getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    @Override
    public boolean registerUser(User user) throws Exception {
        validateUser(user);
        return userDAO.registerUser(user);
    }

    @Override
    public void updateUser(User user) throws Exception {
        validateUser(user);
        userDAO.updateUser(user);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return userDAO.isEmailTaken(email);
    }

    private void validateUser(User user) throws Exception {
        // Kiểm tra email
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new Exception("Email không hợp lệ");
        }
        if (isEmailTaken(user.getEmail()) && (user.getId() == 0 || !getUserByUserName(user.getUsername()).getEmail().equals(user.getEmail()))) {
            throw new Exception("Email đã tồn tại");
        }

        // Kiểm tra tuổi
        if (user.getBirthday() != null) {
            LocalDate dob = user.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now();
            if (Period.between(dob, now).getYears() < 15) {
                throw new Exception("Người dùng phải từ 15 tuổi trở lên");
            }
        } else {
            throw new Exception("Ngày sinh là bắt buộc");
        }

        // Kiểm tra tỉnh/thành
        if (user.getProvince() == null) {
            throw new Exception("Tỉnh/thành là bắt buộc");
        }
    }

    @Override
    public List<User> searchUsersByFollowCounts(int minFollowing, int minFollower) {
        return userDAO.searchUsersByFollowCounts(minFollowing, minFollower);
    }
}