package com.diff.filesdiff.service.impl;

import com.diff.filesdiff.domain.FileEntity;
import com.diff.filesdiff.exception.FileCompareException;
import com.diff.filesdiff.exception.FileNotFoundException;
import com.diff.filesdiff.exception.FileUploadException;
import com.diff.filesdiff.properties.FileProperties;
import com.diff.filesdiff.repository.FileRepository;
import com.diff.filesdiff.resource.FileForm;
import com.diff.filesdiff.service.FileService;
import com.diff.filesdiff.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service("fileSystemFileServiceImpl")
public class FileSystemFileServiceImpl implements FileService {

    private final FileProperties fileProperties;

    private final FileRepository fileRepository;

    public FileSystemFileServiceImpl(final FileProperties fileProperties,
                                     final FileRepository fileRepository) {
        this.fileProperties = fileProperties;
        this.fileRepository = fileRepository;
    }

    @Override
    public Long upload(final String identifier, final FileForm fileForm) {
        log.info("M=upload, iniciando upload de arquivo, name={}, fileForm={}", identifier, fileForm);

        final var decodeBytes = Base64.getDecoder().decode(fileForm.getFile());

        final var fullPath = fileForm.getName() + fileProperties.getBasePath();

        final var file = new File(fullPath);

        try {
            FileUtils.writeFile(file, decodeBytes);
            return fileRepository.save(getFileEntity(identifier, fullPath)).getId();
        } catch (Exception exception) {
            log.error("M=upload, problemas no upload do arquivo");
            throw new FileUploadException("problemas no upload do arquivo");
        }
    }

    @Override
    public String compare(final String identifier) {
        log.info("M=compare, iniciando comparação de arquivos, identifier={}", identifier);

        final var paths = findPathByIdentifier(identifier);

        try {
            return FileUtils.compareWithPath(paths, identifier);
        } catch (IOException e) {
            log.error("M=compare, problemas na comparação dos arquivo");
            throw new FileCompareException();
        }
    }

    private List<String> findPathByIdentifier(final String identifier) {
        log.info("M=findPathByIdentifier, iniciando busca no repositorio, identifier={}", identifier);
        return fileRepository.findPathByIdentifier(identifier)
                .orElseThrow(FileNotFoundException::new);
    }

    private FileEntity getFileEntity(final String identifier, final String path) {
        return FileEntity.builder()
                .identifier(identifier)
                .path(path)
                .build();
    }
}
