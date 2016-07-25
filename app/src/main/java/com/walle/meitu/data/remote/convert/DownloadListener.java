package com.walle.meitu.data.remote.convert;

import java.io.File;

/**
 * Created by yqh on 2016/7/25.
 */
public interface DownloadListener {
    void success(File file);
    void update(long bytesRead, long contentLength);
    void error(Exception exception);
}
