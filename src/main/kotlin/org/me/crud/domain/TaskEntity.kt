package org.me.crud.domain

import jakarta.persistence.*

@Entity
@Table(name = "tb_tasks")
data class TaskEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var name: String,
    var description: String,
    var done: Boolean
) { }