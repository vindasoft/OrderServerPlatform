/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.order.utils;

import com.vindasoft.order.domain.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: Excel文件导入工具
 * @author: jwd
 * @date: 2026-01-02
 */
@Slf4j
@Component
public class ExcelImportThreadPool implements CommandLineRunner {
    private static final int PRODUCER_COUNT = 1;
    private static final int CONSUMER_COUNT = 4;
    private static final int BATCH_SIZE = 100;
    private static final int QUEUE_SIZE = 1000;
    private static final long SHUTDOWN_TIMEOUT_SECONDS = 60;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void run(String... args) {
        String excelFilePath =
            args.length > 0 ? args[0] : "F:\\workspace\\vindasoft\\OrderServerPlatform\\largedatafile.xlsx";

        ExecutorService executor = null;
        try {
            // 创建阻塞队列
            BlockingQueue<List<OrderInfo>> queue = new LinkedBlockingQueue<>(QUEUE_SIZE);

            // 创建线程池
            executor = Executors.newFixedThreadPool(PRODUCER_COUNT + CONSUMER_COUNT);

            // 创建计数器
            AtomicInteger processedCount = new AtomicInteger(0);

            // 启动消费者线程
            for (int i = 0; i < CONSUMER_COUNT; i++) {
                executor.submit(new DataProcessor(queue, sqlSessionFactory, processedCount));
            }

            // 启动生产者线程
            for (int i = 0; i < PRODUCER_COUNT; i++) {
                executor.submit(new ExcelReader(excelFilePath, queue, BATCH_SIZE));
            }

            // shutdown（）关闭线程池：拒绝新任务，等待已有任务完成
            executor.shutdown();

            // 监控处理进度
            monitorProgress(processedCount, executor);

        } catch (Exception e) {
            log.error("程序执行失败: ", e);
        } finally {
            if (executor != null && !executor.isShutdown()) {
                executor.shutdown();
                try {
                    // awaitTermination()：等待指定时间
                    if (!executor.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                        log.warn("线程池在 {} 秒内未能正常关闭，强制关闭", SHUTDOWN_TIMEOUT_SECONDS);
                        // shutdownNow()：超时后强制终止
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    log.error("等待线程池关闭时被中断！", e);
                    executor.shutdownNow();
                }
            }
        }
    }

    private static void monitorProgress(AtomicInteger processedCount, ExecutorService executor) {
        long startTime = System.currentTimeMillis();

        while (!executor.isTerminated()) {
            try {
                Thread.sleep(1000);
                log.info("已处理记录数：{}", processedCount.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        log.info("数据导入完成!");
        log.info("总计处理记录数：{} ", processedCount.get());
        log.info("耗时: {} 毫秒", (endTime - startTime));
    }
}