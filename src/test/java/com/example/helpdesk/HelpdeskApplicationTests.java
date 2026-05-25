package com.example.helpdesk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelpdeskApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homePageRenders() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0414\u043e\u0431\u0440\u043e \u043f\u043e\u0436\u0430\u043b\u043e\u0432\u0430\u0442\u044c \u0432 Help Desk")))
                .andExpect(content().string(containsString(
                        "\u0411\u0430\u0437\u0430 \u0437\u043d\u0430\u043d\u0438\u0439")));
    }

    @Test
    void aboutPageRenders() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0423\u0447\u0435\u0431\u043d\u0430\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u0430 Help Desk")));
    }

    @Test
    void contactsPageRenders() throws Exception {
        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("support@helpdesk.local")))
                .andExpect(content().string(containsString(
                        "\u0427\u0442\u043e \u0443\u043a\u0430\u0437\u0430\u0442\u044c \u0432 \u043e\u0431\u0440\u0430\u0449\u0435\u043d\u0438\u0438")));
    }

    @Test
    void faqPageRenders() throws Exception {
        mockMvc.perform(get("/faq"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0427\u0430\u0441\u0442\u044b\u0435 \u0432\u043e\u043f\u0440\u043e\u0441\u044b")));
    }

    @Test
    void ticketsPageRendersSeededTickets() throws Exception {
        mockMvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0421\u043f\u0438\u0441\u043e\u043a \u0437\u0430\u044f\u0432\u043e\u043a")))
                .andExpect(content().string(containsString(
                        "\u041d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043f\u0440\u0438\u043d\u0442\u0435\u0440")))
                .andExpect(content().string(containsString(
                        "\u041f\u0440\u043e\u0431\u043b\u0435\u043c\u0430 \u0441 VPN")))
                .andExpect(content().string(containsString("6 \u0437\u0430\u044f\u0432\u043e\u043a")));
    }

    @Test
    void newTicketsPageFiltersByStatus() throws Exception {
        mockMvc.perform(get("/tickets/new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("NEW")))
                .andExpect(content().string(containsString(
                        "\u0424\u0438\u043b\u044c\u0442\u0440 \u043f\u043e \u0441\u0442\u0430\u0442\u0443\u0441\u0443 NEW")))
                .andExpect(content().string(containsString("3 \u0437\u0430\u044f\u0432\u043e\u043a")));
    }

    @Test
    void customerTicketsPageFiltersByNamePart() throws Exception {
        mockMvc.perform(get("/tickets/customer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0418\u0432\u0430\u043d \u0418\u0432\u0430\u043d\u043e\u0432")))
                .andExpect(content().string(containsString(
                        "\u0418\u0432\u0430\u043d \u0421\u043e\u043a\u043e\u043b\u043e\u0432")))
                .andExpect(content().string(containsString("2 \u0437\u0430\u044f\u0432\u043e\u043a")));
    }

    @Test
    void stylesheetIsServed() throws Exception {
        mockMvc.perform(get("/css/style.css"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(".hero-card")));
    }
}
