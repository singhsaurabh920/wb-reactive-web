package org.wb.reactive.web.utils;
import org.springframework.boot.convert.DurationUnit;
import org.wb.reactive.web.domain.enity.Geo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class ConversionUtils {

        public static final MathContext MC = new MathContext(4);
        public static final BigDecimal _1000 = new BigDecimal(1000, MC);
        public static final String START_TIME = "00:00:01";
        public static final String END_TIME = "23:59:59";
        public static final long ONE_MILLI_SECOND = 1;
        public static final long ONE_SECOND = 1000 * ONE_MILLI_SECOND;
        public static final long ONE_MINUTE = 60 * ONE_SECOND;
        public static final long ONE_HOUR = 60 * ONE_MINUTE;
        public static final long ONE_DAY = 24 * ONE_HOUR;

        public static enum DistanceUnit {
            METER, KM, MILES,NAUTICAL
        }


        public static enum TimeUnit {
            MILLIS, SEC, MIN, HOUR, DAY
        }


        public static BigDecimal convert(double distance, DistanceUnit from, DistanceUnit to) {
            MathContext mc = new MathContext(4);
            BigDecimal bigDecimal = new BigDecimal(distance);
            switch (from) {
                case METER:
                    switch (to) {
                        case METER:
                            return bigDecimal;
                        case KM:
                            return bigDecimal.divide(new BigDecimal(1000), mc);
                        case MILES:
                            return bigDecimal.multiply(new BigDecimal(0.000621), mc);
                    }
                    break;
                case KM:
                    switch (to) {
                        case METER:
                            return bigDecimal.multiply(new BigDecimal(1000), mc);
                        case KM:
                            return bigDecimal;
                        case MILES:
                            return bigDecimal.multiply(new BigDecimal(0.621), mc);
                    }
                    break;
                case MILES:
                    switch (to) {
                        case METER:
                            return bigDecimal.multiply(new BigDecimal(1609.34), mc);
                        case KM:
                            return bigDecimal.multiply(new BigDecimal(1.60934), mc);
                        case MILES:
                            return bigDecimal;
                    }
                    break;
            }
            return bigDecimal;
        }

        public static BigDecimal convert(long duration, TimeUnit from, TimeUnit to) {
            MathContext mc = new MathContext(4);
            BigDecimal bigDecimal = new BigDecimal(duration);
            switch (from) {
                case MILLIS:
                    switch (to) {
                        case MILLIS:
                            return bigDecimal;
                        case SEC:
                            return bigDecimal.divide(new BigDecimal(1000), mc);
                        case MIN:
                            return bigDecimal.divide(new BigDecimal(60 * 1000), mc);
                        case HOUR:
                            return bigDecimal.divide(new BigDecimal(60 * 60 * 1000), mc);
                    }
                    break;
                case SEC:
                    switch (to) {
                        case MILLIS:
                            return bigDecimal.multiply(new BigDecimal(1000), mc);
                        case SEC:
                            return bigDecimal;
                        case MIN:
                            return bigDecimal.divide(new BigDecimal(60), mc);
                        case HOUR:
                            return bigDecimal.divide(new BigDecimal(60 * 60), mc);
                    }
                    break;
                case MIN:
                    switch (to) {
                        case MILLIS:
                            return bigDecimal.multiply(new BigDecimal(60 * 1000), mc);
                        case SEC:
                            return bigDecimal.multiply(new BigDecimal(1000), mc);
                        case MIN:
                            return bigDecimal;
                        case HOUR:
                            return bigDecimal.divide(new BigDecimal(60), mc);
                    }
                    break;
                case HOUR:
                    switch (to) {
                        case MILLIS:
                            return bigDecimal.multiply(new BigDecimal(60 * 60 * 1000), mc);
                        case SEC:
                            return bigDecimal.multiply(new BigDecimal(60 * 60), mc);
                        case MIN:
                            return bigDecimal.multiply(new BigDecimal(60), mc);
                        case HOUR:
                            return bigDecimal;
                    }
                    break;
            }
            return bigDecimal;
        }

        public static BigDecimal convertMeterToKiloMeter(double meters) {
            return new BigDecimal(meters, MC).divide(_1000, MC);
        }

        public static String convertMeterToKilometer(double distance) {
            return new BigDecimal(distance, MC).divide(_1000, MC).toPlainString() + "(Km)";
        }

        public static String convertFormattedTimeInMillisToHHmmss(long durationInMillis) {
            StringBuilder sb = new StringBuilder();
            int hour = 0;
            int min = 0;
            int sec = 0;
            while (durationInMillis > 1000) {
                if (durationInMillis >= ONE_HOUR) {
                    hour = (int) (durationInMillis / ONE_HOUR);
                    durationInMillis = (long) (durationInMillis % ONE_HOUR);
                } else if (durationInMillis >= ONE_MINUTE) {
                    min = (int) (durationInMillis / ONE_MINUTE);
                    durationInMillis = (long) (durationInMillis % ONE_MINUTE);
                } else if (durationInMillis >= ONE_SECOND) {
                    sec = (int) (durationInMillis / ONE_SECOND);
                    durationInMillis = (long) (durationInMillis % ONE_SECOND);
                }
            }
            sb.append(String.format("%02d", hour)).append(":");
            sb.append(String.format("%02d", min)).append(":");
            sb.append(String.format("%02d", sec));
            return sb.toString();
        }

        public static String convertSecondsToHHMMSS(long seconds) {
            long s = seconds % 60;
            long m = (seconds / 60) % 60;
            long h = (seconds / (60 * 60)) % 24;
            return String.format("%2d:%02d:%02d", h, m, s);
        }

        public static long convertMillisToMinutes(long durationInMillis) {
            return durationInMillis / (1000 * 60);
        }

        public static long convertMillisToSeconds(long durationInMillis) {
            return durationInMillis / 1 * 1000;
        }

        public static long convertMillisToHours(long durationInMillis) {
            return durationInMillis / convertMillisToMinutes(durationInMillis) * 60;
        }

        public static String decimalFormat(double decimal) {
            DecimalFormat formatter = new DecimalFormat("#0.00");
            return formatter.format(decimal);
        }
        public static double roundDown6(double d) {
            return (long) (d * 1e6) / 1e6;
        }

        public static double roundDown4(double d) {
            return (long) (d * 1e4) / 1e4;
        }

        public static double roundDown2(double d) {
            return (long) (d * 1e2) / 1e2;
        }


        public static int calculateDurationAbs(Date startDate, Date endDate, TimeUnit unit){
            return Math.abs(calculateDuration(startDate,endDate,unit));
        }

        public static int calculateDuration(Date startDate, Date endDate, TimeUnit unit) {
            try {
                long duration = endDate.getTime() - startDate.getTime();
                if (unit == TimeUnit.MIN) {
                    return Math.toIntExact(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(duration));
                } else if (unit == TimeUnit.SEC) {
                    return Math.toIntExact(java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(duration));
                } else if (unit == TimeUnit.HOUR) {
                    return Math.toIntExact(java.util.concurrent.TimeUnit.MILLISECONDS.toHours(duration));
                } else if (unit == TimeUnit.DAY) {
                    return Math.toIntExact(java.util.concurrent.TimeUnit.MILLISECONDS.toDays(duration));
                } else {
                    return 0;
                }
            } catch (Exception e) {
                return 30;
            }
        }

        public static double calculateDistance(double[] second, double[] first, DistanceUnit unit) {
            return calculateDistance(first[1], first[0], second[1], second[0], unit);
        }

        public static double calculateDistance(Geo second, Geo first, DistanceUnit unit) {
            return calculateDistance(first.getLat(), first.getLng(), second.getLat(), second.getLng(), unit);
        }

        public static double calculateDistance(double latitude2, double longitude2, double latitude1, double longitude1, DistanceUnit unit) {
            if (latitude2 == latitude1 && longitude2 == longitude1) {
                return 0.0;
            }
            double theta = longitude2 - longitude1;
            double distance = Math.sin(deg2rad(latitude2)) * Math.sin(deg2rad(latitude1)) + Math.cos(deg2rad(latitude2)) * Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(theta));
            distance = Math.acos(distance);
            distance = rad2deg(distance);
            distance = distance * 60 * 1.1515;
            if (unit == DistanceUnit.KM) {
                distance = distance * 1.609344;
            } else if (unit == DistanceUnit.NAUTICAL) {
                distance = distance * 0.8684;
            } else if (unit == DistanceUnit.METER) {
                distance = distance * 1.609344 * 1000;
            }
            if (!Double.isNaN(distance)) {
                return distance;
            }
            return 0;
        }

        /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
        /* :: This function converts decimal degrees to radians : */
        /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
        private static double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
        /* :: This function converts radians to decimal degrees : */
        /* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
        private static double rad2deg(double rad) {
            return (rad * 180 / Math.PI);
        }

        public static boolean isLatLongZero(double value) {
            double threshold = 0.5;
            return value >= -threshold && value <= threshold;
        }


    }

