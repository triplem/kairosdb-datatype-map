package org.javafreedom.kairosdb;

import com.google.gson.*;
import org.kairosdb.core.DataPoint;
import org.kairosdb.core.datapoints.DataPointFactory;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;

public class MultiTenantMapDatapointFactory extends MapDatapointFactory {

    @Override
    public DataPoint getDataPoint(long timestamp, JsonElement json) throws IOException {
        System.out.println("json: " + json);
        if (json.isJsonObject()) {
            JsonObject obj = (JsonObject) json;
            double value = obj.get("value").getAsDouble();
            String user = obj.get("user").getAsString();
            Map<String, Object> fields = jsonToMap(obj.get("fields").getAsJsonObject());

            return new MultiTenantMapDatapoint(timestamp, value, user, fields);
        } else {
            throw new IOException("Given json is not a valid multi-field map datapoint");
        }
    }

    @Override
    public DataPoint getDataPoint(long timestamp, DataInput buffer) throws IOException {
        double value = buffer.readDouble();
        String user = buffer.readUTF();
        String fieldString = buffer.readUTF();

        Map<String, Object> fields = jsonToMap(new JsonPrimitive(fieldString).getAsJsonObject());

        return new MultiTenantMapDatapoint(timestamp, value, user, fields);
    }

    protected Map<String, Object> jsonToMap(JsonObject fields) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(fields, Map.class);
    }
}
