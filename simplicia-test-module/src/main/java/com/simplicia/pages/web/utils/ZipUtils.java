package com.simplicia.pages.web.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    public static List<File> exportZipFile(File f) throws Exception{
        String outputFilePath = f.getAbsolutePath() + "-unzipped";
        List<File> unzippedFiles = new ArrayList<>(0);
        File tempDir = new File(outputFilePath);
        if(!tempDir.exists()) {
            tempDir.mkdir();
        }
        ZipInputStream zipFile = new ZipInputStream(new FileInputStream(f));
        ZipEntry file;
        while ((file = zipFile.getNextEntry()) != null) {
            File actualFile = new File(outputFilePath + File.separator + file.getName());
            OutputStream outputStream = new FileOutputStream(actualFile);
            byte[] buffer = new byte[2048];
            int length;
            while ((length = zipFile.read(buffer, 0, buffer.length)) >= 0) {
                outputStream.write(buffer, 0, length);
            }
            unzippedFiles.add(actualFile);
            IOUtils.closeQuietly(outputStream);
        }
        IOUtils.closeQuietly(zipFile);
        return unzippedFiles;
    }
}
