if (typeof DCI == "undefined") { var DCI = {}; }
DCI.Plot = {
        dialog:null,//对话框
        map:null,
        graphicslayer:null,
        isload:null,
        imgPath:null,
        toolbar:null,//拓展draw
        toolbar1: null,//拓展draw
        drawToolbar: null,//draw工具
        markerSymbol: null,//默认点符号
        lineSymbol: null,//默认线符号
        _customSymbol:null,//接收外部自定义符号
        fillSymbol: null,//默认面符号
        onDrawEnd: null,//draw回调函数
        //模块初始化函数
        Init:function(map){
            DCI.Plot.map = map;
            DCI.Plot.graphicslayer = new esri.layers.GraphicsLayer();
            DCI.Plot.graphicslayer.id = "plot";
            DCI.Plot.map.addLayer(DCI.Plot.graphicslayer);  //将图层赋给地图
            dojo.connect(DCI.Plot.graphicslayer, "onClick", function (event) {
//                if (event.graphic.attributes)
//                    DCI.Plot.showGraphicWin(event.graphic);
            });
            DCI.Plot.isload = true;
            //定义默认点 线  面符号
            DCI.Plot.markerSymbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE, 8, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 69, 0]), 2), new dojo.Color([255, 255, 255, 1]));
            DCI.Plot.lineSymbol = new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 160, 122]), 2);
            DCI.Plot.fillSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color([255, 160, 122]), 2), new dojo.Color([255, 255, 255, 0.5]));
            //初始化拓展Draw
            DCI.Plot.toolbar = new DrawEx(map);
            DCI.Plot.toolbar.on("draw-end", DCI.Plot.addToMap);
            DCI.Plot.toolbar1 = new DrawExt(map);
            DCI.Plot.toolbar1.on("draw-end", DCI.Plot.addToMap);
            //arcgis api自带的Draw
            DCI.Plot.drawToolbar = new esri.toolbars.Draw(map);
            DCI.Plot.drawToolbar.markerSymbol = DCI.Plot.markerSymbol;
            DCI.Plot.drawToolbar.lineSymbol = DCI.Plot.lineSymbol;
            DCI.Plot.drawToolbar.fillSymbol = DCI.Plot.fillSymbol;
            DCI.Plot.drawToolbar.on("draw-end", DCI.Plot.drawEnd);

        },
        drawEnd: function (evt) {
            if (DCI.Plot.onDrawEnd)
                DCI.Plot.onDrawEnd(evt.geometry);
        },
        /**
         * 拓展Draw绘制完毕调用的函数
        */
        addToMap: function (evt) {
            DCI.Plot.map.setMapCursor('auto');//设置鼠标的光标      
            DCI.Plot.toolbar.deactivate();
            DCI.Plot.toolbar1.deactivate();
            if(!DCI.Plot._customSymbol){
            	switch (evt.geometry.type)
                {
                    case "point":
                    case "multipoint":
                    	DCI.Plot._customSymbol = DCI.Plot.markerSymbol;
                        break;
                    case "polyline":
                    	DCI.Plot._customSymbol = DCI.Plot.lineSymbol;
                    	break;
                    default:
                    	DCI.Plot._customSymbol = DCI.Plot.fillSymbol;
                    	break;
                }
            }
            var graphic = new esri.Graphic(evt.geometry, DCI.Plot._customSymbol);
            DCI.Plot.graphicslayer.add(graphic);
            DCI.Plot._customSymbol=null;
        },
        //初始化监听事件
        InitEvent:function(){
            //标绘---形状选择点击事件
            $("#shapepage span").bind("click", function () {
                if (DCI.Plot.map) {
                    DCI.Plot.map.setMapCursor('auto');//设置鼠标的光标
                }
                DCI.Plot.map.setMapCursor('crosshair');
                var symbol=null;
                var geo = null;
                switch ($(this).index()) {
                    case 0://plot_freehandline
                        DCI.Plot.drawFreeHandPolyline(null, function (geometry) {
                            symbol = DCI.Plot.lineSymbol;
                            DCI.Plot.drawEndPlot(geometry, symbol);
                        });
                        break;
                    case 1://plot_line
                        DCI.Plot.drawPolyline(null, function (geometry) {
                            symbol = DCI.Plot.lineSymbol;
                            DCI.Plot.drawEndPlot(geometry, symbol);
                        });
                        break;
                    case 2://emergency_freehand
                        DCI.Plot.drawFreeHandPolygon(null, function (geometry) {
                            symbol = DCI.Plot.fillSymbol;
                            DCI.Plot.drawEndPlot(geometry, symbol);
                        });
                        break;
                    case 3://plot_polygon
                        DCI.Plot.drawPolygon(null, function (geometry) {
                            symbol = DCI.Plot.fillSymbol;
                            DCI.Plot.drawEndPlot(geometry, symbol);
                        });
                        break;
                    case 4://plot_extent
                        DCI.Plot.drawExtent(null, function (geometry) {
                            symbol = DCI.Plot.fillSymbol;
                            DCI.Plot.drawEndPlot(geometry, symbol);
                        });
                        break;
                    case 5://emergency_freehand
                        DCI.Plot.drawCircle(null, function (geometry) {
                            symbol = DCI.Plot.fillSymbol;
                            DCI.Plot.drawEndPlot(geometry, symbol);
                        });
                        break;
                    case 6://直角箭头
                        DCI.Plot.drawStraightArrow(null, function (geometry) {
                            symbol = DCI.Plot.fillSymbol;
                            DCI.Plot.drawEndPlot(geometry, symbol);
                        });
                        break;
                    case 7://简单箭头
                        DCI.Plot.toolbar.activate(Extension.DrawEx.FREEHAND_ARROW);
                      break; 
                    case 8://燕尾箭头
                        DCI.Plot.toolbar1.fillSymbol = DCI.Plot.fillSymbol;
                        DCI.Plot.toolbar1.activate("tailedsquadcombat");
                        break;  
                    case 9://集结地
                        DCI.Plot.toolbar.activate(Extension.DrawEx.BEZIER_POLYGON);
                      break; 
                    case 10://弧线
                        DCI.Plot.toolbar.activate(Extension.DrawEx.CURVE);
                      break;  
                    case 11://曲线
                        DCI.Plot.toolbar.activate(Extension.DrawEx.BEZIER_CURVE);
                      break;                        
                        
                }
                                  
            });
            if (DCI.Plot.dialog) {
                DCI.Plot.dialog.bind("close", function () {
                    DCI.Plot.graphicslayer.clear();
                    DCI.Plot.map.infoWindow.hide();
            	}); 
            }
            
            
        },
        
        /**
         * 绘制完毕调用的函数
        */
        drawEndPlot: function (geometry,symbol) {
            var graphic = new esri.Graphic(geometry, symbol);
            DCI.Plot.graphicslayer.add(graphic);
            DCI.Plot.deactivateDraw();
            DCI.Plot.map.setMapCursor('auto');//设置鼠标的光标          
        },
        //画点
        drawPoint: function (symbol, onDrawEnd) {
            DCI.Plot.onDrawEnd = onDrawEnd;
            if (symbol) {
                DCI.Plot.drawToolbar.markerSymbol = symbol;
            }
            DCI.Plot.drawToolbar.activate(esri.toolbars.Draw.POINT);
            DCI.Plot.disablePan();
        },
        //画多点
        drawMultiPoints: function (symbol, onDrawEnd) {
        	DCI.Plot.onDrawEnd = onDrawEnd;
        	if (symbol) {
        		DCI.Plot.drawToolbar.markerSymbol = symbol;
        	}
        	DCI.Plot.drawToolbar.activate(esri.toolbars.Draw.MULTI_POINT);
        	DCI.Plot.disablePan();
        },
        //画折线
        drawPolyline: function (symbol, onDrawEnd) {
            this.onDrawEnd = onDrawEnd;
            if (symbol) {
                this.drawToolbar.lineSymbol = symbol;
            }
            this.drawToolbar.activate(esri.toolbars.Draw.POLYLINE);
            this.disablePan();
        },
        //画弧线
        drawArc: function (symbol) {
        	this._customSymbol=symbol;
        	DCI.Plot.toolbar.setLineSymbol(symbol);
            DCI.Plot.toolbar.activate(DrawEx.CURVE);
            this.disablePan();
        },
        //画曲线
        drawBezier_curve: function (symbol) {
        	this._customSymbol=symbol;
        	DCI.Plot.toolbar.setLineSymbol(symbol);
        	DCI.Plot.toolbar.activate(DrawEx.BEZIER_CURVE);
        	this.disablePan();
        },
        //自由线
        drawFreeHandPolyline: function (symbol, onDrawEnd) {
            this.onDrawEnd = onDrawEnd;
            if (symbol) {
                this.drawToolbar.lineSymbol = symbol;
            }
            this.drawToolbar.activate(esri.toolbars.Draw.FREEHAND_POLYLINE);
            this.disablePan();
        },
        //画多边形
        drawPolygon: function (symbol, onDrawEnd) {
            this.onDrawEnd = onDrawEnd;
            if (symbol) {
                this.drawToolbar.fillSymbol = symbol;
            }
            this.drawToolbar.activate(esri.toolbars.Draw.POLYGON);
            this.disablePan();
        },
        //手绘多边形
        drawFreeHandPolygon: function (symbol, onDrawEnd) {
            this.onDrawEnd = onDrawEnd;
            if (symbol) {
                this.drawToolbar.fillSymbol = symbol;
            }
            this.drawToolbar.activate(esri.toolbars.Draw.FREEHAND_POLYGON);
            this.disablePan();
        },
        //聚集区
        drawBezierPolygon: function (symbol) {
        	this._customSymbol=symbol;
        	DCI.Plot.toolbar.setFillSymbol(DCI.Plot._customSymbol);
        	DCI.Plot.toolbar.activate(DrawEx.BEZIER_POLYGON);
        	this.disablePan();
        },
        //画圆形
        drawCircle: function (symbol, onDrawEnd) {
            this.onDrawEnd = onDrawEnd;
            if (symbol) {
                this.drawToolbar.fillSymbol = symbol;
            }
            this.drawToolbar.activate(esri.toolbars.Draw.CIRCLE);
            this.disablePan();
        },
        //画椭圆
        drawEllipse: function (symbol, onDrawEnd) {
        	this.onDrawEnd = onDrawEnd;
        	if (symbol) {
        		this.drawToolbar.fillSymbol = symbol;
        	}
        	this.drawToolbar.activate(esri.toolbars.Draw.ELLIPSE);
        	this.disablePan();
        },
        //画矩形
        drawExtent: function (symbol, onDrawEnd) {
            this.onDrawEnd = onDrawEnd;
            if (symbol) {
                this.drawToolbar.fillSymbol = symbol;
            }else{
                this.drawToolbar.setFillSymbol(DCI.Plot.fillSymbol);
            }
            this.drawToolbar.activate(esri.toolbars.Draw.EXTENT);
            this.disablePan();
        },
        //直角箭头
        drawStraightArrow: function (symbol, onDrawEnd) {
            this.onDrawEnd = onDrawEnd;
            if (symbol) {
                this.drawToolbar.fillSymbol = symbol;
            }
            this.drawToolbar.activate(esri.toolbars.Draw.ARROW);
            this.disablePan();
        },
        //斜箭头
        drawFreeHandArrow: function (symbol) {
        	this._customSymbol=symbol;
        	DCI.Plot.toolbar.setFillSymbol(DCI.Plot._customSymbol);
        	this.toolbar.activate(DrawEx.FREEHAND_ARROW);
        	this.disablePan();
        },
        //燕尾箭头
        drawTailArrow: function (symbol) {
        	this._customSymbol=symbol;
        	DCI.Plot.toolbar1.setFillSymbol(DCI.Plot._customSymbol);
            DCI.Plot.toolbar1.activate("tailedsquadcombat");
        	this.disablePan();
        },
        deactivateDraw: function () {
            this.drawToolbar.deactivate();
            this.onDrawEnd = null;
            this.enablePan();
        },
        disablePan: function () {
            this.map.disablePan();
        },
        enablePan: function () {
            this.map.enablePan();
        }


}