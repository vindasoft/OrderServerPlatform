/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.utils;

import com.vindasoft.order.domain.OrderInfo;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @Description: 数据生产者（Excel数据读取器）
 * @author: jwd
 * @date: 2026-01-02
 */
@Slf4j
public class ExcelReader implements Runnable {
    private final String filePath;
    private final BlockingQueue<List<OrderInfo>> queue;
    private final int batchSize;
    private static final int PROGRESS_LOG_INTERVAL = 1000;

    public ExcelReader(String filePath, BlockingQueue<List<OrderInfo>> queue, int batchSize) {
        this.filePath = filePath;
        this.queue = queue;
        this.batchSize = batchSize;
    }

    @Override
    public void run() {
        try (FileInputStream fis = new FileInputStream(filePath);
            // 设置窗口大小，例如100行）
            Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<OrderInfo> batch = new ArrayList<>(batchSize);
            int totalRowsRead = 0;

            // 跳过标题行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row))
                    continue;

                try {
                    OrderInfo record = parseRow(row);
                    if (record != null) {
                        batch.add(record);

                        // 达到批次大小时发送批次数据
                        if (batch.size() >= batchSize) {
                            // queue.put：队列满时自动等待
                            queue.put(new ArrayList<>(batch));
                            batch.clear();
                        }
                    }
                } catch (Exception e) {
                    log.error("解析行数据失败: 行号：{}, 错误：", i, e);
                }
                totalRowsRead++;
                if (totalRowsRead % PROGRESS_LOG_INTERVAL == 0) {
                    log.info("已读取 {} 行数据", totalRowsRead);
                }
            }

            // 发送剩余数据
            if (!batch.isEmpty()) {
                queue.put(new ArrayList<>(batch));
            }

            // 发送结束标记
            queue.put(new ArrayList<>());

        } catch (Exception e) {
            log.error("读取Excel文件失败: ", e);
            try {
                queue.put(new ArrayList<>());
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                log.error("发送结束标记时被中断", ie);
            }
        }
    }

    private boolean isRowEmpty(Row row) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private OrderInfo parseRow(Row row) {
        try {
            Cell orderIdCell = row.getCell(0);
            Cell orderNameCell = row.getCell(1);
            Cell userIdCell = row.getCell(2);
            Cell productIdCell = row.getCell(3);
            Cell statusCell = row.getCell(4);
            Cell createTimeCell = row.getCell(5);
            Cell updateTimeCell = row.getCell(6);
            Cell remarkCell = row.getCell(7);

            String orderId = getCellValueAsString(orderIdCell);
            String orderName = getCellValueAsString(orderNameCell);
            String userId = getCellValueAsString(userIdCell);
            String productId = getCellValueAsString(productIdCell);
            String status = getCellValueAsString(statusCell);
            Date createTime = DateUtils.parse(getCellValueAsString(createTimeCell), DateUtils.DATETIME_PATTERN);
            Date updateTime = DateUtils.parse(getCellValueAsString(updateTimeCell), DateUtils.DATETIME_PATTERN);
            String remark = getCellValueAsString(remarkCell);

            if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId) || StringUtils.isBlank(
                productId) || StringUtils.isBlank(status) || createTime == null) {
                return null;
            }

            return OrderInfo.builder().orderId(orderId).orderName(orderName)
                .userId(userId).productId(productId).status(status).createTime(createTime)
                .updateTime(updateTime).remark(remark).build();
        } catch (Exception e) {
            log.error("读取Excel文件 parseRow 失败:", e);
            return null;
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return DateUtils.format(cell.getDateCellValue(), DateUtils.DATETIME_PATTERN);
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue)) {
                        return String.valueOf((long)numericValue);
                    }
                    return String.valueOf(numericValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}