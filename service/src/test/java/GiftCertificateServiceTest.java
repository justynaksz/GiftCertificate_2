import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.dao.impl.GiftCertificateTagDAOImpl;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exceptions.InvalidInputException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @InjectMocks
    private GiftCertificateService giftCertificateService;

    @Mock
    private GiftCertificateDAOImpl giftCertificateDAO;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private TagDAO tagDAO;

    @Mock
    private GiftCertificateTagDAOImpl giftCertificateTagDAO;

    @Nested
    @DisplayName("find by id tests")
    class findByIdTest {

        @Test
        @DisplayName("gift certificate is correctly found")
        void findByIdShouldReturnCorrectGiftCertificate() {
            // GIVEN
            int id = 1;
            GiftCertificate giftCertificate = new GiftCertificate(id, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                    Duration.ofDays(180), null, null);
            GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(id, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", 49.99,
                    Duration.ofDays(180), null, null, new ArrayList<>());
            // WHEN
            when(giftCertificateDAO.findById(id)).thenReturn(giftCertificate);
            when(giftCertificateMapper.toDTO(giftCertificate)).thenReturn(giftCertificateDTO);
            // THEN
            assertEquals(giftCertificateDTO, giftCertificateService.getById(id));
        }

        @Test
        @DisplayName("gift certificate of given id doesn't exist")
        void findByIdShouldThrowExceptionIfGiftCertificateDoesNotExist() {
            // GIVEN
            int id = 1;
            // WHEN
            when(giftCertificateDAO.findById(id)).thenThrow(EmptyResultDataAccessException.class);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateService.getById(id));
        }

        @Test
        @DisplayName("given id is invalid")
        void findByIdShouldThrowExceptionIfIdIsNegative() {
            // GIVEN
            int id = -2;
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.getById(id));
        }
    }

    @Nested
    @DisplayName("find by tag tests")
    class findByTagTest {

        @Test
        @DisplayName("list of gift certificates correctly generated")
        void findByTagShouldReturnCorrectListOfGiftCertificates() {
            // GIVEN
            String requestedTag = "sport";
            GiftCertificate paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                    Duration.ofDays(180), null, null);
            GiftCertificate aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                    "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00),
                    Duration.ofDays(90), LocalDateTime.parse("2022-03-18T12:24:47.241"), null);
            List<GiftCertificate> giftCertificateByTag = new ArrayList<>();
            giftCertificateByTag.add(paintballCertificate);
            giftCertificateByTag.add(aquaparkCertificate);
            GiftCertificateDTO paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", 49.99,
                    Duration.ofDays(180), null, null, new ArrayList<>());
            GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                    "3 hours in the biggest auqapark in PL", 30.00,
                    Duration.ofDays(90), "2022-03-18T12:24:47.241", null, new ArrayList<>());
            List<GiftCertificateDTO> giftCertificateDTOByTag = new ArrayList<>();
            giftCertificateDTOByTag.add(paintballCertificateDTO);
            giftCertificateDTOByTag.add(aquaparkCertificateDTO);
            // WHEN
            when(giftCertificateDAO.findByTag(requestedTag)).thenReturn(giftCertificateByTag);
            when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
            when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
            // THEN
            assertEquals(giftCertificateDTOByTag, giftCertificateService.getByTag(requestedTag));
        }

        @Test
        @DisplayName("no matching gift certificate found")
        void findByTagShouldReturnEmptyListWhenNoGiftCertificateMatchRequest() {
            // GIVEN
            String requestedTag = "sport";
            // WHEN
            when(giftCertificateDAO.findByTag(requestedTag)).thenReturn(new ArrayList<>());
            // THEN
            assertTrue(giftCertificateService.getByTag(requestedTag).isEmpty());
        }
    }

    @Nested
    @DisplayName("find by name or description tests")
    class findByNameOrDescription {

        @Test
        @DisplayName("list of gift certificates correctly generated")
        void findByNameOrDescriptionShouldReturnCorrectListOfGiftCertificates() {
            // GIVEN
            String keyWord = "hours";
            GiftCertificate paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                    Duration.ofDays(180), null, null);
            GiftCertificate aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                    "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00),
                    Duration.ofDays(90), LocalDateTime.parse("2022-03-18T12:24:47.241"), null);
            List<GiftCertificate> giftCertificateByKey = new ArrayList<>();
            giftCertificateByKey.add(paintballCertificate);
            giftCertificateByKey.add(aquaparkCertificate);
            GiftCertificateDTO paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                    "2 hours of paintball match in Paintball-World", 49.99,
                    Duration.ofDays(180), null, null, new ArrayList<>());
            GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                    "3 hours in the biggest auqapark in PL", 30.00, Duration.ofDays(90),
                    "2022-03-18T12:24:47.241", null, new ArrayList<>());
            List<GiftCertificateDTO> giftCertificateDTOByKey = new ArrayList<>();
            giftCertificateDTOByKey.add(paintballCertificateDTO);
            giftCertificateDTOByKey.add(aquaparkCertificateDTO);
            // WHEN
            when(giftCertificateDAO.findByTag(keyWord)).thenReturn(giftCertificateByKey);
            when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
            when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
            // THEN
            assertEquals(giftCertificateDTOByKey, giftCertificateService.getByTag(keyWord));
        }

        @Test
        @DisplayName("no matching gift certificate found")
        void findByTagShouldReturnEmptyListWhenNoGiftCertificateMatchRequest() {
            // GIVEN
            String requestedTag = "sport";
            // WHEN
            when(giftCertificateDAO.findByNameOrDescription(requestedTag)).thenReturn(new ArrayList<>());
            // THEN
            assertTrue(giftCertificateService.getByNameOrdDescription(requestedTag).isEmpty());
        }
    }

    @Test
    @DisplayName("find all test")
    void findAllShouldReturnListOfAllGiftCertificatesInDb() {
        // GIVEN
        GiftCertificate paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                Duration.ofDays(180), null, null);
        GiftCertificate aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00),
                Duration.ofDays(90), LocalDateTime.parse("2022-03-18T12:24:47.241"), null);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(paintballCertificate);
        giftCertificates.add(aquaparkCertificate);
        GiftCertificateDTO paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", 49.99, Duration.ofDays(180),
                null, null, new ArrayList<>());
        GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", 30.00, Duration.ofDays(90),
                "2022-03-18T12:24:47.241", null, new ArrayList<>());
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();
        giftCertificatesDTO.add(paintballCertificateDTO);
        giftCertificatesDTO.add(aquaparkCertificateDTO);
        // WHEN
        when(giftCertificateDAO.findAll()).thenReturn(giftCertificates);
        when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
        when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
        // THEN
        assertEquals(giftCertificatesDTO, giftCertificateService.getAll());
    }

    @Test
    @DisplayName("sort ascending test")
    void sortAscendingShouldReturnAllGiftCertificatesInAscendingOrder() {
        // GIVEN
        GiftCertificate paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                Duration.ofDays(180), null, null);
        GiftCertificate aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00),
                Duration.ofDays(90), LocalDateTime.parse("2022-03-18T12:24:47.241"), null);
        GiftCertificate movieCertificate = new GiftCertificate(3, "Movie night",
                "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(aquaparkCertificate);
        giftCertificates.add(movieCertificate);
        giftCertificates.add(paintballCertificate);
        GiftCertificateDTO paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", 49.99, Duration.ofDays(180),
                null, null, new ArrayList<>());
        GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", 30.00, Duration.ofDays(90),
                "2022-03-18T12:24:47.241", null, new ArrayList<>());
        GiftCertificateDTO movieCertificateDTO = new GiftCertificateDTO(3, "Movie night",
                "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();
        giftCertificatesDTO.add(aquaparkCertificateDTO);
        giftCertificatesDTO.add(movieCertificateDTO);
        giftCertificatesDTO.add(paintballCertificateDTO);
        // WHEN
        when(giftCertificateDAO.sortAscending()).thenReturn(giftCertificates);
        when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
        when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
        when(giftCertificateMapper.toDTO(movieCertificate)).thenReturn(movieCertificateDTO);
        // THEN
        assertEquals(giftCertificatesDTO, giftCertificateService.sortAscending());
    }

    @Test
    @DisplayName("sort descending test")
    void sortDescendingShouldReturnAllGiftCertificatesInDescendingOrder() {
        // GIVEN
        GiftCertificate paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                Duration.ofDays(180), null, null);
        GiftCertificate aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00),
                Duration.ofDays(90), LocalDateTime.parse("2022-03-18T12:24:47.241"), null);
        GiftCertificate movieCertificate = new GiftCertificate(3, "Movie night",
                "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(paintballCertificate);
        giftCertificates.add(movieCertificate);
        giftCertificates.add(aquaparkCertificate);
        GiftCertificateDTO paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", 49.99, Duration.ofDays(180),
                null, null, new ArrayList<>());
        GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", 30.00, Duration.ofDays(90),
                "2022-03-18T12:24:47.241", null, new ArrayList<>());
        GiftCertificateDTO movieCertificateDTO = new GiftCertificateDTO(3, "Movie night",
                "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();
        giftCertificatesDTO.add(paintballCertificateDTO);
        giftCertificatesDTO.add(movieCertificateDTO);
        giftCertificatesDTO.add(aquaparkCertificateDTO);
        // WHEN
        when(giftCertificateDAO.sortDescending()).thenReturn(giftCertificates);
        when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
        when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
        when(giftCertificateMapper.toDTO(movieCertificate)).thenReturn(movieCertificateDTO);
        // THEN
        assertEquals(giftCertificatesDTO, giftCertificateService.sortDescending());
    }

    @Test
    @DisplayName("sort ascending by date test")
    void sortAscendingByDateShouldReturnAllGiftCertificatesInAscendingOrderByDate() {
        // GIVEN
        GiftCertificate paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                Duration.ofDays(180), LocalDateTime.parse("2019-03-18T12:24:47.241"), null);
        GiftCertificate aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00), Duration.ofDays(90),
                LocalDateTime.parse("2022-03-18T12:24:47.241"), null);
        GiftCertificate movieCertificate = new GiftCertificate(3, "Movie night",
                "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                LocalDateTime.parse("2021-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(paintballCertificate);
        giftCertificates.add(movieCertificate);
        giftCertificates.add(aquaparkCertificate);
        GiftCertificateDTO paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", 49.99, Duration.ofDays(180),
                "2019-03-18T12:24:47.241", null, new ArrayList<>());
        GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", 30.00, Duration.ofDays(90),
                "2022-03-18T12:24:47.241", null, new ArrayList<>());
        GiftCertificateDTO movieCertificateDTO = new GiftCertificateDTO(3, "Movie night",
                "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                "2021-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();
        giftCertificatesDTO.add(paintballCertificateDTO);
        giftCertificatesDTO.add(movieCertificateDTO);
        giftCertificatesDTO.add(aquaparkCertificateDTO);
        // WHEN
        when(giftCertificateDAO.sortAscendingByDate()).thenReturn(giftCertificates);
        when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
        when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
        when(giftCertificateMapper.toDTO(movieCertificate)).thenReturn(movieCertificateDTO);
        // THEN
        assertEquals(giftCertificatesDTO, giftCertificateService.sortAscendingByDate());
    }

    @Test
    @DisplayName("sort descending by date test")
    void sortDescendingByDateShouldReturnAllGiftCertificatesInDescendingOrderByDate() {
        // GIVEN
        GiftCertificate paintballCertificate = new GiftCertificate(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", BigDecimal.valueOf(49.99),
                Duration.ofDays(180), LocalDateTime.parse("2019-03-18T12:24:47.241"), null);
        GiftCertificate aquaparkCertificate = new GiftCertificate(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", BigDecimal.valueOf(30.00),
                Duration.ofDays(90), LocalDateTime.parse("2022-03-18T12:24:47.241"), null);
        GiftCertificate movieCertificate = new GiftCertificate(3, "Movie night",
                "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                LocalDateTime.parse("2021-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(aquaparkCertificate);
        giftCertificates.add(movieCertificate);
        giftCertificates.add(paintballCertificate);
        GiftCertificateDTO paintballCertificateDTO = new GiftCertificateDTO(1, "Paintball voucher",
                "2 hours of paintball match in Paintball-World", 49.99, Duration.ofDays(180),
                "2019-03-18T12:24:47.241", null, new ArrayList<>());
        GiftCertificateDTO aquaparkCertificateDTO = new GiftCertificateDTO(2, "Aquapark Fun",
                "3 hours in the biggest auqapark in PL", 30.00, Duration.ofDays(90),
                "2022-03-18T12:24:47.241", null, new ArrayList<>());
        GiftCertificateDTO movieCertificateDTO = new GiftCertificateDTO(3, "Movie night",
                "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                "2021-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();
        giftCertificatesDTO.add(aquaparkCertificateDTO);
        giftCertificatesDTO.add(movieCertificateDTO);
        giftCertificatesDTO.add(paintballCertificateDTO);
        // WHEN
        when(giftCertificateDAO.sortDescendingByDate()).thenReturn(giftCertificates);
        when(giftCertificateMapper.toDTO(paintballCertificate)).thenReturn(paintballCertificateDTO);
        when(giftCertificateMapper.toDTO(aquaparkCertificate)).thenReturn(aquaparkCertificateDTO);
        when(giftCertificateMapper.toDTO(movieCertificate)).thenReturn(movieCertificateDTO);
        // THEN
        assertEquals(giftCertificatesDTO, giftCertificateService.sortDescendingByDate());
    }

    @Nested
    @DisplayName("create gift certificate tests")
    class createGiftCertificateTests {

        @Test
        @DisplayName("gift certificate with non existing tags correctly inserted")
        void createGiftCertificateShouldInsertNewGiftCertificateWithNewTagsAndReturnInsertedGiftCertificate() {
            // GIVEN
            List<Tag> tags = setUpTags(Arrays.asList("cinema", "date", "movie"), false);
            GiftCertificate giftToBeInserted = new GiftCertificate(0, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                    LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
            GiftCertificateDTO giftToBeInsertedDTO = new GiftCertificateDTO(0, "Movie night",
                    "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", tags);
            giftToBeInsertedDTO.setTags(tags);
            List<Tag> tagsInserted = setUpTags(Arrays.asList("cinema", "date", "movie"), true);
            GiftCertificate giftInserted = new GiftCertificate(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                    LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
            GiftCertificateDTO giftInsertedDTO = new GiftCertificateDTO(3, "Movie night",
                    "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", tagsInserted);
            giftInsertedDTO.setTags(tagsInserted);
            // WHEN
            when(giftCertificateMapper.toModel(giftToBeInsertedDTO)).thenReturn(giftToBeInserted);
            when(tagDAO.findAll()).thenReturn(new ArrayList<>());
            when(giftCertificateDAO.createGiftCertificate(giftToBeInserted)).thenReturn(giftInserted);
            when(tagDAO.createTag(new Tag(0, "cinema"))).thenReturn(new Tag(1, "cinema"));
            when(tagDAO.createTag(new Tag(0, "date"))).thenReturn(new Tag(2, "date"));
            when(tagDAO.createTag(new Tag(0, "movie"))).thenReturn(new Tag(3, "movie"));
            when(giftCertificateTagDAO.createGiftCertificateTag(new GiftCertificateTag(3, 1)))
                    .thenReturn(new GiftCertificateTag(1, 3, 1));
            when(giftCertificateTagDAO.createGiftCertificateTag(new GiftCertificateTag(3, 2)))
                    .thenReturn(new GiftCertificateTag(2, 3, 2));
            when(giftCertificateTagDAO.createGiftCertificateTag(new GiftCertificateTag(3, 3)))
                    .thenReturn(new GiftCertificateTag(3, 3, 3));
            when(tagDAO.findTagsByGiftCertificateId(giftInserted.getId())).thenReturn(tagsInserted);
            when(giftCertificateMapper.toDTO(giftInserted)).thenReturn(giftInsertedDTO);
            // THEN
            assertEquals(giftInsertedDTO, giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }

        @Test
        @DisplayName("gift certificate with existing tags correctly inserted")
        void createGiftCertificateShouldInsertNewGiftCertificateWithExistingTagsAndReturnInsertedGiftCertificate() {
            // GIVEN
            List<Tag> tags = setUpTags(Arrays.asList("cinema", "date", "movie"), false);
            GiftCertificate giftToBeInserted = new GiftCertificate(0, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                    LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
            GiftCertificateDTO giftToBeInsertedDTO = new GiftCertificateDTO(0, "Movie night",
                    "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", tags);
            giftToBeInsertedDTO.setTags(tags);
            List<Tag> tagsInserted = setUpTags(Arrays.asList("cinema", "date", "movie"), true);
            GiftCertificate giftInserted = new GiftCertificate(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                    LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
            GiftCertificateDTO giftInsertedDTO = new GiftCertificateDTO(3, "Movie night",
                    "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", tagsInserted);
            giftInsertedDTO.setTags(tagsInserted);
            Tag existingInDbMovie = new Tag(29, "movie");
            Tag existingInDbDate = new Tag(7, "date");
            // WHEN
            when(giftCertificateMapper.toModel(giftToBeInsertedDTO)).thenReturn(giftToBeInserted);
            when(tagDAO.findAll()).thenReturn(Arrays.asList(existingInDbMovie, existingInDbDate));
            when(giftCertificateDAO.createGiftCertificate(giftToBeInserted)).thenReturn(giftInserted);
            when(tagDAO.createTag(new Tag(0, "cinema"))).thenReturn(new Tag(45, "cinema"));
            when(tagDAO.findByName("date")).thenReturn(existingInDbDate);
            when(tagDAO.findByName("movie")).thenReturn(existingInDbMovie);
            when(giftCertificateTagDAO.createGiftCertificateTag(new GiftCertificateTag(3, 45)))
                    .thenReturn(new GiftCertificateTag(1, 3, 1));
            when(giftCertificateTagDAO.createGiftCertificateTag(new GiftCertificateTag(3, 7)))
                    .thenReturn(new GiftCertificateTag(2, 3, 2));
            when(giftCertificateTagDAO.createGiftCertificateTag(new GiftCertificateTag(3, 29)))
                    .thenReturn(new GiftCertificateTag(3, 3, 3));
            when(tagDAO.findTagsByGiftCertificateId(giftInserted.getId())).thenReturn(tagsInserted);
            when(giftCertificateMapper.toDTO(giftInserted)).thenReturn(giftInsertedDTO);
            // THEN
            assertEquals(giftInsertedDTO, giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }

        @Test
        @DisplayName("create gift certificate with invalid input")
        void createGiftCertificateWithInvalidInputShouldThrowException() {
            // GIVEN
            String name = null;
            GiftCertificateDTO giftToBeInsertedDTO = new GiftCertificateDTO(0, name, "Movie session in Cinema City",
                    15.00, Duration.ofDays(200), "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }


        @Test
        @DisplayName("create gift certificate with empty input")
        void createGiftCertificateWithEmptyInputShouldThrowException() {
            // GIVEN
            GiftCertificateDTO giftToBeInsertedDTO = new GiftCertificateDTO(0, "Movie night", "   ", 15.00,
                    Duration.ofDays(200), "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeInsertedDTO));
        }
    }

    @Nested
    @DisplayName("update gift certificate tests")
    class updateGiftCertificateTests {

        @Test
        @DisplayName("gift certificate correctly updated")
        void updateGiftCertificateShouldCorrectlyUpdateSpecifiedParam() {
            // GIVEN
            GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(3, "Movie night",
                    "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
            GiftCertificate giftCertificate = new GiftCertificate(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                    LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
            // WHEN
            when(giftCertificateMapper.toModel(giftCertificateDTO)).thenReturn(giftCertificate);
            doThrow(new EmptyResultDataAccessException(0)).when(giftCertificateDAO).updateGiftCertificate(giftCertificate);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateService.updateGiftCertificate(giftCertificateDTO));
        }

        @Test
        @DisplayName("update non existing gift certificate")
        void updateNonExistingGiftCertificateShouldTrowException() {
            GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(3, "Movie night",
                    "Movie session in Cinema City", 15.00, Duration.ofDays(200),
                    "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
            GiftCertificate giftCertificate = new GiftCertificate(3, "Movie night",
                    "Movie session in Cinema City", BigDecimal.valueOf(15.00), Duration.ofDays(200),
                    LocalDateTime.parse("2022-03-18T12:24:47.241"), LocalDateTime.parse("2022-06-18T12:24:47.241"));
            // WHEN
            when(giftCertificateMapper.toModel(giftCertificateDTO)).thenReturn(giftCertificate);
            doThrow(new EmptyResultDataAccessException(0)).when(giftCertificateDAO).updateGiftCertificate(giftCertificate);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateService.updateGiftCertificate(giftCertificateDTO));
        }

        @Test
        @DisplayName("update gift certificate with invalid input")
        void updateGiftCertificateWithInvalidInputShouldTrowException() {
            // GIVEN
            String name = null;
            GiftCertificateDTO giftToBeUpdatedDTO = new GiftCertificateDTO(0, name, "Movie session in Cinema City",
                    15.00, Duration.ofDays(200), "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeUpdatedDTO));
        }

        @Test
        @DisplayName("update gift certificate with empty input")
        void updateGiftCertificateWithEmptyInputShouldTrowException() {
            // GIVEN
            GiftCertificateDTO giftToBeUpdatedDTO = new GiftCertificateDTO(0, "Movie night", "     ",
                    15.00, Duration.ofDays(200), "2022-03-18T12:24:47.241", "2022-06-18T12:24:47.241", new ArrayList<>());
            // WHEN

            // THEN
            assertThrows(InvalidInputException.class, () -> giftCertificateService.addGiftCertificate(giftToBeUpdatedDTO));
        }
    }

    @Nested
    @DisplayName("delete gift certificate tests")
    class deleteGiftCertificateTests {

        @Test
        @DisplayName("gift certificate correctly removed")
        void deleteGiftCertificateShouldCorrectlyRemoveGiftCertificate() {
            // GIVEN
            int giftCertificateIdToDelete = 5;
            // WHEN
            giftCertificateService.deleteGiftCertificate(giftCertificateIdToDelete);
            // THEN
            verify(giftCertificateDAO, times(1)).deleteGiftCertificate(giftCertificateIdToDelete);
        }

        @Test
        @DisplayName("delete non existing gift certificate")
        void deleteNonExistingGiftCertificateShouldThrowException() {
            // GIVEN
            int giftCertificateIdToDelete = 5;
            // WHEN
            doThrow(new EmptyResultDataAccessException(0)).when(giftCertificateDAO).deleteGiftCertificate(giftCertificateIdToDelete);
            // THEN
            assertThrows(EmptyResultDataAccessException.class, () -> giftCertificateService.deleteGiftCertificate(giftCertificateIdToDelete));
        }
    }

    private List<Tag> setUpTags(List<String> tagsNames, boolean isInserted) {
        List<Tag> tags = new ArrayList<>();
        int id = 1;
        for (String name : tagsNames) {
            Tag tag = new Tag();
            tag.setName(name);
            if (isInserted) {
                tag.setId(id);
                id++;
            }
            tags.add(tag);
        }
        return tags;
    }
}
