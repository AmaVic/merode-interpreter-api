package be.vamaralds.merode.store

import be.vamaralds.merode.common.MerodeError

open class StoreError(msg: String? = null): MerodeError(msg)
class RecordNotFoundError(msg: String? = null): StoreError(msg)