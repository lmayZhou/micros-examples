package com.example.ms.starter.canal.factory;

import com.example.ms.starter.canal.enums.TableNameEnum;
import com.example.ms.starter.canal.handler.EntryHandler;
import com.example.ms.starter.canal.utils.GenericUtil;
import com.example.ms.starter.canal.utils.HandlerUtil;

/**
 * -- AbstractModelFactory
 *
 * @author Lmay Zhou
 * @date 2021/3/22 11:27
 * @email lmay@lmaye.com
 */
public abstract class AbstractModelFactory<T> implements IModelFactory<T> {
    @Override
    public <R> R newInstance(EntryHandler entryHandler, T t) throws Exception {
        String canalTableName = HandlerUtil.getCanalTableName(entryHandler);
        if (TableNameEnum.ALL.name().toLowerCase().equals(canalTableName)) {
            return (R) t;
        }
        Class<R> tableClass = GenericUtil.getTableClass(entryHandler);
        if (tableClass != null) {
            return newInstance(tableClass, t);
        }
        return null;
    }

    abstract <R> R newInstance(Class<R> c, T t) throws Exception;
}