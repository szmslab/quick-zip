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

public class ZipCompressorTest extends AbstractTest {

    @Test
    public void testExecute_args_file() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = new ZipCompressor()
            .execute(setUpZipFile(method), dir1, file1);
        check(zipFile);
    }

    @Test
    public void testExecute_args_string() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = new ZipCompressor()
            .execute(setUpZipFile(method).getAbsolutePath(), dir1.getAbsolutePath(), file1.getAbsolutePath());
        check(zipFile);
    }

    @Test
    public void testRootPathOfZipEntry() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = new ZipCompressor()
            .rootPathOfZipEntry("root")
            .execute(setUpZipFile(method), dir1, file1);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_store() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.STORE);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_fastest() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_FASTEST);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_faster() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_FASTER);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_fast() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_FAST);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_normal_fast() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_NORMAL_FAST);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_normal() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_NORMAL);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_normal_high() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_NORMAL_HIGH);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_high() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_HIGH);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_higher() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_HIGHER);
        check(zipFile);
    }

    @Test
    public void testCompressionMethod_deflate_highest() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, CompressionMethod.DEFLATE_HIGHEST);
        check(zipFile);
    }

    @Test
    public void testEncryptionType_no_encryption() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, EncryptionType.NO_ENCRYPTION);
        check(zipFile);
    }

    @Test
    public void testEncryptionType_zip_crypto() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, EncryptionType.ZIP_CRYPTO);
        check(zipFile);
    }

    @Test
    public void testEncryptionType_aes_128() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, EncryptionType.AES_128);
        check(zipFile);
    }

    @Test
    public void testEncryptionType_aes_256() throws IOException, ZipException {
        Method method = new Object(){}.getClass().getEnclosingMethod();
        File zipFile = compress(method, EncryptionType.AES_256);
        check(zipFile);
    }

    private void check(File zipFile) {
        assertThat(zipFile.exists(), is(true));
        assertThat(zipFile.length(), greaterThan(0L));
    }

}
