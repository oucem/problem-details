package test;

import com.github.t1.problemdetail.spring.ProblemDetailControllerAdvice;
import com.github.t1.problemdetaildemoapp.SpringDemoBoundary;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static com.github.t1.problemdetail.Constants.PROBLEM_DETAIL_JSON;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SpringDemoIT {
    private final MockMvc mockMvc = MockMvcBuilders
        .standaloneSetup(SpringDemoBoundary.class)
        .setControllerAdvice(ProblemDetailControllerAdvice.class)
        .build();

    @Test void shouldOrderCheapGadget() throws Exception {
        mockMvc.perform(post("/orders").contentType(APPLICATION_FORM_URLENCODED)
            .content("user=1&article=cheap+gadget"))

            .andExpect(status().isOk())
            .andExpect(content().json("{" +
                "\"shipment-id\":\"1:cheap gadget:" + LocalDate.now() + "\"," +
                "\"article\":\"cheap gadget\"," +
                "\"user\":1" +
                "}"));
    }

    @Test void shouldFailToOrderExpensiveGadget() throws Exception {
        mockMvc.perform(post("/orders").contentType(APPLICATION_FORM_URLENCODED)
            .content("user=1&article=expensive+gadget"))

            .andExpect(status().isForbidden())
            .andExpect(content().contentType(PROBLEM_DETAIL_JSON))
            .andExpect(content().json("{" +
                "\"type\":\"https://example.com/probs/out-of-credit\"" +
                // "\"title\":\"You do not have enough credit.\"," +
                //     .hasDetail("Your current balance is 30, but that costs 50.")
                //     .hasInstance("/account/12345/msgs/abc"::equals)
                //     .checkExtensions(extended -> {
                //         then(extended.balance).isEqualTo(30);
                //         then(extended.accounts).containsExactly(ACCOUNT_1, ACCOUNT_2);
                "}"));
    }
}
