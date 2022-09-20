package com.epam.esm.controllers;

import com.epam.esm.assembler.GiftCertificateAssembler;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * Spring REST controllers for processing requests {@code giftCertificate} resource.
 */
@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler assembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateAssembler assembler) {
        this.giftCertificateService = giftCertificateService;
        this.assembler = assembler;
    }

    /**
     * Finds {@code giftCertificate} with requested id.
     * Handles GET http-request.
     *
     * @param id requested id
     * @return GiftCertificateDTO   of given id
     */
    @GetMapping("id")
    public GiftCertificateDTO getById(@RequestParam Integer id) throws InvalidInputException, NotFoundException {
        return assembler.addLink(giftCertificateService.getById(id));
    }

    /**
     * Finds {@code giftCertificate} with requested list of {@code tag} and requested param optionally sorted by name or date.
     * Handles GET http-request.
     *
     * @param sortParam     enum - sorting by giftCertificate's name / date
     * @param sortDirection enum - sorting in ascending / descending order
     * @param key           key word in giftCertificate's name or description
     * @param tags          list of tag's names as search criteria
     * @return giftCertificates lists        giftCertificates that fits criteria
     * @throws NotFoundException in case of no result in database
     */
    @GetMapping("param")
    public CollectionModel<GiftCertificateDTO> getByParam(@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "5") int size,
                                                          @RequestParam(required = false) String sortParam,
                                                          @RequestParam(required = false) String sortDirection,
                                                          @RequestParam(required = false) String key,
                                                          @RequestParam(required = false) Set<String> tags) throws NotFoundException {
        List<GiftCertificateDTO> giftCertificateDTOS = assembler.addLinks(giftCertificateService.getByParam(page, size, sortParam, sortDirection, key, tags).getContent());
        List<Link> links = assembler.getLinkToCollection();
        return CollectionModel.of(giftCertificateDTOS, links);
    }

    /**
     * Gets all {@code giftCertificate}.
     * Handles GET http-request.
     *
     * @return giftCertificates   list of all giftCertificates in database
     * @throws NotFoundException in case of no result in database
     */
    @GetMapping
    public CollectionModel<GiftCertificateDTO> getAll(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "5") int size)
            throws NotFoundException {

        List<GiftCertificateDTO> giftCertificateDTOs = assembler.addLinks(giftCertificateService.getAll(page, size).getContent());
        List<Link> links = assembler.getLinkToCollection();
        return CollectionModel.of(giftCertificateDTOs, links);
    }

    /**
     * Creates new {@code giftCertificate}.
     * Handles POST http-request.
     *
     * @param giftCertificateDTOToInsert giftCertificate to be inserted into database
     * @return giftCertificate                   that has been inserted into database
     * @throws InvalidInputException in case of negative id value
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO addGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTOToInsert) throws InvalidInputException, NotFoundException {
        return assembler.addLink(giftCertificateService.addGiftCertificate(giftCertificateDTOToInsert));
    }

    /**
     * Updates {@code giftCertificate} in database.
     * Handles PUT http-request.
     *
     * @param giftCertificateDTOUpdate giftCertificate to update the one existing in database
     * @throws InvalidInputException in case of negative price or duration input
     * @throws NotFoundException     in case of giftCertificate to be updated is not present in database
     */
    @PutMapping
    public void updateGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTOUpdate) throws InvalidInputException, NotFoundException {
        giftCertificateService.updateGiftCertificate(giftCertificateDTOUpdate);
    }

    /**
     * Removes {@code giftCertificate} from database.
     * Handles DELETE http-request.
     *
     * @param id int id of giftCertificate to delete from database
     * @throws NotFoundException in case of giftCertificate to be deleted is not present in database
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@RequestParam Integer id) throws NotFoundException {
        giftCertificateService.deleteGiftCertificate(id);
    }
}
