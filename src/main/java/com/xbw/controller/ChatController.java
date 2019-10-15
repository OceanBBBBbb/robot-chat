package com.xbw.controller;


import com.xbw.bean.StaticStr;
import com.xbw.service.ChatService;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ChatController {

  @Autowired
  private ChatService chatService;

  @GetMapping("/ask")
  public ResponseEntity<String> getFileNameById(@RequestParam("askWord") String askWord){
//    String repMsg = chatService.getMsgByTuling(askWord);
    String repMsg = chatService.getMsgByMoli(askWord);
//    askWord = askWord.replace("?","!");
//    askWord = askWord.replace("吗","!");
//    int random = new Random().nextInt(8);
//    askWord = random<2?StaticStr.I_DONT_KNOWN :askWord;
    return  ResponseEntity.ok(repMsg);
  }

}
