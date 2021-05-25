package com.example.ms.starter.canal.handler.impl;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.example.ms.starter.canal.handler.AbstractMessageHandler;
import com.example.ms.starter.canal.handler.RowDataHandler;
import com.example.ms.starter.canal.handler.EntryHandler;

import java.util.List;

/**
 * -- SyncMessageHandlerImpl
 *
 * @author Lmay Zhou
 * @date 2021/3/22 11:27
 * @email lmay@lmaye.com
 */
public class SyncMessageHandlerImpl extends AbstractMessageHandler {
    public SyncMessageHandlerImpl(List<? extends EntryHandler> entryHandlers, RowDataHandler<CanalEntry.RowData> rowDataHandler) {
        super(entryHandlers, rowDataHandler);
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
    }
}