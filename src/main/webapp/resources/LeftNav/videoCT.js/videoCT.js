/**
 * Created by LELK on 2017/5/3.
 */
'use strict';

$.fn.videoCt = function (options) {

    //璁剧疆榛樿鍊�
    var defaults = {
        title: '',
        volume: 0.2,
        barrage: false,
        comment: false,
        reversal: false,
        playSpeed: false,
        update: false,
        autoplay: false,
        clarity: {
            type: [],
            src: []
        },
        theme: 'blue',
        commentFile: 'comment.json'
    };
    var opts = $.extend({},defaults, options);
    var intOpts = {
        title: '',
        status: false,
        currentTime: '0.00001',
        duration: '',
        volume: '',
        clarityType: '',
        claritySrc: '',
        fullScreen: false,
        reversal: false,
        playSpeed: 1,
        cutover: true,
        commentTitle: '',
        commentId: '',
        commentClass: '',
        comment: {},
        commentSwitch: true,
    };

    // 鎺т欢缁撴瀯
    var $videoCt = $(this);
    var $videoCtBox = $('<div></div>').addClass('video-player').addClass(opts.theme);
    var $videoCtControls = $('<div class="video-controls">\
                                <!--杩涘害鏉�-->\
                                <div class="video-seek">\
                                    <div class="seek-buffer"></div>\
                                    <div class="seek-con"></div>\
                                    <div class="seek-slider"></div>\
                                    <div class="viewBox">\
                                        <video></video>\
                                        <span>00:00</span>\
                                        <i></i>\
                                    </div>\
                                </div>\
                                <!--鎾斁/鏆傚仠-->\
                                <a class="video-play" title="Play/Pause"></a>\
                                <!--璁℃椂鍣�-->\
                                <div class="video-timer">\
                                    <span class="realTime">00:00</span>\
                                    /&nbsp;<span>00:00</span>\
                                </div>\
                                <!--闊抽噺鎺у埗-->\
                                <div class="video-audio">\
                                    <a class="audio-button" title="Mute/Unmute"></a>\
                                    <div class="audio-box">\
                                        <div class="audio-con"></div>\
                                        <div class="audio-slider"></div>\
                                    </div>\
                                </div>\
                                <!--鍏ㄥ睆-->\
                                <div class="video-screen"></div>\
                                <div class="video-status"></div>\
                            </div>');
    $videoCt.wrap($videoCtBox);
    $videoCt.after($videoCtControls);
    $videoCt.after($('<div class="video-prompt">瑙嗛鍔犺浇涓€傘€傘€�</div>'));
    videoDom(opts);

    var getVideoTimer;
    var comStayTimer = [];
    var comTimer = 0;
    var comStroeStatus = 0;
    var $video_container = $videoCt.parent('.video-player');
    var $videoCt_title = $('.video-title', $video_container);
    var $videoCt_play = $('.video-play', $video_container);
    var $videoCt_seek = $('.video-seek', $video_container);
    var $videoCt_timer = $('.video-timer', $video_container);
    var $videoCt_audio = $('.video-audio', $video_container);
    var $videoCt_clarity = $('.video-clarity', $video_container);
    var $videoCt_screen = $('.video-screen', $video_container);

    $videoCt.attr('src',opts.clarity.src[0]);
    $('.video-seek video').attr('src', opts.clarity.src[0]);
    intOpts.clarityType = opts.clarity.type[0];
    intOpts.claritySrc = opts.clarity.src[0];
    $videoCt_clarity.find('span').eq(0).addClass('disabled');

    var getVideoInf = function (){
        var setTimer;
        var key = true;
        if ($videoCt[0].readyState != 4 && $('.video-seek video')[0].readyState != 4) {
            $('.video-prompt').fadeOut();
            getVideoTimer = setTimeout(getVideoInf, 100);
        } else {
            $('.video-prompt').fadeOut();

            //瑙嗛鎾斁鐘舵€�
            if (intOpts.status) {
                $videoCt[0].play();
            }

            //鏍囬
            if (!intOpts.title) {
                intOpts.title = opts.title;
            }
            $videoCt_title.text(intOpts.title);

            //杞藉叆寮瑰箷
            if(intOpts.cutover && opts.comment){
                $.getJSON(opts.commentFile,function (data,status) {
                    if(status == 'success'){
                        $('.screenShootBox').empty();
                        intOpts.commentTitle = data.title;
                        intOpts.commentId = data.id;
                        intOpts.commentClass = data.class;
                        $.each(data.data,function (index,obj) {
                            $('.screenShootBox').append($('<p name="'+ obj.time +'" class="screenShoot" data-status="0" data-action="'+ obj.action +'" data-speed="'+ obj.speed +'" style="color: '+ obj.color +';top: '+ obj.top +'%;font-size: '+ obj.fontSize +'px">'+ obj.word +'</p>'));
                        })
                    }
                });
            }

            //杩涘害鏉�/鏆傚仠鎾斁
            if (!intOpts.currentTime) {
                intOpts.currentTime = $videoCt[0].currentTime;
            }
            $videoCt[0].currentTime = intOpts.currentTime;
            if (!intOpts.duration) {
                intOpts.duration = $videoCt[0].duration;
            }
            move($('.seek-slider'), $videoCt_seek, $('.seek-con'), 8, videoJump, $('.realTime'), true);
            $('.seek-slider').css('left', intOpts.currentTime / intOpts.duration * $('.video-seek').width() - 8);
            $('.seek-con').css('width', intOpts.currentTime / intOpts.duration * $('.video-seek').width());
            $('.video-seek').hover(function () {
                key = true;
                $(document).mousemove(function(e){
                    clearTimeout(setTimer);
                    if(key){hoverPlay(e);}
                });
            },function () {
                key = false;
                clearTimeout(setTimer);
                $('.video-seek .viewBox').fadeOut();
            });

            //鏃堕棿
            $videoCt_timer.find('span').eq(0).text(gTimeFormat(intOpts.currentTime));
            $videoCt_timer.find('span').eq(1).text(gTimeFormat(intOpts.duration));

            //澹伴煶
            if (!intOpts.volume) {
                intOpts.volume = opts.volume;
            }
            $videoCt[0].volume = intOpts.volume;
            $videoCt_audio.find('.audio-button').removeClass('audio-mute');
            move($('.audio-slider'), $('.audio-box'), $('.audio-con'), 6, audioJump);
            $('.audio-slider').css('left', intOpts.volume * $('.audio-box').width() - 6);
            $('.audio-con').css('width', intOpts.volume * $('.audio-box').width());
            $videoCt_audio.find('.audio-button').off('click').on('click', function () {
                $(this).toggleClass('audio-mute');
                if ($videoCt[0].volume == 0) {
                    $videoCt[0].volume = intOpts.volume;
                } else {
                    $videoCt[0].volume = 0;
                }
            })

            //寮瑰箷
            $('.video-comment input[type=text]').off('focus').on('focus', function () {
                $('.video-comment').addClass('commentActive');
            });
            $('.video-comment p').off('click').on('click', function () {
                $('.video-comment .setBox').fadeToggle();
            });
            $('.video-comment .close').off('click').on('click', function () {
                $('.video-comment .setBox').fadeOut();
            });
            $('.video-comment input[type=color]').on('input', function () {
                var wordColor = $(this).val();
                $('.video-comment input[type=text]').css('color', wordColor);
            });

            //鍙戦€佸脊骞�
            $('.video-comment span').off('click').on('click', function () {
                var $that = $('.video-comment');
                $that.find('.setBox').fadeOut();
                $that.removeClass('commentActive');
                var name = parseInt($videoCt[0].currentTime) + 1,
                    action = $that.find('input[name=action]:checked').val(),
                    speed = $that.find('input[name=speed]').val(),
                    color = $that.find('input[type=color]').val(),
                    top = $that.find('input[name=top]').val(),
                    fontSize = $that.find('input[name=fontSize]').val(),
                    word = $that.find('input[type=text]').val();
                $('.screenShootBox').append($('<p name='+ name +' class="screenShoot" data-status="0" data-action='+ action +' data-speed='+ speed +' style="color: '+ color +';top: '+ top +'%;font-size: '+ fontSize +'px">'+ word +'</p>'));
                $('.video-comment input[type=text]').val("");
            });

            //寮瑰箷寮€鍏�
            $('.video-barrage').off('click').on('click',function () {
                if(intOpts.commentSwitch){
                    intOpts.commentSwitch = false;
                    $('.screenShootBox').fadeOut();
                    $(this).addClass('barrageClose');
                    $('.video-comment').fadeOut();
                }else{
                    intOpts.commentSwitch = true;
                    $('.screenShootBox').fadeIn();
                    $(this).removeClass('barrageClose');
                    $('.video-comment').fadeIn();
                }
            });

            //鍙嶈浆
            $('.video-reversal').off('click').on('click',function () {
                $videoCt.toggleClass('videoStatus');
            });

            //鎾斁閫熷害
            $('.video-playSpeed').off('click').on('click',function () {
                comStop();
                var value = parseFloat($(this).text().split('x').join(""));
                if(value >= 3){
                    value = 0;
                }
                value = value + 0.5;
                $videoCt[0].playbackRate = value;
                intOpts.playSpeed = value;
                $(this).text(value + 'x');
            });

            //娓呮櫚搴�
            $videoCt_clarity.find('span').off("click").on('click', function () {
                var ind = $(this).index();
                $videoCt_clarity.find('span').removeClass('disabled');
                $(this).addClass('disabled');
                $videoCt_clarity.find('p').text($(this).text());
                intOpts.clarityType = opts.clarity.type[ind];
                intOpts.claritySrc = opts.clarity.src[ind];
                $videoCt.attr('src', intOpts.claritySrc);
                $('.video-seek video').attr('src', intOpts.claritySrc);
                opts.autoplay = true;
                intOpts.cutover = false;
                $videoCt[0].currentTime = intOpts.currentTime;
                getVideoInf();
            })

            //涓嬭浇
            $('.video-update').attr('download',intOpts.title);
            $('.video-update').attr('href',intOpts.claritySrc);

            //鍏ㄥ睆
            $videoCt_screen.off('click').on('click', function () {
                if (intOpts.fullScreen == true) {
                    exitFullscreen();
                    $('body').removeClass('fullScreen');
                    intOpts.fullScreen = false;
                } else {
                    requestFullScreen();
                    $('body').addClass('fullScreen');
                    intOpts.fullScreen = true;
                }
                $('.seek-slider').css('left', intOpts.currentTime / intOpts.duration * $('.video-seek').width() - 8);
                $('.seek-con').css('width', intOpts.currentTime / intOpts.duration * $('.video-seek').width());
            })

        }
    }
    getVideoInf();

    //slider
    function move(slider, box, con, radius, fn, vBox, vFollow) {
        var vBox = vBox || false;
        var vFollow = vFollow || false;
        var moveStatus = false;
        var sL = slider.offset().left;
        slider.click(function(){
        }).mousedown(function(e){
            moveStatus = true;
            sL = e.pageX - parseInt(slider.css('left'));
        })
        $(document).mousemove(function(e){
            if(moveStatus){
                var diffL = e.pageX - sL;
                show(diffL);
            }
        }).mouseup(function(){
            moveStatus = false;
        });
        box.off('click').on('click',function (e) {
            var diffL = e.pageX - box.offset().left - radius;
            show(diffL);
        });
        function show(x) {
            if( x >= -1*radius && x <= parseInt(box.width() - radius)){
                slider.css('left',x);
                con.css('width',x+radius);
                if(vBox){
                    var fnIndex = $videoCt[0].duration * (x+radius)/box.width();
                    intOpts.currentTime = fnIndex;
                    comClear();
                    vBox.text(gTimeFormat(fnIndex));
                }else{
                    var fnIndex = (x+radius)/box.width();
                }
                fn(fnIndex);
            }
        }
        if(vFollow){
            //鏆傚仠鎾斁
            var seTimer;
            var vPlay = function() {
                if(!intOpts.status) {
                    $videoCt[0].play();
                    intOpts.status = true;
                    seTimer = setInterval(function () {
                        var vprpo = $videoCt[0].currentTime/$videoCt[0].duration;
                        if(intOpts.cutover){
                            var vBTime = $videoCt[0].buffered.end(0)/$videoCt[0].duration;
                        }
                        if( vprpo < 1){
                            var tl = vprpo * (box.width() - radius);
                            var bL = vBTime * box.width();
                            intOpts.currentTime = $videoCt[0].currentTime;
                            vBox.text(gTimeFormat($videoCt[0].currentTime));
                            slider.css('left',tl);
                            con.css('width',tl+radius);
                            $('.seek-buffer').css('width',bL);
                            intStatus();
                            if($videoCt[0].duration - $videoCt[0].currentTime > 6){
                                comRun(parseInt($videoCt[0].currentTime));
                            }else{
                                comStore();
                            }
                        }else if(vprpo == 1){
                            clearInterval(seTimer);
                            intOpts.status = false;
                            return true;
                        }else{
                            clearInterval(seTimer);
                            intOpts.status = true;
                            return true;
                        }
                    },10);
                } else {
                    $videoCt[0].pause();
                    intOpts.status = false;
                    comStop();
                    clearInterval(seTimer);
                }
            };

            if((intOpts.status || intOpts.cutover) && opts.autoplay){
                intOpts.status = false;
                vPlay();
            }

            $videoCt_play.removeClass('video-pause');
            $videoCt_play.off('click').on('click',vPlay);
            $videoCt.off('click').on('click',vPlay);

            $videoCt.bind('play', function() {
                $videoCt_play.addClass('video-pause');
            });

            $videoCt.bind('pause', function() {
                $videoCt_play.removeClass('video-pause');
            });

            $videoCt.bind('ended', function() {
                $videoCt_play.removeClass('video-pause');
            });
        }
    }

    //鏃堕棿鏍煎紡鍖�
    var gTimeFormat = function (seconds) {
        var m = Math.floor(seconds / 60) < 10 ? "0" + Math.floor(seconds / 60) : Math.floor(seconds / 60);
        var s = Math.floor(seconds - (m * 60)) < 10 ? "0" + Math.floor(seconds - (m * 60)) : Math.floor(seconds - (m * 60));
        return m + ":" + s;
    };

    //杩涘害鏉℃樉绀�
    function hoverPlay(e) {
        var view = e.pageX - $('.video-seek').offset().left + 1;
        var time = $videoCt[0].duration * view / $('.video-seek').width();
        if(view <= 30){
            $('.video-seek .viewBox').css('left',-20);
            $('.video-seek .viewBox i').css('left',view+15);
        }else if(view >= ($('.video-seek').width()-30)){
            $('.video-seek .viewBox').css('left',$('.video-seek').width()-80);
            $('.video-seek .viewBox i').css('left',view-$('.video-seek').width()+75);
        }else{
            $('.video-seek .viewBox').css('left',view - 50);
            $('.video-seek .viewBox i').css('left',44);
        }
        $('.video-seek video')[0].currentTime = time;
        $('.video-seek span').text(gTimeFormat(time));
        $('.video-seek .viewBox').fadeIn();
    }

    //瑙嗛璺宠浆
    function videoJump( timer ) {
        $videoCt[0].currentTime = timer;
    }

    //瑙嗛缃戠粶鍗＄
    function intStatus(){
        if($videoCt[0].readyState == 4){
            $('.video-status').fadeOut();
        }else{
            $('.video-status').fadeIn();
            comStop();
        }
    }

    //闊抽噺璋冭妭
    function audioJump( volume ) {
        opts.volume = volume;
        $videoCt[0].volume = volume;
    }

    //寮瑰箷杩愯
    function comRun(time) {
        if(comTimer != time){
            $(".screenShoot[name="+ time +"][data-status='0']").each(function () {
                var that = $(this);
                var i = that.index();
                var t = that.data('speed')*1000/intOpts.playSpeed;
                if(that.data('action') == 'stay'){
                    that.fadeIn();
                    comStayTimer[i] = setTimeout(function () {
                        that.attr('data-status',3);
                    },t);
                }else{
                    that.css('display','block');
                    that.animate({'left': -40 + '%'},t,'linear');
                    comStayTimer[i] = setTimeout(function () {
                        that.attr('data-status',3);
                    },t);
                }
                that.attr('data-status',1);
            });
            $(".screenShoot[data-status='2']").each(function () {
                var that = $(this);
                var i = that.index();
                var difTime = 1000*(that.data('speed') - (time - that.attr('name')))/intOpts.playSpeed;
                if(that.data('action') == 'stay'){
                    comStayTimer[i] = setTimeout(function () {
                        that.attr('data-status',3);
                    },difTime);
                }else{
                    that.animate({'left': -40 + '%'},difTime,'linear');
                    comStayTimer[i] = setTimeout(function () {
                        that.attr('data-status',3);
                    },difTime);
                }
                that.attr('data-status',1);
            });
            $(".screenShoot[data-status='3']").each(function () {
                if($(this).data('action') == 'marquee'){
                    $(this).css('left', 110 + '%');
                }
                $(this).css('display','none');
                $(this).attr('data-status',0);
            });
            comTimer = time;
        }
    }

    //寮瑰箷鍋滄
    function comStop() {
        $(".screenShoot[data-status='1']").each(function () {
            var that = $(this);
            var i = that.index();
            if(that.data('action') == 'marquee'){
                that.stop();
            }
            clearTimeout(comStayTimer[i]);
            that.attr('data-status',2);
        });
    }

    //寮瑰箷娓呴櫎
    function comClear() {
        $(".screenShoot[data-status='1']").each(function () {
            var that = $(this);
            var i = that.index();
            clearTimeout(comStayTimer[i]);
            if(that.data('action') == 'marquee'){
                $(this).stop();
                $(this).css('left', 110 + '%');
            }
            $(this).fadeOut();
            that.attr('data-status',0);
        });
    }

    //寮瑰箷瀛樺偍
    function comStore() {
        if(comStroeStatus == 0){
            var dataArr = [];
            var date = new Date();
            var time= date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
            $('.screenShoot').each(function () {
                var data = {
                    "word": $(this).text(),
                    "color": $(this).css('color'),
                    "speed": $(this).data('speed'),
                    "top": $(this).css('top').split('%').join(''),
                    "fontSize": $(this).css('fontSize').split('px').join(''),
                    "action": $(this).data('action'),
                    "time": $(this).attr('name')
                };
                dataArr.push(data);
            })
            var obj = {
                "title": intOpts.commentTitle,
                "id": intOpts.commentId,
                "time": time,
                "number": $('.screenShoot').length,
                "class": intOpts.commentClass,
                "data": dataArr
            }
            intOpts.comment = JSON.stringify(obj, null, '  ');
            comStroeStatus = 1;
        }
    }

    //杩涘叆鍏ㄥ睆
    function requestFullScreen() {
        var de = document.documentElement;
        if (de.requestFullscreen) {
            de.requestFullscreen();
        } else if (de.mozRequestFullScreen) {
            de.mozRequestFullScreen();
        } else if (de.webkitRequestFullScreen) {
            de.webkitRequestFullScreen();
        }
    }

    //閫€鍑哄叏灞�
    function exitFullscreen() {
        var de = document;
        if (de.exitFullscreen) {
            de.exitFullscreen();
        } else if (de.mozCancelFullScreen) {
            de.mozCancelFullScreen();
        } else if (de.webkitCancelFullScreen) {
            de.webkitCancelFullScreen();
        }
    }

    //鎵╁睍鍔熻兘
    function videoDom( opts ) {
        var title = $('<!--鏍囬--><div class="video-title"></div>');
        var barrage = $('<!--寮瑰箷--><div class="video-barrage"></div>');
        var comment = $('<!--璇勮-->\
                       <form class="video-comment">\
                            <input type="text" placeHolder="杈撳叆璇勮" />\
                            <div>\
                                <p></p>\
                                <span>纭畾</span>\
                            </div>\
                            <div class="setBox">\
                                <div class="group">\
                                    <label>棰滆壊锛�</label><input type="color" name="color" value="#ffffff">\
                                    <label>澶у皬锛�</label><input type="number" name="fontSize" value="16" step="1" min="12" max="30"><span>鍍忕礌</span>\
                                </div>\
                                <div class="group">\
                                    <label>浣嶇疆锛�</label><input type="number" name="top" value="10" step="1" min="0" max="80"><span>%</span>\
                                    <label>鏃堕棿锛�</label><input type="number" name="speed" value="10" step="1" min="5" max="30"><span>绉�</span>\
                                </div>\
                                <div class="group">\
                                    <label>鍔ㄤ綔锛�</label>\
                                    <input type="radio" name="action" value="marquee" checked><span>婊氬姩</span>\
                                    <input type="radio" name="action" value="stay"><span>鍋滅暀</span>\
                                </div>\
                                <i class="close">脳</i>\
                            </div>\
                       </form>');
        var reversal = $('<!--缈昏浆--><div class="video-reversal"></div>');
        var playSpeed = $('<!--鎾斁閫熷害--><div class="video-playSpeed">1x</div>');
        var update = $('<!--涓嬭浇--><a class="video-update"></a>');
        if( opts.title ){$videoCt.after(title)}
        if( opts.barrage ){
            barrage.appendTo($(".video-controls"));
            $(".video-controls").after($('<div class="screenShootBox"></div>'));
        }
        if( opts.reversal ){reversal.appendTo($(".video-controls"))}
        if( opts.playSpeed ){playSpeed.appendTo($(".video-controls"))}
        if( opts.update ){update.appendTo($(".video-controls"))}
        if( opts.clarity.type && opts.clarity.src ){
            var str = '';
            for(var i = 0;i < opts.clarity.type.length;i++){
                str = str + '<span>' + opts.clarity.type[i] + '</span>';
            }
            var clarity = '<!--娓呮櫚搴�--><div class="video-clarity"><p>'+ opts.clarity.type[0] +'</p><nav>'+ str +'</nav></div>';
            $(clarity).appendTo($(".video-controls"));
        }
        if( opts.comment ){comment.appendTo($(".video-controls"))}
    }

    return {
        title: '',
        status: false,
        currentTime: '0.00001',
        duration: '',
        volume: '',
        clarityType: '',
        claritySrc: '',
        fullScreen: false,
        reversal: false,
        playSpeed: 1,
        cutover: true,
        commentTitle: '',
        commentId: '',
        commentClass: '',
        commentSwitch: true,
        comment: function () {
            return intOpts.comment;
        }
    }
}