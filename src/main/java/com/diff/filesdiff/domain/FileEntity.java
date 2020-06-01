package com.diff.filesdiff.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "FILE")
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "SQ_FILE_ID", sequenceName = "SQ_FILE_ID", allocationSize = 1)
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FILE_ID")
    private Long id;

    @Column(name = "IDENTIFIER")
    private String identifier;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PATH")
    private String path;

    @Column(name = "DAT_UPDATE")
    @UpdateTimestamp
    private LocalDateTime dateUpdate;

    @Column(name = "DAT_CREATE")
    @CreationTimestamp
    private LocalDateTime dateCreate;
}
