package com.qm.media;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
public class MinIOTest {
    // 创建MinioClient对象
    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://116.198.224.127:9000")
                    .credentials("Lotus", "minio123456")
                    .build();

    // 测试上传文件
    @Test
    public void uploadTest() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("testbucket")
                            .filename("D:\\Test-learn-online-media\\images\\1.png")
                            .object("test/1.png")
                            .build()
            );
            System.out.println("上传成功");
        } catch (Exception e) {
            System.out.println("上传失败");
        }
    }

    // 测试删除文件
    @Test
    public void deleteTest() {
        try {
            minioClient.removeObject(RemoveObjectArgs
                    .builder()
                    .bucket("testbucket")
                    .object("1.png")
                    .build());
            System.out.println("删除成功");
        } catch (Exception e) {
            System.out.println("删除失败");
        }
    }

    // 测试查询文件
    @Test
    public void getFileTest() {
        try {
            FilterInputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket("testbucket")
                    .object("test/1.png")
                    .build());
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\Test-learn-online-media\\images\\temp.png");
            IOUtils.copy(inputStream, fileOutputStream);
            System.out.println("下载成功");
        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }

    // 分块
    @Test
    public void testChunk() throws IOException {
        // 源文件
        File sourceFile = new File("D:\\Test-learn-online-media\\videos\\Java入门.mp4");
        // 块文件路径
        String chunkPath = "D:\\Test-learn-online-media\\videos\\chunk\\";
        File chunkFolder = new File(chunkPath);
        if (!chunkFolder.exists()) {
            chunkFolder.mkdirs();
        }
        // 分块大小 1M
        long chunkSize = 1024 * 1024 * 1;
        // 计算块数，向上取整
        long chunkNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);
        // 缓冲区大小
        byte[] buffer = new byte[1024];
        // 使用RandomAccessFile访问文件
        RandomAccessFile raf_read = new RandomAccessFile(sourceFile, "r");
        // 遍历分块，依次向每一个分块写入数据
        for (int i = 0; i < chunkNum; i++) {
            // 创建分块文件，默认文件名 path + i，例如chunk\1  chunk\2
            File file = new File(chunkPath + i);
            if (file.exists()) {
                file.delete();
            }
            boolean newFile = file.createNewFile();
            if (newFile) {
                int len;
                RandomAccessFile raf_write = new RandomAccessFile(file, "rw");
                // 向分块文件写入数据
                while ((len = raf_read.read(buffer)) != -1) {
                    raf_write.write(buffer, 0, len);
                    // 写满就停
                    if (file.length() >= chunkSize)
                        break;
                }
                raf_write.close();
            }
        }
        raf_read.close();
        System.out.println("写入分块完毕");
    }

    // 合并
    @Test
    public void testMerge() throws IOException {
        // 块文件目录
        File chunkFolder = new File("D:\\Test-learn-online-media\\videos\\chunk\\");
        // 源文件
        File sourceFile = new File("D:\\Test-learn-online-media\\videos\\Java入门.mp4");
        // 合并文件
        File mergeFile = new File("D:\\Test-learn-online-media\\videos\\Java入门_2.mp4");
        mergeFile.createNewFile();
        // 用于写文件
        RandomAccessFile raf_write = new RandomAccessFile(mergeFile, "rw");
        // 缓冲区
        byte[] buffer = new byte[1024];
        // 文件名升序排序
        File[] files = chunkFolder.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, Comparator.comparingInt(o -> Integer.parseInt(o.getName())));
        // 合并文件
        for (File chunkFile : fileList) {
            RandomAccessFile raf_read = new RandomAccessFile(chunkFile, "r");
            int len;
            while ((len = raf_read.read(buffer)) != -1) {
                raf_write.write(buffer, 0, len);
            }
            raf_read.close();
        }
        raf_write.close();
        // 判断合并后的文件是否与源文件相同
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        FileInputStream mergeFileStream = new FileInputStream(mergeFile);
        //取出原始文件的md5
        String originalMd5 = DigestUtils.md5Hex(fileInputStream);
        //取出合并文件的md5进行比较
        String mergeFileMd5 = DigestUtils.md5Hex(mergeFileStream);
        if (originalMd5.equals(mergeFileMd5)) {
            System.out.println("合并文件成功");
        } else {
            System.out.println("合并文件失败");
        }
    }
}
