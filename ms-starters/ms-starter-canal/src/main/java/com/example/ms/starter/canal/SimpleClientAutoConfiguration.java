package com.example.ms.starter.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.example.ms.starter.canal.client.SimpleCanalClient;
import com.example.ms.starter.canal.factory.EntryColumnModelFactory;
import com.example.ms.starter.canal.handler.EntryHandler;
import com.example.ms.starter.canal.handler.MessageHandler;
import com.example.ms.starter.canal.handler.RowDataHandler;
import com.example.ms.starter.canal.handler.impl.AsyncMessageHandlerImpl;
import com.example.ms.starter.canal.handler.impl.RowDataHandlerImpl;
import com.example.ms.starter.canal.handler.impl.SyncMessageHandlerImpl;
import com.example.ms.starter.canal.properties.CanalProperties;
import com.example.ms.starter.canal.properties.CanalSimpleProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Configuration
@EnableConfigurationProperties(CanalSimpleProperties.class)
@ConditionalOnBean(value = {EntryHandler.class})
@ConditionalOnProperty(value = CanalProperties.CANAL_MODE, havingValue = "simple", matchIfMissing = true)
@Import(ThreadPoolAutoConfiguration.class)
@ComponentScan(value = "com.example.ms.starter.canal")
public class SimpleClientAutoConfiguration {
    private final CanalSimpleProperties canalSimpleProperties;

    public SimpleClientAutoConfiguration(CanalSimpleProperties canalSimpleProperties) {
        this.canalSimpleProperties = canalSimpleProperties;
    }

    @Bean
    public RowDataHandler<CanalEntry.RowData> rowDataHandler() {
        return new RowDataHandlerImpl(new EntryColumnModelFactory());
    }

    @Bean
    @ConditionalOnProperty(value = CanalProperties.CANAL_ASYNC, havingValue = "true", matchIfMissing = true)
    public MessageHandler messageHandler(RowDataHandler<CanalEntry.RowData> rowDataHandler, List<EntryHandler> entryHandlers,
                                         ExecutorService executorService) {
        return new AsyncMessageHandlerImpl(entryHandlers, rowDataHandler, executorService);
    }

    @Bean
    @ConditionalOnProperty(value = CanalProperties.CANAL_ASYNC, havingValue = "false")
    public MessageHandler messageHandler(RowDataHandler<CanalEntry.RowData> rowDataHandler, List<EntryHandler> entryHandlers) {
        return new SyncMessageHandlerImpl(entryHandlers, rowDataHandler);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SimpleCanalClient simpleCanalClient(MessageHandler messageHandler) {
        String server = canalSimpleProperties.getServer();
        String[] array = server.split(":");
        return SimpleCanalClient.builder()
                .hostname(array[0])
                .port(Integer.parseInt(array[1]))
                .destination(canalSimpleProperties.getDestination())
                .userName(canalSimpleProperties.getUserName())
                .password(canalSimpleProperties.getPassword())
                .messageHandler(messageHandler)
                .batchSize(canalSimpleProperties.getBatchSize())
                .filter(canalSimpleProperties.getFilter())
                .timeout(canalSimpleProperties.getTimeout())
                .unit(canalSimpleProperties.getUnit())
                .build();
    }
}
