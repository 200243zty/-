package com.xs.domain;

import lombok.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class MessageEvent<T> extends ApplicationEvent {
    public MessageEvent(Object source, T content) {
        super(source);
        this.content=content;
    }
    private T content;

//    private ReplyTypeEnum type;
}
