package com.example.appoperation.component;

import com.example.appoperation.hbase.ActiveDaysPO;
import com.example.appoperation.hbase.TableInfo;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Objects;

/**
 * 用来 HBase 操作
 *
 * @author zhangxiaofan 2021/01/11-09:56
 */
public class HBaseComponent {

    private final Connection connection;

    public HBaseComponent(Connection connection) {
        this.connection = connection;
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final TableInfo activeDaysTable = new TableInfo("userinfo", "user_active_days_info", "info");
    private final byte[] c_activityDays1 = toBytes("activityDays1");
    private final byte[] c_activityDays3 = toBytes("activityDays3");
    private final byte[] c_activityDays7 = toBytes("activityDays7");
    private final byte[] c_activityDays14 = toBytes("activityDays14");
    private final byte[] c_activityDays30 = toBytes("activityDays30");
    private final byte[] c_activityDays60 = toBytes("activityDays60");
    private final byte[] c_dataCreationDate = toBytes("dataCreationDate");

    /**
     * to byte
     */
    private byte[] toBytes(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * to byte
     */
    private byte[] toBytes(Integer i) {
        String s = i.toString();
        return toBytes(s);
    }

    private String toString(byte[] bytes) {
        return Bytes.toString(bytes);
    }

    private Integer toInt(byte[] bytes) {
        String s = Bytes.toString(bytes);
        return NumberUtils.toInt(s);
    }

    /**
     * 获取该用户 活跃天数 的信息
     */
    public ActiveDaysPO getActiveDays(Integer userId) {
        ActiveDaysPO po = new ActiveDaysPO();
        po.setUserId(userId);
        try {
            String namespaceTableName = activeDaysTable.getNamespaceTableName();
            byte[] cf = activeDaysTable.getByteCF();

            String userIdMd5Hex = MD5Hash.getMD5AsHex(toBytes(userId.toString()));
            byte[] userIdMd5HexBytes = toBytes(userIdMd5Hex);

            try (Table table = connection.getTable(TableName.valueOf(namespaceTableName))) {
                Get get = new Get(userIdMd5HexBytes);
                Result rs = table.get(get);

                // key: column name, value: value of  column
                NavigableMap<byte[], byte[]> familyMap = rs.getFamilyMap(cf);
                if (Objects.isNull(familyMap))
                    return po;

                byte[] vBytes = familyMap.get(c_activityDays1);
                po.setActivityDays1(toInt(vBytes));

                vBytes = familyMap.get(c_activityDays3);
                po.setActivityDays3(toInt(vBytes));

                vBytes = familyMap.get(c_activityDays7);
                po.setActivityDays7(toInt(vBytes));

                vBytes = familyMap.get(c_activityDays14);
                po.setActivityDays14(toInt(vBytes));

                vBytes = familyMap.get(c_activityDays30);
                po.setActivityDays30(toInt(vBytes));

                vBytes = familyMap.get(c_activityDays60);
                po.setActivityDays60(toInt(vBytes));
                vBytes = familyMap.get(c_dataCreationDate);
                po.setDataCreationDate(toString(vBytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return po;
    }

    public List<ActiveDaysPO> findActiveDays() {
        List<ActiveDaysPO> list = new ArrayList<>();
        String namespaceTableName = activeDaysTable.getNamespaceTableName();
        byte[] cf = activeDaysTable.getByteCF();

        try {
            try (Table table = connection.getTable(TableName.valueOf(namespaceTableName))) {
                Scan scan = new Scan();
                ResultScanner scanner = table.getScanner(scan);
                for (Result rs : scanner) {
                    // key: column name, value: value of  column
                    NavigableMap<byte[], byte[]> familyMap = rs.getFamilyMap(cf);
//                    byte[] row = rs.getRow();
//                    Integer userId = toInt(row);

                    ActiveDaysPO po = new ActiveDaysPO();
//                  po.setUserId(userId); // userId md5 加密了，这里解析不出来

                    byte[] vBytes = familyMap.get(c_activityDays1);
                    po.setActivityDays1(toInt(vBytes));

                    vBytes = familyMap.get(c_activityDays3);
                    po.setActivityDays3(toInt(vBytes));

                    vBytes = familyMap.get(c_activityDays7);
                    po.setActivityDays7(toInt(vBytes));

                    vBytes = familyMap.get(c_activityDays14);
                    po.setActivityDays14(toInt(vBytes));

                    vBytes = familyMap.get(c_activityDays30);
                    po.setActivityDays30(toInt(vBytes));

                    vBytes = familyMap.get(c_activityDays60);
                    po.setActivityDays60(toInt(vBytes));
                    vBytes = familyMap.get(c_dataCreationDate);
                    po.setDataCreationDate(toString(vBytes));

                    list.add(po);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 构造一个 hbase 记录，用来做实验
     */
    public void buildActiveDay(ActiveDaysPO po) {
        String namespaceTableName = activeDaysTable.getNamespaceTableName();
        byte[] cf = activeDaysTable.getByteCF();
        Put p = new Put(toBytes(po.getUserId()));
        p.addColumn(cf, c_activityDays1, toBytes(po.getActivityDays1()));
        p.addColumn(cf, c_activityDays3, toBytes(po.getActivityDays3()));
        p.addColumn(cf, c_activityDays7, toBytes(po.getActivityDays7()));
        p.addColumn(cf, c_activityDays14, toBytes(po.getActivityDays14()));
        p.addColumn(cf, c_activityDays30, toBytes(po.getActivityDays30()));
        p.addColumn(cf, c_activityDays60, toBytes(po.getActivityDays60()));

        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(formatter);
        po.setDataCreationDate(nowStr);

        p.addColumn(cf, c_dataCreationDate, toBytes(nowStr));
        try {
            try (Table table = connection.getTable(TableName.valueOf(namespaceTableName))) {
                table.put(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
