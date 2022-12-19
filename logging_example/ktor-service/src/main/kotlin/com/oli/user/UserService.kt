package com.oli.user

import org.koin.java.KoinJavaComponent.inject
import org.slf4j.Logger

class UserService(
    private val userDAO: UserDAO,
    private val logger: Logger
) {

    suspend fun createUser(user: User): String? {
        val createdUser = userDAO.create(user)
        return createdUser?.userName
    }

    suspend fun getUser(id: String): User? {
        return try {
            // Try to parse the string into an integer. Throws a number if it is not a number.
            val userID = id.toInt()
            val user = userDAO.read(userID)
            user
        } catch (e: NumberFormatException) {
            // Log, that the provided string was not a number.
            logger.error("Failed to parse number: $id")
            null
        }
    }

    suspend fun getAll(): List<User> {
        val userList = userDAO.readAll()
        return userList
    }

    suspend fun updateUser(user: User): Boolean {
        val isSuccessful = userDAO.update(user)
        return isSuccessful
    }

    suspend fun deleteUser(user: User): Boolean {
        val isSuccessful = userDAO.delete(user.id)

        return isSuccessful
    }

}