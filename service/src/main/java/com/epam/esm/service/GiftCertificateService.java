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
 * Presents access to service operations with {@code GiftCertificate}.
 */
@Service
public class GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateService(GiftCertificateDAO giftCertificateDAO, GiftCertificateMapper giftCertificateMapper, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagDAO = tagDAO;
    }


    /**
     * Finds {@code giftCertificate} with requested list of {@code tag} and requested param optionally sorted by name or date.
     *
     * @param sortParamString     String - sorting by giftCertificate's name / date
     * @param sortDirectionString String - sorting in ascending / descending order
     * @param key           key word in giftCertificate's name or description
     * @param tags          list of tag's names as search criteria
     * @return giftCertificates lists        giftCertificates that fits criteria
     * @throws NotFoundException in case of no result in database
     */
    public Page<GiftCertificateDTO> getByParam(int page, int size, String sortParamString, String sortDirectionString, String key, Set<String> tags) throws NotFoundException {
        var sortParamEnum = SortParam.convertString(sortParamString);
        var sortDirectionEnum = SortDirection.convertString(sortDirectionString);
        var filter = new GiftCertificateFilter(sortParamEnum, sortDirectionEnum, key, tags);
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findByParam(page, size, filter);
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificates) {
            giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate));
        }
        return new Page(page, size, giftCertificateDTOs.size(), giftCertificateDTOs);
    }

    /**
     * Finds {@code giftCertificate} of given id value.
     *
     * @param id int id value
     * @return gift certificate             gift certificate of given id value
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException     in case of no result in database
     */
    public GiftCertificateDTO getById(int id) throws InvalidInputException, NotFoundException {
        if (id <= 0) {
            throw new InvalidInputException();
        }
        var giftCertificate = giftCertificateDAO.findById(id);
        return giftCertificateMapper.toDTO(giftCertificate);
    }

    /**
     * Finds all {@code giftCertificate}.
     *
     * @return lists of giftCertificates    all giftCertificates
     * @throws NotFoundException in case of no result in database
     */
    public Page<GiftCertificateDTO> getAll(int index, int size) throws NotFoundException {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findAll(index, size);
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        return new Page<>(index, size, giftCertificateDTOs.size(), giftCertificateDTOs);
    }

    /**
     * Creates new {@code giftCertificate} entity.
     *
     * @param giftCertificateDTO instance to be inserted into database
     * @return giftCertificate              instance with specified id value that has been inserted into database
     * @throws InvalidInputException in case of invalid input
     */
    @Transactional
    public GiftCertificateDTO addGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException, NotFoundException {
        if (giftCertificateDTO.getName() == null || giftCertificateDTO.getName().trim().isEmpty()
                || giftCertificateDTO.getDescription() == null || giftCertificateDTO.getDescription().trim().isEmpty()
                || giftCertificateDTO.getPrice().intValue() <= 0 || giftCertificateDTO.getDuration() <= 0) {
            throw new InvalidInputException();
        }
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
     * Updates {@code giftCertificate} contained in database.
     *
     * @param giftCertificateDTO instance to be updated in database
     * @throws InvalidInputException in case of negative price or duration input
     * @throws NotFoundException     in case of giftCertificate to be updated is not present in database
     */
    @Transactional
    public void updateGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException, NotFoundException {
        var updatedGiftCertificate = giftCertificateDAO.findById(giftCertificateDTO.getId());
        if (updatedGiftCertificate == null) {
            throw new NotFoundException();
        }
        if (giftCertificateDTO.getName() != null || !giftCertificateDTO.getName().trim().isEmpty()) {
            updatedGiftCertificate.setName(giftCertificateDTO.getName());
        }
        if (giftCertificateDTO.getDescription() != null || !giftCertificateDTO.getDescription().isEmpty()) {
            updatedGiftCertificate.setDescription(giftCertificateDTO.getDescription());
        }
        if (giftCertificateDTO.getPrice() != null) {
            if (giftCertificateDTO.getPrice().intValue() > 0) {
                updatedGiftCertificate.setPrice(giftCertificateDTO.getPrice());
            } else {
                throw new InvalidInputException();
            }
        }
        if (giftCertificateDTO.getDuration() != null) {
            if (giftCertificateDTO.getDuration() > 0) {
                updatedGiftCertificate.setDuration(giftCertificateDTO.getDuration());
            } else {
                throw new InvalidInputException();
            }
        }
        if (giftCertificateDTO.getTags() != null) {
            Set<TagDTO> tagsDTO = giftCertificateDTO.getTags();
            Set<Tag> tags = giftCertificateMapper.tagsToModel(tagsDTO);
            Set<Tag> tagsWithId = getTagsWithId(tags);
            updatedGiftCertificate.setTags(tagsWithId);
        }
        updatedGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificateDAO.updateGiftCertificate(updatedGiftCertificate);
    }

    /**
     * Deletes {@code giftCertificate} of given id value.
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

    private boolean checkIfTagExistInDatabase(Tag tag) throws NotFoundException {
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
}
