package baseBean;

/**
 * Created by AsionReachel on 2016/5/26.
 */
public class BaseResponseEntity {
    private ResponsePublicParam common=new ResponsePublicParam();
    private Object data=new Object();
    private ResponseParamMSG msg=new ResponseParamMSG();
    private ResponsePagesEntity pages=new ResponsePagesEntity();

    public ResponsePagesEntity getPages() {
        return pages;
    }

    public void setPages(ResponsePagesEntity pages) {
        this.pages = pages;
    }

    public ResponsePublicParam getCommon() {
        return common;
    }

    public void setCommon(ResponsePublicParam common) {
        this.common = common;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseParamMSG getMsg() {
        return msg;
    }

    public void setMsg(ResponseParamMSG msg) {
        this.msg = msg;
    }
}
