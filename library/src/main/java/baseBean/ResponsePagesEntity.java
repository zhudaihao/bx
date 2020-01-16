package baseBean;

import java.io.Serializable;

/**
 * Created by AsionReachel on 2016/5/30.
 */
public class ResponsePagesEntity implements Serializable {
    private int pageNum=0;
    private int pageSize=0;
    private int totalPageNum=1;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }
}
