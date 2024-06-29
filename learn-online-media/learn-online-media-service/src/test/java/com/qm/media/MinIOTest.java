package com.qm.media;

import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    //将分块文件上传到minio
    @Test
    public void uploadChunk() throws Exception {
        for (int i = 0; i < 2; i++) {
            //上传文件的参数信息
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("testbucket")//桶
                    .filename("D:\\Test-learn-online-media\\videos\\chunk\\" + i) //指定本地文件路径
                    .object("chunk/" + i)//对象名 放在子目录下
                    .build();
            //上传文件
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传分块" + i + "成功");
        }
    }

    //调用minio接口合并分块
    @Test
    public void testMerge() throws Exception {
        List<ComposeSource> sources = Stream
                .iterate(0, i -> ++i)
                .limit(2)
                .map(i -> ComposeSource.builder().bucket("testbucket").object("chunk/" + i).build())
                .collect(Collectors.toList());
        //指定合并后的objectName等信息
        ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
                .bucket("testbucket")
                .object("Java入门.mp4")
                .sources(sources)//指定源文件
                .build();
        //合并文件,
        //报错size 1048576 must be greater than 5242880，minio默认的分块文件大小为5M
        minioClient.composeObject(composeObjectArgs);
    }

    //批量清理分块文件
}
