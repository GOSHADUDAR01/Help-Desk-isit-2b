package com.example.helpdesk.service;

import com.example.helpdesk.dto.TicketCreateDto;
import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.model.TicketStatus;
import com.example.helpdesk.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        ticketService = new TicketServiceImpl(ticketRepository);
    }

    @Test
    void createTicketSavesNewTicketWithDtoData() {
        TicketCreateDto dto = new TicketCreateDto();
        dto.setCustomerName("Иван Иванов");
        dto.setTitle("Не работает принтер");
        dto.setDescription("Принтер не печатает документы.");

        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket ticket = invocation.getArgument(0);
            ticket.setId(10L);
            return ticket;
        });

        Ticket savedTicket = ticketService.createTicket(dto);

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository).save(ticketCaptor.capture());

        Ticket ticketToSave = ticketCaptor.getValue();
        assertThat(ticketToSave.getCustomerName()).isEqualTo("Иван Иванов");
        assertThat(ticketToSave.getTitle()).isEqualTo("Не работает принтер");
        assertThat(ticketToSave.getDescription()).isEqualTo("Принтер не печатает документы.");
        assertThat(ticketToSave.getStatus()).isEqualTo(TicketStatus.NEW);
        assertThat(ticketToSave.getCreatedAt()).isNotNull();
        assertThat(savedTicket.getId()).isEqualTo(10L);
    }

    @Test
    void getTicketByIdThrowsReadableExceptionWhenTicketMissing() {
        when(ticketRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.getTicketById(404L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Заявка не найдена: 404");

        verify(ticketRepository).findById(404L);
    }
}
