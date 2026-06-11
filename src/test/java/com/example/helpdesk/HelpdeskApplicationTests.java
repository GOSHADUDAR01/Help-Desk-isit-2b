package com.example.helpdesk;

import com.example.helpdesk.model.Ticket;
import com.example.helpdesk.model.TicketStatus;
import com.example.helpdesk.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HelpdeskApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

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
    void adminTicketsRequiresLogin() throws Exception {
        mockMvc.perform(get("/admin/tickets"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void adminTicketsPageRendersSeededTickets() throws Exception {
        mockMvc.perform(get("/admin/tickets"))
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
    void newTicketFormRenders() throws Exception {
        mockMvc.perform(get("/tickets/new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0421\u043e\u0437\u0434\u0430\u043d\u0438\u0435 \u0437\u0430\u044f\u0432\u043a\u0438")))
                .andExpect(content().string(containsString("name=\"customerName\"")))
                .andExpect(content().string(containsString("name=\"title\"")))
                .andExpect(content().string(containsString("name=\"description\"")));
    }

    @Test
    void loginPageRenders() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0412\u0445\u043e\u0434 \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u0430")))
                .andExpect(content().string(containsString("name=\"username\"")))
                .andExpect(content().string(containsString("name=\"password\"")));
    }

    @Test
    void invalidLoginRedirectsToLoginError() throws Exception {
        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("username", "admin")
                        .param("password", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    void adminLoginRedirectsToAdminTickets() throws Exception {
        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("username", "admin")
                        .param("password", "admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/tickets"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void userCannotOpenAdminTickets() throws Exception {
        mockMvc.perform(get("/admin/tickets"))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void newTicketsPageFiltersByStatus() throws Exception {
        mockMvc.perform(get("/admin/tickets/status/new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("NEW")))
                .andExpect(content().string(containsString(
                        "\u0424\u0438\u043b\u044c\u0442\u0440 \u043f\u043e \u0441\u0442\u0430\u0442\u0443\u0441\u0443 NEW")))
                .andExpect(content().string(containsString("3 \u0437\u0430\u044f\u0432\u043e\u043a")));
    }

    @Test
    void blankTicketFormShowsValidationErrors() throws Exception {
        mockMvc.perform(post("/tickets")
                        .with(csrf())
                        .param("customerName", "")
                        .param("title", "")
                        .param("description", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0438\u043c\u044f")))
                .andExpect(content().string(containsString(
                        "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0442\u0435\u043c\u0443 \u0437\u0430\u044f\u0432\u043a\u0438")))
                .andExpect(content().string(containsString(
                        "\u041e\u043f\u0438\u0448\u0438\u0442\u0435 \u043f\u0440\u043e\u0431\u043b\u0435\u043c\u0443")));
    }

    @Test
    void validTicketFormCreatesTicketAndRedirectsToSuccess() throws Exception {
        mockMvc.perform(post("/tickets")
                        .with(csrf())
                        .param("customerName", "\u041f\u0430\u0432\u0435\u043b \u041a\u0438\u0440\u0438\u043b\u043b\u043e\u0432")
                        .param("title", "\u041d\u0443\u0436\u0435\u043d \u0434\u043e\u0441\u0442\u0443\u043f \u043a Wi-Fi")
                        .param("description", "\u041d\u0435 \u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0430\u0435\u0442\u0441\u044f \u043d\u043e\u0443\u0442\u0431\u0443\u043a \u0432 \u043f\u0435\u0440\u0435\u0433\u043e\u0432\u043e\u0440\u043d\u043e\u0439."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/tickets/*/success"));

        Ticket savedTicket = ticketRepository.findAll().stream()
                .filter(ticket -> "\u041d\u0443\u0436\u0435\u043d \u0434\u043e\u0441\u0442\u0443\u043f \u043a Wi-Fi".equals(ticket.getTitle()))
                .findFirst()
                .orElseThrow();

        org.assertj.core.api.Assertions.assertThat(savedTicket.getStatus()).isEqualTo(TicketStatus.NEW);
        org.assertj.core.api.Assertions.assertThat(savedTicket.getCreatedAt()).isNotNull();
    }

    @Test
    void ticketSuccessPageIsPublic() throws Exception {
        Long ticketId = ticketRepository.findAll().get(0).getId();

        mockMvc.perform(get("/tickets/" + ticketId + "/success"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0421\u043f\u0430\u0441\u0438\u0431\u043e, \u0432\u0430\u0448\u0430 \u0437\u0430\u044f\u0432\u043a\u0430 \u043f\u0440\u0438\u043d\u044f\u0442\u0430")))
                .andExpect(content().string(containsString(
                        "\u041f\u0435\u0440\u0435\u0439\u0442\u0438 \u0432 \u0430\u0434\u043c\u0438\u043d-\u043f\u0430\u043d\u0435\u043b\u044c")));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void customerTicketsPageFiltersByNamePart() throws Exception {
        mockMvc.perform(get("/admin/tickets/customer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\u0418\u0432\u0430\u043d \u0418\u0432\u0430\u043d\u043e\u0432")))
                .andExpect(content().string(containsString(
                        "\u0418\u0432\u0430\u043d \u0421\u043e\u043a\u043e\u043b\u043e\u0432")))
                .andExpect(content().string(containsString("2 \u0437\u0430\u044f\u0432\u043e\u043a")));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void logoutRedirectsToLoginLogout() throws Exception {
        mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }

    @Test
    void stylesheetIsServed() throws Exception {
        mockMvc.perform(get("/css/style.css"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(".hero-card")));
    }
}
