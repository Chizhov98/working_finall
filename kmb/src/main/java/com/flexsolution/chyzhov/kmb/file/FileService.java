package com.flexsolution.chyzhov.kmb.file;

import com.flexsolution.chyzhov.kmb.AbstractService;
import com.flexsolution.chyzhov.kmb.user.User;
import com.flexsolution.chyzhov.kmb.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService extends AbstractService<FileMetaData, FileRepository> {
    private final FileContentStore contentStore;
    private final UserService userService;

    public FileService(FileRepository repository, FileContentStore contentStore, UserService userService) {
        super(repository);
        this.contentStore = contentStore;
        this.userService = userService;
    }

    public FileMetaData addFileToUser(Long userId, MultipartFile multipartFile) throws IOException {
        FileMetaData file = new FileMetaData();
        file.setMimeType(multipartFile.getContentType());
        file.setName(multipartFile.getName());
        contentStore.setContent(file, multipartFile.getInputStream());
        User user = userService.findById(userId);
        user.getFiles().add(file);
        userService.update(user, userId);
        repository.save(file);
        return file;
    }
}
