package deserializers;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import values.Activity;
import values.Observation;

public class ActivityDeserializer implements JsonDeserializer<Activity> {

	public Activity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		final JsonObject jsonObject = json.getAsJsonObject();

		final String content = jsonObject.get("content").getAsString();
		final String start = jsonObject.get("start").getAsString();
		final String end = jsonObject.get("end").getAsString();

		final Observation[] observations = context.deserialize(jsonObject.get("observations"), Observation[].class);

		final Activity activity = new Activity();
		activity.setContent(content);
		activity.setStart(LocalDateTime.parse(start));
		activity.setEnd(LocalDateTime.parse(end));
		activity.setObservations(observations);
		return activity;

	}

}
