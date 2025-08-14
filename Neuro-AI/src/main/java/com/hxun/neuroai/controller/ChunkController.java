package com.hxun.neuroai.controller;

import com.hxun.neuroai.service.ChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件分块控制器
 */
@RestController
@RequestMapping("/api/chunk")
public class ChunkController {

    @Autowired
    private ChunkService chunkService;

    /**
     * 按固定大小分块PDF文件。
     *
     * @param file      上传的PDF文件
     * @param chunkSize 每个分块的大小
     * @return a {@link ResponseEntity} object.
     */
    @PostMapping("/fixed-size")
    public ResponseEntity<String> chunkByFixedSize(@RequestParam("file") MultipartFile file, @RequestParam("chunkSize") int chunkSize) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("上传文件不能为空");
        }
        try {
            chunkService.chunkByFixedSize(file, chunkSize);
            return ResponseEntity.ok("文件按固定大小分块成功，请查看控制台输出。");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("文件处理失败: " + e.getMessage());
        }
    }

    /**
     * 按段落分块PDF文件。
     *
     * @param file 上传的PDF文件
     * @return a {@link ResponseEntity} object.
     */
    @PostMapping("/paragraph")
    public ResponseEntity<String> chunkByParagraph(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("上传文件不能为空");
        }
        try {
            chunkService.chunkByParagraph(file);
            return ResponseEntity.ok("文件按段落分块成功，请查看控制台输出。");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("文件处理失败: " + e.getMessage());
        }
    }
}
