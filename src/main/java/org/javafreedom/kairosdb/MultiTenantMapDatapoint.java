package org.javafreedom.kairosdb;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.kairosdb.core.datapoints.DataPointHelper;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

public class MultiTenantMapDatapoint extends MapDatapoint {

    private static final String API_TYPE = "multi-tenant-map";
    private static final String DST_TYPE = "multi-tenant-map";

    private String user;

    public MultiTenantMapDatapoint(Long time, Double value,
                                   String user, Map<String, Object> fields) {
        super(time, value, fields);
        this.user = user;
    }

    @Override
    public void writeValueToJson(JSONWriter writer) throws JSONException {
        writer.object();

        writer.key("value").value(this.getValue());
        writer.key("user").value(user);
        writer.key("fields").value(this.getFields());

        writer.endObject();
    }

    @Override
    public void writeValueToBuffer(DataOutput buffer) throws IOException {
        buffer.writeDouble(this.getValue());
        buffer.writeBytes(user);

        JSONObject fieldsAsJson = new JSONObject(this.getFields());
        try {
            buffer.writeBytes(fieldsAsJson.toString(0));
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof MultiTenantMapDatapoint)) return false;

        MultiTenantMapDatapoint dp = (MultiTenantMapDatapoint)other;

        return this.getTimestamp() == dp.getTimestamp()
            && user.equals(dp.getUser());
    }

    @Override
    public String getApiDataType() {
        return API_TYPE;
    }

    @Override
    public String getDataStoreDataType() {
        return DST_TYPE;
    }

    public String getUser() {
        return user;
    }
}
