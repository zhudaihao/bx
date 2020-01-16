package baseBean;

import java.io.Serializable;

/**
 * Created by AsionReachel on 2016/5/31.
 */
public class RequestPublic implements Serializable {

    private String language="";

   private RequestClient client=new RequestClient();

    public RequestClient getClient() {
        return client;
    }

    public void setClient(RequestClient client) {
        this.client = client;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


}
