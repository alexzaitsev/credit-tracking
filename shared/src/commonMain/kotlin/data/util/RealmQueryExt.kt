package data.util

import common.util.asCommonFlow
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.types.RealmObject

fun <T: RealmObject> RealmQuery<T>.asCommonFlow() = this.asFlow().asCommonFlow()
