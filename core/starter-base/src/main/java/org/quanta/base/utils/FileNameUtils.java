package org.quanta.base.utils;

/**
 * Description:
 * Author: wzf
 * Date: 2023/10/15
 */
public class FileNameUtils {
    /**
     * 提取文件名
     * http:/www.xxxx.com/12415adsf/test124.xml
     * => test124.xml
     * */
    public static String getFileName(String url){
        return getFileName(url,true);
    }

    /**
     * 提取文件名 可选是否包含suffix
     * http:/www.xxxx.com/12415adsf/test124.xml
     * => test124
     * or test124.xml
     * */
    public static String getFileName(String url,boolean withSuffix){
        if(url== null){
            return "";
        }
        int startIndex = strFindOrDefault(url,"/",0);
        int lastIndex = withSuffix ? url.length() : strFindOrDefault(url,".",url.length());
        return url.substring( startIndex,lastIndex);
    }

    /**
     * 获取文件名后缀
     * xxx.xml
     * => xml
     * */
    public static String getFileNameSuffix(String url){
        if(url== null){
            return "";
        }
        return url.substring(strFindOrDefault(url,".",url.length()));
    }

    private static int strFindOrDefault(String str,String find,int defaultIndex){
        int index = str.lastIndexOf(find);
        return index == -1 ? defaultIndex:index;
    }
}
