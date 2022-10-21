package HubertRoszyk.company.converters.serialize;

import HubertRoszyk.company.enumTypes.BuildingType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class BuildingTypeSerialize extends StdSerializer<BuildingType> {
    public BuildingTypeSerialize() {
        super(BuildingType.class);
    }
    @Override
    public void serialize(BuildingType buildingType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(buildingType.name());
        jsonGenerator.writeFieldName("levelNums");
        jsonGenerator.writeNumber(buildingType.getLevelNums());
        jsonGenerator.writeFieldName("buildingPrice");
        jsonGenerator.writeNumber(buildingType.getBuildingPrice());
        jsonGenerator.writeFieldName("volume");
        jsonGenerator.writeNumber(buildingType.getVolume());
        jsonGenerator.writeFieldName("constructionCycles");
        jsonGenerator.writeNumber(buildingType.getConstructionCycles());
        jsonGenerator.writeEndObject();
    }
}
