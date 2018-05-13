package org.demon.bean;


/**
 * Description: 分页请求
 * Date: 2016/11/2 10:38
 *
 * @author Sean.xie
 */
public class PageReq<T> extends BaseReq<T> {
    /**
     * 查询参数
     *
     * @see #query
     */
    @Deprecated
    public T data;
    // 分页
    private Integer page;
    private Integer size;
    // 是否查询全部数据
    private Boolean isFull;

    public void setFull(Boolean full) {
        isFull = full;
    }

    public int getLimit() {
        if (isFull()) {
            return 0;
        }
        return getSize() > 100 ? 100 : getSize();
    }

    public int getOffset() {
        if (isFull()) {
            return 0;
        }
        return ((getPage() - 1) < 0 ? 0 : getPage() - 1) * getSize();
    }

    public Boolean isFull() {
        return isFull == null ? false : isFull;
    }

    public Integer getPage() {
        return page == null ? 1 : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size == null ? 30 : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
