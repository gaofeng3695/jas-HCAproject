const X = {
  /**
   * 加载矢量数据的GeoJSON
   * @param url
   * 打开 http://1.119.155.134:10120/geoserver/web/wicket/bookmarkable/org.geoserver.web.demo.MapPreviewPage?4
   * 在你需要加载数据的行右侧点击Select one,选择GeoJSON,复制打开的链接,去除其中的“&maxFeatures=50”.
   * @param callback json => {} 返回JSON对象
   */
  getGeoJSON(url, callback) {
    fetch(url).then(response => {
      return response.json();
    }).then(json => {
      //删除不支持的属性
      //delete json.crs;
      // if(json.crs.properties.name.indexOf("4490") >= 0){
      //     json.crs.properties.name = json.crs.properties.name.replace("4490","4326")
      // }
      callback(json);
    }).catch(error => {
      console.error('获取矢量数据出错!');
    });
  },

  /**
   * 获取相机位置
   * @param camera 相机
   * @returns {Readonly<{destination: {longitude: *, latitude: *, height: *}, orientation: {heading: *, pitch: *, roll: *, direction: any, up: any}}>}
   * 设置位置方法见 cesium/Build/Documentation/Camera.html?classFilter=camera#setView
   */
  getCameraPosition(camera) {
    let cartographicPosition = Cesium.Ellipsoid.WGS84.cartesianToCartographic(camera._positionWC);
    let longitude = Cesium.Math.toDegrees(cartographicPosition.longitude);
    let latitude = Cesium.Math.toDegrees(cartographicPosition.latitude);
    let height = cartographicPosition.height;
    console.debug('坐标:', longitude, ',', latitude, ',', height);
    let heading = camera.heading;
    let pitch = camera.pitch;
    let roll = camera.roll;
    console.debug(
        '取向( 弧度,转角度Cesium.Math.toDegrees() )-heading:',
        heading,
        ',pitch:',
        pitch,
        ',roll:',
        roll
    );
    console.debug(
        '取向( 世界坐标 )-direction:',
        JSON.stringify(camera.directionWC),
        ',up:',
        JSON.stringify(camera.upWC)
    );
    return Object.freeze({
      destination: {
        longitude: longitude,
        latitude: latitude,
        height: height
      },
      orientation: {
        heading: heading,
        pitch: pitch,
        roll: roll,
        direction: JSON.parse(
            JSON.stringify(camera.directionWC)
        ),
        up: JSON.parse(
            JSON.stringify(camera.upWC)
        )
      }
    });
  }
};
