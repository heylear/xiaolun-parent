package cn.glor.xiaolun.app.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by heylear on 15/11/1.
 */
public class DistanceUtil {

    public final static Double EARTH_RADIUS = 6371D;


    public final static String LEFT_TOP_POINT = "leftTopPoint";

    public final static String RIGHT_TOP_POINT = "rightTopPoint";

    public final static String LEFT_BOTTOM_POINT = "leftBottomPoint";

    public final static String RIGHT_BOTTOM_POINT = "rightBottomPoint";



    /**
     * 计算经纬度点对应正方形4个点的坐标
     * @param latitude 经度
     * @param longitude 纬度
     * @param distance 距离 单位公里
     * @return
     */
    public static Map<String, double[]> getSquarePointMap(double latitude, double longitude, double distance){
        Map<String, double[]> squareMap = new HashMap<String, double[]>();

        // 计算经度弧度,从弧度转换为角度

        double dLongitude = 2 * (Math.asin(Math.sin(distance

                / (2 * EARTH_RADIUS))

                / Math.cos(Math.toRadians(latitude))));

        dLongitude = Math.toDegrees(dLongitude);

        // 计算纬度角度

        double dLatitude = distance / EARTH_RADIUS;

        dLatitude = Math.toDegrees(dLatitude);

        // 正方形

        double[] leftTopPoint = { latitude + dLatitude, longitude - dLongitude };

        double[] rightTopPoint = { latitude + dLatitude, longitude + dLongitude };

        double[] leftBottomPoint = { latitude - dLatitude,

                longitude - dLongitude };

        double[] rightBottomPoint = { latitude - dLatitude,

                longitude + dLongitude };

        squareMap.put(LEFT_TOP_POINT, leftTopPoint);

        squareMap.put(RIGHT_TOP_POINT, rightTopPoint);

        squareMap.put(LEFT_BOTTOM_POINT, leftBottomPoint);

        squareMap.put(RIGHT_BOTTOM_POINT, rightBottomPoint);

        return squareMap;
    }
}
