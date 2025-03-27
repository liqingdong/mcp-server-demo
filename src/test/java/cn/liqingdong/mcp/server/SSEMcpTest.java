package cn.liqingdong.mcp.server;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * mcp sse 模式junit测试
 */
public class SSEMcpTest {

    private static final String host = "localhost";
    private static final int port = 8081;
    private static Process serverProcess;
    private McpSyncClient mcpClient;

    @BeforeAll
    public static void init() throws IOException {
        String projectDir = System.getProperty("user.dir");
        String tempJarFilePath = projectDir + "/target/mcp-server-demo-0.0.1-SNAPSHOT.jar";
        File jarFile = new File(tempJarFilePath);
        Assert.isTrue(jarFile.exists(), "未找到 target 目录下的 JAR 文件");
        String jarFilePath = jarFile.getAbsolutePath();


        // start the jar if not running.
        try (Socket ignored = new Socket(host, port)) {
            System.out.printf("SSE 服务已开启，访问地址：http://%s:%d/sse%n", host, port);
        } catch (IOException e) {
            System.out.printf("SSE 服务未开启或无法连接，正在开启中...%n");
            ProcessBuilder process = new ProcessBuilder("java", "-jar", jarFilePath);
            serverProcess = process.start();
            System.out.printf("SSE 服务启动成功，访问地址：http://%s:%d/sse%n", host, port);
        }

        // 确保SSE 服务启动成功
        Assertions.assertDoesNotThrow(() -> TimeUnit.SECONDS.sleep(5));
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
        Assert.isTrue(contentList.size() == 1, "mcpClient.callTool 返回有误");
        McpSchema.TextContent textContent = (McpSchema.TextContent) contentList.get(0);
        String res = textContent.text();
        Assert.hasText(res, "textContent 返回为空");
        System.out.println(res);

    }

    @Test
    public void testJudgeEvenNumber() {
        McpSchema.CallToolResult weather = mcpClient.callTool(new McpSchema.CallToolRequest("judgeEvenNumber", Map.of("num", 79)));

        List<McpSchema.Content> contentList = weather.content();
        Assert.isTrue(contentList.size() == 1, "mcpClient.callTool 返回有误");
        McpSchema.TextContent textContent = (McpSchema.TextContent) contentList.get(0);
        String res = textContent.text();
        Assert.hasText(res, "textContent 返回为空");
        System.out.println(res);

    }

    @Test
    public void testJudgePrimeNumber() {
        McpSchema.CallToolResult weather = mcpClient.callTool(new McpSchema.CallToolRequest("judgePrimeNumber", Map.of("num", 217)));

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
        mcpClient = McpClient.sync(new HttpClientSseClientTransport(String.format("http://%s:%d", host, port)))
                .build();
        System.out.println("mcpClient 初始化完成");

    }

    @AfterEach
    public void tearDown() {
        if (null != mcpClient) {
            mcpClient.closeGracefully();
            System.out.println("mcpClient已关闭");
        }
        if (null != serverProcess) {
            System.out.println("serverProcess已关闭");
            serverProcess.destroy();
        }
    }


}


