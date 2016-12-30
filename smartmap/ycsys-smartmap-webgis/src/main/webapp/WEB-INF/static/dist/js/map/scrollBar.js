       document.onselectstart = function(){return false;};//清除document的select默认事件
        var ScrollBar = {
            value:7,
            maxValue: 19,
            minValue:0,
            step: function(){
            	return $(".BMap_stdMpSlider").height()/(this.maxValue-this.minValue);
            },
            CurrentY: 0,
            map:null,
            sliderHeight:function(){
            	return $(".BMap_stdMpSlider").height();
            },
            barHeight:function(){
            	return $(".BMap_stdMpSliderBar").height();
            },
            Initialize: function (map) {
                if (this.value > this.maxValue) {
                    alert("给定当前值大于了最大值");
                    return;
                }
                this.map=map;
                this.value=map.getZoom();
                $(".BMap_stdMpSliderBar").css("cursor", "pointer");
                this.GetValue();
                $(".BMap_stdMpSliderBgBot").css("height",  this.CurrentY + "px");
                $(".BMap_stdMpSliderBar").css("top", (this.sliderHeight() - this.CurrentY - this.barHeight()/2)  + "px");
                $(".BMap_stdMpSliderTxt").html(ScrollBar.value + "级");
                this.Value();
            },
            Value: function () {
                var valite = false;
                var topVal;//滑块上半部高度
                var bottomVal;//滑块下半部高度
                var barHeight=this.barHeight();//滑块高度
                var sliderHeight=this.sliderHeight();//滑动条高度
                var step=this.step();
                $(".BMap_stdMpSliderBar").mousedown(function () {
                    valite = true;
                    $(document.body).mousemove(function (event) {
                        if (valite == false) return;
                        var top=$(".BMap_stdMpSlider").offset().top;
                        topVal=event.clientY- top;
                        bottomVal=sliderHeight-topVal;
                        if(bottomVal>sliderHeight) bottomVal=sliderHeight;
                        if(bottomVal<0) bottomVal=0;
                        
                        var steps=Math.round(bottomVal/step);//多少级(格)
                        ScrollBar.value =ScrollBar.minValue + steps;
                        bottomVal=steps * step;
                        
                        var barTop=sliderHeight-bottomVal-barHeight/2;
                        if(barTop>sliderHeight-barHeight/2) barTop=sliderHeight-barHeight/2;
                        if(barTop<-barHeight/2) barTop=-barHeight/2;
                        
                        $(".BMap_stdMpSliderBgBot").css("height", bottomVal + "px");
                        $(".BMap_stdMpSliderBar").css("top", barTop + "px");
                        $(".BMap_stdMpSliderTxt").html(ScrollBar.value + "级");
                    });
                });
                $(document.body).mouseup(function () {
                    valite = false;
                    if (ScrollBar.value >= ScrollBar.maxValue) ScrollBar.value = ScrollBar.maxValue;
                    if (ScrollBar.value <= ScrollBar.minValue) ScrollBar.value = ScrollBar.minValue;
                    var steps=Math.round(bottomVal/step);//多少级(格)
                    ScrollBar.value =ScrollBar.minValue + steps;
                    $(".BMap_stdMpSliderTxt").html(ScrollBar.value + "级");
                    map.setZoom(ScrollBar.value);
                });
            },
            GetValue: function () {
                this.CurrentY = $(".BMap_stdMpSlider").height() * ((this.value-this.minValue) / (this.maxValue-this.minValue));
            },
            SetValue:function(val){
                if(val>this.maxValue){
                	val=this.maxValue;
                }else if(val<this.minValue){
                	val=this.minValue;
                }
            	this.value=val;
            	var barHeight=this.barHeight();//滑块高度
                var sliderHeight=this.sliderHeight();//滑动条高度
            	var bottomVal=(val-this.minValue)/(this.maxValue-this.minValue)*sliderHeight;
            	var barTop=sliderHeight-bottomVal-barHeight/2;
                if(barTop>sliderHeight-barHeight/2) barTop=sliderHeight-barHeight/2;
                if(barTop<-barHeight/2) barTop=-barHeight/2;
                
                $(".BMap_stdMpSliderBgBot").css("height", bottomVal + "px");
                $(".BMap_stdMpSliderBar").css("top", barTop + "px");
                $(".BMap_stdMpSliderTxt").html(ScrollBar.value + "级");
            }
        }
