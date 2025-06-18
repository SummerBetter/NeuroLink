package com.hxun.neuroai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class ChatController {

    private final ChatClient chatClient;

    @RequestMapping("/chat")
    public String chat(String prompt){
        return chatClient.prompt()
                .user(prompt)
                .call() // 阻塞式
                .content();
    }
    @RequestMapping(value = "/chatStream", produces = "text/html;charset=utf-8")  //确保输出中文
    public Flux<String> chatStream(String prompt, String chatId){
        return chatClient.prompt()
                .user(prompt)
                .stream() // 流式响应
                .content();
    }

}
