package com.ddangme.dm.model.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;


}
