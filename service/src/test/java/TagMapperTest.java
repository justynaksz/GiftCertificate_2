import com.epam.esm.dto.TagDTO;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TagMapperTest {

    private final SoftAssertions softAssertions = new SoftAssertions();

    @Test
    @DisplayName("to DTO test")
    void toDTOTest() {
        // GIVEN
        var id = 1;
        var name = "sport";
        // WHEN
        var tagMapper = new TagMapper();
        var tag = new Tag(id, name);
        var tagDTO = new TagDTO(id, name);
        // THEN
        softAssertions.assertThat(tagMapper.toDTO(tag).getId()).isEqualTo(tagDTO.getId());
        softAssertions.assertThat(tagMapper.toDTO(tag).getName()).isEqualTo(tagDTO.getName());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("to model test")
    void toModelTest() {
        // GIVEN
        var id = 1;
        var name = "sport";
        // WHEN
        var tagMapper = new TagMapper();
        var tag = new Tag(id, name);
        var tagDTO = new TagDTO(id, name);
        // THEN
        softAssertions.assertThat(tagMapper.toModel(tagDTO).getId()).isEqualTo(tag.getId());
        softAssertions.assertThat(tagMapper.toModel(tagDTO).getName()).isEqualTo(tag.getName());
        softAssertions.assertAll();
    }
}
