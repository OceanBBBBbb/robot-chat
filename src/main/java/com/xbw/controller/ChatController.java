package com.xbw.controller;


import com.xbw.bean.StaticStr;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ChatController {

  @GetMapping("/ask")
  public ResponseEntity<String> getFileNameById(@RequestParam("askWord") String askWord){
    askWord = askWord.replace("?","!");
    askWord = askWord.replace("吗","!");
    int random = new Random().nextInt(8);
    askWord = random<2?StaticStr.I_DONT_KNOWN :askWord;
    return  ResponseEntity.ok(askWord);
  }

}
