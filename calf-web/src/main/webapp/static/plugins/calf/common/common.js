
var DATE_TIME_FMT_DEFAULT = 'yyyy-mm-dd hh:MM:ss';

/**
 * 时间格式化函数, 按照指定格式化字符串格式化传入时间
 *
 * @param {Date} time 需要格式化的时间
 * @param {String} fmStr 定义时间的格式
 *        yyyy: 代表四位数年份
 *          yy: 代表两位数年份
 *          mm: 代表月份(小于10时补0)
 *          dd: 代表日期(小于10时补0)
 *          hh: 代表小时(小于10时补0)
 *          hh: 代表小时(小于10时补0)
 *          MM: 代表分钟(小于10时补0)
 *          ss: 代表秒数(小于10时补0)
 *         SSS: 代表毫秒数
 *           w: 代表周几(数字)
 *           W: 代表周几(中文)
 *          ww: 代表周几(英文)
 * @returns {String} 返回格式化的时间
 */
function timeFormat(time, fmStr) {
    var weekCN = '一二三四五六日';
    var weekEN = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
    if (!fmStr) {
        fmStr = DATE_TIME_FMT_DEFAULT;
    }
    var year = time.getFullYear();
    var month = time.getMonth() + 1;
    var day = time.getDate();
    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();
    var milliSeconds = time.getMilliseconds();
    var week = time.getDay();

    month = month >= 10 ? month : ('0' + month);
    day = day >= 10 ? day : ('0' + day);
    hours = hours >= 10 ? hours : ('0' + hours);
    minutes = minutes >= 10 ? minutes : ('0' + minutes);
    seconds = seconds >= 10 ? seconds : ('0' + seconds);

    if (fmStr.indexOf('yyyy') !== -1) {
        fmStr = fmStr.replace('yyyy', year);
    } else {
        fmStr = fmStr.replace('yy', (year + '').slice(2));
    }
    fmStr = fmStr.replace('mm', month);
    fmStr = fmStr.replace('dd', day);
    fmStr = fmStr.replace('hh', hours);
    fmStr = fmStr.replace('MM', minutes);
    fmStr = fmStr.replace('ss', seconds);
    fmStr = fmStr.replace('SSS', milliSeconds);
    fmStr = fmStr.replace('W', weekCN[week - 1]);
    fmStr = fmStr.replace('ww', weekEN[week - 1]);
    fmStr = fmStr.replace('w', week);

    return fmStr;
}

function now(fmt) {
    if (typeof fmt === 'string') {

        return timeFormat(new Date(), fmt);
    }
    return new Date();
}

function openChildWindow(url,title){
    var iHeight = 400;
    var iWidth = 950;
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
    var op = window.open(url, title, 'height=' + iHeight + ',width=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
    //var op = window.open("https://www.baidu.com", "编辑字典", 'height=' + iHeight + ',width=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
    op.focus();
}

