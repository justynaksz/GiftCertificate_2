package com.epam.esm.embeddedrepotests;

import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.DataSourceConfig;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfig.class}, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
class GiftCertificateDaoIT {

    @Autowired
    GiftCertificateDAOImpl giftCertificateDAOImpl;

    @Nested
    @DisplayName("find by id test")
    class findByIdTest {
        @Test
        @DisplayName("gift certificate is correctly found")
        void findByIdShouldReturnCorrectGiftCertificate() {
            // GIVEN

            // WHEN
            GiftCertificate giftCertificateRetrieved = giftCertificateDAOImpl.findById(1);
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setName("H&M gift card");
            giftCertificate.setDescription("Gift card to the fashion store");
            giftCertificate.setPrice(100.00);
            giftCertificate.setDuration(90);
            giftCertificate.setId(1);
            giftCertificate.setCreateDate(LocalDateTime.parse("2022-06-22T18:31:44.574"));
            giftCertificate.setLastUpdateDate(null);
            // THEN
            assertEquals(giftCertificate, giftCertificateRetrieved);
        }

        @Test
        @DisplayName("gift certificate of given id doesn't exist")
        void findByIdShouldThrowExceptionIfGiftCertificateDoesNotExist() {
            // GIVEN
            int id = 999;
            // WHEN

            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateDAOImpl.findById(id));
        }
    }

    @Nested
    @DisplayName("find by tag test")
    class findByTag {
        @Test
        @DisplayName("gift certificates are correctly found")
        void findByTagShouldReturnListOfGiftCertificatesWithSpecifiedTag() {
            // GIVEN
            String requestedName = "shopping";
            // WHEN
            List<GiftCertificate> giftCertificatesInDb = giftCertificateDAOImpl.findByTag(requestedName);
            // THEN
            assertEquals(2, giftCertificatesInDb.size());
        }

        @Test
        @DisplayName("no matching gift certificate found")
        void findByTagShouldReturnEmptyListWhenNoGiftCertificateMatchRequest() {
            // GIVEN
            String requestedName = "football";
            // WHEN

            // THEN
            assertTrue(giftCertificateDAOImpl.findByTag(requestedName).isEmpty());
        }
    }

    @Nested
    @DisplayName("find by name or description test")
    class findByNameOrDescription {
        @Test
        @DisplayName("gift certificates are correctly found")
        void findByNameOrDescriptionShouldReturnListOfGiftCertificatesWithKeyWordInNameOrDescription() {
            // GIVEN

            // WHEN
            List<GiftCertificate> giftCertificatesInDb = giftCertificateDAOImpl.findByNameOrDescription("store");
            // THEN
            assertEquals(2, giftCertificatesInDb.size());
        }

        @Test
        @DisplayName("no matching gift certificate found")
        void findByNameOrDescriptionShouldReturnEmptyListWhenNoGiftCertificateMatchRequest() {
            // GIVEN
            String key = "swimming";
            // WHEN

            // THEN
            assertTrue(giftCertificateDAOImpl.findByNameOrDescription(key).isEmpty());
        }
    }

    @Test
    @DisplayName("find all gift certificates test")
    void shouldReturnListOfAllGiftCertificates() {
        // GIVEN

        // WHEN
        List<GiftCertificate> giftCertificatesInDb = giftCertificateDAOImpl.findAll();
        // THEN
        assertEquals(3, giftCertificatesInDb.size());
    }

    @Test
    @DisplayName("sort in ascending order test")
    void checksIfListSizeAndASCOrderIsCorrect() {
        // GIVEN

        // WHEN
        List<GiftCertificate> giftCertificatesInDb = giftCertificateDAOImpl.sortAscending();
        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(3).isEqualTo(giftCertificatesInDb.size());
        softAssertions.assertThat("Gift card to cafe").isEqualTo(giftCertificatesInDb.get(2).getDescription());
        softAssertions.assertThat("Gift card to the fashion store").isEqualTo(giftCertificatesInDb.get(0).getDescription());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("sort in descending order test")
    void checksIfListSizeAndDSCOrderIsCorrect() {
        // GIVEN

        // WHEN
        List<GiftCertificate> giftCertificatesInDb = giftCertificateDAOImpl.sortDescending();
        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(3).isEqualTo(giftCertificatesInDb.size());
        softAssertions.assertThat("Gift card to cafe").isEqualTo(giftCertificatesInDb.get(0).getDescription());
        softAssertions.assertThat("Gift card to the fashion store").isEqualTo(giftCertificatesInDb.get(2).getDescription());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("sort in ascending order by date test")
    void checksIfListSizeAndASCOrderByDateIsCorrect() {
        // GIVEN

        // WHEN
        List<GiftCertificate> giftCertificatesInDb = giftCertificateDAOImpl.sortAscendingByDate();
        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(3).isEqualTo(giftCertificatesInDb.size());
        softAssertions.assertThat("Gift card to the fashion store").isEqualTo(giftCertificatesInDb.get(2).getDescription());
        softAssertions.assertThat("Gift card to cafe").isEqualTo(giftCertificatesInDb.get(0).getDescription());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("sort in descending order by date test")
    void checksIfListSizeAndDESCOrderByDateIsCorrect() {
        // GIVEN

        // WHEN
        List<GiftCertificate> giftCertificatesInDb = giftCertificateDAOImpl.sortDescendingByDate();
        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(3).isEqualTo(giftCertificatesInDb.size());
        softAssertions.assertThat("Gift card to cafe").isEqualTo(giftCertificatesInDb.get(2).getDescription());
        softAssertions.assertThat("Gift card to the fashion store").isEqualTo(giftCertificatesInDb.get(0).getDescription());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("create gift certificate test")
    void specificGiftCertificateIsPresentInDbAndCountOfGiftCertificatesInDbIsCorrect() {
        // GIVEN
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Paintball voucher");
        giftCertificate.setDescription("2 hours of paintball match in Paintball-World");
        giftCertificate.setPrice(49.99);
        giftCertificate.setDuration(180);
        int initialDbSize = giftCertificateDAOImpl.findAll().size();
        int expectedDBSizeChange = 1;
        // WHEN
        GiftCertificate giftCertificateInserted = giftCertificateDAOImpl.createGiftCertificate(giftCertificate);
        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(giftCertificateDAOImpl.findAll().contains(giftCertificateInserted)).isTrue();
        softAssertions.assertThat(giftCertificate).isEqualTo(giftCertificateInserted);
        softAssertions.assertThat(initialDbSize + expectedDBSizeChange).isEqualTo(giftCertificateDAOImpl.findAll().size());
        softAssertions.assertAll();
        giftCertificateDAOImpl.deleteGiftCertificate(giftCertificate.getId());
    }

    @Nested
    @DisplayName("update gift certificate test")
    class updateGiftCertificate {

        @Test
        @DisplayName("gift certificate is correctly updated")
        void sizeOfDbIsNotChangedAndGiftCertificateIsCorrectlyUpdated() {
            // GIVEN
            int initDatabaseSize = giftCertificateDAOImpl.findAll().size();
            GiftCertificate giftCertificate = giftCertificateDAOImpl.findById(2);
            giftCertificate.setDescription("Gift card to the Mont Blanc cafe");
            // WHEN
            giftCertificateDAOImpl.updateGiftCertificate(giftCertificate);
            // THEN
            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(initDatabaseSize).isEqualTo(giftCertificateDAOImpl.findAll().size());
            softAssertions.assertThat(giftCertificate).isEqualTo(giftCertificateDAOImpl.findById(2));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("update non existing gift certificate")
        void updateNonExistingGiftCertificateShouldTrowException() {
            // GIVEN

            // WHEN
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setName("Paintball voucher");
            giftCertificate.setDescription("2 hours of paintball match in Paintball-World");
            giftCertificate.setPrice(49.99);
            giftCertificate.setDuration(180);
            giftCertificate.setId(999);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateDAOImpl.updateGiftCertificate(giftCertificate));
        }
    }

    @Nested
    @DisplayName("delete gift certificate test")
    class deleteGiftCertificate {

        @Test
        @DisplayName("gift certificate is correctly removed")
        void afterDeleteGiftCertificateIsNotPresentInDb() {
            // GIVEN
            int dbSize = giftCertificateDAOImpl.findAll().size();
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setName("Photo session");
            giftCertificate.setDescription("Outdoor photo session");
            giftCertificate.setPrice(250.00);
            giftCertificate.setDuration(250);
            // WHEN
            GiftCertificate giftCertificateInserted = giftCertificateDAOImpl.createGiftCertificate(giftCertificate);
            giftCertificateDAOImpl.deleteGiftCertificate(giftCertificateInserted.getId());
            // THEN
            assertFalse(giftCertificateDAOImpl.findAll().contains(giftCertificateInserted));
        }

        @Test
        @DisplayName("delete non existing gift certificate test")
        void deleteNonExistingGiftCertificateShouldTrowException() {
            // GIVEN
            int requestedId = 999;
            // WHEN

            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateDAOImpl.deleteGiftCertificate(requestedId));
        }
    }
}
