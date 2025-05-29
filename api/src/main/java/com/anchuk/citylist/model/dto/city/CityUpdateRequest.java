package com.anchuk.citylist.model.dto.city;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.anchuk.citylist.exception.ExceptionConstants.ERR_MSG_NAME_LENGTH;
import static com.anchuk.citylist.exception.ExceptionConstants.ERR_MSG_PHOTO_LENGTH;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityUpdateRequest {
    @Size(min = 3, max = 50, message = ERR_MSG_NAME_LENGTH)
    private String name;
    @Size(min = 10, max = 2048, message = ERR_MSG_PHOTO_LENGTH)
    private String photo;

}
