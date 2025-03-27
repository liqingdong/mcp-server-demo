package cn.liqingdong.mcp.server.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service
public class WeatherService {
    @Tool(description = "获取城市的温度")
    public String getWeather(@ToolParam(description = "城市名字") String city) throws UnsupportedEncodingException {
        city = new String(city.getBytes("GBK"), StandardCharsets.UTF_8);
        int temperature = new Random().nextInt(28) + 5;// 随机5-32的温度
        return city + "的温度是" + temperature + "摄氏度";
    }

}
