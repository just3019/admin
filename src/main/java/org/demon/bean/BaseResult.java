package org.demon.bean;

import java.util.List;

/**
 * Description: http响应通用结构类型
 * Date: 2016/10/9 19:10
 *
 * @author Sean.xie
 */
public class BaseResult<T> extends BaseBean {
    public static final int CODE_SUCCESS = 1000;
    public static final int CODE_ERROR = 0;

    /**
     * 状态,1000 代表成功(数据可用)
     */
    protected Integer state;
    protected Integer level;
    protected String errormsg;
    protected T data;
    protected List<T> list;

    public BaseResult() {
    }

    /**
     * 响应结构
     *
     * @param state    状态
     * @param errormsg 错误消息
     */
    public BaseResult(Integer state, String errormsg) {
        this.state = state;
        this.errormsg = errormsg;
    }

    public BaseResult(Integer state, Integer level, String errormsg) {
        this.state = state;
        this.level = level;
        this.errormsg = errormsg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public boolean isSuccess() {
        return (state != null && state == CODE_SUCCESS);
    }
}
