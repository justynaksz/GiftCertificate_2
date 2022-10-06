package com.epam.esm.hateoas.pagelinker;

import com.epam.esm.controllers.OrderController;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.model.Order;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Adds link to first, previous, next and last page in {@link Order} get methods.
 */
@Component
public class OrderPageLinker {

    /**
     * Adds links to first, previous, next and last page in {@code getAll} {@link Order}.
     *
     * @param page  current page
     * @param links current list of links
     */
    public void addPagesLinksInGetAllMethod(Page<OrderDTO> page, List<Link> links) {
        if (page.hasPrevious()) {
            int previousPage = page.getPageIndex() - 1;
            links.add(linkTo(getAllMethod(1, page.getSize())).withRel("first"));
            links.add(linkTo(getAllMethod(previousPage, page.getSize())).withRel("previous"));
        }
        if (page.hasNext()) {
            int nextPage = page.getPageIndex() + 1;
            links.add(linkTo(getAllMethod(nextPage, page.getSize())).withRel("next"));
            links.add(linkTo(getAllMethod(page.getLastPageIndex(), page.getSize())).withRel("last"));
        }
    }

    private CollectionModel<OrderDTO> getAllMethod(int pageNumber, int size) {
        return methodOn(OrderController.class).getAll(pageNumber, size);
    }
}
