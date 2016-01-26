package com.twilio.sdk.resources.taskrouter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.converters.MarshalConverter;
import com.twilio.sdk.creators.taskrouter.ActivityCreator;
import com.twilio.sdk.deleters.taskrouter.ActivityDeleter;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.fetchers.taskrouter.ActivityFetcher;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.readers.taskrouter.ActivityReader;
import com.twilio.sdk.resources.Resource;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.SidResource;
import com.twilio.sdk.updaters.taskrouter.ActivityUpdater;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity extends SidResource {
    private static final long serialVersionUID = 22270788626396L;

    /**
     * fetch
     * 
     * @param workspaceSid The workspace_sid
     * @param sid The sid
     * @return ActivityFetcher capable of executing the fetch
     */
    public static ActivityFetcher fetch(final String workspaceSid, final String sid) {
        return new ActivityFetcher(workspaceSid, sid);
    }

    /**
     * update
     * 
     * @param workspaceSid The workspace_sid
     * @param sid The sid
     * @param friendlyName The friendly_name
     * @return ActivityUpdater capable of executing the update
     */
    public static ActivityUpdater update(final String workspaceSid, final String sid, final String friendlyName) {
        return new ActivityUpdater(workspaceSid, sid, friendlyName);
    }

    /**
     * delete
     * 
     * @param workspaceSid The workspace_sid
     * @param sid The sid
     * @return ActivityDeleter capable of executing the delete
     */
    public static ActivityDeleter delete(final String workspaceSid, final String sid) {
        return new ActivityDeleter(workspaceSid, sid);
    }

    /**
     * read
     * 
     * @param workspaceSid The workspace_sid
     * @return ActivityReader capable of executing the read
     */
    public static ActivityReader read(final String workspaceSid) {
        return new ActivityReader(workspaceSid);
    }

    /**
     * create
     * 
     * @param workspaceSid The workspace_sid
     * @param friendlyName The friendly_name
     * @param available The available
     * @return ActivityCreator capable of executing the create
     */
    public static ActivityCreator create(final String workspaceSid, final String friendlyName, final Boolean available) {
        return new ActivityCreator(workspaceSid, friendlyName, available);
    }

    /**
     * Converts a JSON String into a Activity object using the provided ObjectMapper
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return Activity object represented by the provided JSON
     */
    public static Activity fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Activity.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a Activity object using the provided
     * ObjectMapper
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Activity object represented by the provided JSON
     */
    public static Activity fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Activity.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final String accountSid;
    private final Boolean available;
    private final DateTime dateCreated;
    private final DateTime dateUpdated;
    private final String friendlyName;
    private final String sid;
    private final String workspaceSid;

    @JsonCreator
    private Activity(@JsonProperty("account_sid") final String accountSid, 
                     @JsonProperty("available") final Boolean available, 
                     @JsonProperty("date_created") final String dateCreated, 
                     @JsonProperty("date_updated") final String dateUpdated, 
                     @JsonProperty("friendly_name") final String friendlyName, 
                     @JsonProperty("sid") final String sid, 
                     @JsonProperty("workspace_sid") final String workspaceSid) {
        this.accountSid = accountSid;
        this.available = available;
        this.dateCreated = MarshalConverter.dateTimeFromString(dateCreated);
        this.dateUpdated = MarshalConverter.dateTimeFromString(dateUpdated);
        this.friendlyName = friendlyName;
        this.sid = sid;
        this.workspaceSid = workspaceSid;
    }

    /**
     * @return The account_sid
     */
    public final String getAccountSid() {
        return this.accountSid;
    }

    /**
     * @return The available
     */
    public final Boolean getAvailable() {
        return this.available;
    }

    /**
     * @return The date_created
     */
    public final DateTime getDateCreated() {
        return this.dateCreated;
    }

    /**
     * @return The date_updated
     */
    public final DateTime getDateUpdated() {
        return this.dateUpdated;
    }

    /**
     * @return The friendly_name
     */
    public final String getFriendlyName() {
        return this.friendlyName;
    }

    /**
     * @return The sid
     */
    public final String getSid() {
        return this.sid;
    }

    /**
     * @return The workspace_sid
     */
    public final String getWorkspaceSid() {
        return this.workspaceSid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Activity other = (Activity) o;
        
        return Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(available, other.available) && 
               Objects.equals(dateCreated, other.dateCreated) && 
               Objects.equals(dateUpdated, other.dateUpdated) && 
               Objects.equals(friendlyName, other.friendlyName) && 
               Objects.equals(sid, other.sid) && 
               Objects.equals(workspaceSid, other.workspaceSid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountSid,
                            available,
                            dateCreated,
                            dateUpdated,
                            friendlyName,
                            sid,
                            workspaceSid);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("accountSid", accountSid)
                          .add("available", available)
                          .add("dateCreated", dateCreated)
                          .add("dateUpdated", dateUpdated)
                          .add("friendlyName", friendlyName)
                          .add("sid", sid)
                          .add("workspaceSid", workspaceSid)
                          .toString();
    }
}