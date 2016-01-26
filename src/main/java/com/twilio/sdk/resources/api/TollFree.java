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
import com.twilio.sdk.creators.api.TollFreeCreator;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.readers.api.TollFreeReader;
import com.twilio.sdk.resources.Resource;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.SidResource;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TollFree extends SidResource {
    private static final long serialVersionUID = 8405200578969L;

    public enum AddressRequirement {
        NONE("none"),
        ANY("any"),
        LOCAL("local"),
        FOREIGN("foreign");
    
        private final String value;
        
        private AddressRequirement(final String value) {
            this.value = value;
        }
        
        public String toString() {
            return value;
        }
        
        @JsonCreator
        public static AddressRequirement forValue(final String value) {
            String normalized = value.replace("-", "_").toUpperCase();
            return AddressRequirement.valueOf(normalized);
        }
    }

    /**
     * read
     * 
     * @param ownerAccountSid The owner_account_sid
     * @return TollFreeReader capable of executing the read
     */
    public static TollFreeReader read(final String ownerAccountSid) {
        return new TollFreeReader(ownerAccountSid);
    }

    /**
     * create
     * 
     * @param ownerAccountSid The owner_account_sid
     * @param phoneNumber The phone_number
     * @return TollFreeCreator capable of executing the create
     */
    public static TollFreeCreator create(final String ownerAccountSid, final String phoneNumber) {
        return new TollFreeCreator(ownerAccountSid, phoneNumber);
    }

    /**
     * Converts a JSON String into a TollFree object using the provided ObjectMapper
     * 
     * @param json Raw JSON String
     * @param objectMapper Jackson ObjectMapper
     * @return TollFree object represented by the provided JSON
     */
    public static TollFree fromJson(final String json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, TollFree.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    /**
     * Converts a JSON InputStream into a TollFree object using the provided
     * ObjectMapper
     * 
     * @param json Raw JSON InputStream
     * @param objectMapper Jackson ObjectMapper
     * @return TollFree object represented by the provided JSON
     */
    public static TollFree fromJson(final InputStream json, final ObjectMapper objectMapper) {
        // Convert all checked exceptions to Runtime
        try {
            return objectMapper.readValue(json, TollFree.class);
        } catch (final JsonMappingException | JsonParseException e) {
            throw new ApiException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new ApiConnectionException(e.getMessage(), e);
        }
    }

    private final String accountSid;
    private final TollFree.AddressRequirement addressRequirements;
    private final String apiVersion;
    private final Boolean beta;
    private final String capabilities;
    private final DateTime dateCreated;
    private final DateTime dateUpdated;
    private final String friendlyName;
    private final String phoneNumber;
    private final String sid;
    private final String smsApplicationSid;
    private final HttpMethod smsFallbackMethod;
    private final URI smsFallbackUrl;
    private final HttpMethod smsMethod;
    private final URI smsUrl;
    private final URI statusCallback;
    private final HttpMethod statusCallbackMethod;
    private final String uri;
    private final String voiceApplicationSid;
    private final Boolean voiceCallerIdLookup;
    private final HttpMethod voiceFallbackMethod;
    private final URI voiceFallbackUrl;
    private final HttpMethod voiceMethod;
    private final URI voiceUrl;

    @JsonCreator
    private TollFree(@JsonProperty("account_sid") final String accountSid, 
                     @JsonProperty("address_requirements") final TollFree.AddressRequirement addressRequirements, 
                     @JsonProperty("api_version") final String apiVersion, 
                     @JsonProperty("beta") final Boolean beta, 
                     @JsonProperty("capabilities") final String capabilities, 
                     @JsonProperty("date_created") final String dateCreated, 
                     @JsonProperty("date_updated") final String dateUpdated, 
                     @JsonProperty("friendly_name") final String friendlyName, 
                     @JsonProperty("phone_number") final String phoneNumber, 
                     @JsonProperty("sid") final String sid, 
                     @JsonProperty("sms_application_sid") final String smsApplicationSid, 
                     @JsonProperty("sms_fallback_method") final HttpMethod smsFallbackMethod, 
                     @JsonProperty("sms_fallback_url") final URI smsFallbackUrl, 
                     @JsonProperty("sms_method") final HttpMethod smsMethod, 
                     @JsonProperty("sms_url") final URI smsUrl, 
                     @JsonProperty("status_callback") final URI statusCallback, 
                     @JsonProperty("status_callback_method") final HttpMethod statusCallbackMethod, 
                     @JsonProperty("uri") final String uri, 
                     @JsonProperty("voice_application_sid") final String voiceApplicationSid, 
                     @JsonProperty("voice_caller_id_lookup") final Boolean voiceCallerIdLookup, 
                     @JsonProperty("voice_fallback_method") final HttpMethod voiceFallbackMethod, 
                     @JsonProperty("voice_fallback_url") final URI voiceFallbackUrl, 
                     @JsonProperty("voice_method") final HttpMethod voiceMethod, 
                     @JsonProperty("voice_url") final URI voiceUrl) {
        this.accountSid = accountSid;
        this.addressRequirements = addressRequirements;
        this.apiVersion = apiVersion;
        this.beta = beta;
        this.capabilities = capabilities;
        this.dateCreated = MarshalConverter.dateTimeFromString(dateCreated);
        this.dateUpdated = MarshalConverter.dateTimeFromString(dateUpdated);
        this.friendlyName = friendlyName;
        this.phoneNumber = phoneNumber;
        this.sid = sid;
        this.smsApplicationSid = smsApplicationSid;
        this.smsFallbackMethod = smsFallbackMethod;
        this.smsFallbackUrl = smsFallbackUrl;
        this.smsMethod = smsMethod;
        this.smsUrl = smsUrl;
        this.statusCallback = statusCallback;
        this.statusCallbackMethod = statusCallbackMethod;
        this.uri = uri;
        this.voiceApplicationSid = voiceApplicationSid;
        this.voiceCallerIdLookup = voiceCallerIdLookup;
        this.voiceFallbackMethod = voiceFallbackMethod;
        this.voiceFallbackUrl = voiceFallbackUrl;
        this.voiceMethod = voiceMethod;
        this.voiceUrl = voiceUrl;
    }

    /**
     * @return The account_sid
     */
    public final String getAccountSid() {
        return this.accountSid;
    }

    /**
     * @return The address_requirements
     */
    public final TollFree.AddressRequirement getAddressRequirements() {
        return this.addressRequirements;
    }

    /**
     * @return The api_version
     */
    public final String getApiVersion() {
        return this.apiVersion;
    }

    /**
     * @return The beta
     */
    public final Boolean getBeta() {
        return this.beta;
    }

    /**
     * @return The capabilities
     */
    public final String getCapabilities() {
        return this.capabilities;
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
     * @return The phone_number
     */
    public final String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * @return The sid
     */
    public final String getSid() {
        return this.sid;
    }

    /**
     * @return The sms_application_sid
     */
    public final String getSmsApplicationSid() {
        return this.smsApplicationSid;
    }

    /**
     * @return The sms_fallback_method
     */
    public final HttpMethod getSmsFallbackMethod() {
        return this.smsFallbackMethod;
    }

    /**
     * @return The sms_fallback_url
     */
    public final URI getSmsFallbackUrl() {
        return this.smsFallbackUrl;
    }

    /**
     * @return The sms_method
     */
    public final HttpMethod getSmsMethod() {
        return this.smsMethod;
    }

    /**
     * @return The sms_url
     */
    public final URI getSmsUrl() {
        return this.smsUrl;
    }

    /**
     * @return The status_callback
     */
    public final URI getStatusCallback() {
        return this.statusCallback;
    }

    /**
     * @return The status_callback_method
     */
    public final HttpMethod getStatusCallbackMethod() {
        return this.statusCallbackMethod;
    }

    /**
     * @return The uri
     */
    public final String getUri() {
        return this.uri;
    }

    /**
     * @return The voice_application_sid
     */
    public final String getVoiceApplicationSid() {
        return this.voiceApplicationSid;
    }

    /**
     * @return The voice_caller_id_lookup
     */
    public final Boolean getVoiceCallerIdLookup() {
        return this.voiceCallerIdLookup;
    }

    /**
     * @return The voice_fallback_method
     */
    public final HttpMethod getVoiceFallbackMethod() {
        return this.voiceFallbackMethod;
    }

    /**
     * @return The voice_fallback_url
     */
    public final URI getVoiceFallbackUrl() {
        return this.voiceFallbackUrl;
    }

    /**
     * @return The voice_method
     */
    public final HttpMethod getVoiceMethod() {
        return this.voiceMethod;
    }

    /**
     * @return The voice_url
     */
    public final URI getVoiceUrl() {
        return this.voiceUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        TollFree other = (TollFree) o;
        
        return Objects.equals(accountSid, other.accountSid) && 
               Objects.equals(addressRequirements, other.addressRequirements) && 
               Objects.equals(apiVersion, other.apiVersion) && 
               Objects.equals(beta, other.beta) && 
               Objects.equals(capabilities, other.capabilities) && 
               Objects.equals(dateCreated, other.dateCreated) && 
               Objects.equals(dateUpdated, other.dateUpdated) && 
               Objects.equals(friendlyName, other.friendlyName) && 
               Objects.equals(phoneNumber, other.phoneNumber) && 
               Objects.equals(sid, other.sid) && 
               Objects.equals(smsApplicationSid, other.smsApplicationSid) && 
               Objects.equals(smsFallbackMethod, other.smsFallbackMethod) && 
               Objects.equals(smsFallbackUrl, other.smsFallbackUrl) && 
               Objects.equals(smsMethod, other.smsMethod) && 
               Objects.equals(smsUrl, other.smsUrl) && 
               Objects.equals(statusCallback, other.statusCallback) && 
               Objects.equals(statusCallbackMethod, other.statusCallbackMethod) && 
               Objects.equals(uri, other.uri) && 
               Objects.equals(voiceApplicationSid, other.voiceApplicationSid) && 
               Objects.equals(voiceCallerIdLookup, other.voiceCallerIdLookup) && 
               Objects.equals(voiceFallbackMethod, other.voiceFallbackMethod) && 
               Objects.equals(voiceFallbackUrl, other.voiceFallbackUrl) && 
               Objects.equals(voiceMethod, other.voiceMethod) && 
               Objects.equals(voiceUrl, other.voiceUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountSid,
                            addressRequirements,
                            apiVersion,
                            beta,
                            capabilities,
                            dateCreated,
                            dateUpdated,
                            friendlyName,
                            phoneNumber,
                            sid,
                            smsApplicationSid,
                            smsFallbackMethod,
                            smsFallbackUrl,
                            smsMethod,
                            smsUrl,
                            statusCallback,
                            statusCallbackMethod,
                            uri,
                            voiceApplicationSid,
                            voiceCallerIdLookup,
                            voiceFallbackMethod,
                            voiceFallbackUrl,
                            voiceMethod,
                            voiceUrl);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("accountSid", accountSid)
                          .add("addressRequirements", addressRequirements)
                          .add("apiVersion", apiVersion)
                          .add("beta", beta)
                          .add("capabilities", capabilities)
                          .add("dateCreated", dateCreated)
                          .add("dateUpdated", dateUpdated)
                          .add("friendlyName", friendlyName)
                          .add("phoneNumber", phoneNumber)
                          .add("sid", sid)
                          .add("smsApplicationSid", smsApplicationSid)
                          .add("smsFallbackMethod", smsFallbackMethod)
                          .add("smsFallbackUrl", smsFallbackUrl)
                          .add("smsMethod", smsMethod)
                          .add("smsUrl", smsUrl)
                          .add("statusCallback", statusCallback)
                          .add("statusCallbackMethod", statusCallbackMethod)
                          .add("uri", uri)
                          .add("voiceApplicationSid", voiceApplicationSid)
                          .add("voiceCallerIdLookup", voiceCallerIdLookup)
                          .add("voiceFallbackMethod", voiceFallbackMethod)
                          .add("voiceFallbackUrl", voiceFallbackUrl)
                          .add("voiceMethod", voiceMethod)
                          .add("voiceUrl", voiceUrl)
                          .toString();
    }
}