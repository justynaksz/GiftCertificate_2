package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.filter.GiftCertificateFilter;
import com.epam.esm.filter.SortDirection;
import com.epam.esm.filter.SortParam;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Presents access to service operations with {@link GiftCertificate}.
 */
@Service
public class GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagDAO tagDAO;
    private static final String INVALID_INPUT_MESSAGE = "At least one of given parameters is invalid";

    @Autowired
    public GiftCertificateService(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO, GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    /**
     * Finds {@link GiftCertificate} with requested list of {@link Tag} and requested param optionally sorted by name or date.
     *
     * @param sortParamString     String - sorting by giftCertificate's name / date
     * @param sortDirectionString String - sorting in ascending / descending order
     * @param key           key word in giftCertificate's name or description
     * @param tags          list of tag's names as search criteria
     * @return giftCertificates lists        giftCertificates that fits criteria
     */
    public Page<GiftCertificateDTO> getByParam(int page, int size, String sortParamString, String sortDirectionString, String key, Set<String> tags) {
        var sortParamEnum = SortParam.convertString(sortParamString);
        var sortDirectionEnum = SortDirection.convertString(sortDirectionString);
        var filter = new GiftCertificateFilter(sortParamEnum, sortDirectionEnum, key, tags);
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findByParam(page, size, filter);
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificates) {
            giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate));
        }
        return new Page<>(page, size, giftCertificateDAO.countCertificatesFoundByParam(filter), giftCertificateDTOs);
    }

    /**
     * Finds {@link GiftCertificate} of given id value.
     *
     * @param id int id value
     * @return gift certificate             gift certificate of given id value
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException     in case of no result in database
     */
    public GiftCertificateDTO getById(int id) throws InvalidInputException, NotFoundException {
        if (id <= 0) {
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
        }
        var giftCertificate = giftCertificateDAO.findById(id);
        return giftCertificateMapper.toDTO(giftCertificate);
    }

    /**
     * Finds all {@link GiftCertificate}.
     *
     * @return lists of giftCertificates    all giftCertificates
     */
    public Page<GiftCertificateDTO> getAll(int index, int size){
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findAll(index, size);
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        return new Page<>(index, size, giftCertificateDAO.findAll().size(), giftCertificateDTOs);
    }

    /**
     * Creates new {@link GiftCertificate} entity.
     *
     * @param giftCertificateDTO instance to be inserted into database
     * @return giftCertificate              instance with specified id value that has been inserted into database
     * @throws InvalidInputException in case of invalid input
     */
    @Transactional
    public GiftCertificateDTO addGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException, NotFoundException {
        validateInputData(giftCertificateDTO);
        var giftCertificate = giftCertificateMapper.toModel(giftCertificateDTO);
        giftCertificate.setCreateDate(LocalDateTime.now());
        Set<Tag> tags = giftCertificate.getTags();
        Set<Tag> tagsWithId = getTagsWithId(tags);
        giftCertificate.setTags(tagsWithId);
        giftCertificate.setId(null);
        var giftCertificateInserted = giftCertificateDAO.create(giftCertificate);
        return giftCertificateMapper.toDTO(giftCertificateInserted);
    }

    /**
     * Updates {@link GiftCertificate} contained in database.
     *
     * @param giftCertificateDTO instance to be updated in database
     * @return updated giftCertificate
     * @throws InvalidInputException in case of negative price or duration input
     * @throws NotFoundException     in case of giftCertificate to be updated is not present in database
     */
    @Transactional
    public GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException, NotFoundException {
        var updatedGiftCertificate = giftCertificateDAO.findById(giftCertificateDTO.getId());
        if (updatedGiftCertificate == null) {
            throw new NotFoundException("Gift certificate of requested id = " + giftCertificateDTO.getId() + " not found.");
        }
        prepareGiftCertificateToUpdate(updatedGiftCertificate, giftCertificateDTO);
        updatedGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        return giftCertificateMapper.toDTO(giftCertificateDAO.updateGiftCertificate(updatedGiftCertificate));
    }

    /**
     * Deletes {@link GiftCertificate} of given id value.
     *
     * @param id int id value of giftCertificate instance to be removed
     * @throws NotFoundException in case of giftCertificate to be deleted is not present in database
     */
    public void deleteGiftCertificate(Integer id) throws NotFoundException {
        giftCertificateDAO.delete(id);
    }

    private Set<Tag> getTagsWithId(Set<Tag> tags) throws NotFoundException {
        Set<Tag> tagsWithId = new HashSet<>();
        for (Tag tag : tags) {
            Tag tagWithId;
            if (checkIfTagExistInDatabase(tag)) {
                tagWithId = tagDAO.getByName(tag.getName());
            } else {
                tag.setId(null);
                tagWithId = tagDAO.create(tag);
            }
            tagsWithId.add(tagWithId);
        }
        return tagsWithId;
    }

    private boolean checkIfTagExistInDatabase(Tag tag) {
        var isTagInDatabase = false;
        List<Tag> tagsInDatabase = tagDAO.findAll();
        for (Tag tagInDatabase : tagsInDatabase) {
            if (tagInDatabase.getName().equals(tag.getName())) {
                isTagInDatabase = true;
                break;
            }
        }
        return isTagInDatabase;
    }

    private void validateInputData(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException {
        validateName(giftCertificateDTO);
        validateDescription(giftCertificateDTO);
        validatePriceAndDuration(giftCertificateDTO);
    }

    private void validateName(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException {
        if (giftCertificateDTO.getName() == null || giftCertificateDTO.getName().trim().isEmpty()) {
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
        }
    }

    private void validateDescription(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException {
        if (giftCertificateDTO.getDescription() == null || giftCertificateDTO.getDescription().trim().isEmpty()) {
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
        }
    }

    private void validatePriceAndDuration(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException {
        if (giftCertificateDTO.getPrice().intValue() <= 0 || giftCertificateDTO.getDuration() <= 0) {
            throw new InvalidInputException(INVALID_INPUT_MESSAGE);
        }
    }

    private void prepareGiftCertificateToUpdate(GiftCertificate updatedGiftCertificate, GiftCertificateDTO giftCertificateDTO) throws InvalidInputException, NotFoundException {
        prepareNameToUpdate(updatedGiftCertificate, giftCertificateDTO);
        prepareDescriptionToUpdate(updatedGiftCertificate, giftCertificateDTO);
        preparePriceToUpdate(updatedGiftCertificate, giftCertificateDTO);
        prepareDurationToUpdate(updatedGiftCertificate, giftCertificateDTO);
        prepareTagsToUpdate(updatedGiftCertificate, giftCertificateDTO);
    }

    private void prepareNameToUpdate(GiftCertificate updatedGiftCertificate, GiftCertificateDTO giftCertificateDTO) {
        if (giftCertificateDTO.getName() != null || !giftCertificateDTO.getName().trim().isEmpty()) {
            updatedGiftCertificate.setName(giftCertificateDTO.getName());
        }
    }

    private void prepareDescriptionToUpdate(GiftCertificate updatedGiftCertificate, GiftCertificateDTO giftCertificateDTO) {
        if (giftCertificateDTO.getDescription() != null || !giftCertificateDTO.getDescription().isEmpty()) {
            updatedGiftCertificate.setDescription(giftCertificateDTO.getDescription());
        }
    }

    private void preparePriceToUpdate(GiftCertificate updatedGiftCertificate, GiftCertificateDTO giftCertificateDTO) throws InvalidInputException {
        if (giftCertificateDTO.getPrice() != null) {
            if (giftCertificateDTO.getPrice().intValue() > 0) {
                updatedGiftCertificate.setPrice(giftCertificateDTO.getPrice());
            } else {
                throw new InvalidInputException(INVALID_INPUT_MESSAGE);
            }
        }
    }

    private void prepareDurationToUpdate(GiftCertificate updatedGiftCertificate, GiftCertificateDTO giftCertificateDTO) throws InvalidInputException {
        if (giftCertificateDTO.getDuration() != null) {
            if (giftCertificateDTO.getDuration() > 0) {
                updatedGiftCertificate.setDuration(giftCertificateDTO.getDuration());
            } else {
                throw new InvalidInputException(INVALID_INPUT_MESSAGE);
            }
        }
    }

    private void prepareTagsToUpdate(GiftCertificate updatedGiftCertificate, GiftCertificateDTO giftCertificateDTO) throws NotFoundException {
        if (giftCertificateDTO.getTags() != null) {
            Set<TagDTO> tagsDTO = giftCertificateDTO.getTags();
            Set<Tag> tags = giftCertificateMapper.tagsToModel(tagsDTO);
            Set<Tag> tagsWithId = getTagsWithId(tags);
            updatedGiftCertificate.setTags(tagsWithId);
        }
    }
}
