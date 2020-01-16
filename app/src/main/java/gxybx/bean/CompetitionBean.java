package gxybx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/4/25.
 */

public class CompetitionBean implements  Parcelable {
    //赛事的Bean类
    private String competitionLocation="";
    private int competitionId=0;
    private String competitionTitle="";
    private String title="";
    private String descr="";
    private String blueZhName="";
    private String blueEnName="";
    private String blueRecord="";
    private String bluePlayerImageUrl="";
    private String blueCountryImageUrl="";
    private String redZhName="";
    private String redEnName="";
    private String redRecord="";
    private String redPlayerImageUrl="";
    private String redCountryImageUrl="";
    private int competitionStatus=0;
    private String competitionStatusName="";
    private String statusName="";
    private String timeStr="";
    private long time=0;
    private int status=0;
    private long endGuessTime=0;
    private String competitionTimeStr="";
    private int isGuessing=0;
    private int redPlayerId=0;

    private String liveLink;

    public String getLiveLink() {
        return liveLink;
    }

    public void setLiveLink(String liveLink) {
        this.liveLink = liveLink;
    }

    public int getBluePlayerId() {
        return bluePlayerId;
    }

    public void setBluePlayerId(int bluePlayerId) {
        this.bluePlayerId = bluePlayerId;
    }

    public int getRedPlayerId() {
        return redPlayerId;
    }

    public void setRedPlayerId(int redPlayerId) {
        this.redPlayerId = redPlayerId;
    }

    private int bluePlayerId=0;

    public int getCompetitionId() {
        return competitionId;
    }

    public String getCompetitionTitle() {
        return competitionTitle;
    }

    public void setCompetitionTitle(String competitionTitle) {
        this.competitionTitle = competitionTitle;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public int getCompetitionStatus() {
        return competitionStatus;
    }

    public void setCompetitionStatus(int competitionStatus) {
        this.competitionStatus = competitionStatus;
    }

    public String getCompetitionStatusName() {
        return competitionStatusName;
    }

    public void setCompetitionStatusName(String competitionStatusName) {
        this.competitionStatusName = competitionStatusName;
    }

    public int getIsGuessing() {
        return isGuessing;
    }

    public void setIsGuessing(int isGuessing) {
        this.isGuessing = isGuessing;
    }

    public String getCompetitionTimeStr() {
        return competitionTimeStr;
    }

    public void setCompetitionTimeStr(String competitionTimeStr) {
        this.competitionTimeStr = competitionTimeStr;
    }

    public long getEndGuessTime() {
        return endGuessTime;
    }

    public void setEndGuessTime(long endGuessTime) {
        this.endGuessTime = endGuessTime;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getBlueZhName() {
        return blueZhName;
    }

    public void setBlueZhName(String blueZhName) {
        this.blueZhName = blueZhName;
    }

    public String getBlueEnName() {
        return blueEnName;
    }

    public void setBlueEnName(String blueEnName) {
        this.blueEnName = blueEnName;
    }

    public String getBlueRecord() {
        return blueRecord;
    }

    public void setBlueRecord(String blueRecord) {
        this.blueRecord = blueRecord;
    }

    public String getBluePlayerImageUrl() {
        return bluePlayerImageUrl;
    }

    public void setBluePlayerImageUrl(String bluePlayerImageUrl) {
        this.bluePlayerImageUrl = bluePlayerImageUrl;
    }

    public String getBlueCountryImageUrl() {
        return blueCountryImageUrl;
    }

    public void setBlueCountryImageUrl(String blueCountryImageUrl) {
        this.blueCountryImageUrl = blueCountryImageUrl;
    }

    public String getRedZhName() {
        return redZhName;
    }

    public void setRedZhName(String redZhName) {
        this.redZhName = redZhName;
    }

    public String getRedEnName() {
        return redEnName;
    }

    public void setRedEnName(String redEnName) {
        this.redEnName = redEnName;
    }

    public String getRedRecord() {
        return redRecord;
    }

    public void setRedRecord(String redRecord) {
        this.redRecord = redRecord;
    }

    public String getRedPlayerImageUrl() {
        return redPlayerImageUrl;
    }

    public void setRedPlayerImageUrl(String redPlayerImageUrl) {
        this.redPlayerImageUrl = redPlayerImageUrl;
    }

    public String getRedCountryImageUrl() {
        return redCountryImageUrl;
    }

    public void setRedCountryImageUrl(String redCountryImageUrl) {
        this.redCountryImageUrl = redCountryImageUrl;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCompetitionLocation() {
        return competitionLocation;
    }

    public void setCompetitionLocation(String competitionLocation) {
        this.competitionLocation = competitionLocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.competitionLocation);
        dest.writeInt(this.competitionId);
        dest.writeString(this.competitionTitle);
        dest.writeString(this.title);
        dest.writeString(this.descr);
        dest.writeString(this.blueZhName);
        dest.writeString(this.blueEnName);
        dest.writeString(this.blueRecord);
        dest.writeString(this.bluePlayerImageUrl);
        dest.writeString(this.blueCountryImageUrl);
        dest.writeString(this.redZhName);
        dest.writeString(this.redEnName);
        dest.writeString(this.redRecord);
        dest.writeString(this.redPlayerImageUrl);
        dest.writeString(this.redCountryImageUrl);
        dest.writeInt(this.competitionStatus);
        dest.writeString(this.competitionStatusName);
        dest.writeString(this.statusName);
        dest.writeString(this.timeStr);
        dest.writeLong(this.time);
        dest.writeInt(this.status);
        dest.writeLong(this.endGuessTime);
        dest.writeString(this.competitionTimeStr);
        dest.writeInt(this.isGuessing);
        dest.writeInt(this.redPlayerId);
        dest.writeInt(this.bluePlayerId);
    }

    public CompetitionBean() {
    }

    protected CompetitionBean(Parcel in) {
        this.competitionLocation = in.readString();
        this.competitionId = in.readInt();
        this.competitionTitle = in.readString();
        this.title = in.readString();
        this.descr = in.readString();
        this.blueZhName = in.readString();
        this.blueEnName = in.readString();
        this.blueRecord = in.readString();
        this.bluePlayerImageUrl = in.readString();
        this.blueCountryImageUrl = in.readString();
        this.redZhName = in.readString();
        this.redEnName = in.readString();
        this.redRecord = in.readString();
        this.redPlayerImageUrl = in.readString();
        this.redCountryImageUrl = in.readString();
        this.competitionStatus = in.readInt();
        this.competitionStatusName = in.readString();
        this.statusName = in.readString();
        this.timeStr = in.readString();
        this.time = in.readLong();
        this.status = in.readInt();
        this.endGuessTime = in.readLong();
        this.competitionTimeStr = in.readString();
        this.isGuessing = in.readInt();
        this.redPlayerId = in.readInt();
        this.bluePlayerId = in.readInt();
    }

    public static final Creator<CompetitionBean> CREATOR = new Creator<CompetitionBean>() {
        @Override
        public CompetitionBean createFromParcel(Parcel source) {
            return new CompetitionBean(source);
        }

        @Override
        public CompetitionBean[] newArray(int size) {
            return new CompetitionBean[size];
        }
    };
}
