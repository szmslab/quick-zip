/*
 * Copyright (c) 2015 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.quickzip.archiver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

import net.lingala.zip4j.exception.ZipException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import com.szmslab.quickzip.enums.CompressionMethod;
import com.szmslab.quickzip.enums.EncryptionType;

public abstract class AbstractTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    protected File testDir;
    protected File dir1;
    protected File file1;
    protected File file2;
    protected File file3;

    protected static final String PASSWORD = "password";

    protected static final String FILE1_CONTENT = "テスト1テスト1";
    protected static final String FILE2_CONTENT = "テスト2" + System.getProperty("line.separator") + "テスト2";
    protected static final String FILE3_CONTENT = "テスト3テスト3" + System.getProperty("line.separator");

    @Before
    public void setUp() throws Exception {
        testDir = tmpFolder.newFolder("quick-zip");
        dir1 = mkDir(testDir, "フォルダ1");
        file1 = mkFile(testDir, "テスト1.txt", FILE1_CONTENT);
        file2 = mkFile(dir1, "テスト2.txt", FILE2_CONTENT);
        file3 = mkFile(dir1, "テスト3.txt", FILE3_CONTENT);
    }

    protected File mkFile(File dir, String fileName, String content) throws IOException {
        dir.mkdir();
        BufferedWriter bw = null;
        try {
            File file = toFile(dir, fileName);
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(content, 0, content.length());
            return file;
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    protected String readFile(File file) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            int ch = 0;
            while ((ch = br.read()) >= 0) {
                sb.append((char) ch);
            }
            return sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    protected File mkDir(File dir, String dirName) {
        File f = toFile(dir, dirName);
        f.mkdir();
        return f;
    }

    protected File toFile(File dir, String name) {
        return new File(dir.getAbsolutePath() + File.separator + name);
    }

    protected File testFile(String fileName) {
        return toFile(testDir, fileName + ".zip");
    }

    protected File setUpZipFile(Method method) {
        File zipFile = testFile(method.getName());
        if (zipFile.exists()) {
            throw new RuntimeException("zipFile already exists : " + zipFile.getAbsolutePath());
        }
        return zipFile;
    }

    protected File compress(Method method, CompressionMethod compressionMethod) throws IOException, ZipException {
        return new ZipCompressor()
            .compressionMethod(compressionMethod)
            .execute(setUpZipFile(method), dir1, file1);
    }

    protected File compress(Method method, EncryptionType encryptionType) throws IOException, ZipException {
        return new ZipCompressor()
            .encryptionType(encryptionType, PASSWORD)
            .execute(setUpZipFile(method), dir1, file1);
    }

}
