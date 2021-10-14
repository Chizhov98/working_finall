package com.flexsolution.chyzhov.kmb.file;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Component;

@Component
public interface FileContentStore extends ContentStore<FileMetaData, String> {
}