package org.me.crud.resources

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.me.crud.payloads.request.TaskDTORequest
import org.me.crud.payloads.response.TaskDTOResponse
import org.me.crud.services.TaskService
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(TaskResource::class)
@AutoConfigureMockMvc
class TaskResourceTest {
    @Autowired
    private lateinit var mockMvc: MockMvc;

    @Autowired
    private lateinit var objectMapper: ObjectMapper;

    @Autowired
    private lateinit var taskResource: TaskResource

    @MockBean
    private lateinit var taskService: TaskService

    @Test
    @DisplayName("Test unit resource - GET")
    fun findAllTasks() {
        val tasks = listOf(
            TaskDTOResponse(id = 1L, name = "Task one", description = "Description one", done = true),
            TaskDTOResponse(id = 2L, name = "Task two", description = "Description two", done = false)
        )

        given(taskService.findAll()).willReturn(tasks)

        mockMvc.perform(get("/api/v1/ws/tasks"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(tasks)))
    }

    @Test
    @DisplayName("Test unit resource - GET")
    fun findByIdTask() {
        val taskId = 1L
        val task = TaskDTOResponse(id = taskId, name = "Task one", description = "Description one", done = true)

        given(taskService.findById(taskId)).willReturn(task)

        mockMvc.perform(get("/api/v1/ws/tasks/{id}", taskId))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(task)))
    }

    @Test
    @DisplayName("Test unit resource - POST")
    fun createTask() {
        val taskId = 1L
        val taskRequest = TaskDTORequest(name = "Task one", description = "Description one", done = true)
        val taskResponse = TaskDTOResponse(id = taskId, name = taskRequest.name, description = taskRequest.description, done = taskRequest.done)

        given(taskService.insert(taskRequest)).willReturn(taskResponse)

        mockMvc.perform(post("/api/v1/ws/tasks/create").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(taskRequest)))
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", "http://localhost/api/v1/ws/tasks/create/$taskId"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(taskResponse)))
    }

    @Test
    @DisplayName("Test unit resource - PUT")
    fun updateTask() {
        val taskId = 1L
        val taskRequest = TaskDTORequest(name = "Task updated", description = "Description updated", done = true)
        val taskResponse = TaskDTOResponse(id = taskId, name = taskRequest.name, description = taskRequest.description, done = taskRequest.done)

        given(taskService.update(taskId, taskRequest)).willReturn(taskResponse)

        mockMvc.perform(put("/api/v1/ws/tasks/{id}", taskId).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(taskRequest)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(objectMapper.writeValueAsString(taskResponse)))
    }

    @Test
    @DisplayName("Test unit resource - DELETE")
    fun deleteTask() {
        val taskId = 1L
        mockMvc.perform(delete("/api/v1/ws/tasks/{id}", taskId))
            .andExpect(status().isOk)
            .andExpect(content().string(""))
    }
}