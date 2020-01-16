package gxybx.bean;

/**
 * Created by Administrator on 2018/2/1.
 */

public class UpDateBean {

        /**
         * mobileVersion : {"type":2,"url":"https://tf.gxyclub.com/cms/app/app-release_110_jiagu_sign.apk","netUrl":"https://fir.im/e35s","vname":"3.3.3","isUpdate":1}
         */

        private MobileVersionBean mobileVersion;

        public MobileVersionBean getMobileVersion() {
            return mobileVersion;
        }

        public void setMobileVersion(MobileVersionBean mobileVersion) {
            this.mobileVersion = mobileVersion;
        }

        public static class MobileVersionBean {
            /**
             * type : 2
             * url : https://tf.gxyclub.com/cms/app/app-release_110_jiagu_sign.apk
             * netUrl : https://fir.im/e35s
             * vname : 3.3.3
             * isUpdate : 1
             */

            private int type;
            private String url;
            private String netUrl;
            private String vname;
            private int isUpdate;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getNetUrl() {
                return netUrl;
            }

            public void setNetUrl(String netUrl) {
                this.netUrl = netUrl;
            }

            public String getVname() {
                return vname;
            }

            public void setVname(String vname) {
                this.vname = vname;
            }

            public int getIsUpdate() {
                return isUpdate;
            }

            public void setIsUpdate(int isUpdate) {
                this.isUpdate = isUpdate;
            }
        }
    }


