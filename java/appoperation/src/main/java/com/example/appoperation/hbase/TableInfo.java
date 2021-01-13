package com.example.appoperation.hbase;

import java.nio.charset.StandardCharsets;

/**
 * hbase table 的基本信息
 * @author zhangxiaofan 2021/01/11-10:16
 */
public class TableInfo {
    private final String namespace;
    private final String tableName;
    private final String columnFamily; // 这里默认 columnFamily = "info"

    public TableInfo(String namespace, String tableName, String columnFamily) {
        this.namespace = namespace;
        this.tableName = tableName;
        this.columnFamily = columnFamily;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public String getNamespaceTableName() {
        return namespace + ":" + tableName;
    }

    public byte[] getByteCF() {
        return columnFamily.getBytes(StandardCharsets.UTF_8);
    }
}
