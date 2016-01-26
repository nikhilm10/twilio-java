package com.twilio.sdk.fetchers.taskrouter;

import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.fetchers.Fetcher;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.taskrouter.Workflow;

public class WorkflowFetcher extends Fetcher<Workflow> {
    private final String workspaceSid;
    private final String sid;

    /**
     * Construct a new WorkflowFetcher
     * 
     * @param workspaceSid The workspace_sid
     * @param sid The sid
     */
    public WorkflowFetcher(final String workspaceSid, final String sid) {
        this.workspaceSid = workspaceSid;
        this.sid = sid;
    }

    /**
     * Make the request to the Twilio API to perform the fetch
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Fetched Workflow
     */
    @Override
    public Workflow execute(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.GET,
            "/v1/Workspaces/" + this.workspaceSid + "/Workflows/" + this.sid + "",
            client.getAccountSid()
        );
        
        Response response = client.request(request);
        
        if (response == null) {
            throw new ApiConnectionException("Workflow fetch failed: Unable to connect to server");
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
        
        return Workflow.fromJson(response.getStream(), client.getObjectMapper());
    }
}