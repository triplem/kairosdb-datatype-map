A dynamic datapoint for kairosdb

This datapoint provides flexilbility, in that it stores values in a Map (which is stored as a JSON Object in KairosDB).  Doing this could lead to poor performance (not tested yet), in that every datapoint has to get de-serialized on reads. 

To provide an easy way to implement multi-tenant stuff, the user is stored in the MultiTenantMapDatapoint as well, so that it can be used in the equals method. The user should get stored in Tags as well, so that corresponding datapoints can be retrieved faster, since the values stored ini this datapoint are not indexed.

publish:

gradle build bintrayUpload -PbintrayUser=<USER> -PbintrayApiKey=<API-KEY>

