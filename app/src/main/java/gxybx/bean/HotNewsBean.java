package gxybx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/4/25.
 */

public class HotNewsBean implements Parcelable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id=0;
    private String title="";
    private String content="";
    private String descr="";
    private String imageUrl="";
    private String createTimeStr="";

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    private String source="";
    private String editor="";

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.descr);
        dest.writeString(this.imageUrl);
        dest.writeString(this.createTimeStr);
        dest.writeString(this.source);
        dest.writeString(this.editor);
    }

    public HotNewsBean() {
    }

    protected HotNewsBean(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.descr = in.readString();
        this.imageUrl = in.readString();
        this.createTimeStr = in.readString();
        this.source = in.readString();
        this.editor = in.readString();
    }

    public static final Creator<HotNewsBean> CREATOR = new Creator<HotNewsBean>() {
        @Override
        public HotNewsBean createFromParcel(Parcel source) {
            return new HotNewsBean(source);
        }

        @Override
        public HotNewsBean[] newArray(int size) {
            return new HotNewsBean[size];
        }
    };
}
