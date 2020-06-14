package com.diff.filesdiff.resource.v1;

import com.diff.filesdiff.event.ResourceCreatedEvent;
import com.diff.filesdiff.resource.FileForm;
import com.diff.filesdiff.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController()
@RequestMapping("/v1/diff")
public class V1DiffResource {

    private final FileService fileService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public V1DiffResource(final ApplicationEventPublisher applicationEventPublisher,
                          @Qualifier("fileSystemFileServiceImpl") final FileService fileService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.fileService = fileService;
    }

    @PostMapping("/{identifier}/left")
    ResponseEntity<?> uploadLeftFile(@PathVariable final String identifier,
                                     @RequestBody final FileForm file,
                                     final HttpServletResponse response) {
        log.info("M=uploadLeftFile, iniciando de upload, file={}", file);
        return uploadFile(identifier, file, response);
    }

    @PostMapping("/{identifier}/right")
    ResponseEntity<?> uploadRightFile(@PathVariable final String identifier,
                                      @RequestBody final FileForm file,
                                      final HttpServletResponse response) {
        log.info("M=uploadRightFile, iniciando de upload, file={}", file);
        return uploadFile(identifier, file, response);
    }

    @GetMapping("/{identifier}")
    ResponseEntity<?> compareFile(@PathVariable final String identifier) {
        log.info("M=compareFile, iniciando comparação do arquivo, identifier={}", identifier);
        final var result = fileService.compare(identifier);
        return ResponseEntity.ok(result);
    }


    private ResponseEntity<?> uploadFile(final String identifier, final FileForm file, final HttpServletResponse response) {
        final var fileId = fileService.upload(identifier, file);

        applicationEventPublisher.publishEvent(new ResourceCreatedEvent(this, fileId, response));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
