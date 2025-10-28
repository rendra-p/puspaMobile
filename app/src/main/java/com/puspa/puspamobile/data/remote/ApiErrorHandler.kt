package com.puspa.puspamobile.data.remote

import org.json.JSONObject
import retrofit2.Response

object ApiErrorHandler {
    fun getErrorMessage(response: Response<*>): String {
        val errorBody = try {
            response.errorBody()?.string()
        } catch (_: Exception) {
            null
        }

        if (errorBody.isNullOrEmpty()) return "Terjadi kesalahan"

        var message = "Terjadi kesalahan"

        try {
            val json = JSONObject(errorBody)

            message = json.optString("message", message)

            if (json.has("errors")) {
                val errors = json.get("errors")

                when (errors) {
                    is JSONObject -> {
                        val keys = errors.keys()
                        while (keys.hasNext()) {
                            val key = keys.next()
                            val value = errors.get(key)

                            if (value is org.json.JSONArray && value.length() > 0) {
                                message = value.getString(0)
                                break
                            }
                            else if (value is String) {
                                message = value
                                break
                            }
                        }
                    }

                    is String -> {
                        message = errors
                    }

                    is org.json.JSONArray -> {
                        if (errors.length() > 0) {
                            message = errors.getString(0)
                        }
                    }
                }
            }
        } catch (_: Exception) {
            // Parsing gagal, biarkan pesan default
        }

        return message
    }
}

