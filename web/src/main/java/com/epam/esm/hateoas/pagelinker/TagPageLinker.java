package com.epam.esm.hateoas.pagelinker;

import com.epam.esm.controllers.TagController;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.model.Tag;
import com.epam.esm.pagination.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Adds link to first, previous, next and last page in {@link Tag} get methods.
 */
@Component
public class TagPageLinker {

    /**
     * Adds links to first, previous, next and last page in {@code getAll} {@link Tag}.
     *
     * @param page  current page
     * @param links current list of links
     */
    public void addPagesLinksInGetAllMethod(Page<TagDTO> page, List<Link> links) {
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
     * Adds links to first, previous, next and last page in {@code getTagsByParam} {@link Tag}.
     *
     * @param page  current page
     * @param links current list of links
     */
    public void addPagesLinksInGetByParamMethod(Page<TagDTO> page, List<Link> links, Integer id, String name) throws InvalidInputException {
        if(page.hasPrevious()) {
            int previousPage = page.getPageIndex() - 1;
            links.add(linkTo(getByParamMethod(1, page.getSize(), id, name)).withRel("first"));
            links.add(linkTo(getByParamMethod(previousPage, page.getSize(), id, name)).withRel("previous"));
        }
        if(page.hasNext()) {
            int nextPage = page.getPageIndex() + 1;
            links.add(linkTo(getByParamMethod(nextPage, page.getSize(),  id, name))
                    .withRel("next"));
            links.add(linkTo(getByParamMethod(page.getLastPageIndex(), page.getSize(),  id, name))
                    .withRel("last"));
        }
    }

    private CollectionModel<TagDTO> getAllMethod (int pageNumber, int size) {
        return methodOn(TagController.class).getAllTags(pageNumber, size);
    }

    private CollectionModel<TagDTO> getByParamMethod(int pageNumber, int size, Integer id, String name) throws InvalidInputException {
        return methodOn(TagController.class).getTagsByParam(pageNumber, size, id, name);
    }
}
