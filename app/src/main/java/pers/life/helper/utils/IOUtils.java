package pers.life.helper.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtil.e("close",e.getMessage());
			}
		}
		return true;
	}
}
