package com.girlkun.services.func;

import com.girlkun.database.GirlkunDB;
import com.girlkun.server.Manager;
import com.girlkun.utils.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class TopService implements Runnable {
    private static TopService i;
    public static TopService gI() {
        if (i == null) {
            i = new TopService();
        }
        return i;
    }
    public static String formatNumber(long number) {
        if (number >= 1_000_000_000) {
            return number / 1_000_000_000 + " Tỷ";
        } else if (number >= 1_000_000) {
            return number / 1_000_000 + " Triệu";
        } else if (number >= 1_000) {
            return number / 1_000 + "K";
        } else {
            return String.valueOf(number);
        }
    }
    public static String gethotong() {
        StringBuffer sb = new StringBuffer("");
        String SELECT_TOP_POWER = "SELECT name, diemhotong FROM player ORDER BY diemhotong DESC LIMIT 10;";
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = GirlkunDB.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_POWER);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while (rs.next()) {
                sb.append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("diemhotong")).append("\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String gettopnaubanhtrung() {
        StringBuffer sb = new StringBuffer("");
        String SELECT_TOP_POWER = "SELECT name, diemnaubanhtrung FROM player ORDER BY diemnaubanhtrung DESC LIMIT 10;";
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = GirlkunDB.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_POWER);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while (rs.next()) {
                sb.append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("diemnaubanhtrung")).append("\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getpointNroNamec() {
        StringBuffer sb = new StringBuffer("");

        String SELECT_TOP_POWER = "SELECT name, pointNroNamec FROM player ORDER BY pointNroNamec DESC LIMIT 10;";
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = GirlkunDB.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_POWER);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while (rs.next()) {
                sb.append(i).append(".").append(rs.getString("name")).append(": ").append(rs.getString("pointNroNamec")).append("\b");
                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String gettopsukienhe() {
        StringBuffer sb = new StringBuffer("");

        String SELECT_TOP_POWER = "SELECT name, diemsukienhe FROM player ORDER BY diemsukienhe DESC LIMIT 10;";
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = GirlkunDB.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_POWER);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while (rs.next()) {
                sb.append(i)
                        .append(".")
                        .append(rs.getString("name"))
                        .append(": ")
                        .append(rs.getString("diemsukienhe"))
                        .append(" Điểm.")
                        .append("\b");

                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getTopSM() {
        StringBuffer sb = new StringBuffer("ĐUA TOP SỨC MẠNH\n");

        String SELECT_TOP_SM = "SELECT name, \n"
                + "CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(data_point, ',', 2), ',', -1) AS UNSIGNED) AS sm\n"
                + "FROM player\n"
                + "ORDER BY sm DESC\n"
                + "LIMIT 10;";
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = GirlkunDB.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_SM);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while (rs.next()) {
                long smValue = rs.getLong("sm");
                sb.append(i)
                        .append(". ")
                        .append(rs.getString("name"))
                        .append(": ")
                        .append(formatNumber(smValue))
                        .append(" Sức Mạnh.")
                        .append("\n");

                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getTopNap() {
        StringBuffer sb = new StringBuffer("ĐUA TOP TỔNG NẠP\n");

        String SELECT_TOP_NAP = "SELECT p.name, a.tongnap\n"
                + "FROM player p\n"
                + "JOIN account a ON p.account_id = a.id\n"
                + "ORDER BY a.tongnap DESC\n"
                + "LIMIT 20;";
        PreparedStatement ps;
        ResultSet rs;
        try {
            Connection conn = GirlkunDB.getConnection();
            ps = conn.prepareStatement(SELECT_TOP_NAP);
            conn.setAutoCommit(false);

            rs = ps.executeQuery();
            byte i = 1;
            while (rs.next()) {
                long tongnapValue = rs.getLong("tongnap");
                sb.append(i)
                        .append(". ")
                        .append(rs.getString("name"))
                        .append(": ")
                        .append(formatNumber(tongnapValue))
                        .append("\n");

                i++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
    
    public static String getTopNapQua() {
    StringBuffer sb = new StringBuffer("ĐUA TOP TỔNG NẠP\n");

    String SELECT_TOP_NAP = "SELECT p.name\n"
            + "FROM player p\n"
            + "JOIN account a ON p.account_id = a.id\n"
            + "ORDER BY a.tongnap DESC\n"
            + "LIMIT 10;";
    PreparedStatement ps;
    ResultSet rs;
    try {
        Connection conn = GirlkunDB.getConnection();
        ps = conn.prepareStatement(SELECT_TOP_NAP);
        conn.setAutoCommit(false);

        rs = ps.executeQuery();
        byte i = 1;
        while (rs.next()) {
            String reward;
            if (i == 1) {
                reward = "Nhận 200k";
            } else if (i == 2) {
                reward = "Nhận 150k";
            } else if (i == 3) {
                reward = "Nhận 100k";
            } else if (i <= 10) {
                reward = "Nhận 50k";
            } else {
                reward = "Không có phần thưởng";
            }

            sb.append(i)
                    .append(". ")
                    .append(rs.getString("name"))
                    .append(": ")
                    .append(reward)
                    .append("\n");

            i++;
        }
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return sb.toString();
}


    @Override
    public void run() {
        while (true) {
            try {
                if (Manager.timeRealTop + (1000) < System.currentTimeMillis()) {
                    Manager.timeRealTop = System.currentTimeMillis();
                    try (Connection con = GirlkunDB.getConnection()) {
                        Manager.topSieuHang = Manager.realTopSieuHang(con);
                        Manager.topNV = Manager.realTop(Manager.queryTopNV, con);
                        Manager.topSM = Manager.realTop(Manager.queryTopSM, con);
                        Manager.topSK = Manager.realTop(Manager.queryTopSK, con);
                        Manager.topPVP = Manager.realTop(Manager.queryTopPVP, con);
                        Manager.topSD = Manager.realTop(Manager.queryTopSD, con);
                        Manager.topSieuHang = Manager.realTopSieuHang(con);
                    } catch (Exception ignored) {
                        Logger.error("Lỗi đọc top");
                    }
                }
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }

}
