/**
 * Interfaces describe Repository contract with async coroutines api.
 *
 */
package com.yasinkacmaz.jetflix.util.storage

import kotlinx.coroutines.flow.Flow
import java.util.Optional

/**
 * Single value repository that don't accept null as value
 */
interface SingleValueRepo<Value : Any> {
    /**
     * Saves [value] async
     */
    suspend fun save(value: Value): Value

    /**
     * Removes value async
     */
    suspend fun remove(): Boolean

    /**
     * @return Flow that contains [Optional.of(Value)] if value is present
     * or [Optional.empty] if value is null
     */
    fun observe(): Flow<Optional<Value>>
}

/**
 * Key-Value repository that don't accept null as a `value` or `key`
 */
interface KeyValueRepo<Key : Any, Value : Any> {
    /**
     * Saves [value] for given [key]
     */
    suspend fun save(key: Key, value: Value): Value

    /**
     * Removes value by given [key] if present
     */
    suspend fun remove(key: Key): Boolean

    /**
     * @return Observable that contains [Optional.Some] if value is present
     * or [Optional.EMPTY] if value is null by given [key]
     */
    fun observe(key: Key): Flow<Optional<Value>>

    /**
     * Saves given [Map] of objects by it's keys
     * @return map of saved objects
     */
    suspend fun save(data: Map<Key, Value>): Map<Key, Value>

    /**
     * Remove values by given set of keys, if present
     */
    suspend fun remove(keys: Set<Key>): Boolean

    /**
     * Removes all data from current repository
     */
    suspend fun removeAll(): Boolean

    /**
     * Observe all objects changes in repository.
     * Object sorting not guarantied here, use `.map { sort(it) }` or custom interface extension
     * to achieve desired sorting order
     */
    fun observeAll(): Flow<List<Value>>
}

/**
 * Key-ListOfValues repository
 */
interface KeyValueListRepo<in Key : Any, Value : Any> {
    /**
     * Saves [value] by [key] on given [io.reactivex.Scheduler]
     */
    suspend fun save(key: Key, value: List<Value>): List<Value>

    /**
     * Removes value by given [key] if present
     */
    suspend fun remove(key: Key): Boolean

    /**
     * @return Observable that contains non empty list if value is present
     * or [emptyList] if no values is stored by given [key]
     */
    fun observe(key: Key): Flow<List<Value>>

    /**
     * Removes all data from current repository
     */
    suspend fun removeAll(): Boolean
}