package com.example.musika.Me.Setting.Style;

public class itemStyle {
    Integer imgStyle;
    String style;
    boolean isSelect = false;

    public itemStyle(Integer imgStyle, String style) {
        this.imgStyle = imgStyle;
        this.style = style;
    }
    public itemStyle() {

    } //============================================================================================

    public Integer getImgStyle() {
        return imgStyle;
    }

    public void setImgStyle(Integer imgStyle) {
        this.imgStyle = imgStyle;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
