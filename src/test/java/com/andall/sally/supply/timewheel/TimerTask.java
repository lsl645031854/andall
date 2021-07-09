package com.andall.sally.supply.timewheel;

/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 5:37 下午 2021/7/8
 */
/**
 * 任务
 */
public class TimerTask {

    /**
     * 延迟时间
     */
    private long delay;

    /**
     * 任务
     */
    private Runnable task;

    /**
     * 时间槽
     */
    protected TimerTaskList timerTaskList;

    /**
     * 下一个节点
     */
    protected TimerTask next;

    /**
     * 上一个节点
     */
    protected TimerTask pre;

    /**
     * 描述
     */
    public String desc;

    public TimerTask(long delay, Runnable task, String desc) {
        this.delay = System.currentTimeMillis() + delay;
        this.task = task;
        this.timerTaskList = null;
        this.next = null;
        this.pre = null;
        this.desc = desc;
    }

    public Runnable getTask() {
        return task;
    }

    public long getDelayMs() {
        return delay;
    }

    @Override
    public String toString() {
        return "TimerTask{" + "delay=" + delay + ", task=" + task + ", timerTaskList=" + timerTaskList + ", next=" + next + ", pre=" + pre + ", desc='" + desc + '\'' + '}';
    }
}

