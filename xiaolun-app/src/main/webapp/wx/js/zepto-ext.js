(function ($, Template, Settings) {
    $.applyTemplate = function ($container, templateId, Model, Helper) {
        Model = Model || {};

        Helper = Helper || {
                substring: function (str, start, end) {
                    return str.substring(start, end);
                },
                empty: function (arr) {
                    return arr.length == 0;
                }
            };

        $.each(Helper, function (funcname, func) {
            Template.helper(funcname, func);
        });

        try {
            var result = Template(templateId, Model);
            $container.empty().append(result);
        } catch (e) {
        }
    };
    $.getJSONExt = function (url, callback, data) {
        $(Settings['loading']).fadeIn();
        data = data || {};
        $.ajax({
            cache: false,
            type: "GET",
            url: url,
            data: data,
            dataType: "json",
            success: function (Resp) {
                var Result = $.extend({}, Resp['result']);
                if (Result["errorCode"] == 0) {
                    callback(Result["data"]);
                } else {
                    $.tips({
                        content: Result["message"],
                        stayTime: 3000,
                        type: "info"
                    });
                }
            },
            error: function () {
                $.tips({
                    content: "无法处理您的请求",
                    stayTime: 3000,
                    type: "info"
                });
            },
            complete: function () {
                $(Settings['loading']).fadeOut(350);
            }
        });
    };

    var wxConfig = false;

    $.applyWx = function (callback, wx) {
        wx = wx || window['wx'];

        if (!wxConfig) {
            $.getJSONExt('jsapiSignature.action?currentUrl=' + location.href.split('#')[0], function (Data) {
                wx.config({
                    debug: false,
                    appId: Data["appid"],
                    timestamp: Data["timestamp"],
                    nonceStr: Data["noncestr"],
                    signature: Data["signature"],
                    jsApiList: ['chooseImage', 'previewImage', 'uploadImage']
                });
                window.config = true;
            });
        }

        wx.ready(function () {
            callback(wx);
        });
    };

    $.fn.applyTemplate = function (templateId, Model, Helper) {
        var $this = $(this);
        $.applyTemplate($this, templateId, Model, Helper);
        return $this;
    };

    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    $.fn.fadeIn = function () {
        $(this).removeClass('hide');
        $(this).addClass('show');
    }

    $.fn.fadeOut = function () {
        $(this).removeClass('show');
        $(this).addClass('hide');
    }

})(window['$'], window['template'], {loading: '#ajax_loading'});