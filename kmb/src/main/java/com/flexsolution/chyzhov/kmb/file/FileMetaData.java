package com.flexsolution.chyzhov.kmb.file;

import com.flexsolution.chyzhov.kmb.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class FileMetaData extends AbstractEntity {

    private String name;
    private LocalDate created = LocalDate.now();

    @ContentId
    private String contentId;

    @ContentLength
    private long contentLength;

    @MimeType
    private String mimeType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FileMetaData file = (FileMetaData) o;
        return contentLength == file.contentLength
                && Objects.equals(name, file.name)
                && Objects.equals(created, file.created)
                && Objects.equals(contentId, file.contentId)
                && Objects.equals(mimeType, file.mimeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, created, contentId, contentLength, mimeType);
    }
}
