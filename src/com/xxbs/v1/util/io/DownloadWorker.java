package com.xxbs.v1.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.SwingWorker;



/**
 * @author tang
 */
public class DownloadWorker extends SwingWorker<Void, Void> {

	private final int bufferSize = 4096;
	private long totalBytesRead;
	private long totalDownloadTime;
	private DownloadCaller downloadCeller;
	private HTTPDownloadUtil downloadUtil;
	private File saveFile;
	private boolean isStop = false;
	private boolean isBreak = false;

	public DownloadWorker(DownloadCaller downloadCeller, HTTPDownloadUtil downloadUtil, String saveFilePath) {
		this.downloadCeller = downloadCeller;
		this.downloadUtil = downloadUtil;
		FileUtil.deleteFile(new File(saveFilePath));
		saveFile = FileUtil.createFile(saveFilePath);
	}

	@Override
	protected Void doInBackground() throws Exception {

		try (BufferedInputStream bis = new BufferedInputStream(downloadUtil.getInputStream());
				FileOutputStream fos = new FileOutputStream(saveFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {

			long fileSize = downloadUtil.getContentLength();

			byte[] buffer = new byte[bufferSize];

			long begintime = System.currentTimeMillis();

			int bytesRead = bis.read(buffer);

			totalDownloadTime = System.currentTimeMillis() - begintime;

			int percentCompleted = 0;

			while (bytesRead != -1 && !isBreak) {

				if (isStop) {
					continue;
				}

				bos.write(buffer, 0, bytesRead);

				totalBytesRead += bytesRead;

				if (fileSize <= 0) {
					fileSize = 1;
				}

				percentCompleted = (int) (totalBytesRead * 100 / fileSize);

				if (percentCompleted < 0) {
					percentCompleted = 0;
				}

				if (percentCompleted > 100) {
					percentCompleted = 100;
				}

				setProgress(percentCompleted);

				begintime = System.currentTimeMillis();

				bytesRead = bis.read(buffer);

				totalDownloadTime += (System.currentTimeMillis() - begintime);
			}

			if (!isBreak) {
				downloadUtil.disconnect();
			} else {
				downloadUtil.disconnect();
			}
		} catch (Exception ex) {
			if (!isBreak) {
				ex.printStackTrace();
			}
			setProgress(0);
			cancel(true);
		}
		// doInBackground方法执行完之后done不一定就会马上执行
		// 原因在于SwingWorker把done方法单独开启一个线程，并加入线程队列等待执行
		// 这会产生一个致命的问题，就是doInBackground方法执行完毕之后done方法的代码若不执行会搅乱程序的执行顺序，会造成意想不到的bug
		// 解决方案是不重写done方法也就是废弃done方法，而在doInBackground方法执行完成的最后调用自己的done
		doneDownload();
		return null;
	}

	protected void doneDownload() {
		if (!isBreak && !isStop&&!isCancelled()) {
			downloadCeller.doneDownload();
		} else {
			FileUtil.deleteFile(saveFile);
			downloadCeller.undoneDownload();
		}
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public boolean isStop() {
		return isStop;
	}
	public void setBreak(boolean isBreak) {
		this.isBreak = isBreak;
	}

	public boolean isBreak() {
		return isBreak;
	}

	public void deleteOnExit(boolean deleteOnExit) {
		if (deleteOnExit) {
			saveFile.deleteOnExit();
		}
	}

	public File getSaveFile() {
		return saveFile;
	}

	public double computeDownloadSpeed() {
		if (totalBytesRead == 0) {
			totalBytesRead = 1;
		}
		return totalBytesRead / totalDownloadTime * 1.0;
	}
	public long getTotalBytesRead() {
		return totalBytesRead;
	}
	public long getTotalDownloadTime() {
		return totalDownloadTime;
	}
}
