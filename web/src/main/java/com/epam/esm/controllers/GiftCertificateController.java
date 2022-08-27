package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring REST controllers for processing requests {@code giftCertificate} resource.
 */
@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Finds {@code giftCertificate} with requested id.
     * Handles GET http-request.
     *
     * @param id requested id
     * @return GiftCertificateDTO   of given id
     */
    @GetMapping("/{id}")
    public GiftCertificateDTO getById(@PathVariable int id) {
        return giftCertificateService.getById(id);
    }

    /**
     * Finds {@code giftCertificate} with requested key word in name or description.
     * Handles GET http-request.
     *
     * @param key requested key word
     * @return giftCertificates     matching given key word
     */
    @GetMapping("key/{key}")
    public List<GiftCertificateDTO> getByNameOrDescription(@PathVariable String key) {
        return giftCertificateService.getByNameOrdDescription(key);
    }

    /**
     * Finds {@code giftCertificate} with requested tag name.
     * Handles GET http-request.
     *
     * @param tag requested tag name
     * @return giftCertificates       with given tag name
     */
    @GetMapping("tag/{tag}")
    public List<GiftCertificateDTO> getByTag(@PathVariable String tag) {
        return giftCertificateService.getByTag(tag);
    }

    /**
     * Gets all {@code giftCertificate}.
     * Handles GET http-request.
     *
     * @return giftCertificates   list of all giftCertificates in database
     */
    @GetMapping
    public List<GiftCertificateDTO> getAll() {
        return giftCertificateService.getAll();
    }

    /**
     * Sorts all {@code giftCertificate} by ascending order.
     * Handles GET http-request.
     *
     * @return giftCertificates   list of all giftCertificates in database in ascending order
     */
    @GetMapping("asc")
    public List<GiftCertificateDTO> sortAscending() {
        return giftCertificateService.sortAscending();
    }

    /**
     * Sorts all {@code giftCertificate} by descending order.
     * Handles GET http-request.
     *
     * @return giftCertificates   list of all giftCertificates in database in descending order
     */
    @GetMapping("desc")
    public List<GiftCertificateDTO> sortDescending() {
        return giftCertificateService.sortDescending();
    }

    /**
     * Sorts all {@code giftCertificate} by ascending order by date.
     * Handles GET http-request.
     *
     * @return giftCertificates   list of all giftCertificates in database in ascending order by date
     */
    @GetMapping("asc/date")
    public List<GiftCertificateDTO> sortAscendingByDate() {
        return giftCertificateService.sortAscendingByDate();
    }

    /**
     * Sorts all {@code giftCertificate} by descending order by date.
     * Handles GET http-request.
     *
     * @return giftCertificates   list of all giftCertificates in database in descending order by date
     */
    @GetMapping("desc/date")
    public List<GiftCertificateDTO> sortDescendingByDate() {
        return giftCertificateService.sortDescendingByDate();
    }

    /**
     * Creates new {@code giftCertificate}.
     * Handles POST http-request.
     *
     * @param giftCertificateDTOToInsert giftCertificate to be inserted into database
     * @return giftCertificate                   that has been inserted into database
     */
    @PostMapping
    public GiftCertificateDTO addGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTOToInsert) {
        return giftCertificateService.addGiftCertificate(giftCertificateDTOToInsert);
    }

    /**
     * Updates {@code giftCertificate} in database.
     * Handles PUT http-request.
     *
     * @param giftCertificateDTOUpdate giftCertificate to update the one existing in database
     */
    @PutMapping
    public void updateGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateDTOUpdate) {
        giftCertificateService.updateGiftCertificate(giftCertificateDTOUpdate);
    }

    /**
     * Removes {@code giftCertificate} from database.
     * Handles DELETE http-request.
     *
     * @param id int id of giftCertificate to delete from database
     */
    @DeleteMapping("/{id}")
    public void deleteGiftCertificate(@PathVariable int id) {
        giftCertificateService.deleteGiftCertificate(id);
    }
}
