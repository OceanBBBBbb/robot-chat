package com.xbw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xbw.api.util.OkHttpClientUtil;
import com.xbw.service.ChatService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatServiceimpl implements ChatService {

  final String url = "http://openapi.tuling123.com/openapi/api/v2";
  final String apiKey = "e9b00076828047f5a33d363b7f6ab580";
  final String userId = "a0f208c72bf187ba";

  @Override
  public String getMsgByTuling(String askWord) {
 // 先在这里连吧
    Map<String, Object> outP = new TreeMap<>();
    Map<String, Object> perception = new HashMap<>();
    Map<String, Object> inputText = new HashMap<>();
    Map<String, Object> userInfo = new HashMap<>();

    inputText.put("text",askWord);
    perception.put("inputText",inputText);
    outP.put("perception",perception);

    userInfo.put("apiKey",apiKey);
    userInfo.put("userId",userId);
    outP.put("userInfo",userInfo);
    String s = JSONObject.toJSONString(outP);
    JSONObject jsonObject = doCommonGetAndPost(url, s);
    log.info("得到的结果：" + jsonObject.toJSONString());
    JSONArray results = (JSONArray)jsonObject.get("results");
    String string = results.getString(0);
    JSONObject jsonObject1 = JSONObject.parseObject(string);
    String last3 = (String) jsonObject1.getString("values");
    String substring = (String)JSONObject.parseObject(last3).get("text");
    substring = substring.replaceAll("图灵","小海");
    substring = substring.replaceAll("tuling","xiaohai");
    return substring;
  }

  @Override
  public String getMsgByMoli(String askWord) {
    OkHttpClient client = new OkHttpClient();
    String encode = "";
    try {
      encode = URLEncoder.encode(askWord, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return "发了啥，看不懂";
    }
    Request request = new Request.Builder()
        .url("http://i.itpk.cn/api.php?question="+encode+"&api_key=a4e8aefe15e3294bba66164b35ed717c&api_secret=uyo0pp2378eh")
        .get()
        .addHeader("cache-control", "no-cache")
        .addHeader("Postman-Token", "59ed7f9c-bb57-4687-8746-4769a09adda2")
        .build();

    try {
      Response response = client.newCall(request).execute();
      return response.body().string();
    } catch (IOException e) {
      e.printStackTrace();
      return "你说什么我听不明白,不过你长得挺好看的";
    }
  }

  private JSONObject doCommonGetAndPost(String url, String jsonStr) {
    String result = null;
    Map<String, String> headers = new TreeMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("User-Agent",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
    try {
        result = OkHttpClientUtil.doPost(url,headers, jsonStr);
    } catch (Exception e) {
      log.info("HuobiApi httpClient error:{}",e);
    }
    log.info(result);
    JSONObject jsonObject = JSON.parseObject(result);
    return jsonObject;
  }
}
