package cn.wydewy.yuejuba.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by weiyideweiyi8 on 2015/10/29.
 */
public class Dynamic  extends DataSupport {
    private int id;
    private String name;
    private String imageUrl;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
