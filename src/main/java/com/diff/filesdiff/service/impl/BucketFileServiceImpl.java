package com.diff.filesdiff.service.impl;

import com.diff.filesdiff.domain.FileEntity;
import com.diff.filesdiff.exception.FileCompareException;
import com.diff.filesdiff.exception.FileNotFoundException;
import com.diff.filesdiff.exception.FileUploadException;
import com.diff.filesdiff.repository.FileRepository;
import com.diff.filesdiff.resource.FileForm;
import com.diff.filesdiff.service.FileService;
import com.diff.filesdiff.service.StorageService;
import com.diff.filesdiff.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service("bucketFileServiceImpl")
public class BucketFileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    private final StorageService storageService;

    public BucketFileServiceImpl(final FileRepository fileRepository,
                                 final StorageService storageService) {
        this.fileRepository = fileRepository;
        this.storageService = storageService;
    }

    @Override
    public Long upload(String identifier, FileForm fileForm) {
        log.info("M=upload, iniciando upload de arquivo, name={}, fileForm={}", identifier, fileForm);

        final var decodeBytes = Base64.getDecoder().decode(fileForm.getFile());

        try {
            storageService.upload(fileForm.getName(), decodeBytes);
            return fileRepository.save(getFileEntity(identifier, fileForm.getName())).getId();
        } catch (Exception exception) {
            log.error("M=uploadFile, problemas no upload do arquivo");
            throw new FileUploadException("problemas no upload do arquivo");
        }
    }

    @Override
    public String compare(String identifier) {
        log.info("M=compare, iniciando comparação de arquivo, identifier={}", identifier);

        final var fileNames = findNameByIdentifier(identifier);

        final var inputStreamsToCompare = new ArrayList<InputStream>();

        fileNames.forEach(fileName -> {
            inputStreamsToCompare.add(storageService.donwload(fileName));
        });

        try {
            return FileUtils.compareWithInputStream(inputStreamsToCompare, identifier);
        } catch (IOException e) {
            log.error("M=compare, problemas na comparação dos arquivo");
            throw new FileCompareException();
        }
    }

    private List<String> findNameByIdentifier(final String identifier) {
        log.info("M=findNameByIdentifier, iniciando busca no repositorio, identifier={}", identifier);
        return fileRepository.findNameByIdentifier(identifier)
                .orElseThrow(FileNotFoundException::new);
    }

    private FileEntity getFileEntity(final String identifier, final String name) {
        return FileEntity.builder()
                .identifier(identifier)
                .name(name)
                .build();
    }
}
