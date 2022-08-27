package com.epam.esm.embeddedrepotests;

import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.model.Tag;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataSourceConfig.class}, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
class TagDaoIT {

    @Autowired
    TagDAOImpl tagDAOImpl;

    @Nested
    @DisplayName("find by id tests")
    class findByIdTest {

        @Test
        @DisplayName("tag is correctly found")
        void findByIdShouldReturnCorrectTag() {
            // GIVEN
            int id = 1;
            // WHEN
            Tag tagRetrieved = tagDAOImpl.findById(id);
            // THEN
            assertEquals("sweets", tagRetrieved.getName());
        }

        @Test
        @DisplayName("tag of given id doesn't exist")
        void findByIdShouldThrowExceptionIfTagDoesNotExist() {
            // GIVEN
            int id = 999;
            // WHEN

            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagDAOImpl.findById(id));
        }
    }

    @Nested
    @DisplayName("find by name tests")
    class findByName {

        @Test
        @DisplayName("tag is correctly found")
        void findByNameShouldReturnCorrectTag() {
            // GIVEN
            Tag tag = new Tag();
            tag.setId(2);
            tag.setName("fashion");
            // WHEN
            Tag tagRetrieved = tagDAOImpl.findByName("fashion");
            // THEN
            assertEquals(tag.getId(), tagRetrieved.getId());
        }

        @Test
        @DisplayName("tag of given name doesn't exist")
        void findByNameShouldThrowExceptionIfTagDoesNotExist() {
            // GIVEN
            String name = "photo";
            // WHEN

            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagDAOImpl.findByName(name));
        }
    }

    @Nested
    @DisplayName("find by gift certificate id test")
    class findByGiftCertificateId {

        @Test
        @DisplayName("tags are correctly found")
        void findByGiftCertificateIdShouldReturnAllWiredTags() {
            // GIVEN
            int giftCertificateId = 1;
            List<Tag> tagsWiredWithGiftCertificate = new ArrayList<>();
            Tag fashion = new Tag(2, "fashion");
            Tag jewellery = new Tag(3, "jewellery");
            Tag shopping = new Tag(6, "shopping");
            tagsWiredWithGiftCertificate.add(fashion);
            tagsWiredWithGiftCertificate.add(jewellery);
            tagsWiredWithGiftCertificate.add(shopping);
            // WHEN
            List<Tag> retrievedTags = tagDAOImpl.findTagsByGiftCertificateId(giftCertificateId);
            // THEN
            assertEquals(tagsWiredWithGiftCertificate, retrievedTags);
        }

        @Test
        @DisplayName("no tags connected to given giftCertificate")
        void findByGiftCertificateIdWithNonExistingGiftCertificateShouldReturnEmptyList() {
            // GIVEN
            int giftCertificateId = 999;
            // WHEN
            List<Tag> retrievedTags = tagDAOImpl.findTagsByGiftCertificateId(giftCertificateId);
            // THEN
            assertTrue(retrievedTags.isEmpty());
        }
    }

    @Test
    @DisplayName("find all tags test")
    void findAllShouldReturnListOfAllTags() {
        // GIVEN

        // WHEN
        List<Tag> tagsInDb = tagDAOImpl.findAll();
        // THEN
        assertEquals(6, tagsInDb.size());
    }

    @Test
    @DisplayName("create tag test")
    void specificTagIsPresentInDbAndCountOfTagInDbIsCorrect() {
        // GIVEN
        Tag tag = new Tag();
        tag.setName("family time");
        // WHEN
        Tag tagInserted = tagDAOImpl.createTag(tag);
        // THEN
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(tagDAOImpl.findAll().contains(tagInserted)).isTrue();
        softAssertions.assertThat(7).isEqualTo(tagDAOImpl.findAll().size());
        softAssertions.assertAll();
        tagDAOImpl.deleteTag(tagInserted.getId());
    }

    @Nested
    @DisplayName("delete tag tests")
    class deleteTagTest {

        @Test
        @DisplayName("tag is correctly removed")
        void deleteTagShouldRemoveTagOfGivenIdFromDatabase() {
            // GIVEN
            int id = 5;
            Tag tag = tagDAOImpl.findById(id);
            int dbSize = tagDAOImpl.findAll().size();
            int expectedDBSizeChange = 1;
            // WHEN
            tagDAOImpl.deleteTag(id);
            // THEN
            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(dbSize - expectedDBSizeChange).isEqualTo(tagDAOImpl.findAll().size());
            softAssertions.assertThat(tagDAOImpl.findAll().contains(tag)).isFalse();
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("delete non existing tag test")
        void deleteNonExistingTagShouldTrowException() {
            // GIVEN
            int id = 999;
            // WHEN

            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> tagDAOImpl.deleteTag(id));
        }
    }
}
