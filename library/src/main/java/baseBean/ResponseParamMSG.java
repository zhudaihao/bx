package baseBean;

/**
 * Created by AsionReachel on 2016/5/27.
 */
public class ResponseParamMSG {
    private String isVisible = "false";
    private int type = 0;
    private String info = "";


    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
