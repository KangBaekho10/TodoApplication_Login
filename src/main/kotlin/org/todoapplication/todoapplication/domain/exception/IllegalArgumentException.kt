package org.todoapplication.todoapplication.domain.exception

data class IllegalArgumentException(
    override val message: String? = "Writer or Password does not match."
): RuntimeException()
