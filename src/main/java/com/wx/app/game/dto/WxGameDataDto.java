package com.wx.app.game.dto;




public class WxGameDataDto {
    private int id;
    private long updatedTime;

    private Integer aSkipCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getaSkipCount() {
        return aSkipCount;
    }

    public void setaSkipCount(Integer aSkipCount) {
        this.aSkipCount = aSkipCount;
    }

    @Override
    public String toString() {
        return "WxGameDataDto{" +
                "id=" + id +
                ", updatedTime=" + updatedTime +
                ", aSkipCount=" + aSkipCount +
                '}';
    }
}
