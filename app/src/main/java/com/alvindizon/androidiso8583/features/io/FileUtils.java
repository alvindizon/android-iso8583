package com.alvindizon.androidiso8583.features.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    private FileUtils() {
    }

    public static File createFileFromInputStream(InputStream inputStream, String targetFilePath)
            throws Exception {
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);

        File outputFile = new File(targetFilePath);
        OutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(buffer);
        return outputFile;
    }
}
