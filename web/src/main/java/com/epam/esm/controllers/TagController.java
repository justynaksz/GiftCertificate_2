package com.epam.esm.controllers;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring REST controllers for processing requests {@code tag} resource.
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Finds {@code tag}  with requested id.
     * Handles GET http-request.
     *
     * @param id requested id
     * @return TagDTO   of given id
     */
    @GetMapping("/{id}")
    public TagDTO getById(@PathVariable int id) {
        return tagService.getById(id);
    }

    /**
     * Gets all {@code tag}.
     * Handles GET http-request.
     *
     * @return tags   list of all tags in database
     */
    @GetMapping
    public List<TagDTO> getAll() {
        return tagService.getAll();
    }

    /**
     * Finds {@code tag}  with requested name.
     * Handles GET http-request.
     *
     * @param name requested id
     * @return TagDTO    of given name
     */
    @GetMapping("name/{name}")
    public TagDTO getByName(@PathVariable String name) {
        return tagService.getByName(name);
    }

    /**
     * Creates new {@code tag}.
     * Handles POST http-request.
     *
     * @param tagDTOToInsert tag to be inserted into database
     * @return TagDTO        tag that has been inserted into database
     */
    @PostMapping()
    public TagDTO createTag(@RequestBody TagDTO tagDTOToInsert) {
        return tagService.addTag(tagDTOToInsert);
    }

    /**
     * Deletes {@code tag} with requested id.
     * Handles DELETE http-request.
     *
     * @param id requested id
     */
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable int id) {
        tagService.deleteTag(id);
    }
}
