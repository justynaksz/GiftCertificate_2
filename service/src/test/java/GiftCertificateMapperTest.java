import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

class GiftCertificateMapperTest {

    private GiftCertificateMapper giftCertificateMapper;
    private TagMapper tagMapper;
    private GiftCertificate giftCertificate;
    private GiftCertificateDTO giftCertificateDTO;
    private Tag shopping;
    private Tag food;
    private TagDTO shoppingDTO;
    private TagDTO foodDTO;

    private final SoftAssertions softAssertions = new SoftAssertions();

    private final Integer id = 1;
    private final String name = "Aquapark Fun";
    private final String description = "2 hours of paintball match in Paintball-World";
    private final BigDecimal price = BigDecimal.valueOf(49.99);
    private final Integer duration = 180;
    private final String createDate = "2022-03-18T12:24:47.241";
    private final String lastUpdateDate = "2022-06-28T09:28:57.241";
    private Set<Tag> tags;
    private Set<TagDTO> tagsDTO;


    @BeforeEach
    void initEach() {
        tags = new HashSet<>();
        tagsDTO = new HashSet<>();
        tagMapper = new TagMapper();
        giftCertificateMapper = new GiftCertificateMapper(tagMapper);
    }


    @Test
    @DisplayName("to DTO test")
    void toDTOShouldReturnCorrectlyMigratedGiftCertificateDTO() {
        // GIVEN
        shopping = new Tag(1, "shopping");
        food = new Tag(2, "food");
        shoppingDTO = new TagDTO(1, "shopping");
        foodDTO = new TagDTO(2, "food");
        tags.add(shopping);
        tags.add(food);
        tagsDTO.add(shoppingDTO);
        tagsDTO.add(foodDTO);
        // WHEN
        giftCertificate = new GiftCertificate(id, name, description, price, duration, LocalDateTime.parse(createDate),
                LocalDateTime.parse(lastUpdateDate), tags);
        giftCertificateDTO = new GiftCertificateDTO(id, name, description, price, duration, createDate,
                lastUpdateDate, tagsDTO);
        // THEN
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getId()).isEqualTo(giftCertificateDTO.getId());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getName()).isEqualTo(giftCertificateDTO.getName());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getDescription()).isEqualTo(giftCertificateDTO.getDescription());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getDuration()).isEqualTo(giftCertificateDTO.getDuration());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getPrice()).isEqualTo(giftCertificateDTO.getPrice());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getCreateDate()).isEqualTo(giftCertificateDTO.getCreateDate());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getLastUpdateDate()).isEqualTo(giftCertificateDTO.getLastUpdateDate());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getTags()).isEqualTo(giftCertificateDTO.getTags());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("to model test")
    void toModelShouldReturnCorrectlyMigratedGiftCertificate() {
        // GIVEN
        shopping = new Tag(1, "shopping");
        food = new Tag(2, "food");
        shoppingDTO = new TagDTO(1, "shopping");
        foodDTO = new TagDTO(2, "food");
        tags.add(shopping);
        tags.add(food);
        tagsDTO.add(shoppingDTO);
        tagsDTO.add(foodDTO);
        // WHEN
        giftCertificate = new GiftCertificate(id, name, description, price, duration, LocalDateTime.parse(createDate),
                LocalDateTime.parse(lastUpdateDate), tags);
        giftCertificateDTO = new GiftCertificateDTO(id, name, description, price, duration, createDate,
                lastUpdateDate, tagsDTO);
        // THEN
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getId()).isEqualTo(giftCertificate.getId());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getName()).isEqualTo(giftCertificate.getName());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getDescription()).isEqualTo(giftCertificate.getDescription());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getDuration()).isEqualTo(giftCertificate.getDuration());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getPrice()).isEqualTo(giftCertificate.getPrice());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getCreateDate()).isEqualTo(giftCertificate.getCreateDate());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getLastUpdateDate()).isEqualTo(giftCertificate.getLastUpdateDate());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getTags()).isEqualTo(giftCertificate.getTags());
        softAssertions.assertAll();
    }
}
