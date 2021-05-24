import java.util.Scanner;

/**
 * 时间戳是指格林威治时间自1970年1月1日（08:00:00 GMT）至当前时间的总秒数
 * 平常年按1小时3600秒，1天24小时，一年365天计算为31536000秒
 * 闰年按1小时3600秒，1天24小时，一年366天计算为31622400秒
 */
class Main {

    //时间戳
    static int timestamp = 0;
    //    因为月份和天数没有0 所以从1开始
    static int month = 1;
    static int day = 1;
    static int hour = 0;
    static int min = 0;
    static int sec = 0;
    static int currentYear = 1970;
    static boolean finished = false;

    public static void main(String[] args) {

        //闰年1-12月分别为31天，29天，31天，30天，31天，30天，31天，31天，30天，31天，30天，31天，其中闰年2月份由平年的28天变为29天，闰年共有366天。
        int[] leapYearMonth = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        //平年1-12月分别为31天，28天，31天，30天，31天，30天，31天，31天，30天，31天，30天，31天，其中闰年2月份由平年的28天变为29天，闰年共有366天。
        int[] commonYearMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        //一天有86400秒
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入时间戳");
        while (true){
            int judgeTimestamp=scanner.nextInt();
            if (judgeTimestamp>=0) {
                timestamp = judgeTimestamp;
                break;
            }
            else
                System.out.println("时间戳必须为正 请重新输入");
        }

        while (!finished) {
            if (LeapYear(currentYear)) {
                if (timestamp % 31622400 == timestamp) {
                    getCurrentTime(leapYearMonth);
                } else {
                    timestamp -= 31622400;
                    currentYear++;
                }
            } else {
                if (timestamp % 31536000 == timestamp) {
                    getCurrentTime(commonYearMonth);
                } else {
                    timestamp -= 31536000;
                    currentYear++;
                }
            }
        }
    }

    /**
     * 根据不同的年份类型计算不同的值（闰年和平年）
     * @param monthType
     */
    public static void getCurrentTime(int[] monthType) {
        for (int i = 0; i < 12; i++) {
            if (timestamp % (monthType[i] * 86400) != timestamp) {
                timestamp -= monthType[i] * 86400;
                month++;
            } else {
                day += timestamp / 86400;
                timestamp %= 86400;
                hour = timestamp / 3600 + 8;
                timestamp %= 3600;
                min = timestamp / 60;
                timestamp %= 60;
                sec = timestamp;
//                优化显示 避免出现单个数字的尴尬情况
                String optimizedMonth = String.valueOf(Main.month);
                String optimizedDay = String.valueOf(day);
                String optimizedHour = String.valueOf(hour);
                String optimizedMin = String.valueOf(min);
                String optimizedSec = String.valueOf(sec);
                if (month / 10 == 0)
                    optimizedMonth = "0" + optimizedMonth;
                if (day / 10 == 0)
                    optimizedDay = "0" + optimizedDay;
                if (hour / 10 == 0)
                    optimizedHour = "0" + optimizedHour;
                if (min / 10 == 0)
                    optimizedMin = "0" + optimizedMin;
                if (sec / 10 == 0)
                    optimizedSec = "0" + optimizedSec;
//                输出结果
                System.out.println(currentYear + "-" + optimizedMonth + "-" + optimizedDay + " " + optimizedHour + ":" + optimizedMin + ":" + optimizedSec);
//            改变标志位 让while循环停止以便退出main线程
                finished = true;
                break;
            }
        }
    }

    /**
     * 判断是否是闰年
     * @param year
     * @return
     */
    public static boolean LeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0))
            return true;
        return false;
    }

}