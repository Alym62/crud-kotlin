package org.me.crud.config

import org.me.crud.domain.TaskEntity
import org.me.crud.repositories.TaskRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("test")
class ProfileTestConfig {
    @Bean
    fun initData(repository: TaskRepository): CommandLineRunner {
        return CommandLineRunner {
            val task = TaskEntity(id = null, name = "Boards", description = "Initial data", done = false)
            repository.save(task)
        }
    }
}