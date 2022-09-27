package com.epam.esm.hateoas.pagelinker;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Adds link to first, previous, next and last page in {@link GiftCertificate} get methods.
 */
@Component
public class GiftCertificatePageLinker {

    /**
     * Adds links to first, previous, next and last page in {@code getAll} {@link GiftCertificate}.
     *
     * @param page  current page
     * @param links current list of links
     */
    public void addPagesLinksInGetAllMethod(Page<GiftCertificateDTO> page, List<Link> links) {
        if(page.hasPrevious()) {
            int previousPage = page.getPageIndex() - 1;
            links.add(linkTo(getAllMethod(1, page.getSize())).withRel("first"));
            links.add(linkTo(getAllMethod(previousPage, page.getSize())).withRel("previous"));
        }
        if(page.hasNext()) {
            int nextPage = page.getPageIndex() + 1;
            links.add(linkTo(getAllMethod(nextPage, page.getSize())).withRel("next"));
            links.add(linkTo(getAllMethod(page.getLastPageIndex(), page.getSize())).withRel("last"));
        }
    }

    /**
     * Adds links to first, previous, next and last page in {@code getByParam} {@link GiftCertificate}.
     *
     * @param page  current page
     * @param links current list of links
     */
    public void addPagesLinksInGetByParamMethod(Page<GiftCertificateDTO> page, List<Link> links, String sort, String direction,
                                                String key, Set<String> tags) {
        if(page.hasPrevious()) {
            int previousPage = page.getPageIndex() - 1;
            links.add(linkTo(getByParamMethod(1, page.getSize(), sort, direction, key, tags)).withRel("first"));
            links.add(linkTo(getByParamMethod(previousPage, page.getSize(), sort, direction, key, tags)).withRel("previous"));
        }
        if(page.hasNext()) {
            int nextPage = page.getPageIndex() + 1;
            links.add(linkTo(getByParamMethod(nextPage, page.getSize(), sort, direction, key, tags))
                    .withRel("next"));
            links.add(linkTo(getByParamMethod(page.getLastPageIndex(), page.getSize(), sort, direction, key, tags))
                    .withRel("last"));
        }
    }

    private CollectionModel<GiftCertificateDTO> getAllMethod (int pageNumber, int size) {
        return methodOn(GiftCertificateController.class).getAll(pageNumber, size);
    }

    private CollectionModel<GiftCertificateDTO> getByParamMethod(int pageNumber, int size, String sort, String direction,
                                                                 String key, Set<String> tags) {
        return methodOn(GiftCertificateController.class).getByParam(pageNumber, size, sort, direction, key, tags);
    }
}
