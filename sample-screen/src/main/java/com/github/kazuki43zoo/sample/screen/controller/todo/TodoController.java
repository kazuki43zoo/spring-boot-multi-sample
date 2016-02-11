package com.github.kazuki43zoo.sample.screen.controller.todo;

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

import java.util.Collection;

@RequestMapping("/todos")
@Controller
public class TodoController {

    @Autowired
    TodoService todoService;

    @ModelAttribute
    public TodoForm setUpForm() {
        TodoForm form = new TodoForm();
        return form;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        Collection<Todo> todos = todoService.findAll();
        model.addAttribute("todos", todos);
        return "todo/list";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String create(@Validated TodoForm todoForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return list(model);
        }

        Todo todo = new Todo();
        BeanUtils.copyProperties(todoForm, todo);

        todoService.create(todo);

        return "redirect:/todos";
    }

    @RequestMapping(path = "{todoId}", method = RequestMethod.POST, params = "finish")
    public String finish(@PathVariable String todoId, Model model) {

        todoService.finish(todoId);

        return "redirect:/todos";
    }

    @RequestMapping(path = "{todoId}", method = RequestMethod.POST, params = "delete")
    public String delete(@PathVariable String todoId, Model model) {

        todoService.delete(todoId);

        return "redirect:/todos";
    }

}
