package be.vamaralds.merode.common

/**
 * Base class for all Merode errors. Merode errors are Exceptions that can be thrown if necessary.
 */
open class MerodeError(msg: String? = null): Exception(msg)