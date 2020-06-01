package com.diff.filesdiff.resource.v2;

import com.diff.filesdiff.resource.FileForm;
import com.diff.filesdiff.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/v2/diff")
public class V2DiffResource {

    private final FileService fileService;

    public V2DiffResource(@Qualifier("bucketFileServiceImpl") final FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/{identifier}/left")
    ResponseEntity<?> uploadLeftFile(@PathVariable final String identifier,
                                     @RequestBody final FileForm file) {
        log.info("M=uploadLeftFile, iniciando de upload, file={}", file);
        return uploadFile(identifier, file);
    }

    @PostMapping("/{identifier}/right")
    ResponseEntity<?> uploadRightFile(@PathVariable final String identifier,
                                     @RequestBody final FileForm file) {
        log.info("M=uploadRightFile, iniciando de upload, file={}", file);
        return uploadFile(identifier, file);
    }

    @GetMapping("/{identifier}")
    ResponseEntity<?> compareFile(@PathVariable final String identifier) {
        log.info("M=compareFile, iniciando comparação do arquivo, identifier={}", identifier);
        final var result = fileService.compare(identifier);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<?> uploadFile(final String identifier, final FileForm file) {
        final var fileId = fileService.upload(identifier, file);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(fileId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
