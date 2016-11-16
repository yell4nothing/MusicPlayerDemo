package com.dd.musicplayerdemo.constants;

/**
 * Created by my on 2016/11/5.
 */

public class Uri {

    //所有电台
    public static final String SONG_DIANTAI_LIST="http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.radio.getCategoryList&format=json";
    //某一电台的列表
    public static final String SONG_LIST="http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.radio.getChannelSong&format=json&pn=0&rn=10&channelname=%s";
    //歌曲
    public static final String SONG="http://ting.baidu.com/data/music/links?songIds=%s";

    public static final String[] TABS = new String[]{"公共频道","音乐人"};
}
