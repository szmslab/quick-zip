/*
 * Copyright (c) 2015 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.quickzip.archiver;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import net.lingala.zip4j.exception.ZipException;

import org.junit.Test;

import com.szmslab.quickzip.enums.CompressionMethod;
import com.szmslab.quickzip.enums.EncryptionType;

public class ZipExtractorTest extends AbstractTest {

    @Test
    public void testExecute_args_file() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = new ZipCompressor().execute(setUpZipFile(method), dir1, file1);
        File extractDir = new ZipExtractor()
            .execute(createExtractDir(method), zipFile);
        check(extractDir);
    }

    @Test
    public void testExecute_args_string() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = new ZipCompressor().execute(setUpZipFile(method), dir1, file1);
        File extractDir = new ZipExtractor()
            .execute(createExtractDir(method).getAbsolutePath(), zipFile.getAbsolutePath());
        check(extractDir);
    }

    @Test
    public void testAutoCreateDirectory() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = new ZipCompressor().execute(setUpZipFile(method), dir1, file1);
        File extractDir = new ZipExtractor()
            .autoCreateDirectory(true)
            .execute(createExtractDir(method), zipFile);
        File[] files = extractDir.getParentFile().listFiles();
        assertThat(files.length, is(1));
        assertThat(files[0].isDirectory(), is(true));
        assertThat(files[0].getName(), is(zipFile.getName().replace(".zip", "")));
        check(files[0]);
    }

    @Test
    public void testCompressionMethod_store() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.STORE);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_fastest() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_FASTEST);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_faster() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_FASTER);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_fast() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_FAST);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_normal_fast() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_NORMAL_FAST);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_normal() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_NORMAL);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_normal_high() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_NORMAL_HIGH);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_high() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_HIGH);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_higher() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_HIGHER);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testCompressionMethod_deflate_highest() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_HIGHEST);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testEncryptionType_no_encryption() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, EncryptionType.NO_ENCRYPTION);
        File extractDir = extract(method, zipFile);
        check(extractDir);
    }

    @Test
    public void testEncryptionType_zip_crypto() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, EncryptionType.ZIP_CRYPTO);
        File extractDir = extract(method, zipFile, PASSWORD);
        check(extractDir);
    }

    @Test
    public void testEncryptionType_aes_128() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, EncryptionType.AES_128);
        File extractDir = extract(method, zipFile, PASSWORD);
        check(extractDir);
    }

    @Test
    public void testEncryptionType_aes_256() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, EncryptionType.AES_256);
        File extractDir = extract(method, zipFile, PASSWORD);
        check(extractDir);
    }

    private File createExtractDir(Method method) {
        return mkDir(testDir, method.getName());
    }

    private File extract(Method method, File zipFile) throws IOException, ZipException {
        return new ZipExtractor().execute(createExtractDir(method), zipFile);
    }

    private File extract(Method method, File zipFile, String password) throws IOException, ZipException {
        return new ZipExtractor().execute(createExtractDir(method), zipFile, password);
    }

    private void check(File extractDir) throws IOException {
        /*
         * extractDir
         * ├ フォルダ1
         * │ ├ テスト2.txt
         * │ └ テスト3.txt
         * └ テスト1.txt
         */
        int cnt1 = 0;
        int cnt2 = 0;
        File d1 = null;
        File f1 = null;
        File f2 = null;
        File f3 = null;
        for (File f : extractDir.listFiles()) {
            if (f.isDirectory()) {
                d1 = f;
                for (File ff : f.listFiles()) {
                    if (ff.getName().equals(file2.getName())) {
                        f2 = ff;
                    } else {
                        f3 = ff;
                    }
                    cnt2++;
                }
            } else {
                f1 = f;
            }
            cnt1++;
        }
        assertThat(cnt1, is(2));
        assertThat(cnt2, is(2));
        assertThat(d1.getName(), is(dir1.getName()));
        assertThat(f1.getName(), is(file1.getName()));
        assertThat(f2.getName(), is(file2.getName()));
        assertThat(f3.getName(), is(file3.getName()));
        assertThat(readFile(f1), is(FILE1_CONTENT));
        assertThat(readFile(f2), is(FILE2_CONTENT));
        assertThat(readFile(f3), is(FILE3_CONTENT));
    }

}
