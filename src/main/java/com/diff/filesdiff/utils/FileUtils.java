package com.diff.filesdiff.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class FileUtils {

    private FileUtils() {
    }

    public static String getFileType(final String fileName) {
        return fileName.substring(fileName.indexOf("."));
    }

    public static InputStream getInputStream(byte[] bytes) {
        InputStream is = new ByteArrayInputStream(bytes);
        return is;
    }

    public static void writeFile(File file, final byte[] data) throws IOException {
        final var fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(data);
        fileOutputStream.close();
    }

    public static String compareWithInputStream(final List<InputStream> inputStreams, final String identifier) throws IOException {
        final var bufferedInputStreamOne = new BufferedInputStream(inputStreams.get(0));
        final var bufferedInputStreamTwo = new BufferedInputStream(inputStreams.get(1));

        return compare(identifier, bufferedInputStreamOne, bufferedInputStreamTwo);
    }

    public static String compareWithPath(final List<String> paths, final String identifier) throws IOException {
        final var bufferedInputStreamOne = new BufferedInputStream(new FileInputStream(paths.get(0)));
        final var bufferedInputStreamTwo = new BufferedInputStream(new FileInputStream(paths.get(1)));

        return compare(identifier, bufferedInputStreamOne, bufferedInputStreamTwo);
    }

    private static String compare(final String identifier, final BufferedInputStream bufferedInputStreamOne,
                                  final BufferedInputStream bufferedInputStreamTwo) throws IOException {
        var result = "Documentos<" + identifier + "> são identificos";

        int dataOne = 0, dataTwo = 0, position = 1;
        while (dataOne != -1 && dataTwo != -1) {
            if (isDifferent(dataOne, dataTwo)) {
                result = "Na posição " + position + " os documentos<" + identifier + "> se divergem";
            }
            position++;
            dataOne = bufferedInputStreamOne.read();
            dataTwo = bufferedInputStreamTwo.read();
        }
        if (isDifferentFileSize(dataOne, dataTwo)) {
            result = "Documentos<" + identifier + "> são de tamanhos diferentes";
        }
        bufferedInputStreamOne.close();
        bufferedInputStreamTwo.close();
        return result;
    }

    private static boolean isDifferentFileSize(final int b1, final int b2) {
        return b1 != b2;
    }

    private static boolean isDifferent(final int b1, final int b2) {
        return b1 != b2;
    }
}
