package org.todoapplication.todoapplication.domain.exception

data class IllegalStateException(
    override val message: String? = "Email is already in use"
): RuntimeException()
