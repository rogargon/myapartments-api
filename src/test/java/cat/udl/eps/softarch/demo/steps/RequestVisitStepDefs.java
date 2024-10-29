package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Advertisement;
import cat.udl.eps.softarch.demo.domain.AdvertisementStatus;
import cat.udl.eps.softarch.demo.domain.Visit;
import cat.udl.eps.softarch.demo.repository.AdvertisementRepository;
import cat.udl.eps.softarch.demo.repository.AdvertisementStatusRepository;
import cat.udl.eps.softarch.demo.repository.VisitRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestVisitStepDefs {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;
    @Autowired
    private AdvertisementStatusRepository advertisementStatusRepository;

    private MvcResult result;
    private Advertisement advertisement;

    @Given("There is an advertisement with title {string} and address {string}")
    public void thereIsAnAdvertisementWithTitleAndAddress(String title, String address) {
        advertisement = new Advertisement();
        advertisement.setTitle(title);
        advertisement.setAddress(address);
        advertisement.setDescription("A cozy loft in the center of Barcelona");
        advertisement.setPrice(new BigDecimal("1000.00"));
        advertisement.setZipCode("08001");
        advertisement.setCountry("Spain");
        advertisement.setCreationDate(ZonedDateTime.now());
        AdvertisementStatus cur_status = advertisementStatusRepository.findByStatus("Available").stream().findFirst().orElse(null);
        advertisement.setAdStatus(cur_status);

        advertisement = advertisementRepository.save(advertisement);
    }

    @When("I request a visit to the advertisement with title {string}")
    public void iRequestAVisitToTheAdvertisementWithTitle(String title) throws Exception {
        // Ensure that the advertisement exists
        Advertisement advertisement = advertisementRepository.findByTitle(title).get(0);
        assertNotNull(advertisement, "Advertisement should exist");

        // Request visit using MockMvc to simulate a POST request
        result = mockMvc.perform(post("/visits")
                        .contentType("application/json")
                        .content("{\"advertisement\":{\"id\":" + advertisement.getId() + "}}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Then("The visit is successfully requested")
    public void theVisitIsSuccessfullyRequested() {
        assertNotNull(result, "Result should not be null after visit request");
        Assertions.assertEquals(201, result.getResponse().getStatus(), "Expected HTTP status 201 Created");
    }
}
