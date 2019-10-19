package com.github.calve.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void storeRequests(MultipartFile file);

}
