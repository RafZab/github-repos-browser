package pl.rafzab.githubreposervice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.rafzab.githubreposervice.config.mapper.Mapper;
import pl.rafzab.githubreposervice.config.response.ApiData;
import pl.rafzab.githubreposervice.config.response.ApiMessage;
import pl.rafzab.githubreposervice.model.BranchDTO;
import pl.rafzab.githubreposervice.model.RepositoryDTO;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RepositoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRepositoriesTest() throws Exception {
        // Given
        String username = "RafZab";
        String expectedUrl = "/api/v1/users/" + username + "/repositories";

        // When
        MvcResult result = mockMvc.perform(get(expectedUrl)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        ApiData<List<RepositoryDTO>> response = Mapper.mapTo(
                responseContent,
                new TypeReference<>() {
                }
        );

        assertThat(response.getMessage()).isEqualTo(ApiMessage.OK.name());
        assertThat(response.getData()).isNotEmpty();

        AssertionsForClassTypes.assertThat(response.getData().isEmpty()).isFalse();
        RepositoryDTO firstRepo = response.getData().get(0);
        assertThat(firstRepo.getRepositoryName()).isNotNull();
        assertThat(firstRepo.getOwnerLogin()).isNotNull();
        assertThat(firstRepo.getBranches()).isNotEmpty();

        AssertionsForClassTypes.assertThat(firstRepo.getBranches().isEmpty()).isFalse();
        BranchDTO firstBranch = firstRepo.getBranches().get(0);
        assertThat(firstBranch.getBranchName()).isNotNull();
        assertThat(firstBranch.getLastCommitSha()).isNotNull();
    }
}
