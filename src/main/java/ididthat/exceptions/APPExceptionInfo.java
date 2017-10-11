package ididthat.exceptions;

public class APPExceptionInfo {
    private int status;
    private String msg, desc;
    public APPExceptionInfo(int status, String msg, String desc) {
        this.status=status;
        this.msg=msg;
        this.desc=desc;
    }
    public int getStatus() { return status; }
    public String getMessage() { return msg; }
    public String getDescription() { return desc; }
}
