package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

/**
 * DTO class for User.
 */
@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDTO extends RepresentationModel<UserDTO> implements DTO {

    private final Integer id;
    private final String nickname;
    private final String createDate;

    public UserDTO(Integer id, String name, String createDate) {
        this.id = id;
        this.nickname = name;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return nickname;
    }

    public String getCreateDate() {
        return createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(nickname, userDTO.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, nickname);
    }
}
