package com.epam.esm.mapper;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mapper to transform {@link User} and {@link UserDTO} types.
 */
@Component
public class UserMapper extends Mapper<User, UserDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public User toModel(UserDTO userDTO) {
        var id = 0;
        if (userDTO.getId() != null) {
            id = userDTO.getId();
        }
        var name = userDTO.getName();
        LocalDateTime createDate = null;
        if (userDTO.getCreateDate() != null) {
            createDate = LocalDateTime.parse(userDTO.getCreateDate());
        }
        return new User(id, name, createDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO toDTO(User user) {
        var id = 0;
        if (user.getId() != null) {
            id = user.getId();
        }
        var name = user.getNickname();
        String createDate = null;
        if (user.getCreateDate() != null) {
            createDate = constructISOFormatDate(user.getCreateDate());
        }
        return new UserDTO(id, name, createDate);
    }
}
