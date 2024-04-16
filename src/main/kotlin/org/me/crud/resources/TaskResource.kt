package org.me.crud.resources

import org.me.crud.payloads.request.TaskDTORequest
import org.me.crud.payloads.response.TaskDTOResponse
import org.me.crud.services.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("api/v1/ws/tasks")
class TaskResource (val service: TaskService){
    @GetMapping
    fun findAllTasks(): ResponseEntity<List<TaskDTOResponse>> {
        val listTasks = service.findAll()
        return ResponseEntity.ok().body(listTasks)
    }

    @GetMapping("/{id}")
    fun findByIdTask(@PathVariable id: Long): ResponseEntity<TaskDTOResponse> {
        val task = service.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: $id")
        return ResponseEntity.ok().body(task)
    }

    @PostMapping("/create")
    fun createTask(@RequestBody task: TaskDTORequest): ResponseEntity<TaskDTOResponse> {
        val taskCreated = service.insert(task)
        val uri: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("{id}")
            .buildAndExpand(taskCreated.id)
            .toUri()

        return ResponseEntity.created(uri).body(taskCreated)
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody task: TaskDTORequest): ResponseEntity<TaskDTOResponse> {
        val taskUpdate = service.update(id, task) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: $id")
        return ResponseEntity.ok().body(taskUpdate)
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long) {
        service.delete(id)
    }
}