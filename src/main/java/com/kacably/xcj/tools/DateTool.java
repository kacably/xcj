package com.kacably.xcj.tools;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

public class DateTool {


    public static String dataToString(String format, Date date) {
        //格式定义
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        //时间转换
        String reDateString = simpleDateFormat.format(date);
        //返回string
        return reDateString;
    }

    // 常量定义：日时字符串格式
    /**
     * 日时字符串格式：默认格式
     */
    public final static int FORMAT_DEFAULT = 0; // 默认格式

    /**
     * 日时字符串格式：长格式（如：年份用4位表示）
     */
    public final static int FORMAT_LONG = 1; // 长格式（如：年份用4位表示）

    /**
     * 日时字符串格式：短格式（如：年份用2位表示）
     */
    public final static int FORMAT_SHORT = 2; // 短格式（如：年份用2位表示）

    /**
     * 默认日期字符串格式 "yyyy-MM-dd"
     */
    public final static String DATE_DEFAULT = "yyyy-MM-dd";

    /**
     * 日期字符串格式 "yyyyMM"
     */
    public final static String DATE_YYYYMM = "yyyyMM";

    /**
     * 日期字符串格式 "yyyyMMdd"
     */
    public final static String DATE_YYYYMMDD = "yyyyMMdd";

    /**
     * 日期字符串格式 "yyyy-MM"
     */
    public final static String DATE_YYYY_MM = "yyyy-MM";

    /**
     * 日期字符串格式 "yyyy-MM-dd"
     */
    public final static String DATE_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 默认日时字符串格式 "yyyy-MM-dd HH:mm:ss"
     */
    public final static String DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日时字符串格式 "yyyy-MM-dd HH:mm"
     */
    public final static String DATETIME_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日时字符串格式 "yyyy-MM-dd HH:mm:ss"
     */
    public final static String DATETIME_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日时字符串格式 "yyyy-MM-dd HH:mm:ss.SSS"
     */
    public final static String DATETIME_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 默认时间字符串格式 "HH:mm:ss"
     */
    public final static String TIME_DEFAULT = "HH:mm:ss";

    /**
     * 默认时间字符串格式 "HH:mm"
     */
    public final static String TIME_HH_MM = "HH:mm";

    /**
     * 默认时间字符串格式 "HH:mm:ss"
     */
    public final static String TIME_HH_MM_SS = "HH:mm:ss";

    // 常量定义：日期/时间 Field
    /**
     * 日期/时间的各个部分标识：年（1）
     */
    public final static int YEAR = 1;

    /**
     * 日期/时间的各个部分标识：月（2）
     */
    public final static int MONTH = 2;

    /**
     * 日期/时间的各个部分标识：日（3）
     */
    public final static int DAY = 3;

    /**
     * 日期/时间的各个部分标识：时（4）
     */
    public final static int HOUR = 4;

    /**
     * 日期/时间的各个部分标识：分（5）
     */
    public final static int MINUTE = 5;

    /**
     * 日期/时间的各个部分标识：秒（6）
     */
    public final static int SECOND = 6;

    /**
     * 日期/时间的各个部分标识：一刻钟（11）
     */
    public final static int QUATER = 11;

    /**
     * 日期/时间的各个部分标识：一周（12）
     */
    public final static int WEEK = 12;

    /**
     * 当月天数（13）
     */
    public final static int DAY_OF_MONTH = 13;

    /**
     * 当月周数（14）
     */
    public final static int WEEK_OF_MONTH = 14;

    /**
     * 当年天数（15）
     */
    public final static int DAY_OF_YEAR = 15;

    /**
     * 当月周数（16）
     */
    public final static int WEEK_OF_YEAR = 16;

    /**
     *
     */
    public static final long MILLIS_PER_SECOND = 1000;
    /**
     *
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    /**
     *
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    /**
     *
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;

    /**
     * 默认构造函数
     */
    private DateTool() {
        super();
    }

    // =========================================================
    //

    private static final Map<String, SimpleDateFormat> formatMap = new HashMap<String, SimpleDateFormat>();;

    private static SimpleDateFormat getFormat(String pattern) {
        SimpleDateFormat format = formatMap.get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern);
            formatMap.put(pattern, format);
        }
        return format;
    }

    /**
     * 获取值为系统当前时间的Date对象
     *
     * @return Date
     */
    public static Date now() {
        return new java.util.Date(System.currentTimeMillis());
    }

    // ================================================================
    // 时间比较。
    // 返回值：两者之间的时间差（毫秒数）。

    /**
     * 时间比较
     *
     * @param dtDate   待比较的时间对象：Date对象
     * @param lvdtDate 待比较的时间对象：Date对象
     * @return 两者之间的时间差（毫秒数）。
     */
    public static long compareTo(Date dtDate, Date lvdtDate) {
        long lmTime = (dtDate == null ? 0 : dtDate.getTime());
        long laTime = (lvdtDate == null ? 0 : lvdtDate.getTime());
        return (lmTime - laTime);
    }

    /**
     * 取与指定时间之间的（年/月/日/小时/分/秒/季度/周）差
     *
     * @param dtDate   时间
     * @param lvnPart  定义返回值的类型（DateTool.YEAR等）
     * @param lvdtDate 指定的时间对象：java.util.Date
     * @return long
     */
    public static long dateDiff(Date dtDate, Date lvdtDate, int lvnPart) {
        // 检验参数的有效性
        if (dtDate == null || lvdtDate == null)
            throw new IllegalArgumentException("无效的日期时间参数（DateTool.dateDiff(int,java.util.Date,java.util.Date)）");
        // 对于年和月的处理，由于一年/月的准确天数不确定，
        // 故需要根据具体的年月进行计算和判断；
        if (lvnPart == YEAR)
            return dateDiff_year(dtDate, lvdtDate);
        if (lvnPart == MONTH)
            return dateDiff_month(dtDate, lvdtDate);
        // else, 对于有准确时间量的单位（天/小时/分/秒）可以通过直接计算时间差得到。
        long lmTime = (dtDate == null ? 0 : dtDate.getTime());
        long laTime = (lvdtDate == null ? 0 : lvdtDate.getTime());
        long lDiffTime = (lmTime - laTime) / 1000; // 秒数
        switch (lvnPart) {
            // case YEAR: { return lDiffTime/(3600*24)/365; } //year
            case DAY: {
                return lDiffTime / (3600 * 24);
            } // day
            case HOUR: {
                return lDiffTime / 3600;
            } // hour
            case MINUTE: {
                return lDiffTime / 60;
            } // minute
            case SECOND: {
                return lDiffTime;
            } // second
            case QUATER: {
                return lDiffTime / (3600 * 24) / 91;
            } // quater
            case WEEK: {
                return lDiffTime / (3600 * 24) / 7;
            } // week
            default: {
                throw new IllegalArgumentException("参数无效(DateTool.dateDiff(int,java.util.Date))");
            }
        }
    }

    // 计算当前时间与指定时间的 年 差
    // 内部函数，dateDiff中使用
    // 备注：不检测对象的有效性（即是否为空）。检测在调用前做。
    private static long dateDiff_year(Date dtDate, Date lvdtDate) {
        int nYear1, nYear2;
        int nMonth1, nMonth2;

        Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(TimeZone.getDefault());

        cal.setTime(dtDate);
        nYear1 = cal.get(Calendar.YEAR);
        nMonth1 = cal.get(Calendar.MONTH);

        cal.setTime(lvdtDate);
        nYear2 = cal.get(Calendar.YEAR);
        nMonth2 = cal.get(Calendar.MONTH);

        if (nYear1 == nYear2)
            return 0;
        else if (nYear1 > nYear2)
            return (nYear1 - nYear2) + (nMonth1 >= nMonth2 ? 0 : -1);
        else
            return (nYear1 - nYear2) + (nMonth1 > nMonth2 ? 1 : 0);
    }

    // 计算当前时间与指定时间的 月 差
    // 内部函数，dateDiff中使用
    // 备注：不检测对象的有效性（即是否为空）。检测在调用前做。

    /**
     * 计算当前时间对象与指定时间的【月】差
     *
     * @param lvdtDate 指定的时间对象：
     * @return long
     */
    private static long dateDiff_month(Date dtDate, Date lvdtDate) {
        int nMonths1, nMonths2; // 总的月数=年*12+月
        int nDay1, nDay2; // 日期中当月的天数

        Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(TimeZone.getDefault());

        cal.setTime(dtDate);
        nMonths1 = cal.get(Calendar.YEAR) * 12 + cal.get(Calendar.MONTH);
        nDay1 = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(lvdtDate);
        nMonths2 = cal.get(Calendar.YEAR) * 12 + cal.get(Calendar.MONTH);
        nDay2 = cal.get(Calendar.DAY_OF_MONTH);

        if (nMonths1 == nMonths2)
            return 0;
        else if (nMonths1 > nMonths2)
            return nMonths1 - nMonths2 + (nDay1 < nDay2 ? -1 : 0);
        else
            return nMonths1 - nMonths2 + (nDay1 > nDay2 ? 1 : 0);
    }

    // 日期/时间的分解
    // 参数：lvnField指定域（年/月/日/小时/分/秒/星期）编号

    /**
     * 日期/时间的分解
     *
     * @param dtDate   日期
     * @param lvnField 指定域（年/月/日/小时/分/秒/星期）编号（定义在DateTool.YEAR）
     * @return int
     */
    public static int get(Date dtDate, int lvnField) {
        if (dtDate == null) {
            throw new IllegalArgumentException("日期时间为空（DateTool.get）");
        }
        Calendar cal = new java.util.GregorianCalendar();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTime(dtDate);
        switch (lvnField) {
            case YEAR: {
                return cal.get(Calendar.YEAR);
            }
            case MONTH: {
                return cal.get(Calendar.MONTH) + 1;
            }
            case DAY: {
                return cal.get(Calendar.DAY_OF_MONTH);
            }
            case HOUR: {
                return cal.get(Calendar.HOUR_OF_DAY);
            }
            case MINUTE: {
                return cal.get(Calendar.MINUTE);
            }
            case SECOND: {
                return cal.get(Calendar.SECOND);
            }
            case WEEK: {
                return cal.get(Calendar.DAY_OF_WEEK);
            }
            case DAY_OF_MONTH: {
                return ((GregorianCalendar) cal).getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            case WEEK_OF_MONTH: {
                return ((GregorianCalendar) cal).getActualMaximum(Calendar.WEEK_OF_MONTH);
            }
            case DAY_OF_YEAR: {
                return ((GregorianCalendar) cal).getActualMaximum(Calendar.DAY_OF_YEAR);
            }
            case WEEK_OF_YEAR: {
                return ((GregorianCalendar) cal).getActualMaximum(Calendar.WEEK_OF_YEAR);
            }
            default: {
                throw new IllegalArgumentException("无效的日期时间域参数（DateTool.get）");
            }
        }
    }

    /**
     * 获取当前对象中的【年】的部分
     *
     * @param dtDate 日期
     * @return int
     */
    public static int getYear(Date dtDate) {
        return DateTool.get(dtDate, YEAR);
    }

    /**
     * 获取当前对象中的【月】的部分
     *
     * @param dtDate 日期
     * @return int
     */
    public static int getMonth(Date dtDate) {
        return DateTool.get(dtDate, MONTH);
    }

    /**
     * 获取当前对象中的【日】的部分
     *
     * @param dtDate 日期
     * @return int
     */
    public static int getDay(Date dtDate) {
        return DateTool.get(dtDate, DAY);
    }

    /**
     * 获取当前对象中的【小时】的部分
     *
     * @param dtDate 日期
     * @return int
     */
    public static int getHour(Date dtDate) {
        return DateTool.get(dtDate, HOUR);
    }

    /**
     * 获取当前对象中的【分】的部分
     *
     * @param dtDate 日期
     * @return int
     */
    public static int getMinute(Date dtDate) {
        return DateTool.get(dtDate, MINUTE);
    }

    /**
     * 获取当前对象中的【秒】的部分
     *
     * @param dtDate 日期
     * @return int
     */
    public static int getSecond(Date dtDate) {
        return DateTool.get(dtDate, SECOND);
    }

    /**
     * 取当前日期是所在week的第几天 <br>
     * 说明：一个礼拜的第一天为Monday(0)，最后一天为Sunday(6)
     *
     * @param dtDate 日期
     * @return int
     */
    public static int getDayOfWeek(Date dtDate) {
        return DateTool.get(dtDate, WEEK);
    }

    // 日期时间增/减函数
    // 参数：lvnField：指定域；lvnAdd：增加数目（负值为减）

    /**
     * 日期时间增/减函数
     *
     * @param dtDate   日期
     * @param lvnField 指定域（例如DateTool.YEAR等）
     * @param lvnAdd   增加数目（负值为减）
     * @return 返回当前对象本身
     */
    public static Date dateAdd(Date dtDate, int lvnField, int lvnAdd) {
        if (dtDate == null) {
            throw new IllegalArgumentException("日期时间为空（DateTool.dateAdd）");
        }
        int nCalField = 0;
        switch (lvnField) {
            case YEAR:
                nCalField = Calendar.YEAR;
                break;
            case MONTH:
                nCalField = Calendar.MONTH;
                break;
            case WEEK:
                nCalField = Calendar.DATE;
                lvnAdd = lvnAdd * 7;
                break;
            case DAY:
                nCalField = Calendar.DATE;
                break;
            case HOUR:
                nCalField = Calendar.HOUR;
                break;
            case MINUTE:
                nCalField = Calendar.MINUTE;
                break;
            case SECOND:
                nCalField = Calendar.SECOND;
                break;
            default: {
                throw new IllegalArgumentException("无效的日期时间域参数（DateTool.dateAdd）");
            }
        }
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTime(dtDate);
        cal.set(nCalField, cal.get(nCalField) + lvnAdd);
        dtDate = cal.getTime();
        return dtDate;
    }

    /**
     * 使用字符串设置日期和时间
     *
     * @param lvsValue  日期和时间字符串
     * @param lvsFormat 日期和时间字符串格式
     * @return @
     */
    public static Date parse(String lvsValue, String lvsFormat) {
        try {
            return DateTool.getFormat(lvsFormat).parse(lvsValue);
        } catch (Exception ex) {
            throw new IllegalArgumentException("日期时间字符串值和格式无效（DateTool.setDateTime）", ex);
        }
    }

    /**
     * SQL的开始日期
     *
     * @param target 日期
     * @return Date
     */
    public static Date parseSqlStartDate(Date target) {
        if (target == null) {
            return null;
        }
        return parse(format(target, DATE_DEFAULT) + " 00:00:00");
    }

    /**
     * SQL的结束日期
     *
     * @param target 日期
     * @return Date
     */
    public static Date parseSqlEndDate(Date target) {
        if (target == null) {
            return null;
        }
        return parse(format(target, DATE_DEFAULT) + " 23:59:59");
    }

    /**
     * 使用字符串设置日期值
     *
     * @param lvsDateValue  日期值字符串
     * @param lvnFormatType 日期格式类型
     * @return @ 若格式不正确，则抛出异常
     */
    public static Date parseDate(String lvsDateValue, int lvnFormatType) {
        String sDateValue;
        boolean blHasSepChar = false; // if Date string value has seperator char
        int nLen = lvsDateValue.length();
        if (nLen < 6)
            throw new IllegalArgumentException("日期字符串无效（DateTool.parse）");
        try {
            switch (lvnFormatType) {
                case FORMAT_LONG: {
                    // form: (1)has seperator: yyyy-mm-dd / yyyy.mm.dd
                    // (2)has no seperator: yyyymmdd
                    blHasSepChar = (nLen >= 10);
                    sDateValue = lvsDateValue.substring(0, 4) + "-"
                            + lvsDateValue.substring((blHasSepChar ? 5 : 4), (blHasSepChar ? 7 : 6)) + "-"
                            + lvsDateValue.substring((blHasSepChar ? 8 : 6), (blHasSepChar ? 10 : 8));
                    break;
                }
                case FORMAT_SHORT: {
                    // form: (1)has seperator: yy.mm.dd / yy-mm-dd
                    // (2)has no seperator:
                    sDateValue = (lvsDateValue.charAt(0) < '5' ? "20" : "19");
                    blHasSepChar = (nLen >= 8);
                    sDateValue += lvsDateValue.substring(0, 2) + "-"
                            + lvsDateValue.substring((blHasSepChar ? 3 : 2), (blHasSepChar ? 5 : 4)) + "-"
                            + lvsDateValue.substring((blHasSepChar ? 6 : 4), (blHasSepChar ? 8 : 6));
                    break;
                }
                default: { // format: yyyy-mm-dd
                    sDateValue = lvsDateValue;
                    break;
                }
            }
            return DateTool.parse(sDateValue, DATE_DEFAULT);
        } catch (Exception ex) {
            throw new IllegalArgumentException("无效的日期字符串（CMyException.setDate）", ex);
        }
    }

    /**
     * 使用字符串设置时间值
     *
     * @param lvsTimeValue  时间值字符串
     * @param lvnFormatType 时间格式类型（例如DateTool.FORMAT_LONG等）
     * @return @ 若格式不正确，则抛出异常
     */
    public static Date parseTime(String lvsTimeValue, int lvnFormatType) {
        String sTimeValue;
        boolean blHasSepChar = false; // if Date string value has seperator char
        int nLen = lvsTimeValue.length();

        if (nLen < 4)
            throw new IllegalArgumentException("时间字符串格式无效（）");
        try {
            switch (lvnFormatType) {
                case FORMAT_LONG: {
                    // form: (1)has seperator: HH:mm:ss
                    // (2)has no seperator: HHmmss
                    blHasSepChar = (nLen >= 8);
                    sTimeValue = lvsTimeValue.substring(0, 2) + ":"
                            + lvsTimeValue.substring((blHasSepChar ? 3 : 2), (blHasSepChar ? 5 : 4)) + ":"
                            + lvsTimeValue.substring((blHasSepChar ? 6 : 4), (blHasSepChar ? 8 : 6));
                    break;
                }
                case FORMAT_SHORT: {
                    // form: (1)has seperator: HH:mm
                    // (2)has no seperator: HHmm
                    blHasSepChar = (nLen >= 5);
                    sTimeValue = lvsTimeValue.substring(0, 2) + ":"
                            + lvsTimeValue.substring((blHasSepChar ? 3 : 2), (blHasSepChar ? 5 : 4)) + ":00";
                    break;
                }
                default: { // format: HH:mm:ss
                    sTimeValue = lvsTimeValue;
                    break;
                }
            }
            return DateTool.parse(sTimeValue, TIME_DEFAULT);
        } catch (Exception ex) {
            throw new IllegalArgumentException("无效的时间字符串（CMyException.setTime）", ex);
        }
    }

    // //
    // 格式化输出字符串

    /**
     * 获取格式化的日期时间字符串
     *
     * @param dtDate    日期
     * @param lvsFormat 指定日期时间字符串格式（例如："yyyy-MM-dd HH:mm:ss"）
     * @return String
     */
    public static String format(Date dtDate, String lvsFormat) {
        if (dtDate == null)
            return null;
        try {
            return getFormat(lvsFormat).format(dtDate);
        } catch (Exception ex) {
            throw new IllegalArgumentException("指定的日期时间格式有错（DateTool.formatDateTime）", ex);
        }
    }

    /**
     * 输出格式化的日期时间字符串 <br>
     *
     * @param dtDate 日期
     * @return 若日期时间为空，或者格式化对象dtFormater为空，返回null. @
     */
    public String format(Date dtDate) {
        return format(dtDate, DATETIME_DEFAULT);
    }

    /**
     * 提取 日期时间 字符串数据的格式
     *
     * @param lvsValue 指定的日期时间字符串
     * @return 若正确解析，则返回日时字符串的格式字符串；否则返回null。
     */
    public static String extractDateTimeFormat(String lvsValue) {
        final char[] FORMAT_CHAR = {'y', 'M', 'd', 'H', 'm', 's'}; // 域字符
        return extractFormat(lvsValue, FORMAT_CHAR);
    }

    /**
     * 提取 日期 字符串数据的格式
     *
     * @param lvsValue 指定的日期字符串
     * @return 若正确解析，则返回日期字符串的格式字符串；否则返回null。
     */
    public static String extractDateFormat(String lvsValue) {
        final char[] FORMAT_CHAR = {'y', 'M', 'd'}; // 域字符
        return extractFormat(lvsValue, FORMAT_CHAR);
    }

    /**
     * 提取 时间 字符串数据的格式
     *
     * @param lvsValue 指定的时间字符串
     * @return 若正确解析，则返回时间字符串的格式字符串；否则返回null。
     */
    public static String extractTimeFormat(String lvsValue) {
        final char[] FORMAT_CHAR = {'H', 'm', 's'}; // 域字符
        return extractFormat(lvsValue, FORMAT_CHAR);
    }

    /**
     * 内部函数，解析指定日期时间字符串的格式。
     *
     * @param lvsValue    日期或时间或日时字符串
     * @param _formatChar 域字符集。如：日期的域字符集为{'y','M','d'}
     * @return 若解析成功，则返回字符串对应的格式；否则返回null.
     */
    private static String extractFormat(String lvsValue, char[] _formatChar) {
        // 检查参数有效性
        if (lvsValue == null)
            return null;
        char[] buffValue = lvsValue.trim().toCharArray();
        if (buffValue.length == 0)
            return null;
        // 解析lvsValue的数据格式 （如：yyyy-MM-dd HH:mm:ss ）
        StringBuffer buffFormat = new StringBuffer(19); // 用于保存lvsValue的格式化
        int nAt = 0, nAtField = 0;
        char aChar;
        while (nAt < buffValue.length) {
            aChar = buffValue[nAt++];
            if (Character.isDigit(aChar)) { // 是数字
                buffFormat.append(_formatChar[nAtField]); // 填充格式
            } else {
                buffFormat.append(aChar); // 分割符
                nAtField++; // 当前域结束
                if (nAtField >= _formatChar.length)
                    break;
            }
        }
        return buffFormat.toString();
    }

    /**
     * 使用未知格式的字符串值设置日期时间
     *
     * @param lvsValue 指定的日期时间值（字符串）
     * @return 若设置成功，则返回true；否则返回false.
     */
    public static Date parse(String lvsValue) {
        String sFormat = extractDateTimeFormat(lvsValue);
        if (lvsValue == null)
            return null;
        // else
        return DateTool.parse(lvsValue, sFormat);
    }

    /**
     * @param lvsValue 日期
     * @return Date
     */
    public static Date parse(long lvsValue) {
        if (lvsValue <= 0) {
            return null;
        }
        return new Date(lvsValue);
    }

    /**
     * 把以毫秒计算的使用时间格式化为“xxx分xx秒”的格式
     *
     * @param iMillis 毫秒数
     * @return 格式化的时间
     */
    public final static String formatTimeUsed(long iMillis) {
        if (iMillis <= 0) {
            return "";
        }
        int iSecond = 0;
        int iMinute = 0;
        StringBuffer sb = new StringBuffer(16);
        iSecond = (int) (iMillis / 1000);
        iMillis = iMillis % 1000;
        if (iSecond > 0) {
            iMinute = iSecond / 60;
            iSecond = iSecond % 60;
        }
        if (iMinute > 0) {
            sb.append(iMinute).append('M');
            if (iSecond < 10) {
                sb.append('0');
            }
            sb.append(iSecond);
        } else {
            sb.append(iSecond).append('.');
            if (iMillis < 10) {
                sb.append('0').append('0');
            } else if (iMillis < 100) {
                sb.append('0');
            }
            sb.append(iMillis);
        }
        sb.append('S');
        return sb.toString();
    }
}
