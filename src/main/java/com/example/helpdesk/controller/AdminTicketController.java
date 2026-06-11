package com.example.helpdesk.controller;

import com.example.helpdesk.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tickets")
public class AdminTicketController {

    private static final String CUSTOMER_FILTER = "Иван";

    private final TicketService ticketService;

    public AdminTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        model.addAttribute("ticketPageHeading", "Список заявок");
        model.addAttribute("ticketPageDescription", "Закрытая зона администратора");
        return "admin/tickets";
    }

    @GetMapping("/status/new")
    public String newTickets(Model model) {
        model.addAttribute("tickets", ticketService.getNewTickets());
        model.addAttribute("ticketPageHeading", "Новые заявки");
        model.addAttribute("ticketPageDescription", "Фильтр по статусу NEW.");
        return "admin/tickets";
    }

    @GetMapping("/customer")
    public String customerTickets(Model model) {
        model.addAttribute("tickets", ticketService.getTicketsByCustomerNamePart(CUSTOMER_FILTER));
        model.addAttribute("ticketPageHeading", "Заявки клиента");
        model.addAttribute("ticketPageDescription",
                "Фильтр по части имени: " + CUSTOMER_FILTER + ".");
        return "admin/tickets";
    }
}
