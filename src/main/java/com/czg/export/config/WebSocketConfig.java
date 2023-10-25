package com.czg.export.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @ServerEndpoint 用于声明WebSocket响应类，有点像@RequestMapping	@ServerEndpoint(“/websocket”)
 * @OnOpen WebSocket连接时触发    参数有：Session session, EndpointConfig config
 * @OnMessage 有消息时触发    参数很多，一会再说
 * @OnClose 连接关闭时触发    参数有：Session session, CloseReason closeReason
 * @OnError 有异常时触发    参数有：Session session, Throwable throwable
 */
@Configuration
// 开启 WebSocket 支持
@EnableWebSocket
public class WebSocketConfig {

    /**
     * 必须要有的
     *
     * @return serverEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * WebSocket 配置信息
     *
     * @return servletServerContainerFactoryBean
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean bean = new ServletServerContainerFactoryBean();
        
        // 文本缓冲区大小
        bean.setMaxTextMessageBufferSize(8192);
        // 字节缓冲区大小
        bean.setMaxBinaryMessageBufferSize(8192);

        return bean;
    }

}
