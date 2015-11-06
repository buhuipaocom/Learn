package cn.wydewy.filedinhand.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by weiyideweiyi8 on 2015/11/6.
 */
public class Picture extends DataSupport {

    private int id;
    private String pointName;//对应点名
    private String dir;//路径

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }


    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
