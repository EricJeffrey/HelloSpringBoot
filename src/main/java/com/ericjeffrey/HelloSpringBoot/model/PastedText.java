package com.ericjeffrey.HelloSpringBoot.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class PastedText {
    private String content;
    private Date date;

    public PastedText(String content, int y, int m, int d){
        this.content = content;
        this.date = new GregorianCalendar(y, m, d).getTime();
    }

    public PastedText(String content){
        this.content = content;
        this.date = new GregorianCalendar().getTime();
    }

    public String getContent() {
        return content;
    }

    public String getDateString(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", getDateString(), content);
    }
}
