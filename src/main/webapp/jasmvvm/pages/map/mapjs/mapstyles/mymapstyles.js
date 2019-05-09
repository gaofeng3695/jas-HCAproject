
JasMap.require(function () {

    var styleManager = this; //上下文环境为地图应用对象的样式管理器StyleManager对象
    var jasMap = styleManager.api; //地图应用对象
    var mapStyleTemplates = {}; //声明样式模版对象
    var getBasePath = jasMap.commonUtil.getApiRootPath; //工具方法

    //----------------------管线基础数据---------------------
    mapStyleTemplates.demo_pd_zhanchang_style = function(feature) {
        return new ol.style.Style({
            image: new ol.style.Icon({
                src:getBasePath("basepath:images/renderer/nmg/changzhan.png"),
                anchor: [0.5, 0.5],
                scale:0.75
            })
        });
    };
    mapStyleTemplates.demo_pd_fashi_style = function(feature) {
        return new ol.style.Style({
            image: new ol.style.Icon({
                src:getBasePath("basepath:images/renderer/nmg/fashi.png"),
                anchor: [0.5, 0.5],
                scale:0.75
            })
        });
    };
    mapStyleTemplates.demo_pd_pipeline_style = function(feature) {
        return new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: '#ff0000',
                width: 4
            })
        });
    };
    mapStyleTemplates.demo_pd_chuankuayue_style = function(feature) {
        return new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: '#aa00ff',
                width: 5
            })
        });
    };
    
    //---------------------应急资源-------------
    mapStyleTemplates.demo_pd_yiyuan_style = function(feature) {
        return new ol.style.Style({
            image: new ol.style.Icon({
                src:getBasePath("basepath:images/renderer/nmg/yjzy_yiyuan.png"),
                anchor: [0.5, 0.5],
                scale:0.75
            })
        });
    };
    mapStyleTemplates.demo_pd_gongan_style = function(feature) {
        return new ol.style.Style({
            image: new ol.style.Icon({
                src:getBasePath("basepath:images/renderer/nmg/yjzy_gongan.png"),
                anchor: [0.5, 0.5],
                scale:0.75
            })
        });
    };
    mapStyleTemplates.demo_pd_xiaofang_style = function(feature) {
        return new ol.style.Style({
            image: new ol.style.Icon({
                src:getBasePath("basepath:images/renderer/nmg/yjzy_xiaofang.png"),
                anchor: [0.5, 0.5],
                scale:0.75
            })
        });
    };
    mapStyleTemplates.demo_pd_xuexiao_style = function(feature) {
        return new ol.style.Style({
            image: new ol.style.Icon({
                src:getBasePath("basepath:images/renderer/nmg/yjzy_xuexiao.png"),
                anchor: [0.5, 0.5],
                scale:0.75

            })
        });
    };

    
    //-----------隐患--------------------------
    mapStyleTemplates.demo_pd_yinhuan_render_by_level = function(feature) {
        if(!styleManager.demo_pd_yinhuan_render_by_level){
            styleManager.demo_pd_yinhuan_render_by_level = {};
        }
        var name = feature.get('level');
        if(!styleManager.demo_pd_yinhuan_render_by_level[name]){
            //创建openlayers样式
			var idx = 1  ;
			switch (name){
				case '重大隐患':
					idx = 1 ;break;
                case '较大隐患':
                    idx = 2 ;break;
                case '一般隐患':
                    idx = 3 ;break;
				default:;
			}
            var style = new ol.style.Style({
                image: new ol.style.Icon({
                    src:getBasePath("basepath:images/renderer/nmg/yh_"+ idx +".png"),
                    anchor: [0.5, 0.5],
                    scale:0.75
                })
            });
            //保存已经有的属性字段样式
            styleManager.demo_pd_yinhuan_render_by_level[name] = style;
        }
        return styleManager.demo_pd_yinhuan_render_by_level[name]
    };

    
  //-----------工地--------------------------
    mapStyleTemplates.demo_pd_gongdi_render_by_level = function(feature) {
        if(!styleManager.demo_pd_gongdi_render_by_level){
            styleManager.demo_pd_gongdi_render_by_level = {};
        }
        var name = feature.get('level');
        if(!styleManager.demo_pd_gongdi_render_by_level[name]){
            //创建openlayers样式
			var idx = 1  ;
			switch (name){
				case '5米工地':
					idx = '5m' ;break;
                case '50米工地':
                    idx = '50m' ;break;
                case '100米工地':
                    idx = '100m' ;break;
				default:;
			}
            var style = new ol.style.Style({
                image: new ol.style.Icon({
                    src:getBasePath("basepath:images/renderer/nmg/gongdi_"+ idx +".png"),
                    anchor: [0.5, 0.5],
                    scale:0.75
                })
            });
            //保存已经有的属性字段样式
            styleManager.demo_pd_gongdi_render_by_level[name] = style;
        }
        return styleManager.demo_pd_gongdi_render_by_level[name]
    };
    
    //-------------------风险------------>>>
    mapStyleTemplates.demo_pd_fengxian_render_by_level = function(feature) {
        if(!styleManager.demo_pd_fengxian_render_by_level){
            styleManager.demo_pd_fengxian_render_by_level = {};
        }
        var name = feature.get('level');
        if(!styleManager.demo_pd_fengxian_render_by_level[name]){
            //创建openlayers样式
			var idx = 1  ;
			switch (name){
				case 'I级风险':
					idx = 1 ;break;
                case 'Ⅱ级风险':
                    idx = 2 ;break;
                case 'Ⅲ级风险':
                    idx = 3 ;break;
				default:;
			}
            var style = new ol.style.Style({
                image: new ol.style.Icon({
                    src:getBasePath("basepath:images/renderer/nmg/fengxian_"+ idx +".png"),
                    anchor: [0.5, 0.5],
                    scale:0.75
                })
            });
            //保存已经有的属性字段样式
            styleManager.demo_pd_fengxian_render_by_level[name] = style;
        }
        return styleManager.demo_pd_fengxian_render_by_level[name]
    };

    
  //-------------------腐蚀------------>>>
    mapStyleTemplates.demo_pd_fushi_render_by_level = function(feature) {
    	return new ol.style.Style({
            stroke: new ol.style.Stroke({
                color: '#ffb719',
                width: 5
            })
        });
    };
    
    //-------------------地灾------------>>>
    var _index = 0;
    mapStyleTemplates.demo_pd_dizai_render_by_level = function(feature) {
        if(!styleManager.demo_pd_dizai_render_by_level){
            styleManager.demo_pd_dizai_render_by_level = {};
        }
        var name = feature.get('level');
        if(!styleManager.demo_pd_dizai_render_by_level[name]){
            //创建openlayers样式
			var idx = 1  ;
			switch (name){
				case '重大地灾':
					idx = 1 ;break;
	            case '较大地灾':
	                idx = 2 ;break;
	            case '一般地灾':
	                idx = 3 ;break;
				default:;
			}
			var radom = ['dz_huapo_','dz_bengta_','dz_dimiantaxian_','dz_shuituliushi_','dz_huapo_','dz_bengta_'];
			
            var style = new ol.style.Style({
                image: new ol.style.Icon({
                    src:getBasePath("basepath:images/renderer/nmg/"+radom[_index]+ idx +".png"),
                    anchor: [0.5, 0.5],
                    scale:0.75
                })
            });
            _index++;
            //保存已经有的属性字段样式
            styleManager.demo_pd_dizai_render_by_level[name] = style;
        }
        return styleManager.demo_pd_dizai_render_by_level[name]
    };
    
    
    //---------------------高后果区--------------------------------------------
    mapStyleTemplates.demo_pd_gaohouguoqu_render_by_level = function(feature) {
        if(!styleManager.demo_pd_gaohouguoqu_render_by_level){
            styleManager.demo_pd_gaohouguoqu_render_by_level = {};
        }
        var name = feature.get('level');
        if(!styleManager.demo_pd_gaohouguoqu_render_by_level[name]){
            //创建openlayers样式
        	
        	var style = null;
        	
			switch (name){
				case 1:
					style = new ol.style.Style({
						fill: new ol.style.Fill({
				            color: 'rgba(255, 45, 45, 0.6)'
				        }),
				        stroke: new ol.style.Stroke({
				            color: '#ff2d2d',
				            width: 1
				        })
		            });
					break;
                case 2:
                	style = new ol.style.Style({
                		fill: new ol.style.Fill({
                            color: 'rgba(255, 116, 0, 0.6)'
                        }),
                        stroke: new ol.style.Stroke({
                            color: '#ff7400',
                            width: 1
                        })
		            });
                 
                    break;
                case 3:
                	style = new ol.style.Style({
                		fill: new ol.style.Fill({
                            color: 'rgba(255, 183, 25, 0.6)'
                        }),
                        stroke: new ol.style.Stroke({
                            color: '#ffb719',
                            width: 1
                        })
		            });
                    break;
				default:;
			}
            
            //保存已经有的属性字段样式
            styleManager.demo_pd_gaohouguoqu_render_by_level[name] = style;
        }
        return styleManager.demo_pd_gaohouguoqu_render_by_level[name]
    };
    
    
  //-------------------高后果   摄像头------------>>>
    mapStyleTemplates.demo_pd_shexiangtou_render_by_level = function(feature) {
        if(!styleManager.demo_pd_shexiangtou_render_by_level){
            styleManager.demo_pd_shexiangtou_render_by_level = {};
        }
        var name = feature.get('level');
        if(!styleManager.demo_pd_shexiangtou_render_by_level[name]){
            //创建openlayers样式
            var style = new ol.style.Style({
                image: new ol.style.Icon({
                    src:getBasePath("basepath:images/renderer/nmg/shexiangtou.png"),
                    anchor: [0.5, 0.5],
                    scale:0.75
                })
            });
            //保存已经有的属性字段样式
            styleManager.demo_pd_shexiangtou_render_by_level[name] = style;
        }
        
        return styleManager.demo_pd_shexiangtou_render_by_level[name]
    };
    
    //------------------- 水源地 -----------------------------
    mapStyleTemplates.demo_pd_syd_render_by_level = function(feature) {
         
        //创建openlayers样式
        return new ol.style.Style({
            image: new ol.style.Icon({
                src:getBasePath("basepath:images/renderer/nmg/demo_pd_syd.png"),
                anchor: [0.5, 0.5],
                scale:0.75
            })
        }); 
    };
    
    
    //施工数据采集地图样式
    mapStyleTemplates.mvPipeNode = new ol.style.Style({
		image: new ol.style.RegularShape(({
			fill: new ol.style.Fill({
				color: '#82ff46'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			radius: 5,
			points:6,
		}))
	});
	//中低压---阀门信息
	mapStyleTemplates.mvValveInfo = new ol.style.Style({
		image: new ol.style.RegularShape(({
			fill: new ol.style.Fill({
				color: '#003e3e'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			radius: 5,
			points:6,
		}))
	});
	//中低压--标注桩信息
	mapStyleTemplates.mvMarkStake = new ol.style.Style({
		image: new ol.style.RegularShape(({
			fill: new ol.style.Fill({
				color: '#AAAAFF'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			radius: 5,
			points:6,
		}))
	});
	//中低压--电子标签信息
	mapStyleTemplates.mvElectronicLabel = new ol.style.Style({
		image: new ol.style.RegularShape(({
			fill: new ol.style.Fill({
				color: '#FF00FF'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			radius: 5,
			points:6,
		}))
	});
	//中低压--监测井信息
	mapStyleTemplates.mvMonitorWell = new ol.style.Style({
		image: new ol.style.RegularShape(({
			fill: new ol.style.Fill({
				color: '#409EFF'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			radius: 5,
			points:6,
		}))
	});
	//管道试压
	mapStyleTemplates.pipePressureTest = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#000079',
			width: 5
		})
	});
	//管道扫水
	mapStyleTemplates.pipeSweeping = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#003e3e',
			width: 5
		})
	});
	//进出站口 点
	mapStyleTemplates.entranceAndExit = new ol.style.Style({
		image: new ol.style.Circle({
			fill: new ol.style.Fill({
				color: '#82ff46'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 2
			}),
			radius: 5,
		})
	});
	//排凝管
	mapStyleTemplates.condensingTube = new ol.style.Style({
		image: new ol.style.Circle({
			fill: new ol.style.Fill({
				color: '#ff9224'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 2
			}),
			radius: 5,
		})
	});
	//预留甩头 reservedInterface #
	mapStyleTemplates.reservedInterface = new ol.style.Style({
		image: new ol.style.Circle({
			fill: new ol.style.Fill({
				color: '#FF00FF'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 2
			}),
			radius: 5,
		})
	});
	//封堵物
	mapStyleTemplates.stationClosure = new ol.style.Style({
		image: new ol.style.Circle({
			fill: new ol.style.Fill({
				color: '#008000'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 2
			}),
			radius: 5,
		})
	});
	//辅助阳极地床
	mapStyleTemplates.auxiliaryAnodeBed = new ol.style.Style({
		image: new ol.style.Circle({
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 2
			}),
			radius: 5,
		})
	});
	// 内腐蚀检测系统
	mapStyleTemplates.corrosionDetectionSys = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#008000',
			width: 5
		})
	});
	//放空立管
	mapStyleTemplates.ventStack = new ol.style.Style({
		image: new ol.style.Circle({
			fill: new ol.style.Fill({
				color: '##FFFF00'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 2
			}),
			radius: 5,
		})
	});
	// 阴保电源
	mapStyleTemplates.cpPowerSupply = new ol.style.Style({
		image: new ol.style.Circle({
			stroke: new ol.style.Stroke({
				color: '#FF00FF',
				width: 2
			}),
			radius: 5,
		})
	});
	//开挖穿越   ---管段信息
	mapStyleTemplates.crossExcavation = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#1100b7',
			width: 5
		})
	});
	//穿跨越---穿越信息
	mapStyleTemplates.crossAcross = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#fec880',
			width: 5
		})
	});
	//钻爆隧道穿越---跨越信息
	mapStyleTemplates.crossDrillingBlasting = style = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#f9ff00',
			width: 5
		})
	});
	//盾构隧道穿越---管沟信息
	mapStyleTemplates.crossShield = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#ccf068',
			width: 5
		})
	});
	//定向钻穿越---套管信息
	mapStyleTemplates.crossDrilling = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#00e0fc',
			width: 5
		})
	});
	//箱涵穿越
	mapStyleTemplates.crossBoxCulvert = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#ae20ff',
			width: 5
		})
	});
	//顶管穿越
	mapStyleTemplates.crossPipeJacking = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#fd22d8',
			width: 5
		})
	});
	/**
	 *管道焊口信息
	 * @type {ol.style.Style}
	 */
	mapStyleTemplates.constructionWeld = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#ff8845'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 4
		})
	});
	//焊口返修
	mapStyleTemplates.weldrework = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#ae01e7'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 4
		})
	});
	//中线测量  焊口数据
	mapStyleTemplates.weldmeasured = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#82ff46'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 4
		})
	});
	//中线测量  转角点数据
	mapStyleTemplates.weldmeasured01 = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#f56c6c'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 4
		})
	});
	//中线测量  穿越点数据
	mapStyleTemplates.weldmeasured02 = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#9f6cf5'
			}),
			stroke: new ol.style.Stroke({
				color: '#409EFF',
				width: 1
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 4
		})
	});
	//管道阴保
	//绝缘件
	mapStyleTemplates.cathodicIsolatingPiece = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#fe7774'
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 2
		})
	});
	//阴保电缆
	mapStyleTemplates.cathodicCableProtection = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#fcc982'
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 2
		})
	});
	//牺牲阳极
	mapStyleTemplates.cathodicSacrificeAnode = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#feff7f'
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 2
		})
	});
	//  绝缘接头保护器
	mapStyleTemplates.cathodicInsulatedJoint = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#fd22d8'
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 2
		})
	});
	//固态去耦合器
	mapStyleTemplates.cathodicSolidDecoupler = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#caee68'
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 2
		})
	});
	//测试桩
	mapStyleTemplates.cathodicTestStake = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#02defe'
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 2
		})
	});
	//极性排流器
	mapStyleTemplates.cathodicPolarityDrainage = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#80bbff'
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 2
		})
	});
	//辅助阳极地床
	mapStyleTemplates.cathodicAnodeBed = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#ae20ff'
			}),
			points: 4,
			radius: 5,
			angle: Math.PI / 2
		})
	});
	/**附属物**/
	//标志桩
	mapStyleTemplates.appendagesMarkStake = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#ae20ff'
			}),
			radius1: 8,
			radius2: 4,
			points: 3,
		})
	});
	//电子标签
	mapStyleTemplates.appendagesElectronicLabel = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#80bbff'
			}),
			radius1: 8,
			radius2: 4,
			points: 3,
		})
	});
	//手孔
	mapStyleTemplates.appendagesHandHole = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#02defe'
			}),
			radius1: 8,
			radius2: 4,
			points: 3,
		})
	});
	//地下障碍物
	mapStyleTemplates.appendagesObstacle = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#caee68'
			}),
			radius1: 8,
			radius2: 4,
			points: 3,
		})
	});
	//水工保护
	mapStyleTemplates.appendagesHydraulicProtection = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#feff7f',
			width: 5
		})
	});
	//套管
	mapStyleTemplates.appendagesCasingPipe = new ol.style.Style({
		stroke: new ol.style.Stroke({
			color: '#fd22d8',
			width: 5
		})
	});
	/**管道敷设  三角形**/
	//测量放线
	mapStyleTemplates.laySurveying = new ol.style.Style({
		image: new ol.style.RegularShape({
			fill: new ol.style.Fill({
				color: '#ae20ff'
			}),
			radius1: 8,
			radius2: 4,
			points: 5,
		})
	});
	//管沟开挖
	mapStyleTemplates.layPipeTrenchExcavation = function (feature) {
		var style = new ol.style.Style({
			stroke: new ol.style.Stroke({
				color: '#fcc982',
				width: 5
			})
		});
		return style;
	}
	//管沟开回填
	mapStyleTemplates.layPipeTrenchBackfill = function (feature) {
		var style = new ol.style.Style({
			stroke: new ol.style.Stroke({
				color: '#80bbff',
				width: 5
			})
		});
		return style;
	}

	//地貌恢复
	mapStyleTemplates.layLandRestoration = function (feature) {
		var style = new ol.style.Style({
			stroke: new ol.style.Stroke({
				color: '#feff7f',
				width: 5
			})
		});
		return style;
	}

	//保温
	mapStyleTemplates.layThermalInsulation = function (feature) {
		var style = new ol.style.Style({
			stroke: new ol.style.Stroke({
				color: '#82ff46',
				width: 5
			})
		});
		return style;
	}

	//中线桩连线
	mapStyleTemplates.medianStakePolyline = function (feature) {
		// console.log(feature);
		var colorArr = ['#CC0000', '#00FF00', '#CC33FF', '#CCFF00', '#FF66FF', '#FFFF00', '#00FFFF', '#00FFCC'];
		if (!styleManager.medianStakePolylineStyle) {
			styleManager.medianStakePolylineStyle = {};
		}
		var name = feature.get('pipeline_name');
		//console.log(name);
		if (!styleManager.medianStakePolylineStyle[name]) {
			var style = new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: colorArr[random(0, 8)],
					width: 5
				})
			});
			styleManager.medianStakePolylineStyle[name] = style;
		}
		return styleManager.medianStakePolylineStyle[name];
	};
	//标段连线
	mapStyleTemplates.tendersPolyline = function (feature) {
		// console.log(feature);
		var colorArr = ['#CC0000', '#00FF00', '#CC33FF', '#CCFF00', '#FF66FF', '#FFFF00', '#00FFFF', '#00FFCC'];
		if (!styleManager.tendersPolyline) {
			styleManager.tendersPolyline = {};
		}
		var name = feature.get('tenders_name');
		//console.log(name);
		if (!styleManager.tendersPolyline[name]) {
			var style = new ol.style.Style({
				stroke: new ol.style.Stroke({
					color: colorArr[random(0, 8)],
					width: 5
				})
			});
			styleManager.tendersPolyline[name] = style;
		}
		return styleManager.tendersPolyline[name];
	};

	mapStyleTemplates.myLineLabelStyle = new ol.style.Style({
		text: new ol.style.Text({
			font: '16px Calibri,sans-serif',
			placement: 'line',
			overflow: true,
			textBaseline: "top",
			//         offsetY:10,
			//         offsetX:10,
			fill: new ol.style.Fill({
				color: '#fff'
			}),
			stroke: new ol.style.Stroke({
				color: '#580087',
				width: 2
			})
		})
	});
    
    
    
    //----------------------完整性  内检测到数据 ----------------------
    
    mapStyleTemplates.demo_pd_neijiance_render_by_level = function(feature) {
        if(!styleManager.demo_pd_neijiance_render_by_level){
            styleManager.demo_pd_neijiance_render_by_level = {};
        }
        var name = feature.get('remarks');
        if(!styleManager.demo_pd_neijiance_render_by_level[name]){
            //创建openlayers样式
        	
        	var style = null;
        	
			switch (name){
				case '内检测过期未检':
					style = new ol.style.Style({
						fill: new ol.style.Fill({
				            color: 'rgba(255, 45, 45, 0.6)'
				        }),
				        stroke: new ol.style.Stroke({
				            color: '#ff2d2d',
				            width: 3
				        })
		            });
					break;
                case '内检测已检':
                	style = new ol.style.Style({
                		fill: new ol.style.Fill({
                            color: 'rgba(40, 255, 217, 0.6)'
                        }),
                        stroke: new ol.style.Stroke({
                            color: '#28ffd9',
                            width: 3
                        })
		            });
                 
                    break;
                case '内检测未检':
                	style = new ol.style.Style({
                		fill: new ol.style.Fill({
                            color: 'rgba(255，183, 25,0.6)'
                        }),
                        stroke: new ol.style.Stroke({
                            color: '#ffb719',
                            width: 3
                        })
		            });
                    break;
				default:;
			}
            
            //保存已经有的属性字段样式
            styleManager.demo_pd_neijiance_render_by_level[name] = style;
        }
        return styleManager.demo_pd_neijiance_render_by_level[name]
    };
    
    //--------------------- 水源地 -----------------------
    
    
    
    //<<<-------------------自定义样式结束----------------ss
    return mapStyleTemplates; //返回样式模版对象
});


function random(min, max) {
    var Range = max - min;
    var Rand = Math.random();
    var num = min + Math.round(Rand * Range); //四舍五入
    // console.log(num);
    return num;
}