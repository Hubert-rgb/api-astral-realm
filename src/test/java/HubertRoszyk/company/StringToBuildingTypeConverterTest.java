package HubertRoszyk.company;

import HubertRoszyk.company.converters.StringToBuildingsTypeConverter;
import HubertRoszyk.company.enumTypes.BuildingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class StringToBuildingTypeConverterTest {

    StringToBuildingsTypeConverter underTest;

    @BeforeEach
    void setUp () {
        underTest = new StringToBuildingsTypeConverter();
    }

    @Test
    void shouldConvert() {
        //given
        String stringValue = "DEFENSE";
        //when
        BuildingType buildingType = underTest.convert(stringValue);
        //then
        assertThat(buildingType).isEqualTo(BuildingType.DEFENSE);
    }
}