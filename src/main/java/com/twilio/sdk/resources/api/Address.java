package com.twilio.sdk.resources.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;
import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.converters.MarshalConverter;
import com.twilio.sdk.creators.api.AddressCreator;
import com.twilio.sdk.deleters.api.AddressDeleter;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.fetchers.api.AddressFetcher;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.readers.api.AddressReader;
import com.twilio.sdk.resources.Resource;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.SidResource;
import com.twilio.sdk.updaters.api.AddressUpdater;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address extends SidResource {
    private static final long serialVersionUID = 241863313934407L;

    /**
     * create
     * 
     * @param accountSid The account_sid
     * @param customerName The customer_name
     * @param street The street
     * @param city The city
     * @param region The region
     * @param postalCode The postal_code
     * @param isoCountry The iso_country
     * @return AddressCreator capable of executing the create
     */
    public static AddressCreator create(final String accountSid, final String customerName, final String street, final String city, final String region, final String postalCode, final String isoCountry) {
        return new AddressCreator(accountSid, customerName, street, city, region, postalCode, isoCountry);
    }

    /**
     * delete
     * 
     * @param accountSid The account_sid
     * @param sid The sid
     * @return AddressDeleter capable of executing the delete
     */
    public static AddressDeleter delete(final String accountSid, final String sid) {
        return new AddressDeleter(accountSid, sid);
    }

    /**
     * fetch
     * 
     * @param accountSid The account_sid
     * @param sid The sid
     * @return AddressFetcher capable of executing the fetch
     */
    public static AddressFetcher fetch(final String accountSid, final String sid) {
        return new AddressFetcher(accountSid, sid);
    }

    /**
     * update
     * 
     * @param accountSid The account_sid
     * @param sid The sid
     * @return AddressUpdater capable of executing the update
     */
    public static AddressUpdater update(final String accountSid, final String sid) {
        return new AddressUpdater(accountSid, sid);
    }

    /**
     * read
     * 
     * @param accountSid The account_sid
     * @return AddressReader capable of executing the read
     */
    public static AddressReader read(final String accountSid) {
        return new AddressReader(accountSid);
    }

    /**
     * Converts a JSON String into a Address object using the provided ObjectMapper
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return Address object represented by the provided JSON
     */
    public static Address fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Address.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a Address object using the provided
     * ObjectMapper
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return Address object represented by the provided JSON
     */
    public static Address fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, Address.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final String accountSid;
    private final String city;
    private final String customerName;
    private final DateTime dateCreated;
    private final DateTime dateUpdated;
    private final String friendlyName;
    private final String isoCountry;
    private final String postalCode;
    private final String region;
    private final String sid;
    private final String street;
    private final String uri;

    @JsonCreator
    private Address(@JsonProperty("account_sid") final String accountSid, 
                    @JsonProperty("city") final String city, 
                    @JsonProperty("customer_name") final String customerName, 
                    @JsonProperty("date_created") final String dateCreated, 
                    @JsonProperty("date_updated") final String dateUpdated, 
                    @JsonProperty("friendly_name") final String friendlyName, 
                    @JsonProperty("iso_country") final String isoCountry, 
                    @JsonProperty("postal_code") final String postalCode, 
                    @JsonProperty("region") final String region, 
                    @JsonProperty("sid") final String sid, 
                    @JsonProperty("street") final String street, 
                    @JsonProperty("uri") final String uri) {
        this.accountSid = accountSid;
        this.city = city;
        this.customerName = customerName;
        this.dateCreated = MarshalConverter.dateTimeFromString(dateCreated);
        this.dateUpdated = MarshalConverter.dateTimeFromString(dateUpdated);
        this.friendlyName = friendlyName;
        this.isoCountry = isoCountry;
        this.postalCode = postalCode;
        this.region = region;
        this.sid = sid;
        this.street = street;
        this.uri = uri;
    }

    /**
     * @return The account_sid
     */
    public final String getAccountSid() {
        return this.accountSid;
    }

    /**
     * @return The city
     */
    public final String getCity() {
        return this.city;
    }

    /**
     * @return The customer_name
     */
    public final String getCustomerName() {
        return this.customerName;
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
     * @return The iso_country
     */
    public final String getIsoCountry() {
        return this.isoCountry;
    }

    /**
     * @return The postal_code
     */
    public final String getPostalCode() {
        return this.postalCode;
    }

    /**
     * @return The region
     */
    public final String getRegion() {
        return this.region;
    }

    /**
     * @return The sid
     */
    public final String getSid() {
        return this.sid;
    }

    /**
     * @return The street
     */
    public final String getStreet() {
        return this.street;
    }

    /**
     * @return The uri
     */
    public final String getUri() {
        return this.uri;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Address other = (Address) o;
        
        return Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(city, other.city) && 
               Objects.equals(customerName, other.customerName) && 
               Objects.equals(dateCreated, other.dateCreated) && 
               Objects.equals(dateUpdated, other.dateUpdated) && 
               Objects.equals(friendlyName, other.friendlyName) && 
               Objects.equals(isoCountry, other.isoCountry) && 
               Objects.equals(postalCode, other.postalCode) && 
               Objects.equals(region, other.region) && 
               Objects.equals(sid, other.sid) && 
               Objects.equals(street, other.street) && 
               Objects.equals(uri, other.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountSid,
                            city,
                            customerName,
                            dateCreated,
                            dateUpdated,
                            friendlyName,
                            isoCountry,
                            postalCode,
                            region,
                            sid,
                            street,
                            uri);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("accountSid", accountSid)
                          .add("city", city)
                          .add("customerName", customerName)
                          .add("dateCreated", dateCreated)
                          .add("dateUpdated", dateUpdated)
                          .add("friendlyName", friendlyName)
                          .add("isoCountry", isoCountry)
                          .add("postalCode", postalCode)
                          .add("region", region)
                          .add("sid", sid)
                          .add("street", street)
                          .add("uri", uri)
                          .toString();
    }
}