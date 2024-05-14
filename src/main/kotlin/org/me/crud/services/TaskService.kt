package org.me.crud.services

import org.me.crud.domain.TaskEntity
import org.me.crud.payloads.request.TaskDTORequest
import org.me.crud.payloads.response.TaskDTOResponse
import org.me.crud.repositories.TaskRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import kotlin.jvm.optionals.getOrNull

@Service
class TaskService (val repository: TaskRepository){
    fun findAll(): List<TaskDTOResponse> {
        val tasks = repository.findAll()
        return tasks.map {
            TaskDTOResponse(id = it.id!!, name = it.name, description = it.description, done = it.done)
        }
    }

    fun findById(id: Long): TaskDTOResponse? {
        return repository.findById(id).map {
            TaskDTOResponse(id = it.id!!, name = it.name, description = it.description, done = it.done)
        }.getOrNull()
    }

    @Transactional
    fun insert(task: TaskDTORequest): TaskDTOResponse {
        val taskSave = TaskEntity(id = null, name = task.name, description = task.description, done = task.done)
        val save = repository.save(taskSave)
        return TaskDTOResponse(id = save.id!!, name = save.name, description = save.description, done = save.done)
    }

    @Transactional
    fun update(id: Long, task: TaskDTORequest): TaskDTOResponse {
        return repository.findById(id).map {existingTask ->
            existingTask.name = task.name
            existingTask.description = task.description
            existingTask.done = task.done

            val updateTask = repository.save(existingTask)
            TaskDTOResponse(id = updateTask.id!!, name = updateTask.name, description = updateTask.description, done = updateTask.done)
        }.getOrNull() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: $id")
    }

    fun delete(id: Long) {
        repository.deleteById(id)
    }
}