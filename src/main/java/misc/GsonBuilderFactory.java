package misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import deserializers.ActivityDeserializer;
import deserializers.ModelDeserializer;
import deserializers.ObservationDeserializer;
import values.Activity;
import values.SModel;
import values.Observation;

public class GsonBuilderFactory {

	public Gson createBuilder() {
		final GsonBuilder gsonbuilder = new GsonBuilder();
		gsonbuilder.registerTypeAdapter(Observation.class, new ObservationDeserializer());
		gsonbuilder.registerTypeAdapter(Activity.class, new ActivityDeserializer());
		gsonbuilder.registerTypeAdapter(SModel.class, new ModelDeserializer());
		final Gson gson = gsonbuilder.create();
		return gson;

	}

}
