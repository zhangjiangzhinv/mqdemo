package cn.red.com.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)//表示使用Spring Test组件进行单元测试
@ContextConfiguration(locations={"classpath:config/spring/applicationContext.xml","classpath:config/rabbitmq/spring-rabbitmq.xml"})//指定Bean的配置文件信息
@WebAppConfiguration//真实的启一个web服务，调用Controller的Rest API，待单元测试跑完之后再将web服务停掉
public class PostCardControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setUp() throws Exception {		
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testGetPostCard(){
		try {
			//perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
			//andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台；
			 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/postcard/{id}","123")).andDo(MockMvcResultHandlers.print()).andReturn();
			 System.out.println(result.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
