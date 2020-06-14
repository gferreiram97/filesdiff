package com.diff.filesdiff.event.listener;

import com.diff.filesdiff.annotations.Listener;
import com.diff.filesdiff.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Listener
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(final ResourceCreatedEvent event) {

        final var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(event.getResourceId()).toUri();

        addLocation(event, uri);
    }

    private void addLocation(final ResourceCreatedEvent event, final URI uri) {
        event.getHttpServletResponse().setHeader("Location", uri.toASCIIString());
    }
}
