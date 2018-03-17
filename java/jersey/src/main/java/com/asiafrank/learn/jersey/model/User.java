package com.asiafrank.learn.jersey.model;

import com.asiafrank.learn.jersey.serialize.LocalDateTimeDeserializer;
import com.asiafrank.learn.jersey.serialize.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * User
 * <p>
 * </p>
 * Created at 24/1/2017.
 *
 * @author asiafrank
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class User {
    private String name;

//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;

//    @JsonFormat(pattern = "uuuu-MM-dd HH:mm:ssXXXXX")
    private OffsetDateTime offsetDateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", offsetDateTime=" + offsetDateTime +
                '}';
    }
}
