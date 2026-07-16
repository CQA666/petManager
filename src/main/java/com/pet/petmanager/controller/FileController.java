package com.pet.petmanager.controller;


import com.pet.petmanager.utils.FileUtil;
import com.pet.petmanager.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/file")
@RestController
public class FileController {

    /**
     * 上传文件接口
     * @param file
     * @return
     */
    // 映射处理文件上传的请求路径为"/upload"
    @RequestMapping("/upload")
    public Result upLoad(@RequestParam("file") MultipartFile file) {
        // 检查上传文件的原始文件名是否为空，如果为空则返回错误信息"文件不存在！"
        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            // 返回错误信息，错误码为"-1"，提示信息为"文件不存在！"
            return Result.error("-1", "文件不存在！");
        }
        // 调用FileUtil类的saveImage方法保存上传的文件，并将返回的文件路径赋值给path变量
        // 这里传递了两个参数：上传的文件和null（此处null可能是作为某个参数的默认值或占位符）
        String path = FileUtil.saveImage(file, null);
        // 检查path是否为空，如果不为空则表示文件上传成功
        if (StringUtils.isNotBlank(path)) {
            // 返回成功信息，包含上传文件的路径path
            return Result.success(path);
        } else {
            // 如果path为空，表示文件上传失败，则返回错误信息"文件上传失败"
            return Result.error("-1", "文件上传失败");
        }
    }

}
