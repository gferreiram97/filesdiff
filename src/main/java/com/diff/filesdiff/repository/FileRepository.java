package com.diff.filesdiff.repository;

import com.diff.filesdiff.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    @Query("SELECT file.path FROM FileEntity as file "
           + "WHERE file.identifier = :identifier")
    Optional<List<String>> findPathByIdentifier(@Param("identifier") String identifier);

    @Query("SELECT file.name FROM FileEntity as file "
           + "WHERE file.identifier = :identifier")
    Optional<List<String>> findNameByIdentifier(@Param("identifier") String identifier);
}
