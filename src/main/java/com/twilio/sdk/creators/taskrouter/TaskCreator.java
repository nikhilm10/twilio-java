package com.twilio.sdk.creators.taskrouter;

import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.creators.Creator;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.taskrouter.Task;

public class TaskCreator extends Creator<Task> {
    private final String workspaceSid;
    private final String attributes;
    private final String workflowSid;
    private Integer timeout;
    private Integer priority;

    /**
     * Construct a new TaskCreator
     * 
     * @param workspaceSid The workspace_sid
     * @param attributes The attributes
     * @param workflowSid The workflow_sid
     */
    public TaskCreator(final String workspaceSid, final String attributes, final String workflowSid) {
        this.workspaceSid = workspaceSid;
        this.attributes = attributes;
        this.workflowSid = workflowSid;
    }

    /**
     * The timeout
     * 
     * @param timeout The timeout
     * @return this
     */
    public TaskCreator setTimeout(final Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * The priority
     * 
     * @param priority The priority
     * @return this
     */
    public TaskCreator setPriority(final Integer priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the create
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Created Task
     */
    @Override
    public Task execute(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            "/v1/Workspaces/" + this.workspaceSid + "/Tasks",
            client.getAccountSid()
        );
        
        addPostParams(request);
        Response response = client.request(request);
        
        if (response == null) {
            throw new ApiConnectionException("Task creation failed: Unable to connect to server");
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
        
        return Task.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request
     * 
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (attributes != null) {
            request.addPostParam("Attributes", attributes);
        }
        
        if (workflowSid != null) {
            request.addPostParam("WorkflowSid", workflowSid);
        }
        
        if (timeout != null) {
            request.addPostParam("Timeout", timeout.toString());
        }
        
        if (priority != null) {
            request.addPostParam("Priority", priority.toString());
        }
    }
}