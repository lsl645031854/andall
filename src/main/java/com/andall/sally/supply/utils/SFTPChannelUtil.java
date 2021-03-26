package com.andall.sally.supply.utils;

import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * SFTP 文件上传
 * 
 * @author zhaozhaoxin
 *
 */
public class SFTPChannelUtil {

	// http://www.cnblogs.com/longyg/archive/2012/06/25/2556576.html

	private static final Logger log = LoggerFactory
			.getLogger(SFTPChannelUtil.class);

	Session session = null;
	Channel channel = null;
	SFTPConstants constants = null;

	public SFTPChannelUtil() {
	}

	public SFTPChannelUtil(SFTPConstants constants) {
		this.constants = constants;
	}

	public ChannelSftp getChannel() throws JSchException {
		if (null == constants)
			return null;

		String ftpHost = constants.getHost();
		int ftpPort = Integer.valueOf(constants.getPort());
		String ftpUserName = constants.getUserName();
		String privateKey = constants.getPrivateKey();
		String ftpPassword = constants.getPassword();

		JSch jsch = new JSch(); // 创建JSch对象
		if (privateKey != null && !"".equals(privateKey)) {
			// 使用密钥验证方式，密钥可以使有口令的密钥，也可以是没有口令的密钥
			jsch.addIdentity(privateKey);

		}
		session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
		log.debug("Session created.");
		if (ftpPassword != null) {
			session.setPassword(ftpPassword); // 设置密码
		}
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		config.put("PreferredAuthentications",
				"password,gssapi-with-mic,publickey,keyboard-interactive");
		session.setConfig(config); // 为Session对象设置properties
		session.setServerAliveInterval(1000 * 100);
		session.setTimeout(60000); // 设置timeout时间
		session.connect(); // 通过Session建立链接
		log.debug("Session connected.");

		log.debug("Opening Channel.");
		channel = session.openChannel("sftp"); // 打开SFTP通道
		channel.connect(); // 建立SFTP通道的连接
		log.info("Connected successfully to ftpHost = " + ftpHost
				+ ",as ftpUserName = " + ftpUserName + ", returning: "
				+ channel);
		return (ChannelSftp) channel;
	}

	public void closeChannel() throws Exception {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}



	/**
	 * 上传文件
	 * 
	 * @param srcFile
	 *            源文件
	 * @param dest
	 *            目标文件
	 * @return
	 */
	public boolean fileUpload(String srcFile, String dest) {
		File file = new File(srcFile);
		return this.fileUpload(file, dest);
	}

	public boolean  filePwdUpload(String srcFile, String dest) {
		File file = new File(srcFile);

		if (null == file || !file.exists()) {
			log.error("fileUpload file 为空或不存在：{}", file);
			return false;
		}
		if (null == dest || "".equals(dest)) {
			log.error("fileUpload dest 为空");
			return false;
		}

		long startTime = System.currentTimeMillis();

		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
		} catch (JSchException e) {
			log.error(
					"get connect from dr sftp is error param is {},error_msg is {}",
					JSON.toJSONString(constants), e);
			return false;
		}
		createDirs(channelSftp,dest);
		try {
			channelSftp.put(file.getPath(), dest, new FileProgressMonitor(
					srcFile.length()), ChannelSftp.OVERWRITE);
			return true;
		} catch (Exception e) {
			log.error("send file to failed...{}" + e);
			e.printStackTrace();
		} finally {
			try {
				closeChannel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Long endTime = System.currentTimeMillis();
		Long time = endTime - startTime;
		log.info("send file to finished ...[{}],共耗时{}ms", file.getPath(),
				time);

		return false;
	}

	/**
	 * 上传文件
	 * 
	 * @param srcFile
	 * @param dest
	 * @return
	 */
	public boolean fileUpload(File srcFile, String dest) {
		if (null == srcFile || !srcFile.exists()) {
			log.error("fileUpload srcFile 为空或不存在：{}", srcFile);
			return false;
		}
		if (null == dest || "".equals(dest)) {
			log.error("fileUpload dest 为空");
			return false;
		}

		long startTime = System.currentTimeMillis();

		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
		} catch (JSchException e) {
			log.error(
					"get connect from dr sftp is error param is {},error_msg is {}",
					JSON.toJSONString(constants), e);
			return false;
		}

		try {
			channelSftp.put(srcFile.getPath(), dest, new FileProgressMonitor(
					srcFile.length()), ChannelSftp.OVERWRITE);
			return true;
		} catch (Exception e) {
			log.error("send file to failed...{}" + e);
			e.printStackTrace();
		} finally {
			try {
				closeChannel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Long endTime = System.currentTimeMillis();
		Long time = endTime - startTime;
		log.info("send file to finished ...[{}],共耗时{}ms", srcFile.getPath(),
				time);

		return false;
	}

	public boolean fileUpload(InputStream inputStream, String dest) {

		if (null == dest || "".equals(dest)) {
			log.error("fileUpload dest 为空");
			return false;
		}

		long startTime = System.currentTimeMillis();

		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
		} catch (JSchException e) {
			log.error(
					"get connect from dr sftp is error param is {},error_msg is {}",
					JSON.toJSONString(constants), e);
			return false;
		}

		try {
			channelSftp.put(inputStream, dest);
			return true;
		} catch (Exception e) {
			log.error("send file to failed...{}" + e);
			e.printStackTrace();
		} finally {
			try {
				closeChannel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Long endTime = System.currentTimeMillis();
		Long time = endTime - startTime;
		log.info("send file to finished ...[{}],共耗时{}ms",
				time);

		return false;
	}
	/**
	 * 判断目录是否存在
	 */
	public boolean isDirExist(ChannelSftp channelSftp, String directory) {
		boolean isDirExist = true;
		try {
			SftpATTRS sftpATTRS = channelSftp.lstat(directory);
			return sftpATTRS.isDir();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExist = false;
			}
		}
		return isDirExist;
	}

	public boolean isFileExist(String filePath) throws JSchException {
		boolean isDirExist = true;
		ChannelSftp channelSftp = getChannel();
		try {
			SftpATTRS sftpATTRS = channelSftp.stat(filePath);
			if (sftpATTRS != null){
				return true;
			}
			return false;
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().equals("no such file")) {
				isDirExist = false;
			}
		}
		return isDirExist;
	}
	public void createDirs(ChannelSftp channelSftp, String dectFilePath) {
		if (null == channelSftp || StringUtils.isBlank(dectFilePath)) {
			log.error("SFTP Util createDirs 参数为空，dectFilePath={}", dectFilePath);
			return;
		}
		File dectFile = new File(dectFilePath);
		String temp = dectFile.getParent();
		temp = temp.replaceAll("\\\\", "/");
		temp = temp.replaceAll("//", "/");
		log.info("SFTP Util createDirs，temp={}",temp);
		try {
			if (isDirExist(channelSftp,temp)) {
				//channelSftp.cd(temp);
				return;
			}
			String pathArry[] = temp.split("/");
			StringBuilder filePath = new StringBuilder("/");
			for (String path : pathArry) {
				if (path.equals("")) {
					continue;
				}
				filePath.append(path).append("/");
				if (isDirExist(channelSftp,filePath.toString())) {
					//channelSftp.cd(filePath.toString());
				} else {
					// 建立目录
					channelSftp.mkdir(filePath.toString());
					// 进入并设置为当前目录
					//channelSftp.cd(filePath.toString());
				}
			}
			//channelSftp.cd(temp);
		} catch (Exception e) {
			log.error("SFTP Util createDirs 异常：" + e.getMessage(), e);
		}
	}

	public SFTPConstants getConstants() {
		return constants;
	}

	public void setConstants(SFTPConstants constants) {
		this.constants = constants;
	}

	/*
	* srcFile remote address
	* dest locao address
	 */
	public boolean fileDown(String srcFile, String dest) {
		log.info("sftp down file,srcFile={},dest={}",srcFile,dest);

		if (null == srcFile || "".equals(srcFile)) {
			log.error("fileDown srcFile 为空或不存在：{}", srcFile);
			return false;
		}
		if (null == dest || "".equals(dest)) {
			log.error("fileDown dest 为空");
			return false;
		}

		long startTime = System.currentTimeMillis();

		ChannelSftp channelSftp = null;
		try {
			channelSftp = getChannel();
		} catch (JSchException e) {
			log.error(
					"get connect from dr sftp is error param is {},error_msg is {}",
					JSON.toJSONString(constants), e);
			return false;
		}
		try {
			channelSftp.get(srcFile, dest);
			return true;
		} catch (Exception e) {
			log.error("down file to failed...{}" + e);
			e.printStackTrace();
		} finally {
			try {
				closeChannel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Long endTime = System.currentTimeMillis();
		Long time = endTime - startTime;
		log.info("down file to finished,共耗时{}ms",
				time);

		return false;
	}
	
	/**
	 * 根据sftp跟文件名称获取某个文件的内容
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public List<String> readSftpData(String filePath, String fileName) {
		List<String> readSftpDatas = new ArrayList<String>();
		BufferedReader reader = null;
		ChannelSftp sftp = null;
		try {
			sftp = getChannel();
			reader = new BufferedReader(new InputStreamReader(sftp.get(filePath + fileName), "utf-8"));
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			while ((line = br.readLine()) != null) { // 循环读取行
				readSftpDatas.add(line);
				log.info(line);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
		} catch (SftpException e) {
			// TODO: handle exception
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				closeChannel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return readSftpDatas;
	}

	/**
	 * 通过sftp+文件开头名称+文件后缀名 获取文件list
	 * @param filePath
	 * @param startName
	 * @param suffixName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getFilePaths(String filePath, String startName, String suffixName) {
		List<String> ret = new ArrayList<String>();
		ChannelSftp sftp = null;
		try {
			sftp = getChannel();
			sftp.cd(filePath);
			Vector<String> files = sftp.ls(startName + "*." + suffixName);
			for (int i = 0; i < files.size(); i++) {
				Object obj = files.elementAt(i);
				if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
					LsEntry entry = (LsEntry) obj;
					if (true && !entry.getAttrs().isDir()) {
						ret.add(entry.getFilename());
					}
					if (true && entry.getAttrs().isDir()) {
						if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
							ret.add(entry.getFilename());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			try {
				closeChannel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public void rename(String oldFileName, String newFileName, String path) {
		ChannelSftp sftp = null;
		try {
			sftp = getChannel();
			sftp.cd(path);
			sftp.rename(oldFileName, newFileName);
			log.info(oldFileName + "已经修改为" + newFileName);
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			try {
				closeChannel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		SFTPConstants c = new SFTPConstants("121.196.233.27", "22", "lawson-poshub",
				"", "Ea0nS197k1");
		SFTPChannelUtil util = new SFTPChannelUtil(c);
		try {
			 ChannelSftp sftp = util.getChannel();
			// System.err.println(sftp);

			boolean boo = util.isDirExist(sftp, "/DMP");

//			boolean boo = util.fileUpload(new File(
//					"/Users/soushihisashi/Desktop/蜡笔小新.jpg"),
//					"/order");

			System.err.println(boo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
