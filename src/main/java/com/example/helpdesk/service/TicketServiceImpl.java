package com.example.helpdesk.service;

import com.example.helpdesk.dto.TicketCreateDto;
import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.model.TicketStatus;
import com.example.helpdesk.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket createTicket(TicketCreateDto ticketCreateDto) {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(ticketCreateDto.getCustomerName());
        ticket.setTitle(ticketCreateDto.getTitle());
        ticket.setDescription(ticketCreateDto.getDescription());
        ticket.setStatus(TicketStatus.NEW);
        ticket.setCreatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Ticket> getNewTickets() {
        return ticketRepository.findByStatus(TicketStatus.NEW);
    }

    @Override
    public List<Ticket> getTicketsByCustomerNamePart(String customerNamePart) {
        return ticketRepository.findByCustomerNameContainingIgnoreCase(customerNamePart);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка не найдена: " + id));
    }
}
