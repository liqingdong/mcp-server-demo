package cn.liqingdong.mcp.server;

import cn.liqingdong.mcp.server.service.NumberService;
import cn.liqingdong.mcp.server.service.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpApplication.class, args);
    }


    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherService)
                .build();
    }

    @Bean
    public ToolCallbackProvider numberTools(NumberService numberService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(numberService)
                .build();
    }
}
