package com.np.kmm_test.utils

import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}
