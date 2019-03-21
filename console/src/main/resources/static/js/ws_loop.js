var ws;
//重连时间(毫秒)
var reconnectTime = 30000;

//发起websocket协议的请求
function doSend(message){
	if (!window.WebSocket) {
        return;
    }
    if (ws.readyState == WebSocket.OPEN) {
        ws.send(message);
    } else {
        output("通道未打开",0);
    }
}
function fun_close() {
    ws.close()
}
function fun_sendto() {
    var a = $.trim($("#inp_send").val());
    "" != a && (ws && 1 == ws.readyState ? (ws.send(a), output(a, 1), $("#inp_send").val("")) : alert("连接已经断开！"))
}
function fun_initWebSocket() {
    if (ws_url = $.trim($("#inp_url").val()).toLocaleLowerCase()) {
        $("#btn_conn").attr("disabled", !0),
            $("#btn_close").attr("disabled", !1);
        try {
            ws = new WebSocket($.trim($("#inp_url").val())),
            //     ws = new ReconnectingWebSocket($.trim($("#inp_url").val()),null,{debug: false, reconnectInterval: reconnectTime,maxReconnectAttempts : 2});
                output("等待服务器握手包...", 1),
                ws.onopen = function() {
                    output("收到服务器握手包.", 1),
                        output("连接已建立，正在等待数据...", 0)
                },
                ws.onmessage = function(a) {
                    output(a.data, 0)
                },
                ws.onclose = function() {
                    $("#btn_conn").attr("disabled", !1),
                        $("#btn_close").attr("disabled", !0),
                        output("和服务器断开连接！", 0)
                }
        } catch(a) {
            $("#btn_conn").attr("disabled", !1),
                $("#btn_close").attr("disabled", !0),
                output("ws的地址错误,请重新输入！", 1)
        }
    }
}

function fun_loop_initWebSocket() {
    var loopNum = $.trim($("#inp_url_loop").val());
    output("循环建立ws连接:."+loopNum, 1);
    for(var i = 0;i<loopNum;i++){
        if (ws_url = $.trim($("#inp_url").val()).toLocaleLowerCase()) {
            $("#btn_conn").attr("disabled", !0),
                $("#btn_close").attr("disabled", !1);
            try {
                ws = new WebSocket($.trim($("#inp_url").val())),
                    //     ws = new ReconnectingWebSocket($.trim($("#inp_url").val()),null,{debug: false, reconnectInterval: reconnectTime,maxReconnectAttempts : 2});
                    output("等待服务器握手包...", 1),
                    ws.onopen = function() {
                        output("收到服务器握手包.", 1),
                            output("连接已建立，正在等待数据...", 0)
                    },
                    ws.onmessage = function(a) {
                        output(a.data, 0)
                    },
                    ws.onclose = function() {
                        $("#btn_conn").attr("disabled", !1),
                            $("#btn_close").attr("disabled", !0),
                            output("和服务器断开连接！", 0)
                    }
            } catch(a) {
                $("#btn_conn").attr("disabled", !1),
                    $("#btn_close").attr("disabled", !0),
                    output("ws的地址错误,请重新输入！", 1)
            }
        }
    }
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