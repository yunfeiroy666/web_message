package com.wyf.controller;

import com.wyf.model.HostHolder;
import com.wyf.model.Message;
import com.wyf.model.User;
import com.wyf.model.ViewObject;
import com.wyf.service.ContactsService;
import com.wyf.service.MessageService;
import com.wyf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by w7397 on 2017/3/29.
 */
@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    ContactsService contactsService;

    @Autowired
    MessageService messageService;

    private List<ViewObject> getContacts(int userId) {
        List<Integer> contactsIds = contactsService.getContactsIds(userId);
        List<ViewObject> vos = new ArrayList<>();
        for (int id : contactsIds) {
            String conversationId = id < userId ? String.format("%d_%d", id, userId) : String.format("%d_%d", userId, id);
            ViewObject vo = new ViewObject();
            User contacts = userService.getUser(id);
            vo.set("contacts", contacts);
            vo.set("userId", userId);
            vo.set("conversationId", conversationId);
            vo.set("unreadCount", messageService.getUnreadCount(userId, conversationId));
            //vo.set("user", userService.getUser(news.getUserId()));

//            if (localUserId != 0) {
//                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
//
//            } else {
//                vo.set("like", 0);
//            }
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        model.addAttribute("vos", getContacts(localUserId));
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId,
                            @RequestParam(value = "pop", defaultValue = "0") int pop) {
//        model.addAttribute("vos", getUser());
//        model.addAttribute("pop", pop);
        return "home";
    }
}
