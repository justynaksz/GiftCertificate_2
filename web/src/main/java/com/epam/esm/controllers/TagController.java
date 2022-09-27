package com.epam.esm.controllers;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.hateoas.assembler.TagAssembler;
import com.epam.esm.hateoas.pagelinker.TagPageLinker;
import com.epam.esm.model.Tag;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring REST controllers for processing requests {@link Tag} resource.
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final TagAssembler assembler;
    private final TagPageLinker pageLinker;

    @Autowired
    public TagController(TagService tagService, TagAssembler assembler, TagPageLinker pageLinker) {
        this.tagService = tagService;
        this.assembler = assembler;
        this.pageLinker = pageLinker;
    }

    /**
     * Gets all {@link Tag}.
     * Handles GET http-request.
     *
     * @return tags   list of all tags in database
     */
    @GetMapping()
    public CollectionModel<TagDTO> getAllTags(@RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        Page<TagDTO> pageOfResults = tagService.getAll(page, size);
        List<TagDTO> tags = assembler.addLinks(pageOfResults.getContent());
        List<Link> links = new ArrayList<>();
        links.add(assembler.getLinkToCollection());
        pageLinker.addPagesLinksInGetAllMethod(pageOfResults, links);
        return CollectionModel.of(tags, links);
    }

    /**
     * Gets {@link Tag} that fits requested id or/and name.
     * Handles GET http-request.
     *
     * @param id   not required id value
     * @param name not required name value
     * @return tags   list of tags that fits requested criteria
     */
    @GetMapping("param")
    public CollectionModel<TagDTO> getTagsByParam(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "5") int size,
                                                  @RequestParam(required = false) Integer id,
                                                  @RequestParam(required = false) String name)
            throws InvalidInputException {
        Page<TagDTO> pageOfResults = tagService.getTagsByParam(page, size, id, name);
        List<TagDTO> tags = assembler.addLinks(pageOfResults.getContent());
        List<Link> links = new ArrayList<>();
        links.add(assembler.getLinkToCollection());
        pageLinker.addPagesLinksInGetByParamMethod(pageOfResults, links, id, name);
        return CollectionModel.of(tags, links);
    }

    /**
     * Gets {@link Tag} with requested id.
     * Handles GET http-request.
     *
     * @param id required id value
     * @return tag
     */
    @GetMapping("id")
    public TagDTO getTagsById(@RequestParam Integer id) throws InvalidInputException, NotFoundException {
        return assembler.addLink(tagService.getById(id));
    }

    /**
     * Creates new {@link Tag}.
     * Handles POST http-request.
     *
     * @param tagDTOToInsert tag to be inserted into database
     * @return TagDTO        tag that has been inserted into database
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO createTag(@RequestBody TagDTO tagDTOToInsert) throws InvalidInputException, AlreadyExistException {
        return assembler.addLink(tagService.create(tagDTOToInsert));
    }

    /**
     * Deletes {@link Tag} with requested id.
     * Handles DELETE http-request.
     *
     * @param id requested id
     */
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@RequestParam Integer id) throws NotFoundException {
        tagService.delete(id);
    }
}
