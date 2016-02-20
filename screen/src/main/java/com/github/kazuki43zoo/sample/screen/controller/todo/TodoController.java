package com.github.kazuki43zoo.sample.screen.controller.todo;

import com.github.kazuki43zoo.sample.component.tracking.TrackingId;
import com.github.kazuki43zoo.sample.domain.model.Todo;
import com.github.kazuki43zoo.sample.domain.service.TodoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/todos")
@Controller
public class TodoController {

    @Autowired
    TodoService todoService;

    @ModelAttribute
    public TodoForm setUpForm() {
        return new TodoForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Principal principal) {
        List<Todo> todos = todoService.findAllByUsername(extractUsername(principal));
        model.addAttribute("todos", todos);
        return "todo/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Validated TodoForm todoForm, BindingResult bindingResult, Model model,
                         Principal principal, @TrackingId String trackingId) {
        if (bindingResult.hasErrors()) {
            return list(model, principal);
        }
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoForm, todo);
        todo.setTrackingId(trackingId);
        todoService.create(todo, extractUsername(principal));
        return "redirect:/todos";
    }

    @RequestMapping(path = "{todoId}", method = RequestMethod.GET)
    public String get(@PathVariable String todoId, Model model) {
        Todo todo = todoService.findOne(todoId);
        model.addAttribute(todo);
        return "todo/detail";
    }

    @RequestMapping(path = "{todoId}", method = RequestMethod.POST, params = "finish")
    public String finish(@PathVariable String todoId) {
        todoService.finish(todoId);
        return "redirect:/todos";
    }

    @RequestMapping(path = "{todoId}", method = RequestMethod.POST, params = "delete")
    public String delete(@PathVariable String todoId) {
        todoService.delete(todoId);
        return "redirect:/todos";
    }

    private String extractUsername(Principal principal) {
        return Optional.ofNullable(principal).map(Principal::getName).orElse("anonymousUser");
    }

}
