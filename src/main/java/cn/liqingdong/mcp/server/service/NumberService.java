package cn.liqingdong.mcp.server.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class NumberService {

    @Tool(description = "判断一个数是奇数还是偶数")
    public String judgeEvenNumber(@ToolParam(description = "待判断的数字") Integer num) {
        return num + (num % 2 == 0 ? "是偶数" : "是奇数");
    }

    @Tool(description = "判断一个数是不是素数")
    public String judgePrimeNumber(@ToolParam(description = "待判断的数字") Integer num) {
        if (num <= 1) {
            return num + "不是素数";
        }
        if (num == 2) {
            return num + "是素数";
        }
        if (num % 2 == 0) {
            return num + "不是素数";
        }
        int sqrt = (int) Math.sqrt(num);
        for (int i = 3; i <= sqrt; i += 2) {
            if (num % i == 0) {
                return num + "不是素数";
            }
        }
        return num + "是素数";
    }


}