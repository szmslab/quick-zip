/*
 * Copyright (c) 2015 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.quickzip.archiver;

import java.io.File;
import java.io.IOException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.util.InternalZipConstants;
import net.lingala.zip4j.util.Zip4jUtil;

/**
 * Zip形式でのファイルの解凍を行うクラスです。
 *
 * @author szmslab
 */
public class ZipExtractor {

    /**
     * ファイル名のエンコーディング。
     */
    private String encoding = InternalZipConstants.CHARSET_DEFAULT;

    /**
     * 解凍先にディレクトリを作成するかどうか。
     */
    private boolean autoCreateDirectory = false;

    /**
     * コンストラクタ
     */
    public ZipExtractor() {
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
    public ZipExtractor encoding(String encoding) {
        if (Zip4jUtil.isStringNotNullAndNotEmpty(encoding)) {
            this.encoding = encoding;
        }
        return this;
    }

    /**
     * 解凍先にディレクトリを作成するかどうかを取得します。
     *
     * @return 解凍先にディレクトリを作成するかどうか
     */
    public boolean autoCreateDirectory() {
        return autoCreateDirectory;
    }

    /**
     * 解凍先にディレクトリを作成するかどうかを設定します。
     *
     * @param autoCreateDirectory
     *            解凍先にディレクトリを作成するかどうか
     * @return 自身のインスタンス
     */
    public ZipExtractor autoCreateDirectory(boolean autoCreateDirectory) {
        this.autoCreateDirectory = autoCreateDirectory;
        return this;
    }

    /**
     * 対象のファイルをZip形式で解凍します。
     *
     * @param directoryPath
     *            解凍先のディレクトリパス
     * @param zipFilePath
     *            解凍元のZipファイルパス
     * @return 解凍先のディレクトリ
     * @throws IOException
     * @throws ZipException
     */
    public File execute(String directoryPath, String zipFilePath) throws IOException, ZipException {
        return execute(new File(directoryPath), new File(zipFilePath));
    }

    /**
     * 対象のファイルをZip形式で解凍します。
     *
     * @param directoryPath
     *            解凍先のディレクトリパス
     * @param zipFilePath
     *            解凍元のZipファイルパス
     * @param password
     *            パスワード
     * @return 解凍先のディレクトリ
     * @throws IOException
     * @throws ZipException
     */
    public File execute(String directoryPath, String zipFilePath, String password) throws IOException, ZipException {
        return execute(new File(directoryPath), new File(zipFilePath), password);
    }

    /**
     * 対象のファイルをZip形式で解凍します。
     *
     * @param directory
     *            解凍先のディレクトリ
     * @param zipFile
     *            解凍元のZipファイル
     * @return 解凍先のディレクトリ
     * @throws IOException
     * @throws ZipException
     */
    public File execute(File directory, File zipFile) throws IOException, ZipException {
        return execute(directory, zipFile, null);
    }

    /**
     * 対象のファイルをZip形式で解凍します。
     *
     * @param directory
     *            解凍先のディレクトリ
     * @param zipFile
     *            解凍元のZipファイル
     * @param password
     *            パスワード
     * @return 解凍先のディレクトリ
     * @throws IOException
     * @throws ZipException
     */
    public File execute(File directory, File zipFile, String password) throws IOException, ZipException {
        // 解凍先のディレクトリの配下に、解凍元のZipファイル名と同じ名前のディレクトリを設定
        if (autoCreateDirectory) {
            String path = directory.getPath();
            if (!path.endsWith("\\") && !path.endsWith("/")) {
                path += InternalZipConstants.FILE_SEPARATOR;
            }
            directory = new File(path + Zip4jUtil.getZipFileNameWithoutExt(zipFile.getName()));
        }

        // 出力先ディレクトリチェック（存在しなければ作成する）
        Zip4jUtil.checkOutputFolder(directory.getPath());

        // Zip解凍
        ZipFile zip = new ZipFile(zipFile);
        zip.setFileNameCharset(encoding);
        if (zip.isEncrypted() && Zip4jUtil.isStringNotNullAndNotEmpty(password)) {
            zip.setPassword(password);
        }
        zip.extractAll(directory.getCanonicalPath());

        return directory;
    }

}
