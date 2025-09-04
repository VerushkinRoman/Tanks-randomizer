package com.posse.tanksrandomizer.common.compose.utils

import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType.MapperError
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType.TokenError
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import org.jetbrains.compose.resources.getString
import tanks_randomizer.composeapp.generated.resources.*

object ErrorHandler {
    suspend fun getErrorMessage(error: Error): String {
        return getString(
            resource = when (error) {
                is NetworkError -> when (error) {
                    NetworkError.ApiError -> Res.string.network_api_error
                    NetworkError.ApplicationIsBlocked -> Res.string.network_app_blocked
                    is NetworkError.FieldListLimitExceeded -> Res.string.network_field_limit_exceeded
                    is NetworkError.FieldNotFound -> Res.string.network_field_not_found
                    is NetworkError.FieldNotSpecified -> Res.string.network_field_not_specified
                    NetworkError.InvalidApplicationId -> Res.string.network_invalid_app_id
                    is NetworkError.InvalidField -> Res.string.network_invalid_field
                    NetworkError.InvalidIpAddress -> Res.string.network_invalid_ip
                    NetworkError.MethodDisabled -> Res.string.network_method_disabled
                    NetworkError.MethodNotFound -> Res.string.network_method_not_found
                    NetworkError.NoInternet -> Res.string.network_no_internet
                    NetworkError.RequestLimitExceeded -> Res.string.network_request_limit
                    NetworkError.Serialization -> Res.string.network_serialization
                    NetworkError.ServerError -> Res.string.network_server_error
                    NetworkError.SourceNotAvailable -> Res.string.network_source_unavailable
                    NetworkError.Unknown -> Res.string.network_unknown
                }

                is DomainErrorType -> when (error) {
                    MapperError -> Res.string.domain_mapper_error
                    TokenError -> Res.string.domain_token_error
                }

                else -> Res.string.something_went_wrong
            },
            formatArgs = when (error) {
                is NetworkError -> when (error) {
                    is NetworkError.FieldListLimitExceeded -> arrayOf(error.field)
                    is NetworkError.FieldNotFound -> arrayOf(error.field)
                    is NetworkError.FieldNotSpecified -> arrayOf(error.field)
                    is NetworkError.InvalidField -> arrayOf(error.field)
                    else -> emptyArray()
                }

                else -> emptyArray()
            }
        )
    }
}
