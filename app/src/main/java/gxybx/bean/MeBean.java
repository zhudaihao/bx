package gxybx.bean;

public class MeBean  {

    /**
     * info : {"companyName":"北京金浩保险经纪有限公司","detailAddress":"北京市朝阳区亮马桥路39号第一上海中心A座602","serviceTelphone":"13213123","serviceEmail":"1665322@qq.com"}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean  {
        /**
         * companyName : 北京金浩保险经纪有限公司
         * detailAddress : 北京市朝阳区亮马桥路39号第一上海中心A座602
         * serviceTelphone : 13213123
         * serviceEmail : 1665322@qq.com
         */

        private String companyName;
        private String detailAddress;
        private String serviceTelphone;
        private String serviceEmail;
        private String serviceUrl;

        public String getServiceUrl() {
            return serviceUrl;
        }

        public void setServiceUrl(String serviceUrl) {
            this.serviceUrl = serviceUrl;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getServiceTelphone() {
            return serviceTelphone;
        }

        public void setServiceTelphone(String serviceTelphone) {
            this.serviceTelphone = serviceTelphone;
        }

        public String getServiceEmail() {
            return serviceEmail;
        }

        public void setServiceEmail(String serviceEmail) {
            this.serviceEmail = serviceEmail;
        }
    }

}
