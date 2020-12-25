$(function () {
    let tab = $('.change-login p.on').attr('name');
    // 选项卡切换
    $(".account_number").click(function () {
        $('.tel-warn').addClass('hide');
        $(this).addClass("on");
        $(".message").removeClass("on");
        $(".form2").addClass("hide");
        $(".form1").removeClass("hide");
        tab = $('.change-login p.on').attr('name');
        checkBtn();
    });
    // 选项卡切换
    $(".message").click(function () {
        $('.tel-warn').addClass('hide');
        $(this).addClass("on");
        $(".account_number").removeClass("on");
        $(".form2").removeClass("hide");
        $(".form1").addClass("hide");
        tab = $('.change-login p.on').attr('name');
        checkBtn();

    });

    $('#num').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#pass').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#veri').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#num2').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#veri-code').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    // 按钮是否可点击
    function checkBtn() {
        $(".log-btn").off('click');
        if (tab === 'account') {
            let inp = $.trim($('#num').val());
            let pass = $.trim($('#pass').val());
            if (inp != '' && pass != '') {
                if (!$('.code').hasClass('hide')) {
                    code = $.trim($('#veri').val());
                    if (code === '') {
                        $(".log-btn").addClass("off");
                    } else {
                        $(".log-btn").removeClass("off");
                        sendBtn();
                    }
                } else {
                    $(".log-btn").removeClass("off");
                    sendBtn();
                }
            } else {
                $(".log-btn").addClass("off");
            }
        } else {
            let phone = $.trim($('#num2').val());
            let code2 = $.trim($('#veri-code').val());
            if (phone != '' && code2 != '') {
                $(".log-btn").removeClass("off");
                sendBtn();
            } else {
                $(".log-btn").addClass("off");
            }
        }
    }

    function checkAccount(username) {
        if (username === '') {
            $('.num-err').removeClass('hide').find("em").text('请输入账户');
            return false;
        } else {
            $('.num-err').addClass('hide');
            return true;
        }
    }

    function checkPass(pass) {
        if (pass === '') {
            $('.pass-err').removeClass('hide').text('请输入密码');
            return false;
        } else {
            $('.pass-err').addClass('hide');
            return true;
        }
    }

    function checkCode(code) {
        if (code === '') {
            // $('.tel-warn').removeClass('hide').text('请输入验证码');
            return false;
        } else {
            // $('.tel-warn').addClass('hide');
            return true;
        }
    }

    function checkPhone(phone) {
        let status = true;
        if (phone === '') {
            $('.num2-err').removeClass('hide').find("em").text('请输入手机号');
            return false;
        }
        let param = /^1[34578]\d{9}$/;
        if (!param.test(phone)) {
            // globalTip({'msg':'手机号不合法，请重新输入','setTime':3});
            $('.num2-err').removeClass('hide');
            $('.num2-err').text('手机号不合法，请重新输入');
            return false;
        }
        // $.ajax({
        //     url: '/checkPhone',
        //     type: 'post',
        //     dataType: 'json',
        //     async: false,
        //     data: {phone: phone, type: "login"},
        //     success: function (data) {
        //         if (data.code === '0') {
        //             $('.num2-err').addClass('hide');
        //             // console.log('aa');
        //             // return true;
        //         } else {
        //             $('.num2-err').removeClass('hide').text(data.msg);
        //             // console.log('bb');
        //             status = false;
        //             // return false;
        //         }
        //     },
        //     error: function () {
        //         status = false;
        //         // return false;
        //     }
        // });
        return status;
    }

    function checkPhoneCode(pCode) {
        if (pCode === '') {
            $('.error').removeClass('hide').text('请输入验证码');
            return false;
        } else {
            $('.error').addClass('hide');
            return true;
        }
    }

    // 登录点击事件
    function sendBtn() {
        if (tab === 'account') {
            $(".log-btn").click(function () {
                // let type = 'phone';
                let username = $.trim($('#num').val());
                let password = $('#pass').val();
                if (checkAccount(username) && checkPass(password)) {
                    if (!$('.code').hasClass('hide')) {
                        let captcha = $.trim($('#veri').val());
                        if (!checkCode(captcha)) {
                            return false;
                        }
                    }
                    $('#accountLogin').submit();
                } else {
                    return false;
                }
            });
        } else {
            $(".log-btn").click(function () {
                // let type = 'phone';
                let mobile = $.trim($('#num2').val());
                let captcha = $.trim($('#veri-code').val());
                if (checkPhone(mobile) && checkPass(captcha)) {
                    $('#smsLogin').submit();
                } else {
                    $(".log-btn").off('click').addClass("off");
                    return false;
                }
            });
        }
    }

    // 登录的回车事件
    $(window).keydown(function (event) {
        if (event.keyCode === 13) {
            $('.log-btn').trigger('click');
        }
    });


    $(".form-data").delegate(".send", "click", function () {
        let mobile = $.trim($('#num2').val());
        if (checkPhone(mobile)) {
            $.ajax({
                url: '/public/mobile/captcha',
                type: 'get',
                dataType: 'json',
                async: true,
                data: {mobile: mobile},
                success: function (result) {
                    if (result.success) {
                        let data = result.data;
                        $('#smsCaptchaKey').val(data.captchaKey);
                    } else {
                        $('.message-error').text(result.msg);
                    }
                },
                error: function () {

                }
            });
            let oTime = $(".form-data .time"),
                oSend = $(".form-data .send"),
                num = parseInt(oTime.text()),
                oEm = $(".form-data .time em");
            $(this).hide();
            oTime.removeClass("hide");
            let timer = setInterval(function () {
                let num2 = num -= 1;
                oEm.text(num2);
                if (num2 === 0) {
                    clearInterval(timer);
                    oSend.text("重新发送验证码");
                    oSend.show();
                    oEm.text("60");
                    oTime.addClass("hide");
                }
            }, 1000);
        }
    });


});