package com.epam.esm.service;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Presents access to service operations with {@link Tag}.
 */
@Service
public class TagService {

    private final TagDAO tagDAO;
    private final TagMapper tagMapper;
    private static final String INVALID_NAME = "Given tag's name is invalid.";
    private static final String INVALID_ID = "Given tag's id is invalid.";

    @Autowired
    public TagService(TagDAO tagDAO, TagMapper tagMapper) {
        this.tagDAO = tagDAO;
        this.tagMapper = tagMapper;
    }

    /**
     * Finds all {@link Tag}.
     *
     * @return tags    list of all tags on given page
     */
    public Page<TagDTO> getAll(int page, int size) {
        List<Tag> tags = tagDAO.findAll(page, size);
        List<TagDTO> tagDTOs = new ArrayList<>();
        tags.forEach(tag -> tagDTOs.add(tagMapper.toDTO(tag)));
        return new Page<>(page, size, tagDAO.findAll().size(), tagDTOs);
    }

    /**
     * Finds {@link Tag} of given id value.
     *
     * @param id int id value
     * @return tag                   tag of given id value
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException     in case of no result in database
     */
    public TagDTO getById(Integer id) throws InvalidInputException, NotFoundException {
        validateId(id);
        var tag = tagDAO.findById(id);
        return tagMapper.toDTO(tag);
    }

    /**
     * Finds {@link Tag} of given id value.
     *
     * @param name requested String tag's name
     * @return tag of given name
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException     in case of no result in database
     */
    public TagDTO getByName(String name) throws NotFoundException, InvalidInputException {
        validateName(name);
        var tag = tagDAO.getByName(name);
        return tagMapper.toDTO(tag);
    }

    /**
     * Finds {@link Tag} by given id or/and name.
     *
     * @param id            requested Integer id
     * @param name          requested String name
     * @return tags         list of all tags that fits requested criteria
     */
    public Page<TagDTO> getTagsByParam(int page, int size, Integer id, String name) throws InvalidInputException {
        validateInput(id, name);
        List<Tag> tags = tagDAO.getTagsByParam(page, size, id, name);
        List<TagDTO> tagDTOs = new ArrayList<>();
        tags.forEach(tag -> tagDTOs.add(tagMapper.toDTO(tag)));
        return new Page<>(page, size, tagDAO.countTagsFoundByParam(id, name), tagDTOs);
    }

    /**
     * Creates new {@link Tag} entity.
     *
     * @param tagDTO TagDTO instance to be inserted into database
     * @return tagDTO                      instance with specified id value that has been inserted into database
     * @throws InvalidInputException       in case of null or empty tag name
     * @throws AlreadyExistException       in case tag of this name already exists in database
     */
    public TagDTO create(TagDTO tagDTO) throws InvalidInputException, AlreadyExistException{
        validateName(tagDTO.getName());
        var tag = tagMapper.toModel(tagDTO);
        for (Tag tagInDb : tagDAO.findAll()) {
            if (tagInDb.getName().equals(tag.getName())) {
                throw new AlreadyExistException(tag.getName());
            }
        }
        tag.setId(null);
        var tagInserted = tagDAO.create(tag);
        return tagMapper.toDTO(tagInserted);
    }

    /**
     * Deletes {@link Tag} of given id value.
     *
     * @param id                        int value of tag instance to be removed
     * @throws NotFoundException        in case of tag to be deleted is not present in database
     */
    public void delete(Integer id) throws NotFoundException {
        tagDAO.delete(id);
    }

    private void validateInput(Integer id, String name) throws InvalidInputException {
        validateId(id);
        validateName(name);
    }

    private void validateId(Integer id) throws InvalidInputException {
        if (id != null && id <= 0) {
            throw new InvalidInputException(INVALID_ID);
        }
    }

    private void validateName(String name) throws InvalidInputException {
        if (name != null && name.trim().isEmpty()) {
            throw new InvalidInputException(INVALID_NAME);
        }
    }
}
