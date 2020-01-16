package utils;

/**
 * 版本号工具类
 *
 * @author huangxk
 * @version [版本号, 2017-8-2](必须)
 * @see [相关类/方法](可选)
 * @since [产品/模块版本](必选)
 */
public class VersionUtil {

    /**
     * 比较版本号的大小,
     * 前者大则返回一个正数,
     * 后者大返回一个负数,相等则返回0
     * <p>
     * <pre>
     * 注意：其中 split 方法入参为正则匹配表达式，不能用"."("."在正则表达式里匹配任何值)，需要用"\\."，才算是按点号分割；
     * 这样，先分割成子串数组，再挨个比较子版本号，比较子版本号时，先比较位数，位数大的就大，位数一样时再按字符串比较方式比较；
     * </pre>
     *
     * @param newVersion
     * @param oldVersion
     * @return
     */
    public static int compareVersion(String newVersion, String oldVersion) {
        if (Util.isEmpty(newVersion) || Util.isEmpty(oldVersion)) {
            return 0;
        }

        // 注意此处为正则匹配，不能用"."；
        String[] versionArray1 = newVersion.split("\\.");
        String[] versionArray2 = oldVersion.split("\\.");

        // 取最小长度值
        int minLength = Math.min(versionArray1.length, versionArray2.length);

        int index = 0;
        int diff = 0;
        while (index < minLength && (diff = versionArray1[index].length() - versionArray2[index].length()) == 0// 先比较长度
                && (diff = versionArray1[index].compareTo(versionArray2[index])) == 0) {// 再比较字符
            ++index;
        }
        // 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

}
