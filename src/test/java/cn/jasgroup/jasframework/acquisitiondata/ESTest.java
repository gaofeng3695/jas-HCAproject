package cn.jasgroup.jasframework.acquisitiondata;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

public class ESTest {
	
	private TransportClient client;

	@Before
	public void before(){
		 try {
			TransportAddress node = new TransportAddress(InetAddress.getByName("192.168.50.112"), 9300);
//			TransportAddress node = new TransportAddress(InetAddress.getByName("139.219.8.34"), 9300);

			    Settings settings = Settings.builder()
			            .put("cluster.name", "jasframework")
//	                .put("client.transport.sniff", true)
			            .build();

			    client = new PreBuiltTransportClient(settings);
			    client.addTransportAddress(node);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
    public void testInfo() {
        List<DiscoveryNode> nodes = client.connectedNodes();
        for (DiscoveryNode node : nodes) {
            System.out.println(node.getHostAddress());
        }
    }
//	@Test
	public void testSerch(){
		try {
			SearchResponse response = client.prepareSearch()
			        .execute().actionGet();
			System.out.println(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public boolean exists(String index){ 
        IndicesAdminClient adminClient = client.admin().indices();    
        IndicesExistsRequest request = new IndicesExistsRequest(index);  
        IndicesExistsResponse response = adminClient.exists(request).actionGet();  
        if (response.isExists()) {  
          return true;
        }
        return false;
        
    }  
	
//	@Test
	 public void DoMapping() {
		String index = "jas-doc1";
		String type="doc";
        if(exists(index)){
            System.out.println("jas-doc1索引已经存在！");
//            log.info("jas-doc索引已经存在！");
            return;
        }
        try {
			CreateIndexRequestBuilder cib=client.admin()  
	                .indices().prepareCreate(index);
			/*Map<String,Object> fieldsMap = new HashMap();
			Map<String,Object> fields = new LinkedHashMap();
			fields.put("type", "keyword");
			fields.put("ignore_above", 256);
			fieldsMap.put("keyword", fields);*/
			XContentBuilder mapping = XContentFactory.jsonBuilder()
					.startObject()
					.field("dynamic",false)
					.startObject("properties")
					
					.startObject("eventid")
					.field("type","keyword")
					.endObject()
					
					.startObject("folderid")
					.field("type","keyword")
					.endObject()
					
					.startObject("fileno")
					.field("type","keyword")
					.endObject()
					
					.startObject("filename")
					.field("type","text")
					.field("analyzer","ik_max_word")
					.field("search_analyzer","ik_max_word")
					.startObject("fields").startObject("keyword").field("type","keyword").field("ignore_above",256).endObject().endObject()
					.endObject()

					.startObject("filetype")
					.field("type","keyword")
					.field("index",false)
					.endObject()
					
					.startObject("filelocation")
					.field("type","keyword")
					.endObject()
					
					.startObject("keyword")
					.field("type","text")
					.field("analyzer","ik_max_word")
					.field("search_analyzer","ik_max_word")
//					.field("fields", fieldsMap)
					.startObject("fields").startObject("keyword").field("type","keyword").field("ignore_above",256).endObject().endObject()
					.endObject()
					
					.startObject("summary")
					.field("type","text")
					.field("analyzer","ik_max_word")
					.field("search_analyzer","ik_max_word")
//					.field("fields", fieldsMap)
					.startObject("fields").startObject("keyword").field("type","keyword").field("ignore_above",256).endObject().endObject()
					.endObject()
					
					.startObject("filesize")
					.field("type","long")
					.field("index",false)
					.endObject()
					
					.startObject("uploadtime")
					.field("type","date")
					.field("format","yyyy-MM-dd HH:mm:ss||strict_date_optional_time||epoch_millis")
					.endObject()

					.startObject("remark")
					.field("type","text")
					.field("analyzer","ik_max_word")
					.field("search_analyzer","ik_max_word")
//					.field("fields", fieldsMap)
					.startObject("fields").startObject("keyword").field("type","keyword").field("ignore_above",256).endObject().endObject()
					.endObject()
					
					.startObject("contents")
					.field("type","text")
					.field("analyzer","ik_max_word")
					.field("search_analyzer","ik_max_word")
					.endObject()
					
					.startObject("description")
					.field("type","text")
					.field("analyzer","ik_max_word")
					.field("search_analyzer","ik_max_word")
//					.field("fields", fieldsMap)
					.startObject("fields").startObject("keyword").field("type","keyword").field("ignore_above",256).endObject().endObject()
					.endObject()
					
					.startObject("createuser")
					.field("type","keyword")
					.endObject()
					
					.startObject("createusername")
					.field("type","keyword")
					.endObject()
					
					.startObject("createtime")
					.field("type","date")
					.field("format","yyyy-MM-dd HH:mm:ss||strict_date_optional_time||epoch_millis")
					.endObject()
					
					.startObject("updateuser")
					.field("type","keyword")
					.endObject()
					
					.startObject("updateusername")
					.field("type","keyword")
					.endObject()
					
					.startObject("updatetime")
					.field("type","date")
					.field("format","yyyy-MM-dd HH:mm:ss||strict_date_optional_time||epoch_millis")
					.endObject()
					
					.startObject("deleteflag")
					.field("type","keyword")
					.endObject()
					
					.startObject("auditstate")
					.field("type","integer")
					.endObject()
					
					.endObject()
					.endObject();
			cib.addMapping(type, mapping);  
	        cib.execute().actionGet();  
			System.out.println("创建ElasticSearch Mapping完成！！！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        log.info("创建ElasticSearch Mapping完成！！！");
    }
}
