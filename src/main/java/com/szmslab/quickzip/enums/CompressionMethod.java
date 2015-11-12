/*
 * Copyright (c) 2015 szmslab
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package com.szmslab.quickzip.enums;

/**
 * Zipファイルの圧縮方式です。
 *
 * @author szmslab
 */
public enum CompressionMethod {

    /**
     * 無圧縮。
     */
    STORE,

    /**
     * DEFLATE（圧縮レベル1 = 圧縮率：最小、圧縮速度：最高）。
     */
    DEFLATE_FASTEST,

    /**
     * DEFLATE（圧縮レベル2 = 圧縮率：小、圧縮速度：高）。
     */
    DEFLATE_FASTER,

    /**
     * DEFLATE（圧縮レベル3 = 圧縮率：小、圧縮速度：高）。
     */
    DEFLATE_FAST,

    /**
     * DEFLATE（圧縮レベル4 = 圧縮率：小、圧縮速度：高）。
     */
    DEFLATE_NORMAL_FAST,

    /**
     * DEFLATE（圧縮レベル5 = 圧縮率：中、圧縮速度：中）。
     */
    DEFLATE_NORMAL,

    /**
     * DEFLATE（圧縮レベル6 = 圧縮率：大、圧縮速度：低）。
     */
    DEFLATE_NORMAL_HIGH,

    /**
     * DEFLATE（圧縮レベル7 = 圧縮率：大、圧縮速度：低）。
     */
    DEFLATE_HIGH,

    /**
     * DEFLATE（圧縮レベル8 = 圧縮率：大、圧縮速度：低）。
     */
    DEFLATE_HIGHER,

    /**
     * DEFLATE（圧縮レベル9 = 圧縮率：最大、圧縮速度：最低）。
     */
    DEFLATE_HIGHEST;

}
