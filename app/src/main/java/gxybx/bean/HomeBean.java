package gxybx.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class HomeBean {

    /**
     * imageList : [{"fileUrl":"https://tf.gxyclub.com/cms/201908/34371566446621572.jpg"}]
     * productSet : {"content":"<p><img src=\"https://tf.gxyclub.com/cms/201908/68691566446672405.jpg\" title=\"Tulips.jpg\" alt=\"Tulips.jpg\"/><\/p>"}
     */

    private ProductSetBean productSet;
    private List<ImageListBean> imageList;

    public ProductSetBean getProductSet() {
        return productSet;
    }

    public void setProductSet(ProductSetBean productSet) {
        this.productSet = productSet;
    }

    public List<ImageListBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageListBean> imageList) {
        this.imageList = imageList;
    }

    public static class ProductSetBean {
        /**
         * content : <p><img src="https://tf.gxyclub.com/cms/201908/68691566446672405.jpg" title="Tulips.jpg" alt="Tulips.jpg"/></p>
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class ImageListBean {
        /**
         * fileUrl : https://tf.gxyclub.com/cms/201908/34371566446621572.jpg
         */

        private String fileUrl;
        private String linkUrl;

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}




