/**
 * Created by Administrator on 2017/4/28.
 */
'user strict';

window.onload = function () {

    //鍒濆鍖�
    var video = $('#video1').videoCt({
        title: 'jq22 ',              //鏍囬
        volume: 0.2,                //闊抽噺
        barrage: true,              //寮瑰箷寮€鍏�
        comment: true,              //寮瑰箷
        reversal: true,             //闀滃儚缈昏浆
        playSpeed: true,            //鎾斁閫熷害
        update: true,               //涓嬭浇
        autoplay: false,            //鑷姩鎾斁
        clarity: {
            type: ['360P','480P'],            //娓呮櫚搴�
            src: ['http://jq22.qiniudn.com/jq22.mp4',
                  'http://jq22.qiniudn.com/jq22.mp4']      //閾炬帴鍦板潃
        },
        commentFile: 'comment.json'           //瀵煎叆寮瑰箷json鏁版嵁
    });

    //鎵╁睍
    video.title;                    //鏍囬
    video.status;                   //鐘舵€�
    video.currentTime;              //褰撳墠鏃堕暱
    video.duration;                 //鎬绘椂闀�
    video.volume;                   //闊抽噺
    video.clarityType;              //娓呮櫚搴�
    video.claritySrc;               //閾炬帴鍦板潃
    video.fullScreen;               //鍏ㄥ睆
    video.reversal;                 //闀滃儚缈昏浆
    video.playSpeed;                //鎾斁閫熷害
    video.cutover;                  //鍒囨崲涓嬩釜瑙嗛鏄惁鑷姩鎾斁
    video.commentTitle;             //寮瑰箷鏍囬
    video.commentId;                //寮瑰箷id
    video.commentClass;             //寮瑰箷绫诲瀷
    video.commentSwitch;            //寮瑰箷鏄惁鎵撳紑
    $('#video1').bind('ended', function() {
        console.log('寮瑰箷json鏁版嵁锛歕n'+ video.comment());              //鑾峰彇寮瑰箷json鏁版嵁
    });
}