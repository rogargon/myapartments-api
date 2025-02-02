package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Apartment;
import cat.udl.eps.softarch.demo.domain.Owner;
import cat.udl.eps.softarch.demo.domain.Room;
import cat.udl.eps.softarch.demo.repository.ApartmentRepository;
import cat.udl.eps.softarch.demo.repository.OwnerRepository;
import cat.udl.eps.softarch.demo.repository.RoomRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteRoomStepDefs {

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private RoomRepository roomRepository;

    @When("I try to delete Room with id {long}")
    public void iTryToDeleteRoomWithUserAndRoomName(Long roomId) throws Throwable {
        Room room = RoomUtils.getRoom(roomRepository, roomId);
        stepDefs.result = stepDefs.mockMvc.perform(
                delete(room.getUri())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("I should have {int} Room")
    public void iShouldHaveRoom(int nRooms) {

    }

    @When("I try to delete Room of the apartment {string}")
    public void iTryToDeleteRoomOfTheApartment(String apartmentName) throws Throwable {
        Apartment apartment = apartmentRepository.findByName(apartmentName).get(0);
        Room room = RoomUtils.getRoom(roomRepository, apartment);
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete((room == null?"":room.getUri()))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @When("I try to delete Room with the id {string}")
    public void iTryToDeleteRoomWithTheId(String id) throws Exception {
        if(roomRepository.findById(Long.parseLong(id)).isEmpty()) {
            stepDefs.result = stepDefs.mockMvc.perform(
                            delete("/rooms/" + id)  // Ruta para eliminar el Room con el ID dado
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(AuthenticationStepDefs.authenticate()))
                    .andExpect(status().isNotFound()) 
                    .andDo(print());

        }else{
            stepDefs.result = stepDefs.mockMvc.perform(
                    delete("/rooms/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate())).andDo(print());
        }

    }
}
