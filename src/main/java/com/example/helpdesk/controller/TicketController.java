package com.example.helpdesk.controller;

import com.example.helpdesk.dto.TicketCreateDto;
import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private static final String CUSTOMER_FILTER = "Иван";

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String showTickets(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        model.addAttribute("ticketPageHeading", "\u0421\u043f\u0438\u0441\u043e\u043a \u0437\u0430\u044f\u0432\u043e\u043a");
        model.addAttribute("ticketPageDescription",
                "\u0414\u0430\u043d\u043d\u044b\u0435 \u0437\u0430\u0433\u0440\u0443\u0436\u0430\u044e\u0442\u0441\u044f \u0438\u0437 H2 Database.");
        return "tickets";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new TicketCreateDto());
        return "ticket-form";
    }

    @PostMapping
    public String createTicket(
            @Valid @ModelAttribute("ticket") TicketCreateDto ticketCreateDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "ticket-form";
        }

        Ticket savedTicket = ticketService.createTicket(ticketCreateDto);
        return "redirect:/tickets/" + savedTicket.getId() + "/success";
    }

    @GetMapping("/{id}/success")
    public String showSuccessPage(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        return "ticket-success";
    }

    @GetMapping("/status/new")
    public String newTickets(Model model) {
        model.addAttribute("tickets", ticketService.getNewTickets());
        model.addAttribute("ticketPageHeading",
                "\u041d\u043e\u0432\u044b\u0435 \u0437\u0430\u044f\u0432\u043a\u0438");
        model.addAttribute("ticketPageDescription",
                "\u0424\u0438\u043b\u044c\u0442\u0440 \u043f\u043e \u0441\u0442\u0430\u0442\u0443\u0441\u0443 NEW.");
        return "tickets";
    }

    @GetMapping("/customer")
    public String customerTickets(Model model) {
        model.addAttribute("tickets",
                ticketService.getTicketsByCustomerNamePart(CUSTOMER_FILTER));
        model.addAttribute("ticketPageHeading",
                "\u0417\u0430\u044f\u0432\u043a\u0438 \u043a\u043b\u0438\u0435\u043d\u0442\u0430");
        model.addAttribute("ticketPageDescription",
                "\u0424\u0438\u043b\u044c\u0442\u0440 \u043f\u043e \u0447\u0430\u0441\u0442\u0438 \u0438\u043c\u0435\u043d\u0438: "
                        + CUSTOMER_FILTER + ".");
        return "tickets";
    }
}
