package com.otus.finalproject.registryapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.otus.finalproject.registryapp.domain.entities.id.FilePermissionId;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "file_permission", schema = "public")
@Accessors(chain = true)
public class FilePermission implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private FilePermissionId filePermissionId;

    @Column(name = "role", nullable = false)
    private String role;

    @MapsId("filePath")
    @ManyToOne
    @JoinColumn(name = "file_path", referencedColumnName = "path", nullable = false)
    private File file = null;

    @MapsId("userLogin")
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_login", referencedColumnName = "login", nullable = false)
    private User user = null;

}
