/**
 * 自定义样式
 */
JasMap.require(function(){
    //
    var styleManager = this;
    var jasMap = styleManager.api;
    var mapStyleTemplates = {};
    var getBasePath = jasMap.commonUtil.getApiRootPath;
    //
    mapStyleTemplates.pipesegment2 = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: '#0000ff',
            width: 3
        }),
        fill: new ol.style.Fill({
            color: 'rgba(225, 0, 255, 1)'
        })
    });
    /**
     * 线路段渲染
     * @param feature
     * @returns {*}
     */
    mapStyleTemplates.pipesegment_renderer_by_pipename = function(feature ) {
        if(!styleManager.pipesegment_renderer_by_pipename_style){
            styleManager.pipesegment_renderer_by_pipename_style = {};
        }
        var name = feature.get('pipesegmentname');
        if(!styleManager.pipesegment_renderer_by_pipename_style[name]){
            var color = styleManager.randomColor();
            var style = new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: color ,
                    width: 6
                })
            });
            styleManager.pipesegment_renderer_by_pipename_style[name] = style;
        }
        return styleManager.pipesegment_renderer_by_pipename_style[name]
    };
    /**
     *
     * @param feature
     * @returns {*}
     */
    mapStyleTemplates.polygon_renderer_by_arearank = function(feature ) {
        if(!styleManager.polygon_renderer_by_arearank_style){
            styleManager.polygon_renderer_by_arearank_style = {};
        }
        var name = feature.get('rank');
        var colorArray = {
            '四级地区':[255,0,0,255],
            '三级地区':[255,255,0,255],
            '二级地区':[0,0,255,255],
            '一级地区':[0,255,0,255]
        };
        if(!styleManager.polygon_renderer_by_arearank_style[name]){
            var style = null;
            if(name){
                var color = colorArray[name] ;
                var fillColor = [].concat(color);
                fillColor[3] = 0.2;
                style = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: color ,
                        width: 2
                    }),
                    fill: new ol.style.Fill({
                        color:fillColor
                    })
                });

            }else{
                style = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: [255,0,0,255] ,
                        width: 2
                    }),
                    fill: new ol.style.Fill({
                        color:[255,0,0,0]
                    })
                });
            }

            styleManager.polygon_renderer_by_arearank_style[name] = style;
        }
        return styleManager.polygon_renderer_by_arearank_style[name]
    };
    /**
     * 简单桩样式
     * @type { ol.style.Style }
     */
    mapStyleTemplates.simplestake = new ol.style.Style({
        image: new ol.style.RegularShape({
            fill: new ol.style.Fill({
                color: 'red'
            }),
            stroke:new ol.style.Stroke({
                color: 'black',
                width: 1
            }) ,
            points: 4,
            radius: 5,
            angle: Math.PI / 4
        })
    });

    mapStyleTemplates["cell"] = new ol.style.Style({
        stroke: new ol.style.Stroke({
            color: '#fff907',
            width: 2
        }),
        fill: new  ol.style.Fill({
            color: 'rgba(123,123,123,0.3)'
        })
    });

    mapStyleTemplates.polygon_renderer_by_settlement = function(feature ) {
        return  new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: 'rgba(0,255,0,1)'  ,
                width: 2
            }),
            fill: new ol.style.Fill({
                color: 'rgba(0,255,0,0.6)'
            })
        });
    };

    /**
     * 高后果区要素样式
     * @param feature
     * @returns {*}
     */
    mapStyleTemplates.hca_renderer_by_rank_style = function(feature ) {
        if(!styleManager.hca_renderer_by_rank_style){
            styleManager.hca_renderer_by_rank_style = {};
        }
        var attr = feature.get('rank');
        if(!styleManager.hca_renderer_by_rank_style[attr]){
            //
            //var color = styleManager.randomColor();
            var color = null;
            var fillColor = null ;
            switch (attr){
                case "III":
                    color = [255,0,0,.7];
                    break ;
                case "II":
                    color = [255,155,0,.7];
                    break ;
                case "I":
                    color = [255,255,0,.7];
                    break ;
                default:
                    color=[255,255,0,0];
            }
            fillColor = [].concat(color);
            fillColor[3] = fillColor[3] !== 0 ? 0.5 :0;
            var style = new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: color ,
                    width: 2
                }),
                fill: new  ol.style.Fill({
                    color: fillColor
                })
            });
            styleManager.hca_renderer_by_rank_style[attr] = style;
        }
        return styleManager.hca_renderer_by_rank_style[attr];
    };

    mapStyleTemplates[jasMap.Keys.defaultHighlightStyleName] = new  ol.style.Style({
        image: new  ol.style.Circle({
            fill: new  ol.style.Fill({
                color: 'rgba(255,0,255,0.5)'
            }),
            radius: 5,
            stroke: new  ol.style.Stroke({
                color: '#f0f',
                width: 1
            })
        }),
        stroke: new  ol.style.Stroke({
            color: '#f0f',
            width: 3
        }),
        fill: new ol.style.Fill({
            color: 'rgba(255,0,255,0.3)'
        })
    });
    /**
     *中线桩
     * @type {ol.style.Style}
     */
    mapStyleTemplates.gas_station_style = new ol.style.Style({
        image: new ol.style.Icon({
            src:getBasePath("image/icon_gas_station.png"),
            anchor: [0.5, 0.5]
        })
    });
    mapStyleTemplates.government_style = new ol.style.Style({
        image: new ol.style.Icon({
            src:getBasePath("image/icon_government.png"),
            anchor: [0.5, 0.5]
        })
    });
    mapStyleTemplates.hospital_style = new ol.style.Style({
        image: new ol.style.Icon({
            src:getBasePath("image/icon_hospital.png"),
            anchor: [0.5, 0.5]
        })
    });
    mapStyleTemplates.entertainment_style = new ol.style.Style({
        image: new ol.style.Icon({
            src:getBasePath("image/icon_entertainment_place.png"),
            anchor: [0.5, 0.5]
        })
    });
    //
    return mapStyleTemplates;
});




