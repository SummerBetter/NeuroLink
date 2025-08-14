package com.hxun.neuroai.service.impl;

import com.hxun.neuroai.service.ChunkService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkServiceImpl implements ChunkService {

    /**
     * 按固定大小将PDF文件内容分块。
     *
     * @param file      上传的PDF文件
     * @param chunkSize 每个分块的字符数
     * @throws IOException 如果文件读取或解析出错
     */
    @Override
    public void chunkByFixedSize(MultipartFile file, int chunkSize) throws IOException {
        // 从MultipartFile获取输入流并读取为字节数组
        byte[] fileBytes = file.getBytes();
        // 使用 Loader 类加载PDF文档
        try (PDDocument document = Loader.loadPDF(fileBytes)) {
            // 创建PDFTextStripper对象以提取文本
            PDFTextStripper pdfStripper = new PDFTextStripper();
            // 提取PDF中的所有文本
            String text = pdfStripper.getText(document);

            // 按指定大小进行分块
            List<String> chunks = new ArrayList<>();
            for (int i = 0; i < text.length(); i += chunkSize) {
                int end = Math.min(i + chunkSize, text.length());
                chunks.add(text.substring(i, end));
            }

            // 打印分块结果
            System.out.println("--- 按固定大小分块结果 ---");
            chunks.forEach(chunk -> {
                System.out.println("--- CHUNK ---");
                System.out.println(chunk);
            });
            System.out.println("--- 分块结束 ---");
        }
    }

    /**
     * 按段落将PDF文件内容分块。
     *
     * @param file 上传的PDF文件
     * @throws IOException 如果文件读取或解析出错
     */
    @Override
    public void chunkByParagraph(MultipartFile file) throws IOException {
        // 从MultipartFile获取输入流并读取为字节数组
        byte[] fileBytes = file.getBytes();
        // 使用 Loader 类加载PDF文档
        try (PDDocument document = Loader.loadPDF(fileBytes)) {
            // 创建PDFTextStripper对象以提取文本
            PDFTextStripper pdfStripper = new PDFTextStripper();
            // 提取PDF中的所有文本
            String text = pdfStripper.getText(document);

            // 按段落进行分块（这里简单地以换行符作为段落分隔）
            // 注意：这种方法对于复杂的PDF布局可能不够精确
            String[] paragraphs = text.split("\\r?\\n\\r?\\n"); // 匹配一个或多个空行

            // 打印分块结果
            System.out.println("--- 按段落分块结果 ---");
            for (String paragraph : paragraphs) {
                if (!paragraph.trim().isEmpty()) {
                    System.out.println("--- CHUNK ---");
                    System.out.println(paragraph.trim());
                }
            }
            System.out.println("--- 分块结束 ---");
        }
    }
}
