package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * Mapper class to transform {@code GiftCertificate} and {@code GiftCertificateDTO} types.
 */
@Component
public class GiftCertificateMapper {

    private final TagMapper tagMapper;

    @Autowired
    public GiftCertificateMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    /**
     * Migrate {@code GiftCertificateDTO} object to {@code GiftCertificate} type.
     *
     * @param giftCertificateDTO object to transform into {@code GiftCertificate}
     * @return giftCertificate          transformed {@code GiftCertificate}
     */
    public GiftCertificate toModel(GiftCertificateDTO giftCertificateDTO) {
        var id = 0;
        if (giftCertificateDTO.getId() != null) {
            id = giftCertificateDTO.getId();
        }
        var name = giftCertificateDTO.getName();
        var description = giftCertificateDTO.getDescription();
        var price = giftCertificateDTO.getPrice();
        var duration = giftCertificateDTO.getDuration();
        LocalDateTime createDate = null;
        if (giftCertificateDTO.getCreateDate() != null) {
            createDate = LocalDateTime.parse(giftCertificateDTO.getCreateDate());
        }
        LocalDateTime lastUpdateDate = null;
        if (giftCertificateDTO.getLastUpdateDate() != null) {
            lastUpdateDate = LocalDateTime.parse(giftCertificateDTO.getLastUpdateDate());
        }
        var tags = giftCertificateDTO.getTags();
        var tagsModel = tagsToModel(tags);
        return new GiftCertificate(id, name, description, price, duration, createDate, lastUpdateDate, tagsModel);
    }

    /**
     * Migrate {@code GiftCertificate} object to {@code GiftCertificateDTO} type.
     *
     * @param giftCertificate object to transform into {@code GiftCertificateDTO}
     * @return giftCertificateDTO   transformed {@code GiftCertificateDTO}
     */
    public GiftCertificateDTO toDTO(GiftCertificate giftCertificate) {
        var id = giftCertificate.getId();
        var name = giftCertificate.getName();
        var description = giftCertificate.getDescription();
        var price = BigDecimal.valueOf(giftCertificate.getPrice());
        var duration = giftCertificate.getDuration();
        String createDate = null;
        if (giftCertificate.getCreateDate() != null) {
            createDate = constructISOFormatDate(giftCertificate.getCreateDate());
        }
        String lastUpdateDate = null;
        if (giftCertificate.getLastUpdateDate() != null) {
            lastUpdateDate = constructISOFormatDate(giftCertificate.getLastUpdateDate());
        }
        var tags = giftCertificate.getTags();
        var tagsDTO = tagsToDTO(tags);
        return new GiftCertificateDTO(id, name, description, price, duration, createDate, lastUpdateDate, tagsDTO);
    }

    private String constructISOFormatDate(LocalDateTime dateTime) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return dateTime.format(formatter);
    }

    /**
     * Migrate set of {@code tag} objects to set of {@code tagDTO} type.
     *
     * @param modelTags objects to transform into {@code tagDTO}
     * @return tagsDTO   transformed set of {@code tagDTO}
     */
    private Set<TagDTO> tagsToDTO(Set<Tag> modelTags) {
        Set<TagDTO> tagsDTO = new HashSet<>();
        for (var tag : modelTags) {
            tagsDTO.add(tagMapper.toDTO(tag));
        }
        return tagsDTO;
    }

    /**
     * Migrate set of {@code tagDTO} objects to set of {@code tag} type.
     *
     * @param dtoTags objects to transform into {@code tag}
     * @return tags   transformed set of {@code tag}
     */
    public Set<Tag> tagsToModel(Set<TagDTO> dtoTags) {
        Set<Tag> tagsModel = new HashSet<>();
        for (var tag : dtoTags) {
            tagsModel.add(tagMapper.toModel(tag));
        }
        return tagsModel;
    }
}
