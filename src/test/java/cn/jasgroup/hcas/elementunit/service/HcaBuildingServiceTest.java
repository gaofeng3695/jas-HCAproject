package cn.jasgroup.hcas.elementunit.service; 

import cn.jasgroup.hcas.elementunit.dao.entity.HcaBuildings;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* HcaBuildingService Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 22, 2019</pre> 
* @version 1.0 
*/ 
public class HcaBuildingServiceTest { 

    HcaBuildingService hcaBuildingService = new HcaBuildingService();
@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: save(HcaBuildings hcaBuildings) 
* 
*/ 
@Test
public void testSave() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: update(HcaBuildings hcaBuildings) 
* 
*/ 
@Test
public void testUpdate() throws Exception { 
//TODO: Test goes here...
    HcaBuildings hcaBuildings = new HcaBuildings();
    hcaBuildings.setOid("adc");
    hcaBuildings.setObjectId(1);
    hcaBuildings.setGeometry("(113 34.324 45,113.34 34.324 45,113.54 34.524 45,113 34.324 45),(112 34.324 45,113.34 34.324 45,113.54 34.524 45,112 34.324 45)");
    hcaBuildingService.update(hcaBuildings);

} 

/** 
* 
* Method: add(HcaBuildings hcaBuildings) 
* 
*/ 
@Test
public void testAdd() throws Exception { 
//TODO: Test goes here...
    HcaBuildings hcaBuildings = new HcaBuildings();
    hcaBuildings.setGeometry("(113 34.324 45,113.34 34.324 45,113.54 34.524 45,113 34.324 45),(112 34.324 45,113.34 34.324 45,113.54 34.524 45,112 34.324 45)");
    hcaBuildingService.add(hcaBuildings);
} 


} 
