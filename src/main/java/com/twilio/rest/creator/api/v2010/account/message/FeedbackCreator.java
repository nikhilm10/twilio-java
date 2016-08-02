/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /       
 */

package com.twilio.rest.creator.api.v2010.account.message;

import com.twilio.rest.creator.Creator;
import com.twilio.rest.exception.ApiConnectionException;
import com.twilio.rest.exception.ApiException;
import com.twilio.rest.http.HttpMethod;
import com.twilio.rest.http.Request;
import com.twilio.rest.http.Response;
import com.twilio.rest.http.TwilioRestClient;
import com.twilio.rest.resource.RestException;
import com.twilio.rest.resource.api.v2010.account.message.Feedback;

public class FeedbackCreator extends Creator<Feedback> {
    private String accountSid;
    private final String messageSid;
    private Feedback.Outcome outcome;

    /**
     * Construct a new FeedbackCreator.
     * 
     * @param messageSid The message_sid
     */
    public FeedbackCreator(final String messageSid) {
        this.messageSid = messageSid;
    }

    /**
     * Construct a new FeedbackCreator.
     * 
     * @param accountSid The account_sid
     * @param messageSid The message_sid
     */
    public FeedbackCreator(final String accountSid, 
                           final String messageSid) {
        this.accountSid = accountSid;
        this.messageSid = messageSid;
    }

    /**
     * The outcome.
     * 
     * @param outcome The outcome
     * @return this
     */
    public FeedbackCreator setOutcome(final Feedback.Outcome outcome) {
        this.outcome = outcome;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the create.
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Created Feedback
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public Feedback execute(final TwilioRestClient client) {
        this.accountSid = this.accountSid == null ? client.getAccountSid() : this.accountSid;
        Request request = new Request(
            HttpMethod.POST,
            TwilioRestClient.Domains.API.toString(),
            "/2010-04-01/Accounts/" + this.accountSid + "/Messages/" + this.messageSid + "/Feedback.json",
            client.getRegion()
        );
        
        addPostParams(request);
        Response response = client.request(request);
        
        if (response == null) {
            throw new ApiConnectionException("Feedback creation failed: Unable to connect to server");
        } else if (!TwilioRestClient.SUCCESS.apply(response.getStatusCode())) {
            RestException restException = RestException.fromJson(response.getStream(), client.getObjectMapper());
            if (restException == null) {
                throw new ApiException("Server Error, no content");
            }
        
            throw new ApiException(
                restException.getMessage(),
                restException.getCode(),
                restException.getMoreInfo(),
                restException.getStatus(),
                null
            );
        }
        
        return Feedback.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     * 
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (outcome != null) {
            request.addPostParam("Outcome", outcome.toString());
        }
    }
}