package com.otus.finalproject.registryapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity(name = "file")
@Table(name = "file", schema = "public")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @Column(name = "path")
    private String path;

    @Column(name = "is_registered", insertable = false)
    @JsonIgnore
    private Boolean isRegistered;
}
