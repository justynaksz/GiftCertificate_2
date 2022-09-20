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
 * Presents access to service operations with {@code Tag}.
 */
@Service
public class TagService {

    private final TagDAO tagDAO;
    private final TagMapper tagMapper;

    @Autowired
    public TagService(TagDAO tagDAO, TagMapper tagMapper) {
        this.tagDAO = tagDAO;
        this.tagMapper = tagMapper;
    }

    /**
     * Finds all {@code tag}.
     *
     * @return tags    list of all tags on given page
     */
    public Page<TagDTO> getAll(int page, int size) throws NotFoundException {
        List<Tag> tags = tagDAO.findAll(page, size);
        List<TagDTO> tagDTOs = new ArrayList<>();
        tags.forEach(tag -> tagDTOs.add(tagMapper.toDTO(tag)));
        return new Page<>(page, size, tagDTOs.size(), tagDTOs);
    }

    /**
     * Finds {@code tag} of given id value.
     *
     * @param id int id value
     * @return tag                   tag of given id value
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException     in case of no result in database
     */
    public TagDTO getById(Integer id) throws InvalidInputException, NotFoundException {
        if (id <= 0) {
            throw new InvalidInputException();
        }
        var tag = tagDAO.findById(id);
        return tagMapper.toDTO(tag);
    }

    /**
     * Finds {@code tag} of given id value.
     *
     * @param name requested String tag's name
     * @return tag of given name
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException     in case of no result in database
     */
    public TagDTO getByName(String name) throws NotFoundException, InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException();
        }
        var tag = tagDAO.getByName(name);
        return tagMapper.toDTO(tag);
    }

    /**
     * Finds {@code tag} by given id or/and name.
     *
     * @param id            requested Integer id
     * @param name          requested String name
     * @return tags         list of all tags that fits requested criteria
     */
    public Page<TagDTO> getTagsByParam(int page, int size, Integer id, String name) throws NotFoundException, InvalidInputException {
        if (id != null && id <= 0) {
            throw new InvalidInputException();
        }
        if (name != null && name.trim().isEmpty()) {
            throw new InvalidInputException();
        }
        List<Tag> tags = tagDAO.getTagsByParam(page, size, id, name);
        List<TagDTO> tagDTOs = new ArrayList<>();
        tags.forEach(tag -> tagDTOs.add(tagMapper.toDTO(tag)));
        return new Page<>(page, size, tagDTOs.size(), tagDTOs);
    }

    /**
     * Creates new {@code tag} entity.
     *
     * @param tagDTO TagDTO instance to be inserted into database
     * @return tagDTO                      instance with specified id value that has been inserted into database
     * @throws InvalidInputException       in case of null or empty tag name
     * @throws AlreadyExistException       in case tag of this name already exists in database
     */
    public TagDTO create(TagDTO tagDTO) throws InvalidInputException, AlreadyExistException, NotFoundException {
        if (tagDTO.getName() == null || tagDTO.getName().trim().isEmpty()) {
            throw new InvalidInputException();
        }
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
     * Deletes {@code tag} of given id value.
     *
     * @param id                        int value of tag instance to be removed
     * @throws NotFoundException        in case of tag to be deleted is not present in database
     */
    public void delete(Integer id) throws NotFoundException {
        tagDAO.delete(id);
    }
}
