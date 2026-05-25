/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.utils;

import com.vindasoft.order.domain.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 数据处理者（消费者）
 * @author: jwd
 * @date: 2026-01-02
 */
@Slf4j
public class DataProcessor implements Runnable {
    private final BlockingQueue<List<OrderInfo>> queue;
    private final SqlSessionFactory sqlSessionFactory;
    private final AtomicInteger processedCount;

    public DataProcessor(BlockingQueue<List<OrderInfo>> queue, SqlSessionFactory sqlSessionFactory,
        AtomicInteger processedCount) {
        this.queue = queue;
        this.sqlSessionFactory = sqlSessionFactory;
        this.processedCount = processedCount;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // queue.take：队列空时自动等待
                List<OrderInfo> batch = queue.take();

                // 检查结束标记
                if (batch == null || batch.isEmpty()) {
                    // 将结束标记重新放入队列供其他消费者使用
                    queue.put(batch);
                    break;
                }
                long startTime = System.currentTimeMillis();
                int batchSize = batch.size();
                // 处理批次数据
                processBatch(batch);
                processedCount.addAndGet(batchSize);

                long elapsed = System.currentTimeMillis() - startTime;
                log.debug("批次处理完成，大小：{}，耗时：{} ms", batchSize, elapsed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.error("处理数据批次时发生错误，该批次数据可能丢失:", e);
                break;
            }
        }
    }

    private void processBatch(List<OrderInfo> batch) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(false)) {
            try {
                // 批量插入数据
                for (OrderInfo record : batch) {
                    log.debug("处理记录：{}", record);
                    sqlSession.insert("com.vindasoft.order.mapper.OrderManageMapper.insertOrderInfo", record);
                }

                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                throw e;
            }
        }
    }
}