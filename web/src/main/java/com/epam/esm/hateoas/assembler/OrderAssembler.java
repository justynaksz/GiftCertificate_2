package com.epam.esm.hateoas.assembler;

import com.epam.esm.controllers.OrderController;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.model.Order;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Generates links to {@link Order}.
 */
@Component
public class OrderAssembler {

    private final TagAssembler tagAssembler;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final UserAssembler userAssembler;

    public OrderAssembler(TagAssembler tagAssembler, GiftCertificateAssembler giftCertificateAssembler, UserAssembler userAssembler) {
        this.tagAssembler = tagAssembler;
        this.giftCertificateAssembler = giftCertificateAssembler;
        this.userAssembler = userAssembler;
    }

    /**
     * Adds link to itself.
     *
     * @param orderDTO to add link
     * @return orderDTO with link to itself
     */
    public OrderDTO addLink(OrderDTO orderDTO) {
        userAssembler.addLink(orderDTO.getUser());
        giftCertificateAssembler.addLink(orderDTO.getGiftCertificate());
        tagAssembler.addLinks(new ArrayList<>(orderDTO.getGiftCertificate().getTags()));
        return orderDTO.add(linkTo(OrderController.class).slash("id/" + orderDTO.getId()).withSelfRel());
    }

    /**
     * Adds links to every single {@link Order} in the given list.
     *
     * @param orderDTOs to add links
     * @return orderDTOs with links to every single {@link Order}
     */
    public List<OrderDTO> addLinks(List<OrderDTO> orderDTOs) {
        orderDTOs.forEach(this::addLink);
        return orderDTOs;
    }

    /**
     * Gets link to all {@link Order} in database
     *
     * @return link to all {@link Order} in database
     */
    public Link getLinkToCollection() {
        return linkTo(OrderController.class).withSelfRel();
    }
}
