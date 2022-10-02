package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.hateoas.assembler.GiftCertificateAssembler;
import com.epam.esm.hateoas.pagelinker.GiftCertificatePageLinker;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.pagination.Page;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Spring REST controllers for processing requests {@link GiftCertificate} resource.
 */
@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler assembler;
    private final GiftCertificatePageLinker pageLinker;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateAssembler assembler, GiftCertificatePageLinker pageLinker) {
        this.giftCertificateService = giftCertificateService;
        this.assembler = assembler;
        this.pageLinker = pageLinker;
    }

    /**
     * Finds {@link GiftCertificate} with requested id.
     * Handles GET http-request.
     *
     * @param id requested id
     * @return GiftCertificateDTO   of given id
     * @throws InvalidInputException in case of invalid id
     * @throws NotFoundException in case of no tag of given id in database
     */
    @GetMapping("/{id}")
    public GiftCertificateDTO getById(@PathVariable Integer id) throws InvalidInputException, NotFoundException {
        return assembler.addLink(giftCertificateService.getById(id));
    }

    /**
     * Finds {@link GiftCertificate} with requested list of {@link Tag} and requested param optionally sorted by name or date.
     * Handles GET http-request.
     *
     * @param sort     enum - sorting by giftCertificate's name / date
     * @param direction enum - sorting in ascending / descending order
     * @param key           key word in giftCertificate's name or description
     * @param tags          list of tag's names as search criteria
     * @return giftCertificates lists        giftCertificates that fits criteria
     */
    @GetMapping("param")
    public CollectionModel<GiftCertificateDTO> getByParam(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "5") int size,
                                                          @RequestParam(required = false) String sort,
                                                          @RequestParam(required = false) String direction,
                                                          @RequestParam(required = false) String key,
                                                          @RequestParam(required = false) Set<String> tags) {
        Page<GiftCertificateDTO> pageOfResults =  giftCertificateService.getByParam(page, size, sort, direction, key, tags);
        List<GiftCertificateDTO> giftCertificateDTOS = assembler.addLinks(pageOfResults.getContent());
        List<Link> links = assembler.getLinkToCollection();
        pageLinker.addPagesLinksInGetByParamMethod(pageOfResults, links, sort, direction, key, tags);
        return CollectionModel.of(giftCertificateDTOS, links);
    }

    /**
     * Gets all {@link GiftCertificate}.
     * Handles GET http-request.
     *
     * @return giftCertificates   list of all giftCertificates in database
     */
    @GetMapping
    public CollectionModel<GiftCertificateDTO> getAll(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "5") int size) {
        Page<GiftCertificateDTO> pageOfResults = giftCertificateService.getAll(page, size);
        List<GiftCertificateDTO> giftCertificateDTOs = assembler.addLinks(pageOfResults.getContent());
        List<Link> links = assembler.getLinkToCollection();
        pageLinker.addPagesLinksInGetAllMethod(pageOfResults, links);
        return CollectionModel.of(giftCertificateDTOs, links);
    }

    /**
     * Creates new {@link GiftCertificate}.
     * Handles POST http-request.
     *
     * @param giftCertificateDTOToInsert giftCertificate to be inserted into database
     * @return giftCertificate                   that has been inserted into database
     * @throws InvalidInputException in case of negative id value
     * @throws NotFoundException in case no tag of requested id is in database
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO addGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTOToInsert) throws InvalidInputException, NotFoundException {
        return assembler.addLink(giftCertificateService.addGiftCertificate(giftCertificateDTOToInsert));
    }

    /**
     * Updates {@link GiftCertificate} in database.
     * Handles PUT http-request.
     *
     * @param giftCertificateDTOUpdate giftCertificate to update the one existing in database
     * @return updated giftCertificate
     * @throws InvalidInputException in case of negative price or duration input
     * @throws NotFoundException     in case of giftCertificate to be updated is not present in database
     */
    @PutMapping
    public GiftCertificateDTO updateGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTOUpdate) throws InvalidInputException, NotFoundException {
       return giftCertificateService.updateGiftCertificate(giftCertificateDTOUpdate);
    }

    /**
     * Removes {@link GiftCertificate} from database.
     * Handles DELETE http-request.
     *
     * @param id int id of giftCertificate to delete from database
     * @throws NotFoundException in case of giftCertificate to be deleted is not present in database
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable Integer id) throws NotFoundException {
        giftCertificateService.deleteGiftCertificate(id);
    }
}
