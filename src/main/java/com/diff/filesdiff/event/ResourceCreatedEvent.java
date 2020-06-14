package com.diff.filesdiff.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceCreatedEvent extends ApplicationEvent {

    private Long resourceId;

    private HttpServletResponse httpServletResponse;

    public ResourceCreatedEvent(final Object source,
                                final Long resourceId,
                                final HttpServletResponse httpServletResponse) {
        super(source);
        this.resourceId = resourceId;
        this.httpServletResponse = httpServletResponse;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }
}
