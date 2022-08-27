import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class GiftCertificateMapperTest {

    private GiftCertificateMapper giftCertificateMapper;
    private GiftCertificate giftCertificate;
    private GiftCertificateDTO giftCertificateDTO;

    private final SoftAssertions softAssertions = new SoftAssertions();

    private final int id = 1;
    private final String name = "Aquapark Fun";
    private final String description = "2 hours of paintball match in Paintball-World";
    private final double price = 49.99;
    private final long duration = 180;
    private final String createDate = "2022-03-18T12:24:47.241";
    private final String lastUpdateDate = "2022-06-28T09:28:57.241";

    @BeforeEach
    void initEach() {
        giftCertificateMapper = new GiftCertificateMapper();
        giftCertificate = new GiftCertificate();
        giftCertificateDTO = new GiftCertificateDTO();
    }

    @Test
    @DisplayName("to DTO test")
    void toDTOShouldReturnCorrectlyMigratedGiftCertificateDTO() {
        // GIVEN

        // WHEN
        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setDuration(duration);
        giftCertificate.setCreateDate(LocalDateTime.parse(createDate));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse(lastUpdateDate));
        giftCertificateDTO.setId(id);
        giftCertificateDTO.setName(name);
        giftCertificateDTO.setDescription(description);
        giftCertificateDTO.setPrice(price);
        giftCertificateDTO.setDuration(duration);
        giftCertificateDTO.setCreateDate(createDate);
        giftCertificateDTO.setLastUpdateDate(lastUpdateDate);
        // THEN
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getId()).isEqualTo(giftCertificateDTO.getId());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getName()).isEqualTo(giftCertificateDTO.getName());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getDescription()).isEqualTo(giftCertificateDTO.getDescription());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getDuration()).isEqualTo(giftCertificateDTO.getDuration());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getPrice()).isEqualTo(giftCertificateDTO.getPrice());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getCreateDate()).isEqualTo(giftCertificateDTO.getCreateDate());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getLastUpdateDate()).isEqualTo(giftCertificateDTO.getLastUpdateDate());
        softAssertions.assertThat(giftCertificateMapper.toDTO(giftCertificate).getTags()).isEmpty();
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("to model test")
    void toModelShouldReturnCorrectlyMigratedGiftCertificate() {
        // GIVEN

        // WHEN
        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setDuration(duration);
        giftCertificate.setCreateDate(LocalDateTime.parse(createDate));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse(lastUpdateDate));
        giftCertificateDTO.setId(id);
        giftCertificateDTO.setName(name);
        giftCertificateDTO.setDescription(description);
        giftCertificateDTO.setPrice(price);
        giftCertificateDTO.setDuration(duration);
        giftCertificateDTO.setCreateDate(createDate);
        giftCertificateDTO.setLastUpdateDate(lastUpdateDate);
        // THEN
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getId()).isEqualTo(giftCertificate.getId());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getName()).isEqualTo(giftCertificate.getName());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getDescription()).isEqualTo(giftCertificate.getDescription());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getDuration()).isEqualTo(giftCertificate.getDuration());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getPrice()).isEqualTo(giftCertificate.getPrice());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getCreateDate()).isEqualTo(giftCertificate.getCreateDate());
        softAssertions.assertThat(giftCertificateMapper.toModel(giftCertificateDTO).getLastUpdateDate()).isEqualTo(giftCertificate.getLastUpdateDate());
        softAssertions.assertAll();
    }
}
