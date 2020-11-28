package com.otus.finalproject.registryapp.domain.entities.id;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@Accessors(chain = true)
public class FilePermissionId implements Serializable {
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "user_login")
    private String userLogin;
}
