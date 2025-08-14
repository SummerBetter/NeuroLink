package com.hxun.neuroai.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件分块服务
 */
public interface ChunkService {

    /**
     * 按固定大小分块
     *
     * @param file 文件
     * @param chunkSize 分块大小
     */
    void chunkByFixedSize(MultipartFile file, int chunkSize) throws IOException;

    /**
     * 按段落分块
     *
     * @param file 文件
     */
    void chunkByParagraph(MultipartFile file) throws IOException;
}
