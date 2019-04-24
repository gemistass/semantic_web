package deserializers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import values.Activity;
import values.SModel;

public class ModelDeserializer implements JsonDeserializer<SModel> {

	public SModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		JsonObject modelObj = jsonObject.get("model").getAsJsonObject();
		Activity[] activities = context.deserialize(modelObj.get("activities"), Activity[].class);
//		

		SModel model = new SModel();
		model.setActivities(activities);
		return model;
	}

}
