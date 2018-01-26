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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:config/spring/applicationContext.xml","classpath:config/rabbitmq/spring-rabbitmq.xml"})
@WebAppConfiguration
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
			//perform��ִ��һ��RequestBuilder���󣬻��Զ�ִ��SpringMVC�����̲�ӳ�䵽��Ӧ�Ŀ�����ִ�д���
			//andDo�����ResultHandler������������������ʱ��ӡ���������̨��
			 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/postcard/{id}","123")).andDo(MockMvcResultHandlers.print()).andReturn();
			 System.out.println(result.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
