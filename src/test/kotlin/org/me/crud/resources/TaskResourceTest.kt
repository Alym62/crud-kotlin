package org.me.crud.resources

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.me.crud.services.TaskService
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
class TaskResourceTest {
    @Mock
    private lateinit var taskService: TaskService

    @BeforeEach
    fun setUp() {
        
    }

    @Test
    @DisplayName("Test unit resource - GET")
    fun findAllTasks() {
    }

    @Test
    @DisplayName("Test unit resource - GET")
    fun findByIdTask() {
    }

    @Test
    @DisplayName("Test unit resource - POST")
    fun createTask() {
    }

    @Test
    @DisplayName("Test unit resource - PUT")
    fun updateTask() {
    }

    @Test
    @DisplayName("Test unit resource - DELETE")
    fun deleteTask() {
    }
}