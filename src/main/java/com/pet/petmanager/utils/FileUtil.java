package com.pet.petmanager.utils;


import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    // 定义文件存储的基础路径，基于用户当前工作目录下的files文件夹
    public final static String FILE_BASE_PATH = System.getProperty("user.dir") + "/files/";

    // 获取项目根目录路径的方法
    public static Path getProjectRootPath() throws IOException {
        // 使用PathMatchingResourcePatternResolver来解析资源路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 获取所有匹配classpath*:.*的资源
        Resource[] resources = resolver.getResources("classpath*:.");
        // 如果没有找到任何资源，抛出IOException异常
        if (resources.length == 0) {
            throw new IOException("Cannot find project root path.");
        }
        // 假设第一个资源就是项目的根目录，将其转换为File对象
        File rootDir = resources[0].getFile();
        // 将File对象转换为Path对象并返回
        return rootDir.toPath();
    }

    // 定义一个静态方法，用于保存上传的图片文件到指定路径
    public static String saveImage(MultipartFile file, String filePath) {
        // 获取上传文件的原始文件名
        String originalFilename = file.getOriginalFilename();
        // 断言文件名不为空，如果为空则程序会在此处抛出错误
        assert originalFilename != null;
        // 获取当前时间戳，用于生成唯一的文件名
        long timestamp = System.currentTimeMillis();
        // 生成一个随机的UUID，用于生成唯一的文件名
        UUID uuid = UUID.randomUUID();
        // 获取文件扩展名，从原始文件名中截取
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 构建新的文件名，包含时间戳、UUID和原始文件扩展名
        String dFileName = timestamp + "-" + uuid + extension;

        // 获取项目根目录路径，FILE_BASE_PATH需要在其他地方定义为项目的根路径
        Path projectRootPath = Paths.get(FILE_BASE_PATH);

        // 检查filePath是否为空或为空字符串，如果为空则使用项目根目录下的“img/”目录作为目标目录
        // 否则，使用项目根目录下的“img/”目录加上用户提供的filePath作为目标目录
        Path targetDirectory = (filePath == null || filePath.isEmpty()) ? projectRootPath.resolve("img/") : projectRootPath.resolve("img/"+filePath);

        try {
            // 检查目标目录是否存在，如果不存在则创建该目录及其所有必要的父目录
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }
            // 构建完整的上传文件路径，包含目标目录和新的文件名
            Path uploadFilePath = targetDirectory.resolve(dFileName);
            // 将Path对象转换为File对象，以便后续的文件操作
            File uploadFile = uploadFilePath.toFile();

            // 将上传的文件内容写入到指定的文件路径
            file.transferTo(uploadFile);
            // 打印文件保存的绝对路径到控制台
            System.out.println("File saved at: " + uploadFile.getAbsolutePath());
        } catch (IOException e) {
            // 打印异常堆栈信息到控制台，用于调试
            e.printStackTrace();
            // 如果发生IO异常，则返回null，表示文件保存失败
            return null;
        }

        // 返回文件保存的相对路径，以便在Web应用中可以通过该路径访问文件
        // 如果filePath为空或为空字符串，则返回“/img/”加上新的文件名作为相对路径
        // 否则，返回用户提供的filePath加上新的文件名作为相对路径
        return (filePath == null || filePath.isEmpty()) ? "/img/" + dFileName : filePath + "/" + dFileName;
    }



    // 删除指定路径下的文件的方法
    public static boolean deleteFile(String relativeFilePath) {
        // 获取项目根目录路径
        Path projectRootPath = Paths.get(FILE_BASE_PATH);

        // 构造要删除文件的绝对路径
        Path absoluteFilePath = projectRootPath.resolve("img/" + relativeFilePath);

        try {
            // 删除指定路径下的文件
            Files.delete(absoluteFilePath);
            // 打印已删除文件的绝对路径
            System.out.println("File deleted: " + absoluteFilePath);
            // 如果文件成功删除，返回true
            return true;
        } catch (IOException e) {
            // 打印异常堆栈信息
            e.printStackTrace();
            // 如果发生IO异常，返回false
            return false;
        }
    }

    // 生成文件名的方法，基于上传的MultipartFile对象
    private static String generateFileName(MultipartFile file) {
        // 获取上传文件的原始文件名
        String originalFilename = file.getOriginalFilename();
        // 断言原始文件名不为空
        assert originalFilename != null;
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();
        // 生成一个随机的UUID
        UUID uuid = UUID.randomUUID();
        // 获取文件的扩展名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 构造新的文件名，格式为“时间戳-UUID.扩展名”
        String dFileName = timestamp + "-" + uuid + extension;
        // 返回构造的新文件名
        return dFileName;
    }

    // 从URL中移除指定部分的方法
    public static String removePartFromUrlUsingJSON(String url, String part) {
        // 构造一个JSONObject对象，包含原始的URL
        JSONObject jsonObject = JSON.parseObject("{\"url\":\"" + url + "\"}");
        // 从JSONObject中获取URL字符串
        String modifiedUrl = jsonObject.getString("url");
        // 如果URL包含要移除的部分，则执行移除操作
        if (modifiedUrl.contains(part)) {
            // 移除URL中的指定部分
            modifiedUrl = modifiedUrl.substring(0, modifiedUrl.indexOf(part)) + modifiedUrl.substring(modifiedUrl.indexOf(part) + part.length());
            // 如果移除后URL以斜杠结尾，则移除最后一个斜杠
            modifiedUrl = modifiedUrl.endsWith("/") ? modifiedUrl.substring(0, modifiedUrl.length() - 1) : modifiedUrl;
            // 将修改后的URL放回JSONObject中
            jsonObject.put("url", modifiedUrl);
        }
        // 返回修改后的URL字符串
        return jsonObject.getString("url");
    }
}
