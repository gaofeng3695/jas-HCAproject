package cn.jasgroup.hcas.analysis; 

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.geometry.Point;
import cn.jasgroup.gis.util.StringUtil;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.UUID;

/** 
* BuildingLocationMap Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 21, 2019</pre> 
* @version 1.0 
*/ 
public class BuildingLocationMapTest { 


    BuildingLocationMap map = new BuildingLocationMap();
    String uuid = UUID.randomUUID().toString() ;

@Before
public void before() throws Exception {
    Feature[] features = new Feature[2];
    Feature feature1 = new Feature();
    feature1.setGeometry(new Point(new Double[]{112d,21d},4490));

    Feature feature2 = new Feature();
    feature2.setGeometry(new Point(new Double[]{132d,41d},4490));
    features[0] = feature1;
    features[1] = feature2;
    map.putFeatures(uuid,features);
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: get(String oid) 
* 
*/ 
@Test
public void testGet() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: put(String oid, Feature[] points) 
* 
*/ 
@Test
public void testPut() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: merge(String from, String to) 
* 
*/ 
@Test
public void testMerge() throws Exception { 
//TODO: Test goes here...
    String uuid2 = "test" ;
    map.merge(uuid ,uuid2);
    StringUtil.print(map.size(uuid2));
} 

/** 
* 
* Method: contains(String key) 
* 
*/ 
@Test
public void testContains() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: putFeatures(String oid, Feature[] buildings) 
* 
*/ 
@Test
public void testPutFeatures() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: size(String oid) 
* 
*/ 
@Test
public void testSize() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getMap() 
* 
*/ 
@Test
public void testGetMap() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: setMap(Map<String, Feature[]> map) 
* 
*/ 
@Test
public void testSetMap() throws Exception { 
//TODO: Test goes here... 
} 


} 
