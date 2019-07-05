package com.flb.sample.fzw.model;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/6/5
 */
public class ChartBean {

    private List<ThirtyDayBean> thirtyDay;

    public List<ThirtyDayBean> getThirtyDay() {
        return thirtyDay;
    }

    public void setThirtyDay(List<ThirtyDayBean> thirtyDay) {
        this.thirtyDay = thirtyDay;
    }

    public static class ThirtyDayBean {
        /**
         * date : 2019-05-26 00:00:00
         * playCnt : 1170118
         * totalIncome : 479313.67
         * readCnt : 14206414
         * newIncome : 5127.72
         * fansCnt : 8020
         */

        private String date;
        private int playCnt;
        private double totalIncome;
        private int readCnt;
        private double newIncome;
        private int fansCnt;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getPlayCnt() {
            return playCnt;
        }

        public void setPlayCnt(int playCnt) {
            this.playCnt = playCnt;
        }

        public double getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(double totalIncome) {
            this.totalIncome = totalIncome;
        }

        public int getReadCnt() {
            return readCnt;
        }

        public void setReadCnt(int readCnt) {
            this.readCnt = readCnt;
        }

        public double getNewIncome() {
            return newIncome;
        }

        public void setNewIncome(double newIncome) {
            this.newIncome = newIncome;
        }

        public int getFansCnt() {
            return fansCnt;
        }

        public void setFansCnt(int fansCnt) {
            this.fansCnt = fansCnt;
        }
    }
}
