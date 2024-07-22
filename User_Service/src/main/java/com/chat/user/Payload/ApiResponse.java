package com.chat.user.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author nawaz
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

	private String message;
	private boolean success;
}
