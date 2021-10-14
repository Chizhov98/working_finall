package com.flexsolution.chyzhov.kmb.file;

import java.io.IOException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService service;
    private final FileContentStore contentStore;

        public FileController(FileService service, FileContentStore contentStore) {
        this.service = service;
        this.contentStore = contentStore;
    }

    @Parameter(name = "file",
            schema = @Schema(type = "MultipartFile"),
            description = "File that need added to user")
    @Parameter(name = "userId",
            schema = @Schema(type = "Long"),
            description = "User Id ")
    @PostMapping("/user/{userId}")
    public ResponseEntity addFileToUser(@RequestParam(name = "file") MultipartFile multipartFile,
                                        @PathVariable(name = "userId") Long id) throws IOException {

        FileMetaData file = service.addFileToUser(id, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(file);
    }

    @Operation(description = "Found file by file id")
    @GetMapping("/{fileId}")
    public ResponseEntity<?> getContent(@PathVariable("fileId") Long id) {

        FileMetaData file = service.findById(id);
        InputStreamResource inputStreamResource = new InputStreamResource(contentStore.getContent(file));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(file.getContentLength());
        headers.set("Content-Type", file.getMimeType());
        return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
    }

}