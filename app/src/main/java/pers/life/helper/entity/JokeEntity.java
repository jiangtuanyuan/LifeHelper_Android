package pers.life.helper.entity;

/**
 * author : jiang
 * date   : 2019/5/6  17:58
 * desc   :
 */
public class JokeEntity {
    private String content;
    private String hashId;
    private long unixtime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public long getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(long unixtime) {
        this.unixtime = unixtime;
    }
}
