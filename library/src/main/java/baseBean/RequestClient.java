package baseBean;

import java.io.Serializable;

/**
 * Created by AsionReachel on 2016/5/31.
 */
public class RequestClient implements Serializable {
    private int osType=0;
    private String version="";
    private String imei="";

    public int getOsType() {
        return osType;
    }

    public void setOsType(int osType) {
        this.osType = osType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
