var ws;
//重连时间(毫秒)
var reconnectTime = 30000;
//定义定时拉去告警任务对象
var checkNewAlarmTask;

function fun_close() {
    ws.close()
}

function fun_sendto() {
    var a = $.trim($("#inp_send").val());
    //"" != a && (ws && 1 == ws.readyState ? (ws.send(a), output(a, 1), $("#inp_send").val("")) : alert("连接已经断开！"))
    testSplit(a);
}

function fun_sendto1() {
    var a = $.trim($("#inp_send").val());
    //"" != a && (ws && 1 == ws.readyState ? (ws.send(a), output(a, 1), $("#inp_send").val("")) : alert("连接已经断开！"))
    testSplit_power(a);
}


$(function() {
        $("#div_msg").width(window.innerWidth - 530),
        $("#div_msg").height(window.innerHeight - 200),
        //fun_initWebSocket(a),
        $("#inp_send").keydown(function(a) {
            return 13 == a.keyCode && a.ctrlKey ? ($("#btn_send").trigger("click"), !1) : void 0
        })
});

function output(a, b) {
    var f, c = new Date,
        d = "blue",
        e = "服务器";
    1 == b && (d = "green", e = "你"),
        f = "<div style='color:" + d + "'>" + e + " " + c.getHours() + ":" + c.getMinutes() + ":" + c.getSeconds() + "</div>",
        $("#div_msg").append("<div style='margin-bottom:10px;position:relative;left:0px;'>" + f + a + "</div>"),
        $("#div_msg").scrollTop($("#div_msg")[0].scrollHeight)
}

function testSplit(str){
    // var str = "照片:0;视频:1";
    var str = "100:4.05;90:4;80:3.92;70:3.85;60:3.79;50:3.74;40:3.7;30:3.67;20:3.63;5:3.59\n" +
        "100:2.93;90:2.88;80:2.82;70:2.77;60:2.7;50:2.66;40:2.6;30:2.55;20:2.4;10:2.3;0:2\n" +
        "100:4.08;80:3.9;60:3.78;40:3.7;20:3.64;5:3.59;1:3.58\n" +
        "100:3.6;90:3.5;80:3.4;70:3.3;60:3.2;50:3.1;40:3.0;30:2.9;20:2.8;10:2.7;0:2.6";
    var out = str.split("\n");
    for(var j = 0;j<out.length; j++){
        var resArr = [];
        var arr1 = out[j].split(";");
        for(var i =0;i<arr1.length;i++){
            var o = {};
            var ele1 = arr1[i];
            var ele2 = ele1.split(":");
            o.n = ele2[0];
            o.v = ele2[1];
            resArr.push(o);
        }
        console.info(JSON.stringify(resArr));
        output(JSON.stringify(resArr),0);
    }
}

function testSplit_power(str_input){
    // var str = "照片:0;视频:1";
    /*370	0	100:4.05;90:4;80:3.92;70:3.85;60:3.79;50:3.74;40:3.7;30:3.67;20:3.63;5:3.59	GT370/GT730,
GT710	0	100:2.93;90:2.88;80:2.82;70:2.77;60:2.7;50:2.66;40:2.6;30:2.55;20:2.4;10:2.3;0:2	GT710/GT740,
GT360	0	100:4.08;80:3.9;60:3.78;40:3.7;20:3.64;5:3.59;1:3.58	GT360,
GT720	0	100:3.6;90:3.5;80:3.4;70:3.3;60:3.2;50:3.1;40:3.0;30:2.9;20:2.8;10:2.7;0:2.6	GT720,
GT300	1	100:6;70:5;40:4;15:3;5:2;1:1	心跳传电压等级类产品,
GT300N	0	100:4.00;80:3.84;60:3.76;40:3.70;20:3.65;5:3.60;1:3.58	GT300N、GT300S、GT300SW、GT300L,
GK310	0	100:100;99:99;98:98;97:97;96:96;95:95;94:94;93:93;92:92;91:91;90:90;89:89;88:88;87:87;86:86;85:85;84:84;83:83;82:82;81:81;80:80;79:79;78:78;77:77;76:76;75:75;74:74;73:73;72:72;71:71;70:70;69:69;68:68;67:67;66:66;65:65;64:64;63:63;62:62;61:61;60:60;59:59;58:58;57:57;56:56;55:55;54:54;53:53;52:52;51:51;50:50;49:49;48:48;47:47;46:46;45:45;44:44;43:43;42:42;41:41;40:40;39:39;38:38;37:37;36:36;35:35;34:34;33:33;32:32;31:31;30:30;29:29;28:28;27:27;26:26;25:25;24:24;23:23;22:22;21:21;20:20;19:19;18:18;17:17;16:16;15:15;14:14;13:13;12:12;11:11;10:10;9:9;8:8;7:7;6:6;5:5;4:4;3:3;2:2;1:1	GK310,
GT420	1	100:4;75:3;50:2;25:1;5:0	GT420,
GT400	0	100:4.13;90:4.08;80:4;70:3.91;60:3.87;50:3.81;40:3.78;30:3.75;20:3.73;10:3.7;5:3.65;0:3.6	GT400*/
    var str = [
        "370	0	100:4.05;90:4;80:3.92;70:3.85;60:3.79;50:3.74;40:3.7;30:3.67;20:3.63;5:3.59	GT370/GT730",
    "GT710	0	100:2.93;90:2.88;80:2.82;70:2.77;60:2.7;50:2.66;40:2.6;30:2.55;20:2.4;10:2.3;0:2	GT710/GT740",
    "GT360	0	100:4.08;80:3.9;60:3.78;40:3.7;20:3.64;5:3.59;1:3.58	GT360",
    "GT720	0	100:3.6;90:3.5;80:3.4;70:3.3;60:3.2;50:3.1;40:3.0;30:2.9;20:2.8;10:2.7;0:2.6	GT720",
    "GT300	1	100:6;70:5;40:4;15:3;5:2;1:1	心跳传电压等级类产品",
    "GT300N	0	100:4.00;80:3.84;60:3.76;40:3.70;20:3.65;5:3.60;1:3.58	GT300N、GT300S、GT300SW、GT300L",
    "GK310	0	100:100;99:99;98:98;97:97;96:96;95:95;94:94;93:93;92:92;91:91;90:90;89:89;88:88;87:87;86:86;85:85;84:84;83:83;82:82;81:81;80:80;79:79;78:78;77:77;76:76;75:75;74:74;73:73;72:72;71:71;70:70;69:69;68:68;67:67;66:66;65:65;64:64;63:63;62:62;61:61;60:60;59:59;58:58;57:57;56:56;55:55;54:54;53:53;52:52;51:51;50:50;49:49;48:48;47:47;46:46;45:45;44:44;43:43;42:42;41:41;40:40;39:39;38:38;37:37;36:36;35:35;34:34;33:33;32:32;31:31;30:30;29:29;28:28;27:27;26:26;25:25;24:24;23:23;22:22;21:21;20:20;19:19;18:18;17:17;16:16;15:15;14:14;13:13;12:12;11:11;10:10;9:9;8:8;7:7;6:6;5:5;4:4;3:3;2:2;1:1	GK310",
    "GT420	1	100:4;75:3;50:2;25:1;5:0	GT420",
    "GT400	0	100:4.13;90:4.08;80:4;70:3.91;60:3.87;50:3.81;40:3.78;30:3.75;20:3.73;10:3.7;5:3.65;0:3.6	GT400"
    ];
    str = str_input?str_input.split(","):str;
    for(var a=0;a<str.length;a++){
        var table = str[a].split("\t");
        //console.info(table[2]);
        var x = "INSERT INTO `together`.`t_config_electric_voltage_templet` \n" +
            "(`templet_name`, `templet_type`, `value_json`, `remark`, `gmt_create`, `gmt_modified`, `created_by`, `modified_by`) VALUES \n" +
            "('{0}', '{1}', '{2}', '{3}', '2018-12-24 15:51:24', '2018-12-24 17:02:42', '1', '1');"
        var resArr = [];
        var arr1 = table[2].split(";");
        // console.info(arr1);
        for(var i =0;i<arr1.length;i++){
            var o = {};
            var ele1 = arr1[i];
            var ele2 = ele1.split(":");
            // console.info(ele2);
            o.n = ele2[0];
            o.v = ele2[1];
            resArr.push(o);
        }
        // console.info(JSON.stringify(resArr));
        table[2] = JSON.stringify(resArr);
        console.info(String.format(x,table[0],table[1],table[2],table[3]));
        output(String.format(x,table[0],table[1],table[2],table[3]),0);
    }
}

String.prototype.format = function(args) {
    var result = this;
    if (arguments.length > 0) {
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if (args[key] != undefined) {
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    //var reg = new RegExp("({[" + i + "]})", "g");//这个在索引大于9时会有问题，谢谢何以笙箫的指出
                    var reg = new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}
String.format = function () {
    if (arguments.length == 0)
        return null;

    var str = arguments[0];
    for (var i = 1; i < arguments.length; i++) {
        var re = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
}