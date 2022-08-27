package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Presents access to service operations with {@code GiftCertificate}.
 */
@Service
public class GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagDAO tagDAO;
    private final GiftCertificateTagDAO giftCertificateTagDAO;

    @Autowired
    public GiftCertificateService(GiftCertificateDAO giftCertificateDAO, GiftCertificateMapper giftCertificateMapper,
                                  TagDAO tagDAO, GiftCertificateTagDAO giftCertificateTagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagDAO = tagDAO;
        this.giftCertificateTagDAO = giftCertificateTagDAO;
    }

    /**
     * Finds {@code giftCertificate} of given id value.
     * @param id                            int id value
     * @return gift certificate             gift certificate of given id value
     * @throws InvalidInputException     in case of invalid param
     */
    public GiftCertificateDTO getById(int id) throws InvalidInputException {
        if (id <= 0) {
            throw new InvalidInputException("Id value must be greater than 0.");
        }
        GiftCertificate giftCertificate = giftCertificateDAO.findById(id);
        GiftCertificateDTO giftCertificateDTO = giftCertificateMapper.toDTO(giftCertificate);
        giftCertificateDTO.setTags(getTagsList(giftCertificateDTO.getId()));
        return giftCertificateDTO;
    }

    /**
     * Finds {@code giftCertificate} assigned to given tagName value.
     * @param tagName               String value of tag's name
     * @return giftCertificates     list of giftCertificates assigned to given tag's name
     */
    public List<GiftCertificateDTO> getByTag(String tagName){
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findByTag(tagName);
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        giftCertificateDTOs.forEach(giftCertificateDTO -> giftCertificateDTO.setTags(getTagsList(giftCertificateDTO.getId())));
        return giftCertificateDTOs;
    }

    /**
     * Finds all {@code giftCertificate} by part of name or description.
     * @param key                  String value of desired name/description word
     * @return giftCertificates    list of giftCertificates containing key word in their name or description
     */
    public List<GiftCertificateDTO> getByNameOrdDescription(String key) {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findByNameOrDescription(key);
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        giftCertificateDTOs.forEach(giftCertificateDTO -> giftCertificateDTO.setTags(getTagsList(giftCertificateDTO.getId())));
        return giftCertificateDTOs;
    }

    /**
     * Finds all {@code giftCertificate}.
     * @return lists of giftCertificates    all giftCertificates
     */
    public List<GiftCertificateDTO> getAll() {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.findAll();
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        giftCertificateDTOs.forEach(giftCertificateDTO -> giftCertificateDTO.setTags(getTagsList(giftCertificateDTO.getId())));
        return giftCertificateDTOs;
    }

    /**
     * Sorts all {@code giftCertificate} by ascending order.
     * @return giftCertificates in ascending order
     */
    public List<GiftCertificateDTO> sortAscending() {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.sortAscending();
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        giftCertificateDTOs.forEach(giftCertificateDTO -> giftCertificateDTO.setTags(getTagsList(giftCertificateDTO.getId())));
        return giftCertificateDTOs;
    }

    /**
     * Sorts all {@code giftCertificate} by descending order.
     * @return giftCertificates in descending order
     */
    public List<GiftCertificateDTO> sortDescending() {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.sortDescending();
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        giftCertificateDTOs.forEach(giftCertificateDTO -> giftCertificateDTO.setTags(getTagsList(giftCertificateDTO.getId())));
        return giftCertificateDTOs;
    }

    /**
     * Sorts all {@code giftCertificate} by ascending order by date.
     * @return giftCertificates in ascending order by date
     */
    public List<GiftCertificateDTO> sortAscendingByDate() {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.sortAscendingByDate();
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        giftCertificateDTOs.forEach(giftCertificateDTO -> giftCertificateDTO.setTags(getTagsList(giftCertificateDTO.getId())));
        return giftCertificateDTOs;
    }

    /**
     * Sorts all {@code giftCertificate} by descending order by date.
     * @return giftCertificates in descending order by date
     */
    public List<GiftCertificateDTO> sortDescendingByDate() {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.sortDescendingByDate();
        List<GiftCertificateDTO> giftCertificateDTOs = new ArrayList<>();
        giftCertificates.forEach(giftCertificate -> giftCertificateDTOs.add(giftCertificateMapper.toDTO(giftCertificate)));
        giftCertificateDTOs.forEach(giftCertificateDTO -> giftCertificateDTO.setTags(getTagsList(giftCertificateDTO.getId())));
        return giftCertificateDTOs;
    }

    /**
     * Creates new {@code giftCertificate} entity.
     * @param giftCertificateDTO            GiftCertificate instance to be inserted into database
     * @return giftCertificate              GiftCertificate instance with specified id value that has been inserted into database
     * @throws InvalidInputException     in case of invalid param
     */
    @Transactional
    public GiftCertificateDTO addGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws InvalidInputException {
        if (giftCertificateDTO.getName() == null || giftCertificateDTO.getName().trim().isEmpty()
        || giftCertificateDTO.getDescription() == null || giftCertificateDTO.getDescription().trim().isEmpty()
        || giftCertificateDTO.getPrice() == 0 || giftCertificateDTO.getDuration() == 0) {
            throw new InvalidInputException("At least one of given parameter is null or empty.");
        }
        GiftCertificate giftCertificate = giftCertificateMapper.toModel(giftCertificateDTO);
        GiftCertificate giftCertificateInserted = giftCertificateDAO.createGiftCertificate(giftCertificate);
        connectTagToGiftCertificate(giftCertificateDTO.getTags(), giftCertificateInserted.getId());
        List<Tag> tags = getTagsList(giftCertificateInserted.getId());
        GiftCertificateDTO giftCertificateDTOInserted = giftCertificateMapper.toDTO(giftCertificateInserted);
        giftCertificateDTOInserted.setTags(tags);
        return giftCertificateDTOInserted;
    }

    /**
     * Updates {@code giftCertificate} contained in database.
     * @param giftCertificateDTO    GiftCertificate instance to be updated in database
     */
    @Transactional
    public void updateGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        if (giftCertificateDTO.getName() == null || giftCertificateDTO.getName().trim().isEmpty()
                || giftCertificateDTO.getDescription() == null || giftCertificateDTO.getDescription().trim().isEmpty()
                || giftCertificateDTO.getPrice() == 0 || giftCertificateDTO.getDuration() == 0) {
            throw new InvalidInputException("At least one of given parameter is null or empty.");
        }
        GiftCertificate giftCertificate = giftCertificateMapper.toModel(giftCertificateDTO);
        List<Tag> initialTags = getTagsList(giftCertificateDTO.getId());
        giftCertificateDAO.updateGiftCertificate(giftCertificate);
        updateTagsForGiftCertificate(initialTags, getUpdateTagListWithCorrectId(giftCertificateDTO.getTags()), giftCertificateDTO.getId());
    }

    /**
     * Deletes {@code giftCertificate} of given id value.
     * @param id    int id value of giftCertificate instance to be removed
     */
    public void deleteGiftCertificate(int id) {
        disconnectTagListFromGiftCertificate(getTagsList(id), id);
        giftCertificateDAO.deleteGiftCertificate(id);
    }

    private List<Tag> getTagsList(int giftCertificateId) {
        return tagDAO.findTagsByGiftCertificateId(giftCertificateId);
    }

    private List<Tag> getUpdateTagListWithCorrectId(List<Tag> updateTagList) {
        List<Tag> updateTagListWithCorrectId = new ArrayList<>();
        for (Tag tag : updateTagList) {
            if (checkIfTagExistInDatabase(tag)) {
                updateTagListWithCorrectId.add(tagDAO.findByName(tag.getName()));
            }
        }
        return updateTagListWithCorrectId;
    }

    private void connectTagToGiftCertificate(List<Tag> tags, int giftCertificateId) {
        for (Tag tag : tags) {
            if (checkIfTagExistInDatabase(tag)) {
                Tag tagInDatabase = tagDAO.findByName(tag.getName());
                GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
                giftCertificateTag.setTagId(tagInDatabase.getId());
                giftCertificateTag.setGiftCertificateId(giftCertificateId);
                giftCertificateTagDAO.createGiftCertificateTag(giftCertificateTag);
            } else {
                Tag tagInserted = tagDAO.createTag(tag);
                GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
                giftCertificateTag.setTagId(tagInserted.getId());
                giftCertificateTag.setGiftCertificateId(giftCertificateId);
                giftCertificateTagDAO.createGiftCertificateTag(giftCertificateTag);
            }
        }
    }

    private void updateTagsForGiftCertificate(List<Tag> initialTags, List<Tag> updatedTags, int giftCertificateId) {
        List<Tag> tagsToConnectToCertificate = updatedTags.stream()
                .filter(tag -> !initialTags.contains(tag))
                .collect(Collectors.toList());

        connectTagToGiftCertificate(tagsToConnectToCertificate, giftCertificateId);

        List<Tag> actualTags = getTagsList(giftCertificateId);

        List<Tag> tagsToDisconnectFromCertificate = actualTags.stream()
                .filter(tag -> !updatedTags.contains(tag))
                .collect(Collectors.toList());

        disconnectTagListFromGiftCertificate(tagsToDisconnectFromCertificate, giftCertificateId);
    }

    private void disconnectTagListFromGiftCertificate(List<Tag> tags, int giftCertificateId) {
        for (Tag tag : tags) {
            Tag tagInDatabase = tagDAO.findByName(tag.getName());
            GiftCertificateTag giftCertificateTagToBeDeleted = giftCertificateTagDAO.findGiftCertificateTagByIds(giftCertificateId, tagInDatabase.getId());
            giftCertificateTagDAO.deleteGiftCertificateTag(giftCertificateTagToBeDeleted.getId());
        }
    }

    private boolean checkIfTagExistInDatabase(Tag tag) {
        boolean isTagInDatabase = false;
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
