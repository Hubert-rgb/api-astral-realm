package HubertRoszyk.company.converters;

import HubertRoszyk.company.enumTypes.AttackType;
import HubertRoszyk.company.enumTypes.BuildingType;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class StringToAttackTypeConverter implements Converter<String, AttackType> {
    @Override
    public AttackType convert(String s) {
        return AttackType.valueOf(s);
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
