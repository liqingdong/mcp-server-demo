package cn.liqingdong.mcp.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeepSeekService {
    @Value("${api-key.deepseek}")
    private String deepSeekToken;

    @Tool(description = "获取DeepSeek平台的余额信息")
    public String getBalance() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder().url("https://api.deepseek.com/user/balance")
                    .method("GET", null)
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + deepSeekToken)
                    .build()
                    ;

            Response response = client.newCall(request)
                    .execute();
            assert response.body() != null;
            String jsonStr = response.body()
                    .string();
            Map map = new ObjectMapper().readValue(jsonStr, Map.class);
            // @formatter:off
            List<Map<String, Object>> balanceInfos = (List<Map<String, Object>>)map.get("balance_infos");
            return "DeepSeek平台的余额信息为：" +
                   "\n  - 当前账户是否有余额可供API调用: "+ map.get("is_available")+
                   "\n  - 当前账户余额货币，人民币CNY或美元USD: "+balanceInfos.get(0).get("currency") +
                   "\n  - 当前账户总的可用余额，包括赠金和充值余额: "+balanceInfos.get(0).get("total_balance") +
                   "\n  - 当前账户未过期的赠金余额: "+balanceInfos.get(0).get("granted_balance") +
                   "\n  - 当前账户充值余额: "+balanceInfos.get(0).get("topped_up_balance") ;
            // @formatter:off
        } catch (Exception e) {
            return "获取DeepSeek平台的余额信息失败";
        }

    }
}
