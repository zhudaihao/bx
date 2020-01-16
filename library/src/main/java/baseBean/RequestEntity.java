package baseBean;

import java.io.Serializable;

/**
 * Created by AsionReachel on 2016/6/17.
 */
public class RequestEntity implements Serializable {
    private RequestPublic common=new RequestPublic();
    private String requestWhat="";
    private String data="";
    private String security="";

    public RequestPublic getCommon() {
        return common;
    }

    public void setCommon(RequestPublic common) {
        this.common = common;
    }

    public String getRequestWhat() {
        return requestWhat;
    }

    public void setRequestWhat(String requestWhat) {
        this.requestWhat = requestWhat;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }
}
