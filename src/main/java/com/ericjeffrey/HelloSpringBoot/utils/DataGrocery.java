package com.ericjeffrey.HelloSpringBoot.utils;

import com.ericjeffrey.HelloSpringBoot.HelloSpringBootApplication;
import com.ericjeffrey.HelloSpringBoot.model.PastedText;

import java.io.*;
import java.util.ArrayDeque;

import static com.ericjeffrey.HelloSpringBoot.HelloSpringBootApplication.WORD_DIR_PATH;

public class DataGrocery {
    public static final String PAGE_VIEWS_PATH = WORD_DIR_PATH + "pastorPV.txt";

    private static final Object sync = new Object();
    private static long VISIT_COUNT = 0;

    private static final PastedText defaultText = new PastedText("Nothing Here\n:-)", 2035, 11,30);
    private static final int MAX_SZ = 20;
    private static ArrayDeque<PastedText> texts = new ArrayDeque<>();

    /**
     * 返回第 i 个元素的内容
     * @param i 序号
     * @return 第 i 个元素的内容，或默认值{@link #defaultText}若 i > size
     */
    public static String contentOfI(int i){
        if (i < texts.size())
            return ((PastedText)texts.toArray()[i]).getContent();
        return defaultText.getContent();
    }

    public static String dateOfI(int i){
        if (i < texts.size())
            return ((PastedText)texts.toArray()[i]).getDateString();
        return defaultText.getDateString();
    }

    /**
     * 返回队首元素的时间标签
     * @return 队首元素时间标签，或默认值{@link #defaultText}的时间标 若队列为空
     */
    public static String topDate(){
        if (texts.isEmpty())
            return defaultText.getDateString();
        return texts.peek().getDateString();
    }
    /**
     * 返回队列头部元素内容
     *
     * @return 队首元素，或默认值 {@link #defaultText}的内容 若队列为空
     */
    public static String topContent() {
        if (texts.isEmpty())
            return defaultText.getContent();
        return texts.peek().getContent();
    }

    /**
     * 将给定的内容加入队列中
     *
     * @param content 粘贴过来的内容
     */
    public static void add(String content) {
        if (texts.size() == MAX_SZ)
            texts.removeLast();
        texts.addFirst(new PastedText(content));
    }

    /**
     * 访问计数增加 1
     */
    public static void VISIT_INC() {
        synchronized (sync) {
            VISIT_COUNT++;
        }
        if (VISIT_COUNT % 100 == 0){
            try {
                FileWriter writer = new FileWriter(PAGE_VIEWS_PATH, false);
                writer.write(String.valueOf(VISIT_COUNT));
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得访问量
     * @return 访问量值
     */
    public static String getVisitCount() {
        return String.valueOf(VISIT_COUNT);
    }
}
