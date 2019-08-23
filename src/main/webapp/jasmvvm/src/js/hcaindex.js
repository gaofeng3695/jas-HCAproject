Vue.component('loading-bar', LoadingBar);

window.app = new Vue({
	el: '#app',
	data: function () {
		return {
			username: localStorage.getItem('user') && JSON.parse(localStorage.getItem('user')).userName,
			userunit: localStorage.getItem('user') && JSON.parse(localStorage.getItem('user')).unitName,
			userImg: './src/images/enterlogo.png',
			progress: 0,
			error: false,
			direction: 'right',
			panelShowed: true,
			isExpend: true,
			menuWith: 200,
			menusOpened: ['P-HCA-001'],
			currentTap: 'P-HCA-001',
			tabs: [], // 打开的标签页
			items: [], //菜单数组
			aMenu: [],//初始化的菜单数组
			isMapInited: false, //地图未初始化
			isTigger: false,
			menuNumber: []//表示菜单后面的树
		}
	},
	computed: {
		menuStyle: function () {
			return {
				width: this.isCollapse ? '64px' : (this.menuWith || 200) + 'px'
			}
		},
		isCollapse: function () {
			return !this.isExpend;
		},
	},
	watch: {
		tabs: function (newval, oldval) {
			if (newval.length - oldval.length >= 0) {
				this.goProgess();
			}
		}
	},
	created: function () {
		var that = this;
		// this.initJasMap();
//		if (!that.isMapInited) {
//			that.isMapInited = true;
//			setTimeout(function () {
//				that.initJasMap();
//			}, 300);
//		}
		that.statuschanged(!that.isMapInited);
		
	},
	mounted: function () {
		// this.$refs.resizer.panelShowed = false;
		this.goProgess();
		this._queryMenuData();
		this._listenWindowClose();
		this._setWindowResizeEventToCloseMenu();
		this._requestLoginInfo();
		this.requestFile();
		this.statuschanged(true);
	},
	methods: {
		_queryMenuData: function () {
			var that = this; // 获取左侧菜单
			var url = jasTools.base.rootPath + '/jasframework/privilege/privilege/getAllUserFuntion.do';
			$.ajax({
				url: url + "?token=" + localStorage.getItem('token'),
				type: 'get',
				async: true,
				data: {
					"menutype": "0",
					"appId": "402894a152681ba30152681e8b320003",
					"language": "zh_CN"
				},
				success: function (data, xhr, param) {
					if (typeof data === 'object' && data.length > 0) {
						that.aMenu = data;
						that.items = that._formatMenus(data);//首次组装 是否需要去调用请求数据接口
						that.tabs = that._createTabsArr(that.menusOpened, that.items);
					}
				}
			});
		},
		_formatMenus: function (aMenu) {//进行菜单的组装
			var that = this;
			var _aMenu = JSON.parse(JSON.stringify(aMenu));
			var switcher = function (arr) {
				if (typeof arr === "object") {
					arr.forEach(function (item) {
						item.index = item.id || '';
						item.icon = item.icon || 'fa fa-bookmark'; //fa-bars fa-bookmark
						item.title = item.text;
						if (item.attributes && item.attributes.URL) {
							item.link = jasTools.base.rootPath + '/' + item.attributes.URL;
						}
						item.subs = item.children;
						if (that.isTigger) {
							item.value = that._ifExistCode(item.id);
						}
						if (item.subs) {
							switcher(item.subs);
						}
					})
				}

			};
			switcher(_aMenu);
			return _aMenu;

		},
		_ifExistCode: function (id) {
			var that = this;
			var number = 0;
			for (var i = 0; i < that.menuNumber.length; i++) {
				if (id == that.menuNumber[i].privilege_code) {
					number = that.menuNumber[i].number;
					break;
				}
			}
			return number;
		},
		selectMenu: function (index, indexPath) {
			var that = this;
			var newTap = '';
			this.tabs.forEach(function (item) { //循环打开的标签页，判断选中的菜单是否带开过
				if (item.name === index) {
					newTap = index;
				}
			});
			if (!newTap) {
				var aTab = this._createTabsArr([index], this.items);
				this.tabs.push(aTab[0]);
			}
			this.currentTap = index;
		},
		removeTab: function (targetName) {
			var tabs = this.tabs;
			var activeName = this.currentTap;
			//如果当前显示的tab页被删除，更改当前显示的tab页为下一页
			if (activeName === targetName) {
				tabs.forEach(function (tab, index) {
					if (tab.name === targetName) {
						var nextTab = tabs[index + 1] || tabs[index - 1];
						if (nextTab) {
							activeName = nextTab.name;
						}
					}
				});
			}
			//设定当前显示的tab页
			this.currentTap = activeName;
			//在原数组中删除这个要被删除的tab
			this.tabs = tabs.filter(function (tab) {
				return tab.name !== targetName;
			})
		},
		_getMenuInfoByIndex: function (index, aMenu) {
			var _icon = '';
			var _title = '';
			var _closable = true;
			var _link = '';
			var isGetResult = false;

			var getTitle = function (index, aMenu) {
				for (var i = 0; i < aMenu.length; i++) {
					var item = aMenu[i];

					if (item.subs) { //如果有子集递归处理

						if (!isGetResult) {
							_icon = item.icon;
							_title = item.title;
							getTitle(index, item.subs);
						}
					} else {
						if (index === item.index) {
							isGetResult = true;
							_icon = item.icon;
							_title = item.title;
							_link = item.link;
							_closable = item.closable !== false ? true : false;
							return;
						}
					}
				}
			};
			getTitle(index, aMenu);
			return {
				icon: _icon,
				title: _title,
				closable: _closable,
				link: _link || '',
			}
		},
		_setWindowResizeEventToCloseMenu: function () {
			var that = this;
			window.addEventListener('resize', function () {
				if (document.body.clientWidth < 900 && that.isExpend) {
					that.isExpend = false;
				}
			});
		},
		_listenWindowClose: function () {
			var that = this;
			$(window).bind("beforeunload", function (e) {
				var e = window.event || e;
				console.log(e)
				// e.returnValue = ("确定离开当前页面吗？");
				//that._loginOut();
			});
		},
		_loginOut: function () {
			var url = jasTools.base.rootPath + '/jasframework/login/logout.do';

			jasTools.ajax.get(url, {}, function (data) {
				localStorage.removeItem('token');
				localStorage.removeItem('user');
				location.href = './login.html';
			}, function () {
				localStorage.removeItem('token');
				localStorage.removeItem('user');
				location.href = './login.html';
			});
		},
		_goPage: function () {
			//location.href = './pages/row-onepage/total/total.html';
		},
		_createTabsArr: function (aIndex, aMenu) {
			var that = this;

			return aIndex.map(function (index) {
				var info = that._getMenuInfoByIndex(index, aMenu);
				return {
					title: info.title,
					name: index,
					link: info.link,
					icon: info.icon,
					closable: info.closable
				}
			});
		},
		handleCommand: function (command) {
			if (command === 'loginout') {
				this._loginOut();
			} else if (command === 'fullscreen') {
				this._goFullscreen();
			} else if (command === 'resetPassword') {
				this._resetPassword();
			}
		},
		_goFullscreen: function () {
			if (screenfull.enabled) {
				// screenfull.toggle($('.tabswrapper .el-tabs__content')[0]);
				screenfull.toggle();
			} else {
				window.top.Vue.prototype.$message({
					message: '您的浏览器不支持全屏',
					type: 'error'
				});
				// Ignore or do something else
			}
		},
		goProgess: function () {
			var that = this;
			that.progress = 10;
			clearInterval(that.timer);
			this.timer = setInterval(function () {
				that.progress += 5;
				if (that.progress >= 100) {
					clearInterval(that.timer);
				}
			}, 10);


		},
		errorDone: function () {
			this.error = false
		},
		progressDone: function () {
			this.progress = 0
		},
		_resetPassword: function () {
			var that = this;
			jasTools.dialog.show({
				title: '修改密码',
				width: '530px',
				height: '530px',
				src: 'resetword.html',
				cbForClose: function (param) {
					if (param === 1) {
						that._loginOut();
					}
				}
			});
		},
		_requestLoginInfo: function () {
			var that = this;
			var url = jasTools.base.rootPath + '/jasframework/log/login/getUserStatisticsInfo.do';

			jasTools.ajax.get(url, {}, function (data) {
				var obj = data.data;
				that.$notify({
					type: 'success',
					title: '登录成功',
					position: 'bottom-right',
					dangerouslyUseHTMLString: true,
					message: [
						'<div style="font-size:12px;">',
						'	<div><span style="color:#409EFF;font-weight: 700;">' + obj.userName + '</span>,您好！</div>',
						'	<div>登录次数：<span style="color:#409EFF;">' + obj.loginCount + '</span></div>',
						'	<div>累计在线时长：<span style="color:#409EFF;">' + obj.totalLoginDate + '</span></div>',
						'	<div>上次登录IP：<span style="color:#409EFF;">' + obj.clientIp + '</span></div>',
						'	<div>上次登录时间：<span style="color:#409EFF;">' + obj.lastLoginDate + '</span></div>',
						'</div>'
					].join('')
				});

			}, function () {

			});


		},
		paneresize: function () {
			this.jasMap && this.jasMap.resizeMap();
		},
		statuschanged: function (val) {
			var that = this;
			if (val) {
				if (!that.isMapInited) {
					that.isMapInited = true;
					setTimeout(function () {
						that.initJasMap();
					}, 300);
				}
			}else{
				this.panelShowed=false;
			}
		},
		initJasMap: function (fn) {
			/*var that = this;
			var onCenterStakeLayerClicked = function (e) {
				//业务逻辑
			};
			this.jasMap = new JasMap({
				layersVisible: {
					daq_median_stake: true,
				},
				appConfig: './pages/map/config.json',
				onMapLoaded: function (e) {
					fn && fn();
					//that.addMapListener();

				},
				onError: function (e) {
					top.Vue.prototype.$message({
						message: e.data.message,
						type: 'error'
					});
				},
				onOptionalLayersLoaded: function () {
					that.addMapListener();
				},
				onLayerAdded: function (e) {
					var layerId = e.data.layerId;
					var layerArrs = ["v_daq_construction_weld", "v_daq_mv_pipe_node", "v_daq_mv_pipe_section", "v_daq_mv_across_info", "v_daq_mv_stride_across_info", "v_daq_mv_pipe_trench_protect", "v_daq_mv_bushing_info", "v_daq_mv_mark_stake", "v_daq_mv_valve_info", "v_daq_mv_electronic_label", "v_daq_mv_monitor_well",'v_daq_cross_excavation','v_daq_cross_pipe_jacking','v_daq_cross_box_culvert','v_daq_cross_drilling','v_daq_cross_shield','v_daq_cross_drilling_blasting','v_daq_cross_across'];
					if (layerArrs.indexOf(layerId) > -1) { //焊口信息的时候
						//添加单个图层的点击事件
						this.addLayerClickEventListener(layerId, showInfo);
					}
				}
			});*/
		},

		locate: function (id, tableCode) {
			var that = this;
			if (!this.$refs.resizer.panelShowed) {
				this.$refs.resizer.panelShowed = true;
				//setTimeout(function () {
				if (!that.isMapInited) {
					that.isMapInited = true;
					that.initJasMap();
					top.Vue.prototype.$message({
						message: '正在初始化地图，请稍后定位',
						type: 'warning'
					});
				} else {
					that.jasMap.flashGraphic(id, tableCode);
				}
				//}, 300);
			} else {
				this.jasMap.flashGraphic(id, tableCode);
			}
		},
		//获取附件文件
		requestFile: function () {
			var that = this;
			var url = jasTools.base.rootPath + '/attachment/getInfo.do';
			var sBizId = JSON.parse(localStorage.getItem('user')).oid;
			jasTools.ajax.get(url, {
				businessType: 'photo',
				businessId: sBizId,
			}, function (data) {
				var arr = data.rows;
				if (data.rows.length > 0) {
					that.userImg = jasTools.base.rootPath + '/attachment/app/getImageBySize.do?oid=' + data.rows[0].oid + "&token=" + localStorage.getItem("token");
				}
			});
		},
		_goMap:function(){//打开地图展示
			var that=this;
			if (!that.$refs.resizer.panelShowed) {
				that.$refs.resizer.panelShowed = true;
				this.panelShowed = true;
				if (!that.isMapInited) {
					that.isMapInited = true;
					that.initJasMap();
				}
			}else{
				this.panelShowed = false;
				that.$refs.resizer.panelShowed = false;	
			}
		}
	},
});