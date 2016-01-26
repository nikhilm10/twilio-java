package com.twilio.sdk.resources.monitor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.converters.MarshalConverter;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.fetchers.monitor.EventFetcher;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.readers.monitor.EventReader;
import com.twilio.sdk.resources.Resource;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.SidResource;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event extends SidResource {
    private static final long serialVersionUID = 235807442081136L;

    /**
     * fetch
     * 
     * @param sid The sid
     * @return EventFetcher capable of executing the fetch
     */
    public static EventFetcher fetch(final String sid) {
        return new EventFetcher(sid);
    }

    /**
     * read
     * 
     * @return EventReader capable of executing the read
     */
    public static EventReader read() {
        return new EventReader();
    }

    /**
     * Converts a JSON String into a Event object using the provided ObjectMapper
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return Event object represented by the provided JSON
     */
    public static Event fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Event.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a Event object using the provided
     * ObjectMapper
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Event object represented by the provided JSON
     */
    public static Event fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Event.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final String accountSid;
    private final String actorSid;
    private final String actorType;
    private final String description;
    private final String eventData;
    private final DateTime eventDate;
    private final String eventType;
    private final String resourceSid;
    private final String resourceType;
    private final String sid;
    private final String source;
    private final String sourceIpAddress;

    @JsonCreator
    private Event(@JsonProperty("account_sid") final String accountSid, 
                  @JsonProperty("actor_sid") final String actorSid, 
                  @JsonProperty("actor_type") final String actorType, 
                  @JsonProperty("description") final String description, 
                  @JsonProperty("event_data") final String eventData, 
                  @JsonProperty("event_date") final String eventDate, 
                  @JsonProperty("event_type") final String eventType, 
                  @JsonProperty("resource_sid") final String resourceSid, 
                  @JsonProperty("resource_type") final String resourceType, 
                  @JsonProperty("sid") final String sid, 
                  @JsonProperty("source") final String source, 
                  @JsonProperty("source_ip_address") final String sourceIpAddress) {
        this.accountSid = accountSid;
        this.actorSid = actorSid;
        this.actorType = actorType;
        this.description = description;
        this.eventData = eventData;
        this.eventDate = MarshalConverter.dateTimeFromString(eventDate);
        this.eventType = eventType;
        this.resourceSid = resourceSid;
        this.resourceType = resourceType;
        this.sid = sid;
        this.source = source;
        this.sourceIpAddress = sourceIpAddress;
    }

    /**
     * @return The account_sid
     */
    public final String getAccountSid() {
        return this.accountSid;
    }

    /**
     * @return The actor_sid
     */
    public final String getActorSid() {
        return this.actorSid;
    }

    /**
     * @return The actor_type
     */
    public final String getActorType() {
        return this.actorType;
    }

    /**
     * @return The description
     */
    public final String getDescription() {
        return this.description;
    }

    /**
     * @return The event_data
     */
    public final String getEventData() {
        return this.eventData;
    }

    /**
     * @return The event_date
     */
    public final DateTime getEventDate() {
        return this.eventDate;
    }

    /**
     * @return The event_type
     */
    public final String getEventType() {
        return this.eventType;
    }

    /**
     * @return The resource_sid
     */
    public final String getResourceSid() {
        return this.resourceSid;
    }

    /**
     * @return The resource_type
     */
    public final String getResourceType() {
        return this.resourceType;
    }

    /**
     * @return The sid
     */
    public final String getSid() {
        return this.sid;
    }

    /**
     * @return The source
     */
    public final String getSource() {
        return this.source;
    }

    /**
     * @return The source_ip_address
     */
    public final String getSourceIpAddress() {
        return this.sourceIpAddress;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Event other = (Event) o;
        
        return Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(actorSid, other.actorSid) && 
               Objects.equals(actorType, other.actorType) && 
               Objects.equals(description, other.description) && 
               Objects.equals(eventData, other.eventData) && 
               Objects.equals(eventDate, other.eventDate) && 
               Objects.equals(eventType, other.eventType) && 
               Objects.equals(resourceSid, other.resourceSid) && 
               Objects.equals(resourceType, other.resourceType) && 
               Objects.equals(sid, other.sid) && 
               Objects.equals(source, other.source) && 
               Objects.equals(sourceIpAddress, other.sourceIpAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountSid,
                            actorSid,
                            actorType,
                            description,
                            eventData,
                            eventDate,
                            eventType,
                            resourceSid,
                            resourceType,
                            sid,
                            source,
                            sourceIpAddress);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("accountSid", accountSid)
                          .add("actorSid", actorSid)
                          .add("actorType", actorType)
                          .add("description", description)
                          .add("eventData", eventData)
                          .add("eventDate", eventDate)
                          .add("eventType", eventType)
                          .add("resourceSid", resourceSid)
                          .add("resourceType", resourceType)
                          .add("sid", sid)
                          .add("source", source)
                          .add("sourceIpAddress", sourceIpAddress)
                          .toString();
    }
}