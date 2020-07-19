package com.lsl.wanandroid.bean;

import java.util.List;

/**
 * Create by lsl on 2020/7/19
 */
public class Coin {

    /**
     * curPage : 1
     * datas : [{"coinCount":18504,"level":186,"rank":"1","userId":20382,"username":"g**eii"},{"coinCount":15943,"level":160,"rank":"2","userId":3559,"username":"A**ilEyon"},{"coinCount":15447,"level":155,"rank":"3","userId":29303,"username":"深**士"},{"coinCount":13206,"level":133,"rank":"4","userId":2,"username":"x**oyang"},{"coinCount":10557,"level":106,"rank":"5","userId":27535,"username":"1**08491840"},{"coinCount":10129,"level":102,"rank":"6","userId":28694,"username":"c**ng0218"},{"coinCount":9955,"level":100,"rank":"7","userId":12467,"username":"c**yie"},{"coinCount":9952,"level":100,"rank":"8","userId":3753,"username":"S**phenCurry"},{"coinCount":9880,"level":99,"rank":"9","userId":29185,"username":"轻**宇"},{"coinCount":9753,"level":98,"rank":"10","userId":9621,"username":"S**24n"},{"coinCount":9621,"level":97,"rank":"11","userId":1534,"username":"j**gbin"},{"coinCount":9591,"level":96,"rank":"12","userId":28607,"username":"S**Brother"},{"coinCount":9566,"level":96,"rank":"13","userId":14829,"username":"l**changwen"},{"coinCount":9510,"level":96,"rank":"14","userId":7891,"username":"h**zkp"},{"coinCount":9477,"level":95,"rank":"15","userId":12351,"username":"w**igeny"},{"coinCount":9413,"level":95,"rank":"16","userId":27,"username":"y**ochoo"},{"coinCount":9405,"level":95,"rank":"17","userId":7710,"username":"i**Cola7"},{"coinCount":9371,"level":94,"rank":"18","userId":26707,"username":"p**xc.com"},{"coinCount":9326,"level":94,"rank":"19","userId":12331,"username":"R**kieJay"},{"coinCount":9304,"level":94,"rank":"20","userId":833,"username":"w**lwaywang6"},{"coinCount":9179,"level":92,"rank":"21","userId":7809,"username":"1**23822235"},{"coinCount":9176,"level":92,"rank":"22","userId":29076,"username":"f**ham"},{"coinCount":9052,"level":91,"rank":"23","userId":2068,"username":"i**Cola"},{"coinCount":8945,"level":90,"rank":"24","userId":4886,"username":"z**iyun"},{"coinCount":8871,"level":89,"rank":"25","userId":7590,"username":"陈**啦啦啦"},{"coinCount":8720,"level":88,"rank":"26","userId":2160,"username":"R**iner"},{"coinCount":8720,"level":88,"rank":"27","userId":25793,"username":"F**_2014"},{"coinCount":8652,"level":87,"rank":"28","userId":25128,"username":"f**wandroid"},{"coinCount":8610,"level":87,"rank":"29","userId":25419,"username":"蔡**打篮球"},{"coinCount":8559,"level":86,"rank":"30","userId":2786,"username":"8**408834@qq.com"}]
     * offset : 0
     * over : false
     * pageCount : 1504
     * size : 30
     * total : 45100
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
         * coinCount : 18504
         * level : 186
         * rank : 1
         * userId : 20382
         * username : g**eii
         */

        private int coinCount;
        private int level;
        private String rank;
        private int userId;
        private String username;

        public int getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
