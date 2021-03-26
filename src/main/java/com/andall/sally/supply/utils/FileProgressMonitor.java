package com.andall.sally.supply.utils;

import com.jcraft.jsch.SftpProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FileProgressMonitor extends TimerTask implements SftpProgressMonitor {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileProgressMonitor.class);
    
    private long progressInterval = 5 * 1000; // 默认间隔时间为5秒
    
    private boolean isEnd = false; // 记录传输是否结束
    
    private long transfered; // 记录已传输的数据总大小
    
    private long fileSize; // 记录文件总大小
    
    private Timer timer; // 定时器对象
    
    private boolean isScheduled = false; // 记录是否已启动timer记时器
    
    private Date startTime;
    
    private Date endTime;
    
    public FileProgressMonitor(long fileSize) {
        this.fileSize = fileSize;
    }
    
    @Override
    public void run() {
        if (!isEnd()) { // 判断传输是否已结束
        	LOG.info("Transfering is in progress.");
            long transfered = getTransfered();
            if (transfered != fileSize) { // 判断当前已传输数据大小是否等于文件总大小
            	LOG.info("Current transfered: " + transfered + " bytes");
                sendProgressMessage(transfered);
            } else {
            	LOG.info("File transfering is done.");
                setEnd(true); // 如果当前已传输数据大小等于文件总大小，说明已完成，设置end
            }
        } else {
        	LOG.info("Transfering done. Cancel timer.");
            stop(); // 如果传输结束，停止timer记时器
            return;
        }
    }
    
    public void stop() {
    	LOG.info("Try to stop progress monitor.");
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            isScheduled = false;
        }
        LOG.info("Progress monitor stoped.");
    }
    
    public void start() {
    	LOG.info("Try to start progress monitor.");
    	startTime = new Date();
    	setStartTime(startTime);
    	LOG.info("start time is "+startTime);
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(this, 1000, progressInterval);
        isScheduled = true;
        LOG.info("Progress monitor started.");
    }
    
    /**
     * 打印progress信息
     * @param transfered
     */
    private void sendProgressMessage(long transfered) {
        if (fileSize != 0) {
            double d = ((double)transfered * 100)/(double)fileSize;
            DecimalFormat df = new DecimalFormat( "#.##"); 
            LOG.info("Sending progress message: " + df.format(d) + "%");
        } else {
        	LOG.info("Sending progress message: " + transfered);
        }
    }

    /**
     * 实现了SftpProgressMonitor接口的count方法
     */
    public boolean count(long count) {
        if (isEnd()) return false;
        if (!isScheduled) {
            start();
        }
        add(count);
        return true;
    }

    /**
     * 实现了SftpProgressMonitor接口的end方法
     */
    public void end() {
        setEnd(true);
        endTime = new Date();
        LOG.info("end time is "+endTime);
        LOG.info("transfering end ,data transfer time: "+(endTime.getTime()-startTime.getTime()));
    }
    
    private synchronized void add(long count) {
        transfered = transfered + count;
    }
    
    private synchronized long getTransfered() {
        return transfered;
    }
    
    public synchronized void setTransfered(long transfered) {
        this.transfered = transfered;
    }
    
    private synchronized void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
    
    private synchronized boolean isEnd() {
        return isEnd;
    }

    public void init(int op, String src, String dest, long max) {
        // Not used for putting InputStream
    }

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
