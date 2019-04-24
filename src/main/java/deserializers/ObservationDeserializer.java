package deserializers;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import values.Observation;

public class ObservationDeserializer implements JsonDeserializer<Observation> {

	public Observation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		final JsonObject jsonObject = json.getAsJsonObject();
		final Observation observation = new Observation();
		
		final String start = jsonObject.get("start").getAsString();
		final String end = jsonObject.get("end").getAsString();
		
		observation.setContent(jsonObject.get("content").getAsString());
		observation.setStart(LocalDateTime.parse(start));
		observation.setEnd(LocalDateTime.parse(end));
		
		return observation;
	}

}
