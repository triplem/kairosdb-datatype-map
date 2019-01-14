package org.javafreedom.kairosdb;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.kairosdb.core.datapoints.DataPointHelper;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

public class MapDatapoint extends DataPointHelper {

    private static final String API_TYPE = "map";
    private static final String DST_TYPE = "map";


    private Double value;
    private Map<String, Object> fields;

    public MapDatapoint(Long time, Double value,
                        Map<String, Object> fields) {
        super(time);
        this.value = value;
        this.fields = fields;
    }

    @Override
    public void writeValueToJson(JSONWriter writer) throws JSONException {
        writer.object();

        writer.key("value").value(value);
        writer.key("fields").value(fields);

        writer.endObject();
    }

    @Override
    public void writeValueToBuffer(DataOutput buffer) throws IOException {
        buffer.writeDouble(value);

        JSONObject fieldsAsJson = new JSONObject(fields);
        try {
            buffer.writeBytes(fieldsAsJson.toString(0));
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof MapDatapoint)) return false;

        MapDatapoint dp = (MapDatapoint)other;

        return this.getTimestamp() == dp.getTimestamp();
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public double getDoubleValue() {
        return value;
    }

    @Override
    public boolean isLong() {
        return true;
    }

    @Override
    public long getLongValue() {
        return Double.valueOf(value).longValue();
    }

    @Override
    public String getApiDataType() {
        return API_TYPE;
    }

    @Override
    public String getDataStoreDataType() {
        return DST_TYPE;
    }

    public Double getValue() {
        return value;
    }

    public Map<String, Object> getFields() {
        return fields;
    }
}
