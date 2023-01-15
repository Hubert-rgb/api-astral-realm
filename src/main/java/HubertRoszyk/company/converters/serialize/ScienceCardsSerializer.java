package HubertRoszyk.company.converters.serialize;

import HubertRoszyk.company.enumTypes.cardsType.ScienceCardType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ScienceCardsSerializer extends StdSerializer<ScienceCardType> {
    public ScienceCardsSerializer(){
        super(ScienceCardType.class);
    }
    @Override
    public void serialize(ScienceCardType scienceCardType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(scienceCardType.name());
        jsonGenerator.writeFieldName("price");
        jsonGenerator.writeNumber(scienceCardType.getPrice());
        jsonGenerator.writeFieldName("cardType");
        jsonGenerator.writeString(scienceCardType.getCardType().toString());
        jsonGenerator.writeFieldName("militaryCardsRequired");
        jsonGenerator.writeNumber(scienceCardType.getMilitaryCardsRequired());
        jsonGenerator.writeFieldName("economyCardsRequired");
        jsonGenerator.writeNumber(scienceCardType.getEconomyCardsRequired());
        jsonGenerator.writeFieldName("politicalCardsRequired");
        jsonGenerator.writeNumber(scienceCardType.getPoliticalCardsRequired());
        jsonGenerator.writeEndObject();
    }
}
