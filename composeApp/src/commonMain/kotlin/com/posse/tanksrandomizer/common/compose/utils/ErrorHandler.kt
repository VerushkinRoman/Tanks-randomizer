package com.posse.tanksrandomizer.common.compose.utils

import com.posse.tanksrandomizer.common.data.networking.ApiErrorResponse
import com.posse.tanksrandomizer.common.data.networking.parseApiError
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType.MapperError
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType.NoTokensInRepo
import com.posse.tanksrandomizer.common.domain.utils.DomainErrorType.TokenError
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.NetworkError
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import org.jetbrains.compose.resources.getString
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.auth_cancel
import tanks_randomizer.composeapp.generated.resources.auth_error
import tanks_randomizer.composeapp.generated.resources.auth_expired
import tanks_randomizer.composeapp.generated.resources.domain_mapper_error
import tanks_randomizer.composeapp.generated.resources.domain_token_error
import tanks_randomizer.composeapp.generated.resources.network_api_error
import tanks_randomizer.composeapp.generated.resources.network_app_blocked
import tanks_randomizer.composeapp.generated.resources.network_field_limit_exceeded
import tanks_randomizer.composeapp.generated.resources.network_field_not_found
import tanks_randomizer.composeapp.generated.resources.network_field_not_specified
import tanks_randomizer.composeapp.generated.resources.network_invalid_app_id
import tanks_randomizer.composeapp.generated.resources.network_invalid_field
import tanks_randomizer.composeapp.generated.resources.network_invalid_ip
import tanks_randomizer.composeapp.generated.resources.network_method_disabled
import tanks_randomizer.composeapp.generated.resources.network_method_not_found
import tanks_randomizer.composeapp.generated.resources.network_no_internet
import tanks_randomizer.composeapp.generated.resources.network_request_limit
import tanks_randomizer.composeapp.generated.resources.network_serialization
import tanks_randomizer.composeapp.generated.resources.network_server_error
import tanks_randomizer.composeapp.generated.resources.network_source_unavailable
import tanks_randomizer.composeapp.generated.resources.network_unknown
import tanks_randomizer.composeapp.generated.resources.something_went_wrong

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
                    NoTokensInRepo -> Res.string.domain_token_error
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

    suspend fun getRedirectErrorMessage(errorResponse: ErrorResponse): String {
        val defaultError = getString(Res.string.something_went_wrong)

        val code = try {
            errorResponse.code?.toInt() ?: return defaultError
        } catch (_: Exception) {
            return defaultError
        }

        return when (code) {
            401 -> getString(Res.string.auth_cancel)
            403 -> getString(Res.string.auth_expired)
            410 -> getString(Res.string.auth_error)
            else -> {
                val error = parseApiError(
                    ApiErrorResponse(
                        code = code,
                        message = errorResponse.message ?: return defaultError,
                        field = null,
                        value = null
                    )
                )

                getErrorMessage(error)
            }
        }
    }

    fun Error.isTokenError(): Boolean {
        return this is NetworkError.InvalidField && field.contains("access_token")
                || this == TokenError
    }
}
