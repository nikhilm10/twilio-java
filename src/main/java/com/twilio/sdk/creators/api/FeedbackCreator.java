package com.twilio.sdk.creators.api;

import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.converters.Promoter;
import com.twilio.sdk.creators.Creator;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.api.Feedback;

import java.util.List;

public class FeedbackCreator extends Creator<Feedback> {
    private final String accountSid;
    private final String callSid;
    private final Integer qualityScore;
    private List<Feedback.Issues> issue;

    /**
     * Construct a new FeedbackCreator
     * 
     * @param accountSid The account_sid
     * @param callSid The call_sid
     * @param qualityScore The quality_score
     */
    public FeedbackCreator(final String accountSid, final String callSid, final Integer qualityScore) {
        this.accountSid = accountSid;
        this.callSid = callSid;
        this.qualityScore = qualityScore;
    }

    /**
     * The issue
     * 
     * @param issue The issue
     * @return this
     */
    public FeedbackCreator setIssue(final List<Feedback.Issues> issue) {
        this.issue = issue;
        return this;
    }

    /**
     * The issue
     * 
     * @param issue The issue
     * @return this
     */
    public FeedbackCreator setIssue(final Feedback.Issues issue) {
        return setIssue(Promoter.listOfOne(issue));
    }

    /**
     * Make the request to the Twilio API to perform the create
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Created Feedback
     */
    @Override
    public Feedback execute(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            "/2010-04-01/Accounts/" + this.accountSid + "/Calls/" + this.callSid + "/Feedback.json",
            client.getAccountSid()
        );
        
        addPostParams(request);
        Response response = client.request(request);
        
        if (response == null) {
            throw new ApiConnectionException("Feedback creation failed: Unable to connect to server");
        } else if (response.getStatusCode() != TwilioRestClient.HTTP_STATUS_CODE_CREATED) {
            RestException restException = RestException.fromJson(response.getStream(), client.getObjectMapper());
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
     * Add the requested post parameters to the Request
     * 
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (qualityScore != null) {
            request.addPostParam("QualityScore", qualityScore.toString());
        }
        
        if (issue != null) {
            request.addPostParam("Issue", issue.toString());
        }
    }
}