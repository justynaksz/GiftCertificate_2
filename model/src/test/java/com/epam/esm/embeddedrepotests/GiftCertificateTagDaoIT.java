package com.epam.esm.embeddedrepotests;

import com.epam.esm.dao.impl.GiftCertificateTagDAOImpl;
import com.epam.esm.model.GiftCertificateTag;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfig.class}, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
class GiftCertificateTagDaoIT {

    @Autowired
    GiftCertificateTagDAOImpl giftCertificateTagDAOImpl;

    @Nested
    @DisplayName("find by id test")
    class findByIdTest {

        @Test
        @DisplayName("giftCertificateTag is correctly found")
        void findGiftCertificateTagByIdShouldReturnCorrectGiftCertificateTag() {
            // GIVEN
            int id = 1;
            // WHEN
            GiftCertificateTag giftCertificateTagRetrieved = giftCertificateTagDAOImpl.findGiftCertificateTagById(id);
            // THEN
            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(1).isEqualTo(giftCertificateTagRetrieved.getGiftCertificateId());
            softAssertions.assertThat(2).isEqualTo(giftCertificateTagRetrieved.getTagId());
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("giftCertificateTag of given id doesn't exist")
        void findByGiftCertificateTagShouldThrowExceptionIfGiftCertificateTagDoesNotExist() {
            // GIVEN
            int id = 999;
            // WHEN

            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateTagDAOImpl.findGiftCertificateTagById(id));
        }
    }

    @Nested
    @DisplayName("find by ids test")
    class findByIds {

        @Test
        @DisplayName("giftCertificateTag correctly found")
        void findByIdsShouldReturnCorrectGiftCertificateTag() {
            // GIVEN
            int giftCertificateId = 2;
            int tagId = 1;
            GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
            giftCertificateTag.setTagId(tagId);
            giftCertificateTag.setGiftCertificateId(giftCertificateId);
            giftCertificateTag.setId(4);
            // WHEN
            GiftCertificateTag giftCertificateTagRetrieved = giftCertificateTagDAOImpl.findGiftCertificateTagByIds(giftCertificateId, tagId);
            // THEN
            assertEquals(giftCertificateTag, giftCertificateTagRetrieved);
        }

        @Test
        @DisplayName("giftCertificateTag of given params doesn't exist")
        void findByIdsShouldThrowExceptionIfGiftCertificateTagDoesNotExist() {
            // GIVEN
            int giftCertificateId = 999;
            int tagId = 999;
            // WHEN

            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateTagDAOImpl.findGiftCertificateTagByIds(giftCertificateId, tagId));
        }
    }

    @Test
    @DisplayName("create giftCertificateTag test")
    public void specificGiftCertificateTagIsPresentInDbAndCountOfAllGiftCertificateTagsIsCorrect() {
        // GIVEN
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
        giftCertificateTag.setGiftCertificateId(3);
        giftCertificateTag.setTagId(1);
        //WHEN
        GiftCertificateTag giftCertificateTagInserted = giftCertificateTagDAOImpl.createGiftCertificateTag(giftCertificateTag);
        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(giftCertificateTag.getGiftCertificateId()).isEqualTo(giftCertificateTagInserted.getGiftCertificateId());
        softAssertions.assertThat(giftCertificateTag.getTagId()).isEqualTo(giftCertificateTagInserted.getTagId());
        softAssertions.assertThat(giftCertificateTagInserted).isEqualTo(giftCertificateTagDAOImpl.findGiftCertificateTagByIds(3, 1));
        softAssertions.assertAll();
    }

    @Nested
    @DisplayName("delete giftCertificatTag test")
    class deleteGiftCertificateTag {

        @Test
        @DisplayName("giftCertificateTag is correctly removed")
        void deleteGiftCertificateTagShouldRemoveGiftCertificateTagOfGivenIdFromDatabase() {
            // GIVEN
            int id = 5;
            GiftCertificateTag giftCertificateTag = giftCertificateTagDAOImpl.findGiftCertificateTagById(5);
            // WHEN
            giftCertificateTagDAOImpl.deleteGiftCertificateTag(id);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateTagDAOImpl.findGiftCertificateTagById(id));
        }

        @Test
        @DisplayName("delete non existing giftCertificateTag test")
        void deleteNonExistingGiftCertificateTagShouldThrowException() {
            // GIVEN
            int id = 999;
            // WHEN

            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateTagDAOImpl.deleteGiftCertificateTag(id));
        }
    }
}
