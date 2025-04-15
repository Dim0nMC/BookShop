package com.example.bookshop.dto;

import com.example.bookshop.model.AbstractBaseEntity;
import com.example.bookshop.model.HasId;

public interface BookDisplay extends HasId {
    Integer getId();
    String getName();
    String getImage();
    Integer getCRC();
    String getFileName();
    String getArchiveType();
}
