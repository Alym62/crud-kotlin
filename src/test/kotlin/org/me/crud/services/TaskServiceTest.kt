package org.me.crud.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.me.crud.domain.TaskEntity
import org.me.crud.payloads.request.TaskDTORequest
import org.me.crud.payloads.response.TaskDTOResponse
import org.me.crud.repositories.TaskRepository
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
class TaskServiceTest {
    @Mock
    private lateinit var taskRepository: TaskRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    @DisplayName("Test unit service - FindAll")
    fun testGetAllTasks() {
        val tasksList = listOf(
            TaskEntity(1L, "Task one", "Description one", false),
            TaskEntity(2L, "Task two", "Description two", false)
        )

        `when`(taskRepository.findAll()).thenReturn(tasksList)

        val taskService = TaskService(taskRepository)
        val taskResponse = taskService.findAll()

        assert(taskResponse.size == tasksList.size)
        assert(taskResponse[0].name == tasksList[0].name)
        assert(taskResponse[0].description == tasksList[0].description)
        assertNotNull(taskResponse)
    }

    @Test
    @DisplayName("Test unit service - FindById")
    fun testGetById() {
        val taskId = 1L
        val taskEntity = TaskEntity(taskId, "Task one", "Description one", true)

        `when`(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity))

        val taskService = TaskService(taskRepository)

        val taskResponse = taskService.findById(taskId)
        val expected = TaskDTOResponse(taskId, taskEntity.name, taskEntity.description, taskEntity.done)

        assertEquals(taskResponse, expected)
    }

    @Test
    @DisplayName("Test unit service - Insert")
    fun testInsert() {
        val taskDTO = TaskDTORequest(name = "Task one", description = "Description one", done = true)
        val taskEntity = TaskEntity(id = null, name = taskDTO.name, description = taskDTO.description, done = taskDTO.done)
        val taskResponseRepository = TaskEntity(id = 1L, name = taskDTO.name, description = taskDTO.description, done = taskDTO.done)

        `when`(taskRepository.save(taskEntity)).thenReturn(taskResponseRepository)

        val taskService = TaskService(taskRepository)
        val taskResponse = taskService.insert(taskDTO)
        val expected = TaskDTOResponse(taskResponseRepository.id!!, taskResponseRepository.name, taskResponseRepository.description, taskResponseRepository.done)

        assertEquals(expected.id, taskResponse.id)
        assertEquals(expected.name, taskResponse.name)
        assertEquals(expected.description, taskResponse.description)
        assertEquals(expected.done, taskResponse.done)
    }
}