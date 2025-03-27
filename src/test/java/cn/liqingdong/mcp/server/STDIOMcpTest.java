package cn.liqingdong.mcp.server;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * mcp stdio 模式junit测试
 */
public class STDIOMcpTest {

    private static String jarFilePath;
    private McpSyncClient mcpClient;

    @BeforeAll
    public static void init() {
        String projectDir = System.getProperty("user.dir");
        String tempJarFilePath = projectDir + "/target/mcp-server-demo-0.0.1-SNAPSHOT.jar";
        File jarFile = new File(tempJarFilePath);
        Assertions.assertTrue(jarFile.exists(), "未找到 target 目录下的 JAR 文件");
        jarFilePath = jarFile.getAbsolutePath();
    }

    @Test
    public void listTools() {
        McpSchema.ListToolsResult listToolsResult = mcpClient.listTools();
        System.out.println(listToolsResult);
    }

    @Test
    public void testGetWeather() {
        McpSchema.CallToolResult weather = mcpClient.callTool(new McpSchema.CallToolRequest("getWeather", Map.of("city", "南京")));

        List<McpSchema.Content> contentList = weather.content();
        Assertions.assertEquals(1, contentList.size(), "mcpClient.callTool 返回有误");
        McpSchema.TextContent textContent = (McpSchema.TextContent) contentList.get(0);
        String res = textContent.text();
        Assertions.assertTrue(StringUtils.hasText(res), "textContent 返回为空");
        System.out.println(res);

    }

    @Test
    public void testJudgeEvenNumber() {
        McpSchema.CallToolResult weather = mcpClient.callTool(new McpSchema.CallToolRequest("judgeEvenNumber", Map.of("num", 79)));

        List<McpSchema.Content> contentList = weather.content();
        Assertions.assertEquals(1, contentList.size(), "mcpClient.callTool 返回有误");
        McpSchema.TextContent textContent = (McpSchema.TextContent) contentList.get(0);
        String res = textContent.text();
        Assertions.assertTrue(StringUtils.hasText(res), "textContent 返回为空");
        System.out.println(res);

    }

    @Test
    public void testJudgePrimeNumber() {
        McpSchema.CallToolResult weather = mcpClient.callTool(new McpSchema.CallToolRequest("judgePrimeNumber", Map.of("num", 39)));

        List<McpSchema.Content> contentList = weather.content();
        Assert.isTrue(contentList.size() == 1, "mcpClient.callTool 返回有误");
        McpSchema.TextContent textContent = (McpSchema.TextContent) contentList.get(0);
        String res = textContent.text();
        Assert.hasText(res, "textContent 返回为空");
        System.out.println(res);

    }

    @Test
    public void testGetBalance() {
        McpSchema.CallToolResult weather = mcpClient.callTool(new McpSchema.CallToolRequest("getBalance",Map.of()));

        List<McpSchema.Content> contentList = weather.content();
        Assert.isTrue(contentList.size() == 1, "mcpClient.callTool 返回有误");
        McpSchema.TextContent textContent = (McpSchema.TextContent) contentList.get(0);
        String res = textContent.text();
        Assert.hasText(res, "textContent 返回为空");
        System.out.println(res);

    }


    @BeforeEach
    public void setUp() {
        ServerParameters serverParameters = ServerParameters.builder("java")
                .args("-jar", "-Dspring.ai.mcp.server.stdio=true", jarFilePath)
                .build()
                ;
        mcpClient = McpClient.sync(new StdioClientTransport(serverParameters))
                .build();
        System.out.println("mcpClient 初始化完成");
    }

    @AfterEach
    public void tearDown() {
        if (null != mcpClient) {
            mcpClient.closeGracefully();
            System.out.println("mcpClient已关闭");
        }
    }


}


