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
 * Mapper class to transform {@link GiftCertificate} and {@link GiftCertificateDTO} types.
 */
@Component
public class GiftCertificateMapper {

    private final TagMapper tagMapper;

    @Autowired
    public GiftCertificateMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    /**
     * Transform {@link GiftCertificateDTO} object to {@link GiftCertificate} type.
     *
     * @param giftCertificateDTO object to transform into {@link GiftCertificate}
     * @return giftCertificate          transformed {@link GiftCertificate}
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
     * Transform {@link GiftCertificate} object to {@link GiftCertificateDTO} type.
     *
     * @param giftCertificate object to transform into {@link GiftCertificateDTO}
     * @return giftCertificateDTO   transformed {@link GiftCertificateDTO}
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
     * Transform set of {@link Tag} objects to set of {@link TagDTO} type.
     *
     * @param modelTags objects to transform into {@link TagDTO}
     * @return tagsDTO   transformed set of {@link TagDTO}
     */
    private Set<TagDTO> tagsToDTO(Set<Tag> modelTags) {
        Set<TagDTO> tagsDTO = new HashSet<>();
        for (var tag : modelTags) {
            tagsDTO.add(tagMapper.toDTO(tag));
        }
        return tagsDTO;
    }

    /**
     * Transform set of {@link TagDTO} objects to set of {@link Tag} type.
     *
     * @param dtoTags objects to transform into {@link Tag}
     * @return tags   transformed set of {@link Tag}
     */
    public Set<Tag> tagsToModel(Set<TagDTO> dtoTags) {
        Set<Tag> tagsModel = new HashSet<>();
        for (var tag : dtoTags) {
            tagsModel.add(tagMapper.toModel(tag));
        }
        return tagsModel;
    }
}
