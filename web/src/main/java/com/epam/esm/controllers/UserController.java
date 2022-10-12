package com.epam.esm.controllers;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.hateoas.assembler.UserAssembler;
import com.epam.esm.hateoas.pagelinker.UserPageLinker;
import com.epam.esm.model.User;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring REST controllers for processing requests {@link User} resource.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserAssembler assembler;
    private final UserPageLinker pageLinker;

    @Autowired
    public UserController(UserService userService, UserAssembler assembler, UserPageLinker pageLinker) {
        this.userService = userService;
        this.assembler = assembler;
        this.pageLinker = pageLinker;
    }

    /**
     * Gets {@link User} with requested id.
     * Handles GET http-request.
     *
     * @param id required id value
     * @return user
     */
    @GetMapping("id/{id}")
    public UserDTO getById(@PathVariable Integer id) throws InvalidInputException, NotFoundException {
        return assembler.addLink(userService.getById(id));
    }

    /**
     * Gets all {@link User}.
     * Handles GET http-request.
     *
     * @return users   list of all users in database
     */
    @GetMapping()
    public CollectionModel<UserDTO> getAll(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        Page<UserDTO> pageOfResults = userService.getAll(page, size);
        List<UserDTO> users = assembler.addLinks(pageOfResults.getContent());
        List<Link> links = new ArrayList<>();
        links.add(assembler.getLinkToCollection());
        pageLinker.addPagesLinksInGetAllMethod(pageOfResults, links);
        return CollectionModel.of(users, links);
    }

    /**
     * Gets {@link User} with requested nickname.
     * Handles GET http-request.
     *
     * @param nickname required nickname value
     * @return user
     */
    @GetMapping("name")
    public UserDTO getByName(@RequestParam String nickname) throws InvalidInputException, NotFoundException {
        return assembler.addLink(userService.getByName(nickname));
    }
}
