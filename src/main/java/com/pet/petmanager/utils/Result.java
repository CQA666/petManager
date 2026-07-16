package com.pet.petmanager.utils;

import lombok.Data;

/**
 * 统一 API 响应包装类。
 * 所有 Controller 和 Service 层方法均返回此类型，
 * Jackson 序列化后生成 JSON 结构：{"code": "...", "msg": "...", "data": ...}
 * 使用示例：
 * // 成功（无数据）
 * return Result.success();
 * // 成功（带数据）
 * return Result.success(someObject);
 * // 失败
 * return Result.error("-1", "操作失败原因");
 */

@Data
public class Result {

    /** 状态码："0" 表示成功，其他值表示各种错误类型 */
    private String code;

    /** 提示消息，成功时为"成功"，失败时为具体错误描述 */
    private String msg;

    /** 响应数据载荷，可为任意类型对象 */
    private Object data;

    // ==================== 构造器 ====================

    public Result() {
    }

    public Result(Object data) {
        this.data = data;
    }

    /**
     * 创建成功响应（无数据载荷）。
     * @return code="0", msg="成功", data=null
     */
    public static Result success() {
        Result result = new Result();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    /**
     * 创建成功响应（携带数据载荷）。
     * @param data 要返回的数据对象，可为任意类型
     * @return code="0", msg="成功", data=传入的对象
     */
    public static Result success(Object data) {
        Result result = new Result(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    /**
     * 创建错误响应。
     * @param code 自定义错误码
     * @param msg  错误描述消息
     * @return code=自定义码, msg=错误描述, data=null
     */
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}

