package com.epam.esm.hateoas.assembler;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Generates links to {@link GiftCertificate}.
 */
@Component
public class GiftCertificateAssembler {

    private final TagAssembler tagAssembler;

    @Autowired
    public GiftCertificateAssembler(TagAssembler tagAssembler) {
        this.tagAssembler = tagAssembler;
    }

    /**
     * Adds link to itself and to every single tag associated with given giftCertificate.
     *
     * @param giftCertificateDTO to add links
     * @return giftCertificateDTO with links
     */
    public GiftCertificateDTO addLink(GiftCertificateDTO giftCertificateDTO) {
        tagAssembler.addLinks(new ArrayList<>(giftCertificateDTO.getTags()));
        return giftCertificateDTO.add(linkTo(GiftCertificateController.class).slash("id?id=" + giftCertificateDTO.getId()).withSelfRel());
    }

    /**
     * Adds links to every single {@link GiftCertificate} and its {@link Tag} in the given list.
     *
     * @param giftCertificateDTOs to add links
     * @return giftCertificates with links
     */
    public List<GiftCertificateDTO> addLinks(List<GiftCertificateDTO> giftCertificateDTOs) {
        giftCertificateDTOs.forEach(this::addLink);
        return giftCertificateDTOs;
    }

    /**
     * Gets link to all {@link GiftCertificate} in database
     *
     * @return link to all {@link GiftCertificate} in database
     */
    public List<Link> getLinkToCollection() {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(TagController.class).withSelfRel());
        return links;
    }
}
