package kr.ac.kopo.bookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.kopo.bookshop.model.Book;
import kr.ac.kopo.bookshop.pager.Pager;
import kr.ac.kopo.bookshop.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {
	final String path = "book/";
	
	@Autowired
	BookService service;
	
	@GetMapping("/dummy")
	String dummy() {
		service.dummy();
		return "redirect:list";
	}
	
	@GetMapping("/init")
	String init() {
		service.init();
		return "redirect:list";
	}
	
	@GetMapping("/list") // /book/list로 getmapping 하는게 아니라, controller 자체에서 /book까지는 받아줌 => 중복되니까
	String list(Model model, Pager pager) {
		List<Book> list = service.list(pager);
		
		model.addAttribute("list", list);
		
		return path + "list"; //book/list.jsp, customer/list.jsp list 반복되므로, path 경로를 따로 변수 지정해줌
	}	
	
	@GetMapping("/add")
	String add() {
		return path + "add";	
	}
	
	@PostMapping("/add")
	String add(Book item) {
		service.add(item);
		
		return "redirect:list";
	}
	
	@GetMapping("/update/{bookid}") //path variable => 책번호로 주소 access
	String update(@PathVariable Long bookid, Model model) { //주소에 있는 값({bookid}을 이용해서 변수 bookid에 값이 들어옴
		Book item = service.item(bookid);
		
		model.addAttribute("item", item);
		
		return path + "update";
	}
	
	@PostMapping("/update/{bookid}")
	String update(@PathVariable Long bookid, Book item) {
		item.setBookid(bookid);
		
		service.update(item);
		
		return "redirect:../list";
	}
	
	@GetMapping("/delete/{bookid}")
	String delete(@PathVariable Long bookid) {
		service.delete(bookid);
		
		return "redirect:../list";
	}
	
	
}
