package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.model.GiftCertificate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Mapper class to transform {@code GiftCertificate} and {@code GiftCertificateDTO} types.
 */
@Component
public class GiftCertificateMapper {

    /**
     * Migrate {@code GiftCertificateDTO} object to {@code GiftCertificate} type.
     * @param giftCertificateDTO        object to transform into {@code GiftCertificate}
     * @return giftCertificate          transformed {@code GiftCertificate}
     */
    public GiftCertificate toModel(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(giftCertificateDTO.getId());
        giftCertificate.setName(giftCertificateDTO.getName());
        giftCertificate.setDescription(giftCertificateDTO.getDescription());
        giftCertificate.setPrice(giftCertificateDTO.getPrice());
        giftCertificate.setDuration(giftCertificateDTO.getDuration());
        if (giftCertificateDTO.getCreateDate() != null) {
            giftCertificate.setCreateDate(LocalDateTime.parse(giftCertificateDTO.getCreateDate()));
        }
        if (giftCertificateDTO.getLastUpdateDate() != null) {
            giftCertificate.setLastUpdateDate(LocalDateTime.parse(giftCertificateDTO.getLastUpdateDate()));
        }
        return giftCertificate;
    }

    /**
     * Migrate {@code GiftCertificate} object to {@code GiftCertificateDTO} type.
     * @param giftCertificate       object to transform into {@code GiftCertificateDTO}
     * @return giftCertificateDTO   transformed {@code GiftCertificateDTO}
     */
    public GiftCertificateDTO toDTO (GiftCertificate giftCertificate) {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(giftCertificate.getId());
        giftCertificateDTO.setName(giftCertificate.getName());
        giftCertificateDTO.setDescription(giftCertificate.getDescription());
        giftCertificateDTO.setPrice(giftCertificate.getPrice());
        giftCertificateDTO.setDuration(giftCertificate.getDuration());
        if (giftCertificate.getCreateDate() != null){
            giftCertificateDTO.setCreateDate(constructISOFormatDate(giftCertificate.getCreateDate()));
        }
        if (giftCertificate.getLastUpdateDate() != null) {
            giftCertificateDTO.setLastUpdateDate(constructISOFormatDate(giftCertificate.getLastUpdateDate()));
        }
        giftCertificateDTO.setTags(new ArrayList<>());
        return giftCertificateDTO;
    }


    private String constructISOFormatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return dateTime.format(formatter);
    }
}
