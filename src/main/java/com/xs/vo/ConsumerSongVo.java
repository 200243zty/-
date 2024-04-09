package com.xs.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ConsumerSongVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long consumerId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long songId;


}
