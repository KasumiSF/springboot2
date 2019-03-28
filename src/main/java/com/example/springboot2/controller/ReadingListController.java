package com.example.springboot2.controller;


import com.example.springboot2.component.AmazonProperties;
import com.example.springboot2.entity.Book;
import com.example.springboot2.server.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**第一个注解是将其注册为一个bean
 *
 * ReadingListController上加了@ConfigurationProperties注解，这说明该
 * Bean的属性应该是（通过setter方法）从配置属性值注入的。说得更具体一点，prefix属性说明
 * ReadingListController应该注入带amazon前缀的属性。
 *
 *  属性注入.什么原理?
 *
 *
 *  我们指定ReadingListController的属性应该从带amazon前缀的配置属性中
 * 进行注入。ReadingListController只有一个setter方法，就是设置associateId属性用的setter
 * 方法。因此，设置Amazon Associate ID唯一要做的就是添加amazon.associateId属性，把它加
 * 入支持的任一属性源位置里即可
 * 这里是在application.properties 加入了 amazon.associateId=habuma-20
 *
 * 这里之所以使用属性注入，是因为要在html里面用到这个属性，而这个属性并不会改变，但是又要避免硬编码
 * 将其 属性 注入到Controller中，并在配置文件中设置他的值，集中管理
 *
 * 从技术上来说，单写入@ConfigurationProperties是不会生效的，除非加入@EnableConfigurationProperties注解，
 * 但是Spring Boot自动配置后面的全部配置类都已经加上了@EnableConfigurationProperties注解。
 * 因此，除非你完全不使用自动配置（那怎么可能？）。
 *
 * amazon.associateId这个属性和
 * amazon.associate_id以及amazon.associate-id都是等价的。用你习惯的命名规则就好。
 *
 * 但是一个Controller里注入一个属性，其他方法还都用不到，不是很合适，不如创建一个配置Bean——AmazonProperties
 */
@Controller
@RequestMapping("/")
//@ConfigurationProperties(prefix="amazon")
public class ReadingListController {

    private AmazonProperties  amazonProperties;

    private ReadingListRepository readingListRepository;


    //注入（到底注入的是什么？Bean？对象？属性？）
    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository,
                                 AmazonProperties amazonProperties){
        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;

    }



    @RequestMapping(value = "/{reader}",method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader,
                               Model model){
        //获取这个传入的读者的Book集合，是通过控制器的构造器注入的
        List<Book> readingList = readingListRepository.findByReader(reader);
        //如果不为null,就加入到模型里面,键是books,最后返回readingList作为呈现模型的视图逻辑名称
        if (readingList != null){
            model.addAttribute("books",readingList);
            model.addAttribute("reader", reader);
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
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
//        return "redirect:/{reader}";
        return "redirect:/";
    }

}
