package com.mycompany.app.service;


import java.io.File;
import java.io.IOException;

public interface S3Service {

    String uploadFile(String fileName, File file) throws IOException;
}
