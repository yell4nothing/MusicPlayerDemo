package com.dd.musicplayerdemo.beans;

import java.util.List;

/**
 * Created by my on 2016/11/5.
 */

public class SongList {

    /**
     * error_code : 22000
     * result : {"channel":"漫步春天","channelid":null,"ch_name":"public_tuijian_spring","artistid":null,"avatar":null,"count":null,"songlist":[{"songid":"8182419","title":"This Love","artist":"Maroon 5","thumb":"http://qukufile2.qianqian.com/data2/pic/117407909/117407909.jpg","method":0,"flow":0,"artist_id":"807","all_artist_id":"807","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":"8785643","title":"Sunny Came Home","artist":"Shawn Colvin","thumb":"http://qukufile2.qianqian.com/data2/pic/117833845/117833845.jpg","method":0,"flow":0,"artist_id":"45363","all_artist_id":"45363","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":"1000922","title":"春风","artist":"王筝","thumb":"http://qukufile2.qianqian.com/data2/pic/23e4bbdfd0c1a872dce9b9cf66459d94/262360517/262360517.jpg","method":0,"flow":0,"artist_id":"383","all_artist_id":"383","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":"2488586","title":"No Matter","artist":"范玮琪","thumb":"http://qukufile2.qianqian.com/data2/pic/115440321/115440321.jpg","method":0,"flow":0,"artist_id":"168","all_artist_id":"168","resource_type":"0","havehigh":2,"charge":0,"all_rate":"128,320"},{"songid":"7278793","title":"狂想的旅程","artist":"韩雪","thumb":"http://qukufile2.qianqian.com/data2/pic/117828254/117828254.jpg","method":0,"flow":0,"artist_id":"56","all_artist_id":"56","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,198,256,320,flac"},{"songid":"5722450","title":"Everything","artist":"梁心颐","thumb":"http://qukufile2.qianqian.com/data2/pic/27ee7dc957c5491d03db8afea44c09d9/262015407/262015407.jpg","method":0,"flow":0,"artist_id":"4722","all_artist_id":"4722","resource_type":"0","havehigh":0,"charge":0,"all_rate":"128"},{"songid":"207870","title":"甜甜的","artist":"周杰伦","thumb":"http://qukufile2.qianqian.com/data2/pic/04dd2e8d9ba71f0efe450a7d31209a3f/88389092/88389092.jpg","method":0,"flow":0,"artist_id":"29","all_artist_id":"29","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,224,256,320,flac"},{"songid":"442226","title":"Love Like You Loved","artist":"Jonny Diaz","thumb":"http://qukufile2.qianqian.com/data2/pic/117715890/117715890.jpg","method":0,"flow":0,"artist_id":"38246","all_artist_id":"38246","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320"},{"songid":"2058117","title":"每一天都不同","artist":"郭静","thumb":"http://qukufile2.qianqian.com/data2/pic/7b699beb92f605a1281542e1ca269f32/115458092/115458092.jpg","method":0,"flow":0,"artist_id":"844","all_artist_id":"844","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":"7918038","title":"Luka","artist":"Suzanne Vega","thumb":"http://qukufile2.qianqian.com/data2/pic/117401972/117401972.jpg","method":0,"flow":0,"artist_id":"46613","all_artist_id":"46613","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":null,"title":null,"artist":null,"thumb":"","method":0,"flow":0,"artist_id":null,"all_artist_id":null,"resource_type":null,"havehigh":0,"charge":0,"all_rate":""}]}
     */

    private int error_code;
    /**
     * channel : 漫步春天
     * channelid : null
     * ch_name : public_tuijian_spring
     * artistid : null
     * avatar : null
     * count : null
     * songlist : [{"songid":"8182419","title":"This Love","artist":"Maroon 5","thumb":"http://qukufile2.qianqian.com/data2/pic/117407909/117407909.jpg","method":0,"flow":0,"artist_id":"807","all_artist_id":"807","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":"8785643","title":"Sunny Came Home","artist":"Shawn Colvin","thumb":"http://qukufile2.qianqian.com/data2/pic/117833845/117833845.jpg","method":0,"flow":0,"artist_id":"45363","all_artist_id":"45363","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":"1000922","title":"春风","artist":"王筝","thumb":"http://qukufile2.qianqian.com/data2/pic/23e4bbdfd0c1a872dce9b9cf66459d94/262360517/262360517.jpg","method":0,"flow":0,"artist_id":"383","all_artist_id":"383","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":"2488586","title":"No Matter","artist":"范玮琪","thumb":"http://qukufile2.qianqian.com/data2/pic/115440321/115440321.jpg","method":0,"flow":0,"artist_id":"168","all_artist_id":"168","resource_type":"0","havehigh":2,"charge":0,"all_rate":"128,320"},{"songid":"7278793","title":"狂想的旅程","artist":"韩雪","thumb":"http://qukufile2.qianqian.com/data2/pic/117828254/117828254.jpg","method":0,"flow":0,"artist_id":"56","all_artist_id":"56","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,198,256,320,flac"},{"songid":"5722450","title":"Everything","artist":"梁心颐","thumb":"http://qukufile2.qianqian.com/data2/pic/27ee7dc957c5491d03db8afea44c09d9/262015407/262015407.jpg","method":0,"flow":0,"artist_id":"4722","all_artist_id":"4722","resource_type":"0","havehigh":0,"charge":0,"all_rate":"128"},{"songid":"207870","title":"甜甜的","artist":"周杰伦","thumb":"http://qukufile2.qianqian.com/data2/pic/04dd2e8d9ba71f0efe450a7d31209a3f/88389092/88389092.jpg","method":0,"flow":0,"artist_id":"29","all_artist_id":"29","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,224,256,320,flac"},{"songid":"442226","title":"Love Like You Loved","artist":"Jonny Diaz","thumb":"http://qukufile2.qianqian.com/data2/pic/117715890/117715890.jpg","method":0,"flow":0,"artist_id":"38246","all_artist_id":"38246","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320"},{"songid":"2058117","title":"每一天都不同","artist":"郭静","thumb":"http://qukufile2.qianqian.com/data2/pic/7b699beb92f605a1281542e1ca269f32/115458092/115458092.jpg","method":0,"flow":0,"artist_id":"844","all_artist_id":"844","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":"7918038","title":"Luka","artist":"Suzanne Vega","thumb":"http://qukufile2.qianqian.com/data2/pic/117401972/117401972.jpg","method":0,"flow":0,"artist_id":"46613","all_artist_id":"46613","resource_type":"0","havehigh":2,"charge":0,"all_rate":"24,64,128,192,256,320,flac"},{"songid":null,"title":null,"artist":null,"thumb":"","method":0,"flow":0,"artist_id":null,"all_artist_id":null,"resource_type":null,"havehigh":0,"charge":0,"all_rate":""}]
     */

    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String channel;
        private Object channelid;
        private String ch_name;
        private Object artistid;
        private Object avatar;
        private Object count;
        /**
         * songid : 8182419
         * title : This Love
         * artist : Maroon 5
         * thumb : http://qukufile2.qianqian.com/data2/pic/117407909/117407909.jpg
         * method : 0
         * flow : 0
         * artist_id : 807
         * all_artist_id : 807
         * resource_type : 0
         * havehigh : 2
         * charge : 0
         * all_rate : 24,64,128,192,256,320,flac
         */

        private List<SonglistBean> songlist;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public Object getChannelid() {
            return channelid;
        }

        public void setChannelid(Object channelid) {
            this.channelid = channelid;
        }

        public String getCh_name() {
            return ch_name;
        }

        public void setCh_name(String ch_name) {
            this.ch_name = ch_name;
        }

        public Object getArtistid() {
            return artistid;
        }

        public void setArtistid(Object artistid) {
            this.artistid = artistid;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
            this.count = count;
        }

        public List<SonglistBean> getSonglist() {
            return songlist;
        }

        public void setSonglist(List<SonglistBean> songlist) {
            this.songlist = songlist;
        }

        public static class SonglistBean {
            private String songid;
            private String title;
            private String artist;
            private String thumb;
            private int method;
            private int flow;
            private String artist_id;
            private String all_artist_id;
            private String resource_type;
            private int havehigh;
            private int charge;
            private String all_rate;

            public String getSongid() {
                return songid;
            }

            public void setSongid(String songid) {
                this.songid = songid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getArtist() {
                return artist;
            }

            public void setArtist(String artist) {
                this.artist = artist;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public int getMethod() {
                return method;
            }

            public void setMethod(int method) {
                this.method = method;
            }

            public int getFlow() {
                return flow;
            }

            public void setFlow(int flow) {
                this.flow = flow;
            }

            public String getArtist_id() {
                return artist_id;
            }

            public void setArtist_id(String artist_id) {
                this.artist_id = artist_id;
            }

            public String getAll_artist_id() {
                return all_artist_id;
            }

            public void setAll_artist_id(String all_artist_id) {
                this.all_artist_id = all_artist_id;
            }

            public String getResource_type() {
                return resource_type;
            }

            public void setResource_type(String resource_type) {
                this.resource_type = resource_type;
            }

            public int getHavehigh() {
                return havehigh;
            }

            public void setHavehigh(int havehigh) {
                this.havehigh = havehigh;
            }

            public int getCharge() {
                return charge;
            }

            public void setCharge(int charge) {
                this.charge = charge;
            }

            public String getAll_rate() {
                return all_rate;
            }

            public void setAll_rate(String all_rate) {
                this.all_rate = all_rate;
            }
        }
    }
}
