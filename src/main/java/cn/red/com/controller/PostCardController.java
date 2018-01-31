package cn.red.com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.red.com.domain.PostCard;
import cn.red.com.rabbitmq.springjavaconfig.DelayProducer;
import cn.red.com.rabbitmq.springxmlconfig.MyProducer;

@RestController
@RequestMapping(value="/postcard")
public class PostCardController{
	@Autowired
	private MyProducer myProducer;
	
	@Autowired
	private DelayProducer delayProducer;
	
	@GetMapping(value="/{id}")
	public PostCard getPostCard(@PathVariable String id){
		PostCard postCard = new PostCard();
		postCard.setFromName("HongHong");
		postCard.setToName("ChaoChao");
		return postCard;
	}
	
	@GetMapping(value="/send/now")
	public String sendPostCard(){
		myProducer.sendNow();
		return "finished";
	}

	@GetMapping(value="/send/delay")
	public String sendPostCardDelay() throws Exception{
		delayProducer.sendDelay();
		return "finished";
	}
	
}
