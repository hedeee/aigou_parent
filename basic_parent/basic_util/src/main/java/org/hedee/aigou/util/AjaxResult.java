package org.hedee.aigou.util;

public class AjaxResult {
    private Boolean success=true;
    private String message="操作成功";

    //返回到前台的对象
    private Object resultObj;

    public Boolean getSuccess() {
        return success;
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public AjaxResult setResultObj(Object resultObj) {
        this.resultObj = resultObj;
        return this;
    }

    public static AjaxResult getAjaxResult(){
        return  new AjaxResult();
    }
}
