package com.example.springboot2.controller;


import com.example.springboot2.entity.Book;
import com.example.springboot2.server.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//第一个注解是将其注册为一个bean
@Controller
@RequestMapping("/")
public class ReadingListController {

    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository){
        this.readingListRepository = readingListRepository;
    }

    @RequestMapping(value = "/{reader}",method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader,
                               Model model){
        //获取这个传入的读者的Book集合，是通过控制器的构造器注入的
        List<Book> readingList = readingListRepository.findByReader(reader);
        //如果不为null,就加入到模型里面,键是books,最后返回readingList作为呈现模型的视图逻辑名称
        if (readingList != null){
            model.addAttribute("books",readingList);
        }
        return "readingList";
    }

    @RequestMapping(value = "/{reader}",method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader,Book book) {
        //将请求正文里的数据绑定到一个Book对象上(谁有什么书)
        //book的reader属性设置为读者的姓名
        book.setReader(reader);
        //通过仓库的save方法保存修改后的Book对象
        readingListRepository.save(book);
        //最后重定向到/{reader},让控制器的另一个方法处理该请求
        return "redirect:/{reader}";
    }



}
