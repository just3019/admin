package org.demon.bean;

import java.util.List;

/**
 * Description: 分页响应
 * Created by Sean.xie on 2017/4/26.
 */
public class PageData<T> extends BaseBean {
    public long count = 0;
    public List<T> list;
    public T data;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
