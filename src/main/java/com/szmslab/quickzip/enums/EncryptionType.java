/*
 * Copyright (c) 2015 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.quickzip.enums;

/**
 * Zipファイルの暗号化形式です。
 *
 * @author szmslab
 */
public enum EncryptionType {

    /**
     * 暗号化なし。
     */
    NO_ENCRYPTION,

    /**
     * Zipcryptoを使用した暗号化。
     */
    ZIP_CRYPTO,

    /**
     * AES-128を使用した暗号化。
     */
    AES_128,

    /**
     * AES-256を使用した暗号化。
     */
    AES_256;

}
