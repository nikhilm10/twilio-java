package com.twilio.sdk.updaters.taskrouter;

import com.twilio.sdk.clients.TwilioRestClient;
import com.twilio.sdk.exceptions.ApiConnectionException;
import com.twilio.sdk.exceptions.ApiException;
import com.twilio.sdk.http.HttpMethod;
import com.twilio.sdk.http.Request;
import com.twilio.sdk.http.Response;
import com.twilio.sdk.resources.RestException;
import com.twilio.sdk.resources.taskrouter.Workflow;
import com.twilio.sdk.updaters.Updater;

public class WorkflowUpdater extends Updater<Workflow> {
    private final String workspaceSid;
    private final String sid;
    private String friendlyName;
    private String assignmentCallbackUrl;
    private String fallbackAssignmentCallbackUrl;
    private String configuration;
    private Integer taskReservationTimeout;

    /**
     * Construct a new WorkflowUpdater
     * 
     * @param workspaceSid The workspace_sid
     * @param sid The sid
     */
    public WorkflowUpdater(final String workspaceSid, final String sid) {
        this.workspaceSid = workspaceSid;
        this.sid = sid;
    }

    /**
     * The friendly_name
     * 
     * @param friendlyName The friendly_name
     * @return this
     */
    public WorkflowUpdater setFriendlyName(final String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    /**
     * The assignment_callback_url
     * 
     * @param assignmentCallbackUrl The assignment_callback_url
     * @return this
     */
    public WorkflowUpdater setAssignmentCallbackUrl(final String assignmentCallbackUrl) {
        this.assignmentCallbackUrl = assignmentCallbackUrl;
        return this;
    }

    /**
     * The fallback_assignment_callback_url
     * 
     * @param fallbackAssignmentCallbackUrl The fallback_assignment_callback_url
     * @return this
     */
    public WorkflowUpdater setFallbackAssignmentCallbackUrl(final String fallbackAssignmentCallbackUrl) {
        this.fallbackAssignmentCallbackUrl = fallbackAssignmentCallbackUrl;
        return this;
    }

    /**
     * The configuration
     * 
     * @param configuration The configuration
     * @return this
     */
    public WorkflowUpdater setConfiguration(final String configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * The task_reservation_timeout
     * 
     * @param taskReservationTimeout The task_reservation_timeout
     * @return this
     */
    public WorkflowUpdater setTaskReservationTimeout(final Integer taskReservationTimeout) {
        this.taskReservationTimeout = taskReservationTimeout;
        return this;
    }

    /**
     * Make the request to the Twilio API to perform the update
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Updated Workflow
     */
    @Override
    public Workflow execute(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            "/v1/Workspaces/" + this.workspaceSid + "/Workflows/" + this.sid + "",
            client.getAccountSid()
        );
        
        addPostParams(request);
        Response response = client.request(request);
        
        if (response == null) {
            throw new ApiConnectionException("Workflow update failed: Unable to connect to server");
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

    /**
     * Add the requested post parameters to the Request
     * 
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (friendlyName != null) {
            request.addPostParam("FriendlyName", friendlyName);
        }
        
        if (assignmentCallbackUrl != null) {
            request.addPostParam("AssignmentCallbackUrl", assignmentCallbackUrl);
        }
        
        if (fallbackAssignmentCallbackUrl != null) {
            request.addPostParam("FallbackAssignmentCallbackUrl", fallbackAssignmentCallbackUrl);
        }
        
        if (configuration != null) {
            request.addPostParam("Configuration", configuration);
        }
        
        if (taskReservationTimeout != null) {
            request.addPostParam("TaskReservationTimeout", taskReservationTimeout.toString());
        }
    }
}