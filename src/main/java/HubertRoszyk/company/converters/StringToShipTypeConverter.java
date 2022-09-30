package HubertRoszyk.company.converters;

import HubertRoszyk.company.enumTypes.BuildingType;
import HubertRoszyk.company.enumTypes.ShipType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToShipTypeConverter implements Converter<String, ShipType> {
    @Override
    public ShipType convert(String source) {
        return ShipType.valueOf(source);
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
