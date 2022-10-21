package HubertRoszyk.company.converters.serialize;

import HubertRoszyk.company.enumTypes.ShipType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ShipTypeSerialize extends StdSerializer<ShipType> {
    public ShipTypeSerialize(){
        super(ShipType.class);
    }
    @Override
    public void serialize(ShipType shipType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(shipType.name());
        jsonGenerator.writeFieldName("shipPrice");
        jsonGenerator.writeNumber(shipType.getShipPrice());
        jsonGenerator.writeFieldName("constructionCycles");
        jsonGenerator.writeNumber(shipType.getConstructionCycles());
        jsonGenerator.writeFieldName("capacity");
        jsonGenerator.writeNumber(shipType.getCapacity());
        jsonGenerator.writeFieldName("speed");
        jsonGenerator.writeNumber(shipType.getSpeed());
        jsonGenerator.writeFieldName("levelNums");
        jsonGenerator.writeNumber(shipType.getLevelNums());
        jsonGenerator.writeEndObject();
    }
}
