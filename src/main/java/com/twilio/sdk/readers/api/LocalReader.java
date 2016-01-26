package com.twilio.sdk.readers.api;

import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.readers.Reader;
import com.twilio.sdk.resources.Page;
import com.twilio.sdk.resources.ResourceSet;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.api.Local;

public class LocalReader extends Reader<Local> {
    private final String ownerAccountSid;
    private Boolean beta;
    private String friendlyName;
    private String phoneNumber;

    /**
     * Construct a new LocalReader
     * 
     * @param ownerAccountSid The owner_account_sid
     */
    public LocalReader(final String ownerAccountSid) {
        this.ownerAccountSid = ownerAccountSid;
    }

    /**
     * The beta
     * 
     * @param beta The beta
     * @return this
     */
    public LocalReader byBeta(final Boolean beta) {
        this.beta = beta;
        return this;
    }

    /**
     * The friendly_name
     * 
     * @param friendlyName The friendly_name
     * @return this
     */
    public LocalReader byFriendlyName(final String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    /**
     * The phone_number
     * 
     * @param phoneNumber The phone_number
     * @return this
     */
    public LocalReader byPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the read
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Local ResourceSet
     */
    @Override
    public ResourceSet<Local> execute(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.GET,
            "/2010-04-01/Accounts/" + this.ownerAccountSid + "/IncomingPhoneNumbers/Local.json",
            client.getAccountSid()
        );
        
        addQueryParams(request);
        
        Page<Local> page = pageForRequest(client, request);
        
        return new ResourceSet<>(this, client, page);
    }

    /**
     * Retrieve the next page from the Twilio API
     * 
     * @param nextPageUri URI from which to retrieve the next page
     * @param client TwilioRestClient with which to make the request
     * @return Next Page
     */
    @Override
    public Page<Local> nextPage(final String nextPageUri, final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.GET,
            nextPageUri,
            client.getAccountSid()
        );
        return pageForRequest(client, request);
    }

    /**
     * Generate a Page of Local Resources for a given request
     * 
     * @param client TwilioRestClient with which to make the request
     * @param request Request to generate a page for
     * @return Page for the Request
     */
    protected Page<Local> pageForRequest(final TwilioRestClient client, final Request request) {
        Response response = client.request(request);
        
        if (response == null) {
            throw new ApiConnectionException("Local read failed: Unable to connect to server");
        } else if (response.getStatusCode() != TwilioRestClient.HTTP_STATUS_CODE_OK) {
            RestException restException = RestException.fromJson(response.getStream(), client.getObjectMapper());
            throw new ApiException(
                restException.getMessage(),
                restException.getCode(),
                restException.getMoreInfo(),
                restException.getStatus(),
                null
            );
        }
        
        Page<Local> result = new Page<>();
        result.deserialize("incoming_phone_numbers", response.getContent(), Local.class, client.getObjectMapper());
        
        return result;
    }

    /**
     * Add the requested query string arguments to the Request
     * 
     * @param request Request to add query string arguments to
     */
    private void addQueryParams(final Request request) {
        if (beta != null) {
            request.addQueryParam("Beta", beta.toString());
        }
        
        if (friendlyName != null) {
            request.addQueryParam("FriendlyName", friendlyName);
        }
        
        if (phoneNumber != null) {
            request.addQueryParam("PhoneNumber", phoneNumber);
        }
        
        request.addQueryParam("PageSize", Integer.toString(getPageSize()));
    }
}