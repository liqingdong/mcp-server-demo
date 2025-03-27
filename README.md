# MCP Server Demo 项目文档

## 项目简介

这是一个基于Spring Boot 3.4.4的MCP（Model Control Protocol）服务示例项目，集成Spring
AI工具链实现智能服务功能。项目包含基础数值运算、天气查询等服务的实现模板。

## 核心功能

### 1. **数字服务**（`NumberService`）

- 奇偶数判断
- 素数检测（已实现）
- 扩展建议：可添加质因数分解、斐波那契计算等

### 2. **天气服务**（`WeatherService`）

- 天气数据接口（当前使用随机数伪代码。真实需对接第三方API实现，可参考[OpenWeatherMap API](https://openweathermap.org/api)）

### 3. **MCP工具支持**

- 通过`@Tool`注解暴露服务为标准化工具
- 通过`@ToolParam`注解定义服务参数

---

## 项目结构

```text
src
|-- main
|  |-- java
|  |  `-- cn
|  |    `-- liqingdong
|  |      `-- mcp
|  |        `-- server
|  |          |-- McpApplication.java
|  |          `-- service
|  |            |-- NumberService.java
|  |            `-- WeatherService.java
|  `-- resources
|    `-- application.yml
`-- test
  `-- java
    `-- cn
      `-- liqingdong
        `-- mcp
          `-- server
            |-- McpApplicationTest.java
            `-- SSEMcpTest.java
            `-- STDIOMcpTest.java

```

---

## 快速开始

### 环境要求

- JDK 17
- Maven 3.8+
- IDE（推荐IntelliJ IDEA）

### 运行步骤

#### 1. 克隆仓库

```bash
git clone https://github.com/liqingdong/mcp-server-demo.git 
```

#### 2. 构建项目

```bash
mvn clean install
```

#### 3. 使用服务

##### 3.1 使用STDIO模式

###### 3.1.1 配置启动命令

```bash 
java -jar -Dspring.ai.mcp.server.stdio=true target/mcp-server-demo-0.0.1-SNAPSHOT.jar
```

###### 3.1.2 使用支持MCP工具的客户端，例如：Cherry Studio 进行测试

##### 3.2 使用SSE模式

###### 3.2.1 配置启动命令(默认访问地址为：http://localhost:8081/sse)

```bash
java -jar target/mcp-server-demo-0.0.1-SNAPSHOT.jar 
```

---

### 核心测试类

| 测试类名                        | 测试内容         |
|-----------------------------|--------------|
| `McpApplicationTest`        | 应用上下文加载验证    |
| `SSEMcpTest`/`StdioMcpTest` | 不同协议的MCP服务测试 |

---

## 开发规范

1. **代码风格**：遵循Spring官方编码规范
2. **工具注解**：所有服务方法需用`@Tool`标注，参数需用`@ToolParam`标注
3. **测试覆盖率**：核心功能需达到80%+单元测试覆盖

---

## 贡献指南

1. Fork仓库 → 创建功能分支
2. 实现新功能/修复问题
3. 添加对应单元测试
4. 提交Pull Request至`main`分支

## 项目状态

[![Java](https://img.shields.io/badge/Java-v17-blue.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-v3.4.4-green.svg)](https://spring.io/)
[![Maven](https://img.shields.io/badge/Maven-v3.8+-orange.svg)](https://maven.apache.org/)
[![MCP](https://img.shields.io/badge/MCP-v0.7.0-blue.svg)](https://modelcontextprotocol.io/introduction)
[![junit](https://img.shields.io/badge/junit-v5-red.svg)](https://junit.org/junit5/)
