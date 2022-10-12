package com.epam.esm.hateoas.assembler;

import com.epam.esm.controllers.UserController;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.User;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Generates links to {@link User}.
 */
@Component
public class UserAssembler {

    /**
     * Adds link to itself.
     *
     * @param userDTO to add link
     * @return userDTO with link to itself
     */
    public UserDTO addLink(UserDTO userDTO) {
        return userDTO.add(linkTo(UserController.class).slash("id/" + userDTO.getId()).withSelfRel());
    }

    /**
     * Adds links to every single {@link User} in the given list.
     *
     * @param userDTOs to add links
     * @return userDTOs with links to every single {@link User}
     */
    public List<UserDTO> addLinks(List<UserDTO> userDTOs) {
        userDTOs.forEach(this::addLink);
        return userDTOs;
    }

    /**
     * Gets link to all {@link User} in database
     *
     * @return link to all {@link User} in database
     */
    public Link getLinkToCollection() {
        return linkTo(UserController.class).withSelfRel();
    }
}
