package cn.red.com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cn.red.com.domain.PostCard;

@RestController
@RequestMapping(value="/postcard")
public class PostCardController implements Controller{

	@GetMapping(value="/{id}")
	public PostCard getPostCard(@PathVariable String id){
		PostCard postCard = new PostCard();
		postCard.setFromName("HongHong");
		postCard.setToName("ChaoChao");
		return postCard;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		return null;
	}
}
