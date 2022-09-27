package com.epam.esm.hateoas.assembler;

import com.epam.esm.controllers.TagController;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.model.Tag;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Generates links to {@link Tag}.
 */
@Component
public class TagAssembler {

    /**
     * Adds link to itself.
     *
     * @param tagDTO to add link
     * @return tagDTO with link to itself
     */
    public TagDTO addLink(TagDTO tagDTO) {
        return tagDTO.add(linkTo(TagController.class).slash("id?id=" + tagDTO.getId()).withSelfRel());
    }

    /**
     * Adds links to every single {@link Tag} in the given list.
     *
     * @param tagDTOs to add links
     * @return tagDTOs with links to every single {@link Tag}
     */
    public List<TagDTO> addLinks(List<TagDTO> tagDTOs) {
        tagDTOs.forEach(this::addLink);
        return tagDTOs;
    }

    /**
     * Gets link to all {@link Tag} in database
     *
     * @return link to all {@link Tag} in database
     */
    public Link getLinkToCollection() {
        return linkTo(TagController.class).withSelfRel();
    }
}
