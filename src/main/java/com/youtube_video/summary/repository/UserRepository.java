package com.youtube_video.summary.repository;

import com.youtube_video.summary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    public int findOrCreateUserByEmail(String email) {
        // Try to find user
        var users = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", userRowMapper, email);
        if (!users.isEmpty()) {
            return users.get(0).getId();
        }

        // Insert new user
        jdbcTemplate.update("INSERT INTO users (email) VALUES (?)", email);
        return jdbcTemplate.queryForObject("SELECT id FROM users WHERE email = ?", Integer.class, email);
    }
}
