package com.diamondsoftware.android.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;

import android.content.Context;

public class Logger {
	private int _logFilter=100;
	private String _caller="";
	private final Context mCtx;
	public Logger(int logFilter,String caller,Context ctx) {
		_logFilter=logFilter;
		_caller=caller;
		this.mCtx = ctx;

	}
	public synchronized void log(String string, int level) {
		if (level >= _logFilter) {
			Date date = new Date();
			FileOutputStream fos = null;
			PrintWriter pw = null;
			try {
				fos = getLogOutputStream();
				if(fos!=null) {
					pw = new PrintWriter(fos); 
					pw.write(_caller
							+" "
							+ (date.getYear() + 1900)
							+ "-"
							+ padString(true, 2, String
									.valueOf((date.getMonth() + 1)), '0')
									+ "-"
									+ padString(true, 2, String.valueOf(date.getDate()),
									'0')
									+ " "
									+ padString(true, 2, String.valueOf(date.getHours()),
									'0')
									+ ":"
									+ padString(true, 2, String.valueOf(date.getMinutes()),
									'0')
									+ ":"
									+ padString(true, 2, String.valueOf(date.getSeconds()),
									'0') + "|" + string + "\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					pw.close();
				} catch (Exception eee) {
				}
				try {
					fos.close();
				} catch (Exception eee) {
				}
			}
		}
	}
	private FileOutputStream getLogOutputStream() throws FileNotFoundException {
		FileOutputStream fileOutputStream_Log = null;
		File file = null;
		if (isSdPresent()) {
			file = new File("/sdcard/masseur");
			if (!file.exists()) {
				file.mkdirs();
			}
			fileOutputStream_Log = new FileOutputStream("/sdcard/masseur/log.txt",
					true);
		}
		return fileOutputStream_Log;
	}
	public static boolean isSdPresent() {
		String sdState=android.os.Environment.getExternalStorageState();
		return sdState.equals(
				android.os.Environment.MEDIA_MOUNTED) ;
	}
	public static String padString(boolean left, int size, String str,
			char padder) {
		if (str == null) {
			return "";
		}
		if (str.length() <= size) {
			for (int c = 0; c < (size - str.length()); c++) {
				if (left) {
					str = String.valueOf(padder).toString() + str;
				} else {
					str = str + String.valueOf(padder);
				}
			}
			return str;
		} else {
			return str;
		}

	}	
}
