package com.github.calve.service.etc;

import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

public interface StorageService {

    void storeRequests(MultipartFile file) throws SQLException;

    void storeComplaints(MultipartFile file) throws SQLException;

    void storeGenerics(MultipartFile file) throws SQLException;

    void storeInfo(MultipartFile file) throws SQLException;

    void storeForeigners(MultipartFile file) throws SQLException;

    void storeApplications(MultipartFile file) throws SQLException;

    void storeOutgoing(MultipartFile file) throws SQLException;
}
