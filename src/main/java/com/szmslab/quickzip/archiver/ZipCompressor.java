/*
 * Copyright (c) 2015 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.quickzip.archiver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.util.Zip4jUtil;

import com.szmslab.quickzip.enums.CompressionMethod;
import com.szmslab.quickzip.enums.EncryptionType;

/**
 * Zip形式でのファイルの圧縮を行うクラスです。
 *
 * @author szmslab
 */
public class ZipCompressor {

    /**
     * ファイル名のエンコーディング。
     */
    private String encoding = InternalZipConstants.CHARSET_DEFAULT;

    /**
     * 圧縮方式。
     */
    private CompressionMethod compressionMethod = CompressionMethod.DEFLATE_NORMAL_HIGH;

    /**
     * 暗号化形式。
     */
    private EncryptionType encryptionType = EncryptionType.NO_ENCRYPTION;

    /**
     * 暗号化パスワード。
     */
    private String password;

    /**
     * Zipエントリのルートパス。
     */
    private String rootPathOfZipEntry = "";

    /**
     * コンストラクタです。
     */
    public ZipCompressor() {
        if (Zip4jUtil.isWindows()) {
            encoding("Windows-31J");
        }
    }

    /**
     * ファイル名のエンコーディングを取得します。
     *
     * @return ファイル名のエンコーディング
     */
    public String encoding() {
        return encoding;
    }

    /**
     * ファイル名のエンコーディングを設定します。
     *
     * @param encoding
     *            ファイル名のエンコーディング
     * @return 自身のインスタンス
     */
    public ZipCompressor encoding(String encoding) {
        if (Zip4jUtil.isStringNotNullAndNotEmpty(encoding)) {
            this.encoding = encoding;
        }
        return this;
    }

    /**
     * 圧縮方式を取得します。
     *
     * @return 圧縮方式
     */
    public CompressionMethod compressionMethod() {
        return compressionMethod;
    }

    /**
     * 圧縮方式を設定します。
     *
     * @param compressionMethod
     *            圧縮方式
     * @return 自身のインスタンス
     */
    public ZipCompressor compressionMethod(CompressionMethod compressionMethod) {
        if (compressionMethod != null) {
            this.compressionMethod = compressionMethod;
        }
        return this;
    }

    /**
     * 暗号化形式を取得します。
     *
     * @return 暗号化形式
     */
    public EncryptionType encryptionType() {
        return encryptionType;
    }

    /**
     * 暗号化パスワードを取得します。
     *
     * @return 暗号化パスワード
     */
    public String password() {
        return password;
    }

    /**
     * 暗号化形式を設定します。
     *
     * @param encryptionType
     *            暗号化形式
     * @param password
     *            暗号化パスワード
     * @return 自身のインスタンス
     */
    public ZipCompressor encryptionType(EncryptionType encryptionType, String password) {
        if (encryptionType != null) {
            this.encryptionType = encryptionType;
            this.password = password;
        }
        return this;
    }

    /**
     * Zipエントリのルートパスを取得します。
     *
     * @return Zipエントリのルートパス
     */
    public String rootPathOfZipEntry() {
        return rootPathOfZipEntry;
    }

    /**
     * Zipエントリのルートパスを設定します。
     *
     * @param rootPathOfZipEntry
     *            Zipエントリのルートパス
     * @return 自身のインスタンス
     */
    public ZipCompressor rootPathOfZipEntry(String rootPathOfZipEntry) {
        if (rootPathOfZipEntry == null) {
            rootPathOfZipEntry = "";
        } else {
            rootPathOfZipEntry = rootPathOfZipEntry.replaceAll("\\\\+", InternalZipConstants.ZIP_FILE_SEPARATOR);
            if (rootPathOfZipEntry.startsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
                rootPathOfZipEntry = rootPathOfZipEntry.substring(InternalZipConstants.ZIP_FILE_SEPARATOR.length());
            }
            if (!rootPathOfZipEntry.endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
                rootPathOfZipEntry += InternalZipConstants.ZIP_FILE_SEPARATOR;
            }
            this.rootPathOfZipEntry = rootPathOfZipEntry;
        }
        return this;
    }

    /**
     * 対象のファイルをZip形式で圧縮します。
     *
     * @param zipFilePath
     *            圧縮先のZipファイルパス
     * @param filePaths
     *            圧縮元のファイルパス
     * @return 圧縮先のZipファイル
     * @throws IOException
     * @throws ZipException
     */
    public File execute(String zipFilePath, String... filePaths) throws IOException, ZipException {
        File zipFile = new File(zipFilePath);
        File[] files = new File[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            files[i] = new File(filePaths[i]);
        }
        return execute(zipFile, files);
    }

    /**
     * 対象のファイルをZip形式で圧縮します。
     *
     * @param zipFile
     *            圧縮先のZipファイル
     * @param files
     *            圧縮元のファイル
     * @return 圧縮先のZipファイル
     * @throws IOException
     * @throws ZipException
     */
    public File execute(File zipFile, File... files) throws IOException, ZipException {
        // 出力先ディレクトリチェック（存在しなければ作成する）
        Zip4jUtil.checkOutputFolder(zipFile.getParent());

        // Zip圧縮
        ZipModel model = createZipModel();
        ZipParameters parameter = createZipParameters();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile), model);
            write(zos, parameter, files);
            zos.finish();
        } finally {
            if (zos != null) {
                zos.close();
            }
        }
        return zipFile;
    }

    /**
     * Zipモデルを生成します。
     *
     * @return Zipモデル
     */
    private ZipModel createZipModel() {
        ZipModel model = new ZipModel();
        model.setFileNameCharset(encoding);
        return model;
    }

    /**
     * Zipパラメータを生成します。
     *
     * @return Zipパラメータ
     */
    private ZipParameters createZipParameters() {
        ZipParameters parameter = new ZipParameters();

        // 圧縮方式
        switch (compressionMethod) {
        case STORE:
            parameter.setCompressionMethod(Zip4jConstants.COMP_STORE);
            break;
        default:
            parameter.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            break;
        }
        parameter.setCompressionLevel(compressionMethod.ordinal());

        // 暗号化形式
        switch (encryptionType) {
        case NO_ENCRYPTION:
            parameter.setEncryptFiles(false);
            parameter.setEncryptionMethod(Zip4jConstants.ENC_NO_ENCRYPTION);
            parameter.setAesKeyStrength(-1);
            parameter.setPassword((String) null);
            break;
        case ZIP_CRYPTO:
            parameter.setEncryptFiles(true);
            parameter.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            parameter.setAesKeyStrength(-1);
            parameter.setPassword(password);
            break;
        case AES_128:
            parameter.setEncryptFiles(true);
            parameter.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameter.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_128);
            parameter.setPassword(password);
            break;
        case AES_256:
            parameter.setEncryptFiles(true);
            parameter.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameter.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameter.setPassword(password);
            break;
        default:
            break;
        }

        // Zipエントリのルートパス
        parameter.setRootFolderInZip(rootPathOfZipEntry);

        return parameter;
    }

    /**
     * Zip出力ストリームに対象ファイルのデータを書き込みます。
     *
     * @param zos
     *            Zip出力ストリーム
     * @param parameter
     *            Zipパラメータ
     * @param files
     *            圧縮対象のファイル
     * @throws IOException
     * @throws ZipException
     */
    private void write(ZipOutputStream zos, ZipParameters parameter, File... files) throws IOException, ZipException {
        for (File file : files) {
            File[] childFiles = file.listFiles();
            if (childFiles != null && childFiles.length > 0) {
                String parentPath = parameter.getRootFolderInZip();
                parameter.setRootFolderInZip(parentPath + file.getName());
                write(zos, parameter, childFiles);
                parameter.setRootFolderInZip(parentPath);
            } else {
                zos.putNextEntry(file, parameter);
                if (!file.isDirectory()) {
                    BufferedInputStream bis = null;
                    try {
                        bis = new BufferedInputStream(new FileInputStream(file));
                        byte [] buffer = new byte[8096];
                        int len = 0;
                        while ((len = bis.read(buffer)) >= 0) {
                            zos.write(buffer, 0, len);
                        }
                    } finally {
                        if (bis != null) {
                            bis.close();
                        }
                    }
                }
                zos.closeEntry();
            }
        }
    }

}
