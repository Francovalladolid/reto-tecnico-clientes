package com.reto.clientes.dto.response;

import java.util.Map;

public record ErrorResponse(int status,
                            String message,
                            Map<String, String> errors) {

}
