       document.onselectstart = function(){return false;};//清除document的select默认事件
        $(document).ready(function (e) {
            //设置最大值
            ScrollBar.maxValue = 150;
            //初始化
            ScrollBar.Initialize();

        });
        var ScrollBar = {
            value:40,
            maxValue: 150,
            step: 1,
            CurrentY: 0,
            Initialize: function () {
                if (this.value > this.maxValue) {
                    alert("给定当前值大于了最大值");
                    return;
                }
                this.GetValue();
                $(".BMap_stdMpSliderBgBot").css("height",  this.CurrentY + 56 + "px");
                $(".BMap_stdMpSliderBar").css("top", this.CurrentY  + "px");
                this.Value();
                $(".BMap_stdMpSliderTxt").html(ScrollBar.value + "/" + ScrollBar.maxValue);
            },
            Value: function () {
                var valite = false;
                var currentValue;
                var hcurrentValue;
                $(".BMap_stdMpSliderBar").mousedown(function () {
                    valite = true;
                    $(document.body).mousemove(function (event) {
                        if (valite == false) return;
                        var changeY = event.clientY - ScrollBar.CurrentY - $(".map-operate").offset().top;
                        currentValue = changeY - ScrollBar.CurrentY;
                        hcurrentValue = ScrollBar.CurrentY - currentValue;
                        $(".BMap_stdMpSliderBar").css("top", currentValue + "px");
                        $(".BMap_stdMpSliderBgBot").css("height",  hcurrentValue + 96 + "px");
                        if ((currentValue + 15) >= $(".BMap_stdMpSlider").height()) {
                            $(".BMap_stdMpSliderBar").css("top", $(".BMap_stdMpSlider").height() - 15 + "px");
                            $(".BMap_stdMpSliderBgBot").css("height", $(".BMap_stdMpSlider").height()- 150 + "px");
                            ScrollBar.value = ScrollBar.maxValue;
                        } else if (currentValue <= 0) {
                            $(".BMap_stdMpSliderBar").css("top", "-3px");
                            $(".BMap_stdMpSliderBgBot").css("height", "150px");
                        } else {
                            ScrollBar.value = Math.round(150 * (currentValue / $(".BMap_stdMpSlider").height()));
                        }
                       $(".BMap_stdMpSliderTxt").html(ScrollBar.value + "/" + ScrollBar.maxValue);
                       $(".BMap_stdMpSliderBar").css("cursor", "move");
                    });
                });
                $(document.body).mouseup(function () {
                    ScrollBar.value = Math.round(150 * (currentValue / $(".BMap_stdMpSlider").height()));
                    valite = false;
                    if (ScrollBar.value >= ScrollBar.maxValue) ScrollBar.value = ScrollBar.maxValue;
                    if (ScrollBar.value <= 0) ScrollBar.value = 0;
                    $(".BMap_stdMpSliderTxt").html(ScrollBar.value + "/" + ScrollBar.maxValue);
                });
            },
            GetValue: function () {
                this.CurrentY = $(".BMap_stdMpSlider").height() * (this.value / this.maxValue);
            }
        }
