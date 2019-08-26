package com.nanshen.component.actionlog.enums;


public enum LogType {

    /**
     * 操作类型
     */
    WARNING("警告", "因被其他玩家举报，警告玩家"),
    BASE("基础日志","请求{}.{}方法结束");

    /**
     * 类型
     */
    private String type;

    /**
     * 执行操作
     */
    private String operation;

    LogType(String type, String operation) {
        this.type = type;
        this.operation = operation;
    }

    public String getType() { return type; }

    public String getOperation() { return operation; }

}
