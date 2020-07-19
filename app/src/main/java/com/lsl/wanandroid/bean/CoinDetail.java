package com.lsl.wanandroid.bean;

import java.util.List;

/**
 * Create by lsl on 2020/7/19
 */
public class CoinDetail {

    /**
     * curPage : 1
     * datas : [{"coinCount":39,"date":1595132038000,"desc":"2020-07-19 12:13:58 签到 , 积分：10 + 29","id":257718,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":38,"date":1595034583000,"desc":"2020-07-18 09:09:43 签到 , 积分：10 + 28","id":257225,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":37,"date":1594948889000,"desc":"2020-07-17 09:21:29 签到 , 积分：10 + 27","id":256544,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":36,"date":1594831012000,"desc":"2020-07-16 00:36:52 签到 , 积分：10 + 26","id":255557,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":35,"date":1594798660000,"desc":"2020-07-15 15:37:40 签到 , 积分：10 + 25","id":255271,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":34,"date":1594686868000,"desc":"2020-07-14 08:34:28 签到 , 积分：10 + 24","id":253875,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":33,"date":1594608431000,"desc":"2020-07-13 10:47:11 签到 , 积分：10 + 23","id":253261,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":32,"date":1594551362000,"desc":"2020-07-12 18:56:02 签到 , 积分：10 + 22","id":252748,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":31,"date":1594446312000,"desc":"2020-07-11 13:45:12 签到 , 积分：10 + 21","id":252261,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":30,"date":1594345130000,"desc":"2020-07-10 09:38:50 签到 , 积分：10 + 20","id":251233,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":29,"date":1594275106000,"desc":"2020-07-09 14:11:46 签到 , 积分：10 + 19","id":250670,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":28,"date":1594174264000,"desc":"2020-07-08 10:11:04 签到 , 积分：10 + 18","id":249740,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":27,"date":1594126526000,"desc":"2020-07-07 20:55:26 签到 , 积分：10 + 17","id":249420,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":26,"date":1593994298000,"desc":"2020-07-06 08:11:38 签到 , 积分：10 + 16","id":247921,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":25,"date":1593867301000,"desc":"2020-07-04 20:55:01 签到 , 积分：10 + 15","id":247481,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":24,"date":1593747125000,"desc":"2020-07-03 11:32:05 签到 , 积分：10 + 14","id":246756,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":23,"date":1593311158000,"desc":"2020-06-28 10:25:58 签到 , 积分：10 + 13","id":242629,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":22,"date":1593155596000,"desc":"2020-06-26 15:13:16 签到 , 积分：10 + 12","id":241806,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":21,"date":1592492375000,"desc":"2020-06-18 22:59:35 签到 , 积分：10 + 11","id":237343,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"},{"coinCount":20,"date":1592356043000,"desc":"2020-06-17 09:07:23 签到 , 积分：10 + 10","id":235958,"reason":"签到","type":1,"userId":65535,"userName":"13759862486"}]
     * offset : 0
     * over : false
     * pageCount : 2
     * size : 20
     * total : 30
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * coinCount : 39
         * date : 1595132038000
         * desc : 2020-07-19 12:13:58 签到 , 积分：10 + 29
         * id : 257718
         * reason : 签到
         * type : 1
         * userId : 65535
         * userName : 13759862486
         */

        private int coinCount;
        private long date;
        private String desc;
        private int id;
        private String reason;
        private int type;
        private int userId;
        private String userName;

        public int getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
