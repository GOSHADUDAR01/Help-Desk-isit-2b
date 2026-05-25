package com.example.helpdesk.controller;

import com.example.helpdesk.model.TicketStatus;
import com.example.helpdesk.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TicketController {

    private static final String CUSTOMER_FILTER = "\u0418\u0432\u0430\u043d";

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/tickets")
    public String tickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAllByOrderByCreatedAtDesc());
        model.addAttribute("ticketPageHeading", "\u0421\u043f\u0438\u0441\u043e\u043a \u0437\u0430\u044f\u0432\u043e\u043a");
        model.addAttribute("ticketPageDescription",
                "\u0414\u0430\u043d\u043d\u044b\u0435 \u0437\u0430\u0433\u0440\u0443\u0436\u0430\u044e\u0442\u0441\u044f \u0438\u0437 H2 Database.");
        return "tickets";
    }

    @GetMapping("/tickets/new")
    public String newTickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findByStatus(TicketStatus.NEW));
        model.addAttribute("ticketPageHeading",
                "\u041d\u043e\u0432\u044b\u0435 \u0437\u0430\u044f\u0432\u043a\u0438");
        model.addAttribute("ticketPageDescription",
                "\u0424\u0438\u043b\u044c\u0442\u0440 \u043f\u043e \u0441\u0442\u0430\u0442\u0443\u0441\u0443 NEW.");
        return "tickets";
    }

    @GetMapping("/tickets/customer")
    public String customerTickets(Model model) {
        model.addAttribute("tickets",
                ticketRepository.findByCustomerNameContainingIgnoreCase(CUSTOMER_FILTER));
        model.addAttribute("ticketPageHeading",
                "\u0417\u0430\u044f\u0432\u043a\u0438 \u043a\u043b\u0438\u0435\u043d\u0442\u0430");
        model.addAttribute("ticketPageDescription",
                "\u0424\u0438\u043b\u044c\u0442\u0440 \u043f\u043e \u0447\u0430\u0441\u0442\u0438 \u0438\u043c\u0435\u043d\u0438: "
                        + CUSTOMER_FILTER + ".");
        return "tickets";
    }
}
