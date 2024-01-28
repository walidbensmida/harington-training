package org.example.chapitre1.repository.user.criteria;

import org.example.chapitre1.entity.RoleEnum;
import org.example.chapitre1.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        user.setRole(RoleEnum.valueOf(rs.getString("role")));
        user.setPassword(rs.getString("password"));
        user.setAccount(null);
        return user;
    }
}
