package com.github.t1.problemdetails.jaxrs;

import com.github.t1.problemdetails.jaxrs.lib.ProblemDetailBuilder;
import org.eclipse.microprofile.problemdetails.ResponseStatus;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

class JaxRsProblemDetailBuilder extends ProblemDetailBuilder {
    private final HttpHeaders requestHeaders;
    private final Response response;

    JaxRsProblemDetailBuilder(Throwable exception, HttpHeaders requestHeaders) {
        this(exception, requestHeaders, null);
    }

    JaxRsProblemDetailBuilder(Throwable exception, HttpHeaders requestHeaders, Response response) {
        super(exception);
        this.requestHeaders = requestHeaders;
        this.response = response;
    }

    @Override protected ResponseStatus buildStatus() {
        return (response != null && ResponseStatus.allowed(response.getStatus()))
            ? ResponseStatus.valueOf(response.getStatus())
            : super.buildStatus();
    }

    @Override protected URI buildTypeUri() {
        if (response != null)
            return problemTypeUrn(response.getStatusInfo().getReasonPhrase());
        return super.buildTypeUri();
    }

    @Override protected String fallbackTitle() {
        if (response != null)
            return response.getStatusInfo().getReasonPhrase();
        return super.fallbackTitle();
    }

    @Override protected String findMediaTypeSubtype() {
        for (MediaType accept : requestHeaders.getAcceptableMediaTypes()) {
            if ("application".equals(accept.getType())) {
                return accept.getSubtype();
            }
        }
        return "json";
    }

    @Override public JaxRsProblemDetailBuilder log() {
        super.log();
        return this;
    }

    public Response toResponse() {
        return Response
            .status(getStatus().code)
            .entity(getBody())
            .header("Content-Type", getMediaType())
            .build();
    }
}