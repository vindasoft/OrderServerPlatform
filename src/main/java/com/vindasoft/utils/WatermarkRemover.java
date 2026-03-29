/**
 * @copyright vindasoft in 2026-01-01 version V1.0
 */

package com.vindasoft.utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 * @Description: TODO
 * @author: lixiaoqiang
 * @date: 2026-01-02
 */
public class WatermarkRemover {
    public static void main(String[] args) {
        String inputDir = "F:\\workspace\\vindasoft\\OrderServerPlatform\\file";
        String outputDir = "F:\\workspace\\vindasoft\\OrderServerPlatform\\file\\processed";
        int x = 2356;
        int y = 1408;
        int width = 304;
        int height = 62;

        try {
            batchRemoveWatermark(inputDir, outputDir, x, y, width, height);
        } catch (IOException e) {
            System.err.println("批量处理发生错误：" + e.getMessage());
            e.printStackTrace();
        }
    }

    // ... existing code ...

    /**
     * 批量去除指定目录下所有图片的水印
     */
    public static void batchRemoveWatermark(String inputDir, String outputDir,
        int x, int y, int width, int height) throws IOException {
        if (!validateWatermarkParams(x, y, width, height)) {
            throw new IllegalArgumentException("水印参数无效");
        }

        File inputDirectory = new File(inputDir);
        if (!inputDirectory.exists() || !inputDirectory.isDirectory()) {
            throw new IOException("输入目录不存在或不是目录：" + inputDir);
        }

        File outputDirectory = new File(outputDir);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        String[] imageExtensions = {"png", "jpg", "jpeg", "PNG", "JPG", "JPEG"};
        File[] imageFiles = inputDirectory.listFiles((dir, name) -> {
            for (String ext : imageExtensions) {
                if (name.endsWith("." + ext)) {
                    return true;
                }
            }
            return false;
        });

        if (imageFiles == null || imageFiles.length == 0) {
            System.out.println("目录下未找到图片文件");
            return;
        }

        int successCount = 0;
        int failCount = 0;

        for (File inputFile : imageFiles) {
            try {
                String outputFileName = inputFile.getName().replaceFirst("(?i)\\.(png|jpg|jpeg)$", ".$1");
                String outputImagePath = outputDir + File.separator + outputFileName;

                removeWatermark(inputFile.getAbsolutePath(), outputImagePath, x, y, width, height);
                successCount++;
                System.out.println("[" + successCount + "] 处理完成：" + inputFile.getName());

            } catch (Exception e) {
                failCount++;
                System.err.println("处理失败：" + inputFile.getName() + " - " + e.getMessage());
            }
        }

        System.out.println("批量处理完成！成功：" + successCount + ", 失败：" + failCount);
    }

    /**
     * 验证水印参数是否有效
     */
    private static boolean validateWatermarkParams(int x, int y, int width, int height) {
        if (x < 0 || y < 0) {
            System.err.println("坐标值不能为负数");
            return false;
        }
        if (width <= 0 || height <= 0) {
            System.err.println("宽度和高度必须大于 0");
            return false;
        }
        return true;
    }

    /**
     * 去除指定区域的水印，使用周围像素颜色进行替换
     * @param inputImagePath 输入图片路径
     * @param outputImagePath 输出图片路径
     * @param x 水印区域左上角x坐标
     * @param y 水印区域左上角y坐标
     * @param width 水印区域宽度
     * @param height 水印区域高度
     * @throws IOException
     */
    public static void removeWatermark(String inputImagePath, String outputImagePath, int x, int y, int width, int height) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputImagePath));

        // 遍历水印区域的每一个像素
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                // 用左上方的像素颜色替换水印区域的像素
                if (i - 1 >= 0 && j - 1 >= 0) {
                    int rgb = image.getRGB(i - 1, j - 1);
                    image.setRGB(i, j, rgb);
                }
            }
        }

        ImageIO.write(image, outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1), new File(outputImagePath));
    }
}